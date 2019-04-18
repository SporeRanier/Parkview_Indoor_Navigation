package edu.ipfw.parkview.indoornavigation;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionManager {
    private static String url = "jdbc:mysql://127.0.0.1:3306/mobile";
    private static String driverName = "com.mysql.jdbc.Driver";
    private static String username = "root";
    private static String password = "camaroz28";
    private static Connection con;
    private static String urlstring;

    public static Connection getConnection() {


        try {
            ConnectionManager.class.forName("edu.ipfw.parkview.indoornavigation.ConnectionManager");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
                    con = DriverManager.getConnection(url, username, password);
            } catch (SQLException ex) {
                // log an exception. fro example:
                System.out.println("Failed to create the database connection.");
            }


        return con;
    }
}