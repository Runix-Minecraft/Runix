package com.newlinegaming.Runix;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

/** This class contains the basic functions that Runes will use to execute their functions.  Any reusable code or concepts should go in
 * AbstractRune and not in the individual Runes.  This will make it easy to create new and custom runes as well as making the child classes
 * as thin as possible.
 */
public abstract class AbstractRune {
	
	enum Direction {UP, DOWN, NORTH, EAST, SOUTH, WEST};// vectors[(int)Direction.UP] = new int[]{0,1,0};
	
	public AbstractRune(){}

	public abstract int[][][] blockPattern();
	
	/** Executes the main function of a given Rune.  If the Rune is persistent, it will store XYZ and other salient
	 * information for future use.  
	 * @param handler This is for accessing all the world persistence information.
	 * @param player We pass the player instead of World so that Runes can later affect the Player
	 * @param worldX
	 * @param worldY
	 * @param worldZ
	 */
	public abstract void execute(RuneHandler handler, EntityPlayer player, int worldX, int worldY, int worldZ);//
	
	/**This method takes a 3D block Pattern and simply stamps it on the world with coordinates centered on WorldXYZ.  
	 * It should only be used on shapes with odd numbered dimensions.  This will also delete blocks if the template 
	 * calls for 0 (AIR).
	 * @param template The blockPattern to be stamped.
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
	 * @param player
	 * @param coords Target destination
	 * @param direction to move in if they encounter blocks
	 */
	protected void safelyMovePlayer(EntityPlayer player, ChunkCoordinates coords, Direction direction) {
		while( (player.worldObj.getBlockId(coords.posX, coords.posY, coords.posZ) != 0 
				|| player.worldObj.getBlockId(coords.posX, coords.posY+1, coords.posZ) != 0) && coords.posY < 255)
			coords.posY += 1; 
		
		player.setPosition(coords.posX, coords.posY+2, coords.posZ);//Josiah: This is Y+2 because of testing...
		//TODO: check for Lava, fire, and void
	}

	public boolean isPersistent() {
		return false;
	}
}
