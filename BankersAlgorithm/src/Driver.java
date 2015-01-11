/*
<package>
	Banker Algorithm
<.package>
<description>
    Main method 
<.description>
<keywords>
	driver
<.keywords>
*/

public class Driver {
	public static void main(String[] args) {
		Banker b = new Banker(10);
		Client c = new Client("Steve", b, 5, 10, 4000, 5000);
		Client c2 = new Client("Geoff", b, 5, 10, 1000, 2000);
		Client c3 = new Client("Josh", b, 5, 10, 1000, 2000);
		c.start();
		c2.start();
		c3.start();
	}
}
