package com.celluarautomaton.view.schemarule;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import com.celluarautomaton.rulemodule.schema.ComplexSchema;
import com.celluarautomaton.rulemodule.schema.Location;
import com.celluarautomaton.rulemodule.schema.Quantity;
import com.celluarautomaton.rulemodule.schema.Schema;
import com.celluarautomaton.statemodule.State;
import com.celluarautomaton.view.AddSchemaRuleWindow;

public class ComplexSchemaRow extends SchemaRow {

	private final ComplexSchema complexSchema;
	private final Button deleteButton;
	
	public ComplexSchemaRow(final AddSchemaRuleWindow addSchemaRuleWindow) {
		this(addSchemaRuleWindow, null);
	}
	
	public ComplexSchemaRow(final AddSchemaRuleWindow addSchemaRuleWindow, ComplexSchema originalComplexSchema) {
		this.complexSchema = originalComplexSchema == null ? new ComplexSchema() : originalComplexSchema;
		
		getStyleClass().add("spacing");
		setAlignment(Pos.CENTER_LEFT);
		
		
		Integer[] numbers = {1, 2, 3, 4, 5};
		
		Label locationLabel = new Label("and in");
		ComboBox<Location> locationComboBox = new ComboBox<>();
		locationComboBox.getItems().addAll(Location.LOCATIONS);
		locationComboBox.valueProperty().set(complexSchema.getLocation());
		locationComboBox.valueProperty().addListener(new ChangeListener<Location>() {

			@Override
			public void changed(ObservableValue<? extends Location> arg0, Location arg1, Location newValue) {
				complexSchema.setLocation(newValue);
			}
		});
		
		ComboBox<Integer> locationNumComboBox = new ComboBox<>();
		locationNumComboBox.getItems().addAll(numbers);
		locationNumComboBox.valueProperty().set(complexSchema.getLocationNum());
		locationNumComboBox.valueProperty().addListener(new ChangeListener<Integer>() {

			@Override
			public void changed(ObservableValue<? extends Integer> arg0, Integer arg1, Integer newValue) {
				complexSchema.setLocationNum(newValue);
			}
		});
		
		
		Label quantityLabel = new Label(" there is ");
		ComboBox<Quantity> quantityComboBox = new ComboBox<>();
		quantityComboBox.getItems().addAll(Quantity.QUANTITIES);
		quantityComboBox.valueProperty().set(complexSchema.getQuantity());
		quantityComboBox.valueProperty().addListener(new ChangeListener<Quantity>() {

			@Override
			public void changed(ObservableValue<? extends Quantity> arg0, Quantity arg1, Quantity newValue) {
				complexSchema.setQuantity(newValue);
			}
		});
		
		ComboBox<Integer> quantityNumComboBox = new ComboBox<>();
		quantityNumComboBox.getItems().addAll(numbers);
		quantityNumComboBox.valueProperty().set(complexSchema.getQuantityNum());
		quantityNumComboBox.valueProperty().addListener(new ChangeListener<Integer>() {

			@Override
			public void changed(ObservableValue<? extends Integer> arg0, Integer arg1, Integer newValue) {
				complexSchema.setQuantityNum(newValue);
			}
		});
		
		
		ComboBox<State> stateComboBox = new ComboBox<>();
		stateComboBox.getItems().addAll(addSchemaRuleWindow.getEditorStates());
		stateComboBox.valueProperty().set(complexSchema.getState());
		stateComboBox.valueProperty().addListener(new ChangeListener<State>() {

			@Override
			public void changed(ObservableValue<? extends State> arg0, State arg1, State newValue) {
				complexSchema.setState(newValue);
			}
		});
		
		deleteButton = new Button("Delete");
		deleteButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				addSchemaRuleWindow.removeSchemaRow(ComplexSchemaRow.this);
			}
		});
		
		getChildren().addAll(locationLabel, locationComboBox, locationNumComboBox, 
				quantityLabel, quantityComboBox, quantityNumComboBox, stateComboBox, deleteButton);
	}

	@Override
	public Schema getSchema() {
		return complexSchema;
	}

	@Override
	public void showDeleteButton(boolean show) {
		deleteButton.setVisible(show);
	}
}
