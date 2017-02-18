package com.celluarautomaton.rulemodule.schema.quantity;

import com.celluarautomaton.rulemodule.schema.Quantity;

public class Exactly extends Quantity {
	
	public Exactly() {
		super("Exactly");
	}

	@Override
	public boolean matches(int quantityNum, int count) {
		return quantityNum == count;
	}
}
