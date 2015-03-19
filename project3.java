import java.sql.* ;
import java.util.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

class project3
{
    public static void main (String [] args) throws SQLException
    {
	// Unique table names.  Either the user supplies a unique identifier as a command line argument, or the program makes one up.
	String tableNameFake = "";
        int sqlCode=0;      // Variable to hold SQLCODE
        String sqlState="00000";  // Variable to hold SQLSTATE
    
   

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
	tables[0] = "User (user_id INTEGER NOT NULL PRIMARY KEY, username VARCHAR(25) NOT NULL, password VARCHAR(25) NOT NULL, email VARCHAR(25), address VARCHAR(50), city VARCHAR(25), postal_code VARCHAR(10), seller_rating DECIMAL(3,1) CHECK(seller_rating > 0.0 AND seller_rating <= 10.0), since DATE)";
	tables[1] = "PaymentInfo (pay_id INTEGER NOT NULL PRIMARY KEY, first_name VARCHAR(25), last_name VARCHAR(25), billing_address VARCHAR(50), city VARCHAR(25), postal_code VARCHAR(10), credit_card_numb VARCHAR(20), bank_name VARCHAR(25))";
	tables[2] = "Transactions (t_id INTEGER NOT NULL PRIMARY KEY, t_date DATE, buyer_id  INTEGER NOT NULL, seller_id INTEGER NOT NULL )";
	tables[3] = "ShoppingCart (cart_id INTEGER NOT NULL PRIMARY KEY, item_count INTEGER, total DECIMAL(5,2))";
	tables[4] = "Products (p_id INTEGER NOT NULL PRIMARY KEY, p_name VARCHAR(50), price DECIMAL(5,2), model_number VARCHAR(25), rating DECIMAL(3,1) CHECK(rating > 0.0 AND rating <= 10.0), sold_flag VARCHAR(5))";
	tables[5] = "Brands (brand_id INTEGER NOT NULL PRIMARY KEY, brand_name VARCHAR(25))";
	tables[6] = "Categories (cat_id INTEGER NOT NULL PRIMARY KEY, cat_name VARCHAR(25))";
	tables[7] = "Have (pay_id INTEGER, user_id INTEGER, FOREIGN KEY (pay_id) REFERENCES PaymentInfo(pay_id), FOREIGN KEY (user_id) REFERENCES User(user_id)) ";
	tables[8] = "Sells (user_id INTEGER, p_id INTEGER, FOREIGN KEY (user_id) REFERENCES User(user_id), FOREIGN KEY (p_id) REFERENCES Products(p_id), quantity INTEGER, price DECIMAL(5,2))";
	tables[9] = "Owns (cart_id INTEGER, user_id INTEGER, FOREIGN KEY (user_id) REFERENCES User(user_id), FOREIGN KEY (cart_id) REFERENCES ShoppingCart(cart_id))";
	tables[10] = "Contains (cart_id INTEGER, p_id INTEGER, quantity INTEGER, FOREIGN KEY (cart_id) REFERENCES ShoppingCart(cart_id), FOREIGN KEY (p_id) REFERENCES Products(p_id))";
	tables[11] = "BrandedBy (p_id INTEGER, brand_id INTEGER, FOREIGN KEY (p_id) REFERENCES Products(p_id), FOREIGN KEY (brand_id) REFERENCES Brands(brand_id))";
	tables[12] = "GroupedIn (p_id INTEGER, cat_id INTEGER, FOREIGN KEY (p_id) REFERENCES Products(p_id), FOREIGN KEY (cat_id) REFERENCES Categories(cat_id))";
	tables[13] = "MadeUpOf (t_id INTEGER, p_id INTEGER, FOREIGN KEY (t_id) REFERENCES Transactions(t_id), FOREIGN KEY (p_id) REFERENCES Products(p_id), quantity INTEGER, rating DECIMAL(3,1) CHECK(rating > 0.0 AND rating <= 10.0))";

	try {
		String[] createSQL = new String[14];
		// Creating the tables
	    for(int c = 0; c< createSQL.length; c++){
	    	createSQL[c] = "CREATE TABLE " + tables[c]; 
	   		statement.executeUpdate (createSQL[c] ) ;	
	    }
	    System.out.println ("Tables Created") ;
	    
	}catch (SQLException e)
            {
                sqlCode = e.getErrorCode(); // Get SQLCODE
                sqlState = e.getSQLState(); // Get SQLSTATE
                
                // Your code to handle errors comes here;
                // something more meaningful than a print would be good
                System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
            }

	// Inserting Data into the table
    /* Insert Statements for starting data
    */
    String[] insertSQL = new String[14];
    insertSQL[0] = "INSERT INTO User VALUES (1001, 'samsonIsCool', 'password123', 'samson@potato.net', '1234 ste. Street', 'Montreal', 'A1B2C3', 7.3, '2015-02-06'), (1002, 'BestSellerEver', 'BestPasswordEver', 'BestEmail@Ever.com', '4444 Awesome', 'Ottawa', 'H2B2X2', 10.0, '2015-02-06'), (1003, 'ShadySeller', 'superShady1', 'ShadyGuy@secret.com', '453  Scary Street', 'Montreal', 'H6H6H6', 1.2, '2015-02-06'), (1004, 'TheHarryMeister', 'secret123', 'Harry@mail.com', '888  Somewhere Street', 'Montreal', 'H3W2V2', 8.4, '2015-02-07'), (1005, 'JacquesAttack', '321drowssap', 'Evan@mail.net', '3456 Somewhere-Else Street', 'Montreal', 'H3V1V1', 8.2, '2015-02-07')";
	insertSQL[1] = "INSERT INTO PaymentInfo VALUES (2001, 'Samson', 'Stannus', '1234 ste. Street', 'Montreal', 'A1B2C3', '1234-5678-9101-1121', 'Royal Bank of US'), (2002, 'Tammy', 'Burger', '4444 Awesome', 'Ottawa', 'H2B2X2', '4510-6217-1293-1234', 'CIBC'), (2003, 'Chad', 'Smith', '453 Scary Street', 'Montreal', 'H6H6H6', '8888-8888-8888-8888', 'Loan Shark Emporium'), (2004, 'Harry', 'Schneidman', '888  Somewhere Street', 'Montreal', 'H3W2V2', '5634-3241-5346-8756', 'RBC'), (2005, 'Evan', 'Jacques', '3456 Somewhere-Else Street', 'Montreal', 'H3V1V1', '4231-4321-4231-4231', 'CIBC')";
	insertSQL[2] = "INSERT INTO Transactions VALUES (30001, '2015-02-07', 1001, 1004), (30002, '2015-02-10', 1005, 1004), (30003, '2015-02-10', 1004, 1002), (30004, '2015-02-10', 1003, 1002), (30005, '2015-02-11', 1001, 1005), (30006, '2015-02-12', 1002, 1005), (30007, '2015-02-12', 1003, 1001), (30008, '2015-02-25', 1001, 1005), (30009, '2015-02-25', 1002, 1005), (30010, '2015-02-25', 1003, 1005)";
	insertSQL[3] = "INSERT INTO ShoppingCart VALUES (5001, 2, 719.00), (5002, 1, 200.00), (5003, 2, 400.50), (5004, 1, 200.50), (5005, 2, 80.60)";
	insertSQL[4] = "INSERT INTO Products VALUES (10001, 'Iphone 5', 200.50, 'MA8515B2', 9.0, 'FALSE'), (10002, 'Bauer Hockey Stick', 50.35, '', 7.5, 'TRUE'), (10003, 'Yoga Mat', 73.00, 'L111LA9', 10.0, 'FALSE'), (10004, '40inch Flat Screen TV', 350.75, 'SAM123SA213', 10.0, 'TRUE'), (10005, 'Basketball', 15.60, 'TTB233', 10.0, 'FALSE'), (10006, 'Soccer Cleat', 150.00, 'NK778Y', 9.5, 'TRUE'), (10007, 'Bootleg Iphone 6plus', 500.00, '', 1.0, 'FALSE'), (10008, 'Drum Set', 250.00, 'PRL8008', 8.5, 'TRUE'), (10009, 'Bootleg Call Of Duty', 65.00, '', 1.9, 'FALSE'), (10010, 'Head Phones', 300.00, 'BS821OE', 9.0, 'TRUE'), (10011, 'Computer Key Board', 200.00, 'LGI1234', 8.0, 'FALSE'), (10012, 'Computer Mouse', 60.00, 'LGI2144', 8.0, 'TRUE')";
	insertSQL[5] = "INSERT INTO Brands VALUES (8011, 'Nike'), (8002, 'Samsung'), (8003, 'Apple'), (8014, 'Puma'), (8015, 'Lululemon'), (8016, 'Steve Madden'), (8017, 'Adidas'), (8008, 'Microsoft'), (8009, 'Bose'), (8010, 'Roots'), (8001, 'Logitech'), (8020, 'Bauer'), (8021, 'Reebok'), (8030, 'Fender'), (8031, 'Pearl'), (8004, 'Sony')";
	insertSQL[6] = "INSERT INTO Categories VALUES (4000, 'Clothing'), (4001, 'Electronics'), (4002, 'Games and Toys'), (4003, 'Sports Equipment'), (4004, 'Music and Instruments')";
	insertSQL[7] = "INSERT INTO Have VALUES (2001, 1001), (2002, 1002), (2003, 1003), (2004, 1004), (2005, 1005)";
	insertSQL[8] = "INSERT INTO Sells VALUES (1001, 10001, 2, 200.50), (1001, 10002, 1, 50.35), (1002, 10003, 5, 73.00), (1002, 10004, 2, 350.75), (1002, 10005, 1, 15.60), (1005, 10006, 3, 150.00), (1003, 10007, 13, 500.00), (1001, 10008, 1, 250.00), (1003, 10009, 30, 65.00), (1004, 10010, 1, 300.00), (1004, 10011, 2, 200.00), (1005, 10012, 3, 60.00)";
	insertSQL[9] = "INSERT INTO Owns VALUES (5001, 1001), (5002, 1002), (5003, 1003), (5004, 1004), (5005, 1005)";
	insertSQL[10] = "INSERT INTO Contains VALUES (5001, 10003, 3), (5001, 10007, 1), (5002, 10011, 1), (5003, 10001, 1), (5003, 10011, 1), (5004, 10001, 1), (5005, 10005, 1), (5005, 10009, 1)";
	insertSQL[11] = "INSERT INTO BrandedBy VALUES (10001, 8003), (10002, 8020), (10003, 8015), (10004, 8002), (10005, 8011), (10006, 8011), (10007, 8003), (10008, 8031), (10009, 8004), (10009, 8008), (10010, 8009), (10011, 8001), (10012, 8001)";
	insertSQL[12] = "INSERT INTO GroupedIn VALUES (10001, 4001), (10002, 4003), (10003, 4003), (10004, 4001), (10005, 4003), (10006, 4000), (10006, 4003), (10007, 4001), (10008, 4004), (10009, 4001), (10009, 4002), (10010, 4001), (10010, 4004), (10011, 4001), (10012, 4002)";
	insertSQL[13] = "INSERT INTO MadeUpOf VALUES (30001, 10002, 1, 7.3), (30002, 10010, 1, 8.4), (30003, 10004, 1, 10.0), (30004, 10004, 1, 10.0), (30005, 10006, 2, 8.2), (30006, 10006, 1, 8.2), (30007, 10008, 1, 7.3), (30008, 10012, 1, 8.2), (30009, 10012, 1, 8.2), (30010, 10012, 1, 8.2)";



	try {
	    for(int i = 0; i< insertSQL.length; i++){
	   		statement.executeUpdate (insertSQL[i] ) ;
	    }
	    System.out.println ( "Original Inserts DONE" ) ;


	} catch (SQLException e)
            {
                sqlCode = e.getErrorCode(); // Get SQLCODE
                sqlState = e.getSQLState(); // Get SQLSTATE
                
                // Your code to handle errors comes here;
                // something more meaningful than a print would be good
                System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
            }
    Scanner scanner=new Scanner(System.in);
    while (true) {
    	System.out.println("------------------------------------------------------------------------------");
        System.out.println("Please Select one of the following commands or type 'q' or 'Q' to quit [12qQ]:");
        System.out.println("------------------------------------------------------------------------------");
        System.out.println("1. Sign In");
        System.out.println("2. Make new Account");

        String response1 = scanner.nextLine();
        if(response1.equals("q") || response1.equals("Q")){
            break;
        }
        if(response1.equals("1")){
        	System.out.println("--------");
        	System.out.println("Sign In");
        	System.out.println("--------");
        	System.out.println("Enter Username (case sensitive)");
        	String username = scanner.nextLine();
        	System.out.println("Enter Password (case sensitive)");
        	String password = scanner.nextLine();
        	// Querying a table
				try {
					System.out.println(username);
				    String passSQL = "SELECT password FROM User WHERE username = \'" + username + "\'";
				    System.out.println (passSQL) ;
				    java.sql.ResultSet rs1 = statement.executeQuery ( passSQL ) ;
				    
				    while ( rs1.next ( ) ) {
				    	String pass = rs1.getString (1) ;
				    
					    System.out.println(pass);
		
					    
					    if(pass.equals(password)){
					    	while(true){
						    	System.out.println("----------------------------------------------------------------------------------");
						    	System.out.println("Please Select one of the following commands or type 'q' or 'Q' to go back [123qQ]:");
		        				System.out.println("----------------------------------------------------------------------------------");
		        				System.out.println("1. Search for an Item");
		        				System.out.println("2. Checkout Shopping Cart");
		        				System.out.println("3. Edit Account Info");

		        				String response2 = scanner.nextLine();
		        				if(response2.equals("q") || response2.equals("Q")) {
		        					break;
		        				}
		        				if(response2.equals("1")){
		        					System.out.println("--------------------------------------------------------------------------------");
						        	System.out.println("Search by selecting one of the following or type 'q' or 'Q' to go back [12345qQ]:");
						        	System.out.println("--------------------------------------------------------------------------------");
						        	System.out.println("1. List all Items for sale");
						        	System.out.println("2. Search by Item Name");
						        	System.out.println("3. Search by Category");
						        	System.out.println("4. Search by Brand");
						        	System.out.println("5. Search by User");
						        	System.out.println("6. Search by Price");
		        					

						        	String response3 = scanner.nextLine();
						        	if(response3.equals("q") || response2.equals("Q")) {
		        						continue;
		        					}
		        					if(response3.equals("1")){
		        						String querySQL = "SELECT p_name, price FROM Products WHERE sold_flag = \'FALSE\' ";
		        						System.out.println ("--------------------") ;
									    System.out.println ("Listing All Products") ;
									    System.out.println ("--------------------") ;
									    java.sql.ResultSet rs2 = statement.executeQuery ( querySQL ) ;	
									    int p = 1;			   
									    while ( rs2.next ( ) ) {
											String p_name = rs2.getString (1) ;
											int price = rs2.getInt (2) ;
											System.out.println( p+ ". " + p_name + ": $" + price);
											p++;
											/* 
												TODO: make options to go back to previous menu

											*/
		
									    }

			        				}
			        				if(response3.equals("2")){
			        					
			        				}
			        				if(response3.equals("3")){
		        					
			        				}
			        				if(response3.equals("4")){
			        					
			        				}
			        				if(response3.equals("5")){
		        					
			        				}
			        				if(response3.equals("6")){
			        					
			        				}
		        				}
		        				if(response2.equals("2")){
		        					
		        				}
		        				if(response2.equals("3")){
		        					
		        				}
		        				/*


		        				TODO


		        				*/

	        				}
	        				continue;
					    }else{
					    	System.out.println ("Username and/or Password don't match anything in database");
					    	continue;
						}
					}
				} catch (SQLException e)
				    {
					sqlCode = e.getErrorCode(); // Get SQLCODE
					sqlState = e.getSQLState(); // Get SQLSTATE
			                
					// Your code to handle errors comes here;
					// something more meaningful than a print would be good
					System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
					System.out.println ("Username and/or Password don't match anything in database");
				    	continue;
				    }


        }
        if(response1.equals("2")){
        	System.out.println("---------------");
        	System.out.println("Make an Account");
        	System.out.println("---------------");
        	System.out.println("This will take a series of steps (Since this is a fictitious service, don't enter real information)");
        	System.out.println("Enter a Username:");
        	String username = scanner.nextLine();
        	System.out.println("Enter a Password:");
        	String password = scanner.nextLine();
        	System.out.println("Enter your Email:");
        	String email = scanner.nextLine();
        	System.out.println("Enter your Address:");
        	String address = scanner.nextLine();
        	System.out.println("Enter the city you live in:");
        	String city = scanner.nextLine();
        	System.out.println("Enter you Postal Code:");
        	String p_code = scanner.nextLine();

        	//Get the current date
        	DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String since = dateformat.format(date);

        	
        	try {
        		/* 

					TODO
					Need to think of a way to increment userID... for now it's a fixed number....


        		*/
				System.out.println(since);
        		int u_id = 1006;
			    String newAccount = "INSERT INTO User VALUES (" + u_id+ ",\'" + username + "\', \'" + password + "\' , \'" + email + "\', \'" + address + "\', \'" + city + "\', \'" + p_code + "\', 5.0 ,\'" + since + "\')";
			   	statement.executeUpdate ( newAccount ) ;
			    System.out.println("Awesome! Your Account is almost set up.");


			} catch (SQLException e)
		    {
		        sqlCode = e.getErrorCode(); // Get SQLCODE
		        sqlState = e.getSQLState(); // Get SQLSTATE
		        
		        // Your code to handle errors comes here;
		        // something more meaningful than a print would be good
		        System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
		        System.out.println("There was an error, quitting Tradingpo.st");
		        break;
		    }
		    
        	System.out.println("-----------------");
        	System.out.println("Enter PaymentInfo");
        	System.out.println("-----------------");
        	System.out.println("What's your First Name?:");
        	String f_name = scanner.nextLine();
        	System.out.println("What's your Last Name?:");
        	String l_name = scanner.nextLine();
        	System.out.println("What's your Credit Card Number?:");
        	String c_numb = scanner.nextLine();
        	System.out.println("What is your Bank Name?:");
        	String b_name = scanner.nextLine();
        	try {
        		/* 

					TODO
					Need to think of a way to increment payID... for now it's a fixed number....

        		*/
				int p_id = 2006;
			    String newAccount = "INSERT INTO PaymentInfo VALUES (" + p_id + ", \'" + f_name + "\', \'" + l_name + "\', \'" + address + "\', \'" + city + "\',\'" + p_code +  "\',\'" + c_numb + "\', \'" + b_name + "\')";
			   	statement.executeUpdate ( newAccount ) ;
			    System.out.println("Awesome! Your Account is almost set up.");
			    System.out.println("Your new account is totally set up, we'll send back to the first prompt to sign in again!");
			    continue;
			} catch (SQLException e)
		    {
		        sqlCode = e.getErrorCode(); // Get SQLCODE
		        sqlState = e.getSQLState(); // Get SQLSTATE
		        
		        // Your code to handle errors comes here;
		        // something more meaningful than a print would be good
		        System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
		        System.out.println("There was an error, quitting Tradingpo.st");
		        break;
		    }



        }
    }
	// Querying a table
	// try {
	    // String querySQL = "SELECT user_id, username, email FROM User WHERE username = \'samsonIsCool\'";
	//     System.out.println (querySQL) ;
	//     java.sql.ResultSet rs = statement.executeQuery ( querySQL ) ;
	//     while ( rs.next ( ) ) {
	// 	int id = rs.getInt (1) ;
	// 	String username = rs.getString (2);
	// 	String email = rs.getString (3);
	// 	System.out.println ("id:  " + id + " username: " + username + " email: " + email);
	//     }
	//     System.out.println ("DONE");
	// } catch (SQLException e)
	//     {
	// 	sqlCode = e.getErrorCode(); // Get SQLCODE
	// 	sqlState = e.getSQLState(); // Get SQLSTATE
                
	// 	// Your code to handle errors comes here;
	// 	// something more meaningful than a print would be good
	// 	System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
	//     }

	//Updating a table
    try {
	    // String updateSQL = "UPDATE User SET username = \'samsonIsAlright\' WHERE user_id = 1";
	    // System.out.println(updateSQL);
	    // statement.executeUpdate(updateSQL);
	    // System.out.println("DONE");

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
