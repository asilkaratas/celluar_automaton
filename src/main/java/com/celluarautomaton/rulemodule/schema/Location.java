package com.celluarautomaton.rulemodule.schema;

import com.celluarautomaton.rulemodule.schema.location.Column;
import com.celluarautomaton.rulemodule.schema.location.Row;

public abstract class Location {
	
	public static final Row ROW = new Row();
	public static final Column COLUMN = new Column();
	public static final Location[] LOCATIONS = {ROW, COLUMN};
	
	private String name = null;
	
	public Location(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public abstract int[] getLocations(int num);
	
	public String toString() {
		return name;
	}
	
	public static Location fromName(String name) {
		for(Location location : LOCATIONS) {
			if(location.getName().equals(name)) {
				return location;
			}
		}
		return null;
	}
}
