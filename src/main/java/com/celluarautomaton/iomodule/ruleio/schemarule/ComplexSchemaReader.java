package com.celluarautomaton.iomodule.ruleio.schemarule;

import java.io.BufferedReader;
import java.io.IOException;

import com.celluarautomaton.iomodule.ruleio.Reader;
import com.celluarautomaton.rulemodule.schema.ComplexSchema;
import com.celluarautomaton.rulemodule.schema.Location;
import com.celluarautomaton.rulemodule.schema.Quantity;
import com.celluarautomaton.statemodule.State;

public class ComplexSchemaReader implements Reader<ComplexSchema> {

	@Override
	public ComplexSchema read(BufferedReader reader) throws IOException {
		String locationName = reader.readLine();
		int locationNum = Integer.parseInt(reader.readLine());
		String quantityName = reader.readLine();
		int quantityNum = Integer.parseInt(reader.readLine());
		int stateId = Integer.parseInt(reader.readLine());
		
		
		ComplexSchema complexSchema = new ComplexSchema();
		complexSchema.setLocation(Location.fromName(locationName));
		complexSchema.setLocationNum(locationNum);
		complexSchema.setQuantity(Quantity.fromName(quantityName));
		complexSchema.setQuantityNum(quantityNum);
		complexSchema.setState(State.values()[stateId]);
		
		return complexSchema;
	}

}
