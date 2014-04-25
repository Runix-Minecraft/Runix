package com.newlinegaming.Runix.api.rune;

import illyohs.mods.yssgaroth.common.world.WorldXYZ;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;

public abstract class BaseRune {
	
//	public static final Block KEY;
	
	public BaseRune(){};
	
	public abstract boolean isFlatRune();
	
	public abstract boolean isRuneConsumed();
	
	public abstract int EnergyReqired();
	
	public abstract void execute(WorldXYZ coords, EntityPlayer player);
	
}
