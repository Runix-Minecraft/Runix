package com.newlinegaming.runix.common.runes;

import net.minecraft.init.Blocks;

import com.newlinegaming.runix.api.RunixAPI;
import com.newlinegaming.runix.api.energy.EnergyType;

public class SetEnergy {
	
	public static void initBlocks() {
		
		//vanilla Blocks
		
		//1
		RunixAPI.setBlockEnergy(Blocks.air, 0, EnergyType.HAUD, 1);
		RunixAPI.setBlockEnergy(Blocks.stone, 0, EnergyType.TELLUS, 1);
		RunixAPI.setBlockEnergy(Blocks.dirt, 0, EnergyType.TELLUS, 1);
		RunixAPI.setBlockEnergy(Blocks.grass, 0,EnergyType.FLORIS, 1);
		RunixAPI.setBlockEnergy(Blocks.tallgrass, 0, EnergyType.FLORIS, 1);
		RunixAPI.setBlockEnergy(Blocks.mycelium, 0, EnergyType.FLORIS, 1);
		RunixAPI.setBlockEnergy(Blocks.snow, 0, EnergyType.FRIGIDUS, 1);
		RunixAPI.setBlockEnergy(Blocks.snow_layer, 0, EnergyType.FRIGIDUS, 1);
		RunixAPI.setBlockEnergy(Blocks.netherrack, 0, EnergyType.INFERNUS, 1);
		RunixAPI.setBlockEnergy(Blocks.farmland, 0, EnergyType.TELLUS, 1);
		RunixAPI.setBlockEnergy(Blocks.end_stone, 0, EnergyType.MAGUS, 1);
		RunixAPI.setBlockEnergy(Blocks.cobblestone_wall, 0, EnergyType.TELLUS, 1);
		RunixAPI.setBlockEnergy(Blocks.stone_stairs, 0, EnergyType.TELLUS, 1);
		RunixAPI.setBlockEnergy(Blocks.cobblestone, 0, EnergyType.TELLUS, 1);
		RunixAPI.setBlockEnergy(Blocks.stone_slab, 0, EnergyType.TELLUS, 1);
		RunixAPI.setBlockEnergy(Blocks.ice, 0, EnergyType.FRIGIDUS, 1);
		RunixAPI.setBlockEnergy(Blocks.leaves, 0, EnergyType.FLORIS, 1);
		RunixAPI.setBlockEnergy(Blocks.leaves, 1, EnergyType.FLORIS, 1);
		RunixAPI.setBlockEnergy(Blocks.leaves, 2, EnergyType.FLORIS, 1);
		RunixAPI.setBlockEnergy(Blocks.leaves, 3, EnergyType.FLORIS, 1);
		RunixAPI.setBlockEnergy(Blocks.leaves2, 0, EnergyType.FLORIS, 1);
		RunixAPI.setBlockEnergy(Blocks.leaves2, 1, EnergyType.FLORIS, 1);
		RunixAPI.setBlockEnergy(Blocks.bedrock, 0, EnergyType.FLORIS, 0);
		RunixAPI.setBlockEnergy(Blocks.piston_head, 0, EnergyType.MECHANICUS, 1);
		
		//2
		RunixAPI.setBlockEnergy(Blocks.stone_pressure_plate, 0, EnergyType.MECHANICUS, 2);
		RunixAPI.setBlockEnergy(Blocks.stone_button, 0, EnergyType.MECHANICUS, 2);
		RunixAPI.setBlockEnergy(Blocks.wooden_button, 0, EnergyType.MECHANICUS, 2);
		RunixAPI.setBlockEnergy(Blocks.double_stone_slab, 0, EnergyType.TELLUS, 2);
		RunixAPI.setBlockEnergy(Blocks.double_wooden_slab, 0, EnergyType.LIGNUM, 2);
		
		//4
		RunixAPI.setBlockEnergy(Blocks.fire, 0, EnergyType.INFERNUS, 4);
		
		//5
		RunixAPI.setBlockEnergy(Blocks.sand, 0, EnergyType.TELLUS, 5);
		RunixAPI.setBlockEnergy(Blocks.lever, 0, EnergyType.MECHANICUS, 5);
		
		//8
		RunixAPI.setBlockEnergy(Blocks.planks, 0, EnergyType.LIGNUM, 8);
		RunixAPI.setBlockEnergy(Blocks.planks, 1, EnergyType.LIGNUM, 8);
		RunixAPI.setBlockEnergy(Blocks.planks, 2, EnergyType.LIGNUM, 8);
		RunixAPI.setBlockEnergy(Blocks.planks, 3, EnergyType.LIGNUM, 8);
		RunixAPI.setBlockEnergy(Blocks.planks, 4, EnergyType.LIGNUM, 8);
		RunixAPI.setBlockEnergy(Blocks.planks, 5, EnergyType.LIGNUM, 8);
		RunixAPI.setBlockEnergy(Blocks.furnace, 0, EnergyType.MECHANICUS, 8);
		
		//10
		RunixAPI.setBlockEnergy(Blocks.glass, 0, EnergyType.SPECTO, 10);
		RunixAPI.setBlockEnergy(Blocks.glass, 1, EnergyType.SPECTO, 10);
		RunixAPI.setBlockEnergy(Blocks.glass, 2, EnergyType.SPECTO, 10);
		RunixAPI.setBlockEnergy(Blocks.glass, 3, EnergyType.SPECTO, 10);
		RunixAPI.setBlockEnergy(Blocks.glass, 4, EnergyType.SPECTO, 10);
		RunixAPI.setBlockEnergy(Blocks.glass, 5, EnergyType.SPECTO, 10);
		RunixAPI.setBlockEnergy(Blocks.glass, 6, EnergyType.SPECTO, 10);
		RunixAPI.setBlockEnergy(Blocks.glass, 7, EnergyType.SPECTO, 10);
		RunixAPI.setBlockEnergy(Blocks.glass, 8, EnergyType.SPECTO, 10);
		RunixAPI.setBlockEnergy(Blocks.glass, 9, EnergyType.SPECTO, 10);
		RunixAPI.setBlockEnergy(Blocks.glass, 10, EnergyType.SPECTO, 10);
		RunixAPI.setBlockEnergy(Blocks.glass, 11, EnergyType.SPECTO, 10);
		RunixAPI.setBlockEnergy(Blocks.glass, 12, EnergyType.SPECTO, 10);
		RunixAPI.setBlockEnergy(Blocks.glass, 13, EnergyType.SPECTO, 10);
		RunixAPI.setBlockEnergy(Blocks.glass, 14, EnergyType.SPECTO, 10);
		RunixAPI.setBlockEnergy(Blocks.glass, 15, EnergyType.SPECTO, 10);
		
		//12
		RunixAPI.setBlockEnergy(Blocks.stonebrick, 0, EnergyType.TELLUS, 12);
		RunixAPI.setBlockEnergy(Blocks.stonebrick, 1, EnergyType.TELLUS, 12);
		RunixAPI.setBlockEnergy(Blocks.stonebrick, 2, EnergyType.TELLUS, 12);
		RunixAPI.setBlockEnergy(Blocks.stonebrick, 3, EnergyType.TELLUS, 12);
		RunixAPI.setBlockEnergy(Blocks.torch, 0, EnergyType.LUMINIS, 12);
//		RunixAPI.setBlockEnergy(Blocks.birch_stairs, meta, energyValue);
		RunixAPI.setBlockEnergy(Blocks.fence, 0, EnergyType.LIGNUM, 10);
		
		//14
//		RunixAPI.setBlockEnergy(blocks., meta, energyValue); Arrow!?!?
		
		//16
		RunixAPI.setBlockEnergy(Blocks.water, 0, EnergyType.UMIDUS, 16);
		RunixAPI.setBlockEnergy(Blocks.wooden_pressure_plate, 0, EnergyType.LIGNUM, 16);
		RunixAPI.setBlockEnergy(Blocks.flowing_water, 0, EnergyType.UMIDUS, 16);
		RunixAPI.setBlockEnergy(Blocks.yellow_flower, 0, EnergyType.FLORIS, 16);
		
		//20
		RunixAPI.setBlockEnergy(Blocks.sandstone, 0, EnergyType.TELLUS, 20);
		RunixAPI.setBlockEnergy(Blocks.sandstone, 1, EnergyType.TELLUS, 20);
		RunixAPI.setBlockEnergy(Blocks.stonebrick, 2, EnergyType.TELLUS, 20);
		
		//23
		RunixAPI.setBlockEnergy(Blocks.skull, 0, EnergyType.MAGUS, 23);
		RunixAPI.setBlockEnergy(Blocks.skull, 1, EnergyType.MAGUS, 23);
		RunixAPI.setBlockEnergy(Blocks.skull, 2, EnergyType.MAGUS, 23);
		
		//28
		RunixAPI.setBlockEnergy(Blocks.ladder, 0, EnergyType.LIGNUM, 28);
		
		//31
		RunixAPI.setBlockEnergy(Blocks.gravel, 0, EnergyType.TELLUS, 31);
		
		//32
		RunixAPI.setBlockEnergy(Blocks.crafting_table, 0, EnergyType.MECHANICUS, 32);
		
		//Non vanilla blocks
		
	}

}
