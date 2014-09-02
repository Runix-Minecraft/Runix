package com.newlinegaming.Runix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import com.newlinegaming.Runix.block.FuelBlock;
import com.newlinegaming.Runix.block.NoneBlock;
import com.newlinegaming.Runix.block.SignatureBlock;
import com.newlinegaming.Runix.block.TierBlock;
import com.newlinegaming.Runix.handlers.RuneHandler;
import com.newlinegaming.Runix.rune.BuildMasterRune;
import com.newlinegaming.Runix.rune.WaypointRune;
import com.newlinegaming.Runix.utils.Util_Movement;

/**
 * This class contains the basic functions that runes will use to execute their functions.  Any reusable code or concepts should go in
 * AbstractRune and not in the individual runes.  This will make it easy to create new and custom runes as well as making the child classes
 * as thin as possible.
 */


//TODO: Rename to BaseRune as this is the base of all runes
public abstract class AbstractRune {
	//TODO: Get rid of  unnecessary comments all comments that interrupt the code style
	
    public int energy = 0;
	
    public static final Block TIER = new TierBlock(); //Tier
    public static final Block SIGR = new SignatureBlock(); //Signature block
    public static final Block NONE = new NoneBlock(); //Non-Tier, Tier 0
    //Please note: putting 0 in a blockPattern() requires AIR, not simply Tier 0
    public static final Block FUEL = new FuelBlock(); //required to be in the middle of the rune
    
    public String runeName = null;
    public String runeLocalizedName = null;

    public boolean usesConductance = false;
	public AbstractRune(){}

	/**
     * Required implementation to determine what arrangement of blocks maps to your rune.  Once this is
	 * defined in your class, never use it.  Use runicFormulae() instead.
	 */
	protected abstract Block[][][] runicTemplateOriginal();

	public abstract boolean isFlatRuneOnly();

    /**
     * Use this method to check Rune template compliance, not runicTemplateOriginal().
	 * This method will take the facing of coords and use it to match orientation for vertical runes.
	 * @param coords world coordinates and facing to check against the rune
	 * @return WorldXYZ is the coordinates being checked.  Use WorldXYZ.getBlockID().  SigBlock is 
	 * the runeTemplate for that block, which can be special values like TIER or KEY.
	 */
	protected HashMap<WorldXYZ, SigBlock> runicFormulae(WorldXYZ coords){
	    if(isFlatRuneOnly())
	        coords = coords.copyWithNewFacing(1); //we need a new object so we don't side-effect other runes
	    return patternToShape(runicTemplateOriginal(), coords); 
	}

    /**
     * Executes the main function of a given Rune.  If the Rune is persistent, it will store XYZ and other salient
	 * information for future use.  Each Rune class is responsible for keeping track of the information it needs in
	 * a static class variable.
	 * @param coords World and xyz that Rune was activated in.
	 * @param player We pass the player instead of World so that runes can later affect the Player
     * @param forward 
	 */
	public void execute(WorldXYZ coords, EntityPlayer player, Vector3 forward) {
	    execute(coords, player); //Instant runes drop the forward parameter by default
	}

    public abstract void execute(WorldXYZ coords, EntityPlayer player);
	
	/**
     * This method takes a 3D block Pattern and simply stamps it on the world with coordinates centered on WorldXYZ.
	 * It should only be used on shapes with odd numbered dimensions.  This will also delete blocks if the template 
	 * calls for 0 (AIR).
	 * @param pattern The blockPattern to be stamped.
	 * @param player used to check for build permissions.  Player also provides worldObj.
	 * @param worldX
	 * @param worldY
	 * @param worldZ
	 * @return Returns false if the operation was blocked by build protection.  Currently always true.
	 */
	protected boolean stampBlockPattern(HashMap<WorldXYZ, SigBlock> stamp, EntityPlayer player) {
		for(WorldXYZ target : stamp.keySet())
		    target.setBlockId( stamp.get(target) );
		return true;
		//TODO: build permission checking
	}
	
	/**
     * This method takes the player and the rune, and verifies that a rune can be used. to go with perms/disabled runes.txt or whatever
	 * @param player - the caster
	 * @param rune - the rune being cast
	 * @return
	 */
	protected static boolean runeAllowed(EntityPlayer player, AbstractRune rune) {
		// arbi
//		player.sendChatToPlayer(ChatMessageComponent.createFromText(EnumChatFormatting.GREEN+rune.getRuneName()+ " accepted"));
//        player.addChatComponentMessage(IChatComponent);
		return true;
	}
	
