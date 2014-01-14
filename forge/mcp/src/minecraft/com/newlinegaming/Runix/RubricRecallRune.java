package com.newlinegaming.Runix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;


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
			              {SIGR, RT, KEY ,  RT,SIGR},
			              {TIER, AIR,  RT, AIR,TIER},
			              {TIER,TIER,SIGR,TIER,TIER}
			             }}; 
	}

	@Override
	public void poke(EntityPlayer poker, WorldXYZ coords) {
		consumeKeyBlock(coords);
		ItemStack toolused = poker.getCurrentEquippedItem();
		specialName = toolused.getDisplayName();
		Signature signature = new Signature(this, coords);
		//This is necessary because getActiveMagic() CANNOT be static, so it returns a pointer to a static field...
		ArrayList<PersistentRune> rubricList = (new RubricCreationRune().getActiveMagic());
		System.out.println("RubricList.size()" + rubricList.size());
		PersistentRune rubrics = null;
		if (toolused!=null && toolused.itemID == Item.book.itemID){
			rubrics = (new RubricCreationRune()).getRuneBySpecialName(specialName);
		}
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
		consumeRune(location);// absorb energy from recall rune         
		unpackStructure(poker, structure, rubrics.location);

		//	        } catch (NotEnoughRunicEnergyException e) {
		//	            reportOutOfGas(poker);
		//ensure recall is placed back 
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
	
    public void unpackStructure(EntityPlayer initiator, HashMap<WorldXYZ, SigBlock> structure, WorldXYZ origin){
	    //convert old coordinets to vector3 based on offset from origin
    	// create new worldXYZ by adding this.location to each vector3 
    	Vector3 difference = Vector3.offset(origin, location);
    	HashMap<WorldXYZ, WorldXYZ> mapping = Util_Movement.displaceShape(structure.keySet(),difference.x, difference.y, difference.z);
    	HashMap<WorldXYZ, SigBlock> NewStructure = new HashMap<WorldXYZ, SigBlock>();
    	for(WorldXYZ oldlocation:mapping.keySet())
    		NewStructure.put(mapping.get(oldlocation),structure.get(oldlocation));
    	//try{
	    //for structure
    	
    	// for(WorldXYZ point : structure.keySet()){}
    	stampBlockPattern(NewStructure, initiator);
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
