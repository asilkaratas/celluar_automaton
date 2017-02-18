package com.celluarautomaton.rulemodule;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.celluarautomaton.gridmodule.Pattern;
import com.celluarautomaton.rulemodule.schema.ComplexSchema;
import com.celluarautomaton.rulemodule.schema.CustomLocationSchema;
import com.celluarautomaton.rulemodule.schema.SimpleSchema;
import com.celluarautomaton.statemodule.State;

public class SchemaRule extends Rule {
	private static final Logger logger = Logger.getLogger(SchemaRule.class);
	
	private State startState;
	private final List<SimpleSchema> simpleSchemas;
	private final List<ComplexSchema> complexSchemas;
	private final List<CustomLocationSchema> customLocationSchemas;
	private final int[] stateValues;
	
	private final Pattern pattern;
	
	
	public SchemaRule(String name) {
		super(name);
		
		startState = State.EMPTY;
		simpleSchemas = new ArrayList<SimpleSchema>();
		complexSchemas = new ArrayList<ComplexSchema>();
		customLocationSchemas = new ArrayList<CustomLocationSchema>();
		stateValues = new int[25];
		pattern = new Pattern();
	}
	
	public void removeAllSchemas() {
		simpleSchemas.clear();
		complexSchemas.clear();
		customLocationSchemas.clear();
	}
	
	public State getStartState() {
		return startState;
	}

	public void setStartState(State endStateValue) {
		this.startState = endStateValue;
	}
	
	public List<SimpleSchema> getSimpleSchemas() {
		return simpleSchemas;
	}
	
	public List<ComplexSchema> getComplexSchemas() {
		return complexSchemas;
	}
	
	public List<CustomLocationSchema> getCustomLocationSchemas() {
		return customLocationSchemas;
	}
	
	public void removeSimpleSchema(SimpleSchema simpleSchema) {
		simpleSchemas.remove(simpleSchema);
	}
	
	public void addSimpleSchema(SimpleSchema simpleSchema) {
		simpleSchemas.add(simpleSchema);
	}
	
	public void removeComplexSchema(ComplexSchema complexSchema) {
		complexSchemas.remove(complexSchema);
	}
	
	public void addComplexSchema(ComplexSchema complexSchema) {
		complexSchemas.add(complexSchema);
	}
	
	public void removeCustomLocationSchema(CustomLocationSchema customLocationSchema) {
		customLocationSchemas.remove(customLocationSchema);
	}
	
	public void addCustomLocationSchema(CustomLocationSchema customLocationSchema) {
		customLocationSchemas.add(customLocationSchema);
	}
	
	@Override
	public void calculatePatternValue() {
		Pattern.resetStates(stateValues);
		stateValues[12] = getStartState().getValue();
		
		for(SimpleSchema simpleSchema : simpleSchemas) {
			stateValues[simpleSchema.getIndex()] = simpleSchema.getState().getValue();
		}
		
		pattern.calculate(stateValues);
		
		for(ComplexSchema complexSchema : complexSchemas) {
			complexSchema.calculateMaskPattern();
		}
		
		for(CustomLocationSchema customLocationSchema : customLocationSchemas) {
			customLocationSchema.calculateMaskPattern();
		}
	}
	
	
	public SchemaRule clone() {
		SchemaRule schemaRule = new SchemaRule(getName());
		schemaRule.setStartState(getStartState());
		schemaRule.setEndState(getEndState());
		
		
		for(SimpleSchema simpleSchema : simpleSchemas) {
			schemaRule.addSimpleSchema(simpleSchema.clone());
		}
		
		for(ComplexSchema complexSchema : complexSchemas) {
			schemaRule.addComplexSchema(complexSchema.clone());
		}
		
		for(CustomLocationSchema customLocationSchema : customLocationSchemas) {
			schemaRule.addCustomLocationSchema(customLocationSchema.clone());
		}
		
		return schemaRule;
	}
	
	private boolean hasDeadOrAliveInSchemas() {
		for(SimpleSchema simpleSchema : simpleSchemas) {
			if(simpleSchema.getState() == State.DEAD || simpleSchema.getState() == State.ALIVE) {
				return true;
			}
		}
		
		for(ComplexSchema complexSchema : complexSchemas) {
			if(complexSchema.getState() == State.DEAD || complexSchema.getState() == State.ALIVE) {
				return true;
			}
		}
		
		for(CustomLocationSchema customLocationSchema : customLocationSchemas) {
			if(customLocationSchema.getState() == State.DEAD || customLocationSchema.getState() == State.ALIVE) {
				return true;
			}
		}
		
		return false;
	}
	
	public String hasError() {
		if(simpleSchemas.isEmpty() && complexSchemas.isEmpty() && customLocationSchemas.isEmpty()) {
			return "Rule does not have a schema!.";
		}
		
		calculatePatternValue();
		if(!pattern.hasAliveOrDead() && !hasDeadOrAliveInSchemas()) {
			return "Start state should be dead/alive or schemas should contain state dead/alive.";
		}
		
		return null;
	}
	
	@Override
	public boolean matches(Pattern pattern) {
		// TODO Auto-generated method stub
		//logger.info("matchesa:" + pattern);
		//logger.info("matchesb:" + this.pattern);
		
		if(!pattern.contains(this.pattern)) {
			return false;
		}
		
		for(ComplexSchema complexSchema : complexSchemas) {
			if(!complexSchema.matches(pattern)) {
				return false;
			}
		}
		
		for(CustomLocationSchema customLocationSchema : customLocationSchemas) {
			if(!customLocationSchema.matches(pattern)) {
				return false;
			}
		}
		
		return true;
	}
	
	
}
