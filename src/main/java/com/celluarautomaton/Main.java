package com.celluarautomaton;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.Stage;

import com.celluarautomaton.view.MainScene;

public class Main extends Application {
	
	public static final String CSS = "style.css";
	
	public void start(final Stage primaryStage) throws Exception {
		
        primaryStage.setTitle("Celluar Automaton");
        
        final MainScene mainScene = new MainScene();
        mainScene.getStylesheets().add(CSS);
        
        primaryStage.setWidth(1200);
        primaryStage.setHeight(675);
        
        primaryStage.setScene(mainScene);
		primaryStage.show();
		
		primaryStage.fullScreenProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0,
					Boolean arg1, Boolean newValue) {
				if(CelluarAutomaton.getInstance().isFullscreenRequested()) {
					mainScene.updateLayout(newValue);
					if(!newValue) {
						CelluarAutomaton.getInstance().setFullscreenRequested(false);
					}
				}
				
			}
		});
		
		primaryStage.setMinWidth(primaryStage.getWidth());
        primaryStage.setMinHeight(primaryStage.getHeight());
	}
	
	public static void main(String[] args) {
        launch(args);
    }

}
