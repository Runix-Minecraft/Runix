package com.newlinegaming.runix.api.energy;

import net.minecraft.item.ItemStack;

public interface IItemEnergyStore {
	
	/**
	 * Returns the maximum energy that can be stored.
	 */
	public int getMaxEnergy(ItemStack item, int maxEnergy);
	
	/**
	 * Returns the amount of energy is stored.
	 */
	public int getEnergy(ItemStack item);

}
