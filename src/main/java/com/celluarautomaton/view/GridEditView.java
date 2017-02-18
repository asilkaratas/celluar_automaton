package com.celluarautomaton.view;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import com.celluarautomaton.CelluarAutomaton;
import com.celluarautomaton.gridmodule.listener.RunningChangedListener;
import com.celluarautomaton.statemodule.State;

public class GridEditView extends VBox {

	private final CelluarAutomaton application;
	
	public GridEditView(final CelluarAutomaton application) {
		this.application = application;
		
		Label label = new Label("Selected state:");
		ComboBox<State> statesComboBox = new ComboBox<>();
		statesComboBox.getItems().addAll(application.getStateModule().getEditorStates());
		statesComboBox.valueProperty().setValue(application.getSelectedState());
		statesComboBox.valueProperty().addListener(new ChangeListener<State>() {

			@Override
			public void changed(ObservableValue<? extends State> arg0, State arg1, State newValue) {
				application.setSelectedState(newValue);
			}
		});
		
		VBox statesBox = new VBox();
		statesBox.getStyleClass().add("spacing");
		statesBox.getChildren().addAll(label, statesComboBox);
		
		//dragging
		CheckBox draggingCheckBox = new CheckBox("Dragging Grid");
		draggingCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean newValue) {
				application.setDraggingGrid(newValue);
			}
		});
		
		VBox draggingBox = new VBox();
		draggingBox.getStyleClass().add("spacing");
		draggingBox.getChildren().addAll(draggingCheckBox);
		
		
		Button clearGridButton = new Button("Clear Grid");
		clearGridButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				clearGrid();
			}
		});
		
		
		final Button restoreCheckPointButton = new Button("Restore Check Point");
		restoreCheckPointButton.setDisable(true);
		restoreCheckPointButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				application.getCheckPoint().restore();
			}
		});
		
		Button saveCheckPointButton = new Button("Save Check Point");
		saveCheckPointButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				restoreCheckPointButton.setDisable(false);
				application.getCheckPoint().save();
			}
		});
		
		
		VBox container = new VBox();
		container.getStyleClass().add("container");
		container.getChildren().addAll(statesBox, draggingBox, clearGridButton, 
									saveCheckPointButton, restoreCheckPointButton);
		
		BorderedTitledPane pane = new  BorderedTitledPane("Grid Edit", container);
		getChildren().add(pane);
		
		application.getGridModule().addRunningChangedListener(new RunningChangedListener() {
			
			@Override
			public void onRunningChanged() {
				onUpdate();
			}
		});
		
		
	}
	
	private void onUpdate() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				updateUI();
			}
		});
	}
	
	
	private void updateUI() {
		setDisable(application.getGridModule().isRunning());
	}
	
	private void clearGrid() {
		application.getGridModule().clearGrid();
	}
	
}
