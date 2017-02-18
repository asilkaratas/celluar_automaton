package com.celluarautomaton.rulemodule.schema.quantity;

import com.celluarautomaton.rulemodule.schema.Quantity;

public class AtMost extends Quantity {

	public AtMost() {
		super("AtMost");
	}
	
	@Override
	public boolean matches(int quantityNum, int count) {
		return quantityNum >= count;
	}

}
