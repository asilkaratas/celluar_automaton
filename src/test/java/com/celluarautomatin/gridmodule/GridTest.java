package com.celluarautomatin.gridmodule;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.celluarautomaton.gridmodule.Grid;
import com.celluarautomaton.rulemodule.RuleModule;
import com.celluarautomaton.rulemodule.SimpleRule;
import com.celluarautomaton.statemodule.State;

public class GridTest {
	private static final Logger logger = Logger.getLogger(GridTest.class);
	
	private Grid grid = null;
	private RuleModule ruleModule = null;
	
	@Before
	public void setUp() {
		grid = new Grid();
		ruleModule = new RuleModule();
		
		SimpleRule rule = new SimpleRule("rule1");
		
		rule.setEndState(State.ALIVE);
		rule.setState(12, State.EMPTY);
		rule.setState(11, State.EMPTY);
		ruleModule.addRule(rule);
		
	}
	
	@Test
	public void addCellTest() {
		grid.setCell(10, 10, State.ALIVE);
		
		Assert.assertEquals(1, grid.getCellCount());
	}
	
	@Test
	public void calculateActiveCellsTest() {
		grid.setCell(10, 10, State.ALIVE);
		grid.setCell(10, 11, State.ALIVE);
		grid.calculateActiveCells();
		
		//logger.info(grid.toString());
		
		Assert.assertEquals(30, grid.getCellCount());
	}
	
	@Test
	public void calculateActiveCells2Test() {
		grid.setCell(10, 10, State.ALIVE);
		grid.setCell(10, 12, State.ALIVE);
		grid.calculateActiveCells();
		
		//logger.info(grid.toString());
		
		Assert.assertEquals(35, grid.getCellCount());
	}
	
	@Test
	public void calculateActiveCells3Test() {
		grid.setCell(10, 10, State.ALIVE);
		grid.setCell(10, 17, State.ALIVE);
		grid.calculateActiveCells();
		
		//logger.info(grid.toString());
		
		Assert.assertEquals(50, grid.getCellCount());
	}
	
	@Test
	public void calculateActiveCells4Test() {
		grid.setCell(10, 10, State.ALIVE);
		grid.setCell(10, 14, State.ALIVE);
		grid.calculateActiveCells();
		
		//logger.info(grid.toString());
		
		Assert.assertEquals(45, grid.getCellCount());
	}
	
	@Test
	public void removeInactiveCellsTest() {
		grid.setCell(10, 10, State.ALIVE);
		grid.setCell(10, 14, State.ALIVE);
		grid.calculateActiveCells();
		
		Assert.assertEquals(45, grid.getCellCount());
		
		grid.setCell(10, 14, State.EMPTY);
		grid.removeInactiveCells();
		logger.info(grid.toString());
		
		Assert.assertEquals(25, grid.getCellCount());
	}
	
	@Test
	public void calculatePatternValuesTest() {
		grid.setCell(10, 10, State.ALIVE);
		logger.info(grid.toString());
		
		ruleModule.calculatePatternValues();
		
		grid.calculateActiveCells();
		grid.removeInactiveCells();
		grid.calculatePatternValues();
		grid.nextStep(ruleModule.getRules());
		grid.swapNextState();
		
		
		logger.info(grid.toString());
		
		Assert.assertEquals(25, grid.getCellCount());
	}
}
