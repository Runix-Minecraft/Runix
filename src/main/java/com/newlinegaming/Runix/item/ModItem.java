package com.newlinegaming.Runix.item;

import com.newlinegaming.Runix.item.armor.ArmorAetherGoggles;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class ModItem {

    public static Item AetherGoggles;

    public static void init() {

        AetherGoggles = new ArmorAetherGoggles();
        
        gameReg();
    }

    private static void gameReg() {
        GameRegistry.registerItem(AetherGoggles, "RinixAetherGoggles");
        
    }
}
