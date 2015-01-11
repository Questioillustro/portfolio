/*
<package>
	Woolies
<.package>
<description>
    Woolie object, concurrently attempt to cross a bridge
<.description>
<keywords>
    swing, frame, histogram
<.keywords>
*/

/**
 * Author: Stephen Brewster
 * Date: 9/21/2014
 * Description: The Woolie class is part of the Woolie Lab
 * solution for SWEN342. It is an introduction to concurrency
 * and threading in Java
 */

class Woolie extends Thread {

    private String name;
    private int cross_time;
    private String destination;
    private Bridge bridge;

    public Woolie(String name, int cross_time, String destination, Bridge bridge) {
        this.name = name;
	this.cross_time = cross_time;
	this.destination = destination;
	this.bridge = bridge;
    }

    /**
     * Execute the bridge crossing action for the Woolie
     */
    public void run() {
        System.out.println(this.name + " has arrived at the bridge.");

	try {
	    bridge.enterBridge();
	} catch (InterruptedException err) {}

	System.out.println(this.name + " is starting to cross.\n");
	for (int i = cross_time; i > 0; i--) {
	    this.waitASec();
	    System.out.println("\t" + this.name + " " + (cross_time - i + 1) + " seconds");
	}

	System.out.println(this.name + " arrives at " + this.destination);
	bridge.leaveBridge();
    }

    private void waitASec() {
        try {
	    Thread.sleep(1000);
	} catch (Exception err) {}
    }
}
