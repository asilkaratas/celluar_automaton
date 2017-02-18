package com.celluarautomaton.iomodule;

import java.util.ArrayList;
import java.util.List;

import com.celluarautomaton.CelluarAutomaton;
import com.celluarautomaton.gridmodule.Cell;
import com.celluarautomaton.gridmodule.Grid;
import com.celluarautomaton.gridmodule.GridModule;
import com.celluarautomaton.rulemodule.Rule;
import com.celluarautomaton.statemodule.State;

public class CheckPoint  {
	
	private final CelluarAutomaton application;
	
	private final List<Cell> activeCells;
	private final List<Rule> rules;
	
	
	public CheckPoint(CelluarAutomaton application) {
		this.application = application;
		activeCells = new ArrayList<Cell>();
		rules = new ArrayList<Rule>();
	}

	public void save() {
		Grid grid = application.getGridModule().getGrid();
		grid.calculateActiveCells();
		grid.removeInactiveCells();
		
		activeCells.clear();
		
		for(Cell cell : grid.getCells()) {
			if(cell.getState() != State.EMPTY) {
				Cell clonedCell = new Cell(cell.getPoint().getX(), cell.getPoint().getY(), cell.getState());
				activeCells.add(clonedCell);
			}
		}
		
	}
	
	public void restore() {
		GridModule gridModule = application.getGridModule();
		Grid grid = application.getGridModule().getGrid();
		
		gridModule.clearGrid();
		
		for(Cell cell : activeCells) {
			grid.setCell(cell.getPoint().getX(), cell.getPoint().getY(), cell.getState());
		}
		
		grid.calculateActiveCells();
		gridModule.notifyUpdatedListeners();
		
	}
}
