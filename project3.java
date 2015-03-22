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
		tables[0] = "User (user_id INTEGER NOT NULL PRIMARY KEY, username VARCHAR(25) NOT NULL, password VARCHAR(25) NOT NULL, email VARCHAR(25), address VARCHAR(50), city VARCHAR(25), postal_code VARCHAR(10), seller_rating DECIMAL(3,1) CHECK(seller_rating >= 0.0 AND seller_rating <= 10.0), since DATE)";
		tables[1] = "PaymentInfo (pay_id INTEGER NOT NULL PRIMARY KEY, first_name VARCHAR(25), last_name VARCHAR(25), billing_address VARCHAR(50), city VARCHAR(25), postal_code VARCHAR(10), credit_card_numb VARCHAR(20), bank_name VARCHAR(25))";
		tables[2] = "Transactions (t_id INTEGER NOT NULL PRIMARY KEY, t_date DATE, buyer_id  INTEGER NOT NULL, seller_id INTEGER NOT NULL )";
		tables[3] = "ShoppingCart (cart_id INTEGER NOT NULL PRIMARY KEY, item_count INTEGER, total DECIMAL(5,2))";
		tables[4] = "Products (p_id INTEGER NOT NULL PRIMARY KEY, p_name VARCHAR(50), price DECIMAL(5,2), model_number VARCHAR(25), rating DECIMAL(3,1) CHECK(rating >= 0.0 AND rating <= 10.0), sold_flag VARCHAR(5))";
		tables[5] = "Brands (brand_id INTEGER NOT NULL PRIMARY KEY, brand_name VARCHAR(25))";
		tables[6] = "Categories (cat_id INTEGER NOT NULL PRIMARY KEY, cat_name VARCHAR(25))";
		tables[7] = "Have (pay_id INTEGER, user_id INTEGER, FOREIGN KEY (pay_id) REFERENCES PaymentInfo(pay_id), FOREIGN KEY (user_id) REFERENCES User(user_id)) ";
		tables[8] = "Sells (user_id INTEGER, p_id INTEGER, FOREIGN KEY (user_id) REFERENCES User(user_id), FOREIGN KEY (p_id) REFERENCES Products(p_id), quantity INTEGER, price DECIMAL(5,2))";
		tables[9] = "Owns (cart_id INTEGER, user_id INTEGER, FOREIGN KEY (user_id) REFERENCES User(user_id), FOREIGN KEY (cart_id) REFERENCES ShoppingCart(cart_id))";
		tables[10] = "Contains (cart_id INTEGER, p_id INTEGER, quantity INTEGER, FOREIGN KEY (cart_id) REFERENCES ShoppingCart(cart_id), FOREIGN KEY (p_id) REFERENCES Products(p_id))";
		tables[11] = "BrandedBy (p_id INTEGER, brand_id INTEGER, FOREIGN KEY (p_id) REFERENCES Products(p_id), FOREIGN KEY (brand_id) REFERENCES Brands(brand_id))";
		tables[12] = "GroupedIn (p_id INTEGER, cat_id INTEGER, FOREIGN KEY (p_id) REFERENCES Products(p_id), FOREIGN KEY (cat_id) REFERENCES Categories(cat_id))";
		tables[13] = "MadeUpOf (t_id INTEGER, p_id INTEGER, FOREIGN KEY (t_id) REFERENCES Transactions(t_id), FOREIGN KEY (p_id) REFERENCES Products(p_id), quantity INTEGER, rating DECIMAL(3,1) CHECK(rating >= 0.0 AND rating <= 10.0))";
	
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
		insertSQL[2] = "INSERT INTO Transactions VALUES (30001, '2015-02-07', 1004, 1001), (30002, '2015-02-10', 1005, 1004), (30003, '2015-02-10', 1004, 1002), (30004, '2015-02-10', 1003, 1002), (30005, '2015-02-11', 1001, 1005), (30006, '2015-02-12', 1002, 1005), (30007, '2015-02-12', 1003, 1001), (30008, '2015-02-25', 1001, 1005), (30009, '2015-02-25', 1002, 1005), (30010, '2015-02-25', 1003, 1005)";
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
		int start_u_id = 1005;
	    int start_pay_id = 2005;
	    int start_t_id = 30010;
	    int start_p_id = 10012;
	
	    while (true) {
	   	//DON'T TOUCH THE LOGO, it looks weird here, but that's cause \ needs to be escaped
	    	// I don't know which one I like more...
			// System.out.println("  _______            _ _             _____           _   ");
			// System.out.println(" |__   __|          | (_)           |  __ \\         | |  ");
			// System.out.println("    | |_ __ __ _  __| |_ _ __   __ _| |__) |__   ___| |_ ");
			// System.out.println("    | | '__/ _` |/ _` | | '_ \\ / _` |  ___/ _ \\ / __| __|");
			// System.out.println("    | | | | (_| | (_| | | | | | (_| | |  | (_) |\\__ \\ |_ ");
			// System.out.println("    |_|_|  \\__,_|\\__,_|_|_| |_|\\__, |_|   \\___(_)___/\\__|");
			// System.out.println("                                __/ |                    ");
			// System.out.println("                               |___/                     ");
	    	System.out.println(" _____               _ _               ___           _   ");
			System.out.println("/__   \\_ __ __ _  __| (_)_ __   __ _  / _ \\___   ___| |_ ");
			System.out.println("  / /\\/ '__/ _` |/ _` | | '_ \\ / _` |/ /_)/ _ \\ / __| __|");
			System.out.println(" / /  | | | (_| | (_| | | | | | (_| / ___/ (_) |\\__ \\ |_ ");
			System.out.println(" \\/   |_|  \\__,_|\\__,_|_|_| |_|\\__, \\/    \\___(_)___/\\__|");
			System.out.println("                               |___/                     ");
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
        	
				try {
					
				    String passSQL = "SELECT password, user_id FROM User WHERE username = \'" + username + "\'";
				    java.sql.ResultSet rs1 = statement.executeQuery ( passSQL ) ;
				    
				    while ( rs1.next ( ) ) {
				    	String pass = rs1.getString (1) ;
				    	int userIdentity = rs1.getInt(2) ;
		
					    
					    if(pass.equals(password)){
					    	while(true){
						    	System.out.println("-----------------------------------------------------------------------------------");
						    	System.out.println("Please Select one of the following commands or type 'q' or 'Q' to go back [12345qQ]:");
		        				System.out.println("-----------------------------------------------------------------------------------");
		        				System.out.println("1. Search for an Item");
		        				System.out.println("2. Show Shopping Cart");
		        				System.out.println("3. Put an Item up for Sale");
		        				System.out.println("4. Check Transaction History");
		        				System.out.println("5. Edit Account Info");

		        				String response2 = scanner.nextLine();
		        				if(response2.equals("q") || response2.equals("Q")) {
		        					break;
		        				}
		        				if(response2.equals("1")){
		        					while(true){
			        					System.out.println("---------------------------------------------------------------------------------");
							        	System.out.println("Search by selecting one of the following or type 'q' or 'Q' to go back [123456qQ]:");
							        	System.out.println("---------------------------------------------------------------------------------");
							        	System.out.println("1. List all Items for sale");
							        	System.out.println("2. Search by Item Name");
							        	System.out.println("3. Search by Category");
							        	System.out.println("4. Search by Brand");
							        	System.out.println("5. Search by User");
							        	System.out.println("6. Search by Price");
			        					

							        	String response3 = scanner.nextLine();
							        	if(response3.equals("q") || response2.equals("Q")) {
			        						break;
			        					}
			        					if(response3.equals("1")){
			        						String querySQL = "SELECT Products.p_name, Products.price, Sells.quantity, Products.p_id FROM Products, Sells WHERE sold_flag = \'FALSE\' AND Products.p_id = Sells.p_id";
			        						System.out.println ("--------------------") ;
										    System.out.println ("Listing All Products") ;
										    System.out.println ("--------------------") ;
										    java.sql.ResultSet rs2 = statement.executeQuery ( querySQL ) ;	
										    int[] items = new int[100];
										    int p = 1;			   
										    while ( rs2.next ( ) ) {
												String p_name = rs2.getString (1) ;
												float price = rs2.getFloat (2) ;
												int quantity = rs2.getInt(3) ;
												int id = rs2.getInt(4);
												items[(p-1)] = id;
												System.out.print( p+ ". " + p_name + ": $");
												System.out.printf("%.2f", price);
												System.out.println(" Stock: " + quantity);

												p++;
						
										    }
										    System.out.println("Select by the number of the item to add to cart or '0' to cancel:");		        					
							        		int selection = scanner.nextInt();
							        		if(selection == 0){
							        			continue;
							        		}
							        		else{
							        			
							        			String getStock = "SELECT quantity FROM Sells WHERE p_id = " + items[(selection-1)];
							        			java.sql.ResultSet rs3 = statement.executeQuery ( getStock ) ;	
							        			while( rs3.next ( ) ){
							        				int stock = rs3.getInt(1) - 1;

							        				if( stock == 0){
							        					String sold = "UPDATE Products SET sold_flag = \'TRUE\' WHERE p_id = " + items[(selection-1)];
							        					statement.executeUpdate(sold);
							        					
							        				}

							        				
								        			
								        			String updateSQL = "UPDATE Sells SET quantity = " + stock + " WHERE p_id = " + items[(selection-1)];
								        			statement.executeUpdate(updateSQL);
								        			

								        			
								        			String cart = "SELECT cart_id FROM Owns WHERE user_id = " + userIdentity;
								        			java.sql.ResultSet rs4 = statement.executeQuery ( cart ) ;	
								        			while( rs4.next ( ) ){
								        				int cart_id = rs4.getInt(1);
								        				
								        				String addToCart = "INSERT INTO Contains VALUES (" + cart_id + " ," + items[(selection-1)] + ", 1)";
								        				statement.executeUpdate(addToCart);
								        				
								        				System.out.println("Item added to cart!");
								        				break;
								        			}	
								        			break;
							        			}
										   		continue;
							        		}
							        		

				        				}
				        				if(response3.equals("2")){
				        					System.out.println("Type the Item Name:");		        					
							        		String search_name = scanner.nextLine();
							        		String querySQL = "SELECT Products.p_name, Products.price, Sells.quantity, Products.p_id FROM Products, Sells WHERE sold_flag = \'FALSE\' AND Products.p_id = Sells.p_id AND Products.p_name = \'" + search_name +"\'";
			        						System.out.println ("-----------------------------------------") ;
										    System.out.println ("Listing All Products with the Name " + search_name +":") ;
										    System.out.println ("-----------------------------------------") ;
										    java.sql.ResultSet rs2 = statement.executeQuery ( querySQL ) ;	
										    int[] items = new int[100];
										    int p = 1;			   
										    while ( rs2.next ( ) ) {
												String p_name = rs2.getString (1) ;
												float price = rs2.getFloat (2) ;
												int quantity = rs2.getInt(3) ;
												int id = rs2.getInt(4);
												items[(p-1)] = id;
												System.out.print( p+ ". " + p_name + ": $");
												System.out.printf("%.2f", price);
												System.out.println(" Stock: " + quantity);
												p++;

										    }
										    System.out.println("Select by the number of the item to add to cart or '0' to cancel:");		        					
							        		int selection = scanner.nextInt();
							        		if(selection == 0){
							        			continue;
							        		}
							        		else{
							        			
							        			String getStock = "SELECT quantity FROM Sells WHERE p_id = " + items[(selection-1)];
							        			java.sql.ResultSet rs3 = statement.executeQuery ( getStock ) ;	
							        			while( rs3.next ( ) ){
							        				int stock = rs3.getInt(1) - 1;

							        				if( stock == 0){
							        					String sold = "UPDATE Products SET sold_flag = \'TRUE\' WHERE p_id = " + items[(selection-1)];
							        					statement.executeUpdate(sold);
							        					
							        				}

							        				
								        			
								        			String updateSQL = "UPDATE Sells SET quantity = " + stock + " WHERE p_id = " + items[(selection-1)];
								        			statement.executeUpdate(updateSQL);
								        			

								        			
								        			String cart = "SELECT cart_id FROM Owns WHERE user_id = " + userIdentity;
								        			java.sql.ResultSet rs4 = statement.executeQuery ( cart ) ;	
								        			while( rs4.next ( ) ){
								        				int cart_id = rs4.getInt(1);
								        				
								        				String addToCart = "INSERT INTO Contains VALUES (" + cart_id + " ," + items[(selection-1)] + ", 1)";
								        				statement.executeUpdate(addToCart);
								        				
								        				System.out.println("Item added to cart!");
								        				break;
								        			}	
								        			break;
							        			}
										   		continue;
							        		}
				        				
				        				}
				        				if(response3.equals("3")){
				        					System.out.println("Type the Name of a Category:");		        					
							        		String cat_name = scanner.nextLine();
				        					String querySQL = "SELECT Products.p_name, Products.price, Sells.quantity, Products.p_id FROM Products, Sells, Categories, GroupedIn WHERE sold_flag = \'FALSE\' AND Products.p_id = Sells.p_id AND Categories.cat_name = \'" + cat_name + "\' AND Categories.cat_id = GroupedIn.cat_id AND Products.p_id = GroupedIn.p_id";
			        						System.out.println ("-----------------------------------------") ;
										    System.out.println ("Listing All Products in " + cat_name + ":") ;
										    System.out.println ("-----------------------------------------") ;
										    java.sql.ResultSet rs2 = statement.executeQuery ( querySQL ) ;	
										    int[] items = new int[100];
										    int p = 1;			   
										    while ( rs2.next ( ) ) {
												String p_name = rs2.getString (1) ;
												float price = rs2.getFloat (2) ;
												int quantity = rs2.getInt(3) ;
												int id = rs2.getInt(4);
												items[(p-1)] = id;
												System.out.print( p+ ". " + p_name + ": $");
												System.out.printf("%.2f", price);
												System.out.println(" Stock: " + quantity);
												p++;
												
			
										    }
										    System.out.println("Select by the number of the item to add to cart or '0' to cancel:");		        					
							        		int selection = scanner.nextInt();
							        		if(selection == 0){
							        			continue;
							        		}
							        		else{
							        			
							        			String getStock = "SELECT quantity FROM Sells WHERE p_id = " + items[(selection-1)];
							        			java.sql.ResultSet rs3 = statement.executeQuery ( getStock ) ;	
							        			while( rs3.next ( ) ){
							        				int stock = rs3.getInt(1) - 1;

							        				if( stock == 0){
							        					String sold = "UPDATE Products SET sold_flag = \'TRUE\' WHERE p_id = " + items[(selection-1)];
							        					statement.executeUpdate(sold);
							        					
							        				}

							        				
								        			
								        			String updateSQL = "UPDATE Sells SET quantity = " + stock + " WHERE p_id = " + items[(selection-1)];
								        			statement.executeUpdate(updateSQL);
								        			

								        			
								        			String cart = "SELECT cart_id FROM Owns WHERE user_id = " + userIdentity;
								        			java.sql.ResultSet rs4 = statement.executeQuery ( cart ) ;	
								        			while( rs4.next ( ) ){
								        				int cart_id = rs4.getInt(1);
								        				
								        				String addToCart = "INSERT INTO Contains VALUES (" + cart_id + " ," + items[(selection-1)] + ", 1)";
								        				statement.executeUpdate(addToCart);
								        				
								        				System.out.println("Item added to cart!");
								        				break;
								        			}	
								        			break;
							        			}
										   		continue;
							        		}
			        						
				        				}
				        				if(response3.equals("4")){
				        					System.out.println("Type the Name of a Brand:");		        					
							        		String brand_name = scanner.nextLine();
				        					String querySQL = "SELECT Products.p_name, Products.price, Sells.quantity, Products.p_id FROM Products, Sells, Brands, BrandedBy WHERE sold_flag = \'FALSE\' AND Products.p_id = Sells.p_id AND Brands.brand_name = \'" + brand_name + "\' AND Brands.brand_id = BrandedBy.brand_id AND Products.p_id = BrandedBy.p_id";
			        						System.out.println ("-----------------------------------------") ;
										    System.out.println ("Listing All Products in " + brand_name + ":") ;
										    System.out.println ("-----------------------------------------") ;
										    java.sql.ResultSet rs2 = statement.executeQuery ( querySQL ) ;	
										    int[] items = new int[100];
										    int p = 1;			   
										    while ( rs2.next ( ) ) {
												String p_name = rs2.getString (1) ;
												float price = rs2.getFloat (2) ;
												int quantity = rs2.getInt(3) ;
												int id = rs2.getInt(4);
												items[(p-1)] = id;
												System.out.print( p+ ". " + p_name + ": $");
												System.out.printf("%.2f", price);
												System.out.println(" Stock: " + quantity);
												p++;
												
										    }
				        					System.out.println("Select by the number of the item to add to cart or '0' to cancel:");		        					
							        		int selection = scanner.nextInt();
							        		if(selection == 0){
							        			continue;
							        		}
							        		else{
							        			
							        			String getStock = "SELECT quantity FROM Sells WHERE p_id = " + items[(selection-1)];
							        			java.sql.ResultSet rs3 = statement.executeQuery ( getStock ) ;	
							        			while( rs3.next ( ) ){
							        				int stock = rs3.getInt(1) - 1;

							        				if( stock == 0){
							        					String sold = "UPDATE Products SET sold_flag = \'TRUE\' WHERE p_id = " + items[(selection-1)];
							        					statement.executeUpdate(sold);
							        					
							        				}

							        				
								        			
								        			String updateSQL = "UPDATE Sells SET quantity = " + stock + " WHERE p_id = " + items[(selection-1)];
								        			statement.executeUpdate(updateSQL);
								        			

								        			
								        			String cart = "SELECT cart_id FROM Owns WHERE user_id = " + userIdentity;
								        			java.sql.ResultSet rs4 = statement.executeQuery ( cart ) ;	
								        			while( rs4.next ( ) ){
								        				int cart_id = rs4.getInt(1);
								        				
								        				String addToCart = "INSERT INTO Contains VALUES (" + cart_id + " ," + items[(selection-1)] + ", 1)";
								        				statement.executeUpdate(addToCart);
								        				
								        				System.out.println("Item added to cart!");
								        				break;
								        			}	
								        			break;
							        			}
										   		continue;
							        		}
				        				}
				        				if(response3.equals("5")){
				        					System.out.println("Type the Name of the User:");		        					
							        		String user_name = scanner.nextLine();
							        		String querySQL = "SELECT Products.p_name, Products.price, Sells.quantity, Products.p_id FROM Products, Sells, User WHERE sold_flag = \'FALSE\' AND Products.p_id = Sells.p_id AND  User.user_id = Sells.user_id AND User.username = \'" + user_name +"\'";
			        						System.out.println ("--------------------------------------") ;
										    System.out.println ("Listing All Products By " + user_name + ":") ;
										    System.out.println ("--------------------------------------") ;
										    java.sql.ResultSet rs2 = statement.executeQuery ( querySQL ) ;	
										    int[] items = new int[100];
										    int p = 1;			   
										    while ( rs2.next ( ) ) {
												String p_name = rs2.getString (1) ;
												float price = rs2.getFloat (2) ;
												int quantity = rs2.getInt(3) ;
												int id = rs2.getInt(4);
												items[(p-1)] = id;
												System.out.print( p+ ". " + p_name + ": $");
												System.out.printf("%.2f", price);
												System.out.println(" Stock: " + quantity);
												p++;
												
			
										    }
			        						System.out.println("Select by the number of the item to add to cart or '0' to cancel:");		        					
							        		int selection = scanner.nextInt();
							        		if(selection == 0){
							        			continue;
							        		}
							        		else{
							        			
							        			String getStock = "SELECT quantity FROM Sells WHERE p_id = " + items[(selection-1)];
							        			java.sql.ResultSet rs3 = statement.executeQuery ( getStock ) ;	
							        			while( rs3.next ( ) ){
							        				int stock = rs3.getInt(1) - 1;

							        				if( stock == 0){
							        					String sold = "UPDATE Products SET sold_flag = \'TRUE\' WHERE p_id = " + items[(selection-1)];
							        					statement.executeUpdate(sold);
							        					
							        				}

							        				
								        			
								        			String updateSQL = "UPDATE Sells SET quantity = " + stock + " WHERE p_id = " + items[(selection-1)];
								        			statement.executeUpdate(updateSQL);
								        			

								        			
								        			String cart = "SELECT cart_id FROM Owns WHERE user_id = " + userIdentity;
								        			java.sql.ResultSet rs4 = statement.executeQuery ( cart ) ;	
								        			while( rs4.next ( ) ){
								        				int cart_id = rs4.getInt(1);
								        				
								        				String addToCart = "INSERT INTO Contains VALUES (" + cart_id + " ," + items[(selection-1)] + ", 1)";
								        				statement.executeUpdate(addToCart);
								        				
								        				System.out.println("Item added to cart!");
								        				break;
								        			}	
								        			break;
							        			}
										   		continue;
							        		}
				        				}
				        				if(response3.equals("6")){
				        					System.out.println("Look for Products under $:");		        					
							        		String p_price = scanner.nextLine();
							        		String querySQL = "SELECT Products.p_name, Products.price, Sells.quantity, Products.p_id FROM Products, Sells WHERE sold_flag = \'FALSE\' AND Products.p_id = Sells.p_id AND Products.price <" + p_price;
			        						System.out.println ("--------------------------------------") ;
										    System.out.println ("Listing All Products Under $" + p_price + ":") ;
										    System.out.println ("--------------------------------------") ;
										    java.sql.ResultSet rs2 = statement.executeQuery ( querySQL ) ;	
										    int[] items = new int[100];
										    int p = 1;			   
										    while ( rs2.next ( ) ) {
												String p_name = rs2.getString (1) ;
												float price = rs2.getFloat (2) ;
												int quantity = rs2.getInt(3) ;
												int id = rs2.getInt(4);
												items[(p-1)] = id;
												System.out.print( p+ ". " + p_name + ": $");
												System.out.printf("%.2f", price);
												System.out.println(" Stock: " + quantity);
												p++;
												
			
										    }
				        					System.out.println("Select by the number of the item to add to cart or '0' to cancel:");		        					
							        		int selection = scanner.nextInt();
							        		if(selection == 0){
							        			continue;
							        		}
							        		else{
							        			
							        			String getStock = "SELECT quantity FROM Sells WHERE p_id = " + items[(selection-1)];
							        			java.sql.ResultSet rs3 = statement.executeQuery ( getStock ) ;	
							        			while( rs3.next ( ) ){
							        				int stock = rs3.getInt(1) - 1;

							        				if( stock == 0){
							        					String sold = "UPDATE Products SET sold_flag = \'TRUE\' WHERE p_id = " + items[(selection-1)];
							        					statement.executeUpdate(sold);
							        					
							        				}

							        				
								        			
								        			String updateSQL = "UPDATE Sells SET quantity = " + stock + " WHERE p_id = " + items[(selection-1)];
								        			statement.executeUpdate(updateSQL);
								        			

								        			
								        			String cart = "SELECT cart_id FROM Owns WHERE user_id = " + userIdentity;
								        			java.sql.ResultSet rs4 = statement.executeQuery ( cart ) ;	
								        			while( rs4.next ( ) ){
								        				int cart_id = rs4.getInt(1);
								        				
								        				String addToCart = "INSERT INTO Contains VALUES (" + cart_id + " ," + items[(selection-1)] + ", 1)";
								        				statement.executeUpdate(addToCart);
								        				
								        				System.out.println("Item added to cart!");
								        				break;
								        			}	
								        			break;
							        			}
										   		continue;
							        		}
				        				}
				        			}
			        			}
		        				if(response2.equals("2")){
		        					String querySQL = "SELECT Products.p_name, Products.price, Contains.quantity, Products.p_id FROM Products, User, Owns, Contains WHERE Products.p_id = Contains.p_id AND Owns.user_id = " + userIdentity + " AND Contains.cart_id = Owns.cart_id";
		        					System.out.println ("--------------------") ;
		        					System.out.println(username + "'s Cart");
		        					System.out.println ("--------------------") ;
		        					java.sql.ResultSet rs3 = statement.executeQuery ( querySQL ) ;	
		        					int cart = 1;	
		        					int cartLength = 0;		
		        					float total = 0.0f;
		        					int[] cartItems = new int[10];
		        					// Need to make a better query this loops 5 times... 
									while ( rs3.next ( ) ) {
										String p_name = rs3.getString (1) ;
										float price = rs3.getFloat (2) ;
										int quantity = rs3.getInt(3) ;
										int prod_id = rs3.getInt(4);
										if(cart%5 == 0 ){
											cartLength++;
											total = total + price;
											cartItems[((cart/5)-1)] = prod_id;
											System.out.print( cart/5 + ". " + p_name + ": $");
											System.out.printf("%.2f", price);
											System.out.println(" Stock: " + quantity);
										}
										cart++;
									}
									System.out.println ("--------------------") ;
		        					System.out.print("Total is: $");
		        					System.out.printf("%.2f", total);
		        					System.out.println("");
		        					System.out.println("--------------------") ;
									System.out.println("Would you like to checkout? type 'y' for yes or 'n' for no [yn]:");		        					
							       	String checkout = scanner.nextLine();
							       	if(checkout.equals("y")){
							       		
							       		//Get the current date
							        	DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
										Date date = new Date();
										String currentDate = dateformat.format(date);
    	
							       		for(int index = 0; index < cartLength; index++){

							       			start_t_id++;
							       			String cartSQL = "SELECT Sells.user_id, Products.p_name FROM Sells, Products WHERE Sells.p_id = " + cartItems[index] + " AND Sells.p_id = Products.p_id";
							       			java.sql.ResultSet rs4 = statement.executeQuery ( cartSQL ) ;	
							       			int seller = 0;
							       			String prod_name = "";
							       			while( rs4.next() ){
							       				seller = rs4.getInt(1) ;
							       				prod_name = rs4.getString(2) ;
							       				
							       				
							       			}
							       			String transactionsSQL = "INSERT INTO Transactions VALUES(" + start_t_id + " ,\'" + currentDate + "\' ," + userIdentity + " ," + seller + ")";
							       			statement.executeUpdate (transactionsSQL) ;
							       			System.out.println("Rate " + prod_name + " from 0.0 to 10.0");
							       			Float rating = scanner.nextFloat();
							       			String madeUpOfSQL = "INSERT INTO MadeUpOf VALUES (" + start_t_id + " ," + cartItems[index] + ",1 ," + rating + ")";
							       			statement.executeUpdate (madeUpOfSQL) ;
							       			String deleteFromCartSQL = "DELETE FROM Contains WHERE p_id = " + cartItems[index];
			        						statement.executeUpdate (deleteFromCartSQL) ;
			        						

							       			
							       		}
							       		System.out.println("----------------------------");
							       		System.out.println("Thank you for your Purchase!");
							       		continue;
							       	}
							       	else{

										continue;
									}

										    
										    
		        				}
		        				if(response2.equals("3")){
		        					System.out.println ("------------") ;
									System.out.println ("Sell an Item") ;
									System.out.println ("------------") ;
									System.out.println ("Fill out the following to put an item up for sale!");
									System.out.println ("* - Cannot leave null");
									System.out.println("*Item name?:");
        							String p_name = scanner.nextLine();
        							System.out.println("Model Number?:");
        							String m_numb = scanner.nextLine();
        							
        							System.out.println("*Brand?: (Type from one of the following, case matters)");
        							
        							String brandsSQL = "SELECT brand_name FROM Brands";
									java.sql.ResultSet rs2 = statement.executeQuery ( brandsSQL ) ;			   
									while ( rs2.next ( ) ) {
										String b_name = rs2.getString (1) ;
										System.out.println(b_name);
						
									}
									System.out.println("-------------------------------------------");
        							String brand = scanner.nextLine();

        							System.out.println("*Category?: (Type from one of the following, case matters)");
        							String catsSQL = "SELECT cat_name FROM Categories";
									java.sql.ResultSet rs3 = statement.executeQuery ( catsSQL ) ;			   
									while ( rs3.next ( ) ) {
										String cat_name = rs3.getString (1) ;
										System.out.println(cat_name);
						
									}
									System.out.println("-------------------------------------------");
        							String cat = scanner.nextLine();

        							System.out.println("*Price?: (in the form xxx.xx up to 999.99)");
        							String price = scanner.nextLine();
        							System.out.println("*How many " + p_name + "(s) you selling?:");
        							int stock = scanner.nextInt();
        							


        							String bidSQL = "SELECT brand_id FROM Brands WHERE brand_name = \'" + brand + "\'";
									java.sql.ResultSet rs4 = statement.executeQuery ( bidSQL ) ;	
									int brand_id = 0;		   
									while ( rs4.next ( ) ) {
										brand_id = rs4.getInt (1) ;
						
									}

        							String cidSQL = "SELECT cat_id FROM Categories WHERE cat_name = \'" + cat + "\'";
									java.sql.ResultSet rs5 = statement.executeQuery ( cidSQL ) ;	
									int cat_id = 0;		   
									while ( rs5.next ( ) ) {
										cat_id = rs5.getInt (1) ;
						
									}

									start_p_id++;
									String productsSQL = "INSERT INTO Products VALUES (" + start_p_id + ", \'" + p_name + "\', " + price + ", \'" + m_numb + "\', 0.0, \'FALSE\')";
							       	statement.executeUpdate (productsSQL) ;
									String brandedBySQL = "INSERT INTO BrandedBy VALUES (" + start_p_id + ", " + brand_id + ")";
									statement.executeUpdate (brandedBySQL) ;
        							String groupedInSQL = "INSERT INTO GroupedIn VALUES (" + start_p_id + ", " + cat_id + ")";
							       	statement.executeUpdate (groupedInSQL) ;
							       	
							       	String sellsSQL = "INSERT INTO Sells VALUES (" + userIdentity + ", " + start_p_id + ", " + stock + ", " + price + ")";
		        					statement.executeUpdate (sellsSQL) ;

		        					System.out.println("Your " + p_name + "(s) have been added to the database!");
		        					System.out.println("Thanks for using TradingPo.st! Sending you back to previous prompt...");
		        				}
		        				if(response2.equals("4")){
		        					try{
		        						String querySQL = "SELECT Transactions.t_date AS Date, Products.p_name,  u1.username AS Buyer, u2.username as Seller, ROUND((MadeUpOf.quantity * Products.price), 2) AS TotalPrice FROM User u1, User u2, Transactions, Products, MadeUpOf WHERE (Transactions.buyer_id = " + userIdentity + " OR Transactions.seller_id = " + userIdentity + ") AND u1.user_id = Transactions.buyer_id AND u2.user_id = Transactions.seller_id AND Products.p_id = MadeUpOf.p_id AND MadeUpOf.t_id = Transactions.t_id ORDER BY Transactions.t_date";
				        				System.out.println ("------------------------") ;
										System.out.println ("Listing All Transactions") ;
										System.out.println ("------------------------") ;
										java.sql.ResultSet rs2 = statement.executeQuery ( querySQL ) ;	
										
										int p = 1;			   
										while ( rs2.next ( ) ) {
											
											String trans_date = rs2.getString(1);
											String p_name = rs2.getString (2) ;
											String buyer = rs2.getString(3) ;
											String seller = rs2.getString(4) ;
											float total_price = rs2.getFloat(5) ;

											System.out.print( trans_date + " " + seller + " sold " + p_name + " to " + buyer +" for: $");
											System.out.printf("%.2f", total_price);
											System.out.println("");
										
											p++;

							
										}
										continue;
									} catch (SQLException e)
								    {
									sqlCode = e.getErrorCode(); // Get SQLCODE
									sqlState = e.getSQLState(); // Get SQLSTATE
							                
									// Your code to handle errors comes here;
									// something more meaningful than a print would be good
									/* 
											It's just better these stay commented out.. everything is working 
									*/
										System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
										System.out.println ("ERROR printing out Transaction History");
								    	continue;
								    }
		        				}
		        				if(response2.equals("5")){
		        				/*


		        				TODO


		        				*/
		        					System.out.println("-----------------");
		        					System.out.println("Edit Account Info");
		        					System.out.println("-----------------");
		        					System.out.println("if you want to edit a field press 'y' for yes or 'n' for no [yn]:");
		        					System.out.println("Edit Username?:");
		        					String edit1 = scanner.nextLine();
		        					if(edit1.equals("y")){
		        						System.out.println("Enter new Username:");
		        						String newUserName = scanner.nextLine();
		        						String updateSQL = "UPDATE User SET username = \'" + newUserName + "\' WHERE user_id = " + userIdentity;
										statement.executeUpdate(updateSQL);
									}
									System.out.println("Edit Password?:");
		        					String edit2 = scanner.nextLine();
		        					if(edit2.equals("y")){
		        						System.out.println("Enter new Password:");
		        						String newPass = scanner.nextLine();
		        						String updateSQL = "UPDATE User SET password = \'" + newPass + "\' WHERE user_id = " + userIdentity;
										statement.executeUpdate(updateSQL);
									}


//I Don't know where all of these brackets meet up... but the program works somehow..
		        				}
		        			}
		        		}else {
		        			System.out.println ("Incorrect Password");
					    	continue;
		        		}
		        	}
			    }
			    catch (SQLException e){
					sqlCode = e.getErrorCode(); // Get SQLCODE
					sqlState = e.getSQLState(); // Get SQLSTATE
	        		        
					// Your code to handle errors comes here;
					// something more meaningful than a print would be good

					// It's fine, everything is totally fine don't need to uncomment this

					// System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
		    	} 
	
	        }
	        if(response1.equals("2")){
        	System.out.println("---------------");
        	System.out.println("Make an Account");
        	System.out.println("---------------");
        	System.out.println("This will take a series of steps (Since this is a fictitious service, don't enter real information)");
        	System.out.println("* - Cannot leave null");
        	System.out.println("------------------------");
        	System.out.println("Enter a *Username:");
        	String username = scanner.nextLine();
        	System.out.println("Enter a *Password:");
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

				System.out.println(since);
        		
        		start_u_id++;
			    String newAccount = "INSERT INTO User VALUES (" + start_u_id+ ",\'" + username + "\', \'" + password + "\' , \'" + email + "\', \'" + address + "\', \'" + city + "\', \'" + p_code + "\', 5.0 ,\'" + since + "\')";
			   	statement.executeUpdate ( newAccount ) ;
			    System.out.println("Awesome! Your Account is almost set up.");
			    System.out.println("* - Cannot leave null");
        		System.out.println("------------------------");


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
        	System.out.println("What's your *First Name?:");
        	String f_name = scanner.nextLine();
        	System.out.println("What's your *Last Name?:");
        	String l_name = scanner.nextLine();
        	System.out.println("What's your *Credit Card Number?[in the form xxxx-xxxx-xxxx-xxxx]:");
        	String c_numb = scanner.nextLine();
        	System.out.println("What is your *Bank Name?:");
        	String b_name = scanner.nextLine();
        	try {
				
				start_pay_id++;
			    String newAccount = "INSERT INTO PaymentInfo VALUES (" + start_pay_id + ", \'" + f_name + "\', \'" + l_name + "\', \'" + address + "\', \'" + city + "\',\'" + p_code +  "\',\'" + c_numb + "\', \'" + b_name + "\')";
			   	statement.executeUpdate ( newAccount ) ;
			    System.out.println("Awesome! Your Account is almost set up.");
			    System.out.println("Your new account is totally set up, we'll send you back to the first prompt to sign in again!");
			    System.out.println("---------------------------------------------------------------------------------------------");
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
		
	    try {
		    
		    for(int d = 0; d<tableName.length; d++){
			    String dropSQL = "DROP TABLE " + tableName[d];
			    System.out.println ( dropSQL ) ;
			    statement.executeUpdate ( dropSQL ) ;
			    
			}
			System.out.println ("ALL TABLES HAVE BEEN DROPPED");
			System.out.println ("Thanks for using Tradingpo.st!");
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
