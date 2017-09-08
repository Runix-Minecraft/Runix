package com.newlinegaming.Runix.item;

import com.newlinegaming.Runix.item.armor.ArmorAetherGoggles;
import com.newlinegaming.Runix.lib.LibInfo;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class ModItem {

    public static Item aetherGoggles;
    public static Item transRod;
    
    public static void init() {

//        aetherGoggles = new ArmorAetherGoggles();
//        transRod = new ItemTransmutationRod();
        
        gameReg();
    }

    private static void gameReg() {
//        GameRegistry.registerItem(aetherGoggles, LibInfo.MOD_ID  + "aethergoggles");
//        GameRegistry.registerItem(transRod, LibInfo.MOD_ID + "transmutationrod");
        
    }
}
