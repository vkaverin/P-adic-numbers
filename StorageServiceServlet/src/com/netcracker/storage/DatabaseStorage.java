package com.netcracker.storage;

import java.sql.*;
import java.util.ArrayList;

import com.netcracker.Entry;
import com.netcracker.Keys;
import com.netcracker.Storage;

public class DatabaseStorage implements Storage {
	
	Connection connection;
	Statement statement;
	private final String personTable = "Person";
	private final String getID = "IDGETTER.nextval";

	public DatabaseStorage() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "StorageDB";
		String password = "storage";
		
		try {
			connection = DriverManager.getConnection(url, user, password);
			statement = connection.createStatement();
			
			if (statement == null) {
				System.out.println("Bullshit. I leave this fucking place.");
				System.exit(1);
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	
	@Override
	public boolean addEntry(Entry entry) {
		
		String sqlQuery = "INSERT INTO " + personTable + "(" + Keys.personID;
		String values = "VALUES (" + getID;
		
		for (Keys key: Keys.values()) {
			
			String next = "";
			
			next = entry.getValue(key);
			
			if (next == null) {
				continue;
			}
			
			sqlQuery += ", " + key;
			values += ", \'" + next + "\'";
		}
		
		sqlQuery += ") " + values + ")";
		
		System.out.println(sqlQuery);
		
		try {
			statement.executeUpdate(sqlQuery);
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
		
		return true;
	}

	@Override
	public Entry[] getEntries(String[] values) {
		String sql = "SELECT" + " " + "*" + " " + "FROM" + " " + personTable + " " + "WHERE" + " ";
	
		boolean moreThanOne = false;
		
		for (Keys key: Keys.values()) {
			if (values[key.ordinal()] != null) {
				if (moreThanOne) {
					sql += " AND ";
				}				
				sql += key + " = \'" + values[key.ordinal()] + "\'";
				moreThanOne = true;
			}
		}
		
		System.out.println(sql);
		
		ResultSet result = null;
				
		try {
			 result = statement.executeQuery(sql);
		} catch (SQLException e){
			return null;
		}
			
		return DatabaseStorage.sqlResultToEntryArray(result);
	}

	@Override
	public Entry[] getAllEntries() {
		
		try {
			return DatabaseStorage.sqlResultToEntryArray(statement.executeQuery("SELECT * FROM " + personTable));
		} catch (SQLException e) {
			return null;
		}
	}

	private static Entry[] sqlResultToEntryArray(ResultSet result) {
		
		if (result == null) {
			return null;
		}
		
		ArrayList<Entry> found = new ArrayList<>();
		
		try {
			while(result.next()) {
				ArrayList<Keys> activeKeys = new ArrayList<>();
				ArrayList<String> activeValues = new ArrayList<>();
				
				for (Keys key: Keys.values()) {
					String next = result.getString(key.name());
					if (next != null) {
						activeKeys.add(key);
						activeValues.add(next);
					}
				}
				
				Keys[] entryKeys = new Keys[activeKeys.size()];
				String[] entryValues = new String[entryKeys.length];
				
				found.add(new Entry(activeKeys.toArray(entryKeys), activeValues.toArray(entryValues), result.getLong(Keys.personID)));
			}	
		} catch (SQLException e) {
			return null;
		}
		
		Entry[] foundArray = new Entry[found.size()];
		
		return found.toArray(foundArray);
	}
}
