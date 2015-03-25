/*
<package>
	Banker Algorithm
<.package>
<description>
    Client object for the practice exercise 
<.description>
<keywords>
    threads, concurrency, banker algorithm
<.keywords>
*/

import java.util.Random;

public class Client extends Thread {
	private String name;
	private Banker banker;
	private int nUnits;
	private int nRequests;
	private long minSleepMillis;
	private long maxSleepMillis;

	public Client(String name, Banker banker, int nUnits, 
			int nRequests, long minSleepMillis, long maxSleepMillis) 
	{
		super(name);
		this.banker = banker;
		this.nUnits = nUnits;
		this.nRequests = nRequests;
		this.minSleepMillis = minSleepMillis;
		this.maxSleepMillis = maxSleepMillis;
	}
	
	public void run() {
		Random rnd = new Random();
		int units = rnd.nextInt(nUnits) + 1;
		banker.setClaim(nUnits);

		for (int i = 0; i < nRequests; i ++) {
			int remaining = banker.remaining();
			if (remaining == 0) {
				banker.release(nUnits);
				break;
			} else {
				// Request a random number of units, from 0 to remaining
				int req = rnd.nextInt(remaining) + 1;
				banker.request(req);
			}

			int sleep = rnd.nextInt((int)(maxSleepMillis - minSleepMillis));
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		System.out.println("Thread " + this.getName() + " exits.");
	}
}
