package com.celluarautomaton.gridmodule;


public class InvalidPatternInfo {
	//private static final Logger logger = Logger.getLogger(InvalidPatternInfo.class);
	
	public static final InvalidPatternInfo ALL_EMPTY = new InvalidPatternInfo(new Pattern(1317624576693539401L, 585), "Pattern can not be all empty.");
	public static final InvalidPatternInfo ALL_EMPTY_WITH_IGNORE_CENTER = new InvalidPatternInfo(new Pattern(1317624507974062665L, 585), "Pattern can not be all empty and ignore center.");
	public static final InvalidPatternInfo ALL_IGNORE = new InvalidPatternInfo(new Pattern(0, 0), "Pattern can not be all ignored.");
	public static final InvalidPatternInfo ALL_IGNORE_WITH_EMPTY_CENTER = new InvalidPatternInfo(new Pattern(68719476736L, 0), "Pattern can not be all ignored with empty center.");
	
	public static final InvalidPatternInfo[] INVALID_PATTERN_INFOS = {ALL_EMPTY, ALL_EMPTY_WITH_IGNORE_CENTER, 
		ALL_IGNORE, ALL_IGNORE_WITH_EMPTY_CENTER}; 
	
	public static String hasInvalidPattern(Pattern pattern) {
		for(InvalidPatternInfo patternInfo : INVALID_PATTERN_INFOS) {
			if(pattern.equals(patternInfo.getPattern())) {
				return patternInfo.getMessage();
			}
		}
		
		return null;
	}
	
	
	private final Pattern pattern;
	private final String message;
	
	public InvalidPatternInfo(Pattern pattern, String message) {
		this.pattern = pattern;
		this.message = message;
	}
	
	public Pattern getPattern() {
		return pattern;
	}
	
	public String getMessage() {
		return message;
	}
}
