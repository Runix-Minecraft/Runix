package com.newlinegaming.Runix.api.tier;

import net.minecraft.block.Block;

public interface ITier {

    Block   getBlock();
    int     getEnergy();
    boolean isNatural();
    boolean isCrushable();
    boolean isSensitive();
}
