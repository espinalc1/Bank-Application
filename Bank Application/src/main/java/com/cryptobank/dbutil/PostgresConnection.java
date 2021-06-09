package com.cryptobank.dbutil;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PostgresConnection {
	public static Connection connection = null;

	// a static method is the best way to do this because the same connection can be
	// used across the entire program
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		if (connection == null) {
			Class.forName("org.postgresql.Driver");

			FileInputStream fileStream;
			try {
				fileStream = new FileInputStream("src/main/resources/application.properties");
				Properties prop = new Properties();
				prop.load(fileStream);

				String url = prop.getProperty("url");
				String username = prop.getProperty("username");
				String password = prop.getProperty("password");
				connection = DriverManager.getConnection(url, username, password);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Connection Wasn't Established!");
			}
			return connection;
		} else {
			return connection;
		}
	}

}
