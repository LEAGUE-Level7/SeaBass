package com.seabass.doxing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	private static Connection connection = null;

	public static void initializeConnection() {
		try {
			Class.forName("org.postgresql.Driver");
			String pgpassword = System.getenv("PG_PASSWORD");

			connection = DriverManager.getConnection(
					"jdbc:postgresql://seabassdb4.westus.cloudapp.azure.com/seabassdox?sslmode=disable", "postgres",
					pgpassword);

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
		// The getAllData() function below already does something similar
		Statement statement;
		statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("SELECT * FROM threatdata2");

		double avg = 0.0;
		double numbers = 0.0;
		int total = 0;
		String result = "";

		while (rs.next()) {
			int id = rs.getInt("id");
			int threat = rs.getInt("threat");
			String handle = rs.getString("handle");
			result += "{" + id + ":" + threat + ":" + handle + "}";

			total += threat;
			numbers++;

		}
		if (numbers != 0) {
			avg += total / numbers;
		}
		System.out.println(result);
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

	public static String putSomeData(String number, String handle) throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet rs1 = statement.executeQuery("SELECT * FROM threatdata2");

		if (connection == null) {
			return "database error";
		}

		try {
			String insertReplaceCommand = "INSERT INTO public.threatdata2(threat, handle) " + "VALUES(" + number + ",'"
					+ handle + "') " + "ON CONFLICT(handle) DO UPDATE SET threat = excluded.threat";
			// this command was giving me errors on the BEGIN statement so changed it to the
			// above instead
			String insertExceptionCommand = "BEGIN \nINSERT INTO public.threatdata2(threat, handle) VALUES(" + number
					+ " , " + "'" + handle + "'" + "); \nEXCEPTION WHEN unique_violation THEN\nEND;";
			boolean rs = statement.execute(insertReplaceCommand);
		} catch (SQLException e) {
			e.printStackTrace();
			return "oh no it didn't work";
		}
		return "yay it worked";
	}
}
