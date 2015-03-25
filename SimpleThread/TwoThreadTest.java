/*
<package>
	Simple Thread in Java
<.package>
<description>
    Displays Histogram information for a list of years
<.description>
<keywords>
    driver, threads
<.keywords>
*/

/**
 * Driver for simple threading example
 */

class TwoThreadTest {
    public static void main(String args[]) {
	SimpleThread t1 = new SimpleThread("HI");
	SimpleThread t2 = new SimpleThread("HO");
	Thread thr1 = new Thread(t1);
	Thread thr2 = new Thread(t2);
	thr1.start();
	thr2.start();
    }
}
