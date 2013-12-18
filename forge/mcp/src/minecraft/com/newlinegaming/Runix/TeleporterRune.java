package com.newlinegaming.Runix;

import net.minecraft.entity.player.EntityPlayer;

public class TeleporterRune extends AbstractRune {
    

	public int[][][] blockPattern(){
		return new int[][][]
				{{{NONE,TIER,SIGR,TIER,NONE},
				  {TIER,TIER,TIER,TIER,TIER},
				  {SIGR,TIER,NONE,TIER,SIGR},
				  {TIER,TIER,TIER,TIER,TIER},
				  {NONE,TIER,SIGR,TIER,NONE}}};
	}
	
	@Override
	/**This method moves the player to spawn.
	 * WorldXYZ are not used.
	 */
	public void execute(EntityPlayer player, WorldCoordinates coords) {
	    accept(player);
	    Signature signature = new Signature(this, coords);
	    WorldCoordinates destination;
		if( WaypointRune.waypoints.isEmpty()){
		    destination = new WorldCoordinates(player.worldObj.getSpawnPoint());
		    destination.worldObj = player.worldObj;
		}
		else{
    	    WaypointRune wp = null;
    	    for( WaypointRune candidate : WaypointRune.waypoints){
    	        if( new Signature(candidate, candidate.location).equals( signature ) ){
    	            wp = candidate;
    	            break;
    	        }
    	    }
    	    if( wp == null){
    	        aetherSay(player, "A waypoint with that signature cannot be found.");
    	        return;
    	    }
    	    destination = new WorldCoordinates(wp.location);
		}
		safelyMovePlayer(player, destination);
	}

    public String getRuneName() {
        return "Teleporter";
	}

}
