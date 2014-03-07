package com.newlinegaming.Runix.Runes;

import java.util.HashMap;

import com.newlinegaming.Runix.AbstractRune;
import com.newlinegaming.Runix.SigBlock;
import com.newlinegaming.Runix.WorldXYZ;

import net.minecraft.entity.player.EntityPlayer;

public class CompassRune extends AbstractRune{


    public CompassRune(){
        runeName = "Compass";
    }
    
	public int[][][] runicTemplateOriginal(){
		return new int [][][] 
            {{{TIER, 0 ,TIER},
              { 0 ,TIER, 0 },
              {TIER, 0 ,TIER}}}; //This is AIR 0 on purpose
	}

	public void execute(WorldXYZ coords, EntityPlayer player){
	    int ink = getTierInkBlock(coords);
		int[][][] compassOutcome = new int [][][]
				{{{ 0 ,ink, 0 },
				  {ink, 0 ,ink},
				  {ink, 0 ,ink}}};
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
