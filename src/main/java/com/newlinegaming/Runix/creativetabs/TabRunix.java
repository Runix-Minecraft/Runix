package com.newlinegaming.Runix.creativetabs;

import com.newlinegaming.Runix.item.ModItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabRunix extends CreativeTabs {

    public TabRunix(String label) {
        super(label);
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(ModItem.aetherGoggles);
    }

}