package com.celluarautomaton.view.rules;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import com.celluarautomaton.rulemodule.Rule;

public class RuleCell extends ListCell<Rule> {
	
	private final Label label;
	private final Button removeButton;
	private final Button editButton;
	private final CheckBox checkBox;
	
	private final VBox container;
	
	public RuleCell(final RuleCellCallback callback) {
		setContentDisplay(ContentDisplay.LEFT);
		
		
		label = new Label();
		checkBox = new CheckBox();
		HBox top = new HBox();
		top.setAlignment(Pos.CENTER_LEFT);
		top.getChildren().addAll(checkBox, label);
		
		removeButton = new Button("remove");
		editButton = new Button("edit");
		
		HBox bottom = new HBox();
		bottom.setSpacing(5);
		bottom.setAlignment(Pos.TOP_RIGHT);
		bottom.getChildren().addAll(editButton, removeButton);
		
		//label.setStyle("-fx-background-color: #ffffa0;");
		container = new VBox();
		container.getChildren().addAll(top, bottom);
		
		checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0,
					Boolean arg1, Boolean newValue) {
				getItem().setChecked(newValue);
			}
		});
		
		editButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				callback.onEdit(getItem());
			}
		});
		
		removeButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				callback.onRemove(getItem());
			}
		});
		
		setOnDragDetected(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				if (getItem() == null) {
                    return;
                }
				
				ObservableList<Rule> items = getListView().getItems();
				int index = items.indexOf(getItem());
				
				final ImageView preview = new ImageView(label.snapshot(null, null));

                Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString(String.valueOf(index));
                dragboard.setDragView(preview.getImage());
                dragboard.setContent(content);

                e.consume();
			}
		});
		
		setOnDragOver(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent e) {
				if (e.getGestureSource() != RuleCell.this &&
		                   e.getDragboard().hasString()) {
		                e.acceptTransferModes(TransferMode.MOVE);
		            }

		            e.consume();
			}
		});
		
		setOnDragEntered(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent e) {
				if (e.getGestureSource() != RuleCell.this &&
                        e.getDragboard().hasString()) {
                    setOpacity(0.3);
                }
			}
		});
		
		setOnDragExited(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent e) {
				if (e.getGestureSource() != RuleCell.this &&
                        e.getDragboard().hasString()) {
                    setOpacity(1);
                }
			}
		});
		
		setOnDragDropped(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent e) {
				if (getItem() == null) {
                    return;
                }
				
				 Dragboard dragboard = e.getDragboard();
	             boolean success = false;
	             
	             if (dragboard.hasString()) {
	                    ObservableList<Rule> items = getListView().getItems();
	                    int draggedIndex = Integer.valueOf(dragboard.getString());
	                    int index = items.indexOf(getItem());
	                    
	                    callback.onMove(draggedIndex, index);

	                    success = true;
	                }
	             e.setDropCompleted(success);
	             e.consume();
			}
		});
	}
	
	
	@Override
	protected void updateItem(Rule item, boolean empty) {
		super.updateItem(item, empty);
		
		if (empty || item == null) {
            setGraphic(null);
        } else {
            label.setText(item.getName());
            checkBox.setSelected(item.isChecked());
            setGraphic(container);
        }

	}
	
}
