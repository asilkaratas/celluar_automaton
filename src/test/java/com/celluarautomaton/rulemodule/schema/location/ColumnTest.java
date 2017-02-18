package com.celluarautomaton.rulemodule.schema.location;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ColumnTest {

	private Column column;
	
	@Before
	public void setUp() {
		column = new Column();
	}
	
	@Test
	public void firstColumnTest() {
		int[] expected = {0, 5, 10, 15, 20};
		int[] locations = column.getLocations(1);
		
		Assert.assertArrayEquals(expected, locations);
	}
	
	@Test
	public void secondColumnTest() {
		int[] expected = {1, 6, 11, 16, 21};
		int[] locations = column.getLocations(2);
		
		Assert.assertArrayEquals(expected, locations);
	}
	
	@Test
	public void thirdColumnTest() {
		int[] expected = {2, 7, 12, 17, 22};
		int[] locations = column.getLocations(3);
		
		Assert.assertArrayEquals(expected, locations);
	}
	
	@Test
	public void fourthColumnTest() {
		int[] expected = {3, 8, 13, 18, 23};
		int[] locations = column.getLocations(4);
		
		Assert.assertArrayEquals(expected, locations);
	}
	
	@Test
	public void lastColumnTest() {
		int[] expected = {4, 9, 14, 19, 24};
		int[] locations = column.getLocations(5);
		
		Assert.assertArrayEquals(expected, locations);
	}
}
