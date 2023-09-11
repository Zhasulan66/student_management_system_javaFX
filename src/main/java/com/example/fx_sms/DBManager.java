package com.example.fx_sms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {

    // Database connection properties
    private static String url = "jdbc:postgresql://localhost:5432/lms_db";
    private static String username = "postgres";
    private static String password = "bzhz2002";

    public static Connection getConnection() throws SQLException
    {
        System.out.println("Connected to the PostgreSQL database!");
        return DriverManager.getConnection(
                url, username, password);

    }

    /*public static Connection connectDb(){
        // Establish the connection
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            // Connection successful, perform database operations here
            System.out.println("Connected to the PostgreSQL database!");
            return connection;



        } catch (SQLException e) {
            System.out.println("Failed to connect to the PostgreSQL database!");
            e.printStackTrace();
        }
        return null;
    }*/


}
