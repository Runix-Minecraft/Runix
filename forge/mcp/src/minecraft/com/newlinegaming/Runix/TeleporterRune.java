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
	public void execute(EntityPlayer player, int worldX, int worldY, int worldZ) {
	    ChunkCoordinates coords;
		if( WaypointRune.waypoints.isEmpty())
		    coords = player.worldObj.getSpawnPoint();
		else{
    	    WaypointRune wp = WaypointRune.waypoints.get(WaypointRune.waypoints.size()-1);// most recent
    	    coords = new ChunkCoordinates(wp.x, wp.y, wp.z);
		}
		safelyMovePlayer(player, coords);
	}


}
