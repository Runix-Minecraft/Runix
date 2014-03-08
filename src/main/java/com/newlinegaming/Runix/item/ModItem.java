package com.newlinegaming.Runix.item;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class ModItem {
	
	public static Item TransRod;

    public static void init() {
    	
    	TransRod = new ItemTransmutaionRod();
    	
    	GameReg();

    }

	private static void GameReg() {
		GameRegistry.registerItem(TransRod, "runixtranswand");
		
	}
}
