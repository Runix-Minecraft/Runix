package com.newlinegaming.Runix;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;


public class RubricRecallRune extends PersistentRune{
    
	private static ArrayList<PersistentRune> rubricPatterns = new ArrayList<PersistentRune>();
    public RubricRecallRune(){
        runeName = "Rubric Recall";
    }

    public RubricRecallRune(WorldXYZ coords, EntityPlayer player2) 
    {
	    super(coords, player2,"Rubric Recall");
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
	public void poke(EntityPlayer poker, WorldXYZ coords) {
		 consumeKeyBlock(coords);
		    
		    Signature signature = new Signature(this, coords);
		 		    //This is necessary because getActiveMagic() CANNOT be static, so it returns a pointer to a static field...
		    ArrayList<PersistentRune> rubricList = (new RubricCreationRune().getActiveMagic());
		    System.out.println("RubricList.size()" + rubricList.size());
		    PersistentRune rubrics = null;
		    for( PersistentRune candidate : rubricList){
		        if( ((RubricCreationRune)candidate).sig.equals ( signature ) ){
		            rubrics = candidate;
		            break;
		        }
		    }
		    if( rubrics == null){
		        aetherSay(poker, "A Rubric with that signature cannot be found.");
		        return;
		    }
		    HashMap<WorldXYZ, SigBlock> structure  = ((RubricCreationRune)rubrics).structure;
//			try {
	            WorldXYZ structor;
				unpackStructure(poker, structure);
//	        } catch (NotEnoughRunicEnergyException e) {
//	            reportOutOfGas(poker);
//	        }
		//TODO fix the energy requirements
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
	
    public void unpackStructure(EntityPlayer initiator, HashMap<WorldXYZ, SigBlock> structure){
	    //try{
	    //for structure
    	// for(WorldXYZ point : structure.keySet()){}
    	stampBlockPattern(structure, initiator);
	        //setBlockID(
    	//TODO validate area to stamp
	    //catch: need more energy
	}

	@Override
	public ArrayList<PersistentRune> getActiveMagic() {
			return rubricPatterns;
	}

	@Override
	public boolean oneRunePerPerson() {
		// TODO Auto-generated method stub
		return false;
	}
    
}
