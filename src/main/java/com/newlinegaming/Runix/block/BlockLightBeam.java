package com.newlinegaming.Runix.block;

import com.newlinegaming.Runix.RunixMain;
import com.newlinegaming.Runix.lib.LibInfo;
import com.newlinegaming.Runix.tile.TileLightBeam;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockLightBeam extends Block implements ITileEntityProvider{

    public BlockLightBeam() {
        super(Material.air);
        setCreativeTab(RunixMain.TabRunix);
        setBlockBounds(0.25F, 0.25F, 0.25F, 0.75F, 0.75F, 0.75F);
        setBlockTextureName(LibInfo.MOD_ID +":blank");
        setLightLevel(17f);
        setLightOpacity(10);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int x, int y, int z) {
        return null;
    }
    
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;

    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        // TODO Auto-generated method stub
        return new TileLightBeam();
    }

}
