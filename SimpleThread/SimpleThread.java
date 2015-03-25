/*
<package>
	Simple Thread in Java
<.package>
<description>
    A simple practice in java threads
<.description>
<keywords>
    threads, concurrency
<.keywords>
*/

/**
 * Author: Stephen Brewster 
 * Date: 9/17/2014
 * Description: A simple thread that prints a provided string at a
 * random interval between 0-1000 ms
 */


import java.util.Random;

class SimpleThread implements Runnable{
    private String print_stmt;

    public SimpleThread(String stmt) {
	print_stmt = stmt;
    }

    public void run() {
	int counter = 1;
	while (counter <= 9) {
   	    System.out.println(counter + ". " + print_stmt);
	    counter++;
	    try {
	        sleeper();
	    } catch (InterruptedException err) {}
	} 
	System.out.println("DONE! " + print_stmt);
    }

    private void sleeper() throws InterruptedException {
	Random rand = new Random();
        int randomNum = rand.nextInt((1000 - 0) + 1) + 0;
	Thread.sleep(randomNum);
    }
}
