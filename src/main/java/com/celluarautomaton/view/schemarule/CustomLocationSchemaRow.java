package com.celluarautomaton.view.schemarule;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;

import com.celluarautomaton.rulemodule.schema.CustomLocationSchema;
import com.celluarautomaton.rulemodule.schema.Quantity;
import com.celluarautomaton.rulemodule.schema.Schema;
import com.celluarautomaton.rulemodule.schema.location.CustomLocation;
import com.celluarautomaton.statemodule.State;
import com.celluarautomaton.utils.NumberUtil;
import com.celluarautomaton.view.AddSchemaRuleWindow;

public class CustomLocationSchemaRow extends SchemaRow {
	private static final Logger logger = Logger.getLogger(CustomLocationSchemaRow.class);
	
	private final CustomLocationSchema customLocationSchema;
	private final Button deleteButton;
	
	public CustomLocationSchemaRow(final AddSchemaRuleWindow addSchemaRuleWindow) {
		this(addSchemaRuleWindow, null);
	}
	
	public CustomLocationSchemaRow(final AddSchemaRuleWindow addSchemaRuleWindow, CustomLocationSchema originalCustomLocationSchema) {
		this.customLocationSchema = originalCustomLocationSchema == null ? new CustomLocationSchema() : originalCustomLocationSchema;
		
		getStyleClass().add("spacing");
		setAlignment(Pos.CENTER_LEFT);
		
		Label quantityLabel = new Label(" there is ");
		ComboBox<Quantity> quantityComboBox = new ComboBox<>();
		quantityComboBox.getItems().addAll(Quantity.QUANTITIES);
		quantityComboBox.valueProperty().set(customLocationSchema.getQuantity());
		quantityComboBox.valueProperty().addListener(new ChangeListener<Quantity>() {

			@Override
			public void changed(ObservableValue<? extends Quantity> arg0, Quantity arg1, Quantity newValue) {
				if(newValue != null) {
					customLocationSchema.setQuantity(newValue);
				}
			}
		});
		
		int[] numbers = NumberUtil.createNumbers(customLocationSchema.getCustomLocation().getLocations().length);
		final ComboBox<Integer> quantityNumComboBox = new ComboBox<>();
		quantityNumComboBox.getItems().addAll(ArrayUtils.toObject(numbers));
		quantityNumComboBox.valueProperty().set(customLocationSchema.getQuantityNum());
		quantityNumComboBox.valueProperty().addListener(new ChangeListener<Integer>() {

			@Override
			public void changed(ObservableValue<? extends Integer> arg0, Integer arg1, Integer newValue) {
				logger.info("newValue:" + newValue);
				if(newValue != null) {
					customLocationSchema.setQuantityNum(newValue);
				}
			}
		});
		
		Label customLocationLabel = new Label("and in");
		ComboBox<CustomLocation> customLocationComboBox = new ComboBox<>();
		customLocationComboBox.getItems().addAll(CustomLocation.LOCATIONS);
		customLocationComboBox.valueProperty().set(customLocationSchema.getCustomLocation());
		customLocationComboBox.valueProperty().addListener(new ChangeListener<CustomLocation>() {

			@Override
			public void changed(ObservableValue<? extends CustomLocation> arg0, CustomLocation arg1, CustomLocation newValue) {
				customLocationSchema.setCustomLocation(newValue);
				
				int[] numbers = NumberUtil.createNumbers(newValue.getLocations().length);
				
				quantityNumComboBox.getItems().clear();
				quantityNumComboBox.getItems().addAll(ArrayUtils.toObject(numbers));
				
				customLocationSchema.setQuantityNum(1);
				quantityNumComboBox.valueProperty().set(customLocationSchema.getQuantityNum());
			}
		});
		
		
		ComboBox<State> stateComboBox = new ComboBox<>();
		stateComboBox.getItems().addAll(addSchemaRuleWindow.getEditorStates());
		stateComboBox.valueProperty().set(customLocationSchema.getState());
		stateComboBox.valueProperty().addListener(new ChangeListener<State>() {

			@Override
			public void changed(ObservableValue<? extends State> arg0, State arg1, State newValue) {
				customLocationSchema.setState(newValue);
			}
		});
		
		deleteButton = new Button("Delete");
		deleteButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				addSchemaRuleWindow.removeSchemaRow(CustomLocationSchemaRow.this);
			}
		});
		
		getChildren().addAll(customLocationLabel, customLocationComboBox, 
				quantityLabel, quantityComboBox, quantityNumComboBox, stateComboBox, deleteButton);
	}

	@Override
	public Schema getSchema() {
		return customLocationSchema;
	}

	@Override
	public void showDeleteButton(boolean show) {
		deleteButton.setVisible(show);
	}
}
