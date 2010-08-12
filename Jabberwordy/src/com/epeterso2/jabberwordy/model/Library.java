package com.epeterso2.jabberwordy.model;

import java.util.HashSet;
import java.util.Set;

public class Library {
	
	private Set<LibraryEntry> entries = new HashSet<LibraryEntry>();

	public void setEntries(Set<LibraryEntry> entries) {
		this.entries = entries;
	}

	public Set<LibraryEntry> getEntries() {
		return entries;
	}

}
