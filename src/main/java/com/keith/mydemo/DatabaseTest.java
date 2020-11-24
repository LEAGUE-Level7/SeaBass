package com.keith.mydemo;

import java.sql.*;

public class DatabaseTest {
	private static Connection connection = null;
	public static void initializeConnection() {
	     try {
		        Class.forName("org.postgresql.Driver");
		        connection = DriverManager
		           .getConnection("jdbc:postgresql://localhost:5432/seabassdox",
		           "postgres", "asdf");
		        System.out.println("Connected to database!");
		     } catch (Exception e) {
		        e.printStackTrace();
		        System.err.println(e.getClass().getName()+": "+e.getMessage());
		        System.err.println("FAILED TO CONNECT TO POSTGRES DATABASE");
		        connection = null;
		     }
	     
	}

	 public static String getAllData() {
		 if(connection == null) {
			 return "database error";
		 }
        Statement statement;
        String result = "";
		try {
			statement = connection.createStatement();
	        ResultSet rs = statement.executeQuery("SELECT * FROM threatdata");
	        while(rs.next()) {
	        	int id = rs.getInt("id");
	        	int threat = rs.getInt("threat");
	        	result += "{" + id + ":" + threat + "} ";
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	 }
}
