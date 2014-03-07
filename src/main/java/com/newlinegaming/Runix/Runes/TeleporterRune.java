package com.newlinegaming.Runix.Runes;

import java.util.ArrayList;

import com.newlinegaming.Runix.NotEnoughRunicEnergyException;
import com.newlinegaming.Runix.PersistentRune;
import com.newlinegaming.Runix.WorldXYZ;

import net.minecraft.entity.player.EntityPlayer;

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

	public int[][][] runicTemplateOriginal(){
		return new int[][][]
				{{{NONE,TIER,SIGR,TIER,NONE},
				  {TIER,TIER,TIER,TIER,TIER},
				  {SIGR,TIER,KEY ,TIER,SIGR},
				  {TIER,TIER,TIER,TIER,TIER},
				  {NONE,TIER,SIGR,TIER,NONE}}};
	}
	
	
    @Override
    /**Teleport the player to the WaypointRune with a matching signature
     */
    protected void poke(EntityPlayer poker, WorldXYZ coords) {
        consumeKeyBlock(coords);
	    WorldXYZ destination = findWaypointBySignature(poker, getSignature());

	    if(destination != null){
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
