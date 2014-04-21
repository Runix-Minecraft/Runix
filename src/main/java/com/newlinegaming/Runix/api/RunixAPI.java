package com.newlinegaming.Runix.api;

import com.newlinegaming.Runix.api.rune.BaseRune;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class RunixAPI {
	
	//Tool and armor Materials 
	public static ArmorMaterial armorRunix = EnumHelper.addArmorMaterial("RUNEIUMARMOR", 30, new int[] { 4, 6, 6, 4 }, 25);
	public static ArmorMaterial armorArcadian = EnumHelper.addArmorMaterial("ARCADIANARMOR", 50, new int[]{4, 6, 6, 4}, 25);
	public static ToolMaterial toolRunix = EnumHelper.addToolMaterial("RUNEIUMTOOL", 4, 650, 5, 4, 25);
	public static ToolMaterial toolArcadian = EnumHelper.addToolMaterial("ARCADIANARMOR", 4, 800, 5, 6, 25);
	
	/**
	 * Sets the amount of energy that each block will have.
	 * Make sure to load your energy values after Runix's
	 * 	example: RunixAPI.setBlockEnergy(Blocks.cobblestone, 0, 1)
	 * @param blocks The Block
	 * @param meta The meta data value for the block
	 * @param energyValue 1 to 320,940
	 */
	public static void setBlockEnergy(Block block, int meta, int energyValue) {
		
	}
	
	/**
	 * Sets the energy value for each Item
	 * Make sure to load your energy values after Runix's
	 * 	example: RunixAPI.setItemEnergy(Items.apple, 0, 54);
	 * @param item The Item
	 * @param meta The meta data value for the item
	 * @param energyValue
	 */
	public static void setItemEnergy(Item item, int meta, int energyValue) {
		
	}
	
	/**
	 * Registers your your runes in Runix
	 * Make sure your runes are called after Runix's
	 * 	example: RunixAPI.addRune(new foorune());
	 * @param rune points to the rune class that you added
	 */
	public static void addRune(BaseRune rune) {
		
	}
 
}