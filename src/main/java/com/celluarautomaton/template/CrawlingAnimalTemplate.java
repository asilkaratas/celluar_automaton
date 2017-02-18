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

public class CrawlingAnimalTemplate implements Template {

	@Override
	public List<Cell> getCells() {
		List<Cell> cells = new ArrayList<>();
		cells.add(new Cell(10, 10, State.ALIVE));
		cells.add(new Cell(10, 11, State.ALIVE));
		cells.add(new Cell(10, 12, State.ALIVE));
		cells.add(new Cell(10, 13, State.ALIVE));
		cells.add(new Cell(10, 14, State.ALIVE));
		return cells;
	}

	@Override
	public List<Rule> getRules() {
		
		ComplexSchema schema1 = new ComplexSchema();
		schema1.setLocation(Location.COLUMN);
		schema1.setLocationNum(3);
		schema1.setQuantity(Quantity.EXACTLY);
		schema1.setQuantityNum(3);
		schema1.setState(State.ALIVE);
		
		SchemaRule rule1 = new SchemaRule("schema rule 1");
		rule1.setStartState(State.ALIVE);
		rule1.setEndState(State.EMPTY);
		rule1.addComplexSchema(schema1);
		
		
		ComplexSchema schema2 = new ComplexSchema();
		schema2.setLocation(Location.COLUMN);
		schema2.setLocationNum(2);
		schema2.setQuantity(Quantity.EXACTLY);
		schema2.setQuantityNum(3);
		schema2.setState(State.ALIVE);
		
		SchemaRule rule2 = new SchemaRule("schema rule 2");
		rule2.setStartState(State.EMPTY);
		rule2.setEndState(State.ALIVE);
		rule2.addComplexSchema(schema2);
		
		List<Rule> rules = new ArrayList<Rule>();
		rules.add(rule1);
		rules.add(rule2);
		
		return rules;
	}

	@Override
	public String getName() {
		return "CrawlingAnimal";
	}
	
	

}
