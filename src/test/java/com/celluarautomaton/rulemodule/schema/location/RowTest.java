package com.celluarautomaton.rulemodule.schema.location;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RowTest {

	private Row row;
	
	@Before
	public void setUp() {
		row = new Row();
	}
	
	@Test
	public void firstRowTest() {
		int[] expected = {0, 1, 2, 3, 4};
		int[] locations = row.getLocations(1);
		
		Assert.assertArrayEquals(expected, locations);
	}
	
	@Test
	public void secondRowTest() {
		int[] expected = {5, 6, 7, 8, 9};
		int[] locations = row.getLocations(2);
		
		Assert.assertArrayEquals(expected, locations);
	}
	
	@Test
	public void thirdRowTest() {
		int[] expected = {10, 11, 12, 13, 14};
		int[] locations = row.getLocations(3);
		
		Assert.assertArrayEquals(expected, locations);
	}
	
	@Test
	public void fourthRowTest() {
		int[] expected = {15, 16, 17, 18, 19};
		int[] locations = row.getLocations(4);
		
		Assert.assertArrayEquals(expected, locations);
	}
	
	@Test
	public void fifthRowTest() {
		int[] expected = {20, 21, 22, 23, 24};
		int[] locations = row.getLocations(5);
		
		Assert.assertArrayEquals(expected, locations);
	}
}
