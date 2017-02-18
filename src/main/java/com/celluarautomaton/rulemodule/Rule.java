package com.celluarautomaton.rulemodule;

import com.celluarautomaton.gridmodule.Pattern;
import com.celluarautomaton.statemodule.State;


public abstract class Rule {
	private String name;
	private State endState;
	private boolean checked;
	
	public Rule(String name) {
		this.name = name;
		checked = true;
		endState = State.EMPTY;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public State getEndState() {
		return endState;
	}

	public void setEndState(State endState) {
		this.endState = endState;
	}
	
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	public abstract void calculatePatternValue();
	public abstract boolean matches(Pattern pattern);
	
}
