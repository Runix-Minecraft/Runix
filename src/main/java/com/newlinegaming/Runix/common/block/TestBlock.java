package com.newlinegaming.Runix.common.block;

import com.newlinegaming.Runix.Runix;
import com.newlinegaming.Runix.common.lib.LibRef;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

public class TestBlock extends Block {

	protected TestBlock() {
		super(Material.rock);
		this.setCreativeTab(Runix.TabRunix);
		this.setBlockName("testBlock");
		this.setHardness(3F);
		this.setResistance(900.0F);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		this.blockIcon = reg.registerIcon(LibRef.MOD_ID + ":testtexture");
	}

}
