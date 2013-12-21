package com.newlinegaming.Runix;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;

public class WaypointRune extends AbstractRune {
    public static ArrayList<WaypointRune> waypoints = new ArrayList<WaypointRune>();
    public WorldXYZ location;

    public WaypointRune(){}
    
    public WaypointRune(WorldXYZ coords)
    {
        this.location = new WorldXYZ(coords);
    }

    @Override
    public int[][][] blockPattern() {
        return new int[][][]
                {{{NONE,TIER,TIER,TIER,NONE},
                  {TIER,TIER,SIGR,TIER,TIER},
                  {TIER,SIGR,TIER,SIGR,TIER},
                  {TIER,TIER,SIGR,TIER,TIER},
                  {NONE,TIER,TIER,TIER,NONE}}};
    }

    @Override
    public void execute(EntityPlayer player, WorldXYZ coords) {
        AbstractRune persistentCopy = new WaypointRune(coords);
        if( addWaypoint((WaypointRune) persistentCopy) )
            accept(player);
    }

    /** This method exists to ensure that no duplicate waypoints are persisted. */
    public boolean addWaypoint(WaypointRune wp) {
        for(WaypointRune oldWP : waypoints){
            //this will handle the odd case where you have 2 wps in different dimensions with the same xyz
            if( oldWP.location.equals(wp.location) )  
                return false; //ensure there are no duplicates
        }
        waypoints.add(wp);
        return true;
    }

    public String getRuneName() {
		return "Waypoint";
	}
}
