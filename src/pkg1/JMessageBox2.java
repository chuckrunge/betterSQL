package pkg1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Custom MessageBox Dialogs for messages and
 * data capture.
 */
public class JMessageBox2 extends JFrame 
	implements ActionListener, KeyListener {
	
	public java.lang.String szString;
	static final long serialVersionUID = 0L;
	
	String driver = null;
	String url = null;
	String user = null;
	String pswd = null;
	
	JTextField driverFld = null;
	JTextField urlFld = null;
	JTextField userFld = null;
	JTextField pswdFld = null;

	JTextField myText = new JTextField(20);

	JButton myOK = new JButton("OK");
	JFrame myFrame = new JFrame(); //dummy frame for dialog
	JDialog myDialog;

class WindowHandler extends WindowAdapter {

	//handler for window closing event
	public void windowClosing(WindowEvent e) {
		dispose();			//release the window resources
		System.exit(0);
	} //end windowClosing

} //end WindowHandler

	public JMessageBox2() {
		super();
	}
	public JMessageBox2(String szLabel) {
		super();
		JLabel myLabel = new JLabel(szLabel);
		myOK.addActionListener(this);
		myText.addKeyListener(this);

//		JDialog myDialog = new JDialog(myFrame,"say what?");
		myDialog = new JDialog(myFrame,true);

		FlowLayout flo = new FlowLayout();
		myDialog.getContentPane().setLayout(flo);
		myDialog.getContentPane().add(myLabel);
		myDialog.getContentPane().add(myText);
		myDialog.getContentPane().add(myOK);
		
		myDialog.setSize(300,150);
		myDialog.setLocation(200,100);
		myDialog.setVisible(true); //show();
		
	}

	public JMessageBox2(String szTitle, String szLabel) {
		super();
		JLabel myLabel = new JLabel(szLabel);
		myOK.addActionListener(this);
		myText.addKeyListener(this);

		JFrame myFrame = new JFrame(); //dummy frame for dialog
		myDialog = new JDialog(myFrame,szTitle,true);

		FlowLayout flo = new FlowLayout();
		myDialog.getContentPane().setLayout(flo);
		myDialog.getContentPane().add(myLabel);
		myDialog.getContentPane().add(myText);
		myDialog.getContentPane().add(myOK);
		
		myDialog.setSize(300,150);
		myDialog.setLocation(200,100);

		myDialog.setVisible(true);  //.show();
		
	}

	public JMessageBox2(ConnectionData connectData) {
		super();

		driverFld = new JTextField(20);
		urlFld = new JTextField(20);
		userFld = new JTextField(20);
		pswdFld = new JTextField(20);
		driverFld.setText(connectData.getDriver());
		urlFld.setText(connectData.getUrl());
		userFld.setText(connectData.getUser());
		pswdFld.setText(connectData.getPassword());

		driverFld.addKeyListener(this);
		urlFld.addKeyListener(this);
		userFld.addKeyListener(this);
		pswdFld.addKeyListener(this);
		myOK.addActionListener(this);

		JFrame myFrame = new JFrame(); //dummy frame for dialog
		myDialog = new JDialog(myFrame,"Connection Information",true);

		FlowLayout flo = new FlowLayout();
		myDialog.getContentPane().setLayout(flo);
		myDialog.getContentPane().add(new JLabel("driver"));
		myDialog.getContentPane().add(driverFld);
		myDialog.getContentPane().add(new JLabel("URL"));
		myDialog.getContentPane().add(urlFld);
		myDialog.getContentPane().add(new JLabel("UserID"));
		myDialog.getContentPane().add(userFld);
		myDialog.getContentPane().add(new JLabel("pswd"));
		myDialog.getContentPane().add(pswdFld);
		myDialog.getContentPane().add(myOK);
		
		myDialog.setSize(300,200);
		myDialog.setLocation(200,100);

		myDialog.setVisible(true); //.show();
		
	}

	/**
 * Insert the method's description here.
 * Creation date: (1/27/2007 4:32:07 PM)
 */
public void actionPerformed(ActionEvent e) { 
                //int i; 
                // add action code here 
                Object source = e.getSource(); 
                if(source == myOK) {
	                setString(myText.getText());
	        		driver = driverFld.getText();
	        		url = urlFld.getText();
	        		user = userFld.getText();
	        		pswd = pswdFld.getText();
	                //setVisible(false);
	            	myDialog.setVisible(false);
	                //hide();
	                //if(myFrame != null)
		            //    myFrame.hide();
		            //    myDialog.hide();
		                //setVisible(false);
                } //end if 

} //end method 

public void dispose() {
	myDialog.dispose();
	myFrame.dispose(); 
 	//System.exit(0); 
}

public java.lang.String getString() {
	return szString;
}

public void keyPressed(KeyEvent e) {
	int i = e.getKeyCode();
	if(i == 10) {
    	setString(myText.getText());
    	myDialog.setVisible(false);
	    //hide();
	    //if(myFrame != null)
		//   myFrame.hide();
		//myDialog.hide();
	}
}

public void keyReleased(KeyEvent e) {
	//int i = e.getKeyCode();
	}

public void keyTyped(KeyEvent e) {
		//int i = e.getKeyCode();
	}

public void setString(java.lang.String newString) {
	szString = newString;
}
public String getDriver() {
	return driver;
}
public String getUrl() {
	return url;
}
public String getUser() {
	return user;
}
public String getPswd() {
	return pswd;
}
}