package com.newlinegaming.Runix.item;

import net.minecraft.item.Item;

public class ModItem {

    public static Item PlaceHolder;

    public static void init() {

        PlaceHolder = new RunixPlaceHolder(10000).setUnlocalizedName("RunixPlaceHolder");

    }
}
