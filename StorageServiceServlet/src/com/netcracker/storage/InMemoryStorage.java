package com.netcracker.storage;

import java.util.ArrayList;
import java.util.List;

import com.netcracker.Entry;
import com.netcracker.Keys;
import com.netcracker.Storage;

public class InMemoryStorage implements Storage {
	
	private static long count = 1L;
	private final List<Entry> storage = new ArrayList<>();
	
	public boolean addEntry(Entry entry) {
		entry.setID(count++);
		storage.add(entry);
		
		return true;
	}
	
	public Entry[] getEntries(String[] values) {
		ArrayList<Entry> found = new ArrayList<>();
		
		for (String s: values) {
			System.out.println(s);
		}
		
		for (int i = 0; i < storage.size(); ++i) {
			boolean match = true;
			Entry current = storage.get(i);
			
			for (Keys key: Keys.values()) {
				
				int id = key.ordinal();
				
				System.out.println(current.getValue(key) + " " + values[id]);
				
				if (values[id] != null) {
					match = match & (values[id].equals(current.getValue(key)));
				}
				
				if (!match) {
					break;
				}					
			}
			
			if (match) {
				found.add(current);
			}
		}
		
		Entry[] result = new Entry[found.size()];
		result = found.toArray(result);
		
		return result;
	}
	
	public Entry[] getAllEntries() {
		Entry[] entries = new Entry[storage.size()];
		entries = storage.toArray(entries);
		return entries;		
	}
}
