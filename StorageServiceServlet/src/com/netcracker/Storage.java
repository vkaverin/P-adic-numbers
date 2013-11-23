package com.netcracker;

public interface Storage {
	boolean addEntry(Entry entry);
	Entry[] getEntries(String values[]);
	Entry[] getAllEntries();
}
