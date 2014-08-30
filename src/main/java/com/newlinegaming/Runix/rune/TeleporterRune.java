package com.newlinegaming.Runix.rune;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;

import com.newlinegaming.Runix.NotEnoughRunicEnergyException;
import com.newlinegaming.Runix.PersistentRune;
import com.newlinegaming.Runix.WorldXYZ;

public class TeleporterRune extends PersistentRune {

    private static ArrayList<PersistentRune> energizedTeleporters = new ArrayList<PersistentRune>();
    
    public TeleporterRune(){
        super();
        runeName = "Teleporter";
    }
    
    public TeleporterRune(WorldXYZ coords, EntityPlayer activator){
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
        consumeKeyBlock(coords);
	    WorldXYZ destination = findWaypointBySignature(poker, getSignature());

	    if(destination != null){
	        aetherSay(poker, "Teleporting to " + destination.toString());
    		try {
                teleportPlayer(poker, destination);
            } catch (NotEnoughRunicEnergyException e) {
                reportOutOfGas(poker);
            }
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
