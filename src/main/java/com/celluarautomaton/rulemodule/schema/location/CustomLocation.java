package com.celluarautomaton.rulemodule.schema.location;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;


public class CustomLocation {
	
	public static final CustomLocation LEFT_DIAGONAL = new CustomLocation("Left Diagonal", new int[]{0, 6, 12, 18, 24});
	public static final CustomLocation RIGHT_DIAGONAL = new CustomLocation("Right Diagonal", new int[]{4, 8, 12, 16, 20});
	public static final CustomLocation OUTHER_RING = new CustomLocation("Outher Ring", new int[]{0, 1, 2, 3, 4, 9, 14, 19, 24, 23, 22, 21, 20, 15, 10, 5});
	public static final CustomLocation INNER_RING = new CustomLocation("Inner Ring", new int[]{6, 7, 8, 13, 18, 17, 16, 11});
	public static final CustomLocation ABOVE = new CustomLocation("Above", new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
	public static final CustomLocation BELOW = new CustomLocation("Below", new int[]{15, 16, 17, 18, 19, 20, 21, 22, 23, 24});
	
	public static final CustomLocation[] LOCATIONS = {LEFT_DIAGONAL, RIGHT_DIAGONAL, OUTHER_RING, INNER_RING, ABOVE, BELOW};
	
	private String name;
	private int[] allLocations;
	
	public CustomLocation(String name, int[] locations) {
		this.name = name;
		
		allLocations = new int[25];
		for(int location : locations) {
			allLocations[location] = 1;
		}
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public int[] getAllLocations() {
		return allLocations;
	}

	public int[] getLocations() {
		List<Integer> locations = new ArrayList<Integer>();
		for(int i = 0; i < 25; ++i) {
			if(allLocations[i] == 1) {
				locations.add(i);
			}
		}
		
		Integer[] array = new Integer[locations.size()];
		locations.toArray(array);
		
		return ArrayUtils.toPrimitive(array);
	}
	
	public String toString() {
		return name;
	}
	
	public static CustomLocation fromName(String name) {
		for(CustomLocation customLocation : LOCATIONS) {
			if(customLocation.getName().equals(name)) {
				return customLocation;
			}
		}
		return null;
	}
}
