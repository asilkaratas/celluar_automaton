package com.celluarautomaton.gridmodule;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;

import org.apache.log4j.Logger;

import com.celluarautomaton.gridmodule.listener.RunningChangedListener;
import com.celluarautomaton.gridmodule.listener.UpdatedListener;
import com.celluarautomaton.rulemodule.RuleModule;

public class GridModule implements ActionListener {
	private static final Logger logger = Logger.getLogger(GridModule.class);
	
	private final Grid grid;
	private final RuleModule ruleModule;
	
	private final List<RunningChangedListener> runningChangedListeners;
	private final List<UpdatedListener> updatedListeners;
	
	private boolean running;
	private int generation;
	private Thread loopThread;
	private int speed;
	private int zoom;
	private Point2D gridPoint;
	private boolean resetted;
	
	public GridModule(RuleModule ruleModule) {
		this.ruleModule = ruleModule;
		grid = new Grid();
		generation = 0;
		loopThread = null;
		gridPoint = new Point2D(0, 0);
		
		zoom = 15;
		speed = 1000;
		runningChangedListeners = new ArrayList<RunningChangedListener>();
		updatedListeners = new ArrayList<UpdatedListener>();
	}
	
	public void goToStep(int step) {
		setRunning(true);
		long elapsed = System.nanoTime();
		for(int i = 0; i < step; i++) {
			nextStep();
			if(!running) {
				break;
			}
		}
		elapsed = System.nanoTime() - elapsed;
		logger.info("\nelapsed:" + elapsed);
		
		notifyUpdatedListeners();
		
		setRunning(false);
	}
	
	private void nextStep() {
		grid.calculateActiveCells();
		grid.calculatePatternValues();
		grid.nextStep(ruleModule.getRules());
		grid.swapNextState();
		grid.removeInactiveCells();
		generation ++;
	}
	
	private void nextStepWithNotift() {
		long past = System.nanoTime();
		nextStep();
		long now = System.nanoTime();
		logger.info("\nelapsedTime:" + (now - past));
		
		notifyUpdatedListeners();
	}
	
	public void goToNextStep() {
		setRunning(true);
		nextStepWithNotift();
		setRunning(false);
	}
	
	public Grid getGrid() {
		return grid;
	}
	
	public Point2D getGridPoint() {
		return gridPoint;
	}

	public void setGridPoint(Point2D gridPoint) {
		this.gridPoint = gridPoint;
	}
	
	public void start() {
		setRunning(true);
		
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				logger.info("LoopThread starts");
				while(running) {
					nextStepWithNotift();
					
					try {
						Thread.sleep(speed);
					} catch (InterruptedException e) {
						logger.error("Int:" + e.getMessage());
					}
				}
				
				logger.info("LoopThread ends");
			}
		};
		
		loopThread = new Thread(runnable, "LoopThread");
		loopThread.setDaemon(true);
		loopThread.start();
	}
	
	public void stop() {
		setRunning(false);
		if(loopThread != null) {
			loopThread.interrupt();
			loopThread = null;
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		goToNextStep();
	}
	
	public void addUpdatedListener(UpdatedListener listener) {
		updatedListeners.add(listener);
	}
	
	public void removeUpdatedListener(UpdatedListener listener) {
		updatedListeners.remove(listener);
	}
	
	public void notifyUpdatedListeners() {
		for(UpdatedListener listener : updatedListeners) {
			listener.onUpdated();
		}
	}
	
	public void addRunningChangedListener(RunningChangedListener listener) {
		runningChangedListeners.add(listener);
	}
	
	public void removeRunningChangedListener(RunningChangedListener listener) {
		runningChangedListeners.remove(listener);
	}
	
	public void notifyRunningChangedListeners() {
		for(RunningChangedListener listener : runningChangedListeners) {
			listener.onRunningChanged();
		}
	}
	
	public boolean isRunning() {
		return running;
	}
	
	private void setRunning(boolean running) {
		this.running = running;
		notifyRunningChangedListeners();
	}
	
	public int getGeneration() {
		return generation;
	}
	
	public int getZoom() {
		return zoom;
	}
	
	public void setZoom(int zoom) {
		double indexX = gridPoint.getX() / (this.zoom);
		double indexY = gridPoint.getY() / (this.zoom);
		double posX = indexX * (zoom);
		double posY = indexY * (zoom);
		logger.info("zoom:" + zoom + " this.zoom:" + this.zoom + " indexX:" + indexX);
		
		//posX = this.zoom / zoom * gridPoint.getX();
		//posY = this.zoom / zoom * gridPoint.getY();
		
		this.zoom = zoom;
		
		gridPoint = new Point2D(posX, posY);
		
		notifyUpdatedListeners();
	}
	
	public void reset(int zoom, Point2D gridPoint) {
		this.zoom = zoom;
		//this.gridPoint = gridPoint;
		this.resetted = true;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		logger.info("speed:" + speed);
		this.speed = speed;
	}
	
	public void clearGrid() {
		grid.clear();
		generation = 0;
		notifyUpdatedListeners();
	}

	public boolean isResetted() {
		return resetted;
	}

	public void setResetted(boolean resetted) {
		this.resetted = resetted;
	}
	
}
