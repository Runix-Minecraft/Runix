package com.newlinegaming.Runix.common.runes;

import net.minecraft.init.Blocks;

import com.newlinegaming.Runix.api.RunixAPI;

public class SetEnergy {
	
	public static void init() {
		
		//1
		RunixAPI.setBlockEnergy(Blocks.air, 0, 1);
		RunixAPI.setBlockEnergy(Blocks.stone, 0, 1);
		RunixAPI.setBlockEnergy(Blocks.dirt, 0, 1);
		RunixAPI.setBlockEnergy(Blocks.grass, 0, 1);
		RunixAPI.setBlockEnergy(Blocks.tallgrass, 0, 1);
		RunixAPI.setBlockEnergy(Blocks.mycelium, 0, 1);
		RunixAPI.setBlockEnergy(Blocks.snow, 0, 1);
		RunixAPI.setBlockEnergy(Blocks.snow_layer, 0, 1);
		RunixAPI.setBlockEnergy(Blocks.netherrack, 0, 1);
		RunixAPI.setBlockEnergy(Blocks.farmland, 0, 1);
		RunixAPI.setBlockEnergy(Blocks.end_stone, 0, 1);
		RunixAPI.setBlockEnergy(Blocks.cobblestone_wall, 0, 1);
		RunixAPI.setBlockEnergy(Blocks.stone_stairs, 0, 1);
		RunixAPI.setBlockEnergy(Blocks.cobblestone, 0, 1);
		RunixAPI.setBlockEnergy(Blocks.stone_slab, 0, 1);
		RunixAPI.setBlockEnergy(Blocks.ice, 0, 1);
		RunixAPI.setBlockEnergy(Blocks.leaves, 0, 1);
		RunixAPI.setBlockEnergy(Blocks.leaves, 1, 1);
		RunixAPI.setBlockEnergy(Blocks.leaves, 2, 1);
		RunixAPI.setBlockEnergy(Blocks.leaves, 3, 1);
		RunixAPI.setBlockEnergy(Blocks.leaves2, 0, 1);
		RunixAPI.setBlockEnergy(Blocks.leaves2, 1, 1);
		RunixAPI.setBlockEnergy(Blocks.bedrock, 0, 0);
		RunixAPI.setBlockEnergy(Blocks.piston_head, 0, 1);
		
		//2
		RunixAPI.setBlockEnergy(Blocks.stone_pressure_plate, 0, 2);
		RunixAPI.setBlockEnergy(Blocks.stone_button, 0, 2);
		RunixAPI.setBlockEnergy(Blocks.wooden_button, 0, 2);
		RunixAPI.setBlockEnergy(Blocks.double_stone_slab, 0, 2);
		RunixAPI.setBlockEnergy(Blocks.double_wooden_slab, 0, 2);
		
		//4
		RunixAPI.setBlockEnergy(Blocks.fire, 0, 4);
		
		//5
		RunixAPI.setBlockEnergy(Blocks.sand, 0, 5);
		RunixAPI.setBlockEnergy(Blocks.lever, 0, 5);
		
		//8
		RunixAPI.setBlockEnergy(Blocks.planks, 0, 8);
		RunixAPI.setBlockEnergy(Blocks.planks, 1, 8);
		RunixAPI.setBlockEnergy(Blocks.planks, 2, 8);
		RunixAPI.setBlockEnergy(Blocks.planks, 3, 8);
		RunixAPI.setBlockEnergy(Blocks.planks, 4, 8);
		RunixAPI.setBlockEnergy(Blocks.planks, 5, 8);
		RunixAPI.setBlockEnergy(Blocks.furnace, 0, 8);
		
		//10
		RunixAPI.setBlockEnergy(Blocks.glass, 0, 10);
		RunixAPI.setBlockEnergy(Blocks.glass, 1, 10);
		RunixAPI.setBlockEnergy(Blocks.glass, 2, 10);
		RunixAPI.setBlockEnergy(Blocks.glass, 3, 10);
		RunixAPI.setBlockEnergy(Blocks.glass, 4, 10);
		RunixAPI.setBlockEnergy(Blocks.glass, 5, 10);
		RunixAPI.setBlockEnergy(Blocks.glass, 6, 10);
		RunixAPI.setBlockEnergy(Blocks.glass, 7, 10);
		RunixAPI.setBlockEnergy(Blocks.glass, 8, 10);
		RunixAPI.setBlockEnergy(Blocks.glass, 9, 10);
		RunixAPI.setBlockEnergy(Blocks.glass, 10, 10);
		RunixAPI.setBlockEnergy(Blocks.glass, 11, 10);
		RunixAPI.setBlockEnergy(Blocks.glass, 12, 10);
		RunixAPI.setBlockEnergy(Blocks.glass, 13, 10);
		RunixAPI.setBlockEnergy(Blocks.glass, 14, 10);
		RunixAPI.setBlockEnergy(Blocks.glass, 15, 10);
		
		//12
		RunixAPI.setBlockEnergy(Blocks.stonebrick, 0, 12);
		RunixAPI.setBlockEnergy(Blocks.stonebrick, 1, 12);
		RunixAPI.setBlockEnergy(Blocks.stonebrick, 2, 12);
		RunixAPI.setBlockEnergy(Blocks.stonebrick, 3, 12);
		RunixAPI.setBlockEnergy(Blocks.torch, 0, 12);
//		RunixAPI.setBlockEnergy(Blocks.birch_stairs, meta, energyValue);
		RunixAPI.setBlockEnergy(Blocks.fence, 0, 10);
		
		//14
//		RunixAPI.setBlockEnergy(blocks., meta, energyValue); Arrow!?!?
		
		//16
		RunixAPI.setBlockEnergy(Blocks.water, 0, 16);
		RunixAPI.setBlockEnergy(Blocks.wooden_pressure_plate, 0, 16);
		RunixAPI.setBlockEnergy(Blocks.flowing_water, 0, 16);
		RunixAPI.setBlockEnergy(Blocks.yellow_flower, 0, 16);
		
		//20
		RunixAPI.setBlockEnergy(Blocks.sandstone, 0, 20);
		RunixAPI.setBlockEnergy(Blocks.sandstone, 1, 20);
		RunixAPI.setBlockEnergy(Blocks.stonebrick, 2, 20);
		
		//23
		RunixAPI.setBlockEnergy(Blocks.skull, 0, 23);
		RunixAPI.setBlockEnergy(Blocks.skull, 1, 23);
		RunixAPI.setBlockEnergy(Blocks.skull, 2, 23);
		
		//28
		RunixAPI.setBlockEnergy(Blocks.ladder, 0, 28);
		
		//31
		RunixAPI.setBlockEnergy(Blocks.gravel, 0, 31);
		
		//32
		RunixAPI.setBlockEnergy(Blocks.crafting_table, 0, 32);
		
		
	}

}
