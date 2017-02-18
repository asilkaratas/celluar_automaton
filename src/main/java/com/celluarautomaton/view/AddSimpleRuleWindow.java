package com.celluarautomaton.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.apache.log4j.Logger;

import com.celluarautomaton.CelluarAutomaton;
import com.celluarautomaton.rulemodule.SimpleRule;
import com.celluarautomaton.statemodule.State;
import com.celluarautomaton.utils.ColorUtil;
import com.celluarautomaton.utils.WindowUtil;

public class AddSimpleRuleWindow extends VBox {
	private static final Logger logger = Logger.getLogger(AddSimpleRuleWindow.class);
	
	private final CelluarAutomaton application;
	
	private final Button addButton;
	private final Button cancelButton;
	private final TextField nameTextField;
	
	private final Canvas canvas;
	private final GraphicsContext graphicsContext;
	
	private final Canvas finalCanvas;
	private final GraphicsContext finalGraphicsContext;
	
	private int borderSize = 1;
	private int cellSize = 20;
	private int filledCellSize = cellSize - borderSize;
	
	private final SimpleRule simpleRule;
	private final SimpleRule originalSimpleRule;
	
	private State selectedState;
	private final boolean adding;
	
	private final javafx.scene.paint.Color[] colors;
	
	public AddSimpleRuleWindow() {
		this(null);
	}
	
