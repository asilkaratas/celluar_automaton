package com.celluarautomatin.gridmodule;

import java.util.BitSet;

import org.apache.log4j.Logger;
import org.junit.Test;

public class BitsetTest {
	private static final Logger logger = Logger.getLogger(BitsetTest.class);
	
	@Test
	public void firstTest() {
		BitSet pattern = new BitSet(75);
		pattern.set(1, true);
		
		pattern.and(pattern);
		
		logger.info(1<<6);
	}
}
