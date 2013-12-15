package com.newlinegaming.Runix;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

/** This class contains the basic functions that Runes will use to execute their functions.  Any reusable code or concepts should go in
 * AbstractRune and not in the individual Runes.  This will make it easy to create new and custom runes as well as making the child classes
 * as thin as possible.
 */
public abstract class AbstractRune {
	
	public int[][][] blockPattern;  //Josiah: this pattern should never actually be used
	enum Direction {UP, DOWN, NORTH, EAST, SOUTH, WEST};
	
	public AbstractRune(){}
	
	public abstract void execute(EntityPlayer player, int worldX, int worldY, int worldZ);//I'm passing the player instead of World so that Runes can later affect the Player
	
	/**This method takes a 3D block Pattern and simply stamps it on the world with coordinates centered on WorldXYZ.  
	 * It should only be used on shapes with odd numbered dimensions.  This will also delete blocks if the template 
	 * calls for 0 (AIR).
	 * @param template
	 * @param player used to check for build permissions.  Player also provides worldObj.
	 * @param worldX
	 * @param worldY
	 * @param worldZ
	 * @return Returns false if the operation was blocked by build protection.  Currently always true.
	 */
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
	
	
	protected void safelyMovePlayer(EntityPlayer player, ChunkCoordinates coords) {
		safelyMovePlayer(player, coords, Direction.UP);
	}
	
	/**This method should be used for any teleport or similar move that may land the player in some blocks.
	 * 
	 * @param player
	 * @param coords Target destination
	 * @param direction to move in if they encounter blocks
	 */
	protected void safelyMovePlayer(EntityPlayer player, ChunkCoordinates coords, Direction direction) {
//		coords.posY = player.worldObj.getFirstUncoveredBlock(coords.posX, coords.posZ);
		while( (player.worldObj.getBlockId(coords.posX, coords.posY, coords.posZ) != 0 
				|| player.worldObj.getBlockId(coords.posX, coords.posY+1, coords.posZ) != 0) && coords.posY < 255)
			coords.posY += 1; 
		
		player.setPosition(coords.posX, coords.posY+2, coords.posZ);
		//TODO: check for Lava, fire, and void
	}
}
