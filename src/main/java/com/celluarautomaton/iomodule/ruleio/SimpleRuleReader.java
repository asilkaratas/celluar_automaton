package com.celluarautomaton.iomodule.ruleio;

import java.io.BufferedReader;
import java.io.IOException;

import com.celluarautomaton.rulemodule.SimpleRule;
import com.celluarautomaton.statemodule.State;

public class SimpleRuleReader implements Reader<SimpleRule> {
	
	public SimpleRule read(BufferedReader reader) throws IOException {
		String name = reader.readLine();
		int stateId = Integer.parseInt(reader.readLine());
		String[] ids = reader.readLine().split(" ");
		reader.readLine();
		
		int[] stateIds = new int[25];
		for(int i = 0; i < 25; ++i) {
			stateIds[i] = Integer.parseInt(ids[i]);
		}
		
		SimpleRule simpleRule = new SimpleRule(name, stateIds);
		simpleRule.setEndState(State.values()[stateId]);
		
		return simpleRule;
	}
}
