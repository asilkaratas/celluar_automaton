package com.celluarautomaton.rulemodule.schema;

import com.celluarautomaton.statemodule.State;

public abstract class Schema {
	
	private State state;
	
	public Schema() {
		state = State.EMPTY;
	}
	
	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	
}
