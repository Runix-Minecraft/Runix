package com.newlinegaming.Runix;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

/** This class contains the basic functions that Runes will use to execute their functions.  Any reusable code or concepts should go in
 * AbstractRune and not in the individual Runes.  This will make it easy to create new and custom runes as well as making the child classes
 * as thin as possible.
 */
public abstract class AbstractRune {
	
	enum Direction {UP, DOWN, NORTH, EAST, SOUTH, WEST};// vectors[(int)Direction.UP] = new int[]{0,1,0};
	
	//Josiah: I'd be happy to find a more compact way to right this,
	//but enum requires .value to be used as an int, which we need.
    public static final int TIER = -1; //Tier
    public static final int SIGN = -2; //Signature block
    public static final int NONE = -3; //Non-Tier, Tier 0
    public static final int ENTY = -4; //Entity blocks with special data like heads, picture frames, ect... 
    //Please note: putting 0 in a blockPattern() requires AIR, not simply Tier 0
	
	public AbstractRune(){}

	public abstract int[][][] blockPattern();
	
	/** Executes the main function of a given Rune.  If the Rune is persistent, it will store XYZ and other salient
	 * information for future use.  Each Rune class is responsible for keeping track of the information it needs in
	 * a (possibly static) class variable.
	 * @param player We pass the player instead of World so that Runes can later affect the Player
	 * @param coords World and xyz that Rune was activated in.
	 */
	public abstract void execute(EntityPlayer player, WorldCoordinates coords);//
	
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
	protected boolean stampBlockTemplate(int[][][] template, EntityPlayer player, WorldCoordinates coords)
	{
		World world = player.worldObj;
		//TODO: Josiah: this loop is lifted from RuneHandler.checkRunePattern.  Abstract this into a shape iterator
		for (int y = 0; y < template.length; y++) {
			for (int z = 0; z < template[y].length; z++) {
				for (int x = 0; x < template[y][z].length; x++) {
                    //World coordinates + relative offset + half the size of the rune (for middle)
					int blockX = coords.posX - template[y][z].length /2 + x;
					int blockY = coords.posY - y; //Josiah: the activation and "center" block for 3D runes is the top layer, at the moment
					int blockZ = coords.posZ - template[y].length /2 + z;

					world.setBlock(blockX, blockY, blockZ, template[y][z][x]);
                }
            }
		}
		return true;//TODO: build permission checking
	}
	
	/** This method takes the player and the rune, and verifies that a rune can be used. to go with perms/disabled runes.txt or whatever
	 * @param player - the caster
	 * @param rune - the rune being cast
	 * @return
	 */
	static boolean runeAllowed(EntityPlayer player, AbstractRune rune)
	{
		// arbi
		player.sendChatToPlayer(ChatMessageComponent.createFromText(EnumChatFormatting.GREEN+rune.getRuneName()+ " accepted"));
		return true;
	}
	
	protected void safelyMovePlayer(EntityPlayer player, WorldCoordinates coords) {
		safelyMovePlayer(player, coords, Direction.UP);
	}
	
	/**This method should be used for any teleport or similar move that may land the player in some blocks.
	 * @param player
	 * @param coords Target destination
	 * @param direction to move in if they encounter blocks
	 */
	protected void safelyMovePlayer(EntityPlayer player, WorldCoordinates coords, Direction direction) {
		while( (player.worldObj.getBlockId(coords.posX, coords.posY, coords.posZ) != 0 
				|| player.worldObj.getBlockId(coords.posX, coords.posY+1, coords.posZ) != 0) && coords.posY < 255)
			coords.posY += 1; 
		
		player.setPosition(coords.posX+0.5, coords.posY+1.5, coords.posZ+0.5);//Josiah: This is Y+2 because of testing...
		//TODO: check for Lava, fire, and void
	}
	
	/** returns the unique name of the rune */
	public abstract String getRuneName();
	
	public static void message(EntityPlayer player, String message)
	{
		player.sendChatToPlayer(ChatMessageComponent.createFromText(message));
	}
}
