/*
<package>
	Conference Room Simulation
<.package>
<description>
    Project manager class for the simulation 
<.description>
<keywords>
    concurrency, synchronization
<.keywords>
*/

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PM extends Employee {

    private SimulationClock.Stopwatch answeringQuestionsWatch = new SimulationClock.Stopwatch();
    private List<Employee> waiting = Collections.synchronizedList(new ArrayList<Employee>());
   
    int team_count;
    
    final Lock lock = new ReentrantLock();
    final Condition meetingReady = lock.newCondition();
    final Condition meetingComplete = lock.newCondition();
    
    final ConferenceRoom confRoom;
    
    public PM(String name, ConferenceRoom confRoom, int team_count) {
        super("PM", name);
        this.confRoom = confRoom;
        this.team_count = team_count;
    }

    public void askQuestion() {
        synchronized (this) {
            log("receives a question");
            answeringQuestionsWatch.start();

            // The buck stops here, but give it ten minutes.
            SimulationClock.waitMinutes(10);

            answeringQuestionsWatch.pause();
            log("answers a question");
        }
    }

    public int getTimeSpentAnsweringQuestions() {
        return answeringQuestionsWatch.totalTimeElapsed();
    }

    public void goToMorningMeeting(TeamLead lead) {
    	waiting.add(lead);
    	log(", Team lead " + lead.getName() +" arrives at my office");
    	
    	lock.lock();
    	
    	try {
    		// Wait for all memebers to arrive
    		while(waiting.size() < team_count) 
    			meetingReady.await();
    		
    		meetingReady.signal(); // Alert the next thread it can go
    		
    		// Wait for meeting to complete
    		meetingComplete.await();
    		
    		meetingComplete.signal(); // Alert the next thread it can go
	    } catch (InterruptedException err) { 
	    	
	    } finally {
	    	lock.unlock();
    	}
    	
    	SimulationClock.waitMinutes(1);
    	log(", Team lead " + lead.getName() + " leaves my office.");
    	waiting.remove(lead);
    }
    
    @Override
    protected void doMorningWork() {
    	this.conductMorningMeeting();
    	SimulationClock.waitUntil(10, 00);
    	log(" attends executive meeting");
    	SimulationClock.waitMinutes(60);
    	log(" returns to 'work'");
    }
    
    private void conductMorningMeeting() {
    	lock.lock();
    	
		try {
	    	log(" is doing administrative work, waiting for team leaders to arrive");
	    	while(waiting.size() < team_count)
	    		meetingReady.await();
	    	
	    	// Signal team lead that the meeting is beginning
	    	meetingReady.signal();
	    
	    	log(" starts the morning meeting with team leaders");
	    	SimulationClock.waitMinutes(15); // Conduct meeting for 15 minutes
	    	log(" morning meeting is concluded");
	    	
	    	// Signal a team lead the meeting is complete
	    	meetingComplete.signal();
		} catch (InterruptedException err) {} 
		finally {
			lock.unlock();
		}
    }

    @Override
    protected void doAfternoonWork() {
    	SimulationClock.waitUntil(14, 0);
    	log(" attends afternoon executive meeting.");
    	SimulationClock.waitMinutes(60);
    	log(" returns to 'work'.");
    }
    
    public ConferenceRoom getConferenceRoom() {
    	return this.confRoom;
    }
}
