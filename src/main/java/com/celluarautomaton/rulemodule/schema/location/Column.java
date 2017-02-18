package com.celluarautomaton.rulemodule.schema.location;

import com.celluarautomaton.rulemodule.schema.Location;

public class Column extends Location {

	public Column() {
		super("Column");
	}

	@Override
	public int[] getLocations(int num) {
		int[] locations = new int[5];
		int location = num - 1;
		for(int i = 0; i < 5; i++) {
			locations[i] = location;
			location += 5;
		}
		return locations;
	}
	
	
}
