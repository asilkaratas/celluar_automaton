package com.celluarautomaton.view;

import java.util.List;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import org.apache.log4j.Logger;

import com.celluarautomaton.CelluarAutomaton;
import com.celluarautomaton.gridmodule.Cell;
import com.celluarautomaton.gridmodule.GridModule;
import com.celluarautomaton.gridmodule.Point;
import com.celluarautomaton.gridmodule.listener.UpdatedListener;
import com.celluarautomaton.statemodule.State;
import com.celluarautomaton.statemodule.StateModule;
import com.celluarautomaton.statemodule.listener.StatesChangedListener;
import com.celluarautomaton.utils.ColorUtil;

public class GridView extends StackPane {
	private static final Logger logger = Logger.getLogger(GridView.class);
	
	private final CelluarAutomaton application;
	private GridModule gridModule = null;
	private StateModule stateModule = null;
	private Canvas canvas = null;
	private GraphicsContext graphicsContext = null;
	private int borderSize = 1;
	private int halfCanvasWidth = 300;
	private int halfCanvasHeight = 300;
	
	private Point2D clickedPoint;
	
	private StackPane canvasContainer;
	
	public GridView(final CelluarAutomaton application) {
		this.application = application;
		this.stateModule = application.getStateModule();
		this.gridModule = application.getGridModule();
		clickedPoint = new Point2D(0, 0);
		
		gridModule.setGridPoint(new Point2D(halfCanvasHeight, halfCanvasHeight));
		
		canvas = new ResizableCanvas();
		graphicsContext = canvas.getGraphicsContext2D();
		canvas.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				Point2D point = calculatePoint(e.getX(), e.getY());
				logger.info("MousePressed x:" + point.getX() + " y:" + point.getY());
				if(application.isDraggingGrid()) {
					clickedPoint = point;
				} else {
					setCell(point);
				}
			}
		});
		canvas.setOnMouseDragged(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				//logger.info("MouseDragged");
				Point2D point = calculatePoint(e.getX(), e.getY());
				if(application.isDraggingGrid()) {
					setGridPoint(point);
				} else {
					setCell(point);
				}
			}
		});
		
		canvasContainer= new StackPane();
		canvasContainer.setMinSize(0, 0);
		canvasContainer.setAlignment(Pos.CENTER);
		
		giveCanvasBack();
		
		
		BorderedTitledPane pane = new BorderedTitledPane("Grid", canvasContainer);
		getChildren().add(pane);
		
		gridModule.addUpdatedListener(new UpdatedListener() {
			
			@Override
			public void onUpdated() {
				onUpdate();
			}
		});
		
		application.getStateModule().addStatesChangedListener(new StatesChangedListener() {
			
			@Override
			public void onStatesChanged() {
				onUpdate();
			}
		});
		
		InvalidationListener listener = new InvalidationListener() {
			
			@Override
			public void invalidated(Observable arg0) {
				updateUI();  
			}
		};
				
	
		canvas.widthProperty().addListener(listener);
		canvas.heightProperty().addListener(listener);
		
		
		updateUI();
	}
	
	public Canvas takeCanvas() {
		return canvas;
	}
	
	public void giveCanvasBack() {
		canvas.widthProperty().bind(canvasContainer.widthProperty().subtract(20));
		canvas.heightProperty().bind(canvasContainer.heightProperty().subtract(20));
		if(!canvasContainer.getChildren().contains(canvas)) {
			canvasContainer.getChildren().add(canvas);
		}
	}
	
	private Point2D calculatePoint(double x, double y) {
		return new Point2D(x, y);
	}
	
	private void setGridPoint(Point2D point) {
		Point2D deltaPoint = clickedPoint.subtract(point);
		clickedPoint = point;
		gridModule.setGridPoint(gridModule.getGridPoint().add(deltaPoint));
		//gridModule.setCenterPoint(gridModule.getCenterPoint().add(deltaPoint));
		
		updateUI();
	}
	
	private void setCell(Point2D point) {
		if(point.getX() >= 0 && point.getX() < canvas.getWidth() && 
				point.getY() >= 0 && point.getY() < canvas.getHeight()) {
		
			Point2D gridPoint = gridModule.getGridPoint();
			int cellSize = getCellSize();
			
			int offsetX = (int) (gridPoint.getX() - halfCanvasWidth);
			int offsetY = (int) (gridPoint.getY() - halfCanvasHeight);
			offsetX = offsetX - offsetX % cellSize;
			offsetY = offsetY - offsetY % cellSize;
			
			int x = (int)(offsetX + point.getX())/cellSize;
			int y = (int)(offsetY + point.getY())/cellSize;
			
			gridModule.getGrid().setCell(x, y, application.getSelectedState());
			
			updateUI();
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
		clear();
		draw();
	}
	
	private void clear() {
		Color[] colors = ColorUtil.getColors(stateModule.getColors());
		int cellSize = getCellSize();
		int filledCellSize = getFilledCellSize();
		
		//logger.info("canvas.getWidth():" + canvas.getWidth());
		
		//graphicsContext.setFill(Color.BLACK);
		graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		graphicsContext.setFill(colors[1]);
		
		for(int y = 0; y < canvas.getWidth(); y++) {
			for(int x = 0; x < canvas.getHeight(); x++) {
				graphicsContext.fillRect(x * cellSize, y * cellSize, filledCellSize, filledCellSize);
			}
		}
	}
	
	private void draw() {
		if(gridModule.isResetted()) {
			gridModule.setResetted(false);
			gridModule.setGridPoint(new Point2D(halfCanvasHeight, halfCanvasHeight));
		}
		
		Color[] colors = ColorUtil.getColors(stateModule.getColors());
		Point2D gridPoint = gridModule.getGridPoint();
		halfCanvasWidth = (int)(canvas.getWidth() / 2);
		halfCanvasHeight = (int)(canvas.getHeight() / 2);
		int offsetX = -(int)gridPoint.getX() + halfCanvasWidth;
		int offsetY = -(int)gridPoint.getY() + halfCanvasHeight;
		int cellSize = getCellSize();
		int filledCellSize = getFilledCellSize();
		
		offsetX = offsetX - offsetX % cellSize;
		offsetY = offsetY - offsetY % cellSize;
		
		//logger.info("canvas.getWidth():" + canvas.getWidth());
		//logger.info("centerPoint:" + gridModule.getCenterPoint());
		//logger.info("gridPoint:" + gridModule.getGridPoint());
		
		List<Cell> cells = gridModule.getGrid().getCells();
		//logger.info("draw cells.len:" + cells.size());
		for(Cell cell : cells) {
			if(cell.getState() != State.EMPTY) {
				//logger.info("draw.cell:" + cell);
				graphicsContext.setFill(colors[cell.getState().getId()]);
				Point point = cell.getPoint();
				int x = point.getX() * cellSize + offsetX;
				int y = point.getY() * cellSize + offsetY;
				if(x >= 0 && x < canvas.getWidth() && y >= 0 && y < canvas.getHeight()) {
					graphicsContext.fillRect(x, y, filledCellSize, filledCellSize);
				}
			}
		}
	}
	
	private int getCellSize() {
		return gridModule.getZoom();
	}
	
	private int getFilledCellSize() {
		return getCellSize() - borderSize;
	}
	
}
