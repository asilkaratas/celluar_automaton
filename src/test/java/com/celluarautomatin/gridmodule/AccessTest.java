package com.celluarautomatin.gridmodule;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

public class AccessTest {
	private static final Logger logger = Logger.getLogger(AccessTest.class);
	
	private static class Cell {
		private State state;
		private int stateValue;
		
		public Cell(State state) {
			this.state = state;
			stateValue = state.value;
		}
		
		public State getState() {
			return state;
		}
		
		public int getStateValue() {
			return stateValue;
		}
	}
	
	private static enum State {
		ALIVE(2);
		
		private int value;
		
		private State(int value) {
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}
	}
	
	private List<Cell> createCells(int count) {
		List<Cell> cells = new ArrayList<AccessTest.Cell>();
		
		for(int i = 0; i < count; ++i) {
			cells.add(new Cell(State.ALIVE));
		}
		
		return cells;
	}
	
	//@Test
	public void indirectAccessTest() {
		
		List<Cell> cells = createCells(10000000);
		long started = System.nanoTime();
		for(Cell cell : cells) {
			int value = cell.getState().getValue();
		}
		long elapsed = System.nanoTime() - started;
		
		logger.info("indirectAccessTest:" + elapsed);
	}
	
	//@Test
	public void directAccessTest() {
		
		List<Cell> cells = createCells(10000000);
		long started = System.nanoTime();
		for(Cell cell : cells) {
			int value = cell.getStateValue();
		}
		long elapsed = System.nanoTime() - started;
		
		logger.info("directAccessTest:" + elapsed);
	}
}
