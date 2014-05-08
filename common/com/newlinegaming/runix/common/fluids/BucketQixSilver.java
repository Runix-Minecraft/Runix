package com.newlinegaming.runix.common.fluids;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemBucket;

import com.newlinegaming.runix.Runix;
import com.newlinegaming.runix.common.lib.LibRef;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BucketQixSilver extends ItemBucket {

	public BucketQixSilver(Block block) {
		super(block);
		this.setCreativeTab(Runix.TabRunix);
//		this.setUnlocalizedName("runix:qwicksilverbucket");
		this.setUnlocalizedName("runix:qixsilverbucket");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		this.itemIcon = reg.registerIcon(LibRef.MOD_ID + ":qsbucket");
	}
}
