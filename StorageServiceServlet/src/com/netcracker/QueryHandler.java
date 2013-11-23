package com.netcracker;

import static com.netcracker.HTMLTags.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Exception that is thrown if all fields of HTML form were submitted empty.
 * @author Red_Shuhov
 *
 */
class NoValuesException extends Exception {
	static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		return "All fields are empty.";
	}
	
	@Override
	public String toString() {
		return "NoValuesException: " + this.getMessage();
	}
}

/**
 * <code>QueryHandler</code> is a class for working with all HTTP queries. 
 * With <code>QueryHandler</code> it is possible to parse queries, form HTML view for entries and managing printing of queries.
 */

public abstract class QueryHandler {
	
	private static Logger log = Logger.getLogger("Log2File");
	
	/**
	 * Prints result of search query for {@code entry} in {@code storage} to {@printWriter}.
	 * @param storage - current storage. Instance of {@link Storage}.
	 * @param printWriter - output stream.
	 * @param pattern - a pattern of entries that are queries.
	 * @throws IOException - if something is wrong with printing to {@code printWriter}.
	 */
	public static void printQuery(Storage storage, PrintWriter printWriter, Entry pattern) throws IOException {		
		Entry[] entries = storage.getEntries(pattern.getValues());
		
		if (entries == null || entries.length == 0) {
			QueryHandler.errorAlert(printWriter, "No result");
			log.warn("Queried entries not found");
			return;
		}
		
		printWriter.println(openHTML);
		printWriter.println(openHead);
		printWriter.println(closeHead);
		printWriter.println(openBody);
		printWriter.println(openTable);
		
		printWriter.println(keyNames);
		
		for (Entry next: entries) {
			printWriter.println(QueryHandler.formResponseForEntry(next));
		}
		
		printWriter.println(closeTable);
		printWriter.println(closeBody);
		printWriter.println(closeHTML);
		
		log.info("Find entry query completed successfully.");
	}
	
	/**
	 * Information alert about successful inserting of new entry. 
	 * @param printWriter - output stream.
	 * @throws IOException - if something is wrong with printing to {@code printWriter}.
	 */
	public static void newEntryAlert(PrintWriter printWriter) throws IOException {
		printWriter.println("New entry has been added successfully.");
		log.info("New entry has been added.");
	}
	
	/**
	 * Provides valid HTML mark-up for {@code entry} as follows:
	 * 
	 * <pre>
	 * 	&lt;tr&gt;&lt;td&gt;entry.field_1&lt;/td&gt;...&lt;td&gt;entry.field_n&lt;/td&gt;&lt;/tr&gt;
	 * </pre>
	 * @param entry - entry to form HTML mark-up of.
	 * @return - valid HTML mark-up of {@code entry} as {@link String} object.
	 */
	private static String formResponseForEntry(Entry entry) {
		String response = "";	
		
		response += trOpen;
		
		response += tdOpen + entry.getID() + tdClose;
		
		for (Keys key: Keys.values()) {
			
			String next = "NULL";
			
			if (entry.getValue(key) != null) {
				next = entry.getValue(key);
			}
			
			response +=  tdOpen + next + tdClose;
		}
		
		response += trClose;
		
		return response;
	}
	
	/**
	 * Prints all data that contained in {@code storage} to {@code printWriter}.
	 * @param storage - current storage. Instance of {@link Storage}.
	 * @param printWriter - output stream.
	 * @throws IOException - if something is wrong with printing to {@code printWriter}.
	 */
	public static void printAllData(Storage storage, PrintWriter printWriter) throws IOException {
		
		if (storage.getAllEntries().length == 0) {
			QueryHandler.errorAlert(printWriter, "The storage is empty");
			return;
		}
		
		printWriter.println(openHTML);
		printWriter.println(openHead);
		printWriter.println(closeHead);
		printWriter.println(openBody);
		printWriter.println(openTable);

		printWriter.println(keyNames);
	
		for (Entry entry: storage.getAllEntries()) {
			printWriter.println(formResponseForEntry(entry));
		}
		
		printWriter.println(closeTable);
		printWriter.println(closeBody);
		printWriter.println(closeHTML);
		
		log.info("Query for getting all entries.");
	}
	
	/**
	 * Parsing HTTP query for action, keys and values.
	 * @param request - HTTP request.
	 * @param response - HTTP response
	 * @return - valid query as {@link Query} object.
	 * @throws ServletException - any issues when trying to retrieve query information 
	 * @throws IOException - if something is wrong with printing to {@code printWriter}. 
	 * @throws IllegalArgumentException - if illegal action is attempted.
	 * @throws NoValuesException - if all fields of query are empty.
	 */
	public static Query parseQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IllegalArgumentException, NoValuesException {
		
		Action action = Action.show_all;
		
		String key = null;
		try {
			key = request.getParameter("action");
			action = Action.valueOf(key);
		} catch (IllegalArgumentException e) {
			log.warn("No such action " + key + ".");
			throw new IllegalArgumentException("No such action <b><i>\'" + key + "\'</i></b>");
		}
		
		ArrayList<Keys> keys = new ArrayList<>();
		ArrayList<String> values = new ArrayList<>();
		
		for (Keys next: Keys.values()){
			String value = request.getParameter(next.toString());
		
			if (value != null) {
				value = value.trim();
			}
			
			if ("".equals(value)) {
				value = null;
			}
			
			if (value != null) {
				keys.add(next);
				values.add(value);
			}
		}
		
		if (action != Action.show_all && keys.size() == 0) {
			log.warn("Empty query.");
			throw new NoValuesException();
		}	
		
		Keys[] keysArray = new Keys[keys.size()];
		String[] valuesArray = new String[Keys.values().length];
		
		keysArray = keys.toArray(keysArray);
		valuesArray = values.toArray(valuesArray);
		
		Entry entry = new Entry(keysArray, valuesArray);
		
		log.info("New query parsed successfully");
		return new Query(action, entry);
	}
	
	/**
	 * Printing error messages
	 * @param printWriter - output stream.
	 * @param message - message of error.
	 */
	public static void errorAlert(PrintWriter printWriter, String message) {
		printWriter.println("ERROR: " + message);
		log.error(message);
	}
}
