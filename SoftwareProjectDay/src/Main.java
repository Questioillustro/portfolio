/*
<package>
	Conference Room Simulation
<.package>
<description>
    Driver class for the simulation 
<.description>
<keywords>
	driver
<.keywords>
*/

import java.util.*;

public class Main {

    public static void main(String[] args) {
        // 2014-08-06 06:00:00
        Calendar startDate = new GregorianCalendar(2014, Calendar.AUGUST, 6, 6, 0, 0);
        // one simulated minute per ten actual milliseconds
        double speedFactor = 6000;
        SimulationClock.start(startDate, speedFactor);

        final int team_count = 3;
        final int team_size = 4;
        
        ConferenceRoom confRoom = new ConferenceRoom();
        		
        // Create a team hierarchy.
        PM carmen = new PM("Carmen", confRoom, team_count);
        
        for(int i = 1; i <= team_count; i++) {
        	TeamLead leadObj = new TeamLead(String.valueOf(i), carmen);
        	
        	for(int j = 1; j < team_size; j++) {
        		Developer devObj = new Developer(String.valueOf(i + "-" + j), leadObj);
        	}
        }
    }

}
