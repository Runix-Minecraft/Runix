package com.newlinegaming.runix.api.energy;

public interface IBlockEnergyStore {
	
	/**
	 * Returns the maximum energy that can be stored.
	 */
	public int getMaxEnergy();
	
	/**
	 * Returns the amount of energy is stored.
	 */
	public int getEnergy();

}
