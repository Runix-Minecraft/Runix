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
        WorldXYZ destination = findWaypointBySignature(poker, getSignature());
        if(destination == null)
            return; //failure
        HashSet<WorldXYZ> structure = attachedStructureShape(poker);
        if(structure.isEmpty())
            return;
        
        try {
            Vector3 scanDirection = Vector3.facing[destination.face];
            
            WorldXYZ destinationCenter = destination.offset(scanDirection.multiply(getTier()));
            //TODO collision
            moveShape(structure, location, destinationCenter);
            
            teleportPlayer(poker, destinationCenter);
        } catch (NotEnoughRunicEnergyException e) {
            reportOutOfGas(poker);
        }
    }

    @Override
    public ArrayList<PersistentRune> getActiveMagic() {
        return energizedFTP;
    }

    

}
