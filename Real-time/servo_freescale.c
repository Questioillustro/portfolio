/*
<package>
	Servo Controller on Freescale
<.package>
<description>
    Executes concurrent servo control on freescale microcontroller 
<.description>
<keywords>
	servo, concurrency, blob class, real-time
<.keywords>
*/


/*
  Servo: Futaba s3003
    50Hz
    20ms duty cycle
    1-3ms pulse width
*/
void InitializeServoState(struct servo *servo_x, int id, char *p_recipe);
void SwitchCmd(struct servo *servo_x);
void SwitchServoState(struct servo *servo_x);
void CheckOverrideCmd();
void SwitchInputCmd(struct servo *servo_x, char *p_cmd);
void initialize_pwm_1();
void initialize_pwm_2();
void InitializeLeds();
void UpdateServo1();
void UpdateServo2();

#include <hidef.h>      
#include "derivative.h" 
#include <stdio.h>      
#include "types.h"
#include <math.h>

#define MOV (0x20)
#define WAIT (0x40)
#define START_LOOP (0x80)
#define END_LOOP (0xa0)
#define END_RECIPE (0x00)

#define LOOP_ERROR (0x01)
#define INVALID_OPCODE (0x02)

UINT16 elapsed = 0;
UINT16 recipe_size = 20;

// holds user input for command override
char ovr_cmd[2];
int ovr_index = 0;


typedef enum {
  RUN, BEGIN, PAUSE, END, ERROR
} servo_state;

typedef struct {
  int id;
  char *p_recipe;  // recipe under execution
  int index;  // index of current recipe commmand
  int waiting;  // currently waiting state variable
  int loop_start; // start,end index of the loop
  int looping; // track loops to execute
  int position;
  servo_state state; //state
  int error_code;
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
  MOV+4
};

unsigned char recipe1[] = {
  START_LOOP+2,
  MOV+0, WAIT+10,  
  MOV+1, WAIT+10,
  MOV+2, WAIT+10, 
  MOV+3, WAIT+10,
  MOV+4, WAIT+10, 
  MOV+5, WAIT+10, 
  END_LOOP, END_RECIPE
};

unsigned char recipe2[] = {
  START_LOOP+2,
  MOV+5, WAIT+10,  
  MOV+4, WAIT+10, 
  MOV+3, WAIT+10, 
  MOV+2, WAIT+10,
  MOV+1, WAIT+10, 
  MOV+0, WAIT+10, 
  END_LOOP, END_RECIPE
};


void initialize_pwm_1() {
  
  //Scale clock to 50Hz
  PWMPRCLK_PCKA0 = 0;
  PWMPRCLK_PCKA1 = 0;
  PWMPRCLK_PCKA2 = 1;
  PWMSCLA = 8;
  
  PWMPOL_PPOL0 = 1;
  
  // Pulse Width channel 0 is enabled
  PWME_PWME0 = 1;
  
  // Clock SA is the clock source for PWM channel 0
  PWMCLK_PCLK0 = 1;
  
  // Gives us 125 microsecond DTY
  // Set period
  PWMPER0 = 160;              
  // Duty cycle for channel 0
  PWMDTY0 = 3;
}

void initialize_pwm_2() {
      
  // Scale clock by 16
  PWMPRCLK_PCKB0 = 0;
  PWMPRCLK_PCKB1 = 0;
  PWMPRCLK_PCKB2 = 1;
  PWMSCLB = 8;

  // Set polarity  
  PWMPOL_PPOL1 = 1;
  
  // Pulse Width channel 1 is enabled
  PWME_PWME1 = 1;
  
  // Clock SA is the clock source for PWM channel 1
  PWMCLK_PCLK1 = 1;
  
  // Gives us .0625 millisecond DTY
  // Set period
  PWMPER1 = 160;              
  // Duty cycle for channel 1
  PWMDTY1 = 3;
}

