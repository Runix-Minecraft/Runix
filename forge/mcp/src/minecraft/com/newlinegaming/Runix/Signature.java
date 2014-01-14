package com.newlinegaming.Runix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import net.minecraft.block.Block;

public class Signature {
    
    public ArrayList<SigBlock> blocks;
    public ArrayList<Integer> metaWhiteList;
    
    public Signature()
    {
        blocks = new ArrayList<SigBlock>();
    }

    /**Legacy Note: Signatures now include "Tier 0" blocks as valid signature options.
     * @param rune for fetching the runicFormulae()
     * @param coords with facing
     */
    public Signature(AbstractRune rune, WorldXYZ coords) {
        blocks = new ArrayList<SigBlock>();
        Block[] usableMetaData = new Block[]{//this list specifically lacks any block that uses meta for orientation
                Block.carpet, Block.cloth, 
                Block.crops, //added just in case you WANT an impossible waypoint
                Block.hardenedClay, Block.music, //adjusting the notes would change your Signature :D
                Block.planks, 
                Block.pressurePlateGold, Block.pressurePlateIron, Block.pressurePlatePlanks, Block.pressurePlateStone, //may be some potential there
                Block.stainedClay,
                Block.wood, Block.woodDoubleSlab, Block.woodSingleSlab,
        };
        metaWhiteList = Tiers.loadBlockIds(usableMetaData);

        HashMap<WorldXYZ, SigBlock> shape = rune.runicFormulae(coords);
        for (WorldXYZ target : shape.keySet()) {
            if (shape.get(target).blockID == AbstractRune.SIGR) {
                int blockID = target.getBlockId();
                if(metaWhiteList.contains(new Integer(blockID)))
                    blocks.add(target.getSigBlock());
                else
                    blocks.add(new SigBlock(blockID, 0));//just the blockID
            }
        }
        rune.aetherSay(coords.getWorld(), "Signature:" + blocks.toString());
    }
    
    public boolean equals(Signature other){
        for( SigBlock b : blocks){//ensure the two signatures have the same number of instances of each block i.e. 3 cobble vs 1 cobble
            if( Collections.frequency(other.blocks, b) != Collections.frequency(blocks, b))
                return false;
        }
//        System.out.println("Match found");//"Comparing:" + this + " =? " + other + " = " + answer);
        return true;
    }
    
    public String toString(){
        return blocks.toString();
    }
}
