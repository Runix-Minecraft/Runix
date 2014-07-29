package com.newlinegaming.Runix.rune;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.newlinegaming.Runix.BlockRecord;
import com.newlinegaming.Runix.PersistentRune;
import com.newlinegaming.Runix.SigBlock;
import com.newlinegaming.Runix.Signature;
import com.newlinegaming.Runix.WorldXYZ;


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
	public Block[][][] runicTemplateOriginal() {
		Block RT=Blocks.redstone_torch;
		Block AIR=Blocks.air;
		return new Block[][][] {{
			{TIER,TIER,SIGR,TIER,TIER},
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
		if(toolused != null)
		    specialName = toolused.getDisplayName();
		ArrayList<PersistentRune> rubricList = (new RubricCreationRune().getActiveMagic());
        Signature signature = getSignature();
		
        PersistentRune rubrics = null;
		if (toolused!=null && toolused.getItem() == Items.written_book){
			rubrics = (new RubricCreationRune()).getRuneBySpecialName(specialName);
		}
		else if( !signature.isEmpty() ){
    		for( PersistentRune candidate : rubricList ){
    			if( signature.equals ( candidate.getSignature() ) ){
    				rubrics = candidate;
    				break;
    			}
    		}
		}
		if( rubrics == null){
			aetherSay(poker, "A Rubric with that signature cannot be found.");
			return;
		}
		ArrayList<BlockRecord> structure  = ((RubricCreationRune)rubrics).structure;
		//			try {
		consumeRune(location);// absorb energy from recall rune         
		unpackStructure(poker, structure, rubrics.location);

		//	        } catch (NotEnoughRunicEnergyException e) {
		//	            reportOutOfGas(poker);
		//ensure recall is placed back 
		//	        }
		//TODO fix the energy requirements
	    //consume Rune for energy
	    //transfer energy to Rubric rune
	        //if not enough energy, Rubric can keep the energy, just ask for more
	    //delete self
	}


    public boolean isFlatRuneOnly() {
        return true;
    }
	
    public void unpackStructure(EntityPlayer initiator, ArrayList<BlockRecord> structure, WorldXYZ origin){
	    //convert old coordinets to vector3 based on offset from origin
    	// create new worldXYZ by adding this.location to each vector3 
    	HashMap<WorldXYZ, SigBlock> NewStructure = new HashMap<WorldXYZ, SigBlock>();
    	for(BlockRecord record : structure){
    	    NewStructure.put(location.offset(record.offset), record.block);
    	}
    		
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
