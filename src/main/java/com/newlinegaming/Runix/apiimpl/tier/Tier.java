package com.newlinegaming.Runix.apiimpl.tier;

import com.newlinegaming.Runix.api.tier.ITier;
import net.minecraft.block.Block;

public final class Tier implements ITier {

    private Block   block;
    private int     energy;
    private boolean natural;
    private boolean crushable;
    private boolean sensitive;

    public Tier(Block block, int energy, boolean natural, boolean crushable, boolean sensitive) {
        this.block = block;
        this.energy = energy;
        this.natural = natural;
        this.crushable = crushable;
        this.sensitive = sensitive;
    }

    @Override
    public Block getBlock() {
        return this.block;
    }

    @Override
    public int getEnergy() {
        return this.energy;
    }

    @Override
    public boolean isNatural() {
        return this.natural;
    }

    @Override
    public boolean isCrushable() {
        return this.crushable;
    }

    @Override
    public boolean isSensitive() {
        return this.sensitive;
    }
}
