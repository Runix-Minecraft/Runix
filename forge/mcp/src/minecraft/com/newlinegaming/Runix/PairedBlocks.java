package com.newlinegaming.Runix;

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