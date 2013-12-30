package com.newlinegaming.Runix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

/** This class contains the basic functions that Runes will use to execute their functions.  Any reusable code or concepts should go in
 * AbstractRune and not in the individual Runes.  This will make it easy to create new and custom runes as well as making the child classes
 * as thin as possible.
 */
public abstract class AbstractRune {
	
    protected int energy = 0;
	enum Direction {UP, DOWN, NORTH, EAST, SOUTH, WEST};// vectors[(int)Direction.UP] = new int[]{0,1,0};
	
    public static final int TIER = -1; //Tier
    public static final int SIGR = -2; //Signature block
    public static final int NONE = -3; //Non-Tier, Tier 0
    //Please note: putting 0 in a blockPattern() requires AIR, not simply Tier 0
    public static final int ENTY = -4; //Entity blocks with special data like heads, picture frames, ect... 
    //Josiah: I'm not sure what to do with ENTY? 
    public static final int KEY = -5; //required to be in the middle of the rune
	
	public AbstractRune(){}

	public abstract int[][][] blockPattern();
	
	/** Executes the main function of a given Rune.  If the Rune is persistent, it will store XYZ and other salient
	 * information for future use.  Each Rune class is responsible for keeping track of the information it needs in
	 * a (possibly static) class variable.
	 * @param coords World and xyz that Rune was activated in.
	 * @param player We pass the player instead of World so that Runes can later affect the Player
	 */
	public abstract void execute(WorldXYZ coords, EntityPlayer player);
	
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
	protected boolean stampBlockTemplate(int[][][] template, EntityPlayer player, WorldXYZ coords)
	{//TODO this can be changed to iterating over HashMap<WorldXYZ, SigBlock> to match standards elsewhere
		for (int y = 0; y < template.length; y++) {
			for (int z = 0; z < template[y].length; z++) {
				for (int x = 0; x < template[y][z].length; x++) {
                    WorldXYZ target = coords.offset(-template[y][z].length / 2 + x,  -y,  -template[y].length / 2 + z);
					target.setBlockId( template[y][z][x] );
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
	
	protected void teleportPlayer(EntityPlayer player, WorldXYZ coords) throws NotEnoughRunicEnergyException {
		teleportPlayer(player, coords, Direction.UP);
	}
	
	/**This method should be used for any teleport or similar move that may land the player in some blocks.
	 * @param player
	 * @param coords Target destination
	 * @param direction to move in if they encounter blocks
	 * @throws NotEnoughRunicEnergyException 
	 */
	protected void teleportPlayer(EntityPlayer subject, WorldXYZ loc, Direction direction) throws NotEnoughRunicEnergyException {
	    WorldXYZ coords = new WorldXYZ(loc);//it's important to give it NEW coords object because it will be changed
        while ((coords.worldObj.getBlockId(coords.posX, coords.posY, coords.posZ) != 0 
				|| coords.worldObj.getBlockId(coords.posX, coords.posY+1, coords.posZ) != 0) && coords.posY < 255)
			coords.posY += 1; 
        //TODO: distance should be calculated after the Nether -> Overworld transform has been done
        spendEnergy((int)( coords.getDistanceSquaredToChunkCoordinates(new WorldXYZ(subject)) * Tiers.movementPerMeterCost));
        
        if(!coords.worldObj.equals(subject.worldObj))// && !subject.worldObj.isRemote)
            subject.travelToDimension(coords.worldObj.provider.dimensionId);
		subject.setPositionAndUpdate(coords.posX+0.5, coords.posY+1.5, coords.posZ+0.5);//Josiah: This is Y+2 because of testing...
		System.out.println("Done Teleporting");
		//TODO: check for Lava, fire, and void
	}
	
    /* Example Code:
     * @Override
    public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3) {
        Side side = FMLCommonHandler.instance().getEffectiveSide();
        if (side == Side.SERVER) {

            if (var3 instanceof EntityPlayerMP) {
                WorldServer worldserver = (WorldServer) var2;
                EntityPlayerMP var4 = (EntityPlayerMP) var3;
                if (var3.ridingEntity == null && var3.riddenByEntity == null && var3 instanceof EntityPlayer && var4.dimension != 0) {
                    var4.mcServer.getConfigurationManager().transferPlayerToDimension(var4, 20, new TeleporterAlzairio(worldserver));
                }
            }
        }
        return var1;
    }*/	
	
	
	/** returns the unique name of the rune */
	public abstract String getRuneName();
	
	public static void aetherSay(EntityPlayer recipient, String message)
	{
	    if(recipient.worldObj.isRemote && recipient != null)
	        recipient.sendChatToPlayer(ChatMessageComponent.createFromText(message));
	    else
	        System.out.println(message);
	}

    public void aetherSay(World worldObj, String message) {
        if(worldObj.isRemote)
            Minecraft.getMinecraft().thePlayer.addChatMessage(message); 
        else
            System.out.println(message);
    }
	
	/**Checks to see if there is a block match for the Rune blockPattern center at 
	 * WorldXYZ coords.  
	 * @return true if there is a valid match
	 */
    public boolean checkRunePattern(WorldXYZ coords) {
        int [][][] pattern = blockPattern();
        int tierID = 0;
        int inkID = getTierInkBlock(coords);
        if( Tiers.isTier0(inkID) )
            return false;
        for (int y = 0; y < pattern.length; y++) {
            for (int z = 0; z < pattern[y].length; z++) {
                for (int x = 0; x < pattern[y][z].length; x++) {
                    WorldXYZ target = coords.offset(-pattern[y][z].length / 2 + x,  -y,  -pattern[y].length / 2 + z);
                    // World coordinates + relative offset + half the size of the rune (for middle)
                    // "-y" the activation and "center" block for 3D runes is the top layer, at the moment
                    int blockID = target.getBlockId();
                    int patternID = pattern[y][z][x];
                    // Handle special Template Values
                    switch(patternID){
                        case NONE: 
                            if( !Tiers.isTier0(blockID) )
                                return false; 
                            break;
                        case TIER:
                            if( blockID != inkID ){
//                                aetherSay(coords.worldObj, "Found " + blockID + " ink is " + inkID);
                                return false; //inconsistent Tier block
                            }
                            break;
                        case SIGR: 
                            if( blockID == inkID )
                                return false; //you can't use your ink as part of your signature, it ruins the shape
                            break;
                        case KEY:
                            if( !target.equals(coords) || Tiers.isTier0(blockID) )//key block must be center block
                                return false;
//                                return false; //can be ink, or SIGR but not T0
                            break;
                        default:
                            if (patternID < 0) //Josiah: Make sure you added "break" if you add new special numbers
                                aetherSay(coords.worldObj, "ERROR: This rune is using an unaccounted for number!");
                            if (blockID != patternID){//normal block
//                                aetherSay(coords.worldObj, "Found " + blockID + " expected " + patternID);
                                return false;
                            }
                            break;
                        
                    }
                }
            }
        }
        return true;
    }

    protected int getTier(WorldXYZ coords){
        int blockID = getTierInkBlock(coords);
        return blockID != -1 ? Tiers.getTier(blockID) : 1;
    }
    
    protected int getTierInkBlock(WorldXYZ coords) {
        int [][][] pattern = blockPattern();
        for (int y = 0; y < pattern.length; y++) {
            for (int z = 0; z < pattern[y].length; z++) {
                for (int x = 0; x < pattern[y][z].length; x++) {
                    if( pattern[y][z][x] == TIER ){
                        WorldXYZ target = coords.offset(-pattern[y][z].length / 2 + x,  -y,  -pattern[y].length / 2 + z);
                        return target.getBlockId();
                    }
                }
            }
        }
        return -1; //There was no TIER mentioned in the pattern
    }

    /** Call accept() once you are sure the rune will be executed to tell the player it was successful.
     */
    protected void accept(EntityPlayer player) {
        aetherSay(player, EnumChatFormatting.GREEN + getRuneName() + " Accepted.");
    }

    /**This will return an empty list if the activation would tear a structure in two. */
    public HashMap<WorldXYZ, SigBlock> conductanceStep(WorldXYZ startPoint, int maxDistance) {
        HashMap<WorldXYZ, SigBlock> workingSet = new HashMap<WorldXYZ, SigBlock>();
        HashSet<WorldXYZ> activeEdge;
        HashSet<WorldXYZ> nextEdge = new HashSet<WorldXYZ>();
        workingSet.put(startPoint, startPoint.getSigBlock());
        nextEdge.add(startPoint);
        
        for(int iterationStep = maxDistance+1; iterationStep > 0; iterationStep--) {
            activeEdge = nextEdge;
            nextEdge = new HashSet<WorldXYZ>();
          //tear detection: this should be empty by the last step
            if(iterationStep == 1 && activeEdge.size() != 0) 
                return new HashMap<WorldXYZ, SigBlock>();
            
            for(WorldXYZ block : activeEdge) {
                ArrayList<WorldXYZ> neighbors = block.getNeighbors();
                for(WorldXYZ n : neighbors) {
                    int blockID = n.getBlockId();
                    // && blockID != 0 && blockID != 1){  // this is the Fun version!
                    if( !workingSet.keySet().contains(n) && !Tiers.isNatural(blockID) ) {
                        //TODO: possible slow down = long list of natural blocks
                        workingSet.put(n, n.getSigBlock());
                        nextEdge.add(n);
                    }
                }
            }
        }
        return workingSet;
    }

    protected boolean shapeCollides(HashMap<WorldXYZ, SigBlock> shape, int dX, int dY, int dZ) {
        for(WorldXYZ start : shape.keySet()){
            WorldXYZ target = start.offset(dX, dY, dZ);
            if( target.getBlockId() != 0 && !shape.containsKey(target) )
                return true; //TODO: check for isCrushable
        }
        return false;
    }

    protected boolean shapeCollides(HashMap<WorldXYZ, WorldXYZ> move) {
        for(WorldXYZ newPos : move.values()){
            if( !move.containsKey(newPos) && newPos.getBlockId() != 0 )//doesn't overlap with the old position
                return true; //TODO: check for isCrushable
        }
        return false;
    }

    public void moveMagic(Collection<WorldXYZ> blocks, int dX, int dY, int dZ) {
        //Default behavior is nothing.  Override this for persistent runes
    }
    
    public void moveMagic(HashMap<WorldXYZ, WorldXYZ> positionsMoved) {
        // Default behavior is nothing. Override this for persistent runes
    }

    /**This is essentially a way to make iterating over blockPatterns much easier by
     * enabling a single for loop:  for(WorldXYZ target : shape.keySet)
     * @param template
     * @param centerPoint
     * @return
     */
    HashMap<WorldXYZ, SigBlock> templateToShape(int[][][] template, WorldXYZ centerPoint){
        HashMap<WorldXYZ, SigBlock> shape = new HashMap<WorldXYZ, SigBlock>();
        for (int y = 0; y < template.length; y++) {
            for (int z = 0; z < template[y].length; z++) {
                for (int x = 0; x < template[y][z].length; x++) {
                    WorldXYZ target = centerPoint.offset(-template[y][z].length / 2 + x,  -y,  -template[y].length / 2 + z);
                    shape.put(target, new SigBlock(template[y][z][x],0));
                }
            }
        }
        return shape;
    }
    
    /**Removes the shape and adds its block energy to the rune*/
    protected void consumeRune(WorldXYZ coords) {
        HashMap<WorldXYZ, SigBlock> shape = templateToShape( blockPattern(), coords);
        for( WorldXYZ target : shape.keySet()){
            //for each block, get blockID
            int blockID = target.getBlockId();
            if(shape.get(target).blockID == NONE)
                continue; // we don't consume these
            energy += Tiers.getEnergy(blockID);//convert ID into energy
            target.setBlockId(0);// delete the block
        }
        System.out.println(getRuneName() + " energy: " + energy);
    }

    /**Removes the shape and adds its block energy to the rune*/
    protected void consumeRune(Collection<WorldXYZ> shape) {
        for(WorldXYZ target : shape){
            int blockID = target.getBlockId();
            energy += Tiers.getEnergy(blockID);//convert ID into energy
            target.setBlockId(0);// delete the block
        }
        System.out.println(getRuneName() + " energy: " + energy);
    }
    
    public void setBlockId(WorldXYZ coords, int blockID) throws NotEnoughRunicEnergyException {
        if( blockID == 0 )//this is actually breaking, not paying for air
            spendEnergy(Tiers.blockBreakCost);
        else
            spendEnergy(Tiers.getEnergy(blockID));        
        coords.setBlockId(blockID);
    }

    /**
     * @param energyCost 
     * @throws NotEnoughRunicEnergyException
     */
    protected void spendEnergy(int energyCost) throws NotEnoughRunicEnergyException {
        if( energy < energyCost){
            throw new NotEnoughRunicEnergyException();
        }
        energy -= energyCost;
    }

    /**This is a minature convenience version of moveShape(moveMapping) for single blocks
     * @throws NotEnoughRunicEnergyException */
    public void moveBlock(WorldXYZ coords, WorldXYZ newPos) throws NotEnoughRunicEnergyException {
        newPos.setBlockId(coords.getSigBlock());
        coords.setBlockId(0);
        spendEnergy(Tiers.blockMoveCost);
        
        HashMap<WorldXYZ, WorldXYZ> moveMapping = new HashMap<WorldXYZ, WorldXYZ>(1, 1.0f);//tiny HashMap!
        moveMapping.put(coords, newPos);
        RuneHandler.getInstance().moveMagic(moveMapping);
    }

    protected void consumeKeyBlock(WorldXYZ coords) {
        if(Tiers.getTier(coords.getBlockId()) > 1){
            List<WorldXYZ> wrapper = Arrays.asList(coords);
            consumeRune(wrapper);
            coords.setBlockId(Block.cobblestone.blockID);//we don't want air sitting here
        }
    }

    protected HashMap<WorldXYZ, SigBlock> moveShape(HashMap<WorldXYZ, SigBlock> structure, int dX, int dY, int dZ) throws NotEnoughRunicEnergyException {
        spendEnergy(Tiers.blockMoveCost * structure.size());
        return Util_Movement.moveShape(structure, dX, dY, dZ);
    }

}
