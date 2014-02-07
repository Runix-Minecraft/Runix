package com.newlinegaming.Runix.Runes;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;

import com.newlinegaming.Runix.NotEnoughRunicEnergyException;
import com.newlinegaming.Runix.PersistentRune;
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
        
        try {
            teleportPlayer(poker, destination);
        } catch (NotEnoughRunicEnergyException e) {
            reportOutOfGas(poker);
        }
    }

    @Override
    public ArrayList<PersistentRune> getActiveMagic() {
        return energizedFTP;
    }

    

}
