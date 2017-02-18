package com.celluarautomaton.rulemodule.schema.quantity;

import com.celluarautomaton.rulemodule.schema.Quantity;

public class AtLeast extends Quantity {
	
	public AtLeast() {
		super("AtLeast");
	}
	
	@Override
	public boolean matches(int quantityNum, int count) {
		return quantityNum <= count;
	}

}
