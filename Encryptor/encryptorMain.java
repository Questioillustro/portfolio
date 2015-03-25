/*
<package>
	Encryptor
<.package>
<description>
    Starts the Encryptor application
<.description>
<keywords>
    driver, swing, frame
<.keywords>
*/

import java.awt.*;
import javax.swing.*;

class encryptorMain{	
	
	public static void main (String[] args) {
	 EncryptorFrame frame = new EncryptorFrame();
    frame.setTitle("Encryptor by Stephen Brewster");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(700,400);
    frame.setVisible(true);
	}
}
	