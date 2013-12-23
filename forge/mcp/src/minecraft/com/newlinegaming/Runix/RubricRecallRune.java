package com.newlinegaming.Runix;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;


public class RubricRecallRune extends AbstractRune{

	@Override
		public int[][][] blockPattern() {
			int RT=Block.torchRedstoneActive.blockID;
			int AIR=0;
			return new int [][][] 
			            {{{TIER,TIER, AIR,TIER,TIER},
			              {TIER, AIR,  RT, AIR,TIER},
			              { AIR , RT,TIER,  RT, AIR},
			              {TIER, AIR,  RT, AIR,TIER},
			              {TIER,TIER, AIR,TIER,TIER}
			             }}; 
	}

	@Override
	public void execute(EntityPlayer player, WorldXYZ coords) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getRuneName() {  return "RubricRecallRune";
		
	}
	
	
}
