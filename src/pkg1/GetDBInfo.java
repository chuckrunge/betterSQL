package pkg1;

/**

 * Insert the type's description here.
 * Creation date: (1/20/2007 9:37:46 PM)
 * @author: Administrator
 */
import java.sql.*;
class GetDBInfo {
	
	public java.lang.String DSNName;
	public java.lang.String szTables;

	ResultSet rsTables = null;
	int i=0,j=0,k=0; 
	
	GetDBInfo() {}
	
	public ResultSet getTableList(ConnectionData connectData) { //szString
		DSNName = connectData.getUrl(); //szString;

	try {
		//load the driver class
		Class.forName(connectData.getDriver()); //"sun.jdbc.odbc.JdbcOdbcDriver"); "org.hsqldb.jdbc.JDBCDriver"

		//define data source for driver    
		String sourceURL = DSNName;  
		
		//create connection thru the driver manager
		Connection dbC = DriverManager.getConnection(sourceURL,connectData.getUser(),connectData.getPassword()); //"SA","" //"Admin","");

               // some information about the connection
               DatabaseMetaData dbMD = dbC.getMetaData ();
               
               System.out.println("\nConnected to " + dbMD.getURL());
               System.out.println("Driver       " + 
                       dbMD.getDriverName());
               System.out.println("Version      " +
                       dbMD.getDriverVersion());
               System.out.println("");
               System.out.println("Product      " +
			dbMD.getDatabaseProductName());
               System.out.println("Version      " +
			dbMD.getDatabaseProductVersion());
               System.out.println("");

// display catalogs
		ResultSet rsCatalogs = dbMD.getCatalogs();
		//display column names
		ResultSetMetaData mdCatalogs = rsCatalogs.getMetaData();
		String[] catNames = new String[mdCatalogs.getColumnCount()];
		int catCount = mdCatalogs.getColumnCount();
		for (int cat = 0; cat < catCount; cat++) {
			catNames[cat] = mdCatalogs.getColumnName(cat + 1);
                //System.out.println("Catalog      " +
		//   mdCatalogs.getColumnName(cat + 1));
		};
        System.out.println("");

String[] tbTypes = {"TABLE", "VIEW", "ALIAS", "SYNONYM"}; 

// display user tables
		rsTables = dbMD.getTables(null,null,null,tbTypes); //cat, schema, name, type

/*
	//display table names
		ResultSetMetaData mdTables = rsTables.getMetaData();
		int tabCount = mdTables.getColumnCount();
		String[] colNames = new String[mdTables.getColumnCount()];

		for (int tab = 0; tab < tabCount; tab++) {
			colNames[tab] = mdTables.getColumnName(tab + 1);
               	//System.out.println("System Table:      " +
		   //mdTables.getColumnName(tab + 1));
		}; //end for

        String [] tblNames = new String[99]; k = 1;szTables = "";
        int m = 0;
        while( rsTables.next() ) {
			for (int tab = 0; tab < tabCount; tab++) {
				//System.out.println(String.valueOf( colNames[tab] ) + " = " +
				//   rsTables.getString(colNames[tab]));
				
				if(String.valueOf( colNames[tab] ).equals("TABLE_NAME")) {
					//System.out.println("*" + rsTables.getString(colNames[tab]) + "*");
					m = m + 1;
					if(m==8) {
						szTables += rsTables.getString(colNames[tab]) + ";" + newline;
						m=0;
					} else {
						szTables += rsTables.getString(colNames[tab]) + "; ";						
					}
				} //end if
			
			} //end for

        } //end while
*/
	} //end try

	catch(ClassNotFoundException cnfe) {
		System.err.println(cnfe);
	} // end catch

	catch(SQLException sqle) {
		System.err.println(sqle);
	} // end catch

    return rsTables;
			
	} //GetDBInfo

public String getTables() {
	return szTables;
}

}
