/*
	This class creates a window
*/

import javax.swing.*;
import java.awt.*;

class FrameWindow extends JFrame {

	private static final int FRAME_WIDTH    = 600;
	private static final int FRAME_HEIGHT 	 = 600;
	private static final int FRAME_X_ORIGIN = 150;
	private static final int FRAME_Y_ORIGIN = 250;
	
	public FrameWindow () {
	
		//set default properties
		setTitle 	("Java Window");
		setSize  	(FRAME_WIDTH, FRAME_HEIGHT);
		setLocation (FRAME_X_ORIGIN, FRAME_Y_ORIGIN);
		
		//regist 'Exit upon closing' as a default close operation
		setDefaultCloseOperation( EXIT_ON_CLOSE );
		
		changeBkColor();
		
		//Text Area
		Container contentPane = getContentPane();
		JTextArea textArea;
			textArea = new JTextArea();
			textArea.setColumns(22);
			textArea.setRows(8);
			textArea.setBorder(
					BorderFactory.createLineBorder(Color.RED));
			textArea.setEditable(false);
			contentPane.add(textArea);
	
		textArea.append("hi");
	}
	
	private void changeBkColor() {
		Container contentPane = getContentPane();
		contentPane.setBackground(Color.BLUE);
	}
}