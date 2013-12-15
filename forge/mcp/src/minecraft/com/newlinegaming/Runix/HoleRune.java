package com.newlinegaming.Runix;

import cpw.mods.fml.common.network.Player;

public class HoleRune extends AbstractRune{

	public HoleRune(){
		this.blockPattern = new int [][][] 
            {{{4,4,4},
              {4,2,4},
              {4,4,4}}};
	}

	public void execute(Player player, int x, int y, int z){}

}
