package com.celluarautomaton.iomodule.ruleio.schemarule;

import java.io.BufferedReader;
import java.io.IOException;

import com.celluarautomaton.iomodule.ruleio.Reader;
import com.celluarautomaton.rulemodule.schema.CustomLocationSchema;
import com.celluarautomaton.rulemodule.schema.Quantity;
import com.celluarautomaton.rulemodule.schema.location.CustomLocation;
import com.celluarautomaton.statemodule.State;

public class CustomLocationSchemaReader implements Reader<CustomLocationSchema> {

	@Override
	public CustomLocationSchema read(BufferedReader reader) throws IOException {
		String locationName = reader.readLine();
		String quantityName = reader.readLine();
		int quantityNum = Integer.parseInt(reader.readLine());
		int stateId = Integer.parseInt(reader.readLine());
		
		
		CustomLocationSchema customLocationSchema = new CustomLocationSchema();
		customLocationSchema.setCustomLocation(CustomLocation.fromName(locationName));
		customLocationSchema.setQuantity(Quantity.fromName(quantityName));
		customLocationSchema.setQuantityNum(quantityNum);
		customLocationSchema.setState(State.values()[stateId]);
		
		return customLocationSchema;
	}

}
