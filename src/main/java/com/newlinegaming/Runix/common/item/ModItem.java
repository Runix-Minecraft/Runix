package com.newlinegaming.Runix.common.item;

import com.newlinegaming.Runix.common.item.armor.ArmorAetherGoggles;
import com.newlinegaming.Runix.common.item.books.ItemMystTome;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ModItem {
	
	public static Item TransRod;
	public static Item MystTome;
	
	public static Item AetherGoggles;

    public static void init() {
    	
    	TransRod = new ItemTransmutaionRod();
//    	MystTome = new ItemMystTome();
    	AetherGoggles = new ArmorAetherGoggles();
    	
    	GameReg();

    }

	private static void GameReg() {
		GameRegistry.registerItem(TransRod, "runixtranswand");
		GameRegistry.registerItem(AetherGoggles, "aethergoggles");
		GameRegistry.addShapedRecipe(new ItemStack(AetherGoggles), new Object[]{ "xxx", " x ", "xxx", 'x', new ItemStack(Items.gold_nugget), });//Temp just for testing
		
	}
}
