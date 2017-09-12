package com.newlinegaming.Runix.rune;

import java.util.ArrayList;

import com.newlinegaming.Runix.NoSuchSignatureException;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;

import com.newlinegaming.Runix.PersistentRune;
import com.newlinegaming.Runix.WorldXYZ;

public class TeleporterRune extends PersistentRune {

    private static final ArrayList<PersistentRune> energizedTeleporters = new ArrayList<>();
    
    public TeleporterRune(){
        super();
        runeName = "Teleporter";
    }
    
    TeleporterRune(WorldXYZ coords, EntityPlayer activator){
        super(coords, activator,"Teleporter");
        energy = 1;
    }

    public Block[][][] runicTemplateOriginal(){
        return new Block[][][]
                {{{NONE,TIER,SIGR,TIER,NONE},
                  {TIER,TIER,TIER,TIER,TIER},
                  {SIGR,TIER,FUEL ,TIER,SIGR},
                  {TIER,TIER,TIER,TIER,TIER},
                  {NONE,TIER,SIGR,TIER,NONE}}};
    }


    /**
     * Teleport the player to the WaypointRune with a matching signature
     */
    @Override
    protected void poke(EntityPlayer poker, WorldXYZ coords) {
        consumeFuelBlock(coords);

        try{
            WorldXYZ destination = findWaypointBySignature(getSignature());
            aetherSay(poker, "Teleporting to " + destination.toString());
            teleportPlayer(poker, destination);
        }catch (NoSuchSignatureException e){
            aetherSay(poker, "There's no waypoint with that signature.");
        }
    }

    @Override
    public ArrayList<PersistentRune> getActiveMagic() {
        return energizedTeleporters;
    }

    public boolean oneRunePerPerson() {
        return false;
    }

    @Override
    public boolean isFlatRuneOnly() {
        return false;
    }

}
