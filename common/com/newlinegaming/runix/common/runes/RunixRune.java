package com.newlinegaming.runix.common.runes;

import net.minecraft.entity.player.EntityPlayer;

import com.newlinegaming.runix.api.energy.EnergyType;
import com.newlinegaming.runix.api.rune.BaseRune;

public class RunixRune extends BaseRune {

	@Override
	public boolean isFlatRune() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRuneConsumed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isBlockSpecific() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int EnergyReqired() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public EnergyType EnergyTypeReqired(EnergyType type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void execute(int CordX, int CordY, int CordZ, EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

}
