package com.celluarautomaton.gridmodule;

import com.celluarautomaton.statemodule.State;


public class Cell {
	//private static final Logger logger = Logger.getLogger(Cell.class);
	
	public static final int[] COORDINATES = {
		   -2, -2, -1, -2, 0, -2, 1, -2, 2, -2,
		   -2, -1, -1, -1, 0, -1, 1, -1, 2, -1,
		   -2,  0, -1,  0, 0,  0, 1,  0, 2,  0,
		   -2,  1, -1,  1, 0,  1, 1,  1, 2,  1,
		   -2,  2, -1,  2, 0,  2, 1,  2, 2,  2};
	
	private static final Point neighborPoint = new Point();
	
	private final Point point;
	private final Cell[] neighbors;
	private final Pattern pattern;
	
	private State state = State.EMPTY;
	private State nextState = State.EMPTY;
	
	public Cell(Point point) {
		this.point = point;
		
		neighbors = new Cell[25];
		neighbors[12] = this;
		
		pattern = new Pattern();
	}
	
	public Cell(int x, int y, State state) {
		this(new Point(x, y));
		this.state = state;
	}
	
	public Point getPoint() {
		return point;
	}
	
	public void setPoint(Point point) {
		this.point.setPoint(point);
	}
	
	public Cell[] getNeighbors() {
		return neighbors;
	}
	
	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
		nextState = state;
	}
	
	public State getNextState() {
		return nextState;
	}

	public void setNextState(State nextState) {
		this.nextState = nextState;
	}
	
	public void swapNextState() {
		state = nextState;
	}
	
	public Pattern getPattern() {
		return pattern;
	}
	
	public Point getNeighborPoint(int index) {
		index *= 2;
		neighborPoint.setX(this.point.getX() + COORDINATES[index]);
		neighborPoint.setY(this.point.getY() + COORDINATES[index + 1]);
		neighborPoint.calculateHash();
		return neighborPoint;
	}
	
	public void calculatePatternValue() {
		pattern.reset();
		for(int i = 0; i < 21; ++i) {
			Cell cell = neighbors[i];
			if(cell != null) {
				pattern.setFirstWord(cell.getState().getValue(), i);
			} else {
				pattern.setFirstWord(State.EMPTY.getValue(), i);
			}
		}
		
		for(int i = 0; i < 4; ++i) {
			Cell cell = neighbors[i + 21];
			if(cell != null) {
				pattern.setSecondWord(cell.getState().getValue(), i);
			} else {
				pattern.setSecondWord(State.EMPTY.getValue(), i);
			}
		}
	}
	
	public String toString() {
		return "[Cell state:" + state + " point x:" + point.getX() + " y:" + point.getY();
	}
}
