package com.celluarautomaton.view;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import org.apache.log4j.Logger;

import com.celluarautomaton.gridmodule.GridModule;
import com.celluarautomaton.gridmodule.listener.RunningChangedListener;
import com.celluarautomaton.gridmodule.listener.UpdatedListener;

public class ControlsView extends StackPane implements EventHandler<ActionEvent> {
	private static final Logger logger = Logger.getLogger(ControlsView.class);
	
	private final GridModule gridModule;
	
	private final Button runButton;
	private final Button stopButton;
	
	private final Label generationLabel;
	private final Button nextStepButton;
	
	private final TextField goTextField;
	private final Button goButton;
	private final Label goLabel;
	
	private final Slider zoomSlider;
	private final Label zoomLabel;
	
	private final Slider speedSlider;
	private final Label speedLabel;
	
	public ControlsView(GridModule gm) {
		this.gridModule = gm;
		
		setWidth(200);
		setPrefWidth(200);
		
		//run
		runButton = new Button("Run");
		runButton.setOnAction(this);

		stopButton = new Button("Stop");
		stopButton.setOnAction(this);
		
		HBox runBox = new HBox();
		runBox.getStyleClass().add("spacing");
		runBox.getChildren().addAll(runButton, stopButton);
		
		//nextstep
		generationLabel = new Label(getGenerationString());
		nextStepButton = new Button("Next Step");
		nextStepButton.setOnAction(this);
		
		//go
		goLabel = new Label("Enter Step Number");
		goTextField = new TextField();
		goTextField.setPrefWidth(100);
		
		goButton = new Button("Go");
		goButton.setOnAction(this);
		
		HBox goHBox = new HBox();
		goHBox.getStyleClass().add("spacing");
		goHBox.getChildren().addAll(goTextField, goButton);
		
		VBox goVBox = new VBox();
		goVBox.getStyleClass().add("spacing");
		goVBox.getChildren().addAll(goLabel, goHBox);
		
		
		//zoom
		zoomLabel = new Label("Zoom");
		zoomSlider = new Slider();
		zoomSlider.setMin(2);
		zoomSlider.setMax(30);
		zoomSlider.setBlockIncrement(1);
		zoomSlider.setValue(gridModule.getZoom());
		zoomSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number newValue) {
				gridModule.setZoom(newValue.intValue());
			}
		});
		
		VBox zoomVBox = new VBox();
		zoomVBox.getChildren().addAll(zoomLabel, zoomSlider);
		
		//speed
		speedLabel = new Label("Speed");
		speedSlider = new Slider();
		speedSlider.setMin(30);
		speedSlider.setMax(2000);
		speedSlider.setValue(gridModule.getSpeed());
		speedSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number newValue) {
				gridModule.setSpeed(2030 - newValue.intValue());
			}
		});
		
		VBox speedVBox = new VBox();
		speedVBox.getChildren().addAll(speedLabel, speedSlider);
		
		
		//container
		VBox container = new VBox();
		container.getStyleClass().add("container");
		container.getChildren().addAll(runBox, generationLabel, nextStepButton, goVBox, zoomVBox, speedVBox);
		
		BorderedTitledPane pane = new  BorderedTitledPane("Controls", container);
		getChildren().add(pane);
		
		
		gridModule.addRunningChangedListener(new RunningChangedListener() {
			@Override
			public void onRunningChanged() {
				onUpdate();
			}
		});
		
		gridModule.addUpdatedListener(new UpdatedListener() {
			@Override
			public void onUpdated() {
				onUpdate();
			}
		});
		
		updateUI();
	}
	
	private void onUpdate() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				updateUI();
			}
		});
	}
	
	private int getStep() {
		int step = 0;
		try {
			step = Integer.parseInt(goTextField.getText());
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
		}
		return step;
	}
	
	@Override
	public void handle(ActionEvent event) {
		Object source = event.getSource();
		if(source == runButton) {
			gridModule.start();
		} else if(source == stopButton) {
			gridModule.stop();
		} else if(source == goButton) {
			if(!goTextField.getText().isEmpty()) {
				int step = getStep();
				if(step > 0) {
					goToStep(step);
				}
			}
		} else if(source == nextStepButton) {
			goToNextStep();
		}
	}
	
	private void goToStep(final int step) {
		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				gridModule.goToStep(step);
				return null;
			}
		};
		
		Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();
	}
	
	private void goToNextStep() {
		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				gridModule.goToNextStep();
				return null;
			}
		};
		
		Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();
	}
	
	private void updateUI() {
		boolean running = gridModule.isRunning();
		//logger.info("updateUI running:" + running);
		
		stopButton.setDisable(!running);
		runButton.setDisable(running);
		goLabel.setDisable(running);
		goButton.setDisable(running);
		goTextField.setDisable(running);
		zoomLabel.setDisable(running);
		zoomSlider.setDisable(running);
		speedLabel.setDisable(running);
		speedSlider.setDisable(running);
		nextStepButton.setDisable(running);
		
		generationLabel.setText(getGenerationString());
	}
	
	private String getGenerationString() {
		return "Generation " + gridModule.getGeneration();
	}
	
}
