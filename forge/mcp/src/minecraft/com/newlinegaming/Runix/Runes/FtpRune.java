package com.newlinegaming.Runix.Runes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import net.minecraft.entity.player.EntityPlayer;

import com.newlinegaming.Runix.NotEnoughRunicEnergyException;
import com.newlinegaming.Runix.PersistentRune;
import com.newlinegaming.Runix.Util_Movement;
import com.newlinegaming.Runix.Vector3;
import com.newlinegaming.Runix.WorldXYZ;

public class FtpRune extends TeleporterRune {
    
    private static ArrayList<PersistentRune> energizedFTP = new ArrayList<PersistentRune>(); 
    
    public FtpRune(){
        super();
        this.runeName = "Faith Transfer Portal";
    }
    
    public FtpRune(WorldXYZ coords, EntityPlayer activator)
    {
        super(coords, activator);
        this.runeName = "Faith Transfer Portal";
    }

    public int[][][] runicTemplateOriginal(){
        int GOLD = 41;
        return new int[][][]
                {{{NONE,NONE,TIER,SIGR,TIER,NONE,NONE},
                  {NONE,NONE,TIER,TIER,TIER,NONE,NONE},
                  {TIER,TIER,GOLD,TIER,GOLD,TIER,TIER},
                  {SIGR,TIER,TIER,KEY ,TIER,TIER,SIGR},
                  {TIER,TIER,GOLD,TIER,GOLD,TIER,TIER},
                  {NONE,NONE,TIER,TIER,TIER,NONE,NONE},
                  {NONE,NONE,TIER,SIGR,TIER,NONE,NONE}}};
    }

    @Override
    protected void poke(EntityPlayer poker, WorldXYZ coords) {
        consumeKeyBlock(coords);
        location.face = coords.face; //update the facing 
        WorldXYZ destination = findWaypointBySignature(poker, getSignature());
        if(destination == null)
            return; //failure
        HashSet<WorldXYZ> structure = attachedStructureShape(poker);
        if(structure.isEmpty())
            return;
        
        try {
            WorldXYZ destinationCenter = collideAndBounceStructureTeleport(structure, location, destination);
            if(destinationCenter != null)
                teleportPlayer(poker, destinationCenter.copyWithNewFacing(location.face)); // so that the player always lands in the right spot regardless of signature
        } catch (NotEnoughRunicEnergyException e) {
            reportOutOfGas(poker);
        }
    }

    /**Attempt to teleport the structure to a non-colliding location at destination, scanning in the direction
     * of destination.face.
     * @param structure
     * @param destination
     * @return center of destination teleport or null if the teleport was unsuccessful
     * @throws NotEnoughRunicEnergyException
     */
    public WorldXYZ collideAndBounceStructureTeleport(HashSet<WorldXYZ> structure, WorldXYZ startPoint, WorldXYZ destination) throws NotEnoughRunicEnergyException 
    {
        Vector3 roomForShip = Vector3.facing[destination.face].multiply(getTier());// TODO get width/height/depth of structure + 1
        WorldXYZ destinationCenter = destination.offset(roomForShip);
        //Landing placement if there is no collisions:
        Util_Movement.bumpShape(structure, new Vector3(startPoint, destinationCenter));
        int collisionTries = 0;
        while( Util_Movement.teleportCollision( structure ) && collisionTries < 20)
        {
            Vector3 displacement = Vector3.facing[destination.face].multiply(5);//try moving it over 5m
            destinationCenter.bump(displacement);
            Util_Movement.bumpShape(structure,  displacement);
            collisionTries++;
        }
        if(collisionTries >= 20)
            return null; //the teleport did not work
        moveShape(structure, startPoint, destinationCenter);
        return destinationCenter;
    }

    @Override
    public ArrayList<PersistentRune> getActiveMagic() {
        return energizedFTP;
    }

    

}
