package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ControllerDB {
	//private static String driverName = "com.mysql.jdbc.Driver";
	private static final String url = "jdbc:mysql://remotemysql.com:3306/JTw8pRhybP";
	private static final String user = "JTw8pRhybP";
    private static final String password = "FeYHzXYXxk";
    private static Connection con;
    

    public static Connection getConnection() {
    
            try {
                con = DriverManager.getConnection(url, user, password);
            } catch (SQLException ex) {
               
                System.out.println("Failed to create the database connection."); 
            }
        return con;
    }
}
