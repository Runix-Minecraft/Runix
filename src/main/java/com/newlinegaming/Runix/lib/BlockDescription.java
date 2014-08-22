package com.newlinegaming.Runix.lib;

import net.minecraft.block.Block;

/**
 * Plain old Data Struct for the energyRegistry in Tiers.java
 */
public class BlockDescription
{
    public Block type; 
    public int energy; 
    public boolean natural;
    public boolean crushable;
    public boolean sensitive;
    
    public BlockDescription (Block type, int energy, boolean natural, boolean crushable, boolean sensitive){
        this.type = type;
        this.energy = energy;
        this.natural = natural;
        this.crushable = crushable;
        this.sensitive = sensitive;
    }
}
