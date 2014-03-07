package com.newlinegaming.Runix.fluids;

import com.newlinegaming.Runix.Runix;
import com.newlinegaming.Runix.lib.LibRef;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;

public class BucketQuickSilver extends ItemBucket {

	public BucketQuickSilver(Block block) {
		super(block);
		this.setCreativeTab(Runix.TabRunix);
		this.setUnlocalizedName("qwicksilverbucket");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		this.itemIcon = reg.registerIcon(LibRef.MOD_ID + ":qsbucket");
	}
}
