package com.celluarautomaton.rulemodule;


import org.apache.log4j.Logger;

import com.celluarautomaton.gridmodule.Pattern;
import com.celluarautomaton.statemodule.State;

public class SimpleRule extends Rule {
	private static final Logger logger = Logger.getLogger(SimpleRule.class);
	
	private final State[] states;
	private final Pattern pattern;
	
	public SimpleRule(String name, int[] stateIds) {
		this(name);
		
		for(int i = 0; i < 25; ++i) {
			states[i] = State.values()[stateIds[i]];
		}
	}
	
	public SimpleRule(String name) {
		super(name);
		
		states = new State[25];
		for(int i = 0; i < 25; ++i) {
			states[i] = State.EMPTY;
		}
		
		pattern = new Pattern();
	}
	
	public void setAll(State state) {
		for(int i = 0; i < 25; ++i) {
			states[i] = state;
		}
	}

	@Override
	public void calculatePatternValue() {
		pattern.calculate(states);
		//logger.info("pattern:" + pattern);
	}
	
	public State[] getStates() {
		return states;
	}
	
	public void setState(int index, State state) {
		if(index >= 0 && index < states.length) {
			states[index] = state;
		}
	}
	
	public SimpleRule clone() {
		SimpleRule simpleRule = new SimpleRule(getName());
		for(int i = 0; i < states.length; i++) {
			simpleRule.states[i] = states[i];
		}
		simpleRule.setEndState(getEndState());
		return simpleRule;
	}
	
	public void setValuesFrom(SimpleRule simpleRule) {
		setName(simpleRule.getName());
		
		for(int i = 0; i < states.length; i++) {
			states[i] = simpleRule.states[i];
		}
		setEndState(simpleRule.getEndState());
		
		calculatePatternValue();
	}
	
	public String hasError() {
		if(getName().isEmpty()) {
			return "Name can not be empty";
		}
		
		if(getEndState() == State.IGNORED) {
			return "End state can not be ignored";
		}
		
		calculatePatternValue();
		//logger.info("pattern:" + pattern);
		
		if(!pattern.hasAliveOrDead()) {
			return "At least one non-empty/non-ignored state is required.";
		}
		
		return null;
	}

	@Override
	public boolean matches(Pattern pattern) {
		return pattern.contains(this.pattern);
	}
	
}
