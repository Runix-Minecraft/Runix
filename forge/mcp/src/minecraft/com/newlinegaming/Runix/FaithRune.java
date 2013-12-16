package com.newlinegaming.Runix;

import net.minecraft.entity.player.EntityPlayer;

public class FaithRune extends AbstractRune{

	public int[][][] blockPattern(){
		return new int [][][] 
           {{{0,0,0},
             {0,4,0},
             {0,0,0}},
            {{4,0,4},
             {0,4,0},
             {4,0,4}}};
	}

	public void execute(EntityPlayer player, WorldCoordinates coords){}

}
