package com.netcracker;

public abstract class HTMLTags {
	static String openHTML = "<html>";
	static String closeHTML = "</html>";
	static String openHead = "<head>";
	static String closeHead = "</head>";
	static String openBody = "<body>";
	static String closeBody = "</body>";
	static String openTable = "<table border = 1px>";
	static String closeTable = "</table>";
	static String trOpen = "<tr>";
	static String tdOpen = "<td> ";
	static String trClose = "</tr>";
	static String tdClose = " </td>";
	static String keyNames = "";
	
	static {
		
		keyNames += trOpen + tdOpen + "person_id" + tdClose;
		
		for (Keys key: Keys.values()) {
			keyNames += tdOpen + key + tdClose;
		}		
		
		keyNames += trClose;
	}
}
