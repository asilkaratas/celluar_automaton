package com.celluarautomaton.rulemodule.schema;

import com.celluarautomaton.gridmodule.Pattern;


public class ComplexSchema extends Schema {
	//private static final Logger logger = Logger.getLogger(ComplexSchema.class);
	
	private Location location;
	private int locationNum;
	private Quantity quantity;
	private int quantityNum;
	
	private final Pattern maskPattern;
	
	public ComplexSchema() {
		location = Location.ROW;
		locationNum = 1;
		
		quantity = Quantity.EXACTLY;
		quantityNum = 1;
		
		maskPattern = new Pattern();
	}
	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	public int getLocationNum() {
		return locationNum;
	}

	public void setLocationNum(int locationNum) {
		this.locationNum = locationNum;
	}

	public Quantity getQuantity() {
		return quantity;
	}

	public void setQuantity(Quantity quantity) {
		this.quantity = quantity;
	}

	public int getQuantityNum() {
		return quantityNum;
	}

	public void setQuantityNum(int quantityNum) {
		this.quantityNum = quantityNum;
	}
	
	public void calculateMaskPattern() {
		maskPattern.reset();
		int[] locations = location.getLocations(locationNum);
		
		for(int location : locations) {
			if(location < 21) {
				maskPattern.setFirstWord(getState().getValue(), location);
			} else {
				maskPattern.setSecondWord(getState().getValue(), location - 21);
			}
		}
	}
	
	public boolean matches(Pattern pattern) {
		int count = pattern.getCount(this.maskPattern);
		return quantity.matches(quantityNum, count);
	}

	public ComplexSchema clone() {
		ComplexSchema complexSchema = new ComplexSchema();
		complexSchema.setLocation(getLocation());
		complexSchema.setLocationNum(getLocationNum());
		complexSchema.setQuantity(getQuantity());
		complexSchema.setQuantityNum(getQuantityNum());
		complexSchema.setState(getState());
		
		
		return complexSchema;
	}
	
}
