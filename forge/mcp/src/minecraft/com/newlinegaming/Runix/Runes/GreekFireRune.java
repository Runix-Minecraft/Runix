package com.newlinegaming.Runix.Runes;

import com.newlinegaming.Runix.AbstractRune;
import com.newlinegaming.Runix.Vector3;
import com.newlinegaming.Runix.WorldXYZ;
import com.newlinegaming.Runix.block.GreekFire;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;

public class GreekFireRune extends AbstractRune {
    
    public GreekFireRune(){
        runeName = "Greek Fire";
    }

    @Override
    protected int[][][] runicTemplateOriginal() {
        int FENC = Block.fenceIron.blockID;
        int LAPS = Block.blockLapis.blockID;
        return new int [][][] 
                {{{TIER,FENC,TIER},
                  {FENC,LAPS,FENC},
                  {TIER,FENC,TIER}}}; 
    }

    @Override
    public boolean isFlatRuneOnly() {
        return true;
    }

    @Override
    public void execute(WorldXYZ coords, EntityPlayer player) {
        accept(player);
        consumeRune(coords);
        coords.setBlockIdAndUpdate(Block.blockLapis.blockID);
        coords.offset(Vector3.UP).setBlockIdAndUpdate(GreekFire.blockIdBackup);//GreekFire.blockID
        //TODO set meta on Fire block for remaining energy from the Rune
    }

}
