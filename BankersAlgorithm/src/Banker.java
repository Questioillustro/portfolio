/*
<package>
	Banker Algorithm
<.package>
<description>
    Banker object for the practice exercise 
<.description>
<keywords>
    threads, concurrency, banker algorithm
<.keywords>
*/

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Banker {

	private int nUnits;
	private int numberOfUnitsOnhand;
	
	// int[] holds currentAllocation and remainingAllocation for a thread
	private Map<Thread, int[]> claims;

	public Banker(int nUnits) {
		this.nUnits = nUnits;
		this.numberOfUnitsOnhand = nUnits;
	}

	public void setClaim(int nUnits) {
		// If the map doesn't exist, lazily create it.
		if (claims == null) {
			// Not sure if we need synchronization or not but eh...
			claims = Collections.synchronizedMap(new HashMap<Thread, int[]>());
		}

		// Figure out if everything is good to go, if not, exit with error
		/* Exit if...
		 *	the thread already has a claim registered, or
		 *	nUnits is not strictly positive, or
		 *	nUnits exceeds the number of resources in the system.*/
		Thread requestor = Thread.currentThread();
		if (claims.get(requestor) != null || nUnits < 0 || nUnits > this.nUnits) {
			System.exit(1);
		}

		// Add the claim
		// claim contains currentAllocation and remainingClaim
		int[] claim = {0,nUnits};
		claims.put(requestor, claim);

		// Let the world know what we did here.
		System.out.println("Thread " + requestor.getName() 
				+ " sets a claim for " + nUnits + " units.");
	}
	
	private boolean safe(int nUnits) {
		List<int[]> tmpClaims = new ArrayList<>( claims.values() );
		System.err.println("OnHand: " + numberOfUnitsOnhand);
		int unitsOnHand = numberOfUnitsOnhand - nUnits; // Verify the request will result in a safe state
		
		if (unitsOnHand < 0) {
			return false;
		}
		
		// Sort the list here on remaining claim
		boolean sorted = false;
		while(!sorted) {
			sorted = true;
			for(int i = 0; i < tmpClaims.size()-1; i++) {
				if (tmpClaims.get(i)[1] > tmpClaims.get(i+1)[1]) {
					int[] temp = tmpClaims.remove(i);
					tmpClaims.add(i+1, temp);
					sorted = false;
				}
			}
		}
		
		// Perform safety check on sorted list
		for (int i = 0; i < tmpClaims.size(); i++) {
	        // If there are not enough units for the ith thread's claim, then it cannot be guaranteed to
	        // complete. Because the array is sorted, no thread after i can be guaranteed to complete,
	        // so we have possible deadlock.

	        if (tmpClaims.get(i)[1] > unitsOnHand) {    // Too few units remain
	        	System.out.println("Units on hand: " + unitsOnHand + ", Claim remaining: " + tmpClaims.get(i)[1]);
	            return false;
	        }
	        
        	// There are enough resources on hand so that we could run this thread until it releases all its
        	// resources, in which case we'd reclaim them and advance to the array entry for the next thread.
        	unitsOnHand += tmpClaims.get(i)[0];
		}

	    // We get here if it is possible for all threads to complete.
	    return true;
	}


	/**
	 * Current thread requests nUnits more resources
	 * @param nUnits number of resources
	 */
	public synchronized boolean request(int nUnits) {
		// If the map doesn't exist, exit as there are no claims
		if (claims == null) {
			System.err.println("Claims null on request(), exiting.");
			System.exit(1);
		}

		// Figure out if everything is good to go, if not, exit with error
		/* Exit if...
		 * (a) the current thread has no claim registered, 
		 * (b) nUnits is not strictly positive or 
		 * (c) nUnits exceeds the invoking thread's remaining claim.*/
		Thread requestor = Thread.currentThread();
		if (claims.get(requestor) == null || nUnits < 0 || nUnits > claims.get(requestor)[1]) {
			System.err.println("Invalid request or thread is null, exiting.");
			System.exit(1);
		}

		// Print the message Thread name requests nUnits units.
		System.out.println("Thread " + requestor.getName() + " requests " + nUnits + " units.");

		/* If allocating the resources results in a safe state,
		 * Print a message Thread name has nUnits units allocated.
		 * Update the banker's state and return to the caller. */
		while(!safe(nUnits)) {
			System.out.println("Thread" + requestor.getName() + " waits.");
			try {
				synchronized(requestor) {
					requestor.wait();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		
		numberOfUnitsOnhand -= nUnits;
		claims.get(requestor)[1] -= nUnits;
		claims.get(requestor)[0] += nUnits;
		return true;
	}

	public synchronized void release(int nUnits) {
		// If the map doesn't exist, exit as there are no claims
		if (claims == null) {
			System.err.println("Claims is null on release(), exiting.");
			System.exit(1);
		}

		// Figure out if everything is goot to go, if not, exit with error
		/* (a) the current thread has no claim registered, 
		 * (b) nUnits is not strictly positive or 
		 * (c) nUnits exceeds the number of units allocated to the current thread.*/
		Thread requestor = Thread.currentThread();
		if (claims.get(requestor) == null || nUnits < 0 || nUnits > claims.get(requestor)[0]) {
			System.err.println("Null or invalid value in release(), exiting.");
			System.exit(1);
		}

		// Print the message Thread name releases nUnits units.
		System.out.println("Thread " + requestor.getName() + " releases " + nUnits + " units.");
		
		numberOfUnitsOnhand += nUnits;
		claims.get(requestor)[0] -= nUnits;
		claims.get(requestor)[1] += nUnits;
		
		List<Thread> threads = new ArrayList<>( claims.keySet());
		for(Thread t : threads) {
			synchronized(t) {
				System.out.println("Threadname: " + t.getName() + " notified.");
				t.notify();
			}
		}
	}

	public synchronized int allocated( ) {
		int[] claim = claims.get(Thread.currentThread());
		if (claim == null) {
			return 0;
		}
		return claim[0];
	}

	public synchronized int remaining( ) {
		Thread thread = Thread.currentThread();
		int[] claim = claims.get(thread);
		if (claim == null) {
			return 0;
		}
		return claim[1];	
	}
}
