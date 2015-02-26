package com.utilities;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class MySQLConnector {
	Connection connection = null;
	String hostname;
	int portnumber;
	String dbUsername;
	String password;

	public static Connection singleConnection = null;

	MySQLConnector(String hostname, int portnumber, String dbUsername,
			String password) {
		this.hostname = hostname;
		this.portnumber = portnumber;
		this.dbUsername = dbUsername;
		this.password = password;
	}

	public static Connection getSingleConnectionObject() {
		if (singleConnection == null) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				singleConnection = (Connection) java.sql.DriverManager
						.getConnection("jdbc:mysql://localhost:3306/textreco",
								"root", "");
			} catch (SQLException se) {
				se.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return singleConnection;
	}

	public Connection getConnection() {
		return this.connection;
	}
}
