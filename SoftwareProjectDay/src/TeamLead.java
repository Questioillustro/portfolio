/*
<package>
	Conference Room Simulation
<.package>
<description>
 	Simulates the team leader in the simulation
<.description>
<keywords>
    explicit lock, concurrency
<.keywords>
*/

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TeamLead extends Employee {

    private PM boss;
    private List<Developer> developers = Collections.synchronizedList(new ArrayList<Developer>());

    public TeamLead(String name, PM boss) {
        super("team lead", name);
        this.boss = boss;
    }

    /**
     * Asks a question of this team lead. The team lead might answer it or might pass the buck to the boss.
     */
    public void askQuestion() {
        busyLock.lock();
        log("receives a question");

        // Flip a coin.
        boolean answerable = (Math.random() < 0.5);
        if (!answerable) {
            // Let's ask the PM.
            log("takes a question to the head honcho");
            boss.askQuestion();
            log("gets an answer from the head honcho");
        }

        // Take your answer and go.
        log("answers a question");
        busyLock.unlock();
    }

    public void addEmployee(Developer dev) {
    	this.developers.add(dev);
    }
    
    @Override
    protected void doMorningWork() {
    	this.boss.goToMorningMeeting(this);
    	this.conductTeamMeeting();
    }
    
    public void conductTeamMeeting() {
    	ConferenceRoom confRoom = this.boss.getConferenceRoom(); 
    	confRoom.getLock().lock();
    	
    	log(" gains access to the conference room.");
    	
    	for(Developer dev : developers) {
    		dev.attendTeamMeeting();
    	}
		
    	log(" starts standup meeting.");
    	SimulationClock.waitMinutes(15);
    	
    	log(" completes standup meeting.");
    	confRoom.getLock().unlock();
    	
    	for(Developer dev : developers) {
    		dev.returnToWork();
    	}
	}

    @Override
    protected void doAfternoonWork() {

    }
    
    public List<Developer> getDevelopers() {
    	return this.developers;
    }
}
