/*
<package>
	Conference Room Simulation
<.package>
<description>
    Logging for the simulation 
<.description>
<keywords>
	logger
<.keywords>
*/

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Logger {

    /**
     * Provides standardised logging for actions.
     * @param actorName Who is doing the action?
     * @param actionName What is the action?
     */
    public static void logAction(String actorName, String actionName) {
        String timeOfDay = SimulationClock.currentTimeString();
        String message = timeOfDay + " " + actorName + " " + actionName + ".";
        System.out.println(message);
    }

}
