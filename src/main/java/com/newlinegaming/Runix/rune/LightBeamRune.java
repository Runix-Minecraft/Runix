package com.newlinegaming.Runix.rune;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;

import com.newlinegaming.Runix.AbstractRune;
import com.newlinegaming.Runix.SigBlock;
import com.newlinegaming.Runix.WorldXYZ;
import com.newlinegaming.Runix.block.ModBlock;

public class LightBeamRune extends AbstractRune {
    
    public LightBeamRune() {
        runeName = ("Lightbeam");
    }

    @Override
    public Block[][][] runicTemplateOriginal() {
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
        Block INK = getTierInkBlock(coords);
        Block GLOW = Blocks.glowstone;
        Block FAKE = ModBlock.fakeAirBeam;
        Block[][][] outcome = new Block[][][]{{
            {INK, GLOW, INK},
            {GLOW, FAKE, GLOW},
            {INK,GLOW, GLOW}
        }};
        coords = coords.copyWithNewFacing(1);
        aetherSay(player, "created");
        HashMap<WorldXYZ, SigBlock> stamp = patternToShape(outcome, coords);
        if(stampBlockPattern(stamp, player))
            accept(player);

    }
    
    public String getRuneName() {
        return "Lightbeam";
    }

}
