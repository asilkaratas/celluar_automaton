package com.celluarautomaton.gridmodule;

import com.celluarautomaton.statemodule.State;

public class Pattern {
	
	public static final Pattern ALIVE_MASK = new Pattern(2635249153387078802L, 1170);
	public static final Pattern DEAD_MASK = new Pattern(5270498306774157604L, 2340);
	
	public static void resetStates(int[] states) {
		for(int i = 0; i < states.length; i++) {
			states[i] = 0;
		}
	}
	
	public static Pattern calculatePatternValue(int[] stateValues) {
		Pattern pattern = new Pattern();
		//pattern.reset();
		for(int i = 0; i < 21; ++i) {
			pattern.setFirstWord(stateValues[i], i);
		}
		
		for(int i = 0; i < 4; ++i) {
			pattern.setSecondWord(stateValues[i + 21], i);
		}
		
		pattern.calculateHash();
		return pattern;
	}
	
	private long firstWord;
	private long secondWord;
	private int hash;
	private static long stateHolder;
	
	
	public Pattern() {
		reset();
	}
	
	public Pattern(long firstWord, long secondWord) {
		this.firstWord = firstWord;
		this.secondWord = secondWord;
	}
	
	public void reset() {
		firstWord = 0L;
		secondWord = 0L;
	}
	
	public void setFirstWord(int stateValue, int index) {
		index *= 3;
		stateHolder = stateValue;
		firstWord = firstWord | (stateHolder << index);
	}
	
	public void setSecondWord(int stateValue, int index) {
		index *= 3;
		stateHolder = stateValue;
		secondWord = secondWord | (stateHolder << index);
	}
	
	public boolean contains(Pattern pattern) {
		if((this.firstWord & pattern.firstWord) == pattern.firstWord &&
			(this.secondWord & pattern.secondWord) == pattern.secondWord) {
			return true;
		}
		return false;
	}
	
	public int getCount(Pattern maskPattern) {
		return Long.bitCount(this.firstWord & maskPattern.firstWord) + 
				Long.bitCount(this.secondWord & maskPattern.secondWord);
	}
	
	public boolean hasAliveOrDead() {
		int aliveCount = getCount(ALIVE_MASK);
		int deadCount = getCount(DEAD_MASK);
		return (aliveCount + deadCount) == 0 ? false : true;
	}
	
	public int bitCounts() {
		return Long.bitCount(firstWord) + Long.bitCount(secondWord);
	}
	
	public void calculate(State[] states) {
		reset();
		
		for(int i = 0; i < 21; ++i) {
			setFirstWord(states[i].getValue(), i);
		}
		
		for(int i = 0; i < 4; ++i) {
			setSecondWord(states[i + 21].getValue(), i);
		}
	}
	
	public void calculate(int[] stateValues) {
		reset();
		for(int i = 0; i < 21; ++i) {
			setFirstWord(stateValues[i], i);
		}
		
		for(int i = 0; i < 4; ++i) {
			setSecondWord(stateValues[i + 21], i);
		}
		
		calculateHash();
	}
	
	
	@Override
	public String toString() {
		return String.format("[Pattern firstWord:%s secondWord:%s first:%d second:%d]", 
				String.format("%64s", Long.toBinaryString(firstWord)).replace(' ', '0'),
				String.format("%64s", Long.toBinaryString(secondWord)).replace(' ', '0'),
				firstWord, secondWord);
	}
	
	public void calculateHash() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (firstWord ^ (firstWord >>> 32));
		result = prime * result + (int) (secondWord ^ (secondWord >>> 32));
		hash = result;
	}

	@Override
	public int hashCode() {
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pattern other = (Pattern) obj;
		if (firstWord != other.firstWord)
			return false;
		if (secondWord != other.secondWord)
			return false;
		return true;
	}
	
	
}
