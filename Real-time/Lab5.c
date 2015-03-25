/*
<package>
	Ultrasonic Measure
<.package>
<description>
    Measures distance using an ultrasonic sensor 
<.description>
<keywords>
	ultrasonic sensor, blob class, real-time
<.keywords>
*/


#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>       /* for sleep() */
#include <stdint.h>       /* for uintptr_t */
#include <hw/inout.h>     /* for in*() and out*() functions */
#include <sys/neutrino.h> /* for ThreadCtl() */
#include <sys/mman.h>     /* for mmap_device_io() */
#include <assert.h>
#include <sys/netmgr.h>
#include <sys/syspage.h>
#include <time.h>

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
#define INIT_BIT 0x02

// Low/High signal values 0/15
#define LOW 0
#define HIGH 1

struct _clockperiod clk;

long GetDiff (struct timespec start, struct timespec end) {
	double start_nano, end_nano;

	end_nano = end.tv_sec * 1000000000 + end.tv_nsec;
	start_nano = start.tv_sec * 1000000000 + start.tv_nsec;
	return end_nano - start_nano;
}

int main(int argc, char *argv[]) {
	uintptr_t ctrl_handle;
	uintptr_t data_handle_a;
	uintptr_t data_handle_b;
	int privity_err;
	double time_elapsed = 0;
	struct timespec start, end;
	int distance = 0;
	clk.fract = 0;
	clk.nsec = 10000;
	ClockPeriod(CLOCK_REALTIME, &clk, NULL, 0);

	/* Set our priority to the maximum, so we won’t get disrupted by anything other than interrupts. */
	{
		struct sched_param param;
		int ret;
		param.sched_priority = sched_get_priority_max( SCHED_RR );
		ret = sched_setscheduler( 0, SCHED_RR, &param);
		assert ( ret != -1 );	// if returns a -1 for failure we stop with error
	}

	/* Give this thread root permissions to access the hardware */
	privity_err = ThreadCtl( _NTO_TCTL_IO, NULL );
	if ( privity_err == -1 )
	{
		fprintf( stderr, "can't get root permissions\n" );
		return -1;
	}

	/* Get a handle to the Control register */
	ctrl_handle = mmap_device_io( PORT_LENGTH, CTRL_ADDRESS );

	/* Get a handle to Output port A */
	data_handle_a = mmap_device_io( PORT_LENGTH, DATA_ADDRESS_A );

	/* Get a handle to Input port B */
	data_handle_b = mmap_device_io( PORT_LENGTH, DATA_ADDRESS_B );

	for( ;; )
	{
		/* Make sure port B isn't listening yet */
		out8( ctrl_handle, 0x00 );

		out8( data_handle_a, HIGH );

		nanospin_ns(50000);

		out8( data_handle_a, LOW );

		nanospin_ns(150000);

		/* Start listening on port B */
		out8( ctrl_handle, INIT_BIT );

		clock_gettime( CLOCK_REALTIME, &start);

		// Don't proceed until the echo line has gone to a 1
		if (in8( data_handle_b ) != 1) {
			continue;
		}

		// Wait for the echo to come back
		while(in8( data_handle_b ) == 1) { }

		// Get end time for distance calculation
		clock_gettime( CLOCK_REALTIME, &end);

		// Find total time for echo to come back
		time_elapsed = GetDiff(start, end);

		// Calculate distance in inches
		distance = ((time_elapsed * 341) / 2000000000) * 100 * 0.393701;
		distance++; //Calibration value!

		if (distance <= 0 || distance >= 1000) {
			printf("*\n");
		} else {
			printf("Distance: %d inches\n", distance);
		}
		// delay before next measurement
		usleep(98000);
	}

	return 0;
}
