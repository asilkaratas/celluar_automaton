package com.celluarautomaton.iomodule.ruleio.schemarule;

import java.io.BufferedReader;
import java.io.IOException;

import com.celluarautomaton.iomodule.ruleio.Reader;
import com.celluarautomaton.rulemodule.schema.SimpleSchema;
import com.celluarautomaton.statemodule.State;

public class SimpleSchemaReader implements Reader<SimpleSchema> {

	@Override
	public SimpleSchema read(BufferedReader reader) throws IOException {
		int row = Integer.parseInt(reader.readLine());
		int column = Integer.parseInt(reader.readLine());
		int stateId = Integer.parseInt(reader.readLine());
		
		SimpleSchema simpleSchema = new SimpleSchema();
		simpleSchema.setRow(row);
		simpleSchema.setColumn(column);
		simpleSchema.setState(State.values()[stateId]);
		
		return simpleSchema;
	}

}
