package com.celluarautomaton.view;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import com.celluarautomaton.CelluarAutomaton;
import com.celluarautomaton.common.CommonException;
import com.celluarautomaton.gridmodule.listener.RunningChangedListener;
import com.celluarautomaton.template.Template;
import com.celluarautomaton.utils.WindowUtil;
import com.celluarautomaton.view.helper.FileChooserHelper;

public class MainScene extends Scene {
	private static final Logger logger = Logger.getLogger(MainScene.class);
	
	private final CelluarAutomaton application;
	private final GridView gridView;
	private final MenuBar menuBar;
	
	private final BorderPane mainContainer;
	private final VBox fullscreenContainer;
	
	
	public MainScene() {
		super(new StackPane());
		
		application = CelluarAutomaton.getInstance();
		menuBar = createMenuBar();
		
		StatsView statsView = new StatsView(application);
		GridEditView gridEditView = new GridEditView(application);
		ControlsView controlsView = new ControlsView(application.getGridModule());
		RulesView rulesView = new RulesView(application.getRuleModule(), application.getGridModule());
		gridView = new GridView(application);
		
		VBox vBox = new VBox();
		vBox.getChildren().addAll(controlsView, gridEditView, statsView);
		
		HBox rightView = new HBox();
		rightView.getChildren().addAll(vBox, rulesView);

		
		BorderPane borderPane = new BorderPane();
		//borderPane.setStyle("-fx-background-color: #336699;");
		borderPane.setCenter(gridView);
		borderPane.setRight(rightView);
		
		mainContainer = new BorderPane();
		mainContainer.setTop(menuBar);
		mainContainer.setCenter(borderPane);
		
		fullscreenContainer = new VBox();
		
		
		application.getGridModule().addRunningChangedListener(new RunningChangedListener() {
			
			@Override
			public void onRunningChanged() {
				onUpdate();
			}
		});
		
		
		updateLayout(false);
		updateUI();
	}
	
	public void updateLayout(boolean fullscreen) {
		StackPane root = (StackPane)getRoot();
		root.getChildren().clear();
		
		if(fullscreen) {
			Canvas canvas = gridView.takeCanvas();
			canvas.widthProperty().bind(fullscreenContainer.widthProperty());
			canvas.heightProperty().bind(fullscreenContainer.heightProperty());
			fullscreenContainer.getChildren().add(canvas);
			root.getChildren().add(fullscreenContainer);
		}else {
			gridView.giveCanvasBack();
			root.getChildren().add(mainContainer);
		}
		
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
		menuBar.setDisable(application.getGridModule().isRunning());
	}
	
