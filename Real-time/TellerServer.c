/*
<package>
	Teller Server
<.package>
<description>
    Simulates a bank day and prints statistics 
<.description>
<keywords>
	threads, concurrency, mutex, blob class, real-time
<.keywords>
*/

#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <pthread.h>
#include <sched.h>
#include <sys/iofunc.h>		// used by named channel calls
#include <sys/dispatch.h>	// used by named channel calls
#include <assert.h>
#include <errno.h>			// defines EOK
#include "TellerInterface.h"

#include <stdint.h>       /* for uintptr_t */
#include <hw/inout.h>     /* for in*() and out*() functions */
#include <sys/neutrino.h> /* for ThreadCtl() */
#include <sys/mman.h>     /* for mmap_device_io() */
#include <sys/netmgr.h>
#include <sys/syspage.h>
#include <sys/time.h>
#include <time.h>
#include <unistd.h>

pthread_t threads[NUMBER_OF_TELLERS] ;	// where we store the results of the thread creation
pthread_t *threadIDs[NUMBER_OF_TELLERS] = {
		&threads[0], &threads[1], &threads[2]
};	// sets up an array of pointers to where we store the thread creation results

pthread_mutex_t lock;

// Perform simulation logic for each second (tick)
void tickUpdate() {
	// Add to queue
	pthread_mutex_lock(&lock);
	if( (queue_head - queue_tail) > max_queue_depth) {
		max_queue_depth = queue_head - queue_tail;
	}
	pthread_mutex_unlock(&lock);

	// Handles customer entrance times
	// Set up a customer entrance sequence
	if(!customer_coming) {
		cust_enter_counter = rand() % ENTER_MAX + ENTER_MIN;
		total_entrance_time += cust_enter_counter;
		if(cust_enter_counter > max_entrance_time) {
			max_entrance_time = cust_enter_counter;
		}
		if(cust_enter_counter < min_entrance_time) {
			min_entrance_time = cust_enter_counter;
		}
		customer_coming = 1;
	// Waiting for customer to enter
	} else if (cust_enter_counter > 0) {
		cust_enter_counter -= 1;
	// New customer enters, initialize and add to queue
	} else if (cust_enter_counter == 0) {
		bankCustomer newCust ;
		newCust.enter_time = malloc(sizeof(struct tm));
		newCust.leave_time = malloc(sizeof(struct tm));
		newCust.service_time = malloc(sizeof(struct tm));
		newCust.time_in_queue = 0;
		newCust.id = customer_count + 1;
		memcpy(newCust.enter_time, sim_clock, sizeof *sim_clock);
		newCust.time_needed = rand() % NEEDED_MAX + NEEDED_MIN;

		// Add to queue
		pthread_mutex_lock(&lock);
		customer_queue[queue_head++] = newCust;
		pthread_mutex_unlock(&lock);

		customer_count++; // Track number of customers for the day
		customer_coming = 0; // customer entered, set up the next one on the following tick
	}
}

void simulation_clock() {
	int pid;
	int chid;
	int count = 0;
	struct _pulse pulse;
	int pulse_id = 1234 ;
	timer_t timer_id;
	struct sigevent event;
	struct itimerspec timer;
	struct _clockperiod clkper;
	float cpu_freq;
	time_t now;
	char buf[80];

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
	clkper.nsec = 1000000;
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
	timer.it_value.tv_sec = 0;
	timer.it_value.tv_nsec = 100000;
	timer.it_interval.tv_sec = 0;
	timer.it_interval.tv_nsec = 100000;

	/* Start the timer. */
	if ( timer_settime( timer_id, 0, &timer, NULL ) == -1 )
	{
		perror("Can’t start timer.\n");
		exit( EXIT_FAILURE );
	}

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

	//Set simulation clock time to 6am
	sim_clock = localtime(&now);
	sim_clock->tm_hour = 9;
	sim_clock->tm_min = 0;
	sim_clock->tm_sec = 0;

	pthread_mutex_unlock(&lock);
	while(sim_clock->tm_hour < 16) {
		// Wait for a pulse.
		pid = MsgReceivePulse ( chid, &pulse, sizeof( pulse ), NULL );

		if(count >= 2) {
			sim_clock->tm_sec++;
			mktime(sim_clock);
			//strftime(buf, sizeof(buf), "%a %Y-%m-%d %H:%M:%S %Z", sim_clock);
			//printf("System Time: %s\n", buf);
			tickUpdate();
			count = 0;
		}
		count++;
	}

	DONE = 1;

	return 0;
}

