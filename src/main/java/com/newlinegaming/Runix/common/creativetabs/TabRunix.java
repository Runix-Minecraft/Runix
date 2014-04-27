package com.newlinegaming.Runix.common.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import com.newlinegaming.Runix.common.fluids.ModFluid;
import com.newlinegaming.Runix.common.item.ModItem;

public class TabRunix extends CreativeTabs {
	
	public TabRunix(String lable) {
		super(lable);
		
	}

	@Override
	public Item getTabIconItem() {
		return ModItem.AetherGoggles;
	}

}
