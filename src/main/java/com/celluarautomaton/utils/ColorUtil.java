package com.celluarautomaton.utils;

import com.celluarautomaton.statemodule.Color;

public class ColorUtil {

	public static javafx.scene.paint.Color[] getColors(Color[] stateColors) {
		
		javafx.scene.paint.Color[] colors = new javafx.scene.paint.Color[stateColors.length];
	
		for(int i = 0; i < stateColors.length; i++) {
			Color stateColor = stateColors[i];
			javafx.scene.paint.Color color = new javafx.scene.paint.Color(stateColor.getRed()/255f, 
					stateColor.getGreen()/255f, stateColor.getBlue()/255f, stateColor.getAlpha()/255f);
			colors[i] = color;
		}
		
		return colors;
	}
	
	public static Color getColor(javafx.scene.paint.Color color) {
		return new Color((int)(color.getRed() * 255), (int)(color.getGreen() * 255), (int)(color.getBlue() * 255));
		
	}
	
}
