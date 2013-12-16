package com.newlinegaming.Runix;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

public class TeleporterRune extends AbstractRune {

	public int[][][] blockPattern(){
		return new int[][][]
				{{{0,4,0,4,0},
				  {4,4,4,4,4},
				  {0,4,3,4,0},
				  {4,4,4,4,4},
				  {0,4,0,4,0}}};
	}
	
	@Override
	/**This method moves the player to spawn.
	 * WorldXYZ are not used.
	 */
	public void execute(EntityPlayer player, WorldCoordinates coords) {
	    WorldCoordinates destination;
		if( WaypointRune.waypoints.isEmpty())
		    destination = new WorldCoordinates(player.worldObj.getSpawnPoint());
		else{
    	    WaypointRune wp = WaypointRune.waypoints.get(WaypointRune.waypoints.size()-1);// most recent
    	    destination = new WorldCoordinates(wp.location);
		}
		safelyMovePlayer(player, destination);
	}


}
