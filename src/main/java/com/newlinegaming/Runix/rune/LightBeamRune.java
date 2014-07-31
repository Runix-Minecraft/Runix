package com.newlinegaming.Runix.rune;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;

import com.newlinegaming.Runix.AbstractRune;
import com.newlinegaming.Runix.WorldXYZ;

public class LightBeamRune extends AbstractRune {
    
    public LightBeamRune() {
        runeName = ("Lightbeam");
    }

    @Override
    protected Block[][][] runicTemplateOriginal() {
       Block GLOW = Blocks.glowstone;
        return new Block[][][] {{
            {TIER, GLOW, TIER},
            {GLOW, TIER, TIER},
            {TIER, GLOW, TIER}
        }};
    }

    @Override
    public boolean isFlatRuneOnly() {
        return false;
    }

    @Override
    public void execute(WorldXYZ coords, EntityPlayer player) {
        // TODO Work on placement of the fake air block

    }

}