void StartTellerThreads()
{
	int loopCounter;
	pthread_attr_t threadAttributes ;
	int policy ;
	struct sched_param parameters ;

	pthread_attr_init(&threadAttributes) ;		// initialize thread attributes structure -- must do before any other activity on this struct
	pthread_getschedparam(pthread_self(), &policy, &parameters) ;	// get this main thread's scheduler parameters
	parameters.sched_priority-- ;									// lower the priority
	pthread_attr_setschedparam(&threadAttributes, &parameters) ;	// set up the pthread_attr struct with the updated priority

	// now create the threads and pass along its thread number from the loop counter.
	pthread_create( threadIDs[0], &threadAttributes, (void *)TellerThread, 1 ) ;
	pthread_create( threadIDs[1], &threadAttributes, (void *)TellerThread, 2 ) ;
	pthread_create( threadIDs[2], &threadAttributes, (void *)TellerThread, 3 ) ;
}

void TellerThread( int threadNumber )
{
	int queue_time,sleepTime;
	char buf[80];
	bankTeller *me = malloc(sizeof(bankTeller));
	bankCustomer myCust;
	time_t service_t, entrance_t;
	time_t idle_start, idle_end;

	me->idle = 1;
	me->id = threadNumber;
	me->customers_served = 0;
	me->my_service_time = 0;
	me->idle_time = 0;
	me->max_idle_time = 0;

	printf("Teller %d clocked in\n", me->id) ;

	while(!DONE || !me->idle) {
		pthread_mutex_lock(&lock);
		if(!CustomerQueueIsEmpty() && me->idle) {
			idle_end = mktime(sim_clock);
			if( difftime(idle_end, idle_start) > me->max_idle_time) {
				me->max_idle_time =  difftime(idle_end, idle_start);
			}
			myCust = customer_queue[queue_tail++];
			//Update stats
			total_service_time += myCust.time_needed;

			if (myCust.time_needed > max_transaction) {
				max_transaction = myCust.time_needed;
			}
			if (myCust.time_needed < min_transaction) {
				min_transaction = myCust.time_needed;
			}

			//Record the customer service time
			memcpy(myCust.service_time, sim_clock, sizeof *sim_clock);
			service_t = mktime(myCust.service_time);
			entrance_t = mktime(myCust.enter_time);
			queue_time = difftime(service_t, entrance_t);

			total_queue_time += queue_time ;

			if(queue_time > max_queue_time) {
				max_queue_time = queue_time;
			}

			pthread_mutex_unlock(&lock);

			myCust.time_in_queue = queue_time;
			me->customers[me->customers_served++] = myCust;
			me->my_service_time += myCust.time_needed;

			// service customer
			sleepTime = myCust.time_needed * 1000 * 2;
			usleep(sleepTime);

			//Record the customer departure time
			memcpy(myCust.leave_time, sim_clock, sizeof *sim_clock);
			me->idle = 1;
			idle_start = mktime(sim_clock);
		} else {
			pthread_mutex_unlock(&lock);
			me->idle_time++;
			usleep(2000); // Pause for 1 second
		}
//	    if (breakTime >= mktime(sim_clock) {
//		  // take a break
//	    }
//		// Extension
//		time_t lastBreak = mktime(sim_clock);
//
//		if( difftime( mktime(sim_clock),lastBreak) > 1800 ) {
//			// set next break time
//		}
	}

	pthread_mutex_lock(&lock);
	printf("Teller %d Stats +++++++++++++++++++\n", me->id);
	printf("Total time with customers: %d\n", me->my_service_time);
	printf("Customers served: %d\n", me->customers_served);
	printf("Average Service Time: %d\n", me->my_service_time/me->customers_served);
	printf("Average Idle Time: %d\n", me->idle_time/me->customers_served);
	printf("Max Idle Time: %d\n", me->max_idle_time);

	strftime(buf, sizeof(buf), "%a %Y-%m-%d %H:%M:%S %Z", sim_clock);
	printf("Teller %d clocked out at %s.\n\n", me->id, buf) ;

	tellers_clocked_out++;
	pthread_mutex_unlock(&lock);
}

int CustomerQueueIsEmpty() {
	if (queue_tail == queue_head) {
		return 1;
	} else {
		return 0;
	}
}

void printResults() {
	printf("Totals **************************** \n");
	printf("Total Customers: %d\n", customer_count);
	printf("Average Service Time: %d\n", total_service_time/customer_count);
	printf("Max Queue Depth: %d\n", max_queue_depth);
	printf("Max Queue Time: %d\n", max_queue_time);
	printf("Average Time in Q: %f\n", (double)((double)total_queue_time/(double)customer_count));
	printf("Max Transaction Time: %d\n", max_transaction);
	printf("Min Transaction Time: %d\n", min_transaction);
}

int main(int argc, char *argv[]) {
	srand(time(NULL));
    if (pthread_mutex_init(&lock, NULL) != 0)
    {
        printf("\n mutex init failed\n");
        return 1;
    }

    pthread_mutex_lock(&lock);
    StartTellerThreads();

    simulation_clock();

	pthread_mutex_destroy(&lock);
	while(tellers_clocked_out < 3) {
		sleep(1);
	}
	printResults();
	printf("Exiting main thread in 5 seconds...\n");
	sleep(5);
	return EXIT_SUCCESS;
}
