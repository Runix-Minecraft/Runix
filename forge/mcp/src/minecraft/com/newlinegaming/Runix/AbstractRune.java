package com.newlinegaming.Runix;

import cpw.mods.fml.common.network.Player;

public abstract class AbstractRune {

	public int[][][] blockPattern;  //Josiah: this pattern should never actually be used
	
	public AbstractRune(){}
	
	public abstract void execute(Player player, int x, int y, int z);
}
