package com.newlinegaming.Runix.common.item;

import com.newlinegaming.Runix.common.item.books.ItemMystTome;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class ModItem {
	
	public static Item TransRod;
	
	public static Item MystTome;

    public static void init() {
    	
    	TransRod = new ItemTransmutaionRod();
//    	MystTome = new ItemMystTome();
    	
    	GameReg();

    }

	private static void GameReg() {
		GameRegistry.registerItem(TransRod, "runixtranswand");
		
	}
}
