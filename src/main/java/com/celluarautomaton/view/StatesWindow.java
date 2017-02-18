package com.celluarautomaton.view;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import org.apache.log4j.Logger;

import com.celluarautomaton.CelluarAutomaton;
import com.celluarautomaton.statemodule.State;
import com.celluarautomaton.statemodule.StateModule;
import com.celluarautomaton.utils.ColorUtil;

public class StatesWindow extends VBox {
	private static final Logger logger = Logger.getLogger(StatesWindow.class);
	
	public StatesWindow() {
		setAlignment(Pos.TOP_CENTER);
		getStyleClass().add("container");
		//setPrefSize(300, 180);
		
		final StateModule stateModule = CelluarAutomaton.getInstance().getStateModule();
		final List<State> states = stateModule.getStates();
		final Color[] colors = ColorUtil.getColors(stateModule.getColors());
				
		final VBox statesBox = new VBox();
		statesBox.getStyleClass().add("container");
		
		for(int i = 0; i < states.size(); i++) {
			final HBox stateBox = new HBox();
			stateBox.setAlignment(Pos.CENTER_LEFT);
			stateBox.getStyleClass().add("spacing");
			
			final Label stateIdLabel = new Label(String.valueOf(i));
			final Label stateNameLabel = new Label(states.get(i).getName());
			stateNameLabel.setPrefWidth(100);
			
			final State state = states.get(i);
			
			final ColorPicker colorPicker = new ColorPicker();
			colorPicker.setPrefWidth(150);
		    colorPicker.setValue(colors[i]);
		    colorPicker.setOnAction(new EventHandler<ActionEvent>() {
	            public void handle(ActionEvent e) {
	            	com.celluarautomaton.statemodule.Color color = ColorUtil.getColor(colorPicker.getValue());
	            	state.setColor(color);
	            	logger.info("state:" + state + " colorPicker.getValue():" + colorPicker.getValue() + " color:" + color);
	            	
	            	stateModule.notiftStatesChangedListeners();
	            }
	        });
		    
		    stateBox.getChildren().addAll(stateIdLabel, stateNameLabel, colorPicker);
		    
		    statesBox.getChildren().add(stateBox);
		}
		
	    HBox container = new HBox();
		container.getChildren().addAll(statesBox);
		
		BorderedTitledPane pane = new BorderedTitledPane("States", container);
		
		getChildren().addAll(pane);
		
		
	}
}
