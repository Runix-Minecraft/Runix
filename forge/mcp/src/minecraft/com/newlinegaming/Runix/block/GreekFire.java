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

    //@SideOnly(Side.CLIENT)
    //private Icon[] iconArray;

    public GreekFire(int par1) {
	super(par1);

	setTickRandomly(true);
	setCreativeTab(Runix.TabRunix);

    }

    //public Icon getIcon(int par1, int par2)
    //{
	//return this.blockIcon;
    //}

    public void initializeBlock()
    {
	this.blockHardness = 100.0F;
	this.setLightOpacity(15);
	this.setLightValue(15.0F);
	this.setBurnProperties(Block.stone.blockID, 30, 60);
	this.setBurnProperties(Block.grass.blockID, 30, 60);
	this.setBurnProperties(Block.dirt.blockID, 30, 60);
	this.setBurnProperties(Block.gravel.blockID, 30, 60);
    }

    //public boolean isOpaqueCube()
    //{
	//return false;
   // }
   // public boolean renderAsNormalBlock()
   // {
	//return false;
   // }
   // public int getRenderType()
   // {
	//return 3;
   // }

   // @SideOnly(Side.CLIENT)
   // public void registerIcons(IconRegister par1IconRegister)
   // {
//	this.iconArray = new Icon[] { par1IconRegister.registerIcon("Runix:GreekFire"), par1IconRegister.registerIcon("Runix:GreekFire1") };
   // }
   // @SideOnly(Side.CLIENT)
   // public Icon func_94438_c(int par1) {
//	return this.iconArray[par1];
   // }
   // @SideOnly(Side.CLIENT)
   // public Icon getBlockTextureFromSideAndMetadata(int par1, int par2)
   // {
//	return this.iconArray[0];
   // }

}
