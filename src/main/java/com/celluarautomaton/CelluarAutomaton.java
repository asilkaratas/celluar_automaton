package com.celluarautomaton;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;

import com.celluarautomaton.gridmodule.Cell;
import com.celluarautomaton.gridmodule.GridModule;
import com.celluarautomaton.iomodule.CheckPoint;
import com.celluarautomaton.iomodule.IOModule;
import com.celluarautomaton.rulemodule.Rule;
import com.celluarautomaton.rulemodule.RuleModule;
import com.celluarautomaton.statemodule.State;
import com.celluarautomaton.statemodule.StateModule;
import com.celluarautomaton.template.BulletTemplate;
import com.celluarautomaton.template.StrangeFractalTemplate;
import com.celluarautomaton.template.CrawlingAnimalTemplate;
import com.celluarautomaton.template.PalmTreeTemplate;
import com.celluarautomaton.template.Template;

public class CelluarAutomaton {
	//private static final Logger logger = Logger.getLogger(CelluarAutomaton.class);
	
	private final StateModule stateModule;
	private final RuleModule ruleModule;
	private final GridModule gridModule;
	private final CheckPoint checkPoint;
	private final IOModule ioModule;
	
	private State selectedState;
	private boolean isDraggingGrid;
	private boolean fullscreenRequested;
	
	private static CelluarAutomaton instance = null;
	public static CelluarAutomaton getInstance() {
		if(instance == null) {
			instance = new CelluarAutomaton();
		}
		return instance;
	}
	
	private CelluarAutomaton() {
		stateModule = new StateModule();
		ruleModule = new RuleModule();
		gridModule = new GridModule(ruleModule);
		ioModule = new IOModule(stateModule, gridModule, ruleModule);
		
		checkPoint = new CheckPoint(this);
		
		setSelectedState(stateModule.getEditorStates().get(1));
	}
	
	public void setTemplate(Template template) {
		gridModule.clearGrid();
		ruleModule.getRules().clear();
		
		for(Cell cell : template.getCells()) {
			gridModule.getGrid().setCell(cell.getPoint(), cell.getState());
		}
		
		for(Rule rule : template.getRules()) {
			ruleModule.addRule(rule);
		}
		
		gridModule.reset(15, new Point2D(5, 0));
		gridModule.getGrid().calculateActiveCells();
		gridModule.notifyUpdatedListeners();
	}
	
	public StateModule getStateModule() {
		return stateModule;
	}
	
	public RuleModule getRuleModule() {
		return ruleModule;
	}
	
	public GridModule getGridModule() {
		return gridModule;
	}
	
	public IOModule getIOModule() {
		return ioModule;
	}

	public State getSelectedState() {
		return selectedState;
	}

	public void setSelectedState(State selectedState) {
		this.selectedState = selectedState;
	}

	public boolean isDraggingGrid() {
		return isDraggingGrid;
	}

	public void setDraggingGrid(boolean isDraggingGrid) {
		this.isDraggingGrid = isDraggingGrid;
	}
	
	public CheckPoint getCheckPoint() {
		return checkPoint;
	}
	
	public List<Template> getTemplates() {
		List<Template> templates = new ArrayList<Template>();
		templates.add(new CrawlingAnimalTemplate());
		templates.add(new StrangeFractalTemplate());
		templates.add(new BulletTemplate());
		templates.add(new PalmTreeTemplate());
		return templates;
	}

	public boolean isFullscreenRequested() {
		return fullscreenRequested;
	}

	public void setFullscreenRequested(boolean fullscreenRequested) {
		this.fullscreenRequested = fullscreenRequested;
	}
}
