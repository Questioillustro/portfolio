/*
<package>
	Graphical Histogram
<.package>
<description>
    Driver program to run the Woolies concurrency simulation
<.description>
<keywords>
	driver
<.keywords>
*/

import java.util.ArrayList;
import java.util.Random;

/**
 * Author: Stephen Brewster
 * Date: 9/21/2014
 * Description: WoolieDriver uses the Woolie class to create
 * a simple threading and concurrency practice program
 */

public class WoolieDriver {
    
    private static ArrayList<Woolie> woolies = new ArrayList<Woolie>();

    public static void main(String args[]) {
	defineWoolies();
	Random rand = new Random();
	for(Woolie w : woolies) {
	    Thread t = new Thread(w);
	    t.start();
	}
    }

    /**
     * Generate a list of woolies, their bridge crossing time in seconds,
     * and their destination city (Merctan, or Scistine)
     */
    private static void defineWoolies() {
	Bridge bridge = new Bridge();
        Woolie w1 = new Woolie("Bixter", 10, "Merctan", bridge);
	Woolie w2 = new Woolie("Simpat", 15, "Sicstine", bridge);
	Woolie w3 = new Woolie("Numpy", 7, "Sicstine", bridge);
	Woolie w4 = new Woolie("IamNumber4", 9, "Merctan", bridge);
	Woolie w5 = new Woolie("Brugh", 11, "Sicstine", bridge);
	Woolie w6 = new Woolie("GrassyFace", 4, "Sicstine", bridge);
	woolies.add(w1);
	woolies.add(w2);
	woolies.add(w3);
	woolies.add(w4);
	woolies.add(w5);
	woolies.add(w6);
    }

}