	/**
     * This will safely teleport the player by scanning in the coords.face direction for 2 AIR blocks that drop the player
	 * less than 20 meters onto something that's not fire or lava.
	 * This method should be used for any teleport or similar move that may land the player in some blocks.
	 * @param player
	 * @param coords Target destination
	 * @param direction to move in if they encounter blocks
	 * @throws NotEnoughRunicEnergyException 
	 */
	protected void teleportPlayer(EntityPlayer player, WorldXYZ coords) throws NotEnoughRunicEnergyException {
		
	    Vector3 direction = Vector3.facing[coords.face];
	    for(int tries = 0; tries < 100; ++tries) {
	        if( (coords.posY < 255 && coords.posY > 0) // coords are in bounds
	                && coords.getWorld().getBlock(coords.posX, coords.posY, coords.posZ) == Blocks.air
	                && coords.getWorld().getBlock(coords.posX, coords.posY+1, coords.posZ) == Blocks.air)//two AIR blocks
	        {  
	            for(int drop = 1; drop < 20 && coords.posY-drop > 0; ++drop)//less than a 20 meter drop
	            {//begin scanning downward
	                Block block = coords.getWorld().getBlock(coords.posX, coords.posY - drop, coords.posZ);
	                if(block != Blocks.air)
	                { //We found something not AIR
    	                if (block == Blocks.lava || block == Blocks.flowing_lava//check for Lava, fire, and void
    	                        || block == Blocks.fire){//if we teleport now, the player will land on an unsafe block
                            break; //break out of the drop loop and proceed on scanning a new location
                        }
    	                else if(coords.offset(0, -drop, 0).isSolid()){ //we're going to land on something solid, without dying
    	                    //distance should be calculated uses the Nether -> Overworld transform
    	                    WorldXYZ dCalc = new WorldXYZ(player);
    	                    if(player.worldObj.provider.isHellWorld  && !coords.getWorld().provider.isHellWorld){ //leaving the Nether
    	                        dCalc.posX *= 8;
    	                        dCalc.posZ *= 8;
    	                    }else if (!player.worldObj.provider.isHellWorld  && coords.getWorld().provider.isHellWorld){// going to the Nether
                                dCalc.posX /= 8;
                                dCalc.posZ /= 8;
    	                    }
    	                    spendEnergy((int)( coords.getDistance(dCalc) * Tiers.movementPerMeterCost));
    
    	                    if(!coords.getWorld().equals(player.worldObj))// && !subject.worldObj.isRemote)
    	                        player.travelToDimension(coords.getWorld().provider.dimensionId);
    	                    player.setPositionAndUpdate(coords.posX+0.5, coords.posY, coords.posZ+0.5);
    	                    return;
    	                }//we've found something that's not AIR, but it's not dangerous so just pass through it and keep going
	                }
	            } 
	        }
            coords = coords.offset(direction);
	    }
	    aetherSay(player, "There was no safe place to put your character.");
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
	
	
	/**
     * returns the unique name of the rune
     */
	public String getRuneName() {
		
		if(!runeName.isEmpty()) {
	    	return runeName;
	    }else{
	    	return shortClassName();
	    }
	}

    /**
     * Used to get rune names from the .lang file
     */
    public String getLocalizedRuneName() {
        return runeLocalizedName;
    }

    public static void aetherSay(EntityPlayer player, String message) {
    	
	    if(!player.worldObj.isRemote && player != null) {
	    	player.addChatMessage(new ChatComponentText(message));
	    }else{
	    	System.out.println(message);
	    }
	}

    public void aetherSay(World worldObj, String message) {
    	
        if(!worldObj.isRemote) { //[6915f56] Fixed player messages by just sending them from the server side instead of the ignorant client.
            Minecraft.getMinecraft().thePlayer.sendChatMessage(message); 
        }else{
            System.out.println(message);
        }
    }
	
	/**
     * Checks to see if there is a block match for the Rune blockPattern center at
	 * WorldXYZ coords.  
	 * Legacy Note: RunixMain changed rune pattern recognition to accept T0 ink blocks
	 * and T1+ None Corners.  So if there is a recognizable shape, it will be accepted.
	 * @return true if there is a valid match
	 */
    public WorldXYZ checkRunePattern(WorldXYZ coords) {
        HashMap<WorldXYZ, SigBlock> shape = runicFormulae(coords);
        if( !isAssymetrical()) {
            if(runeOrientationMatches(coords, shape))
                return coords;
            else
                return null;
        } else {
            for(int nTurns = 0; nTurns < 4; ++nTurns) {//90 degree turns 
                HashMap<WorldXYZ, SigBlock> newShape = Util_Movement.rotateStructureInMemory(shape, coords, nTurns);
                if( runeOrientationMatches(coords, newShape) ){
                    //change coords to be pointing in the detected direction, [array lookup]
                    switch(coords.face){
                        case 0: case 1: 
                            coords.face = (new ArrayList<Vector3>(Arrays.asList(Vector3.facing))).indexOf(Vector3.xzRotationOrder[nTurns]);
                            break;
                        case 2: case 3: 
                            coords.face = (new ArrayList<Vector3>(Arrays.asList(Vector3.facing))).indexOf(Vector3.xyRotationOrder[nTurns]);
                            break;
                        case 4: case 5: 
                            coords.face = (new ArrayList<Vector3>(Arrays.asList(Vector3.facing))).indexOf(Vector3.yzRotationOrder[nTurns]);
                            break;
                    }
                    return coords;
                }
            }
        }
        return null;
    }

    public boolean runeOrientationMatches(WorldXYZ coords, HashMap<WorldXYZ, SigBlock> shape) {
        Block ink = getTierInkBlock(coords);
        if(ink == Blocks.air)
            return false; //Tier blocks cannot be AIR
//        printPattern(shape, coords);
        for (WorldXYZ target : shape.keySet()) 
        {
            Block blockID = target.getBlock();
            SigBlock patternID = shape.get(target);
//            System.out.println(patternID.blockID + " should be " + blockID);
            switch(patternID.blockID.getUnlocalizedName())
            { // Handle special Template Values
                case "tile.NONE": 
                    if( blockID == ink )
                        return false; 
                    break;
                case "tile.TIER":
                    if( blockID != ink ){
                        return false; //inconsistent Tier block
                    }
                    break;
                case "tile.SIGR": 
                    if( blockID == ink )
                        return false; //you can't use your ink as part of your signature, it ruins the shape
                    break;
                case "tile.FUEL":
                    if( !target.equals(coords) || blockID == Blocks.air )//key block must be center block and not AIR 
                        return false;
                    break;
                default:
                    if (!patternID.equals(blockID) ){//normal block
                        return false;
                    }
                    break;
            }
        }
        return true;
    }

    private void printPattern(HashMap<WorldXYZ, SigBlock> shape, WorldXYZ coords) {
        Vector3[] neighbors = {
                new Vector3(-1, 0, -1),
                new Vector3(0 , 0, -1),
                new Vector3( 1, 0, -1),
                new Vector3(-1, 0, 0),
                new Vector3( 0, 0, 0),
                new Vector3( 1, 0, 0),
                new Vector3(-1, 0, 1),
                new Vector3(0 , 0, 1),
                new Vector3( 1, 0, 1)};
        
        try {
            for(int z = -1; z <= 1; ++z){
                for(int x = -1; x <= 1; ++x){
                    char ch = 'O';
                    if(shape.get(coords.offset(x, 0, z)).equals(Blocks.iron_block))
                        ch = '#';
                    System.out.print(ch);
                }
                System.out.println();//newline
            }
        } catch (Exception e) {}
    }

    protected int getTier(WorldXYZ coords){
        Block blockID = getTierInkBlock(coords);
        return blockID != null ? Tiers.getTier(blockID) : 1;
    }
    
    protected Block getTierInkBlock(WorldXYZ coords) {
        HashMap<WorldXYZ, SigBlock> shape = runicFormulae(coords);
        for (WorldXYZ target : shape.keySet()) {
            if (shape.get(target).equals(TIER)) {
                return target.getBlock();
            }
        }
        return null; // There was no TIER mentioned in the pattern
    }

    /**
     * Call accept() once you are sure the rune will be executed to tell the player it was successful.
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
                    Block blockID = n.getBlock();
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
    protected HashMap<WorldXYZ, SigBlock> patternToShape(Block[][][] pattern, WorldXYZ centerPoint){
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
                    if(pattern[y][z][x] != NONE) { //do not include NONE blocks in the runic template at all.
                        shape.put(target, new SigBlock(pattern[y][z][x], 0));
                    }
                }
            }
        }
        return shape;
    }
    
    /**
     * Removes the shape and adds its block energy to the rune
     */
    protected void consumeRune(WorldXYZ coords) {
        if(isFlatRuneOnly())
            coords = coords.copyWithNewFacing(1);
        HashMap<WorldXYZ, SigBlock> shape = runicFormulae(coords);
        for( WorldXYZ target : shape.keySet()){
            //for each block, get blockID
            Block blockID = target.getBlock();
            energy += Tiers.getEnergy(blockID);//convert ID into energy
            target.setBlockIdAndUpdate(Blocks.air);// delete the block
        }
        System.out.println(getRuneName() + " energy: " + energy);
    }

    /**Removes the shape and adds its block energy to the rune*/
    protected void consumeRune(Collection<WorldXYZ> shape) {
        for(WorldXYZ target : shape){
            Block blockID = target.getBlock();
            energy += Tiers.getEnergy(blockID);//convert ID into energy
            target.setBlockIdAndUpdate(Blocks.air);// delete the block
        }
        System.out.println(getRuneName() + " energy: " + energy);
    }
    
    public void setBlockIdAndUpdate(WorldXYZ coords, Block blockID) throws NotEnoughRunicEnergyException {
        if( blockID == Blocks.air )//this is actually breaking, not paying for air
            spendEnergy(Tiers.blockBreakCost);
        else
            spendEnergy(Tiers.getEnergy(blockID));
        coords.setBlockIdAndUpdate(blockID);
    }

    public void setBlockIdAndUpdate(WorldXYZ destination, SigBlock sourceBlock) throws NotEnoughRunicEnergyException {
        if( sourceBlock.blockID == Blocks.air )//this is actually breaking, not paying for air
            spendEnergy(Tiers.blockBreakCost);
        else
            spendEnergy(Tiers.getEnergy(sourceBlock.blockID));
        destination.setBlockId(sourceBlock);
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
        coords.setBlockIdAndUpdate(Blocks.air);
        spendEnergy((int) Tiers.blockMoveCost);
        
        HashMap<WorldXYZ, WorldXYZ> moveMapping = new HashMap<WorldXYZ, WorldXYZ>(1, 1.0f);//tiny HashMap!
        moveMapping.put(coords, newPos);
        RuneHandler.getInstance().moveMagic(moveMapping);
    }

    protected boolean consumeFuelBlock(WorldXYZ coords) {
        if(Tiers.getTier(coords.getBlock()) > 1){
            energy += Tiers.getEnergy(coords.getBlock());
            coords.setBlockIdAndUpdate(Blocks.cobblestone);//we don't want air sitting here
            return true;
        }
        return false;
    }

    protected String shortClassName() {
        return this.getClass().toString().replace("class com.newlinegaming.Runix.rune.", "");
    }

    public WorldXYZ findWaypointBySignature(EntityPlayer poker, Signature signature) {
        //new WaypointRune() is necessary because getActiveMagic() CANNOT be static, so it returns a pointer to a static field...
        ArrayList<PersistentRune> waypointList = (new WaypointRune().getActiveMagic());
        PersistentRune wp = null;
        for( PersistentRune candidate : waypointList) {
            if( new Signature(candidate, candidate.location).equals( signature ) 
                    && candidate.runeIsIntact()) {
                wp = candidate;
                break;
            }
        }
        if( wp == null){
            aetherSay(poker, "A waypoint with that signature cannot be found.");
            return null;
        }
        WorldXYZ destination = new WorldXYZ(wp.location);
        return destination;
    }

    /*
     * Placeholder which returns an empty signature.  Ovverride this to add signatures to your rune.
     */
    public Signature getSignature() {
        return new Signature();
    }

    public int authority() {
        return 0;
    }

    public boolean isAssymetrical() {
        return false;
    }

    public HashSet<WorldXYZ> runeBlocks(WorldXYZ coords) {
        return new HashSet<WorldXYZ>( runicFormulae(coords).keySet());
    }
}
