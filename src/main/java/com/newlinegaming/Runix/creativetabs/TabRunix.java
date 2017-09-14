package com.newlinegaming.Runix.creativetabs;

import com.newlinegaming.Runix.item.ModItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class TabRunix extends CreativeTabs {

    public TabRunix(String label) {
        super(label);
    }

    @Override
    public Item getTabIconItem() {
        return ModItem.aetherGoggles;
    }

}