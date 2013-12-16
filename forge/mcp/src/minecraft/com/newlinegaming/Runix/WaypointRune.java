package com.newlinegaming.Runix;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;

public class WaypointRune extends AbstractRune {
    public static ArrayList<WaypointRune> waypoints = new ArrayList<WaypointRune>();
    public int x,y,z;

    public WaypointRune(){}
    
    public WaypointRune(int worldX, int worldY, int worldZ)
    {
        this.x = worldX;
        this.y = worldY;
        this.z = worldZ;
        
    }
    @Override
    public int[][][] blockPattern() {
        return new int[][][]
                {{{0,4,4,4,0},
                  {4,4,0,4,4},
                  {4,0,4,0,4},
                  {4,4,0,4,4},
                  {0,4,4,4,0}}};
    }

    @Override
    public void execute(EntityPlayer player, int worldX, int worldY, int worldZ) {
        WaypointRune persistentCopy = new WaypointRune(worldX, worldY, worldZ);
        addWaypoint((WaypointRune) persistentCopy);
        //handler.aetherSay("Waypoint added to persistence list");
    }    
    /** This method exists to ensure that no duplicate waypoints are persisted. */
    public void addWaypoint(WaypointRune wp) {
        for(WaypointRune oldWP : waypoints){
            if( oldWP.x == wp.x && oldWP.y == wp.y && oldWP.z == wp.z )
                return; //ensure there are no duplicates
        }
        waypoints.add(wp);
    }
}
