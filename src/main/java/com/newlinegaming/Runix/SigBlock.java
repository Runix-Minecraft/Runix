package com.newlinegaming.Runix;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * Plain old Data.  getClass() and meta.  Use this to preserve all your block info.
 * Meta is the additional information like orientation or color used in some blocks.
 * Doesn't currently do TileEntity or NBT.
 */
public class SigBlock{
    public int meta;
    public Block blockID;
    public SigBlock(Block blockID, int meta){
        this.blockID = blockID;
        this.meta = meta;
    }
    
    @Override
    public boolean equals(Object other){
        if(other instanceof SigBlock)
            return blockID == ((SigBlock)other).blockID && meta == ((SigBlock)other).meta;
        else if (other instanceof Block)
            return blockID.equals(other);  //can't get meta from block without coordinates
        return false;
    }
    
    public String toString(){
        return "" + blockID + ":" + meta;
    }
}