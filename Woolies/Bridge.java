/*
<package>
	Woolies
<.package>
<description>
    Bridge component of the simulation
<.description>
<keywords>
    concurrency, threads
<.keywords>
*/

public class Bridge {
    private int CROSSING = 0;
    private int CAP = 3;

    public synchronized void enterBridge() throws InterruptedException {
	while (CROSSING == CAP) {
	    wait();
	} 

        CROSSING++;	
    }

    public synchronized void leaveBridge() {
	CROSSING--;
        notify();
    }
}
