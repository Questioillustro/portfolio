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

/* The Neutrino IO port used here corresponds to a single register, which is
 * one byte long */
#define PORT_LENGTH 1

/* The first parallel port usually starts at 0x378. Each parallel port is
 * three bytes wide. The first byte is the Data register, the second byte is
 * the Status register, the third byte is the Control register. */
#define DATA_ADDRESS 0x378
#define CTRL_ADDRESS 0x37a
 /* bit 2 = printer initialisation (high to initialise)
  * bit 4 = hardware IRQ (high to enable) */
#define INIT_BIT 0x04

#define LOW 0x00
#define HIGH 0xFF

#define MAX_COUNT 60

int main() {
	int pid;
	int chid;
	int pulse_id = 1234 ;
	timer_t timer_id;
	struct sigevent event;
	struct itimerspec timer;
	struct _clockperiod clkper;
	struct _pulse pulse;
	int count = 0 ;
	float cpu_freq;
	time_t start;

	/* Get the CPU frequency in order to do precise time calculations. */
	cpu_freq = SYSPAGE_ENTRY( qtime )->cycles_per_sec;

	/* Set our priority to the maximum, so we won’t get disrupted by anything other than interrupts. */
	{
		struct sched_param param;
		int ret;
		param.sched_priority = sched_get_priority_max( SCHED_RR );
		ret = sched_setscheduler( 0, SCHED_RR, &param);
		assert ( ret != -1 );	// if returns a -1 for failure we stop with error
	}

	int privity_err;
	uintptr_t ctrl_handle;
	uintptr_t data_handle;

	/////////////////////////////////////////////////////////////////////////////////
	// Beginning of the most important code for Project 3
	/////////////////////////////////////////////////////////////////////////////////
	// Change the clock tick size from 10 ms to 1 ms.
	clkper.nsec = 500000;
	clkper.fract = 0;
	ClockPeriod ( CLOCK_REALTIME, &clkper, NULL, 0 ); // 1ms

	/* Create a channel to receive timer events on. */
	chid = ChannelCreate( 0 );
	assert ( chid != -1 );			// if returns a -1 for failure we stop with error
	/* Set up the timer and timer event. */
	event.sigev_value.sival_int = 210 ;
	event.sigev_notify = SIGEV_PULSE;		// most basic message we can send -- just a pulse number
	event.sigev_coid = ConnectAttach ( ND_LOCAL_NODE, 0, chid, 0, 0 );  // Get ID that allows me to communicate on the channel
	assert ( event.sigev_coid != -1 );		// stop with error if cannot attach to channel
	event.sigev_priority = getprio(0);
	event.sigev_code = 1023;				// As far as I can tell this does not have a meaning
	event.sigev_value.sival_int = pulse_id;		// 4 byte value to pass with the pulse

	// Now create the timer and get back the timer_id value for the timer we created.
	if ( timer_create( CLOCK_REALTIME, &event, &timer_id ) == -1 )	// CLOCK_REALTIME available in all POSIX systems
	{
		perror ( "can’t create timer" );
		exit( EXIT_FAILURE );
	}

	/* Change the timer request to alter the behavior. */
	#if 1
		timer.it_value.tv_sec = 0;
		timer.it_value.tv_nsec = 500000;		// interrupt at 1 ms.
		timer.it_interval.tv_sec = 0;
		timer.it_interval.tv_nsec = 500000;	// keep interrupting every 1 ms.
	#else
		timer.it_value.tv_sec = 0;
		timer.it_value.tv_nsec = 499847;		// exact timing that match PC hardware for 1 ms.
		timer.it_interval.tv_sec = 0;
		timer.it_interval.tv_nsec = 499847;
	#endif

	/* Start the timer. */
	if ( timer_settime( timer_id, 0, &timer, NULL ) == -1 )
	{
		perror("Can’t start timer.\n");
		exit( EXIT_FAILURE );
	}

	/* Keep track of time. */
	start = time(NULL);

	/* Give this thread root permissions to access the hardware */
	privity_err = ThreadCtl( _NTO_TCTL_IO, NULL );
	if ( privity_err == -1 )
	{
		fprintf( stderr, "can't get root permissions\n" );
		return -1;
	}

	/* Get a handle to the parallel port's Control register */
	ctrl_handle = mmap_device_io( PORT_LENGTH, CTRL_ADDRESS );
	/* Initialise the parallel port */
	out8( ctrl_handle, INIT_BIT );

	/* Get a handle to the parallel port's Data register */
	data_handle = mmap_device_io( PORT_LENGTH, DATA_ADDRESS );

	// Simple version that blocks on the message and confirms that we are getting a 1 ms.
	// clock rate.
	for( ;; )
	{
		// Wait for a pulse.
		pid = MsgReceivePulse ( chid, &pulse, sizeof( pulse ),
				NULL );

		if ( count % 2 == 0 ) {
			out8( data_handle, HIGH );
		} else {
			out8( data_handle, LOW );
		}

		count += 1;
	}

	return 0;
}
