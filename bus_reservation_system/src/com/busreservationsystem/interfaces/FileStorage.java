package com.busreservationsystem.interfaces;

import java.util.ArrayList;

public interface FileStorage {
	public abstract void store();
	public abstract ArrayList<String> load(String name);
}
