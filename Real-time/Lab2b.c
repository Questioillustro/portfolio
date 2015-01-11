/*
<package>
	Servo Control in QNX
<.package>
<description>
    Concurrently controls 2 servos using the QNX operating system 
<.description>
<keywords>
	threads, concurrency, mutex, blob class, real-time, servo
<.keywords>
*/


/*
  Servo: Futaba s3003
    50Hz
    20ms duty cycle
    1-3ms pulse width
*/
#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include <unistd.h>       /* for sleep() */
#include <stdint.h>       /* for uintptr_t */
#include <hw/inout.h>     /* for in*() and out*() functions */
#include <sys/neutrino.h> /* for ThreadCtl() */
#include <pthread.h>
#include <sys/mman.h>     /* for mmap_device_io() */
#include <assert.h>
#include <sys/netmgr.h>
#include <sys/syspage.h>
#include <time.h>

#define MOV (0x20)
#define WAIT (0x40)
#define START_LOOP (0x80)
#define END_LOOP (0xa0)
#define END_RECIPE (0x00)
#define CENTRE (0xE0)     //Graduate Extension (Moves the servo to the Centre Position)(Parameter=0)

#define LOOP_ERROR (0x01)
#define INVALID_OPCODE (0x02)

#define elapsed = 0;
#define recipe_size = 20;

// holds user input for command override
char ovr_cmd[2];
int ovr_index = 0;

/* The Neutrino IO port used here corresponds to a single register, which is
 * one byte long */
#define PORT_LENGTH 1

// Address for Input port A
#define DATA_ADDRESS_A 0x288
// Address for Output port B
#define DATA_ADDRESS_B 0x289
// Address for Control Register
#define CTRL_ADDRESS 0x28B

// Initialize B as the input
// A is output
#define INIT_BIT 0x00

// Low/High signal values
#define LOW 0
#define HIGH 1

struct _clockperiod clk;

pthread_t *servo_1_control ;
pthread_t *servo_2_control ;
pthread_t *servo_1_pwm ;
pthread_t *servo_2_pwm ;
pthread_t *input_detection ;

typedef enum {
  RUN, BEGIN, PAUSE, END, ERROR
} servo_state;

typedef struct {
  int id;
  unsigned char *p_recipe;  // recipe under execution
  int index;  // index of current recipe commmand
  int waiting;  // currently waiting state variable
  int loop_start; // start,end index of the loop
  int looping; // track loops to execute
  int position;
  servo_state state; //state
  int error_code;
  uintptr_t data_handle;
} servo;

servo servo_1;
servo servo_2;

unsigned char nested_loop_recipe[] = {
  MOV+5,
  MOV+4,
  START_LOOP+2,
  MOV+0,
  MOV+1,
  START_LOOP+2,
  MOV+2,
  MOV+3,
  END_LOOP,
  END_LOOP,
  0x00
};

unsigned char bad_opcode_recipe[] = {
  MOV+0,
  MOV+1,
  0x75,
  0x00
};

unsigned char required[] = {
  MOV+0,
  MOV+5,
  MOV+0,
  MOV+3,
  START_LOOP+0,
  MOV+1,
  MOV+4,
  END_LOOP,
  MOV+0,
  MOV+2,
  WAIT+0,
  MOV+3,
  MOV+2,
  MOV+3,
  WAIT+31,
  WAIT+31,
  WAIT+31,
  MOV+4,
  CENTRE       //Graduate extension with opcode 111 and parameter 0
};

unsigned char recipe1[] = {
  START_LOOP+2,
  MOV+0,
  MOV+1,
  MOV+2,
  MOV+3,
  MOV+4,
  MOV+5,
  MOV+4,
  MOV+3,
  MOV+2,
  MOV+1,
  MOV+0,
  END_LOOP,
  END_RECIPE
};

unsigned char recipe2[] = {
  START_LOOP+2,
  MOV+0, WAIT+5,
  MOV+2, WAIT+5,
  MOV+1, WAIT+5,
  MOV+3, WAIT+5,
  MOV+5, WAIT+5,
  MOV+4, WAIT+5,
  MOV+2, WAIT+5,
  MOV+1, WAIT+5,
  MOV+5, WAIT+5,
  MOV+2, WAIT+5,
  MOV+0, WAIT+5,
  END_LOOP,
  END_RECIPE
};

void InitializeServoState(servo *servo_x, int id);
void SwitchCmd(servo *servo_x);
void SwitchServoState(servo *servo_x);
void CheckOverrideCmd();
void SwitchInputCmd(servo *servo_x, char *p_cmd);
void Initialize();
void RunServo(servo *myServo);
void RunInputDetection();
void GeneratePWM(servo *myServo);

