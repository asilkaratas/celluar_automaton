package com.celluarautomaton.rulemodule.schema.location;

import com.celluarautomaton.rulemodule.schema.Location;

public class Row extends Location {

	public Row() {
		super("Row");
	}

	@Override
	public int[] getLocations(int num) {
		int[] locations = new int[5];
		int location = 5 * (num - 1);
		for(int i = 0; i < 5; i++) {
			locations[i] = location;
			location ++;
		}
		return locations;
	}
}