	private MenuBar createMenuBar() {
		
		MenuItem saveStateList = new MenuItem("Save States");
		saveStateList.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				FileChooser fileChooser = FileChooserHelper.createStatesFileChooser("Save States");
				
				File file = fileChooser.showSaveDialog(getWindow());
				if(file != null) {
					try {
						application.getIOModule().saveStates(file.getAbsolutePath());
					} catch (CommonException e) {
						logger.info("e:" + e.getMessage());
						WindowUtil.showAlert(e.getMessage(), MainScene.this);
					}
				}
			}
		});
		
		MenuItem loadStateList = new MenuItem("Load States");
		loadStateList.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				FileChooser fileChooser = FileChooserHelper.createStatesFileChooser("Load States");
				
				File file = fileChooser.showOpenDialog(getWindow());
				if(file != null) {
					try {
						application.getIOModule().loadStates(file.getAbsolutePath());
					} catch (CommonException e) {
						logger.info("e:" + e.getMessage());
						WindowUtil.showAlert(e.getMessage(), MainScene.this);
					}
				}
			}
		});
		
		MenuItem saveGridState = new MenuItem("Save Grid");
		saveGridState.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				FileChooser fileChooser = FileChooserHelper.createGridFileChooser("Save Grid");
				
				File file = fileChooser.showSaveDialog(getWindow());
				if(file != null) {
					try {
						application.getIOModule().saveGrid(file.getAbsolutePath());
					} catch (CommonException e) {
						logger.info("e:" + e.getMessage());
						WindowUtil.showAlert(e.getMessage(), MainScene.this);
					}
				}
			}
		});
		
		MenuItem loadGridState = new MenuItem("Load Grid");
		loadGridState.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				FileChooser fileChooser = FileChooserHelper.createGridFileChooser("Load Grid");
				
				File file = fileChooser.showOpenDialog(getWindow());
				if(file != null) {
					try {
						application.getIOModule().loadGrid(file.getAbsolutePath());
					} catch (CommonException e) {
						logger.info("e:" + e.getMessage());
						WindowUtil.showAlert(e.getMessage(), MainScene.this);
					}
				}
			}
		});
		
		MenuItem saveRules = new MenuItem("Save Rules");
		saveRules.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				FileChooser fileChooser = FileChooserHelper.createRulesFileChooser("Save Rules");
				
				File file = fileChooser.showSaveDialog(getWindow());
				if(file != null) {
					try {
						application.getIOModule().saveRules(file.getAbsolutePath());
					} catch (CommonException e) {
						logger.info("e:" + e.getMessage());
						WindowUtil.showAlert(e.getMessage(), MainScene.this);
					}
				}
			}
		});
		
		MenuItem loadRules = new MenuItem("Load Rules");
		loadRules.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				FileChooser fileChooser = FileChooserHelper.createRulesFileChooser("Load Rules");
				
				File file = fileChooser.showOpenDialog(getWindow());
				if(file != null) {
					try {
						application.getIOModule().loadRules(file.getAbsolutePath());
					} catch (CommonException e) {
						logger.info("e:" + e.getMessage());
						WindowUtil.showAlert(e.getMessage(), MainScene.this);
					}
				}
			}
		});
		
		MenuItem saveProject = new MenuItem("Save Project");
		saveProject.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				FileChooser fileChooser = FileChooserHelper.createProjectFileChooser("Save Project");
				
				File file = fileChooser.showSaveDialog(getWindow());
				if(file != null) {
					try {
						application.getIOModule().saveProject(file.getAbsolutePath());
					} catch (CommonException e) {
						logger.info("e:" + e.getMessage());
						WindowUtil.showAlert(e.getMessage(), MainScene.this);
					}
				}
			}
		});
		
		MenuItem loadProject = new MenuItem("Load Project");
		loadProject.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				FileChooser fileChooser = FileChooserHelper.createProjectFileChooser("Load Project");
				
				File file = fileChooser.showOpenDialog(getWindow());
				if(file != null) {
					try {
						application.getIOModule().loadProject(file.getAbsolutePath());
					} catch (CommonException e) {
						logger.info("e:" + e.getMessage());
						WindowUtil.showAlert(e.getMessage(), MainScene.this);
					}
				}
			}
		});
		
		MenuItem exit = new MenuItem("Exit");
		exit.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent e) {
				Platform.exit();
			}
		});
		
		Menu fileMenu = new Menu("File");
		fileMenu.getItems().addAll(saveStateList, loadStateList,
				saveGridState, loadGridState,
				saveRules, loadRules, 
				saveProject, loadProject,
				exit);
		
		
		MenuItem showStates = new MenuItem("Edit States");
		showStates.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				StatesWindow stateListWindow = new StatesWindow();
				WindowUtil.showModal(stateListWindow, "Edit States", MainScene.this);
			}
		});
		
		MenuItem fullScreen = new MenuItem("Show Grid View Fullscreen");
		fullScreen.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				application.setFullscreenRequested(true);
				Stage stage = (Stage)getWindow();
				stage.setFullScreen(true);
			}
		});
		Menu viewMenu = new Menu("View");
		viewMenu.getItems().addAll(showStates, fullScreen);
		
		Menu templateMenu = new Menu("Templates");
		List<Template> templates = CelluarAutomaton.getInstance().getTemplates();
		for(final Template template : templates) {
			MenuItem menuItem = new MenuItem(template.getName());
			menuItem.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					CelluarAutomaton.getInstance().setTemplate(template);
				}
			});
			
			templateMenu.getItems().add(menuItem);
		}
		
		final MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(fileMenu, viewMenu, templateMenu);
		
		return menuBar;
	}
}
