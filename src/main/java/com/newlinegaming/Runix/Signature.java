package com.newlinegaming.Runix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import org.jetbrains.annotations.NotNull;

public class Signature {
    
    @NotNull
    private final ArrayList<SigBlock> blocks;
    public transient ArrayList<String> metaWhiteList;
    
    public Signature() {
        blocks = new ArrayList<>();
    }

    /**
     * Legacy Note: Signatures now include "Tier 0" blocks as valid signature options.
     * @param rune for fetching the runicFormulae()
     * @param coords with facing
     */
    public Signature(@NotNull AbstractRune rune, WorldXYZ coords) {
        blocks = new ArrayList<>();
        Block[] metaWhiteList = new Block[]{//this list specifically lacks any block that uses meta for orientation
                Blocks.CARPET, Blocks.WOOL,
                Blocks.WHEAT, //added just in case you WANT an impossible waypoint
                Blocks.HARDENED_CLAY, Blocks.JUKEBOX, //adjusting the notes would change your Signature :D
                Blocks.PLANKS,
                Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE,
                Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, Blocks.WOODEN_PRESSURE_PLATE, Blocks.STONE_PRESSURE_PLATE, //may be some potential there
                Blocks.STAINED_HARDENED_CLAY,
                Blocks.LOG, Blocks.LOG2, Blocks.WOODEN_SLAB, Blocks.DOUBLE_WOODEN_SLAB,
                Blocks.STAINED_GLASS, Blocks.STAINED_GLASS_PANE
        };

        HashMap<WorldXYZ, SigBlock> shape = rune.runicFormulae(coords);
        for (WorldXYZ target : shape.keySet()) {
            if (shape.get(target).equals(AbstractRune.SIGR) ) {
                Block blockID = target.getBlock();
                if( !blockID.equals(Blocks.AIR) ){
                    if(Arrays.asList(metaWhiteList).contains(target.getBlock()))
                        blocks.add(target.getSigBlock());
                    else
                        blocks.add(new SigBlock(blockID));//just the blockID
                }
            }
        }
    }
    
    public boolean equals(@NotNull Signature other){
        for(SigBlock b : blocks){//ensure the two signatures have the same number of instances of each block i.e. 3 cobble vs 1 cobble
            if(Collections.frequency(other.blocks, b) != Collections.frequency(blocks, b))
                return false;
        }
        for(SigBlock b : other.blocks){//second loop ensures there's no EXTRA blocks in the other signature
            if(Collections.frequency(other.blocks, b) != Collections.frequency(blocks, b))
                return false;
        }
        
        return true;
    }
    
    public String toString(){
        return blocks.toString();
    }

}


