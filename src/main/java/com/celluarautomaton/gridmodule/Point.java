package com.celluarautomaton.gridmodule;

public class Point {
	private int x;
	private int y;
	private int hash = 0;
	
	public Point() {
		this(0, 0);
	}
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
		
		calculateHash();
	}
	
	public Point clone() {
		return new Point(x, y);
	}
	
	public void setPoint(Point point) {
		this.x = point.x;
		this.y = point.y;
		
		calculateHash();
	}
	
	public void setPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void calculateHash() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		
		hash = result;
	}

	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public int hashCode() {
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "[Point x:" + x + " y:" + y + "]";
	}
}
