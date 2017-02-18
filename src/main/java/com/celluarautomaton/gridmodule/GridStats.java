package com.celluarautomaton.gridmodule;

public class GridStats {

	private int activeCellCount;
	
	public GridStats() {
		reset();
	}
	
	public int getActiveCellCount() {
		return activeCellCount;
	}
	
	public void setActiveCellCount(int activeCellCount) {
		this.activeCellCount = activeCellCount;
	}
	
	public void reset() {
		activeCellCount = 0;
	}
}
