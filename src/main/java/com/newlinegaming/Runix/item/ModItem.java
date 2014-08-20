package com.newlinegaming.Runix.item;

import com.newlinegaming.Runix.item.armor.ArmorAetherGoggles;
import com.newlinegaming.Runix.lib.LibInfo;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class ModItem {

    public static Item AetherGoggles;
    public static Item transRod;
    
    public static void init() {

        AetherGoggles = new ArmorAetherGoggles();
        transRod = new ItemTransmutationRod();
        
        gameReg();
    }

    private static void gameReg() {
        GameRegistry.registerItem(AetherGoggles, "RinixAetherGoggles");
//        GameRegistry.registerItem(transRod, LibInfo.MOD_ID + "transmutaionrod");
        
    }
}
