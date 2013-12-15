package com.newlinegaming.Runix;

import net.minecraft.entity.player.EntityPlayer;

public abstract class AbstractRune {

	public int[][][] blockPattern;  //Josiah: this pattern should never actually be used
	
	public AbstractRune(){}
	
	public abstract void execute(EntityPlayer player, int x, int y, int z);
}
