package com.newlinegaming.Runix.block;

import com.newlinegaming.Runix.tile.TileFakeCarry;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockFakeCarry extends Block implements ITileEntityProvider {
    
    public BlockFakeCarry() {
        super(Material.air);
        setBlockName("runix:fakeairblockfx");
        setLightLevel(17f);
        
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileFakeCarry();
    }
    
    

}
