package com.newlinegaming.runix.common.block.trees;

import com.newlinegaming.runix.Runix;
import com.newlinegaming.runix.common.lib.LibRef;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class VoidLog extends Block {

	public VoidLog() {
		super(Material.wood);
		this.setCreativeTab(Runix.TabRunix);
		this.setBlockName(LibRef.MOD_ID + ":"+ "voidlog" );
	}
	
	@SideOnly(Side.CLIENT)
	public static IIcon icontop;
	@SideOnly(Side.CLIENT)
	public static IIcon iconside;

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		icontop = reg.registerIcon(LibRef.MOD_ID + ":" + "void_log_top");
		iconside = reg.registerIcon(LibRef.MOD_ID + ":" + "void_log_side");
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int meta) {
		if(side == 0) {
			return icontop;
		} else if(side == 1) {
			return icontop;
		}
		return iconside;
	}
}
