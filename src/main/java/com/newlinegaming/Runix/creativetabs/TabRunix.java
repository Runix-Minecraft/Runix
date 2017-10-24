package com.newlinegaming.Runix.creativetabs;

import com.newlinegaming.Runix.item.ModItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class TabRunix extends CreativeTabs {

    public TabRunix(@NotNull String label) {
        super(label);
    }

    @NotNull
    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(ModItem.aetherGoggles);
    }

}