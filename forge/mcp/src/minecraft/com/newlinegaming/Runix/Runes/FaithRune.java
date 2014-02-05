package com.newlinegaming.Runix.Runes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.EnumChatFormatting;

import com.newlinegaming.Runix.NotEnoughRunicEnergyException;
import com.newlinegaming.Runix.PersistentRune;
import com.newlinegaming.Runix.Tiers;
import com.newlinegaming.Runix.Util_Movement;
import com.newlinegaming.Runix.Util_SphericalFunctions;
import com.newlinegaming.Runix.Vector3;
import com.newlinegaming.Runix.WorldXYZ;

public class FaithRune extends PersistentRune{
	
	protected static ArrayList<PersistentRune> activeFaithList = new ArrayList<PersistentRune>();
	public Integer radius = 11;
    private boolean firstTime;
	
	public FaithRune(){
	    runeName = "Faith";
	}
	public FaithRune(WorldXYZ loc, EntityPlayer creator)
	{
	    super(loc, creator, "Faith");
	    firstTime = true;
	}
	
	public int[][][] runicTemplateOriginal(){
		return new int [][][] 
           {{{NONE,NONE,NONE},
             {NONE, 41 ,NONE},
             {NONE,NONE,NONE}},
            {{41,TIER,41},
             {TIER,41,TIER},
             {41,TIER,41}}};
	}
	
//	public void execute(WorldXYZ coords, EntityPlayer player) // TODO we need a way for runes to check more complex validation and delete themselves if they don't pass
//	{
//		if (!runeAllowed(player, this)) return;
//		for (PersistentRune tmp : activeFaithList)
//		{//Josiah: sorry, there's no way to get around type casting and still have an interface or abstract parent 
//		    FaithRune fr = (FaithRune)tmp;
//			if (fr.location != null && fr.radius != null)
//			{
//				if(coords.getDistance(fr.location) < radius)// check for other Faith centers inside our radius
//				{
//					player.sendChatToPlayer(ChatMessageComponent.createFromText(EnumChatFormatting.RED+"ISLANDS TOO CLOSE"));
//					return;//TODO Chain FTP
//				}
//			}
//		}
//		PersistentRune newIsland = this.getOrCreateRune(coords, player);
//		if(newIsland != null)
//		    newIsland.poke(player, coords);
//	}
	
    @Override
    protected void poke(EntityPlayer poker, WorldXYZ coords) {
        if(firstTime){// firstTime prevents players from injecting more energy by building a second rune on top of the first
            firstTime = false;
            consumeRune(coords);
            try {
                setBlockIdAndUpdate(coords, Block.blockGold.blockID); //Gold block is to be a permanent marker
            } catch (NotEnoughRunicEnergyException e) {}
            energy -= Tiers.getEnergy(Block.blockGold.blockID) * 5; //the Gold blocks don't count towards the energy
            radius = Tiers.energyToRadiusConversion(energy);
            HashSet<WorldXYZ> sphere = Util_SphericalFunctions.getSphere(coords, radius);
            energy -= sphere.size() * Tiers.blockMoveCost;
            aetherSay(poker, "Created a Faith Sphere with a radius of "+ radius + " and " + sphere.size() + " blocks.");
            bounceIsland();
        }
    }
    
    /**bouncIsland() will place the sphere sitting on top of the old sphere's location (y+diameter).  It is used the first time
     * Faith is activated. 
     * Josiah: I've tried to speed this up as much as possible with little effect.  Profiling is needed.
     * @param sphere coordinates passed in so they don't need to be recalculated
     */
    public void bounceIsland()
	{
        int height = Math.min(location.posY + radius*2+1, 255 - radius-1);// places a ceiling that does not allow islands to go out the top of the map
        try {
            moveBlock(location, location.offset(0, height - location.posY, 0));
            //moveIsland(new Vector3(0, height - location.posY, 0));
        } catch (NotEnoughRunicEnergyException e) {}
	}
    
    /**The main movement teleportation function for Faith Islands. 
     * @param positionsMoved 
     * @throws NotEnoughRunicEnergyException comes only from the teleport involved in moving people*/
    public void moveIsland(Vector3 displacement, HashMap<WorldXYZ, WorldXYZ> positionsMoved) throws NotEnoughRunicEnergyException {
        // TODO Handle inter-dimensional travel
        HashSet<WorldXYZ> sphere = Util_SphericalFunctions.getSphere(location, radius);
        sphere.removeAll(positionsMoved.values()); //don't move things that have already been moved, including the center block
        sphere.remove(location);
        HashMap<WorldXYZ, WorldXYZ> moveMapping = Util_Movement.displaceShape(sphere, displacement.x, displacement.y, displacement.z);
        Util_Movement.performMove(moveMapping);//this is the version that doesn't cost energy, Faith pays the cost up front
        
//        WorldXYZ playerPosition = new WorldXYZ(poker); //TODO check for all users
//        if(Util_SphericalFunctions.radiusCheck(playerPosition.posX, playerPosition.posY, playerPosition.posZ, radius))
//            teleportPlayer(getPlayer(), playerPosition.offset(displacement)); //relative move from the players position
        
        /**Manual block move to avoid triggering moveMagic() which would create an infinite loop = BAD*/
        WorldXYZ destinationCenter = location.offset(displacement).copyWithNewFacing(location.face); //preserve old facing for runes
        location = destinationCenter;
    }
    
    @Override
    /**moveMagic() On Faith Islands checks for a movement of the center block.  If that block (location) gets moved, then the move
     * is amplified to all other blocks in the radius of the Faith Island.  This will be a little odd with rotation moves.*/
    public void moveMagic(HashMap<WorldXYZ, WorldXYZ> positionsMoved) 
    {
        for(PersistentRune rune : getActiveMagic())
        {
            if(positionsMoved.keySet().contains(rune.location) ) // we have a move that affects the center
            {
                try {
                    ((FaithRune) rune).moveIsland(new Vector3(rune.location, positionsMoved.get(rune.location)), positionsMoved);
                } catch (NotEnoughRunicEnergyException e) {
                    aetherSay(location.getWorld(), "The Faith sphere at " + location.toString() + " has run out of energy and the " +
                    		"magic has become untethered from the center block.");
                }
            }
        }
    }

    @Override
    public ArrayList<PersistentRune> getActiveMagic() {
        return activeFaithList;
    }

    @Override
    public boolean oneRunePerPerson() {
        return false;
    }
    @Override
    public boolean isFlatRuneOnly(){
        return true;
    }
	

	

}
