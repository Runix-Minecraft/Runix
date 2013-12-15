package com.newlinegaming.Runix;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public abstract class AbstractRune {
	/** This class contains the basic functions that Runes will use to execute their functions.  Any reusable code or concepts should go in
	 * AbstractRune and not in the individual Runes.  This will make it easy to create new and custom runes as well as making the child classes
	 * as thin as possible.
	 */
	
	public int[][][] blockPattern;  //Josiah: this pattern should never actually be used
	
	public AbstractRune(){}
	
	public abstract void execute(EntityPlayer player, int x, int y, int z);//I'm passing the player instead of World so that Runes can later affect the Player
	
	protected boolean stampBlockTemplate(int[][][] template, EntityPlayer player, int worldX, int worldY, int worldZ)
	{
		World world = player.worldObj;
		//TODO: Josiah: this loop is lifted from RuneHandler.checkRunePattern.  Abstract this into a shape iterator
		for (int y = 0; y < template.length; y++) {
			for (int z = 0; z < template[y].length; z++) {
				for (int x = 0; x < template[y][z].length; x++) {
                    //World coordinates + relative offset + half the size of the rune (for middle)
					int blockX = worldX - template[y][z].length /2 + x;
					int blockY = worldY - y; //Josiah: the activation and "center" block for 3D runes is the top layer, at the moment
					int blockZ = worldZ - template[y].length /2 + z;

					world.setBlock(blockX, blockY, blockZ, template[y][z][x]);
                }
            }
		}
		return true;//TODO: build permission checking
	}
}