void Initialize() {
	uintptr_t data_handle_a, data_handle_b, ctrl_handle;
	int privity_err;
	pthread_attr_t threadAttributes ;
	int policy ;
	struct sched_param parameters ;

	/* Give this thread root permissions to access the hardware */
	privity_err = ThreadCtl( _NTO_TCTL_IO, NULL );
	if ( privity_err == -1 )
		fprintf( stderr, "can't get root permissions\n" );

	// Set clock freq
	clk.fract = 0;
	clk.nsec = 50000;
	ClockPeriod(CLOCK_REALTIME, &clk, NULL, 0);

	// Initialize handles to PWM ports and their CONTROL port
	ctrl_handle = mmap_device_io(PORT_LENGTH, CTRL_ADDRESS);
	data_handle_a = mmap_device_io(PORT_LENGTH, DATA_ADDRESS_A);
	data_handle_b = mmap_device_io(PORT_LENGTH, DATA_ADDRESS_B);
	out8(ctrl_handle, INIT_BIT); // Set ports A and B to output

	// Create threads for both PWMs
	pthread_attr_init(&threadAttributes) ;		// initialize thread attributes structure -- must do before any other activity on this struct
	pthread_getschedparam(pthread_self(), &policy, &parameters) ;	// get this main thread's scheduler parameters
	pthread_attr_setschedparam(&threadAttributes, &parameters) ;	// set up the pthread_attr struct with the updated priority

	InitializeServoState(&servo_1, 1);
	InitializeServoState(&servo_2, 2);

	servo_1.data_handle = data_handle_a;
	servo_1.p_recipe = recipe2;
	servo_2.data_handle = data_handle_b;
	servo_2.p_recipe = recipe2;

	// now create the threads and pass along its thread number from the loop counter.
	pthread_create( servo_1_control, &threadAttributes, (void *)RunServo, &servo_1 ) ;
	pthread_create( servo_1_pwm, &threadAttributes, (void *)GeneratePWM, &servo_1 ) ;

	pthread_create( servo_2_control, &threadAttributes, (void *)RunServo, &servo_2 ) ;
	pthread_create( servo_2_pwm, &threadAttributes, (void *)GeneratePWM, &servo_2 ) ;

	pthread_create( input_detection, &threadAttributes, (void *)RunInputDetection, NULL) ;
}

void RunServo(servo *myServo) {
	printf("PWM %d Running.\n", myServo->id);

	for (;;) {
		switch(myServo->state) {
		    case RUN:
		    	SwitchCmd(myServo);
		      break;
		    case BEGIN:

		      break;
		    case END:

		      break;
		    case ERROR:
		      switch(myServo->error_code) {
		        // Loop error
		        case LOOP_ERROR:

		          break;
		        case INVALID_OPCODE:

		          break;
		      }
		      break;
		    case PAUSE:

		      break;
		  }

		usleep(100000);
	}

	printf("PWM %d shutting down.\n", myServo->id);
}

void GeneratePWM(servo *myServo) {
	// Execute the move
	for(;;) {
	  out8( myServo->data_handle, HIGH );

	  usleep((myServo->position * 320) + 400);

	  out8( myServo->data_handle, LOW );

	  usleep((myServo->position * 320) + 400);
	}
}

void RunInputDetection () {
	char input[2] ;
	printf("Input detection is running.\n");

	for (;;) {
		printf(">> ");
		scanf("%s", &input);
		SwitchInputCmd(&servo_1, &input[0]);
		SwitchInputCmd(&servo_2, &input[1]);
	}

	printf("Input detection shutting down.\n");
}

void SwitchCmd(servo *servo_x) {
  int index = servo_x->index;
  unsigned char command = servo_x->p_recipe[index];
  unsigned char opcode = command & 0xe0;
  unsigned char param = command & 0x1f;

  if (servo_x->waiting) {
	  servo_x->waiting -= 1;
	  return;
  }

  // Validate param
  if (param < 0 || param > 31) {
    servo_x->state = ERROR;
    return;
  }

  switch(opcode) {
    // Move servo to new position
    case MOV:
      servo_x->waiting = abs(servo_x->position - param) * 2;
      servo_x->position = param;
	  servo_x->index += 1;
      break;
    case WAIT:
      servo_x->waiting = param;
      servo_x->index += 1;
      break;
    case START_LOOP:
      if (servo_x->looping) {
        servo_x->state = ERROR;
        servo_x->error_code = LOOP_ERROR;
      }

      servo_x->looping = param;
      servo_x->loop_start = (index + 1);
      servo_x->index += 1;
      break;
    case END_LOOP:
      if (servo_x->looping) {
        servo_x->looping -= 1;

        // if still looping after decrement, restart loop
        if (servo_x->looping) {
          servo_x->index = servo_x->loop_start;
        }
      } else {
        servo_x->index += 1; // leave loop portion of recipe
      }
      break;
    case END_RECIPE:
      servo_x->state = END;
      break;
    default:
      servo_x->state = ERROR;
      servo_x->error_code = INVALID_OPCODE;
      break;
  } // switch(cmd)
}

void SwitchInputCmd(servo *servo_x, char *p_cmd) {
  //Ignore error servos
  if(servo_x->state == ERROR) {
    return;
  }

  switch(*p_cmd) {
    case 80:
    case 112:
         //pause
         servo_x->state = PAUSE;
      break;
    case 67:
    case 99:
    	// continue
        if (servo_x->state != END) {
          servo_x->state = RUN;
        }
        break;
    case 82:
    case 114:
        //Move to right if recipe is paused
        if(servo_x->state == PAUSE && servo_x->position >= 1) {
        	servo_x->position -= 1;
        }
        break;
    case 76:
    case 108:
        //Move to left if recipe is paused
        if(servo_x->state == PAUSE && servo_x->position <= 4) {
        	servo_x->position += 1;
        }
        break;
    case 78:
    case 110:
         //No operation performed
         break;
    case 66:
    case 98:
        //Restart the recipe
        InitializeServoState(servo_x, servo_x->id);
        servo_x->state = RUN;
        break;
  } // switch servo 1 command
}

void InitializeServoState(servo *servo_x, int id) {
  servo_x->id = id;
  servo_x->index = 0;
  servo_x->waiting = 0;
  servo_x->looping = 0;
  servo_x->position = 0;
  servo_x->error_code = 0;
  servo_x->state = BEGIN;
}

void main(void) {
  Initialize();

  for(;;) {} /* loop forever */

  printf("Exiting application...\n");
  sleep(3);
}
