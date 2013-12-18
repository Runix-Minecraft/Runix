package com.newlinegaming.Runix;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;

public class WaypointRune extends AbstractRune {
    public static ArrayList<WaypointRune> waypoints = new ArrayList<WaypointRune>();
    public WorldCoordinates location;

    public WaypointRune(){}
    
    public WaypointRune(WorldCoordinates coords)
    {
        this.location = new WorldCoordinates(coords);
    }

    @Override
    public int[][][] blockPattern() {
        return new int[][][]
                {{{NONE,TIER,TIER,TIER,NONE},
                  {TIER,TIER,SIGN,TIER,TIER},
                  {TIER,SIGN,TIER,SIGN,TIER},
                  {TIER,TIER,SIGN,TIER,TIER},
                  {NONE,TIER,TIER,TIER,NONE}}};
    }

    @Override
    public void execute(EntityPlayer player, WorldCoordinates coords) {
	aetherSay(player,EnumChatFormatting.GREEN+"Waypoint Accepted.");
        WaypointRune persistentCopy = new WaypointRune(coords);
        addWaypoint((WaypointRune) persistentCopy);
        //handler.aetherSay("Waypoint added to persistence list");
    }    
    /** This method exists to ensure that no duplicate waypoints are persisted. */
    public void addWaypoint(WaypointRune wp) {
        for(WaypointRune oldWP : waypoints){
            //this will handle the odd case where you have 2 wps in different dimensions with the same xyz
            if( oldWP.location.equals(wp.location) )  
                return; //ensure there are no duplicates
        }
        waypoints.add(wp);
    }

    public String getRuneName() {
		return "Waypoint";
	}
}
