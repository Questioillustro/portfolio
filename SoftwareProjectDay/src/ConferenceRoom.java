/*
<package>
	Conference Room Simulation
<.package>
<description>
    Simulates the conference room component of the simulation 
<.description>
<keywords>
    explicit lock, concurrency
<.keywords>
*/

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ConferenceRoom {
	
	final Lock lock = new ReentrantLock();
	final Condition isAvailable = lock.newCondition();
	
	public Lock getLock() {
		return this.lock;
	}
	
	public Condition getCondition() {
		return this.isAvailable;
	}

}
