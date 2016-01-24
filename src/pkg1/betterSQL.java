package pkg1;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

/* 
 * generic GUI for executing SQL
 */
public class betterSQL extends JFrame 
	implements ActionListener, KeyListener {
	
	static final long serialVersionUID = 0L;

class WindowHandler extends WindowAdapter {

	//handler for window closing event
	public void windowClosing(WindowEvent e) {
		dispose();			//release the window resources
		System.exit(0);
	} //end windowClosing

} //end WindowHandler

JButton command = new JButton("eXecute");	// make it go
JButton info = new JButton("Info");	// make it go
JButton setODBC = new JButton("Connection");	// make it go
JButton reset = new JButton("Reset");	// make it go
JButton cancel = new JButton("caNcel");	// make it go

JTextArea szSQLcommand = new JTextArea(3,20);		// input area for SQL - rows, cols
JTextArea status = new JTextArea(3,1);			// output area for status and errors
JScrollPane resultsPane;				
	
JMenuBar menuBar = new JMenuBar();			// the menu bar
JMenuItem commandItem = new JMenuItem("eXecute");		// execute item
JMenuItem infoItem = new JMenuItem("Information");		// execute item
JMenuItem ODBCItem = new JMenuItem("Connection");		// execute item
JMenuItem clearQueryItem = new JMenuItem("Clear Query"); //clear SQL item
JMenuItem exitItem = new JMenuItem("Exit");		// exit item

Connection connection;				//connection to the database
Statement statement;				// statement object for queries
ResultsModel model;

String szDSNName = "";
ConnectionData connectData = null;

/**
 * BetterSQL constructors
 */
public betterSQL() {
	super();
}
	public betterSQL(String driver, String url,
			String user, String password) {
		super("betterSQL");			// call base constructor
		connectData = new ConnectionData(driver, url, user, password);
		setBounds(0, 0, 400, 300);			// set window bounds
		setDefaultCloseOperation(DISPOSE_ON_CLOSE); // close window operation
		addWindowListener(new WindowHandler() ); 	// listener for window close
		
		//add the input for SQL statements at the top
		command.setToolTipText("Key SQL command and press");
		command.addActionListener(this);
		info.setToolTipText("display db info");
		info.addActionListener(this);
		setODBC.setToolTipText("set JDBC URL");
		setODBC.addActionListener(this);
		reset.addActionListener(this);
		cancel.addActionListener(this);

		JPanel buttonFlow1 = new JPanel();
		buttonFlow1.setLayout(new GridLayout(3,1));
		JPanel buttonFlow2 = new JPanel();
		buttonFlow2.setLayout(new GridLayout(3,1));

		buttonFlow1.add(command);
		buttonFlow1.add(info);
		buttonFlow1.add(setODBC);
		buttonFlow2.add(reset);
		buttonFlow2.add(cancel);

		getContentPane().add(buttonFlow1, BorderLayout.WEST);
		getContentPane().add(buttonFlow2, BorderLayout.EAST);

		JScrollPane sqlPane = new JScrollPane(szSQLcommand);
		getContentPane().add(sqlPane, BorderLayout.NORTH);
		szSQLcommand.addKeyListener(this);

		//add the status reporting area at the bottom
		status.setLineWrap(true);
		status.setWrapStyleWord(true);
		getContentPane().add(status, BorderLayout.SOUTH);

		//create the menubar from the menu item
		JMenu fileMenu = new JMenu("File");  	//create file menu
		fileMenu.setMnemonic('F');		//create shortcut
		commandItem.addActionListener(this);
		infoItem.addActionListener(this);
		ODBCItem.addActionListener(this);
		clearQueryItem.addActionListener(this);
		exitItem.addActionListener(this);
		fileMenu.add(commandItem);	//add command item
		fileMenu.add(infoItem);
		fileMenu.add(ODBCItem);
		fileMenu.add(clearQueryItem);	//add clear query item
		fileMenu.add(exitItem);		//add exit item
		menuBar.add(fileMenu);		//add menu to the menubar
		setJMenuBar(menuBar);		//add menubar to the window

		//establish a database connection and set up the table
		//try {
			model = new ResultsModel();
			JTable table = new JTable(model);	// create a table from the model
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); //use scrollbars
			resultsPane = new JScrollPane(table);	//create scrollbar for table
			getContentPane().add(resultsPane, BorderLayout.CENTER);
		//} //end try

		pack();
		setVisible(true);
				
	} // end betterSQL
	
public void actionPerformed(ActionEvent e) {
	Object source = e.getSource();
	try{
	if(szDSNName == null || szDSNName.length() == 0) 
		szDSNName = "scratchPadDatabase";
	
	if(source == commandItem || source == command)
		executeSQL(connectData, szSQLcommand.getText());
	else if(source == infoItem || source == info) {
		GetDBInfo myInfo = new GetDBInfo(); //szDSNName connectData
		model.setResultSet(myInfo.getTableList(connectData));
		//szSQLcommand.setText(myInfo.getTables());
	}
	else if(source == ODBCItem || source == setODBC) {
			JMessageBox2 myJMsgBox = new JMessageBox2(connectData);
			connectData.setDriver(myJMsgBox.getDriver());
			connectData.setUrl(myJMsgBox.getUrl());
			connectData.setUser(myJMsgBox.getUser());
			connectData.setPassword(myJMsgBox.getPswd());
			szDSNName = connectData.getUrl();
			myJMsgBox.dispose();
			status.setText(szDSNName);
	}
	else if(source == clearQueryItem || source == reset) {
			szSQLcommand.setText("");
			status.setText("");
	}
	else if(source == exitItem || source == cancel) {
		dispose();
		System.exit(0);
	} //end if
	
	} catch(Exception ex) {
		status.setText("Error: Check Database Connection: "+ex.getMessage());
		System.out.println(ex.getMessage());
		ex.printStackTrace();
		
	}
} //end ActionPerformed

public void executeSQL(ConnectionData connectData, String query) {

	try {
			
		Class.forName(connectData.getDriver()); //"sun.jdbc.odbc.JdbcOdbcDriver");
		String sourceURL = connectData.getUrl();  //"jdbc:odbc:" + szDSN;
		Connection dbC = DriverManager.getConnection(sourceURL,connectData.getUser(),connectData.getPassword());  //"Admin","");
		Statement statement = dbC.createStatement();

		if(query == null)
			return;
		System.out.println("executing " + query);

		model.setResultSet(statement.executeQuery(query));
		status.setText("Resultset has " + model.getRowCount() + " rows.");
		
	} //end try
	
	catch(SQLException sqle) {
		status.setText(sqle.getMessage() );
	} //end catch
	catch(ClassNotFoundException e) {
		status.setText(e.getMessage() );
	} //end catch
	catch(Exception exc) {
		status.setText(exc.getMessage() );
	} //end catch
	
} //end executeSQL

public void keyPressed(KeyEvent e) {
	int i = e.getKeyCode();
	if(i == 124)
		return;
	else
		return;
}

public void keyReleased(KeyEvent e) {
//System.out.println("keyReleased");
}

public void keyTyped(KeyEvent e) {
//System.out.println("keyTyped");
}

	public static void main(String[] args) {

		//create the application object
		new betterSQL("org.hsqldb.jdbc.JDBCDriver", 
			"jdbc:hsqldb:hsql://localhost/mdb",
			"SA", //"Admin",
			"");
			
	} //end main
} //end class
