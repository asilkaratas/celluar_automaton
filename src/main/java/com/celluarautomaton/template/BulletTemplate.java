package com.celluarautomaton.template;

import java.util.ArrayList;
import java.util.List;

import com.celluarautomaton.gridmodule.Cell;
import com.celluarautomaton.rulemodule.Rule;
import com.celluarautomaton.rulemodule.SimpleRule;
import com.celluarautomaton.statemodule.State;

public class BulletTemplate implements Template{
	
	public List<Cell> getCells() {
		List<Cell> cells = new ArrayList<>();
		cells.add(new Cell(10, 10, State.ALIVE));
		cells.add(new Cell(11, 10, State.ALIVE));
		cells.add(new Cell(12, 9, State.ALIVE));
		cells.add(new Cell(12, 10, State.ALIVE));
		cells.add(new Cell(12, 11, State.ALIVE));
		cells.add(new Cell(13, 10, State.ALIVE));
		
		cells.add(new Cell(23, 7, State.DEAD));
		cells.add(new Cell(23, 8, State.DEAD));
		cells.add(new Cell(23, 9, State.DEAD));
		cells.add(new Cell(23, 10, State.DEAD));
		cells.add(new Cell(23, 11, State.DEAD));
		cells.add(new Cell(23, 12, State.DEAD));
		cells.add(new Cell(23, 13, State.DEAD));
		return cells;
	}

	public List<Rule> getRules() {
		List<Rule> rules = new ArrayList<>();
		
		SimpleRule rule = new SimpleRule("rule 1");
		rule.setAll(State.IGNORED);
		rule.setEndState(State.EMPTY);
		rule.setState(12, State.ALIVE);
		rule.setState(13, State.ALIVE);
		rule.setState(14, State.ALIVE);
		rule.setState(9, State.ALIVE);
		rule.setState(19, State.ALIVE);
		rules.add(rule);
		
		SimpleRule rule2 = new SimpleRule("rule 2");
		rule2.setAll(State.IGNORED);
		rule2.setEndState(State.ALIVE);
		rule2.setState(11, State.IGNORED);
		rule2.setState(11, State.ALIVE);
		rule2.setState(10, State.ALIVE);
		rule2.setState(15, State.ALIVE);
		rule2.setState(5, State.ALIVE);
		rules.add(rule2);
		
		SimpleRule rule3 = new SimpleRule("rule 3");
		rule3.setAll(State.IGNORED);
		rule3.setEndState(State.EMPTY);
		rule3.setState(12, State.ALIVE);
		rule3.setState(15, State.ALIVE);
		rule3.setState(16, State.ALIVE);
		rule3.setState(17, State.ALIVE);
		rule3.setState(18, State.ALIVE);
		rule3.setState(22, State.ALIVE);
		rules.add(rule3);
		
		SimpleRule rule4 = new SimpleRule("rule 4");
		rule4.setAll(State.IGNORED);
		rule4.setEndState(State.EMPTY);
		rule4.setState(12, State.ALIVE);
		rule4.setState(5, State.ALIVE);
		rule4.setState(6, State.ALIVE);
		rule4.setState(7, State.ALIVE);
		rule4.setState(8, State.ALIVE);
		rule4.setState(2, State.ALIVE);
		rules.add(rule4);
		
		SimpleRule rule5 = new SimpleRule("rule 5");
		rule5.setAll(State.IGNORED);
		rule5.setEndState(State.ALIVE);
		rule5.setState(11, State.ALIVE);
		rule5.setState(12, State.IGNORED);
		rule5.setState(15, State.ALIVE);
		rule5.setState(16, State.ALIVE);
		rule5.setState(17, State.ALIVE);
		rule5.setState(21, State.ALIVE);
		rules.add(rule5);
		
		SimpleRule rule6 = new SimpleRule("rule 6");
		rule6.setAll(State.IGNORED);
		rule6.setEndState(State.ALIVE);
		rule6.setState(12, State.IGNORED);
		rule6.setState(1, State.ALIVE);
		rule6.setState(5, State.ALIVE);
		rule6.setState(6, State.ALIVE);
		rule6.setState(7, State.ALIVE);
		rule6.setState(11, State.ALIVE);
		rules.add(rule6);
		
		return rules;
	}
	
	@Override
	public String getName() {
		return "Bullet";
	}
}
