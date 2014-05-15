package com.newlinegaming.runix.api.rune;

import net.minecraft.entity.player.EntityPlayer;

import com.newlinegaming.runix.api.energy.EnergyType;

public abstract class BaseRune {
	
//	public static final Block KEY;
	
	public BaseRune(){};
	
	public abstract boolean isFlatRune();
	
	public abstract boolean isRuneConsumed();
	
	public abstract boolean isBlockSpecific();
	
	public abstract int EnergyReqired();
	
	public abstract EnergyType EnergyTypeReqired(EnergyType type);
	
	public abstract boolean dropEnergyItem();
	
	//Make a imprint class in the base code and reflect this?
	public abstract void execute(int CordX, int CordY, int CordZ, EntityPlayer player);
	//TODO: Rune Imprinting 
}
