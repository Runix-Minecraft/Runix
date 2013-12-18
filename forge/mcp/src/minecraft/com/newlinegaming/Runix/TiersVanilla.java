package com.newlinegaming.Runix;



import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.block.Block;
// Josiah: I added these imports, They should help


public class TiersVanilla {
    //Josiah: For naming block ids use the vanilla constants instead of hand typing from the wiki.
    //Example:   Tier2.add(Block.torchWood.blockID);  or Block.sandStone.blockID

    public static ArrayList<Integer> Tier0;
    
    public TiersVanilla(){
        Block[] Tier0Blocks = new Block[]{
            Block.sand, Block.stone, Block.dirt, Block.grass, Block.tallGrass, Block.snow, 
            Block.mycelium, Block.netherrack, Block.signPost, Block.signWall};
        
        Tier0 = loadBlockIds(Tier0Blocks);
        Tier0.add( 0 );//Josiah: for whatever reason, AIR is not defined... so we have to add it manually.
        
        Block[] Tier1Blocks = new Block[]{Block.cobblestone};//Josiah: Someone else can finish this list.
    }

    /**The idea behind this method is to take a list of Blocks and pull all the ids. 
     * It really only exists to cut down on the number of ".blockID" that is in this file given
     * how long it will be.  
     * @param blockList
     */
    private ArrayList<Integer> loadBlockIds(Block[] blockList) {
        ArrayList<Integer> IDs = new ArrayList<Integer>();
        for(Block block : blockList)
            IDs.add(block.blockID);
        return IDs;
    }
    
    public boolean isTier0(int blockID){
        return Tier0.contains(new Integer(blockID));
    }
    
}
