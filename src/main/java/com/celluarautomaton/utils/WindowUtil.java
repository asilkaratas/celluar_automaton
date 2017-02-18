package com.celluarautomaton.utils;

import javafx.scene.Scene;
import javafx.scene.control.Dialogs;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import com.celluarautomaton.Main;
import com.celluarautomaton.view.AlertWindow;

public class WindowUtil {
	
	public static void showModal(VBox root, String title, Scene owner) {
		Scene scene = new Scene(root);
		scene.getStylesheets().add(Main.CSS);
		
		Stage stage = new Stage();
		stage.resizableProperty().set(false);
		stage.setScene(scene);
	    stage.setTitle(title);
	    stage.initStyle(StageStyle.UTILITY);
	    stage.initModality(Modality.WINDOW_MODAL);
	    stage.initOwner(owner.getWindow());
	    stage.show();
	}
	
	public static void showAlert(String message, Scene owner) {
		AlertWindow alertWindow = new AlertWindow(message);
		showModal(alertWindow, "Error", owner);
	}
}
