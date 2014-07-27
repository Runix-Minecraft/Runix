package com.newlinegaming.Runix.creativetabs;

import com.newlinegaming.Runix.item.ModItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class TabRunix extends CreativeTabs {

    public TabRunix(String lable) {
        super(lable);

    }

    @Override
    public Item getTabIconItem() {
        return ModItem.AetherGoggles;
    }

}