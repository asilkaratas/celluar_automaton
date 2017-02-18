package com.celluarautomatin.gridmodule;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.celluarautomaton.gridmodule.Pattern;
import com.celluarautomaton.statemodule.State;

public class PatternTest {
	private static final Logger logger = Logger.getLogger(PatternTest.class);
	
	@Test
	public void firstTest() {
		State[] states = {
				State.EMPTY, State.EMPTY, State.EMPTY, State.EMPTY, State.EMPTY,
				State.EMPTY, State.EMPTY, State.EMPTY, State.EMPTY, State.EMPTY,
				State.EMPTY, State.ALIVE, State.ALIVE, State.EMPTY, State.EMPTY,
				State.EMPTY, State.EMPTY, State.EMPTY, State.EMPTY, State.EMPTY,
				State.EMPTY, State.EMPTY, State.EMPTY, State.EMPTY, State.EMPTY
		};
		Pattern pattern = new Pattern();
		pattern.calculate(states);
		
		State[] ruleStates = {
				State.EMPTY, State.EMPTY, State.EMPTY, State.EMPTY, State.EMPTY,
				State.EMPTY, State.EMPTY, State.EMPTY, State.EMPTY, State.EMPTY,
				State.EMPTY, State.IGNORED, State.ALIVE, State.EMPTY, State.EMPTY,
				State.EMPTY, State.EMPTY, State.EMPTY, State.EMPTY, State.EMPTY,
				State.EMPTY, State.EMPTY, State.EMPTY, State.EMPTY, State.EMPTY
		};
		
		Pattern rulePattern = new Pattern();
		rulePattern.calculate(ruleStates);
		
		logger.info("pattern:\n" + pattern);
		logger.info("rulePattern:\n" + rulePattern);
		
		boolean contains = pattern.contains(rulePattern);
		logger.info("contains:\n" + contains);
	}
}