void initialize_timer() {
  // Set the timer prescaler to %2, since the bus clock is at 2 MHz,
  // and we want the timer running at 1 MHz
  TSCR2_PR0 = 1;
  TSCR2_PR1 = 1;
  TSCR2_PR2 = 1;
  
    
  // Enable output compare on Channel 1
  TIOS_IOS1 = 1;
  
  // Set up output compare action to toggle Port T, bit 1
  TCTL2_OM1 = 0;
  TCTL2_OL1 = 1;
  
  // Set up timer compare value
  TC1 = 1562;
  
  // Clear the Output Compare Interrupt Flag (Channel 1) 
  TFLG1 = TFLG1_C1F_MASK;
  
  // Enable the output compare interrupt on Channel 1;
  TIE_C1I = 1;  
  
  //
  // Enable the timer
  // 
  TSCR1_TEN = 1;
   
  //
  // Enable interrupts via macro provided by hidef.h
  //
  EnableInterrupts;
}

#pragma push
#pragma CODE_SEG __SHORT_SEG NON_BANKED
//--------------------------------------------------------------       
void interrupt 9 OC1_isr( void )
{
  TC1 += 1562;
  elapsed += 1;
  TFLG1   =   TFLG1_C1F_MASK;  
}
#pragma pop

// This function is called by printf in order to
// output data. Our implementation will use polled
// serial I/O on SCI0 to output the character.
//
// Remember to call InitializeSerialPort() before using printf!
//
// Parameters: character to output
//--------------------------------------------------------------       
void TERMIO_PutChar(INT8 ch)
{
    // Poll for the last transmit to be complete
    do
    {
      // Nothing  
    } while (SCI0SR1_TC == 0);
    
    // write the data to the output shift register
    SCI0DRL = ch;
}

// Initializes SCI0 for 8N1, 9600 baud, polled I/O
// The value for the baud selection registers is determined
// using the formula:
//
// SCI0 Baud Rate = ( 2 MHz Bus Clock ) / ( 16 * SCI0BD[12:0] )
//--------------------------------------------------------------
void InitializeSerialPort(void)
{
    // Set baud rate to ~9600 (See above formula)
    SCI0BD = 13;          
    
    // 8N1 is default, so we don't have to touch SCI0CR1.
    // Enable the transmitter and receiver.
    SCI0CR2_TE = 1;
    SCI0CR2_RE = 1;
}

void UpdateServo1() {
  SwitchServoState(&servo_1);
}

void UpdateServo2() {
  SwitchServoState(&servo_2);
}

