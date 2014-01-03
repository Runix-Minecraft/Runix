package com.newlinegaming.Runix;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;


public class RubricRecallRune extends AbstractRune{

	@Override
		public int[][][] runicTemplateOriginal() {
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
	public void execute(WorldXYZ coords, EntityPlayer player) {
		// TODO Auto-generated method stub
		//find match signature in RubricCreationRune.getActiveMagic()
	    //consume Rune for energy
	    //transfer energy to Rubric rune
	    //Rubric.unpackStructure
	        //if not enough energy, Rubric can keep the energy, just ask for more
	    //delete self
	}

	@Override
	public String getRuneName() {  return "RubricRecallRune";
		
	}

    public boolean isFlatRuneOnly() {
        return false;
    }
	
}
