package com.newlinegaming.Runix;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;

public class WaypointRune extends AbstractRune implements PersistentRune{
    public static ArrayList<WaypointRune> activeMagic = new ArrayList<WaypointRune>();
    public WorldXYZ location = null;

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

    /** This method exists to ensure that no duplicate activeMagic are persisted. */
    public boolean addWaypoint(WaypointRune wp) {
        for(WaypointRune oldWP : activeMagic){
            //this will handle the odd case where you have 2 wps in different dimensions with the same xyz
            if( oldWP.location.equals(wp.location) )  
                return false; //ensure there are no duplicates
        }
        activeMagic.add(wp);
        return true;
    }

    public String getRuneName() {
		return "Waypoint";
	}
    
    @Override
    public void moveMagic(Collection<WorldXYZ> blocks, int dX, int dY, int dZ) {
        for(WaypointRune wp : activeMagic){
            if(blocks.contains(wp.location) )
                wp.location.bump(dX, dY, dZ);
        }
    }

    @Override
    public void moveMagic(HashMap<WorldXYZ, WorldXYZ> positionsMoved) {
        for(WaypointRune wp : activeMagic){
            if(positionsMoved.keySet().contains(wp.location) )
                wp.location = positionsMoved.get(wp.location); //grab the destination keyed by source position
        }
    }

    @Override
    public void saveActiveRunes() {
        System.out.println(getRuneName() + " saving data.");
        //TODO output JSON file
    }
}
