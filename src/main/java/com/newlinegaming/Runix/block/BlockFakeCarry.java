package com.newlinegaming.Runix.block;

import com.newlinegaming.Runix.tile.TileFakeCarry;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockFakeCarry extends RunixAirBlockFX implements ITileEntityProvider {
    
    public BlockFakeCarry() {
        
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileFakeCarry();
    }
    
    

}
