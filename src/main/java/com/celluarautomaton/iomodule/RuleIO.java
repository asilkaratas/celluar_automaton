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

public class RuleIO extends AbstractIO <Rule> {
	private static final Logger logger = Logger.getLogger(RuleIO.class);
	
	public List<Rule> read(BufferedReader reader) throws IOException, CommonException {
		final List<Rule> rules = new ArrayList<Rule>();
		final SimpleRuleReader simpleRuleReader = new SimpleRuleReader();
		final SchemaRuleReader schemaRuleReader = new SchemaRuleReader();
		
		String line = null;
		
		if((line = reader.readLine()) == null) {
			throw new CommonException(Message.FILE_FORMAT_EXCEPTION);
		}
		
		if(!line.startsWith("Rules")) {
			throw new CommonException(Message.FILE_FORMAT_EXCEPTION);
		}
		
		if((line = reader.readLine()) == null) {
			throw new CommonException(Message.FILE_FORMAT_EXCEPTION);
		}
		
		if(!line.isEmpty()) {
			throw new CommonException(Message.FILE_FORMAT_EXCEPTION);
		}
		
		while((line = reader.readLine()) != null && !line.isEmpty()) {
			if(line.equals("SimpleRule")) {
				SimpleRule simpleRule = simpleRuleReader.read(reader);
				rules.add(simpleRule);
				
			} else if(line.equals("SchemaRule")) {
				SchemaRule schemaRule = schemaRuleReader.read(reader);
				rules.add(schemaRule);
			}
			
		}
		
		return rules;
	}
	
	@Override
	public List<Rule> loadFromFile(String filename) throws CommonException  {
		List<Rule> rules = null;
 		String line = null;
		BufferedReader reader = null;
		
		try {
			reader = getReader(filename);
			
			rules = read(reader);
			
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
		
		return rules;
	}
	
	public void write(PrintWriter writer, List<Rule> rules) {
		writer.println("Rules");
		for(Rule rule : rules) {
			writer.println();
			if(rule instanceof SimpleRule) {
				writer.println("SimpleRule");
				SimpleRule simpleRule = (SimpleRule)rule;
				writer.println(simpleRule.getName());
				writer.println(simpleRule.getEndState().getId());
				int len = simpleRule.getStates().length;
				for(int i = 0; i < len; ++i) {
					writer.print(simpleRule.getStates()[i].getId()); 
					if(i != len -1) {
						writer.print(" ");
					}
				}
				writer.println();
			} else {
				writer.println("SchemaRule");
				SchemaRule schemaRule = (SchemaRule)rule;
				writer.println(schemaRule.getName());
				writer.println(schemaRule.getStartState().getId());
				writer.println(schemaRule.getEndState().getId());
				
				int len = schemaRule.getSimpleSchemas().size();
				for(int i = 0; i < len; ++i) {
					writer.println("SimpleSchema");
					SimpleSchema simpleSchema = schemaRule.getSimpleSchemas().get(i);
					writer.println(simpleSchema.getRow());
					writer.println(simpleSchema.getColumn());
					writer.println(simpleSchema.getState().getId());
				}
				
				len = schemaRule.getComplexSchemas().size();
				for(int i = 0; i < len; ++i) {
					writer.println("ComplexSchema");
					ComplexSchema complexSchema = schemaRule.getComplexSchemas().get(i);
					writer.println(complexSchema.getLocation().getName());
					writer.println(complexSchema.getLocationNum());
					writer.println(complexSchema.getQuantity().getName());
					writer.println(complexSchema.getQuantityNum());
					writer.println(complexSchema.getState().getId());
				}
				
				len = schemaRule.getCustomLocationSchemas().size();
				for(int i = 0; i < len; ++i) {
					writer.println("CustomLocationSchema");
					CustomLocationSchema customLocationSchema = schemaRule.getCustomLocationSchemas().get(i);
					writer.println(customLocationSchema.getCustomLocation().getName());
					writer.println(customLocationSchema.getQuantity().getName());
					writer.println(customLocationSchema.getQuantityNum());
					writer.println(customLocationSchema.getState().getId());
				}
			}
		}
	}

	@Override
	public void saveToFile(String filename, List<Rule> rules) throws CommonException  {
		PrintWriter writer = null;
		try {
			writer = getWriter(filename);
			write(writer, rules);
			
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
