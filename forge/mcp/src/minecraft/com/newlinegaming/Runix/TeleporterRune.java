package com.newlinegaming.Runix;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;

public class TeleporterRune extends PersistentRune {

    private static ArrayList<PersistentRune> energizedTeleporters = new ArrayList<PersistentRune>();
    
    public TeleporterRune(){}
    
    public TeleporterRune(WorldXYZ coords, EntityPlayer activator){
        super(coords, activator);
        energy = 1000;
    }

	public int[][][] blockPattern(){
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
//	    if(Tiers.getTier(coords.getBlockId()) > 0){
//	        consumeKeyBlock(coords...)
	    
	    Signature signature = new Signature(this, coords);
	    WorldXYZ destination;
	    //This is necessary because getActiveMagic() CANNOT be static, so it returns a pointer to a static field...
	    ArrayList<PersistentRune> waypointList = (new WaypointRune().getActiveMagic());
	    
	    PersistentRune wp = null;
	    for( PersistentRune candidate : waypointList){
	        if( new Signature(candidate, candidate.location).equals( signature ) ){
	            wp = candidate;
	            break;
	        }
	    }
	    if( wp == null){
	        aetherSay(player, "A waypoint with that signature cannot be found.");
	        return;
	    }
	    destination = new WorldXYZ(wp.location);
	    energy -= destination.getDistanceSquaredToChunkCoordinates(new WorldXYZ(poker)) * .22;
	    aetherSay(poker, energy + " energy left");
		teleportPlayer(player, destination);
	}
	
    public String getRuneName() {
        return "Teleporter";
	}

    @Override
    public List<PersistentRune> getActiveMagic() {
        return energizedTeleporters;
    }

}
