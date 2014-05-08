package com.newlinegaming.runix.api.rune;

import net.minecraft.entity.player.EntityPlayer;
import us.illyohs.yssgaroth.common.world.WorldXYZ;

import com.newlinegaming.runix.api.energy.EnergyType;

public abstract class BaseRune {
	
//	public static final Block KEY;
	
	public BaseRune(){};
	
	public abstract boolean isFlatRune();
	
	public abstract boolean isRuneConsumed();
	
	public abstract boolean isBlockSpecific();
	
	public abstract int EnergyReqired();
	
	public abstract EnergyType EnergyTypeReqired(EnergyType type);
	
	public abstract void execute(WorldXYZ coords, EntityPlayer player);
	
}
