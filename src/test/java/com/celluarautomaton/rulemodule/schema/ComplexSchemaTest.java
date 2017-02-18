package com.celluarautomaton.rulemodule.schema;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.celluarautomaton.statemodule.State;

public class ComplexSchemaTest {

	private ComplexSchema complexSchema;
	
	@Before
	public void setUp() {
		complexSchema = new ComplexSchema();
	}
	/*
	@Test
	public void row1Quantity1WithZeroStatesTest() throws ComplexSchemaConflictException {
		complexSchema.setState(State.ALIVE);
		complexSchema.setLocation(Location.ROW);
		complexSchema.setLocationNum(1);
		complexSchema.setQuantity(Quantity.EXACTLY);
		complexSchema.setQuantityNum(1);
		
		int[][] expected = {{
					1, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0
				}, {
					0, 1, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0
				}, {
					0, 0, 1, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0
				}, {
					0, 0, 0, 1, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0
				}, {
					0, 0, 0, 0, 1,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0
				}
		};
		
		int[] states = {
				0, 0, 0, 0, 0,
				0, 0, 0, 0, 0,
				0, 0, 0, 0, 0,
				0, 0, 0, 0, 0,
				0, 0, 0, 0, 0
		};
		int[][] oldStatesList = {states};
		int[][] stateList = complexSchema.getStatesList(oldStatesList);
		
		Assert.assertArrayEquals(expected, stateList);
	}
	
	@Test
	public void row1Quantity1Test() throws ComplexSchemaConflictException {
		complexSchema.setState(State.ALIVE);
		complexSchema.setLocation(Location.ROW);
		complexSchema.setLocationNum(1);
		complexSchema.setQuantity(Quantity.EXACTLY);
		complexSchema.setQuantityNum(1);
		
		int[][] expected = {{
					1, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0
				}
		};
		
		int[] states = {
				1, 0, 0, 0, 0,
				0, 0, 0, 0, 0,
				0, 0, 0, 0, 0,
				0, 0, 0, 0, 0,
				0, 0, 0, 0, 0
		};
		int[][] oldStatesList = {states};
		int[][] stateList = complexSchema.getStatesList(oldStatesList);
		
		Assert.assertArrayEquals(expected, stateList);
	}
	
	@Test
	public void row2Quantity1Test() throws ComplexSchemaConflictException {
		complexSchema.setState(State.ALIVE);
		complexSchema.setLocation(Location.ROW);
		complexSchema.setLocationNum(2);
		complexSchema.setQuantity(Quantity.EXACTLY);
		complexSchema.setQuantityNum(1);
		
		int[][] expected = {{
					0, 0, 0, 0, 0,
					0, 1, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0
				}
		};
		
		int[] states = {
				0, 0, 0, 0, 0,
				0, 1, 0, 0, 0,
				0, 0, 0, 0, 0,
				0, 0, 0, 0, 0,
				0, 0, 0, 0, 0
		};
		int[][] oldStatesList = {states};
		int[][] stateList = complexSchema.getStatesList(oldStatesList);
		
		Assert.assertArrayEquals(expected, stateList);
	}
	
	@Test(expected = ComplexSchemaConflictException.class)
	public void row2Quantity1ExceptionTest() throws ComplexSchemaConflictException {
		complexSchema.setState(State.ALIVE);
		complexSchema.setLocation(Location.ROW);
		complexSchema.setLocationNum(2);
		complexSchema.setQuantity(Quantity.EXACTLY);
		complexSchema.setQuantityNum(1);
		
		int[][] expected = {{
					0, 0, 0, 0, 0,
					0, 1, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0
				}
		};
		
		int[] states = {
				0, 0, 0, 0, 0,
				1, 1, 0, 0, 0,
				0, 0, 0, 0, 0,
				0, 0, 0, 0, 0,
				0, 0, 0, 0, 0
		};
		int[][] oldStatesList = {states};
		int[][] stateList = complexSchema.getStatesList(oldStatesList);
		
		Assert.assertArrayEquals(expected, stateList);
	}
	
	@Test
	public void row2Quantity2Test() throws ComplexSchemaConflictException {
		complexSchema.setState(State.ALIVE);
		complexSchema.setLocation(Location.ROW);
		complexSchema.setLocationNum(2);
		complexSchema.setQuantity(Quantity.EXACTLY);
		complexSchema.setQuantityNum(2);
		
		int[][] expected = {{
					0, 0, 0, 0, 0,
					1, 1, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0
				}, {
					0, 0, 0, 0, 0,
					0, 1, 1, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0
				}, {
					0, 0, 0, 0, 0,
					0, 1, 0, 1, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0
				}, {
					0, 0, 0, 0, 0,
					0, 1, 0, 0, 1,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0
				}
		};
		
		int[] states = {
				0, 0, 0, 0, 0,
				0, 1, 0, 0, 0,
				0, 0, 0, 0, 0,
				0, 0, 0, 0, 0,
				0, 0, 0, 0, 0
		};
		int[][] oldStatesList = {states};
		int[][] stateList = complexSchema.getStatesList(oldStatesList);
		
		Assert.assertArrayEquals(expected, stateList);
	}
	
	@Test
	public void row2Quantity3Test() throws ComplexSchemaConflictException {
		complexSchema.setState(State.ALIVE);
		complexSchema.setLocation(Location.ROW);
		complexSchema.setLocationNum(2);
		complexSchema.setQuantity(Quantity.EXACTLY);
		complexSchema.setQuantityNum(3);
		
		int[][] expected = {{
					0, 0, 0, 0, 0,
					1, 1, 1, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0
				}, {
					0, 0, 0, 0, 0,
					1, 1, 0, 1, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0
				}, {
					0, 0, 0, 0, 0,
					1, 1, 0, 0, 1,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0
				}, {
					0, 0, 0, 0, 0,
					0, 1, 1, 1, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0
				}, {
					0, 0, 0, 0, 0,
					0, 1, 1, 0, 1,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0
				}, {
					0, 0, 0, 0, 0,
					0, 1, 0, 1, 1,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0
				}
		};
		
		int[] states = {
				0, 0, 0, 0, 0,
				0, 1, 0, 0, 0,
				0, 0, 0, 0, 0,
				0, 0, 0, 0, 0,
				0, 0, 0, 0, 0
		};
		int[][] oldStatesList = {states};
		int[][] stateList = complexSchema.getStatesList(oldStatesList);
		
		Assert.assertArrayEquals(expected, stateList);
	}
	
	@Test
	public void row2Quantity4Test() throws ComplexSchemaConflictException {
		complexSchema.setState(State.ALIVE);
		complexSchema.setLocation(Location.ROW);
		complexSchema.setLocationNum(2);
		complexSchema.setQuantity(Quantity.EXACTLY);
		complexSchema.setQuantityNum(4);
		
		int[][] expected = {{
					0, 0, 0, 0, 0,
					1, 1, 1, 1, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0
				}, {
					0, 0, 0, 0, 0,
					1, 1, 1, 0, 1,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0
				}, {
					0, 0, 0, 0, 0,
					1, 1, 0, 1, 1,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0
				}, {
					0, 0, 0, 0, 0,
					0, 1, 1, 1, 1,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0
				}
		};
		
		int[] states = {
				0, 0, 0, 0, 0,
				0, 1, 0, 0, 0,
				0, 0, 0, 0, 0,
				0, 0, 0, 0, 0,
				0, 0, 0, 0, 0
		};
		int[][] oldStatesList = {states};
		int[][] stateList = complexSchema.getStatesList(oldStatesList);
		
		Assert.assertArrayEquals(expected, stateList);
	}
	
	@Test
	public void row2Quantity5Test() throws ComplexSchemaConflictException {
		complexSchema.setState(State.ALIVE);
		complexSchema.setLocation(Location.ROW);
		complexSchema.setLocationNum(2);
		complexSchema.setQuantity(Quantity.EXACTLY);
		complexSchema.setQuantityNum(5);
		
		int[][] expected = {{
					0, 0, 0, 0, 0,
					1, 1, 1, 1, 1,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0
				}
		};
		
		int[] states = {
				0, 0, 0, 0, 0,
				0, 1, 0, 0, 0,
				0, 0, 0, 0, 0,
				0, 0, 0, 0, 0,
				0, 0, 0, 0, 0
		};
		int[][] oldStatesList = {states};
		int[][] stateList = complexSchema.getStatesList(oldStatesList);
		
		Assert.assertArrayEquals(expected, stateList);
	}
	
	@Test
	public void row2Quantity5FullTest() throws ComplexSchemaConflictException {
		complexSchema.setState(State.ALIVE);
		complexSchema.setLocation(Location.ROW);
		complexSchema.setLocationNum(2);
		complexSchema.setQuantity(Quantity.EXACTLY);
		complexSchema.setQuantityNum(5);
		
		int[][] expected = {{
					0, 0, 0, 0, 0,
					1, 1, 1, 1, 1,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0
				}
		};
		
		int[] states = {
				0, 0, 0, 0, 0,
				1, 1, 1, 1, 1,
				0, 0, 0, 0, 0,
				0, 0, 0, 0, 0,
				0, 0, 0, 0, 0
		};
		int[][] oldStatesList = {states};
		int[][] stateList = complexSchema.getStatesList(oldStatesList);
		
		Assert.assertArrayEquals(expected, stateList);
	}
	*/

	
	
	
}
