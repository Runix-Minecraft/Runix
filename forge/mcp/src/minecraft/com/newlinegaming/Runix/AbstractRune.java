package com.newlinegaming.Runix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

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
	
    public static final int TIER = -1; //Tier
    public static final int SIGR = -2; //Signature block
    public static final int NONE = -3; //Non-Tier, Tier 0
    //Please note: putting 0 in a blockPattern() requires AIR, not simply Tier 0
    public static final int KEY = -4; //required to be in the middle of the rune
    public static final int ENTY = -5; //Entity blocks with special data like heads, picture frames, ect... 
    //Josiah: I'm not sure what to do with ENTY? 
	
	public AbstractRune(){}

	/**Required implementation to determine what arrangement of blocks maps to your rune.  Once this is
	 * defined in your class, never use it.  Use runicFormulae() instead.
	 */
	protected abstract int[][][] runicTemplateOriginal();

	public abstract boolean isFlatRuneOnly();

    /** Use this method to check Rune template compliance, not runicTemplateOriginal().
	 * This method will take the facing of coords and use it to match orientation for vertical runes.
	 * @param coords world coordinates and facing to check against the rune
	 * @return WorldXYZ is the coordinates being checked.  Use WorldXYZ.getBlockID().  SigBlock is 
	 * the runeTemplate for that block, which can be special values like TIER or KEY.
	 */
	protected HashMap<WorldXYZ, SigBlock> runicFormulae(WorldXYZ coords){
	    if(isFlatRuneOnly())
	        coords = coords.overrideFacing(1); //we need a new object so we don't side-effect other Runes
	    return patternToShape(runicTemplateOriginal(), coords); 
	}
	
	/** Executes the main function of a given Rune.  If the Rune is persistent, it will store XYZ and other salient
	 * information for future use.  Each Rune class is responsible for keeping track of the information it needs in
	 * a static class variable.
	 * @param coords World and xyz that Rune was activated in.
	 * @param player We pass the player instead of World so that Runes can later affect the Player
	 */
	public abstract void execute(WorldXYZ coords, EntityPlayer player);
	
	/**This method takes a 3D block Pattern and simply stamps it on the world with coordinates centered on WorldXYZ.  
	 * It should only be used on shapes with odd numbered dimensions.  This will also delete blocks if the template 
	 * calls for 0 (AIR).
	 * @param pattern The blockPattern to be stamped.
	 * @param player used to check for build permissions.  Player also provides worldObj.
	 * @param worldX
	 * @param worldY
	 * @param worldZ
	 * @return Returns false if the operation was blocked by build protection.  Currently always true.
	 */
	protected boolean stampBlockPattern(HashMap<WorldXYZ, SigBlock> stamp, EntityPlayer player)
	{
		for(WorldXYZ target : stamp.keySet())
		    target.setBlockId( stamp.get(target) );
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
	
	/**This will safely teleport the player by scanning in the coords.face direction for 2 AIR blocks that drop the player
	 * less than 20 meters onto something that's not fire or lava.
	 * This method should be used for any teleport or similar move that may land the player in some blocks.
	 * @param player
	 * @param coords Target destination
	 * @param direction to move in if they encounter blocks
	 * @throws NotEnoughRunicEnergyException 
	 */
	protected void teleportPlayer(EntityPlayer subject, WorldXYZ coords) throws NotEnoughRunicEnergyException {
	    Vector3 direction = Vector3.facing[coords.face];
	    for(int tries = 0; tries < 100; ++tries)
	    {
	        if( (coords.posY < 255 && coords.posY > 0) // coords are in bounds
	                && coords.worldObj.getBlockId(coords.posX, coords.posY, coords.posZ) == 0 
	                && coords.worldObj.getBlockId(coords.posX, coords.posY+1, coords.posZ) == 0)//two AIR blocks
	        {  
	            for(int drop = 1; drop < 20 && coords.posY-drop > 0; ++drop)//less than a 20 meter drop
	            {//begin scanning downward
	                int block = coords.worldObj.getBlockId(coords.posX, coords.posY-drop, coords.posZ);
	                if(block != 0)
	                { //We found something not AIR
    	                if( block == Block.lavaStill.blockID || block == Block.lavaMoving.blockID//check for Lava, fire, and void  
    	                        || block == Block.fire.blockID){//if we teleport now, the player will land on an unsafe block
                            break; //break out of the drop loop and proceed on scanning a new location
                        }
    	                else if(coords.offset(0, -drop, 0).isSolid()){
    	                    //distance should be calculated uses the Nether -> Overworld transform
    	                    WorldXYZ dCalc = new WorldXYZ(subject);
    	                    if(subject.worldObj.provider.isHellWorld  && !coords.worldObj.provider.isHellWorld){ //leaving the Nether
    	                        dCalc.posX *= 8;
    	                        dCalc.posZ *= 8;
    	                    }else if (!subject.worldObj.provider.isHellWorld  && coords.worldObj.provider.isHellWorld){// going to the Nether
                                dCalc.posX /= 8;
                                dCalc.posZ /= 8;
    	                    }
    	                    spendEnergy((int)( coords.getDistance(dCalc) * Tiers.movementPerMeterCost));
    
    	                    if(!coords.worldObj.equals(subject.worldObj))// && !subject.worldObj.isRemote)
    	                        subject.travelToDimension(coords.worldObj.provider.dimensionId);
    	                    subject.setPositionAndUpdate(coords.posX+0.5, coords.posY, coords.posZ+0.5);
    	                    System.out.println("Done Teleporting");
    	                    return;
    	                }//we've found something that's not AIR, but it's not dangerous so just pass through it and keep going
	                }
	            } 
	        }
            coords = coords.offset(direction);
	    }
	    System.out.println("There was no safe place to put your character.");
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
        int tierID = 0;
        int inkID = getTierInkBlock(coords);
        if( Tiers.isTier0(inkID) )
            return false;

        HashMap<WorldXYZ, SigBlock> shape = runicFormulae(coords);
        for (WorldXYZ target : shape.keySet()) 
        {
            int blockID = target.getBlockId();
            int patternID = shape.get(target).blockID;
            switch(patternID){// Handle special Template Values
                case NONE: 
                    if( !Tiers.isTier0(blockID)//Special cased torches as part of NONE so that Torch Bearer doesn't jack up your runes. 
                            && (inkID == Block.torchWood.blockID || blockID != Block.torchWood.blockID))
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
                    if( Tiers.isTier0(blockID) )//key block must be center block  !target.equals(coords) || 
                        return false;//can be ink, or SIGR but not T0
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
        return true;
    }

    protected int getTier(WorldXYZ coords){
        int blockID = getTierInkBlock(coords);
        return blockID != -1 ? Tiers.getTier(blockID) : 1;
    }
    
    protected int getTierInkBlock(WorldXYZ coords) {
        HashMap<WorldXYZ, SigBlock> shape = runicFormulae(coords);
        for (WorldXYZ target : shape.keySet()) {
            if (shape.get(target).blockID == TIER) {
                return target.getBlockId();
            }
        }
        return -1; // There was no TIER mentioned in the pattern
    }

    /** Call accept() once you are sure the rune will be executed to tell the player it was successful.
     */
    protected void accept(EntityPlayer player) {
        aetherSay(player, EnumChatFormatting.GREEN + getRuneName() + " Accepted.");
    }

    /**This will return an empty list if the activation would tear a structure in two. */
    public HashSet<WorldXYZ> conductanceStep(WorldXYZ startPoint, int maxDistance) {
        HashSet<WorldXYZ> workingSet = new HashSet<WorldXYZ>();
        HashSet<WorldXYZ> activeEdge;
        HashSet<WorldXYZ> nextEdge = new HashSet<WorldXYZ>();
        workingSet.add(startPoint);
        nextEdge.add(startPoint);
        
        for(int iterationStep = maxDistance+1; iterationStep > 0; iterationStep--) {
            activeEdge = nextEdge;
            nextEdge = new HashSet<WorldXYZ>();
          //tear detection: this should be empty by the last step
            if(iterationStep == 1 && activeEdge.size() != 0) 
                return new HashSet<WorldXYZ>();
            
            for(WorldXYZ block : activeEdge) {
                ArrayList<WorldXYZ> neighbors = block.getNeighbors();
                for(WorldXYZ n : neighbors) {
                    int blockID = n.getBlockId();
                    // && blockID != 0 && blockID != 1){  // this is the Fun version!
                    if( !workingSet.contains(n) && !Tiers.isNatural(blockID) ) {
                        workingSet.add(n);
                        nextEdge.add(n);
                    }
                }
            }
        }
        return workingSet;
    }

    protected boolean shapeCollides(HashMap<WorldXYZ, WorldXYZ> move) {
        for(WorldXYZ newPos : move.values()){
            if( !move.containsKey(newPos) //doesn't overlap with the old position
                    && newPos.getBlockId() != 0 //AIR
                    && !Tiers.isCrushable(newPos.getBlockId()) ) //Something's there, but squish it anyways
                return true;
        }
        return false;
    }

    public void moveMagic(HashMap<WorldXYZ, WorldXYZ> positionsMoved) {
        // Default behavior is nothing. Override this for persistent runes
    }

    /**
     * This is essentially a way to make iterating over blockPatterns much
     * easier by enabling a single for loop: for(WorldXYZ target : shape.keySet) 
     * blockPattern() "iterator" actually a HashMap<WorldXYZ, SigBlock>. The
     * WorldXYZ serves as a comparison for world coordinates and the world block
     * can be had through WorldXYZ.getBlockID(). The HashMap SigBlock is
     * actually the runic formulae including special values like TIER.
     * @param pattern
     * @param centerPoint
     * @return
     */
    HashMap<WorldXYZ, SigBlock> patternToShape(int[][][] pattern, WorldXYZ centerPoint){
        // World coordinates + relative offset + half the size of the rune (for middle)
        // "-y" the activation and "center" block for 3D runes is the top layer, at the moment
        HashMap<WorldXYZ, SigBlock> shape = new HashMap<WorldXYZ, SigBlock>();
        for (int y = 0; y < pattern.length; y++) {
            for (int z = 0; z < pattern[y].length; z++) {
                for (int x = 0; x < pattern[y][z].length; x++) {
                    WorldXYZ target; 
                    //switch on different orientations
                    switch(centerPoint.face){
                    case 1: //laying flat activated from top or bottom
                    case 0:
                        target = centerPoint.offset(-pattern[y][z].length / 2 + x,  -y,  -pattern[y].length / 2 + z);//TODO: clockwise vs CCW?
                        break;
                    case 2://NORTH or SOUTH which points along the z axis
                    case 3://this means that flat runes (XZ runes) will extend along XY
                        target = centerPoint.offset(-pattern[y][z].length / 2 + x,  pattern[y].length / 2 - z,  -y );//TODO: +y for SOUTH
                        break;
                    case 4://WEST or EAST facing
                    case 5://flat runes extend along the ZY plane
                        target = centerPoint.offset(-y,  pattern[y][z].length / 2 - x,  -pattern[y].length / 2 + z);
                        break;
                    default:
                        System.err.println("Block facing not recognized: " + centerPoint.face + " should be 0-5.");
                        target = centerPoint;
                    }
                    shape.put(target, new SigBlock(pattern[y][z][x],0));
                    }
                }
            }
        return shape;
    }
    
    /**Removes the shape and adds its block energy to the rune*/
    protected void consumeRune(WorldXYZ coords) {
        if(isFlatRuneOnly())
            coords = coords.overrideFacing(1);
        HashMap<WorldXYZ, SigBlock> shape = runicFormulae(coords);
        for( WorldXYZ target : shape.keySet()){
            //for each block, get blockID
            int blockID = target.getBlockId();
            if(shape.get(target).blockID == NONE)
                continue; // we don't consume these
            energy += Tiers.getEnergy(blockID);//convert ID into energy
            target.setBlockIdAndUpdate(0);// delete the block
        }
        System.out.println(getRuneName() + " energy: " + energy);
    }

    /**Removes the shape and adds its block energy to the rune*/
    protected void consumeRune(Collection<WorldXYZ> shape) {
        for(WorldXYZ target : shape){
            int blockID = target.getBlockId();
            energy += Tiers.getEnergy(blockID);//convert ID into energy
            target.setBlockIdAndUpdate(0);// delete the block
        }
        System.out.println(getRuneName() + " energy: " + energy);
    }
    
    public void setBlockIdAndUpdate(WorldXYZ coords, int blockID) throws NotEnoughRunicEnergyException {
        if( blockID == 0 )//this is actually breaking, not paying for air
            spendEnergy(Tiers.blockBreakCost);
        else
            spendEnergy(Tiers.getEnergy(blockID));        
        coords.setBlockIdAndUpdate(blockID);
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
        coords.setBlockIdAndUpdate(0);
        spendEnergy(Tiers.blockMoveCost);
        
        HashMap<WorldXYZ, WorldXYZ> moveMapping = new HashMap<WorldXYZ, WorldXYZ>(1, 1.0f);//tiny HashMap!
        moveMapping.put(coords, newPos);
        RuneHandler.getInstance().moveMagic(moveMapping);
    }

    protected boolean consumeKeyBlock(WorldXYZ coords) {
        if(Tiers.getTier(coords.getBlockId()) > 1){
            List<WorldXYZ> wrapper = Arrays.asList(coords);
            consumeRune(wrapper);
            coords.setBlockIdAndUpdate(Block.cobblestone.blockID);//we don't want air sitting here
            return true;
        }
        return false;
    }

    protected HashSet<WorldXYZ> moveShape(HashMap<WorldXYZ, WorldXYZ> moveMapping) throws NotEnoughRunicEnergyException {
        int blocksMovedToNewArea = 0;
        for(WorldXYZ point : moveMapping.values()){
            if( !moveMapping.containsKey(point) )
                ++blocksMovedToNewArea;
        }
        spendEnergy( Tiers.blockMoveCost * blocksMovedToNewArea );
        return Util_Movement.performMove(moveMapping);
    }

}
