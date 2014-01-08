package com.newlinegaming.Runix;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;


public class RubricRecallRune extends PersistentRune{
    
    public RubricRecallRune(){
        runeName = "Rubric Recall";
    }

	@Override
		public int[][][] runicTemplateOriginal() {
			int RT=Block.torchRedstoneActive.blockID;
			int AIR=0;
			return new int [][][] 
			            {{{TIER,TIER,SIGR,TIER,TIER},
			              {TIER, AIR,  RT, AIR,TIER},
			              {SIGR, RT, TIER,  RT,SIGR},
			              {TIER, AIR,  RT, AIR,TIER},
			              {TIER,TIER,SIGR,TIER,TIER}
			             }}; 
	}

	@Override
	public void poke(EntityPlayer player, WorldXYZ coords) {
		//find match signature in RubricCreationRune.getActiveMagic()
	    //consume Rune for energy
	    //transfer energy to Rubric rune
	    //Rubric.unpackStructure
	        //if not enough energy, Rubric can keep the energy, just ask for more
	    //delete self
	}


    public boolean isFlatRuneOnly() {
        return true;
    }
	
    public void unpackStructure(EntityPlayer initiator, WorldXYZ unpackAnchor){
	    //try{
	    //for structure
	        //setBlockID(
	    //catch: need more energy
	}

	@Override
	public ArrayList<PersistentRune> getActiveMagic() {
		// TODO get active magic;
		return null;
	}

	@Override
	public boolean oneRunePerPerson() {
		// TODO Auto-generated method stub
		return false;
	}
    
}
