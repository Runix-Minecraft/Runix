package com.newlinegaming.Runix;

import net.minecraft.block.Block;

/**
 * Plain old Data.  blockID and meta.  Use this to preserve all your block info.
 * Meta is the additional information like orientation or color used in some blocks.
 * Doesn't currently do TileEntity or NBT.
 */
public class SigBlock{
    public Block blockID;
    public int meta;
    public SigBlock(Block blockID, int meta){
        this.blockID = blockID;
        this.meta = meta;
    }
    
    @Override
    public boolean equals(Object other){
        if(other instanceof SigBlock)
            return blockID == ((SigBlock)other).blockID && meta == ((SigBlock)other).meta;
        return false;
    }
    
    public String toString(){
        return "" + blockID + ":" + meta;
    }
}