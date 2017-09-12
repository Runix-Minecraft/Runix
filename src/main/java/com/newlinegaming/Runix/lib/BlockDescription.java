package com.newlinegaming.Runix.lib;

import net.minecraft.block.Block;

/**
 * Plain old Data Struct for the energyRegistry in Tiers.java
 */
public class BlockDescription
{
    public final Block type;
    public final int energy;
    public final boolean natural;
    public final boolean crushable;
    public final boolean sensitive;
    
    public BlockDescription (Block type, int energy, boolean natural, boolean crushable, boolean sensitive){
        this.type = type;
        this.energy = energy;
        this.natural = natural;
        this.crushable = crushable;
        this.sensitive = sensitive;
    }
}
