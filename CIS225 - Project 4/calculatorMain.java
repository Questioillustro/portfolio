/*
<package>
	Calculator Server
<.package>
<description>
    Starts up the Calculator Server application
<.description>
<keywords>
	frame, main
<.keywords>
*/

import java.awt.*;
import javax.swing.*;

class calculatorMain{	
	
	public static void main(String[] args) {
	 makeFrame frame = new makeFrame();
    frame.setTitle("Fraction Calculator");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(300,200);
    frame.setVisible(true);
	}
}
	