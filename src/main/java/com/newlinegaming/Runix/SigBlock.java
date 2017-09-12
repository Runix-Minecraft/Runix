package com.newlinegaming.Runix;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * Plain old Data.  getClass() and meta.  Use this to preserve all your block info.
 * Meta is the additional information like orientation or color used in some blocks.
 * Doesn't currently do TileEntity or NBT.
 */
@SuppressWarnings("unused")
public class SigBlock{
    public final int meta;
    public final Block blockID;
    public SigBlock(Block blockID, int meta){
        this.blockID = blockID;
        this.meta = meta;
    }
    
    @Override
    public boolean equals(Object other){
        if(other instanceof SigBlock)
            return equals((SigBlock) other);
        else if (other instanceof Block)
            return equals((Block)other);  //can't get meta from block without coordinates
        return false;
    }

    public boolean equals(SigBlock other){
        return blockID == other.blockID && meta == other.meta;
    }

    public boolean equals(Block other){
        return blockID.equals(other);  //can't get meta from block without coordinates
    }

    public String toString(){
        return "" + blockID + ":" + meta;
    }
}