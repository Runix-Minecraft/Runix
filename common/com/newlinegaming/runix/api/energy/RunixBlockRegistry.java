package com.newlinegaming.runix.api.energy;

import net.minecraft.block.Block;

public class RunixBlockRegistry {
	
	Block block;
	EnergyType type;
	int meta;
	int energy;
	
	public RunixBlockRegistry(Block block, int meta, EnergyType type, int energy) {
		this.block = block;
		this.meta = meta;
		this.type = type;
		this.energy = energy;
	}
}
