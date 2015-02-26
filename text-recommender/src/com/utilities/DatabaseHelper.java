package com.utilities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class DatabaseHelper {
	private java.sql.Connection connection;
	private java.sql.Statement statement;
	private PreparedStatement preparedStatement;
	private HashMap<String, String> textCity;

	private void createStatement() {
		try {
			this.statement = connection.createStatement();

		} catch (java.sql.SQLException se) {
			System.out.println("Error in createStatement SQLException\n");
			se.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error in createStatement Exception\n");
			e.printStackTrace();
		}
	}

	private void createPreparedStatementToInsertHotel() {
		try {
			this.preparedStatement = connection
					.prepareStatement("INSERT INTO  `textreco`.`hotel` (`index` ,`city` ,`name` ,`rawsummary` ,`prcsummary`) VALUES(NULL,?,?,?,?)");

		} catch (java.sql.SQLException se) {
			System.out.println("Error in createStatement SQLException\n");
			se.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error in createStatement Exception\n");
			e.printStackTrace();
		}
	}

	public void insert(String city, String hotel, String rawSumm, String proSumm) {
		// String query =
		// "INSERT INTO  `textreco`.`hotel` (`index` ,`city` ,`name` ,`rawsummary` ,`prcsummary`) VALUES (NULL ,  '"
		// + city +"',  '" + hotel +"',  '" + rawSumm + "',  '" + proSumm +"')";
		try {
			createPreparedStatementToInsertHotel();
			preparedStatement.setString(1, city);
			preparedStatement.setString(2, hotel);
			preparedStatement.setString(3, rawSumm);
			preparedStatement.setString(4, proSumm);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// System.out.println("\n" + query + "\n");
			System.out.println("Error in insert() SQLException\n");
			e.printStackTrace();
		}
	}

	public void insertHotelReview(String fileName, String rawSumm,
			String proSumm) {
		String city = "unknown";
		String hotel = fileName;
		Set<String> keys = textCity.keySet();

		for (Iterator<String> i = keys.iterator(); i.hasNext();) {
			String text = (String) i.next();
			if (fileName.contains(text)) {
				city = (String) textCity.get(text);
				int end = text.length();
				hotel = fileName.substring(end);
				hotel = hotel.replace("_"," ");
				hotel = hotel.trim();
				break;
			}
		}
		insert(city, hotel, rawSumm, proSumm);
	}

	private void initHashMap() {
		textCity = new HashMap<String, String>();
		textCity.put("china_beijing", "Beijing");
		textCity.put("usa_illinois_chicago", "Chicago");
		textCity.put("are_dubai", "Dubai");
		textCity.put("usa_nevada_las-vegas", "Las Vegas");
		textCity.put("uk_england_london", "London");
		textCity.put("can_montreal", "Montreal");
		textCity.put("india_new delhi", "Delhi");
		textCity.put("usa_new york city", "New York City");
		textCity.put("usa_san francisco", "San Fransisco");
		textCity.put("china_shanghai", "shanghai");

	}

	public DatabaseHelper() {
		connection = MySQLConnector.getSingleConnectionObject();
		// createStatement();
		initHashMap();
		// insert("delhi","taj","test","notest");

	}
}
