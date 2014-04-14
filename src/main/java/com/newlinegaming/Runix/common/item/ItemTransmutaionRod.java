package com.newlinegaming.Runix.common.item;

import net.minecraft.item.Item;

import com.newlinegaming.Runix.Runix;

public class ItemTransmutaionRod extends Item {
	
	public ItemTransmutaionRod(){
		super();
		this.setCreativeTab(Runix.TabRunix);
		this.setUnlocalizedName("runix:transmutationrod");//odd different than blocks
		
	}
	
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerIcons(IIconRegister reg) {
//		this.itemIcon = reg.registerIcon(LibRef.MOD_ID + ":boop");
//	}
}
