package com.newlinegaming.Runix;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import org.jetbrains.annotations.NotNull;

/**
 * Plain old Data.  getClass() and meta.  Use this to preserve all your block info.
 * Meta is the additional information like orientation or color used in some blocks.
 * Doesn't currently do TileEntity or NBT.
 */

//TODO: only use block state
@SuppressWarnings("unused")
public class SigBlock {

    private IBlockState state;
    private Block block;

    public SigBlock(@NotNull Block block){
        this.block = block;
        this.state = block.getDefaultState();
    }

    public Block getBlock() {
        return block;
    }

    public IBlockState getState() {
        return state;
    }

    @Override
    public boolean equals(Object other){
        if(other instanceof SigBlock)
            return equals((SigBlock) other);
        else if (other instanceof Block)
            return equals((Block)other);  //can't get meta from block without coordinates
        return false;
    }

    public boolean equals(@NotNull SigBlock other){
        return block == other.state && state == other.state;
    }

    public boolean equals(Block other){
        return block.equals(other);  //can't get meta from block without coordinates
    }

    @NotNull
    public String toString(){
        return "" + block + ":" + state.toString();
    }
}