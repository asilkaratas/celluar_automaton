package com.celluarautomaton.iomodule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.celluarautomaton.common.CommonException;
import com.celluarautomaton.common.Message;
import com.celluarautomaton.gridmodule.Cell;
import com.celluarautomaton.iomodule.ruleio.SchemaRuleReader;
import com.celluarautomaton.iomodule.ruleio.SimpleRuleReader;
import com.celluarautomaton.rulemodule.Rule;
import com.celluarautomaton.rulemodule.SchemaRule;
import com.celluarautomaton.rulemodule.SimpleRule;
import com.celluarautomaton.rulemodule.schema.ComplexSchema;
import com.celluarautomaton.rulemodule.schema.CustomLocationSchema;
import com.celluarautomaton.rulemodule.schema.SimpleSchema;
import com.celluarautomaton.statemodule.State;

public class ProjectIO extends AbstractIO <Project> {
	private static final Logger logger = Logger.getLogger(ProjectIO.class);
	
	private final StateIO stateIO;
	private final GridIO gridIO;
	private final RuleIO ruleIO;
	public ProjectIO(StateIO stateIO, GridIO gridIO, RuleIO ruleIO) {
		this.stateIO = stateIO;
		this.gridIO = gridIO;
		this.ruleIO = ruleIO;
	}
	
	@Override
	public List<Project> loadFromFile(String filename) throws CommonException  {
		Project project = new Project();
		List<Project> projects = new ArrayList<Project>();
		projects.add(project);
		
		String line = null;
		BufferedReader reader = null;
		
		try {
			reader = getReader(filename);
			if((line = reader.readLine()) == null) {
				throw new CommonException(Message.FILE_FORMAT_EXCEPTION);
			}
			
			if(!line.startsWith("Project")) {
				throw new CommonException(Message.FILE_FORMAT_EXCEPTION);
			}
			
			line = reader.readLine();
			line = reader.readLine();
			
			List<State> states = stateIO.read(reader);
			project.setStates(states);
			
			line = reader.readLine();
			List<Rule> rules = ruleIO.read(reader);
			project.setRules(rules);
			
			List<GridData> gridDatas = gridIO.read(reader);
			project.setGridDatas(gridDatas);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(e.getMessage());
		} finally {
			if(reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
			}
		}
		
		return projects;
	}
	
	public void write(PrintWriter writer, List<Project> projects) {
		writer.println("Project");
		writer.println();
		writer.println();
		Project project = projects.get(0);
		
		stateIO.write(writer, project.getStates());
		writer.println();
		writer.println();
		ruleIO.write(writer, project.getRules());
		writer.println();
		writer.println();
		gridIO.write(writer, project.getGridDatas());
	}

	@Override
	public void saveToFile(String filename, List<Project> projects) throws CommonException  {
		PrintWriter writer = null;
		try {
			writer = getWriter(filename);
			write(writer, projects);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(e.getMessage());
		} finally {
			if(writer != null) {
				writer.flush();
				writer.close();
			}
		}
	}
	
}
