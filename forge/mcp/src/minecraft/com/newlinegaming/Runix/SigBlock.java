package com.newlinegaming.Runix;

/** Plain old Data.  blockID and meta.  Use this to preserve all your block info. */
public class SigBlock{
    int blockID;
    int meta;
    public SigBlock(int blockID, int meta){
        this.blockID = blockID;
        this.meta = meta;
    }
}