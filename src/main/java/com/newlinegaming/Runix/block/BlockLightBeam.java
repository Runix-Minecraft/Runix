package com.newlinegaming.Runix.block;

import com.newlinegaming.Runix.RunixMain;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockLightBeam extends Block implements ITileEntityProvider{

    public BlockLightBeam() {
        super(Material.air);
        setCreativeTab(RunixMain.TabRunix);
        setBlockBounds(0.25F, 0.25F, 0.25F, 0.75F, 0.75F, 0.75F);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        // TODO Auto-generated method stub
        return null;
    }

}
