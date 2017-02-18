package com.celluarautomaton.utils;

public class NumberUtil {

	public static int[] createNumbers(int count) {
		int[] numbers = new int[count];
		
		for(int i = 0; i < count; ++i) {
			numbers[i] = i + 1;
		}
		
		return numbers;
	}
}
