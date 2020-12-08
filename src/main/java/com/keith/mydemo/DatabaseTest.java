package com.keith.mydemo;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseTest {
	private static Connection connection = null;

	public static void initializeConnection() {
		try {
			Class.forName("org.postgresql.Driver");
			String pgpassword = System.getenv("PG_PASSWORD");
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/seabassdox", "postgres", pgpassword);
			System.out.println("Connected to database!");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.err.println("FAILED TO CONNECT TO POSTGRES DATABASE");
			connection = null;
		}
	}

	public static String getAllData() {
		if (connection == null) {
			return "database error";
		}
		Statement statement;
		String result = "";
		try {
			statement = connection.createStatement();
	        ResultSet rs = statement.executeQuery("SELECT * FROM threatdata");
	        int numbers = 0;
	        int total = 0;
	        while(rs.next()) {
	        	int id = rs.getInt("id");
	        	int threat = rs.getInt("threat");
	        	result += "{" + id + ":" + threat + "} ";
	        	total+=threat;
	        	numbers++;
	        }
	        if(numbers !=0) {
	        	result+="Average: "+total/numbers;
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static String putData() {
		if (connection == null) {
			return "database error";
		}
		Statement statement;
		String result = "";
		int totalIdVals = 0;
		int numThreats = 1;
		try {
			statement = connection.createStatement();
			statement.execute("INSERT INTO threatdata(threat) VALUES (35)");
			ResultSet rs = statement.executeQuery("SELECT * FROM threatdata");
			while (rs.next()) {

				int id = rs.getInt("id");
				int threat = rs.getInt("threat");
				result += "{" + id + ":" + threat + "} ";
				totalIdVals += threat;
				numThreats += 1;
			}
			totalIdVals /= numThreats;
			result += "\n the average is: " + totalIdVals;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	 public static String putSomeData(String number) {
		 if(connection == null) {
			 return "database error";
		 }
        Statement statement;
        try {
			statement = connection.createStatement();
			boolean rs = statement.execute("INSERT INTO public.threatdata(threat) VALUES("+number+");");
        }catch (SQLException e) {
        	e.printStackTrace();
        	return "oh no it didn't work";
        }
		return "yay it worked";
	 }
}
