package com.newlinegaming.Runix;

import java.util.ArrayList;
import java.util.Iterator;


/**Wrapper class for an Iterator for a wrapper. */
public class PairedLocationList<PairedBlocks> implements Iterable{
    int[][][] blockPattern; 
    WorldCoordinates centerPoint;
    
    public PairedLocationList(int[][][] pattern, WorldCoordinates center){
        blockPattern = pattern;
        centerPoint = center;
    }
    
    @Override
    public PairedLocationListIterator<PairedBlocks> iterator() {
        return new PairedLocationListIterator(blockPattern, centerPoint);
    }   
    
    /** This was meant for placed like AbstractRune.checkRunePattern where we iterate
     * over the same pair of rune blockPattern and WorldCoordinates.  
     * for( PairedBlocks p : new PairedLocationList(blockPattern, centerPoint)
     * It was intended to reduce code duplication, but it turned into an explosion
     * of unnecessary java code.
     */
   
}