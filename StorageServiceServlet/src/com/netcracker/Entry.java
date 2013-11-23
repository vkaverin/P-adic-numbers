package com.netcracker;

public class Entry {
	
	/** Contains ALL values of entry. If there's no value for a key then value is <t>null</t>. */
	private String[] values = new String[Keys.values().length];
	
	/** ID of entry in storage. The id set by database itself. */
	private long id;  
	
	public Entry(Keys[] keys, String[] values) {
		int i = 0;
		for (Keys key: keys) {
			int id = key.ordinal();
			this.values[id] = values[i];
			++i;
		}
	}
	
	public Entry(Keys[] keys, String[] values, long id) {
		this(keys, values);
		this.id = id;
	}
	
	public void setID(long id) {
		this.id = id;
	}
	
	public long getID() {
		return this.id;
	}
	
	public String getValue(Keys key){
		return this.values[key.ordinal()];
	}
	
	public void setValue(Keys key, String value) {
		this.values[key.ordinal()] = value;
	}
	
	public String[] getValues() {
		return this.values.clone();
	}
}
