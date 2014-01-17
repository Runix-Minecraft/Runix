package com.newlinegaming.Runix.block;

import com.newlinegaming.Runix.Runix;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;

public class GreekFire extends BlockFire {

    public GreekFire(int par1) {
	super(par1);
	
        this.blockHardness = 100.0F;
        this.setLightOpacity(15);
        this.setCreativeTab(Runix.TabRunix);
        this.setLightValue(15.0F);
        this.setBurnProperties(Block.stone.blockID, 30, 60);
        this.setBurnProperties(Block.grass.blockID, 30, 60);
        this.setBurnProperties(Block.dirt.blockID, 30, 60);
        this.setBurnProperties(Block.gravel.blockID, 30, 60);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
    this.setIconArray(new Icon[] { par1IconRegister.registerIcon("Runix:GreekFire"), par1IconRegister.registerIcon("Runix:GreekFire1") });
    }
    @SideOnly(Side.CLIENT)
    public Icon func_94438_c(int par1) {
    return this.getIconArray()[par1];
    }
    @SideOnly(Side.CLIENT)
    public Icon getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
    return this.getIconArray()[0];
    }
    

}
