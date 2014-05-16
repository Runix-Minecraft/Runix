package com.newlinegaming.runix.common.block.trees;

import net.minecraft.block.BlockLeaves;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

import com.newlinegaming.runix.Runix;
import com.newlinegaming.runix.common.lib.LibRef;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class VoidLeaves extends BlockLeaves {

	public VoidLeaves() {
		super();
		this.setBlockName(LibRef.MOD_ID + ":" + "voidleaves");
		this.setCreativeTab(Runix.TabRunix);
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		this.blockIcon = reg.registerIcon("minecraft:leaves_oak");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(int par1)
	{
		return  0xFFFFFF;
	}

	@Override
	public IIcon getIcon(int var1, int var2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] func_150125_e() {
		// TODO Auto-generated method stub
		return null;
	}

}