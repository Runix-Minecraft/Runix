package com.newlinegaming.Runix;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;

public class CompassRune extends AbstractRune{

	public int[][][] blockPattern(){
		return new int [][][] 
            {{{TIER, 0 ,TIER},
              { 0 ,TIER, 0 },
              {TIER, 0 ,TIER}}}; //This is AIR 0 on purpose
	}

	public void execute(WorldXYZ coords, EntityPlayer player){
	    int ink = getTierInkBlock(coords);
		int[][][] compassOutcome = new int [][][]
				{{{ 0 ,ink, 0 }, //TODO: pass meta-data
				  {ink, 0 ,ink},
				  {ink, 0 ,ink}}};
		stampBlockTemplate(compassOutcome, player, coords);
		accept(player);
	}
	
	public String getRuneName()
	{
		return "Compass";
	}
	

}
