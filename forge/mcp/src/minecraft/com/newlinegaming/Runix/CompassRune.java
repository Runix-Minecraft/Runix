package com.newlinegaming.Runix;

import net.minecraft.entity.player.EntityPlayer;

public class CompassRune extends AbstractRune{

	public int[][][] blockPattern(){
		return new int [][][] 
            {{{4,0,4},
              {0,4,0},
              {4,0,4}}};
	}

	public void execute(EntityPlayer player, int x, int y, int z){
		int[][][] compassOutcome = new int [][][]
				{{{0,4,0},
				  {4,0,4},
				  {4,0,4}}};
		stampBlockTemplate(compassOutcome, player, x, y, z);
	}

}
