package com.celluarautomaton.template;

import java.util.ArrayList;
import java.util.List;

import com.celluarautomaton.gridmodule.Cell;
import com.celluarautomaton.rulemodule.Rule;
import com.celluarautomaton.rulemodule.SchemaRule;
import com.celluarautomaton.rulemodule.schema.ComplexSchema;
import com.celluarautomaton.rulemodule.schema.CustomLocationSchema;
import com.celluarautomaton.rulemodule.schema.Location;
import com.celluarautomaton.rulemodule.schema.Quantity;
import com.celluarautomaton.rulemodule.schema.location.CustomLocation;
import com.celluarautomaton.rulemodule.schema.quantity.Exactly;
import com.celluarautomaton.statemodule.State;

public class PalmTreeTemplate implements Template {

	@Override
	public List<Cell> getCells() {
		List<Cell> cells = new ArrayList<>();
		cells.add(new Cell(13, 10, State.ALIVE));
		cells.add(new Cell(13, 11, State.ALIVE));
		
		cells.add(new Cell(14, 10, State.ALIVE));
		cells.add(new Cell(14, 11, State.ALIVE));
		
		cells.add(new Cell(15, 10, State.ALIVE));
		cells.add(new Cell(15, 11, State.ALIVE));
		
		cells.add(new Cell(16, 10, State.ALIVE));
		cells.add(new Cell(16, 11, State.ALIVE));
		return cells;
	}

	@Override
	public List<Rule> getRules() {
		
		CustomLocationSchema schema1 = new CustomLocationSchema();
		schema1.setCustomLocation(CustomLocation.LEFT_DIAGONAL);
		schema1.setState(State.EMPTY);
		schema1.setQuantity(Quantity.EXACTLY);
		schema1.setQuantityNum(1);
		
		SchemaRule rule1 = new SchemaRule("schema rule 1");
		rule1.setStartState(State.ALIVE);
		rule1.setEndState(State.EMPTY);
		rule1.addCustomLocationSchema(schema1);
		
		
		CustomLocationSchema schema2 = new CustomLocationSchema();
		schema2.setCustomLocation(CustomLocation.ABOVE);
		schema2.setState(State.ALIVE);
		schema2.setQuantity(Quantity.AT_LEAST);
		schema2.setQuantityNum(4);
		
		SchemaRule rule2 = new SchemaRule("schema rule 2");
		rule2.setStartState(State.EMPTY);
		rule2.setEndState(State.ALIVE);
		rule2.addCustomLocationSchema(schema2);
		
		
		List<Rule> rules = new ArrayList<Rule>();
		rules.add(rule1);
		rules.add(rule2);
		return rules;
	}

	@Override
	public String getName() {
		return "PalmTree";
	}

}
