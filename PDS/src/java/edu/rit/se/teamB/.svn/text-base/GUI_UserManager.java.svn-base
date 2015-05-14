package edu.rit.se.teamB;

import javax.swing.JPanel;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;

import com.sun.xml.internal.ws.api.server.Container;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Font;

public class GUI_UserManager extends JPanel implements ActionListener{

	GUI_Controller parent;
	ArrayList<User> users;
	JPanel fullpanel;
	Color bgColor;
	JList userListDisplay;
	ArrayList<User> userList;
	JTextField username, password;
	JCheckBox manager;
	JButton btnAddUser, btnModify, btnRemove, btnSave, btnCancel;
	User selected;
	boolean mod;
	
	/**
	 * Create the panel.
	 */
	public GUI_UserManager(GUI_Controller parent) {
		this.parent = parent;
		bgColor = parent.getBgColor();
		fullpanel = new JPanel();
		userList = parent.getUserDB().getUserList();
		Mouse_Listener ml = new Mouse_Listener();
		
		userListDisplay = new JList(userList.toArray());
		userListDisplay.setSelectedIndex(0);
		userListDisplay.setFont(new Font("Arial",Font.PLAIN,34));

//Left Panel
		JPanel leftpanelcontainer = new JPanel();
		leftpanelcontainer.setBackground(bgColor);
		leftpanelcontainer.setBorder(parent.getPanelBorder());
		leftpanelcontainer.setPreferredSize(parent.getSidePanelWidth());
		
		JPanel leftpanel = new JPanel();
		leftpanel.setLayout(new GridLayout(3,1,0,30));
		leftpanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		leftpanel.setBackground(bgColor);
	
		JButton Back = new JButton();
		Back.setPreferredSize(parent.getBtSize());
		Back.setIcon(parent.getBackArrow());
		Back.setHorizontalTextPosition(JButton.CENTER);
		Back.setActionCommand("Back");
		Back.addActionListener(this);
			setLayout(new BorderLayout(0, 0));
		
			JPanel left_subpanel1 = new JPanel();
			left_subpanel1.setBackground(bgColor);
			left_subpanel1.add(Back);
			
			JPanel left_subpanel2 = new JPanel();
			left_subpanel2.setBackground(bgColor);
			
			JPanel left_subpanel3 = new JPanel();
			left_subpanel3.setBackground(bgColor);
		
//Center Panel		
		JPanel centerpanel = new JPanel();
		centerpanel.setBorder(parent.getPanelBorder());
		centerpanel.setLayout(new BorderLayout(0, 0));
		centerpanel.add(userListDisplay);
		centerpanel.setBackground(parent.getBgPanelColor());
		centerpanel.setBorder(BorderFactory.createTitledBorder("User List"));

//Build Panels
		
		leftpanel.add(left_subpanel1);
		leftpanel.add(left_subpanel2);
		leftpanel.add(left_subpanel3);
		leftpanelcontainer.add(leftpanel);
		fullpanel.setLayout(new BorderLayout(0, 0));

		fullpanel.add(leftpanelcontainer, BorderLayout.WEST);
		fullpanel.add(centerpanel, BorderLayout.CENTER);
		
		add(fullpanel);
		
//Right Panel			
		JPanel rightpanelcontainer = new JPanel();	
		rightpanelcontainer.setBackground(bgColor);
		rightpanelcontainer.setBorder(parent.getPanelBorder());
		rightpanelcontainer.setPreferredSize(parent.getSidePanelWidth());
		
		JPanel rightpanel = new JPanel();
		
		rightpanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		rightpanel.setBackground(bgColor);
		rightpanelcontainer.setLayout(new GridLayout(2, 1, 0, 0));
				JPanel rightsubpanel = new JPanel();
				rightpanelcontainer.add(rightsubpanel);
				
				rightsubpanel.setBackground(bgColor);
				
				username = new JTextField();
				password = new JTextField();				
				manager = new JCheckBox("");
				
				username.setEditable(false);
				password.setEditable(false);
				manager.setEnabled(false);
				
				username.addMouseListener(ml);
				password.addMouseListener(ml);
				
				rightsubpanel.setLayout(new GridLayout(3, 2, 0, 10));
				
				rightsubpanel.add(new JLabel("Username: "));
				rightsubpanel.add(username);
				JLabel label_1 = new JLabel("Password: ");
				rightsubpanel.add(label_1);
				rightsubpanel.add(password);
				JLabel label = new JLabel("Manager?: ");
				rightsubpanel.add(label);
				rightsubpanel.add(manager);
		
				rightpanelcontainer.add(rightpanel);
				rightpanel.setLayout(new GridLayout(5, 1, 0, 30));
				
				//centerpanel.add(new GUI_KeyBoard().getContainer());

				
				btnAddUser = new JButton("Add");
				btnAddUser.setPreferredSize(parent.getBtSize());
				rightpanel.add(btnAddUser);
				btnAddUser.addActionListener(this);
				
				btnModify = new JButton("Modify");
				btnModify.setPreferredSize(parent.getBtSize());
				rightpanel.add(btnModify);
				btnModify.addActionListener(this);
				
				btnRemove = new JButton("Remove");
				btnRemove.setPreferredSize(parent.getBtSize());
				rightpanel.add(btnRemove);
				btnRemove.addActionListener(this);
				
				btnSave = new JButton("Save");
				btnSave.setEnabled(false);
				btnSave.setPreferredSize(parent.getBtSize());
				rightpanel.add(btnSave);
				btnSave.addActionListener(this);
				
				btnCancel = new JButton("Cancel");
				btnCancel.setEnabled(false);
				btnCancel.setPreferredSize(parent.getBtSize());
				rightpanel.add(btnCancel);
				btnCancel.addActionListener(this);
				
				fullpanel.add(rightpanelcontainer, BorderLayout.EAST);
			
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("Back")){
			parent.mainMenuCommand("manage");
			doneEditing();
		}
		if(e.getActionCommand().equals("Add")){
			editing();
		}
		if(e.getActionCommand().equals("Modify")){
			mod = true;
			selected = (User) userListDisplay.getSelectedValue();
			if(selected.getUsername().equals("admin")){
				JOptionPane.showMessageDialog(parent, "Default user 'admin' cannot be modified.");
			}
			else{
				username.setText(selected.getUsername());
				password.setText(selected.getPassword());
				editing();
			}				
		}
		if(e.getActionCommand().equals("Remove")){
			if(selected.getUsername().equals("admin")){
				JOptionPane.showMessageDialog(parent, "Default user 'admin' cannot be deleted.");
			}
			else{
				if(JOptionPane.showConfirmDialog(
					    parent,
					    "Do you really want to remove user: " + selected.getUsername() + "?",
					    "Confirm user removal.",
					    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
					parent.getUserDB().getUserList().remove(selected);
				}
			}
		}
		if(e.getActionCommand().equals("Save")){
			// If currently modifying a user, don't write a new one, edit the current
			if(mod){
				selected.setUsername(username.getText());
				selected.setPassword(password.getText());
				selected.setPermissionLevel(manager.isSelected());
			}
			else{
				parent.getUserDB().addUser(username.getText(), password.getText(), manager.isSelected());
			}
			doneEditing();

		}
		if(e.getActionCommand().equals("Cancel")){
			doneEditing();
		}
		
		userList = parent.getUserDB().getUserList();
		userListDisplay.setListData(userList.toArray());
	}
	
	/**
	 * Sets fields to editable for editing
	 */	
	private void editing(){
		username.setEditable(true);
		password.setEditable(true);
		manager.setEnabled(true);
		
		btnAddUser.setEnabled(false);
		btnRemove.setEnabled(false);
		btnModify.setEnabled(false);
		btnSave.setEnabled(true);
		btnCancel.setEnabled(true);
		
		userListDisplay.setEnabled(false);
	}
	
	/**
	 * Sets fields to uneditable
	 */	
	private void doneEditing(){
		username.setText("");
		password.setText("");
		manager.setSelected(false);
		username.setEditable(false);
		password.setEditable(false);
		manager.setEnabled(false);
		
		btnAddUser.setEnabled(true);
		btnRemove.setEnabled(true);
		btnModify.setEnabled(true);
		btnSave.setEnabled(false);
		btnCancel.setEnabled(false);
		
		userListDisplay.setEnabled(true);
		mod = false;
	}

}
