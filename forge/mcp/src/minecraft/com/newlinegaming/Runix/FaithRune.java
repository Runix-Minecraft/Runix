package com.newlinegaming.Runix;

import cpw.mods.fml.common.network.Player;

public class FaithRune extends AbstractRune{

	public FaithRune(){
		this.blockPattern = new int [][][] 
           {{{0,0,0},
             {0,4,0},
             {0,0,0}},
            {{4,0,4},
             {0,4,0},
             {4,0,4}}};
	}

	public void execute(Player player, int x, int y, int z){}

}
