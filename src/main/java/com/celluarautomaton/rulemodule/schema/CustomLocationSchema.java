package com.celluarautomaton.rulemodule.schema;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;

import com.celluarautomaton.gridmodule.Pattern;
import com.celluarautomaton.rulemodule.SchemaRule;
import com.celluarautomaton.rulemodule.schema.location.CustomLocation;

public class CustomLocationSchema extends Schema {
	private static final Logger logger = Logger.getLogger(CustomLocationSchema.class);
	
	private Quantity quantity;
	private int quantityNum;
	private CustomLocation customLocation;
	private final Pattern maskPattern;
	
	public CustomLocationSchema() {
		customLocation = CustomLocation.LEFT_DIAGONAL;
		quantity = Quantity.EXACTLY;
		quantityNum = 1;
		
		maskPattern = new Pattern();
	}
	
	public CustomLocation getCustomLocation() {
		return customLocation;
	}

	public void setCustomLocation(CustomLocation customLocation) {
		this.customLocation = customLocation;
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
		int[] locations = customLocation.getLocations();
		
		//logger.info("locations:" + ArrayUtils.toString(locations));
		
		for(int location : locations) {
			if(location < 21) {
				maskPattern.setFirstWord(getState().getValue(), location);
			} else {
				maskPattern.setSecondWord(getState().getValue(), location - 21);
			}
		}
	}
	
	public boolean matches(Pattern pattern) {
		//logger.info("patterna:" + pattern);
		//logger.info("patternb:" + maskPattern);
		
		int count = pattern.getCount(this.maskPattern);
		return quantity.matches(quantityNum, count);
	}

	public CustomLocationSchema clone() {
		CustomLocationSchema customLocationSchema = new CustomLocationSchema();
		customLocationSchema.setCustomLocation(getCustomLocation());
		customLocationSchema.setQuantity(getQuantity());
		customLocationSchema.setQuantityNum(getQuantityNum());
		customLocationSchema.setState(getState());
		
		return customLocationSchema;
	}
}
