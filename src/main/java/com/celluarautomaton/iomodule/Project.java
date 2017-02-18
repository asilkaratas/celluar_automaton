package com.celluarautomaton.iomodule;

import java.util.List;

import com.celluarautomaton.gridmodule.Cell;
import com.celluarautomaton.rulemodule.Rule;
import com.celluarautomaton.statemodule.State;

public class Project {
	
	private List<State> states;
	private List<GridData> gridDatas;
	private List<Rule> rules;
	
	public Project() {
		
	}

	public List<State> getStates() {
		return states;
	}

	public void setStates(List<State> states) {
		this.states = states;
	}

	public List<GridData> getGridDatas() {
		return gridDatas;
	}

	public void setGridDatas(List<GridData> gridDatas) {
		this.gridDatas = gridDatas;
	}

	public List<Rule> getRules() {
		return rules;
	}

	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}
	
	
}
