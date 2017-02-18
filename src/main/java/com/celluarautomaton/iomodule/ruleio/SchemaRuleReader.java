package com.celluarautomaton.iomodule.ruleio;

import java.io.BufferedReader;
import java.io.IOException;

import com.celluarautomaton.iomodule.ruleio.schemarule.ComplexSchemaReader;
import com.celluarautomaton.iomodule.ruleio.schemarule.CustomLocationSchemaReader;
import com.celluarautomaton.iomodule.ruleio.schemarule.SimpleSchemaReader;
import com.celluarautomaton.rulemodule.SchemaRule;
import com.celluarautomaton.rulemodule.SimpleRule;
import com.celluarautomaton.rulemodule.schema.ComplexSchema;
import com.celluarautomaton.rulemodule.schema.CustomLocationSchema;
import com.celluarautomaton.rulemodule.schema.SimpleSchema;
import com.celluarautomaton.statemodule.State;
import com.celluarautomaton.view.schemarule.ComplexSchemaRow;

public class SchemaRuleReader implements Reader<SchemaRule> {
	
	public SchemaRule read(BufferedReader reader) throws IOException {
		String name = reader.readLine();
		int startStateId = Integer.parseInt(reader.readLine());
		int endStateId = Integer.parseInt(reader.readLine());
		
		SchemaRule schemaRule = new SchemaRule(name);
		schemaRule.setStartState(State.values()[startStateId]);
		schemaRule.setEndState(State.values()[endStateId]);
		
		final SimpleSchemaReader simpleSchemaReader = new SimpleSchemaReader();
		final ComplexSchemaReader complexSchemaReader = new ComplexSchemaReader();
		final CustomLocationSchemaReader customLocationSchemaReader = new CustomLocationSchemaReader();
		
		String line = null;
		while((line = reader.readLine()) != null && !line.isEmpty()) {
			if(line.equals("SimpleSchema")) {
				SimpleSchema simpleSchema = simpleSchemaReader.read(reader);
				schemaRule.getSimpleSchemas().add(simpleSchema);
			} else if(line.equals("ComplexSchema")) {
				ComplexSchema complexSchema = complexSchemaReader.read(reader);
				schemaRule.getComplexSchemas().add(complexSchema);
			} else if(line.equals("CustomLocationSchema")) {
				CustomLocationSchema customLocationSchema = customLocationSchemaReader.read(reader);
				schemaRule.getCustomLocationSchemas().add(customLocationSchema);
			}
		}
		
		return schemaRule;
	}
}
