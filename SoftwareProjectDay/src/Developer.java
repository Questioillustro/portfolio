/*
<package>
	Conference Room Simulation
<.package>
<description>
 	Developer object for the simulation
<.description>
<keywords>
    trivial
<.keywords>
*/

import java.util.concurrent.locks.Condition;


public class Developer extends Employee {

    TeamLead boss;

    public Developer(String name, TeamLead boss) {
        super("developer", name);
        this.boss = boss;
        boss.addEmployee(this);
    }
    
    public void attendTeamMeeting() {
    	log(" attends team meeting.");
    }
    
    public void returnToWork() {
    	log(" returns to work");
    }

    @Override
    protected void doMorningWork() {
    	
    }

    @Override
    protected void doAfternoonWork() {

    }
}