	public AddSimpleRuleWindow(SimpleRule originalSimpleRule) {
		this.adding = (originalSimpleRule == null);
		this.originalSimpleRule = originalSimpleRule;
		this.simpleRule = adding ? new SimpleRule("Simple Rule") : originalSimpleRule.clone();
		
		setAlignment(Pos.TOP_CENTER);
		getStyleClass().add("container");
		setPrefSize(300, 400);
		
		application = CelluarAutomaton.getInstance();
		selectedState = application.getStateModule().getStates().get(2);
		
		colors = ColorUtil.getColors(application.getStateModule().getColors());
		
		
		canvas = new Canvas(5 * cellSize, 5 * cellSize);
		graphicsContext = canvas.getGraphicsContext2D();
		canvas.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				//logger.info("MousePressed x:" + e.getX() + " y:" + e.getY());
				setCell(e.getX(), e.getY());
			}
		});
		canvas.setOnMouseDragged(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				//logger.info("MouseDragged");
				setCell(e.getX(), e.getY());
			}
		});
		
		VBox canvasBox = new VBox();
		canvasBox.getChildren().add(canvas);
		BorderedTitledPane canvasPane = new BorderedTitledPane("States", canvasBox);
		
		
		finalCanvas = new Canvas(cellSize, cellSize);
		finalGraphicsContext = finalCanvas.getGraphicsContext2D();
		finalCanvas.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				//logger.info("MousePressed x:" + e.getX() + " y:" + e.getY());
				setFinalCell(e.getX(), e.getY());
			}
		});
		finalCanvas.setOnMouseDragged(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				//logger.info("MouseDragged");
				setFinalCell(e.getX(), e.getY());
			}
		});
		
		VBox finalCanvasBox = new VBox();
		finalCanvasBox.setAlignment(Pos.CENTER);
		finalCanvasBox.setPrefWidth(56);
		finalCanvasBox.getChildren().add(finalCanvas);
		BorderedTitledPane finalCanvasPane = new BorderedTitledPane("Final", finalCanvasBox);
		
		
		Label label = new Label("Selected state:");
		ComboBox<State> statesComboBox = new ComboBox<>();
		statesComboBox.getItems().addAll(application.getStateModule().getStates());
		statesComboBox.valueProperty().setValue(selectedState);
		statesComboBox.valueProperty().addListener(new ChangeListener<State>() {

			@Override
			public void changed(ObservableValue<? extends State> arg0, State arg1, State newValue) {
				selectedState = newValue;
			}
		});
		
		VBox statesBox = new VBox();
		statesBox.setAlignment(Pos.TOP_CENTER);
		statesBox.getStyleClass().add("spacing");
		statesBox.getChildren().addAll(label, statesComboBox);
		
		addButton = new Button(getButtonText());
		addButton.setPrefWidth(60);
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				add();
			}
		});
		
		cancelButton = new Button("Cancel");
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				close();
			}
		});
		
		HBox buttonContainer = new HBox();
		buttonContainer.setSpacing(10);
		buttonContainer.setAlignment(Pos.TOP_CENTER);
		buttonContainer.getChildren().addAll(cancelButton, addButton);
		
		nameTextField = new TextField();
		StackPane nameTextContainer = new StackPane();
		nameTextContainer.getChildren().add(nameTextField);
		BorderedTitledPane nameTextPane = new BorderedTitledPane("Name", nameTextContainer);
		
		HBox row = new HBox();
		row.setSpacing(40f);
		row.getChildren().addAll(canvasPane, finalCanvasPane);
		
		VBox column = new VBox();
		column.getChildren().addAll(nameTextPane, row);
		BorderedTitledPane pane = new BorderedTitledPane("Simple Rule", column);
		
		getChildren().addAll(pane, statesBox, buttonContainer);
		
		updateUI();
	}
	
	private String getButtonText() {
		return adding ? "Add" : "Save";
	}
	
	private void setCell(double xPos, double yPos) {
		if((xPos < 0 || xPos >= canvas.getWidth()) || 
		   (yPos < 0 || yPos >= canvas.getHeight())) {
			return;
		}
		
		int x = (int)Math.floor(xPos/cellSize);
		int y = (int)Math.floor(yPos/cellSize);
		
		int index = y * 5 + x;
		State[] states = simpleRule.getStates();
		states[index] = selectedState;
		
		updateUI();
	}
	
	private void setFinalCell(double xPos, double yPos) {
		if((xPos < 0 || xPos > canvas.getWidth()) || 
		   (yPos < 0 || yPos > canvas.getHeight())) {
			return;
		}
		
		simpleRule.setEndState(selectedState);
		updateUI();
	}
	
	private void updateUI() {
		clear();
		draw();
		nameTextField.setText(simpleRule.getName());
	}
	
	private void clear() {
		graphicsContext.setFill(javafx.scene.paint.Color.BLACK);
		graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		graphicsContext.setFill(colors[1]);
		
		for(int y = 0; y < canvas.getWidth(); y++) {
			for(int x = 0; x < canvas.getHeight(); x++) {
				graphicsContext.fillRect(x * cellSize, y * cellSize, filledCellSize, filledCellSize);
			}
		}
		
		finalGraphicsContext.setFill(javafx.scene.paint.Color.BLACK);
		finalGraphicsContext.clearRect(0, 0, finalCanvas.getWidth(), finalCanvas.getHeight());
		finalGraphicsContext.setFill(colors[1]);
		
		for(int y = 0; y < finalCanvas.getWidth(); y++) {
			for(int x = 0; x < finalCanvas.getHeight(); x++) {
				finalGraphicsContext.fillRect(x * cellSize, y * cellSize, filledCellSize, filledCellSize);
			}
		}
	}
	
	private void draw() {
		
		javafx.scene.paint.Color[] colors = ColorUtil.getColors(application.getStateModule().getColors());
		
		State[] states = simpleRule.getStates();
		for(int y = 0; y < 5; y++) {
			for(int x = 0; x < 5; x++) {
				int index = y * 5 + x;
				State state = states[index];
				
				graphicsContext.setFill(colors[state.getId()]);
				graphicsContext.fillRect(x * cellSize, y * cellSize, filledCellSize, filledCellSize);
			}
		}
		
		finalGraphicsContext.setFill(colors[simpleRule.getEndState().getId()]);
		finalGraphicsContext.fillRect(0, 0, filledCellSize, filledCellSize);
	}
	
	
	private void add() {
		simpleRule.setName(nameTextField.getText().toString());
		
		String error = simpleRule.hasError();
		if(error != null) {
			logger.error(error);
			WindowUtil.showAlert(error, getScene());
			return;
		}
		
		if(adding) {
			application.getRuleModule().addRule(simpleRule);
		} else {
			//originalSimpleRule.setValuesFrom(simpleRule);
			application.getRuleModule().replaceRule(originalSimpleRule, simpleRule);
		}
		
		close();
	}
	
	private void close() {
		Stage stage = (Stage)getScene().getWindow();
		stage.close();
	}

}
