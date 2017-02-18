package com.celluarautomaton.iomodule;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;

import com.celluarautomaton.common.CommonException;
import com.celluarautomaton.gridmodule.Cell;
import com.celluarautomaton.gridmodule.GridModule;
import com.celluarautomaton.rulemodule.Rule;
import com.celluarautomaton.rulemodule.RuleModule;
import com.celluarautomaton.statemodule.State;
import com.celluarautomaton.statemodule.StateModule;

public class IOModule {

	private final StateModule stateModule;
	private final GridModule gridModule;
	private final RuleModule ruleModule;
	
	private final StateIO stateIO;
	private final GridIO gridIO;
	private final RuleIO ruleIO;
	private final ProjectIO projectIO;
	
	public IOModule(StateModule stateModule, GridModule gridModule, RuleModule ruleModule) {
		this.stateModule = stateModule;
		this.gridModule = gridModule;
		this.ruleModule = ruleModule;
		
		stateIO = new StateIO();
		gridIO = new GridIO();
		ruleIO = new RuleIO();
		projectIO = new ProjectIO(stateIO, gridIO, ruleIO);
	}
	
	public void saveStates(String filename) throws CommonException {
		stateIO.saveToFile(filename, stateModule.getStates());
	}
	
	public void loadStates(String filename) throws CommonException  {
		List<State> states = stateIO.loadFromFile(filename);
		if(states.size() > 0) {
			stateModule.setStates(states);
		}
	}
	
	public void saveGrid(String filename) throws CommonException  {
		List<Cell> cells = gridModule.getGrid().getActiveCells();
		GridData gridData = new GridData();
		gridData.setCells(cells);
		gridData.setZoom(gridModule.getZoom());
		gridData.setGridPoint(gridModule.getGridPoint());
		
		List<GridData> gridDatas = new ArrayList<>();
		gridDatas.add(gridData);
		
		gridIO.saveToFile(filename, gridDatas);
	}
	
	public void loadGrid(String filename) throws CommonException {
		List<GridData> gridDatas = gridIO.loadFromFile(filename);
		GridData gridData = gridDatas.get(0);
		
		gridModule.setGridPoint(gridData.getGridPoint());
		gridModule.setZoom(gridData.getZoom());
		gridModule.getGrid().setActiveCells(gridData.getCells());
		gridModule.notifyUpdatedListeners();
	}
	
	public void saveRules(String filename) throws CommonException  {
		List<Rule> rules = ruleModule.getRules();
		ruleIO.saveToFile(filename, rules);
	}
	
	public void loadRules(String filename) throws CommonException {
		List<Rule> rules = ruleIO.loadFromFile(filename);
		ruleModule.setRules(rules);
	}
	
	public void saveProject(String filename) throws CommonException  {
		Project project = new Project();
		project.setStates(stateModule.getStates());
		project.setRules(ruleModule.getRules());
		
		
		GridData gridData = new GridData();
		gridData.setZoom(gridModule.getZoom());
		gridData.setGridPoint(gridModule.getGridPoint());
		gridData.setCells(gridModule.getGrid().getActiveCells());
		List<GridData> gridDatas = new ArrayList<>();
		gridDatas.add(gridData);
		project.setGridDatas(gridDatas);
		
		List<Project> projects = new ArrayList<Project>();
		projects.add(project);
		
		projectIO.saveToFile(filename, projects);
	}
	
	public void loadProject(String filename) throws CommonException {
		List<Project> projects = projectIO.loadFromFile(filename);
		Project project = projects.get(0);
		
		stateModule.setStates(project.getStates());
		ruleModule.setRules(project.getRules());
		
		GridData gridData = project.getGridDatas().get(0);
		
		gridModule.reset(gridData.getZoom(), gridData.getGridPoint());
		gridModule.getGrid().setActiveCells(gridData.getCells());
		gridModule.notifyUpdatedListeners();
	}
}
