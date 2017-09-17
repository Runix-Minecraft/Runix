package com.newlinegaming.Runix.rune;

import java.util.HashMap;

import com.newlinegaming.Runix.AbstractRune;
import com.newlinegaming.Runix.SigBlock;
import com.newlinegaming.Runix.WorldXYZ;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;

public class CompassRune extends AbstractRune{


    public CompassRune(){
        runeName = "Compass";
    }
    
    public Block[][][] runicTemplateOriginal(){
        Block air = Blocks.AIR;//This is AIR 0 on purpose
        return new Block [][][]
            {{
              {TIER, air, air , air,TIER},
              {air, TIER, air ,TIER, air},
              {air,  air ,TIER, air, air},
              {air, TIER, air ,TIER, air},
              {TIER, air, air , air,TIER}
              }}; 
    }

    public void execute(WorldXYZ coords, EntityPlayer player){
        Block ink = getTierInkBlock(coords);
        Block air = Blocks.AIR;
        Block[][][] compassOutcome = new Block[][][]
                {{{air,ink,air},
                  {ink,air,ink},
                  {ink,air,ink}}};
        coords = coords.copyWithNewFacing(1);
        HashMap<WorldXYZ, SigBlock> stamp = patternToShape(compassOutcome, coords);
        if(stampBlockPattern(stamp, player))
            accept(player);
    }

    public String getRuneName()
    {
        return "Compass";
    }

    public boolean isFlatRuneOnly() {
        return true;
    }

}
