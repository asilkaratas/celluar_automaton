package com.celluarautomaton.view;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.apache.log4j.Logger;

import com.celluarautomaton.CelluarAutomaton;
import com.celluarautomaton.rulemodule.SchemaRule;
import com.celluarautomaton.rulemodule.schema.ComplexSchema;
import com.celluarautomaton.rulemodule.schema.CustomLocationSchema;
import com.celluarautomaton.rulemodule.schema.SimpleSchema;
import com.celluarautomaton.statemodule.State;
import com.celluarautomaton.utils.WindowUtil;
import com.celluarautomaton.view.schemarule.ComplexSchemaRow;
import com.celluarautomaton.view.schemarule.CustomLocationSchemaRow;
import com.celluarautomaton.view.schemarule.SchemaRow;
import com.celluarautomaton.view.schemarule.SimpleSchemaRow;

public class AddSchemaRuleWindow extends VBox {
	private static final Logger logger = Logger.getLogger(AddSchemaRuleWindow.class);
	
	private final CelluarAutomaton application;
	
	private final SchemaRule schemaRule;
	private final SchemaRule originalSchemaRule;
	
	private final List<SchemaRow> simpleSchemaRows;
	private final List<SchemaRow> complexSchemaRows;
	private final List<CustomLocationSchemaRow> customLocationSchemaRows;
	
	private final VBox simpleSchemaContainer;
	private final VBox complexSchemaContainer;
	private final VBox customSchemaContainer;
	
	private final Button addSimpleSchemaButton;
	private final Button addComplexSchemaButton;
	private final Button addCustomSchemaButton;
	
	private final HBox startStateBox;
	private final HBox endStateBox;
	private final VBox container;
	
	private final TextField nameTextField;
	private final Button addButton;
	private final Button cancelButton;
	private final boolean adding;
	
	public AddSchemaRuleWindow() {
		this(null);
	}
	
