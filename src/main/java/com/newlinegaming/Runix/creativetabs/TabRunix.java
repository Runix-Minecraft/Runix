package com.newlinegaming.Runix.creativetabs;

import com.newlinegaming.Runix.Runix;

import com.newlinegaming.Runix.item.ModItem;
import net.minecraft.creativetab.CreativeTabs;

public class TabRunix extends CreativeTabs {
	
	public TabRunix(int par1, String par2Str) {
		super(par1, par2Str);
		
	}
	public int getTabIconItemIndex() {
		return ModItem.PlaceHolder.itemID;
	}
	
	public String getTranslatedTabLabel() {
		return "Runix";
	}

}
