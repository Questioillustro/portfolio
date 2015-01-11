/*
<package>
	Conference Room Simulation
<.package>
<description>
    Controls time for the simulation 
<.description>
<keywords>
	simulation clock, stopwatch
<.keywords>
*/

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * SimulationClock keeps track of a simulated timeline that starts at a given date and progresses at a given rate relative to real time.
 */
public class SimulationClock {

    private static boolean started = false;
    private static Calendar simulatedStartDate;
    private static Calendar actualStartDate;
    private static double speedMultiplier;

    /**
     * Starts the simulation clock at a given simulated Date, running at a given speed.
     * @param startCal The date at which the clock starts according to the simulation.
     * @param speedMultiplier How many milliseconds pass in the simulation for each real millisecond.
     */
    public synchronized static void start(Calendar startCal, double speedMultiplier) {
        if (started)
            throw new IllegalStateException("The SimulationClock was already running. It can only be start()ed once.");
        started = true;
        simulatedStartDate = startCal;
        actualStartDate = GregorianCalendar.getInstance();
        SimulationClock.speedMultiplier = speedMultiplier;
    }

    /**
     * Pauses the current thread for the given number of simulated minutes.
     * @param simulatedMinutes how many simulated minutes to wait
     */
    public static void waitMinutes(int simulatedMinutes) {
        if (!started)
            throw new IllegalStateException("The SimulationClock hasn't been started. Call start() first.");
        long simulatedMilliseconds = simulatedMinutes * 60 * 1000;
        long actualMilliseconds = actualDelay(simulatedMilliseconds);
        if (actualMilliseconds > 0) {
            try {
                synchronized (Thread.currentThread()) {
                    Thread.currentThread().wait(actualMilliseconds);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Pauses the current thread until the simulated time specified.
     * @param clockHour the hour of the simulated time until which to wait
     * @param clockMinute the minute of the simulated time until which to wait
     * @return the simulated duration of the wait in minutes
     */
    public static int waitUntil(int clockHour, int clockMinute) {
        if (!started)
            throw new IllegalStateException("The SimulationClock hasn't been started. Call start() first.");
        Calendar until = getNext(clockHour, clockMinute);
        long simulatedDelay = until.getTimeInMillis() - currentSimulationDate().getTimeInMillis();
        long actualDelay = actualDelay(simulatedDelay);
        if (actualDelay > 0) {
            try {
                synchronized (Thread.currentThread()) {
                    Thread.currentThread().wait(actualDelay);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return (int) (simulatedDelay / (60 * 1000));
    }

    /**
     * Check how many simulated minutes (rounded down) remain until the given time.
     * @param clockHour the hour of the simulated time to check
     * @param clockMinute the minute of the simulated time to check
     */
    public int getMinutesUntil(int clockHour, int clockMinute) {
        if (!started)
            throw new IllegalStateException("The SimulationClock hasn't been started. Call start() first.");
        Calendar until = getNext(clockHour, clockMinute);
        long milliseconds = until.getTimeInMillis() - currentSimulationDate().getTimeInMillis();
        int minutes = (int) (milliseconds / (60 * 1000));
        return minutes;
    }

    /**
     * Check the current simulated time.
     * @return the current simulated time in a string of the format "HH:mm"
     */
    public static String currentTimeString() {
        return new SimpleDateFormat("HH:mm").format(currentSimulationDate().getTime());
    }

    /**
     * Stopwatch acts like, well, a stopwatch. It counts minutes on the SimulationClock.
     */
    public static class Stopwatch {

        private Calendar startDate;
        private long committedDuration = 0, lapTime = 0;

        /**
         * Check whether the watch is running.
         * @return true if the watch is running, false otherwise
         */
        public boolean isRunning() {
            return startDate != null;
        }

        /**
         * Starts or resumes the watch.
         */
        public void start() {
            if (isRunning())
                throw new IllegalStateException("The Stopwatch was already running. It can only be start()ed when it is not running.");
            startDate = GregorianCalendar.getInstance();
            lapTime = 0;
        }

        /**
         * Pauses the watch.
         */
        public void pause() {
            if (!isRunning())
                throw new IllegalStateException("The Stopwatch is not running. It can only be stop()ped when it is running.");

            // Commit the lap time.
            lapTime = currentSimulationDate().getTimeInMillis() - startDate.getTimeInMillis();
            committedDuration += lapTime;
            // Clear the running state.
            startDate = null;
        }

        /**
         * Sets the time elapsed to zero and pauses the watch.
         */
        public void reset() {
            if (isRunning())
                pause();
            lapTime = 0;
            committedDuration = 0;
        }

        /**
         * @return how many simulated minutes have been counted since the last reset
         */
        public int totalTimeElapsed(){
            long milliseconds = committedDuration;
            if (isRunning())
                milliseconds += currentSimulationDate().getTimeInMillis() - startDate.getTimeInMillis();
            else
                milliseconds += lapTime;
            int minutes = (int) (milliseconds / (60 * 1000));
            return minutes;
        }

        /**
         * @return how many simulated minutes have been counted since the last start
         */
        public int lapTimeElapsed(){
            long milliseconds;
            if (isRunning())
                milliseconds = currentSimulationDate().getTimeInMillis() - startDate.getTimeInMillis();
            else
                milliseconds = lapTime;
            int minutes = (int) (milliseconds / (60 * 1000));
            return minutes;
        }

    }

    /**
     * Takes a duration in actual milliseconds and returns the same duration in simulated milliseconds.
     * @param actualMilliseconds a duration in actual milliseconds
     * @return the same duration in simulated milliseconds
     */
    private static long simulatedDelay(long actualMilliseconds) {
        if (!started)
            throw new IllegalStateException("The SimulationClock hasn't been started. Call start() first.");
        return (long) (actualMilliseconds * speedMultiplier);
    }

    /**
     * Takes a duration in actual milliseconds and returns the same duration in simulated milliseconds.
     * @param simulatedMilliseconds a duration in actual milliseconds
     * @return the same duration in simulated milliseconds
     */
    private static long actualDelay(long simulatedMilliseconds) {
        if (!started)
            throw new IllegalStateException("The SimulationClock hasn't been started. Call start() first.");
        return (long) (simulatedMilliseconds / speedMultiplier);
    }

    /**
     * Gets the simulated date that has a given clock time on the same day as the start time.
     * @param hour the hour, from 0 to 23
     * @param minute the minute, from 0 to 60
     * @return the Calendar corresponding to the next simulated date when it will be the given time
     */
    private static Calendar getNext(int hour, int minute) {
        Calendar desiredDate = GregorianCalendar.getInstance();
        desiredDate.setTimeInMillis(simulatedStartDate.getTimeInMillis());
        desiredDate.set(Calendar.HOUR_OF_DAY, hour);
        desiredDate.set(Calendar.MINUTE, minute);
        desiredDate.set(Calendar.SECOND, 0);
        desiredDate.set(Calendar.MILLISECOND, 0);
        return desiredDate;
    }

    /**
     * @return the current date, according to the simulation
     */
    public static Calendar currentSimulationDate() {
        if (!started)
            throw new IllegalStateException("The SimulationClock hasn't been started. Call start() first.");
        Calendar actualNow = GregorianCalendar.getInstance();
        long actualDifference = actualNow.getTimeInMillis() - actualStartDate.getTimeInMillis();
        long simulatedDifference = simulatedDelay(actualDifference);
        long simulatedStartTime = simulatedStartDate.getTimeInMillis();
        Calendar simulatedNow = GregorianCalendar.getInstance();
        simulatedNow.setTimeInMillis(simulatedStartTime + simulatedDifference);
        return simulatedNow;
    }

}
