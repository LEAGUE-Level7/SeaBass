package com.keith.mydemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseTest {
	private static Connection connection = null;

	public static void initializeConnection() {
		try {
			Class.forName("org.postgresql.Driver");
			String pgpassword = System.getenv("PG_PASSWORD");
			connection = DriverManager.getConnection(
					"jdbc:postgresql://seabassdb4.westus.cloudapp.azure.com/seabassdox", "postgres", pgpassword);

			System.out.println("Connected to database!");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.err.println("FAILED TO CONNECT TO POSTGRES DATABASE");
			connection = null;
		}
	}

	public static boolean isConnected() {
		return connection != null;
	}

	public static double getWorldAverage() throws SQLException {
		// TODO query for world average and return it as a double.
		// The getAllData() function below already does something similar
		Statement statement;
		statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("SELECT * FROM threatdata");

		double avg = 0.0;
		double numbers = 0.0;
		int total = 0;
		ArrayList<Integer> idList = new ArrayList<Integer>();

		while (rs.next()) {
			int id = rs.getInt("id");
			int threat = rs.getInt("threat");
			// result += "{" + id + ":" + threat + "} ";
			idList.add(id);

			if (idList.contains(id)) {
				total+=0;
				numbers+=0;
			} else {
				total += threat;
				numbers++;
			}

		}
		if (numbers != 0) {
			avg += total / numbers;
		}

		return avg;
	}

	public static String getAllData() throws SQLException {
		if (connection == null) {
			return "database error";
		}

		String result = "";

		result += "Average: " + getWorldAverage();

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
		if (connection == null) {
			return "database error";
		}
		Statement statement;
		try {
			statement = connection.createStatement();
			boolean rs = statement.execute("INSERT INTO public.threatdata(threat) VALUES(" + number + ");");
		} catch (SQLException e) {
			e.printStackTrace();
			return "oh no it didn't work";
		}
		return "yay it worked";
	}
}
