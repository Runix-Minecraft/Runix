package com.newlinegaming.Runix;

import net.minecraft.entity.player.EntityPlayer;

public class CompassRune extends AbstractRune{

	public CompassRune(){
		this.blockPattern = new int [][][] 
            {{{4,0,4},
              {0,4,0},
              {4,0,4}}};
	}

	public void execute(EntityPlayer player, int x, int y, int z){
//		par1World.setBlock(alterX + par2, alterY + par3, alterZ + par4, Block.sandStone.blockID);
	}
}
