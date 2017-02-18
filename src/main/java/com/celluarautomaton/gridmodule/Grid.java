package com.celluarautomaton.gridmodule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.celluarautomaton.rulemodule.Rule;
import com.celluarautomaton.statemodule.State;

public class Grid {
	//private static final Logger logger = Logger.getLogger(Grid.class);
	
	private HashMap<Point, Cell> hashMap = null;
	private Point key = null;
	
	private final GridStats gridStats;
	
	public Grid() {
		hashMap = new HashMap<Point, Cell>();
		key = new Point();
		gridStats = new GridStats();
	}
	
	public GridStats getGridStats() {
		return gridStats;
	}
	
	public Cell getCell(Point point) {
		return hashMap.getOrDefault(point, null);
	}
	
	public void clear() {
		gridStats.reset();
		hashMap.clear();
	}
	
	public void setCell(Point point, State state) {
		setCell(point.getX(), point.getY(), state);
	}
	
	public void setCell(int x, int y, State state) {
		key.setX(x);
		key.setY(y);
		key.calculateHash();
	
		if(hashMap.containsKey(key)) {
			Cell cell = hashMap.get(key);
			cell.setState(state);
		} else if(state != State.EMPTY) {
			//logger.info("LLLL:" + key);
			Point point = new Point(x, y);
			Cell cell = new Cell(point);
			//cell.setPoint(key);
			cell.setState(state);
			hashMap.put(point, cell);
		}
	}
	
	public int getCellCount() {
		return hashMap.size();
	}
	
	public Cell getCell(int x, int y) {
		key.setX(x);
		key.setY(y);
		return getCell(key);
	}
	
	public List<Cell> getCells(){
		return Collections.list(Collections.enumeration(hashMap.values()));
	}
	
	public void calculateActiveCells() {
		List<Cell> cells = Collections.list(Collections.enumeration(hashMap.values()));
		for(Cell cell : cells) {
			if(cell.getState() == State.EMPTY) continue;
			
			final Cell[] neighbors = cell.getNeighbors();
			for(int i = 0; i < neighbors.length; i++) {
				Cell neighborCell = neighbors[i];
				if(neighborCell == null) {
					Point neighborPoint = cell.getNeighborPoint(i);
					neighborCell = getCell(neighborPoint);
					if(neighborCell == null) {
						Point neightborPoint = neighborPoint.clone();
						neighborCell = new Cell(neightborPoint);
						//neighborCell.setPoint(neighborPoint);
						hashMap.put(neightborPoint, neighborCell);
					}
				}
				
				neighbors[i] = neighborCell;
				neighborCell.getNeighbors()[24-i] = cell;
			}
		}
		
		gridStats.setActiveCellCount(hashMap.size());
	}
	
	public void removeInactiveCells() {
		List<Cell> cells = Collections.list(Collections.enumeration(hashMap.values()));
		for(Cell cell : cells) {
			if(cell.getState() != State.EMPTY) continue;
			
			final Cell[] neighbors = cell.getNeighbors();
			boolean isAllNeighborsEmpty = true;
			for(int i = 0; i < neighbors.length; i++) {
				Cell neighborCell = neighbors[i];
				if(neighborCell != null && neighborCell.getState() != State.EMPTY) {
					isAllNeighborsEmpty = false;
					break;
				}
			}
			
			//logger.info("isAllNeighborsEmpty:" + isAllNeighborsEmpty + " cell:" + cell);
			
			if(isAllNeighborsEmpty) {
				for(int i = 0; i < neighbors.length; i++) {
					Cell neighborCell = neighbors[i];
					if(neighborCell != null) {
						neighborCell.getNeighbors()[24-i] = null;
						neighbors[i] = null;
					}
				}
				
				hashMap.remove(cell.getPoint());
				//cellPool.put(cell);
			}
			
		}
	}
	
	public void calculatePatternValues() {
		for(Cell cell : hashMap.values()) {
			cell.calculatePatternValue();
		}
	}
	
	public void nextStep(List<Rule> rules) {
		for(Cell cell : hashMap.values()) {
			for(Rule rule : rules) {
				//if(rule.isChecked() && rule.matches(cell.getPatternValue())) {
				if(rule.isChecked() && rule.matches(cell.getPattern())) {
					cell.setNextState(rule.getEndState());
					break;
				}
			}
		}
	}
	
	public void swapNextState() {
		for(Cell cell : hashMap.values()) {
			cell.swapNextState();
		}
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Grid size:" + hashMap.size());
		stringBuilder.append('\n');
		for(Entry<Point, Cell> entry : hashMap.entrySet()) {
			final Cell cell = entry.getValue();
			stringBuilder.append(cell.toString());
			stringBuilder.append('\n');
		}
		return stringBuilder.toString();
	}
	
	public List<Cell> getActiveCells() {
		List<Cell> activeCells = new ArrayList<Cell>();
		
		calculateActiveCells();
		removeInactiveCells();
		
		for(Cell cell : getCells()) {
			if(cell.getState() != State.EMPTY) {
				Cell clonedCell = new Cell(cell.getPoint().getX(), cell.getPoint().getY(), cell.getState());
				activeCells.add(clonedCell);
			}
		}
		return activeCells;
	}
	
	public void setActiveCells(List<Cell> activeCells) {
		clear();
		
		for(Cell cell : activeCells) {
			setCell(cell.getPoint().getX(), cell.getPoint().getY(), cell.getState());
		}
	}
	
}
