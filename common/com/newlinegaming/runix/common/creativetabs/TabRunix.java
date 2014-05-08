package com.newlinegaming.runix.common.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import com.newlinegaming.runix.common.item.ModItem;

public class TabRunix extends CreativeTabs {
	
	public TabRunix(String lable) {
		super(lable);
		
	}

	@Override
	public Item getTabIconItem() {
		return ModItem.AetherGoggles;
	}

}
