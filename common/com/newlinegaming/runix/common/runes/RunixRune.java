package com.newlinegaming.runix.common.runes;

import net.minecraft.entity.player.EntityPlayer;
import us.illyohs.yssgaroth.common.world.WorldXYZ;

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
	public int EnergyReqired() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void execute(WorldXYZ coords, EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isBlockSpecific() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public EnergyType EnergyTypeReqired(EnergyType type) {
		// TODO Auto-generated method stub
		return type;
	}

}
