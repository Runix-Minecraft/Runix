package com.newlinegaming.Runix.block;

import com.newlinegaming.Runix.tile.TileFakeCarry;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockFakeCarry extends RunixAirBlock implements ITileEntityProvider {
    
    public BlockFakeCarry() {
        setBlockName("runix:fakeairblockfx");
        setLightLevel(17f);
        
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileFakeCarry();
    }
    
    

}
