package com.newlinegaming.Runix;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

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
	    WorldCoordinates destination;
		if( WaypointRune.waypoints.isEmpty()){
		    destination = new WorldCoordinates(player.worldObj.getSpawnPoint());
		    destination.worldObj = player.worldObj;
		}
		else{
    	    WaypointRune wp = WaypointRune.waypoints.get(WaypointRune.waypoints.size()-1);// most recent
    	    destination = new WorldCoordinates(wp.location);
		}
		safelyMovePlayer(player, destination);
	}

    public String getRuneName() {
        return "Teleporter";
	}

}
