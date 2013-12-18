package com.newlinegaming.Runix;

import java.util.ArrayList;
import java.util.Iterator;

import com.newlinegaming.Runix.PairedLocationList.PairedBlocks;


public class PairedLocationList implements Iterator<PairedBlocks> {
    
    /**PairedBlocks is a convenience class for iterating over the common side-by-side comparison of
     * Rune templates and world blocks.  At the moment, it does not handle meta-value of a block.
     */
    public class PairedBlocks{// extends Pair<WorldCoordinates, WorldCoordinates> 
        //Josiah: org.apache.commons.lang3.tuple.Pair didn't offer enough to make it worth it
        public int runeBlock;
        public int worldBlock;
        
        public PairedBlocks(int runeTemplate, int worldTarget){
            this.runeBlock = runeTemplate;
            this.worldBlock = worldTarget; //Josiah: can this be side effected?  do I need a copy?
        }
    }

    private ArrayList<PairedBlocks> coordinates;
    private int position;
    
    public PairedLocationList (int[][][] blockPattern, WorldCoordinates centerPoint){
        position = 0;
        for (int y = 0; y < blockPattern.length; y++) {
            for (int z = 0; z < blockPattern[y].length; z++) {
                for (int x = 0; x < blockPattern[y][z].length; x++) {
                    WorldCoordinates target = centerPoint.offset(-blockPattern[y][z].length / 2 + x,  -y,  -blockPattern[y].length / 2 + z);
                    coordinates.add(new PairedBlocks(blockPattern[y][z][x], target.getBlockId()));
                }
            }
        }
    }
    
    @Override
    public boolean hasNext() {
        return position < coordinates.size();
    }

    @Override
    public PairedBlocks next() {
        return coordinates.get(position++);
    }

    @Override
    public void remove() {
        coordinates.remove(position-1);
    }
    
}