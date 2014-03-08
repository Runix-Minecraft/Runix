package com.newlinegaming.Runix.item;

import com.newlinegaming.Runix.Runix;
import com.newlinegaming.Runix.lib.LibRef;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemTransmutaionRod extends Item {
	
	public ItemTransmutaionRod(){
		super();
		this.setCreativeTab(Runix.TabRunix);
		this.setUnlocalizedName("transmutaionrod");
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		this.itemIcon = reg.registerIcon(LibRef.MOD_ID + ":boop");
	}
}
