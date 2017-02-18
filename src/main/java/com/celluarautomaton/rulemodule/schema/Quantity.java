package com.celluarautomaton.rulemodule.schema;

import com.celluarautomaton.rulemodule.schema.quantity.AtLeast;
import com.celluarautomaton.rulemodule.schema.quantity.AtMost;
import com.celluarautomaton.rulemodule.schema.quantity.Exactly;

public abstract class Quantity {
	
	public static final Exactly EXACTLY = new Exactly();
	public static final AtLeast AT_LEAST = new AtLeast();
	public static final AtMost AT_MOST = new AtMost();
	public static final Quantity[] QUANTITIES = {EXACTLY, AT_LEAST, AT_MOST};
	
	private String name = null;
	
	public Quantity(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public abstract boolean matches(int quantityNum, int count);
	
	public String toString() {
		return name;
	}
	
	public static Quantity fromName(String name) {
		for(Quantity quantity : QUANTITIES) {
			if(quantity.getName().equals(name)) {
				return quantity;
			}
		}
		return null;
	}
}
