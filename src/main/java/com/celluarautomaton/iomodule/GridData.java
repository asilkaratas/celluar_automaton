package com.celluarautomaton.iomodule;

import java.util.List;

import javafx.geometry.Point2D;

import com.celluarautomaton.gridmodule.Cell;

public class GridData {
	
	private List<Cell> cells;
	private int zoom;
	private Point2D gridPoint;

	public int getZoom() {
		return zoom;
	}

	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

	public Point2D getGridPoint() {
		return gridPoint;
	}

	public void setGridPoint(Point2D gridPoint) {
		this.gridPoint = gridPoint;
	}

	public List<Cell> getCells() {
		return cells;
	}

	public void setCells(List<Cell> cells) {
		this.cells = cells;
	}
	
	
}