void SwitchCmd(servo *servo_x) {
  int index = servo_x->index;
  unsigned char command = servo_x->p_recipe[index];
  unsigned char opcode = command & 0xe0;
  unsigned char param = command & 0x1f;
  
  // Validate param
  if (param < 0 || param > 31) {
    servo_x->state = ERROR;
    return;
  }
  
  // In a waiting state so decrement and return
  if (servo_x->waiting) {
    servo_x->waiting -= 1;
    return;
  }
  
  switch(opcode) {
    // Move servo to new position
    case MOV:
      // Use servo id to determine which pwm to change
      if(servo_x->id == 1) {
        PWMDTY0 = ((param + 3) + (param * 2));
      } else if (servo_x->id == 2) {
        PWMDTY1 = ((param + 3) + (param * 2));
      }
      
      // Set a wait delay to allow servo to move to position
      servo_x->waiting = abs(servo_x->position - param) * 2; 
      servo_x->position = param;
      
      // Move pointer to next opcode
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

void SwitchServoState(servo *servo_x) {
  switch(servo_x->state) {
    case RUN:
      SwitchCmd(servo_x);
      if (servo_x->id == 1) {
        PORTB = PORTB & 0xf0;
      } else if(servo_x->id == 2) {
        PORTB = PORTB & 0x0f;
      }
      break;
    case BEGIN:
      
      break;
    case END:
      // End of recipe led indicator
      if (servo_x->id == 1) {
        PORTB = PORTB & 0xf0;
        PORTB = PORTB | 0x04;
      } else if(servo_x->id == 2) {
        PORTB = PORTB & 0x0f;
        PORTB = PORTB | 0x40;
      }
      break;
    case ERROR:
      switch(servo_x->error_code) {
        // Loop error
        case LOOP_ERROR:
          if (servo_x->id == 1) {
            PORTB = PORTB & 0xf0;
            PORTB = PORTB | 0x02;
          } else if(servo_x->id == 2) {
            PORTB = PORTB & 0x0f;
            PORTB = PORTB | 0x20;
          }
          break;
        case INVALID_OPCODE:
          if (servo_x->id == 1) {
            PORTB = PORTB & 0xf0;
            PORTB = PORTB | 0x01;
          } else if(servo_x->id == 2) {
            PORTB = PORTB & 0x0f;
            PORTB = PORTB | 0x10;
          }
          break;        
      }
      break;
    case PAUSE:
      if (servo_x->id == 1) {
        PORTB = PORTB & 0xf0;
        PORTB = PORTB | 0x08;
      } else if(servo_x->id == 2) {
        PORTB = PORTB & 0x0f;
        PORTB = PORTB | 0x80;
      }
      break;
  }
}

void CheckOverrideCmd() {
  char input_char;  
  
  // if there is a keyboard input, store to array
  if (SCI0SR1_RDRF != 0) {
    input_char = SCI0DRL;
    // when enter is pressed, process input
    if (input_char == 13) {
      SwitchInputCmd(&servo_1, &ovr_cmd[0]);  
      SwitchInputCmd(&servo_2, &ovr_cmd[1]);  
      
      (void)printf("\n\r> ");
      ovr_index = 0;
    } else if (input_char == 8) {
      // handle backspace
      ovr_index--;
    } else {
      ovr_cmd[ovr_index] = input_char;
      // just echo the char to console
      (void)printf("%c", ovr_cmd[ovr_index]);
      ovr_index++;
    }
  }  
}

void SwitchInputCmd(servo *servo_x, char *p_cmd) {
  //Ignor error servos
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
        if(servo_x->state == PAUSE) {
          // set new position
          if (servo_x->position >= 1) {
            servo_x->position -= 1;
          }
          
          // move servo
          if(servo_x->id == 1) {
            PWMDTY0 = (servo_x->position + 3) + (servo_x->position * 2);
          } else if (servo_x->id == 2) {
            PWMDTY1 = (servo_x->position + 3) + (servo_x->position * 2);
          }
          
          // set wait
          servo_x->waiting = 2;
        }
        break;
    case 76:
    case 108:
        //Move to left if recipe is paused
        if(servo_x->state == PAUSE) {
          // set new position
          if (servo_x->position <= 4) {
            servo_x->position += 1;
          }
          
          // move servo
          if(servo_x->id == 1) {
            PWMDTY0 = (servo_x->position + 3) + (servo_x->position * 2);
          } else if (servo_x->id == 2) {
            PWMDTY1 = (servo_x->position + 3) + (servo_x->position * 2);
          }
          
          // set wait
          servo_x->waiting = 2;
        }
        break;
    case 78:
    case 110:
         //No operation performed
         break;
    case 66:
    case 98:
        //Restart the recipe
        InitializeServoState(servo_x, servo_x->id, servo_x->p_recipe);
        servo_x->state = RUN;
        break;
  } // switch servo 1 command
}

void InitializeServoState(servo *servo_x, int id, char *p_recipe) {
  servo_x->id = id;
  servo_x->p_recipe = p_recipe;
  servo_x->index = 0;
  servo_x->waiting = 0;
  servo_x->looping = 0;
  servo_x->position = 0;
  servo_x->error_code = 0;
  servo_x->state = BEGIN;
}

void InitializeLeds() {
  DDRB = 0xFF;
  PORTB = 0x00; 
}
  
void main(void) {
  initialize_pwm_1();
  initialize_pwm_2();
  
  InitializeServoState(&servo_1, 1, &recipe1);
  InitializeServoState(&servo_2, 2, &recipe2);
  
  InitializeSerialPort();
  initialize_timer();
  
  InitializeLeds();

  (void)printf("> ");
  for(;;) {
    if (elapsed > 0) {
      CheckOverrideCmd();
      UpdateServo1();
      UpdateServo2();
      elapsed = 0; 
    }
  } /* loop forever */
}