	public AddSchemaRuleWindow(SchemaRule originalSchemaRule) {
		adding = (originalSchemaRule == null);
		this.originalSchemaRule = originalSchemaRule;
		this.schemaRule = adding ? new SchemaRule("Schema Rule") : originalSchemaRule.clone();
				
		setAlignment(Pos.TOP_CENTER);
		getStyleClass().add("container");
		setPrefSize(665, 600);
		
		application = CelluarAutomaton.getInstance();
		simpleSchemaRows = new ArrayList<SchemaRow>();
		complexSchemaRows = new ArrayList<SchemaRow>();
		customLocationSchemaRows = new ArrayList<CustomLocationSchemaRow>();
		
		simpleSchemaContainer = new VBox();
		simpleSchemaContainer.getStyleClass().add("container");
		
		complexSchemaContainer = new VBox();
		complexSchemaContainer.getStyleClass().add("container");
		
		customSchemaContainer = new VBox();
		customSchemaContainer.getStyleClass().add("container");
		
		Label startStateLabel = new Label("I am ");
		ComboBox<State> startStateComboBox = new ComboBox<>();
		startStateComboBox.getItems().addAll(getStates());
		startStateComboBox.valueProperty().setValue(schemaRule.getStartState());
		startStateComboBox.valueProperty().addListener(new ChangeListener<State>() {

			@Override
			public void changed(ObservableValue<? extends State> arg0, State arg1, State newValue) {
				schemaRule.setStartState(newValue);
			}
		});
		
		startStateBox = new HBox();
		startStateBox.getStyleClass().add("spacing");
		startStateBox.setAlignment(Pos.CENTER_LEFT);
		startStateBox.getChildren().addAll(startStateLabel, startStateComboBox);
		
		
		Label endStateLabel = new Label("then I will be ");
		ComboBox<State> endStateComboBox = new ComboBox<>();
		endStateComboBox.getItems().addAll(getEditorStates());
		endStateComboBox.valueProperty().setValue(schemaRule.getEndState());
		endStateComboBox.valueProperty().addListener(new ChangeListener<State>() {

			@Override
			public void changed(ObservableValue<? extends State> arg0, State arg1, State newValue) {
				schemaRule.setEndState(newValue);
			}
		});
		
		endStateBox = new HBox();
		endStateBox.getStyleClass().add("spacing");
		endStateBox.setAlignment(Pos.CENTER_LEFT);
		endStateBox.getChildren().addAll(endStateLabel, endStateComboBox);
		
		//Simple Schema
		BorderedTitledPane simpleSchemaPane = new BorderedTitledPane("Simple Schemas", simpleSchemaContainer);
		
		addSimpleSchemaButton = new Button("Add");
		addSimpleSchemaButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				addSimpleSchema();
			}
		});
		
		//Complex Schema
		BorderedTitledPane complexSchemaPane = new BorderedTitledPane("Complex Schemas", complexSchemaContainer);
		
		addComplexSchemaButton = new Button("Add");
		addComplexSchemaButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				addComplexSchema();
			}
		});
		
		//Custom Schema
		BorderedTitledPane customSchemaPane = new BorderedTitledPane("Custom Schemas", customSchemaContainer);
		
		addCustomSchemaButton = new Button("Add");
		addCustomSchemaButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				addCustomLocationSchema();
			}
		});
		
		nameTextField = new TextField();
		nameTextField.setPrefWidth(250);
		HBox nameTextContainer = new HBox();
		nameTextContainer.getChildren().add(nameTextField);
		BorderedTitledPane nameTextPane = new BorderedTitledPane("Name", nameTextContainer);
		
		
		container = new VBox();
		container.getChildren().addAll(nameTextPane, startStateBox, simpleSchemaPane, complexSchemaPane, customSchemaPane, endStateBox);
		BorderedTitledPane schemaRulePane = new BorderedTitledPane("Schema Rule", container);
		
		addButton = new Button(getAddButtonText());
		addButton.setPrefWidth(60f);
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
		
		
		VBox mainContainer = new VBox();
		mainContainer.setPrefWidth(getPrefWidth());
		mainContainer.setAlignment(Pos.TOP_CENTER);
		mainContainer.getChildren().addAll(schemaRulePane, buttonContainer);
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollPane.setPrefSize(getPrefWidth(), getPrefHeight());
		scrollPane.setContent(mainContainer);
		getChildren().add(scrollPane);
		
		createSchemaRows();
		updateUI();
	}
	
	private void createSchemaRows() {
		for(SimpleSchema simpleSchema : schemaRule.getSimpleSchemas()) {
			SimpleSchemaRow simpleSchemaRow = new SimpleSchemaRow(this, simpleSchema);
			simpleSchemaRows.add(simpleSchemaRow);
		}
		
		for(ComplexSchema complexSchema : schemaRule.getComplexSchemas()) {
			ComplexSchemaRow complexSchemaRow = new ComplexSchemaRow(this, complexSchema);
			complexSchemaRows.add(complexSchemaRow);
		}
		
		for(CustomLocationSchema customLocationSchema : schemaRule.getCustomLocationSchemas()) {
			CustomLocationSchemaRow customLocationSchemaRow = new CustomLocationSchemaRow(this, customLocationSchema);
			customLocationSchemaRows.add(customLocationSchemaRow);
		}
	}
	
	private void updateUI() {
		nameTextField.setText(schemaRule.getName());
		
		simpleSchemaContainer.getChildren().clear();
		simpleSchemaContainer.getChildren().addAll(simpleSchemaRows);
		simpleSchemaContainer.getChildren().add(addSimpleSchemaButton);
		
		complexSchemaContainer.getChildren().clear();
		complexSchemaContainer.getChildren().addAll(complexSchemaRows);
		complexSchemaContainer.getChildren().add(addComplexSchemaButton);
		
		customSchemaContainer.getChildren().clear();
		customSchemaContainer.getChildren().addAll(customLocationSchemaRows);
		customSchemaContainer.getChildren().add(addCustomSchemaButton);
	}
	
	private void addSimpleSchema() {
		SchemaRow schemaRow =  new SimpleSchemaRow(this);
		simpleSchemaRows.add(schemaRow);
		schemaRule.addSimpleSchema((SimpleSchema)schemaRow.getSchema());
		
		updateUI();
	}
	
	private void addComplexSchema() {
		SchemaRow schemaRow =  new ComplexSchemaRow(this);
		complexSchemaRows.add(schemaRow);
		schemaRule.addComplexSchema((ComplexSchema)schemaRow.getSchema());
		
		updateUI();
	}
	
	private void addCustomLocationSchema() {
		CustomLocationSchemaRow schemaRow =  new CustomLocationSchemaRow(this);
		customLocationSchemaRows.add(schemaRow);
		schemaRule.addCustomLocationSchema((CustomLocationSchema)schemaRow.getSchema());
		
		updateUI();
	}
	
	public void removeSchemaRow(SchemaRow schemaRow) {
		if(schemaRow instanceof SimpleSchemaRow) {
			simpleSchemaRows.remove(schemaRow);
			schemaRule.removeSimpleSchema((SimpleSchema)schemaRow.getSchema());
		} else if(schemaRow instanceof ComplexSchemaRow) {
			complexSchemaRows.remove(schemaRow);
			schemaRule.removeComplexSchema((ComplexSchema)schemaRow.getSchema());
		} else {
			customLocationSchemaRows.remove(schemaRow);
			schemaRule.removeCustomLocationSchema((CustomLocationSchema)schemaRow.getSchema());
		}
		updateUI();
	}
	
	public List<State> getStates() {
		return application.getStateModule().getStates();
	}
	
	public List<State> getEditorStates() {
		return application.getStateModule().getEditorStates();
	}
	
	private String getAddButtonText() {
		return adding ? "Add" : "Save";
	}
	
	private void add() {
		String error = schemaRule.hasError();
		if(error != null) {
			WindowUtil.showAlert(error, getScene());
			return;
		}
		
		schemaRule.setName(nameTextField.getText().toString());
		
		if(adding) {
			application.getRuleModule().addRule(schemaRule);
		} else {
			application.getRuleModule().replaceRule(originalSchemaRule, schemaRule);
		}
		
		close();
	}
	
	private void close() {
		Stage stage = (Stage)getScene().getWindow();
		stage.close();
	}

}
