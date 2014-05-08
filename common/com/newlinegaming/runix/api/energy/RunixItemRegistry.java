package com.newlinegaming.runix.api.energy;

import net.minecraft.item.Item;

public class RunixItemRegistry {
	
	Item item;
	EnergyType type;
	int meta;
	int energy;
	
	public RunixItemRegistry(Item item, int meta, EnergyType type, int energy) {
		this.item = item;
		this.meta = meta;
		this.type = type;
		this.energy = energy;
	}
	
}
