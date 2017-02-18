package com.celluarautomaton.template;

import java.util.List;

import com.celluarautomaton.gridmodule.Cell;
import com.celluarautomaton.rulemodule.Rule;

public interface Template {

	String getName();
	List<Cell> getCells();
	List<Rule> getRules();
}
