package com.celluarautomaton.statemodule;

public enum State {
	IGNORED(0, 0, "Ignored", new Color(0xFF2EC4B6)), 
	EMPTY(1, 1, "Empty", new Color(0xFFFDFFFC)), 
	ALIVE(2, 2, "Alive", new Color(0xFF011627)), 
	DEAD(3, 4, "Dead", new Color(0xFFE71D36));
	
	private int id;
	private int value;
	private String name;
	private Color color;
	
	private State(int id, int value, String name, Color color) {
		this.id = id;
		this.value = value;
		this.name = name;
		this.color = color;
	}
	
	public int getId() {
		return id;
	}
	
	public int getValue() {
		return value;
	}
	
	public String getName() {
		return name;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
}
