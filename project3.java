import java.sql.* ;

class project2
{
    public static void main (String [] args) throws SQLException
    {
	// Unique table names.  Either the user supplies a unique identifier as a command line argument, or the program makes one up.
	String tableNameFake = "";
        int sqlCode=0;      // Variable to hold SQLCODE
        String sqlState="00000";  // Variable to hold SQLSTATE

    /* I want to Hard Code Tables
    */
	if ( args.length > 0 ){
	    tableNameFake += args [ 0 ] ;
	}
	else {
	    tableNameFake += "example3.tbl";
	}

	// Register the driver.  You must register the driver before you can use it.
        try {
	    DriverManager.registerDriver ( new com.ibm.db2.jcc.DB2Driver() ) ;
	} catch (Exception cnfe){
	    System.out.println("Class not found");
        }

	// This is the url you must use for DB2.
	//Note: This url may not valid now !
	String url = "jdbc:db2://db2:50000/cs421";
	Connection con = DriverManager.getConnection (url,"cs421g09" ,"jeavinjos") ;
	Statement statement = con.createStatement ( ) ;

	/* Create all the Tradingpo.st Tables
		Using String Array
	*/
	// tables
	String[] tableName = new String[14];
	tableName[0] = "User";
	tableName[1] = "PaymentInfo";
	tableName[2] = "Transactions";
	tableName[3] = "ShoppingCart";
	tableName[4] = "Products";
	tableName[5] = "Brands";
	tableName[6] = "Categories";
	tableName[7] = "Have";
	tableName[8] = "Sells";
	tableName[9] = "Owns";
	tableName[10] = "Contains";
	tableName[11] = "BrandedBy";
	tableName[12] = "GroupedIn";
	tableName[13] = "MadeUpOf";
	// Create Statments
	String[] tables = new String[14];
	tables[0] = "User (user_id INTEGER NOT NULL PRIMARY KEY, username VARCHAR(25) NOT NULL, password VARCHAR(25) NOT NULL, email VARCHAR(25), address VARCHAR(50), city VARCHAR(25), postal_code VARCHAR(10), seller_rating FLOAT CHECK(seller_rating > 0.0 AND seller_rating <= 10.0), since DATE)";
	tables[1] = "PaymentInfo (pay_id INTEGER NOT NULL PRIMARY KEY, first_name VARCHAR(25), last_name VARCHAR(25), billing_address VARCHAR(50), city VARCHAR(25), postal_code VARCHAR(10), credit_card_numb VARCHAR(20), bank_name VARCHAR(20))";
	tables[2] = "Transactions (t_id INTEGER NOT NULL PRIMARY KEY, t_date DATE, type VARCHAR(10), buyer_id  INTEGER NOT NULL, seller_id INTEGER NOT NULL )";
	tables[3] = "ShoppingCart (cart_id INTEGER NOT NULL PRIMARY KEY, product_id INTEGER, item_count INTEGER, total INTEGER)";
	tables[4] = "Products (p_id INTEGER NOT NULL PRIMARY KEY, user_id INTEGER, FOREIGN KEY (user_id) REFERENCES User (user_id), p_name VARCHAR(50), price FLOAT, stock INTEGER, model_number VARCHAR(25), rating FLOAT CHECK(rating > 0.0 AND rating <= 10.0), sold_flag VARCHAR(5))";
	tables[5] = "Brands (brand_id INTEGER NOT NULL PRIMARY KEY, brand_name VARCHAR(25))";
	tables[6] = "Categories (cat_id INTEGER NOT NULL PRIMARY KEY, cat_name VARCHAR(25))";
	tables[7] = "Have (pay_id INTEGER, user_id INTEGER, FOREIGN KEY (pay_id) REFERENCES PaymentInfo(pay_id), FOREIGN KEY (user_id) REFERENCES User(user_id)) ";
	tables[8] = "Sells (user_id INTEGER, p_id INTEGER, FOREIGN KEY (user_id) REFERENCES User(user_id), FOREIGN KEY (p_id) REFERENCES Products(p_id), quantity INTEGER, price FLOAT)";
	tables[9] = "Owns (user_id INTEGER, cart_id INTEGER, FOREIGN KEY (user_id) REFERENCES User(user_id), FOREIGN KEY (cart_id) REFERENCES ShoppingCart(cart_id))";
	tables[10] = "Contains (cart_id INTEGER, p_id INTEGER, FOREIGN KEY (cart_id) REFERENCES ShoppingCart(cart_id), FOREIGN KEY (p_id) REFERENCES Products(p_id))";
	tables[11] = "BrandedBy (p_id INTEGER, brand_id INTEGER, FOREIGN KEY (p_id) REFERENCES Products(p_id), FOREIGN KEY (brand_id) REFERENCES Brands(brand_id))";
	tables[12] = "GroupedIn (p_id INTEGER, cat_id INTEGER, FOREIGN KEY (p_id) REFERENCES Products(p_id), FOREIGN KEY (cat_id) REFERENCES Categories(cat_id))";
	tables[13] = "MadeUpOf (t_id INTEGER, p_id INTEGER, FOREIGN KEY (t_id) REFERENCES Transactions(t_id), FOREIGN KEY (p_id) REFERENCES Products(p_id), quantity INTEGER, rating FLOAT CHECK(rating > 0.0 AND rating <= 10.0))";
	try {
		String[] createSQL = new String[14];
		// Creating the tables
	    for(int i = 0; i< createSQL.length; i++){
	    	createSQL[i] = "CREATE TABLE " + tables[i]; 
	    	System.out.println (createSQL[i] ) ;
	   		statement.executeUpdate (createSQL[i] ) ;
	   		System.out.println ("DONE");
	    }
	    
	}catch (SQLException e)
            {
                sqlCode = e.getErrorCode(); // Get SQLCODE
                sqlState = e.getSQLState(); // Get SQLSTATE
                
                // Your code to handle errors comes here;
                // something more meaningful than a print would be good
                System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
            }

	// Inserting Data into the table
    /* 
    */
	try {
	    String insertSQL = "INSERT INTO User VALUES ( 1 , \'samsonIsCool\', \'password123\', \'samson@potato.net\', \'1234 Ste. Street\', \'Montreal\', \'A1B2C3\', 7.3, \'2015-03-6\') " ;
	    System.out.println ( insertSQL ) ;
	    statement.executeUpdate ( insertSQL ) ;
	    System.out.println ( "DONE" ) ;

	    // insertSQL = "INSERT INTO " + tableName + " VALUES ( 2 , \'Vera\' ) " ;
	    // System.out.println ( insertSQL ) ;
	    // statement.executeUpdate ( insertSQL ) ;
	    // System.out.println ( "DONE" ) ;
	    // insertSQL = "INSERT INTO " + tableName + " VALUES ( 3 , \'Franca\' ) " ;
	    // System.out.println ( insertSQL ) ;
	    // statement.executeUpdate ( insertSQL ) ;
	    // System.out.println ( "DONE" ) ;

	} catch (SQLException e)
            {
                sqlCode = e.getErrorCode(); // Get SQLCODE
                sqlState = e.getSQLState(); // Get SQLSTATE
                
                // Your code to handle errors comes here;
                // something more meaningful than a print would be good
                System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
            }

	// Querying a table
	try {
	    String querySQL = "SELECT user_id, username, email FROM User WHERE username = \'samsonIsCool\'";
	    System.out.println (querySQL) ;
	    java.sql.ResultSet rs = statement.executeQuery ( querySQL ) ;
	    while ( rs.next ( ) ) {
		int id = rs.getInt (1) ;
		String username = rs.getString (2);
		String email = rs.getString (3);
		System.out.println ("id:  " + id + " username: " + username + " email: " + email);
	    }
	    System.out.println ("DONE");
	} catch (SQLException e)
	    {
		sqlCode = e.getErrorCode(); // Get SQLCODE
		sqlState = e.getSQLState(); // Get SQLSTATE
                
		// Your code to handle errors comes here;
		// something more meaningful than a print would be good
		System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
	    }

	//Updating a table
    try {
	    String updateSQL = "UPDATE User SET username = \'samsonIsAlright\' WHERE user_id = 1";
	    System.out.println(updateSQL);
	    statement.executeUpdate(updateSQL);
	    System.out.println("DONE");

	    // Dropping a tables
	    for(int d = 0; d<tableName.length; d++){
		    String dropSQL = "DROP TABLE " + tableName[d];
		    System.out.println ( dropSQL ) ;
		    statement.executeUpdate ( dropSQL ) ;
		    System.out.println ("DONE");
		}
	} catch (SQLException e)
	    {
		sqlCode = e.getErrorCode(); // Get SQLCODE
		sqlState = e.getSQLState(); // Get SQLSTATE
                
		// Your code to handle errors comes here;
		// something more meaningful than a print would be good
		System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
	    }


	// Finally but importantly close the statement and connection
	statement.close ( ) ;
	con.close ( ) ;
    }
}
