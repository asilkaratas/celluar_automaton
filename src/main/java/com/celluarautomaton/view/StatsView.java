package com.celluarautomaton.view;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import com.celluarautomaton.CelluarAutomaton;
import com.celluarautomaton.gridmodule.GridStats;
import com.celluarautomaton.gridmodule.listener.UpdatedListener;

public class StatsView extends VBox {

	private final Label activeCellCountLabel;
	private final GridStats gridStats;
	
	public StatsView(CelluarAutomaton application) {
		gridStats = application.getGridModule().getGrid().getGridStats();
		activeCellCountLabel = new Label();
		
		VBox container = new VBox();
		container.getChildren().addAll(activeCellCountLabel);
		
		
		BorderedTitledPane pane = new  BorderedTitledPane("Stats", container);
		getChildren().add(pane);
		
		application.getGridModule().addUpdatedListener(new UpdatedListener() {
			
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
	
	private void updateUI() {
		
		activeCellCountLabel.setText("ActiveCells: " + gridStats.getActiveCellCount());
	}
}
