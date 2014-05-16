package com.newlinegaming.runix.common.runes;

import com.newlinegaming.runix.api.RunixAPI;
import com.newlinegaming.runix.api.rune.BaseRune;

public class RunixRune {
	
	//I know this class doesn't work I'm just using it to make reference to show what I want to get done. 
	
	public static BaseRune fooRune; 
	
	public static void init() {
		
		fooRune = new RuneFooRune();
		
		RuneReg();
	}

	private static void RuneReg() {
		RunixAPI.registerRune(fooRune, "FooRune");
		
	}
}