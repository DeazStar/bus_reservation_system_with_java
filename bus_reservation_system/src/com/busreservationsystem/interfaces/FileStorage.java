package com.busreservationsystem.interfaces;

import java.util.ArrayList;

public interface FileStorage {
	public abstract void store();

	default ArrayList<String> load(String name) {
		return null;
	}
}
