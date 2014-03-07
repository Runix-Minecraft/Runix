package com.newlinegaming.Runix.creativetabs;

import com.newlinegaming.Runix.Runix;
import com.newlinegaming.Runix.item.ModItem;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

public class TabRunix extends CreativeTabs {
	
	public TabRunix(String lable) {
		super(lable);
		
	}

	@Override
	public Item getTabIconItem() {
		// TODO Auto-generated method stub
		return Item.getItemFromBlock(Blocks.bookshelf);
	}

}
