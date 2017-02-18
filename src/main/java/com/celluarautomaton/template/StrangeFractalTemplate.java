package com.celluarautomaton.template;

import java.util.ArrayList;
import java.util.List;

import com.celluarautomaton.gridmodule.Cell;
import com.celluarautomaton.rulemodule.Rule;
import com.celluarautomaton.rulemodule.SchemaRule;
import com.celluarautomaton.rulemodule.schema.ComplexSchema;
import com.celluarautomaton.rulemodule.schema.Location;
import com.celluarautomaton.rulemodule.schema.Quantity;
import com.celluarautomaton.statemodule.State;

public class StrangeFractalTemplate implements Template {

	@Override
	public List<Cell> getCells() {
		List<Cell> cells = new ArrayList<Cell>();
		
		cells.add(new Cell(15, 5, State.ALIVE));
		cells.add(new Cell(16, 5, State.ALIVE));
		
		return cells;
	}

	@Override
	public List<Rule> getRules() {
		List<Rule> rules = new ArrayList<Rule>();
		
		ComplexSchema schema = new ComplexSchema();
		schema.setLocation(Location.ROW);
		schema.setLocationNum(2);
		schema.setQuantity(Quantity.EXACTLY);
		schema.setQuantityNum(1);
		schema.setState(State.ALIVE);
		
		SchemaRule rule = new SchemaRule("schema rule 1");
		rule.setStartState(State.EMPTY);
		rule.setEndState(State.ALIVE);
		rule.addComplexSchema(schema);
		
		
		ComplexSchema schema2 = new ComplexSchema();
		schema2.setLocation(Location.ROW);
		schema2.setLocationNum(4);
		schema2.setQuantity(Quantity.EXACTLY);
		schema2.setQuantityNum(1);
		schema2.setState(State.ALIVE);
		
		SchemaRule rule2 = new SchemaRule("schema rule 1");
		rule2.setStartState(State.ALIVE);
		rule2.setEndState(State.EMPTY);
		rule2.addComplexSchema(schema);
		
		rules.add(rule);
		rules.add(rule2);
		
		return rules;
	}
	
	@Override
	public String getName() {
		return "StrangeFractal";
	}

}
