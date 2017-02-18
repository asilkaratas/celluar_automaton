package com.celluarautomatin.iomodule;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.celluarautomaton.common.CommonException;
import com.celluarautomaton.iomodule.StateIO;
import com.celluarautomaton.statemodule.State;
import com.celluarautomaton.statemodule.StateModule;

public class StateIOTest {

	private String filename;
	private StateIO stateIO;
	private StateModule stateModule;
	
	@Before
	public void setUp() {
		filename = "file.states";
		stateModule = new StateModule();
		stateIO = new StateIO();
	}
	
	@Test
	public void writeReadTest() throws CommonException {
		stateIO.saveToFile(filename, stateModule.getStates());
		List<State> states = stateIO.loadFromFile(filename);
		
		Assert.assertEquals(4, states.size());
	}
	
}
