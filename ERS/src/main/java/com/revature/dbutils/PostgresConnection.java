package com.revature.dbutils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresConnection {
	private static Connection c;

	private PostgresConnection() {
	};

	public static Connection getConnection() {
		String DB_NAME = "postgres";
		String RD_HOSTNAME = "database-1.cajpe9j7dns9.us-east-2.rds.amazonaws.com";
		String RD_USERNAME = "postgres";
		String DB_PASSWORD = "NUMERO01";
		String JDBC_URL = "jdbc:postgresql://" + RD_HOSTNAME + ":" + 5432 + "/" + DB_NAME + "?user=" + RD_USERNAME
				+ "&password=" + DB_PASSWORD;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection(JDBC_URL);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}
}
