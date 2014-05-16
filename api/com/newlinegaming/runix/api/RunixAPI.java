package com.newlinegaming.runix.api;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

import com.newlinegaming.runix.api.energy.EnergyType;
import com.newlinegaming.runix.api.energy.RunixBlockRegistry;
import com.newlinegaming.runix.api.energy.RunixItemRegistry;

public class RunixAPI {
	
	public static List<RunixBlockRegistry> setBlockEnergy = new ArrayList<RunixBlockRegistry>();
	public static List<RunixItemRegistry> setItemEnergy = new ArrayList<RunixItemRegistry>();
	
	//Tool and armor Materials 
	public static ArmorMaterial armorRunix = EnumHelper.addArmorMaterial("RUNEIUMARMOR", 30, new int[] { 4, 6, 6, 4 }, 25);
	public static ArmorMaterial armorArcadian = EnumHelper.addArmorMaterial("ARCADIANARMOR", 50, new int[]{4, 6, 6, 4}, 25);
	public static ToolMaterial toolRunix = EnumHelper.addToolMaterial("RUNEIUMTOOL", 4, 650, 5, 4, 25);
	public static ToolMaterial toolArcadian = EnumHelper.addToolMaterial("ARCADIANARMOR", 4, 800, 5, 6, 25);
	
	/**
	 * Sets the amount of energy that each block will have.
	 * Make sure to load your energy values after Runix's
	 * 	example: RunixAPI.setBlockEnergy(Blocks.cobblestone, EnergyType.Ignotus, 0, 1)
	 * @param blocks The Block
	 * @param meta The meta data value for the block
	 * @param type sets the EnergyType for each block @see EnergyType
	 * @param energy sets the amount of energy the block is worth
	 */
	public static RunixBlockRegistry setBlockEnergy(Block block, int meta, EnergyType type, int energy) {
		RunixBlockRegistry regBlock = new RunixBlockRegistry(block, meta, type, energy);
		setBlockEnergy.add(regBlock);
		return regBlock;
		
	}
	
	/**
	 * Sets the energy value for each Item
	 * Make sure to load your energy values after Runix's
	 * 	example: RunixAPI.setItemEnergy(Items.apple, 0, EnergyType.Ignotus, 64);
	 * @param item The Item
	 * @param meta The meta data value for the item
	 * @param type sets the EnergyType for each item @see EnergyType
	 * @param sets the amount of energy the item is worth
	 */
	
	public static RunixItemRegistry setItemEnergy(Item item, int meta, EnergyType type, int energy) {
		RunixItemRegistry regItem = new RunixItemRegistry(item, meta, type, energy);
		setItemEnergy.add(regItem);
		return regItem;
	}
	
	/**
	 * Registers your your runes in Runix
	 * Make sure your runes are called after Runix's
	 * 	example: RunixAPI.registerRune(fooRune, "fooRune");
	 * @param Object points to the rune class that you added
	 * @param String the name of your rune
	 */
	public static void registerRune(Object rune, String runename) {
		
	}
 
}