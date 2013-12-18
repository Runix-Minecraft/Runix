package com.newlinegaming.Runix;

import java.util.ArrayList;
import java.util.Iterator;

public class PairedLocationListIterator<PairedBlocks> implements Iterator<PairedBlocks> {
    
    private ArrayList<PairedBlocks> coordinates;  
    private int position;
    
    public PairedLocationListIterator (int[][][] blockPattern, WorldCoordinates centerPoint){
        position = 0;
        for (int y = 0; y < blockPattern.length; y++) {
            for (int z = 0; z < blockPattern[y].length; z++) {
                for (int x = 0; x < blockPattern[y][z].length; x++) {
                    // World coordinates + relative offset + half the size of the rune (for middle)
                    // "-y" the activation and "center" block for 3D runes is the top layer, at the moment
                    WorldCoordinates target = centerPoint.offset(-blockPattern[y][z].length / 2 + x,  -y,  -blockPattern[y].length / 2 + z);
                    //coordinates.add(new PairedBlocks(blockPattern[y][z][x], target.getBlockId()));
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
        return (PairedBlocks) coordinates.get(position++);
    }

    @Override
    public void remove() {
        coordinates.remove(position-1);
    }
    
} 