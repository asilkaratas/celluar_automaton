package com.celluarautomaton.rulemodule;

import org.junit.Before;

public class SchemaRuleTest {

	private SchemaRule schemaRule;
	
	@Before
	public void setUp() {
		schemaRule = new SchemaRule("Schema Rule 1");
		
	}
	/*
	@Test
	public void simpleSchemaTest() throws ComplexSchemaConflictException {
		
		SimpleSchema simpleSchema = new SimpleSchema();
		simpleSchema.setColumn(0);
		simpleSchema.setRow(0);
		simpleSchema.setState(State.ALIVE);
		schemaRule.addSimpleSchema(simpleSchema);
		schemaRule.calculatePatternValue();
		
		long patternValue = 1;
		
		boolean matches = schemaRule.matches(patternValue);
		
		Assert.assertTrue(matches);
	}
	
	@Test
	public void simple2SchemaTest() throws ComplexSchemaConflictException {
		
		SimpleSchema simpleSchema = new SimpleSchema();
		simpleSchema.setColumn(0);
		simpleSchema.setRow(0);
		simpleSchema.setState(State.ALIVE);
		
		SimpleSchema simpleSchema2 = new SimpleSchema();
		simpleSchema2.setColumn(1);
		simpleSchema2.setRow(0);
		simpleSchema2.setState(State.ALIVE);
		
		schemaRule.addSimpleSchema(simpleSchema);
		schemaRule.addSimpleSchema(simpleSchema2);
		schemaRule.calculatePatternValue();
		
		long patternValue = 4;
		
		//boolean matches = schemaRule.matches(patternValue);
		
		//Assert.assertTrue(matches);
	}
	
	@Test
	public void complexRowQuantity1SchemaTest() throws ComplexSchemaConflictException {
		
		ComplexSchema complexSchema = new ComplexSchema();
		complexSchema.setLocation(Location.ROW);
		complexSchema.setLocationNum(1);
		complexSchema.setQuantity(Quantity.EXACTLY);
		complexSchema.setQuantityNum(1);
		complexSchema.setState(State.ALIVE);
		schemaRule.addComplexSchema(complexSchema);
		schemaRule.calculatePatternValue();
		
		long[] patternValues = {1, 3, 9, 27, 81};
		
		for(long patternValue : patternValues) {
			Assert.assertTrue(schemaRule.matches(patternValue));
		}
	}
	
	@Test
	public void complexRowQuantity2SchemaTest() throws ComplexSchemaConflictException {
		
		ComplexSchema complexSchema = new ComplexSchema();
		complexSchema.setLocation(Location.ROW);
		complexSchema.setLocationNum(1);
		complexSchema.setQuantity(Quantity.EXACTLY);
		complexSchema.setQuantityNum(2);
		complexSchema.setState(State.ALIVE);
		schemaRule.addComplexSchema(complexSchema);
		schemaRule.calculatePatternValue();
		
		long[] patternValues = {82, 4, 84, 36, 10, 90, 28, 12, 108, 30};
		
		for(long patternValue : patternValues) {
			Assert.assertTrue(schemaRule.matches(patternValue));
		}
	}
	
	@Test(expected = ComplexSchemaConflictException.class)
	public void complexRowQuantity2With2SchemaTest() throws ComplexSchemaConflictException {
		
		ComplexSchema complexSchema = new ComplexSchema();
		complexSchema.setLocation(Location.ROW);
		complexSchema.setLocationNum(1);
		complexSchema.setQuantity(Quantity.EXACTLY);
		complexSchema.setQuantityNum(1);
		complexSchema.setState(State.ALIVE);
		
		ComplexSchema complexSchema2 = new ComplexSchema();
		complexSchema2.setLocation(Location.ROW);
		complexSchema2.setLocationNum(1);
		complexSchema2.setQuantity(Quantity.EXACTLY);
		complexSchema2.setQuantityNum(2);
		complexSchema2.setState(State.ALIVE);
		
		schemaRule.addComplexSchema(complexSchema);
		schemaRule.addComplexSchema(complexSchema2);
		schemaRule.calculatePatternValue();
		
		long[] patternValues = {82, 4, 84, 36, 10, 90, 28, 12, 108, 30};
		
		for(long patternValue : patternValues) {
			Assert.assertTrue(schemaRule.matches(patternValue));
		}
	}
	
	@Test
	public void complexColumnQuantity1SchemaTest() throws ComplexSchemaConflictException {
		
		ComplexSchema complexSchema = new ComplexSchema();
		complexSchema.setLocation(Location.COLUMN);
		complexSchema.setLocationNum(1);
		complexSchema.setQuantity(Quantity.EXACTLY);
		complexSchema.setQuantityNum(1);
		complexSchema.setState(State.ALIVE);
		schemaRule.addComplexSchema(complexSchema);
		schemaRule.calculatePatternValue();
		
		long[] patternValues = {1, 243, 59049, 14348907, 2147483647};
		
		for(long patternValue : patternValues) {
			Assert.assertTrue(schemaRule.matches(patternValue));
		}
	}
	
	@Test
	public void complexRowAndColumnSchemaTest() throws ComplexSchemaConflictException {
		
		ComplexSchema complexSchema = new ComplexSchema();
		complexSchema.setLocation(Location.ROW);
		complexSchema.setLocationNum(1);
		complexSchema.setQuantity(Quantity.EXACTLY);
		complexSchema.setQuantityNum(1);
		complexSchema.setState(State.ALIVE);
		
		ComplexSchema complexSchema2 = new ComplexSchema();
		complexSchema2.setLocation(Location.COLUMN);
		complexSchema2.setLocationNum(1);
		complexSchema2.setQuantity(Quantity.EXACTLY);
		complexSchema2.setQuantityNum(1);
		complexSchema2.setState(State.ALIVE);
		
		schemaRule.addComplexSchema(complexSchema);
		schemaRule.addComplexSchema(complexSchema2);
		schemaRule.setStartState(State.EMPTY);
		schemaRule.calculatePatternValue();
		
		long[] patternValues = {1, 2147483650L, 59076, 324, 14348988, 2147483656L, 59052, 14348916, 270, 2147483728L, 59058, 14348910, 246, 2147483674L, 59130, 252, 14348934};
		
		for(long patternValue : patternValues) {
			Assert.assertTrue(schemaRule.matches(patternValue));
		}
		
		Assert.assertEquals(17, patternValues.length);
	}
	
	@Test
	public void complexRowAndColumn2SchemaTest() throws ComplexSchemaConflictException {
		
		ComplexSchema complexSchema = new ComplexSchema();
		complexSchema.setLocation(Location.ROW);
		complexSchema.setLocationNum(1);
		complexSchema.setQuantity(Quantity.EXACTLY);
		complexSchema.setQuantityNum(1);
		complexSchema.setState(State.ALIVE);
		
		ComplexSchema complexSchema2 = new ComplexSchema();
		complexSchema2.setLocation(Location.COLUMN);
		complexSchema2.setLocationNum(1);
		complexSchema2.setQuantity(Quantity.EXACTLY);
		complexSchema2.setQuantityNum(2);
		complexSchema2.setState(State.ALIVE);
		
		schemaRule.addComplexSchema(complexSchema);
		schemaRule.addComplexSchema(complexSchema2);
		schemaRule.setStartState(State.ALIVE);
		schemaRule.setEndState(State.EMPTY);
		schemaRule.calculatePatternValue();
		
		long[] patternValues = {14939424, 14880672, 2162364004L, 14880618, 2162364076L, 590491, 2148074140L, 2162364022L, 590814, 590736, 590742, 590760, 2148074218L, 2148015340L, 14939400, 2148074146L, 531685, 14939406, 2148015334L, 14880594, 14939478, 2148015358L, 2148015089L, 14880600, 2148015412L, 2162363998L, 2148074164L, 14880349};
		
		for(long patternValue : patternValues) {
			Assert.assertTrue(schemaRule.matches(patternValue));
		}
		
		Assert.assertEquals(28, patternValues.length);
	}
	*/
	
	
	
}
