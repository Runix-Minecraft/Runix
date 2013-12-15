package com.newlinegaming.Runix;

import cpw.mods.fml.common.network.Player;

public class CompassRune extends AbstractRune{

	public CompassRune(){
		this.blockPattern = new int [][][] 
            {{{4,0,4},
              {0,4,0},
              {4,0,4}}};
	}

	public void execute(Player player, int x, int y, int z){
//		par1World.setBlock(alterX + par2, alterY + par3, alterZ + par4, Block.sandStone.blockID);
	}
}
