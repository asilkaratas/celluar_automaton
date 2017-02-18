package com.celluarautomaton.view;

import javafx.scene.canvas.Canvas;

public class ResizableCanvas extends Canvas {

	public ResizableCanvas() {
		
	}
	
	@Override
    public boolean isResizable() {
      return true;
    }
 
    @Override
    public double prefWidth(double height) {
      return getWidth();
    }
 
    @Override
    public double prefHeight(double width) {
      return getHeight();
    }
}
