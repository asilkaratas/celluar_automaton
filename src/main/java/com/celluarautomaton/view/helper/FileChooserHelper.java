package com.celluarautomaton.view.helper;

import java.io.File;

import javafx.stage.FileChooser;

public class FileChooserHelper {

	public static FileChooser createStatesFileChooser(String title) {
		return createFileChooser(title, "States", "*.states");
	}
	
	public static FileChooser createGridFileChooser(String title) {
		return createFileChooser(title, "Grid", "*.grid");
	}
	
	public static FileChooser createRulesFileChooser(String title) {
		return createFileChooser(title, "Rules", "*.rules");
	}
	
	public static FileChooser createProjectFileChooser(String title) {
		return createFileChooser(title, "Project", "*.proj");
	}
	
	private static FileChooser createFileChooser(String title, String fileType, String extention) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(title);
        fileChooser.setInitialDirectory(new File("."));  
        
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter(fileType, extention)
        );
		
        return fileChooser;
	}
}
