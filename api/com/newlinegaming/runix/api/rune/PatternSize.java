package com.newlinegaming.runix.api.rune;

public enum PatternSize {
	
	THREE (3, 3),
	FIVE (5, 5),
	SEVEN (7, 7),
	NINE (9, 9),
	ELEVEN (11, 11);
	
	private final int length;
	private final int width;
	
	PatternSize(int length, int width) {
		this.length = length;
		this.width = width;
		
	}
		
}
