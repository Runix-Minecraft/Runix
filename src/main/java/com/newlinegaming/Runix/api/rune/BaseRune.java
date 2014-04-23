package com.newlinegaming.Runix.api.rune;

//import com.newlinegaming.Runix.common.utils.WorldXYZ;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;

public abstract class BaseRune {
	
//	public static final Block KEY;
	
	public BaseRune(){};
	
	public abstract boolean isFlatRune();
	
	public abstract boolean isRuneConsumed();
	
	public abstract int EnergyReqired();
	
//	public abstract void execute(WorldXYZ coords, EntityPlayer player);
	
}
