package com.celluarautomaton.statemodule;

public class Color {
	private final int argb;
	
	public Color(int argb) {
		this.argb = argb;
	}
	
	public Color(int r, int g, int b) {
		this(r, g, b, 255);
	}
	
	public Color(int r, int g, int b, int a) {
		this((a << 24) | (r << 16) | (g << 8) | b);
	}
	
	public int getArgb() {
		return argb;
	}
	
	public int getRed() {
		return 0xFF & ( argb >> 16);
	}
	
	public int getGreen() {
		return 0xFF & ( argb >> 8);
	}
	
	public int getBlue() {
		return 0xFF & ( argb >> 0);
	}
	
	public int getAlpha() {
		return 0xFF & ( argb >> 24);
	}
	
	@Override
	public String toString() {
		return Integer.toHexString(argb);
	}
}
