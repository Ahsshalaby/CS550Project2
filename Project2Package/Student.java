package Project2Package;


import java.util.Scanner; 
import java.io.*;
//oracle.sql imports did not want to work on my version of java no matter what I did and how I configured build paths after trying for hours
// using different imports from the net that are supposedly more modern
import java.sql.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Student{
    static Connection con;
    static Statement stmt;

    
    
    public static void main(String argv[])
    {
    	Scanner myScanner = new Scanner(System.in);
    	System.out.println(System.getProperty("java.class.path"));
    	System.out.println("hello World:");
	    connectToDatabase(myScanner);
	    try {
			executePaperSQL(myScanner);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    myScanner.close();
	   
	    
	    
    }

    
    // Part 1:  Write a Java program using JDBC to connect to your Oracle account
    public static void connectToDatabase(Scanner myScanner)
    {
    	

          
	String driverPrefixURL="jdbc:oracle:thin:@";
	String jdbc_url="artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";

	   // IMPORTANT: DO NOT PUT YOUR LOGIN INFORMATION HERE. INSTEAD, PROMPT USER FOR HIS/HER LOGIN/PASSWD
	  // prompting user for user and pass. make sure your connected to vpn
      System.out.println("Enter your user name for Oracle: ");
      String username = myScanner.nextLine();
      System.out.println("Enter your password for Oracle: ");
      String password = myScanner.nextLine();

      
     

	
        try{
        	  // Register Oracle driver. alternate method using java sql
            Class.forName("oracle.jdbc.OracleDriver");
            System.out.println("Driver loaded successfully.");

            // Connect to database
            con = DriverManager.getConnection(driverPrefixURL + jdbc_url, username, password);
            stmt = con.createStatement();
        } catch (Exception e) {
            System.out.println("Failed to load JDBC/ODBC driver.");
            return;
        }

       try{
            System.out.println(driverPrefixURL+jdbc_url);
            con=DriverManager.getConnection(driverPrefixURL+jdbc_url, username, password);
            DatabaseMetaData dbmd=con.getMetaData();
            stmt=con.createStatement();

            System.out.println("Connected.");

            if(dbmd==null){
                System.out.println("No database meta data");
            }
            else {
                System.out.println("Database Product Name: "+dbmd.getDatabaseProductName());
                System.out.println("Database Product Version: "+dbmd.getDatabaseProductVersion());
                System.out.println("Database Driver Name: "+dbmd.getDriverName());
                System.out.println("Database Driver Version: "+dbmd.getDriverVersion());
            }
        }catch( Exception e) {e.printStackTrace();}
       
       

       
    }// End of connectToDatabase()
    
    //Part2: execute paper.sql script file provided with this project to create and insert data into your database
    public static void executePaperSQL(Scanner myScanner) throws SQLException {
    	
    	//prompt the user for the location of paper.sql script file when you create
    	System.out.println("Enter the path for your paper.SQL File: ");
    	String filePath = myScanner.nextLine();
    	
    	//and insert data into the database from the script file
    	
/* EXAMPLE: how to make a table in JDBC from Oracle Doc
 *   public void createTable() throws SQLException {
    String createString =
      "create table COFFEES " + "(COF_NAME varchar(32) NOT NULL, " +
      "SUP_ID int NOT NULL, " + "PRICE numeric(10,2) NOT NULL, " +
      "SALES integer NOT NULL, " + "TOTAL integer NOT NULL, " +
      "PRIMARY KEY (COF_NAME), " +
      "FOREIGN KEY (SUP_ID) REFERENCES SUPPLIERS (SUP_ID))";
    try (Statement stmt = con.createStatement()) {
      stmt.executeUpdate(createString);
    } catch (SQLException e) {
      JDBCTutorialUtilities.printSQLException(e);
    }
  }
   */
    	
    	/*
        String clearTable =
	    		"DROP TABLE AUTHORS CASCADE CONSTRAINTS";
	    try (Statement stmt = con.createStatement()) {
	      stmt.executeUpdate(clearTable);
	    } catch (SQLException e) {
	      e.printStackTrace();
	    }
	  	
	    String clearTable2 =
	    		"DROP TABLE PUBLICATIONS";
	    try (Statement stmt = con.createStatement()) {
	      stmt.executeUpdate(clearTable2);
	    } catch (SQLException e) {
	      e.printStackTrace();
	    }
    	   
    		    String createTable =
    		    		"CREATE TABLE PUBLICATIONS "+
    		    		        "(PUBLICATIONID VARCHAR2(4), "+
    		    			"YEAR CHAR(4), "+
    		    			"TYPE VARCHAR2(5), "+
    		    			"TITLE VARCHAR2(100), "+
    		    			"SUMMARY VARCHAR2(2000), "+
    		    			"PRIMARY KEY (PUBLICATIONID))";
    		    try (Statement stmt = con.createStatement()) {
    		      stmt.executeUpdate(createTable);
    		    } catch (SQLException e) {
    		      e.printStackTrace();
    		    }
    		  	
    	
    		    String createTable2 =
    		    		"CREATE TABLE AUTHORS"+
    		    		        "(PUBLICATIONID VARCHAR2(4), " +
    		    			"AUTHOR VARCHAR2(50), "+
    		    			"PRIMARY KEY (PUBLICATIONID,AUTHOR), " +
    		    			"FOREIGN KEY (PUBLICATIONID) REFERENCES PUBLICATIONS(PUBLICATIONID))"; 
    		    		
    		    try (Statement stmt = con.createStatement()) {
    		      stmt.executeUpdate(createTable2);
    		    } catch (SQLException e) {
    		      e.printStackTrace();
    		    }
    		    
    		    
    		    
    		  */  
  
  /* EXAMPLE: populating table with JDBC from oracle Doc
   *  public void populateTable() throws SQLException {
    try (Statement stmt = con.createStatement()) {
      stmt.executeUpdate("insert into COFFEES " +
                         "values('Colombian', 00101, 7.99, 0, 0)");
      stmt.executeUpdate("insert into COFFEES " +
                         "values('French_Roast', 00049, 8.99, 0, 0)");
      stmt.executeUpdate("insert into COFFEES " +
                         "values('Espresso', 00150, 9.99, 0, 0)");
      stmt.executeUpdate("insert into COFFEES " +
                         "values('Colombian_Decaf', 00101, 8.99, 0, 0)");
      stmt.executeUpdate("insert into COFFEES " +
                         "values('French_Roast_Decaf', 00049, 9.99, 0, 0)");
    } catch (SQLException e) {
      JDBCTutorialUtilities.printSQLException(e);
    }
  }
   */
    	
    	// rather than copy and paste, more appropriate to have java read the file, and stop each statement at the semicolons
    	
    	try(BufferedReader br = new BufferedReader (new FileReader(filePath))) {
    	System.out.println("Generating tables...");
    	String tuple; // each line of file between semi colons

    	StringBuilder sb = new StringBuilder(); // mutable string. 
    	
    	Statement stmt = con.createStatement();
    	
    	 while ((tuple = br.readLine()) != null) {
             tuple = tuple.trim(); // remove whitespace
            // if(tuple.isEmpty() || lineNumber < 18) {
            	// lineNumber++ ;    continue;
             //}
      sb.append(tuple);

    	
      if (tuple.endsWith(";")) {
          String sql = sb.toString().replace(";", ""); // Remove the semicolon
          try {
              stmt.execute(sql);
              //System.out.println("Executed: " + sql); // lets us confirm that the things were added
          } catch (SQLException e) {
              System.out.println("Error executing SQL command: " + sql);
              e.printStackTrace();
          }

          // Reset the SQL command builder for the next command
          sb.setLength(0);
      }
      
  } // ends while loop string builder to create next string
    	 
  System.out.println("SQL script executed successfully."); // at end of loop to let you know we are done
  
  runProgram(myScanner);

    	} catch (FileNotFoundException e) { // catch for buffered reader 
  System.out.println("File not found at the specified path: " + filePath);
  e.printStackTrace();
} catch (IOException e) {
  System.out.println("Error reading the SQL script file.");
  e.printStackTrace();
} finally {
  if (con != null && !con.isClosed()) {
      con.close();
      System.out.println("Database connection closed."); // close connection no matter what
  }
}

    }// end of executePaperSQL
    
	private static void runProgram(Scanner myScanner) throws SQLException
	{
		int choice = 0;
		do
		{
			choice = printMenu(myScanner);
			switch(choice)
			{
				case 1:
					viewTableContents(myScanner);
					break;
				case 2:
					searchByPublicationID(myScanner);
					break;
				case 3:
					searchByOneOrMoreAttributes(myScanner);
					break;
			}
		}while(choice != 4);
		
	}//end of runProgram
	
	private static int printMenu(Scanner myScanner)
	{
		int choice;
		
		do
		{
			System.out.println("Enter the number of the option below:");
			System.out.println("1. View table contents");
			System.out.println("2. Search by publicationID");
			System.out.println("3. Search by one or more attributes");
			System.out.println("4. exit");
			System.out.println();
			choice = myScanner.nextInt();
			myScanner.nextLine();
			if(choice < 1 || choice > 4)
				System.out.println("Invalid choice of " + choice);
		}while(choice < 1 || choice > 4);
		
		return choice;
	}//end printMenu
	
	private static void viewTableContents(Scanner myScanner) throws SQLException
	{
		String publications;
		String authors;
		
		do
		{
			System.out.print("Display all Publication tables? Type Yes or No: ");
			publications = myScanner.next();
			myScanner.nextLine();
			if(!typedYesOrNo(publications))
				System.out.println("Invalid input of " + publications);
		}while(!typedYesOrNo(publications));
		
		do
		{
			System.out.print("Display all Author tables? Type Yes or No: ");
			authors = myScanner.next();
			myScanner.nextLine();
			if(!typedYesOrNo(authors))
				System.out.println("Invalid input of " + publications);
		}while(!typedYesOrNo(authors));
		
		if(typedYes(publications))
		{
			try
			{
				String allPublications = "SELECT PublicationID, Year, Type, Title, Summary FROM Publications";
				PreparedStatement pstmt = con.prepareStatement(allPublications);
				ResultSet rs = pstmt.executeQuery(allPublications);
				
				while(rs.next())
				{
                    System.out.println("PublicationID: " + rs.getString("PublicationID"));
                    System.out.println("Year: " + rs.getInt("year"));
                    System.out.println("Type: " + rs.getString("type"));
                    System.out.println("Title: " + rs.getString("title"));
                    System.out.println("Summary: " + rs.getString("Summary"));	
                    System.out.println();
				}
			}
			catch (SQLException e) 
			{
  		      e.printStackTrace();
  		    }
		}
		
		if(typedYes(authors))
		{
			try
			{
				String allAuthors = "Select Author, PublicationID from Authors";
				PreparedStatement pstmt = con.prepareStatement(allAuthors);
				ResultSet rs = pstmt.executeQuery(allAuthors);
				
				while(rs.next())
				{
					System.out.println("Publication ID: " + rs.getString("PublicationID"));
					System.out.println("Author: " + rs.getString("Author"));
					System.out.println();
				}
			}
			catch (SQLException e) 
			{
  		      e.printStackTrace();
  		    }
		}	
	}//end viewTableContents
	
	private static void searchByPublicationID(Scanner myScanner) throws SQLException
	{
		String publicationID;
	
		System.out.print("Enter the publication ID: ");
		publicationID = myScanner.next();
		myScanner.nextLine();
		
		try
		{
			String allAttributes = "Select PublicationID, year, type, title, Summary from Publications where PublicationID = " + publicationID;
				PreparedStatement pmstm = con.prepareStatement(allAttributes); // preparing the statement 
				ResultSet rs = pmstm.executeQuery(); // result set interface to hold values of query
				
			if(rs.next()) { // rs starts at 0, need to move it next. returns false at no more rows.
				//https://docs.oracle.com/javase/8/docs/api/java/sql/ResultSet.html
                    System.out.println("PublicationID: " + rs.getString("PublicationID"));
                    System.out.println("Year: " + rs.getInt("year"));
                    System.out.println("Type: " + rs.getString("type"));
                    System.out.println("Title: " + rs.getString("title"));
                    System.out.println("Summary: " + rs.getString("Summary"));	
			}
                    
             String authorCount = "Select Count(*) AS IntCount from Authors where PublicationID = " + publicationID;
             PreparedStatement pmstmAuthor = con.prepareStatement(authorCount);
             ResultSet rsAuthor = pmstmAuthor.executeQuery();
             
             	if (rsAuthor.next()) {
             		System.out.println("Number Authors: " + rsAuthor.getInt("IntCount")); // made column intcount
             	}
             	System.out.println();
             
             //rsAttributes = stmt.executeQuery(allAttributes);
			//stmt.executeQuery(authorCount);
		
		} // end all attributes
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

	        
	}//end searchByPublicationID
	
	private static void searchByOneOrMoreAttributes(Scanner myScanner) throws SQLException
	{
		String author;
		String title;
		String year;
		String type;
		String sortBy;
		
		String OutputID;
		String OutputAuthor;
		String OutputTitle;
		String OutputYear;
		String OutputType;
		String OutputSummary;
		
		
		System.out.println("Enter specifics Inputs you would like. To leave an attribute empty press Enter:");
		
		System.out.println("Author Name: ");
		author = myScanner.nextLine();
		System.out.println();
		
		System.out.println("Title: ");
		title = myScanner.nextLine();
		System.out.println();
		
		System.out.println("Year (2016 or 2017): ");
		year = myScanner.nextLine();
		System.out.println();
		
		System.out.println("Type (short or long): ");
		type = myScanner.nextLine();
		System.out.println();
		
		
		System.out.println("Output Publication ID? (yes/no)");
		OutputID = myScanner.nextLine();
		System.out.println("Output Author? (yes/no)");
		OutputAuthor = myScanner.nextLine();
		System.out.println("Output Title? (yes/no)");
		OutputTitle = myScanner.nextLine();
		System.out.println("Output Year? (yes/no)");
		OutputYear = myScanner.nextLine();
		System.out.println("Output Type? (yes/no)");
		OutputType = myScanner.nextLine();
		System.out.println("Output Summary? (yes/no)");
		OutputSummary = myScanner.nextLine();
		
		
		System.out.println("sort by:  (Author, Title, Year, Type, publicationID)" );
		sortBy = myScanner.nextLine();
		
		if (!sortBy.matches("Author|Title|Year|Type|PublicationID")) {
	        System.out.println("Invalid sortBy column. Defaulting to PublicationID.");
	        sortBy = "PublicationID";  // sort by pubID if they dont specify
	    }
		
		
		StringBuilder  SearchAttributes = new StringBuilder( "Select DISTINCT ");
		boolean isFirst = true;
		
		 if (typedYes(OutputID)) {
	            if (!isFirst) SearchAttributes.append(", ");
	            SearchAttributes.append("p.PublicationID");
	            isFirst = false;
	        }
	        if (typedYes(OutputAuthor)) {
	            if (!isFirst) SearchAttributes.append(", ");
	            SearchAttributes.append("a.Author");
	            isFirst = false;
	        }
	        if (typedYes(OutputTitle)) {
	            if (!isFirst) SearchAttributes.append(", ");
	            SearchAttributes.append("p.Title");
	            isFirst = false;
	        }
	        if (typedYes(OutputYear)) {
	            if (!isFirst) SearchAttributes.append(", ");
	            SearchAttributes.append("p.Year");
	            isFirst = false;
	        }
	        if (typedYes(OutputType)) {
	            if (!isFirst) SearchAttributes.append(", ");
	            SearchAttributes.append("p.Type");
	            isFirst = false;
	        }
	        if (typedYes(OutputSummary)) {
	            if (!isFirst) SearchAttributes.append(", ");
	            SearchAttributes.append("p.Summary");
	            isFirst = false;
	        }

	        // Handle the case where no attributes were selected
	        if (isFirst) {
	            System.out.println("No attributes selected. Defaulting to PublicationID.");
	            SearchAttributes.append("p.PublicationID");
	        }
				
	        SearchAttributes.append(" FROM Publications p JOIN authors a on p.publicationID = a.publicationID WHERE 1=1");
		
		
		
		
	    if (!author.isEmpty()) {
	        SearchAttributes.append(" AND a.Author = " + "'" + author + "'");
	    }
	    if (!title.isEmpty()) {
	    	SearchAttributes.append(" AND p.Title = " + "'" + title + "'");
	    }
	    if (!year.isEmpty()) {
	    	SearchAttributes.append(" AND p.Year =  " + year);
	    }
	    if (!type.isEmpty()) {
	    	SearchAttributes.append(" AND p.Type =  " + "'" + type + "'");
	    }
	    if (!sortBy.isEmpty()) {
	    	 if (SearchAttributes.indexOf(sortBy) == -1) { 
	    	        // sb returns -1 if sort by is not found in the Select index. so code does not break
	    	      System.out.print("Selected Sort By is not in Your Outputs, Default Sorting");
	    	    }
	    	 else
	    		 SearchAttributes.append(" ORDER BY  " + sortBy);
	    }
		
		
		PreparedStatement pmstm = con.prepareStatement(SearchAttributes.toString()); // preparing the statement 
		ResultSet rs = pmstm.executeQuery(); // result set interface to hold values of query
		
		
		while(rs.next()) { // dont just want next. need all of them, so using while
                
			 if (typedYes(OutputID)) {
				 System.out.println("PublicationID: " + rs.getString("PublicationID"));
		        }
		        if (typedYes(OutputAuthor)) {
		        	 System.out.println("Author: " + rs.getString("Author"));
		        }
		        if (typedYes(OutputTitle)) {
		            System.out.println("Title: " + rs.getString("title"));
		        }
		        if (typedYes(OutputYear)) {
		        	   System.out.println("Year: " + rs.getInt("Year"));
		        }
		        if (typedYes(OutputType)) {
		            System.out.println("Type: " + rs.getString("type"));
		        }
		        if (typedYes(OutputSummary)) {
		            System.out.println("Summary: " + rs.getString("Summary"));	
		        }

                System.out.println("___________________________________________");
                System.out.println();
		}
	}//end searchByOneOrMoreAttributes
	
	private static boolean typedYesOrNo(String input)
	{
		return typedYes(input) || typedNo(input);
	}//end typedYesOrNo
	
	private static boolean typedYes(String input)
	{
		return input.equalsIgnoreCase("Yes") || input.equalsIgnoreCase("Y");
	}//end typedYes
	
	private static boolean typedNo(String input)
	{
		return input.equalsIgnoreCase("No") || input.equalsIgnoreCase("N");
	}//end typedNo

    
}// End of class
