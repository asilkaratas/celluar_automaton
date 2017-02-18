package com.celluarautomaton.view.schemarule;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import com.celluarautomaton.rulemodule.schema.Schema;
import com.celluarautomaton.rulemodule.schema.SimpleSchema;
import com.celluarautomaton.statemodule.State;
import com.celluarautomaton.view.AddSchemaRuleWindow;

public class SimpleSchemaRow extends SchemaRow {

	private final SimpleSchema simpleSchema;
	private final Button deleteButton;
	
	public SimpleSchemaRow(final AddSchemaRuleWindow addSchemaRuleWindow) {
		this(addSchemaRuleWindow, null);
	}
	
	public SimpleSchemaRow(final AddSchemaRuleWindow addSchemaRuleWindow, SimpleSchema originalSimpleSchema) {
		this.simpleSchema = originalSimpleSchema == null ? new SimpleSchema() : originalSimpleSchema;
		getStyleClass().add("spacing");
		setAlignment(Pos.CENTER_LEFT);
		
		Integer[] numbers = {1, 2, 3, 4, 5};
		
		Label rowLabel = new Label("and in row ");
		ComboBox<Integer> rowComboBox = new ComboBox<>();
		rowComboBox.getItems().addAll(numbers);
		rowComboBox.valueProperty().set(simpleSchema.getRow());
		rowComboBox.valueProperty().addListener(new ChangeListener<Integer>() {

			@Override
			public void changed(ObservableValue<? extends Integer> arg0, Integer arg1, Integer newValue) {
				simpleSchema.setRow(newValue);
			}
		});
		
		Label columnLabel = new Label(" and column ");
		ComboBox<Integer> columnComboBox = new ComboBox<>();
		columnComboBox.getItems().addAll(numbers);
		columnComboBox.valueProperty().set(simpleSchema.getColumn());
		columnComboBox.valueProperty().addListener(new ChangeListener<Integer>() {

			@Override
			public void changed(ObservableValue<? extends Integer> arg0, Integer arg1, Integer newValue) {
				simpleSchema.setColumn(newValue);
			}
		});
		
		
		Label stateLabel = new Label(" there is a ");
		ComboBox<State> stateComboBox = new ComboBox<>();
		stateComboBox.getItems().addAll(addSchemaRuleWindow.getEditorStates());
		stateComboBox.valueProperty().set(simpleSchema.getState());
		stateComboBox.valueProperty().addListener(new ChangeListener<State>() {

			@Override
			public void changed(ObservableValue<? extends State> arg0, State arg1, State newValue) {
				simpleSchema.setState(newValue);
			}
		});
		
		deleteButton = new Button("Delete");
		deleteButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				addSchemaRuleWindow.removeSchemaRow(SimpleSchemaRow.this);
			}
		});
		
		
		getChildren().addAll(rowLabel, rowComboBox, columnLabel, columnComboBox, stateLabel, stateComboBox, deleteButton);
	}

	@Override
	public Schema getSchema() {
		return simpleSchema;
	}

	@Override
	public void showDeleteButton(boolean show) {
		deleteButton.setVisible(show);
	}
}
