package com.celluarautomaton.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AlertWindow extends VBox {
	
	public AlertWindow(String message) {
		
		getStyleClass().add("alert-window");
		setAlignment(Pos.TOP_CENTER);
		setSpacing(10);
		
		Label label = new Label(message);
		
		
		Button button = new Button("Ok");
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				Stage stage = (Stage)getScene().getWindow();
				stage.close();
			}
		});
		
		getChildren().addAll(label, button);
	}
}
