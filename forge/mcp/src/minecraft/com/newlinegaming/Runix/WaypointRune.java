package com.newlinegaming.Runix;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;

public class WaypointRune extends PersistentRune{
    private static ArrayList<PersistentRune> activeMagic = new ArrayList<PersistentRune>();

    public WaypointRune(){}
    
    public WaypointRune(WorldXYZ coords, EntityPlayer player)
    {
        super(coords, player);
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

    public String getRuneName() {
		return "Waypoint";
	}
    
    @Override
    public void moveMagic(Collection<WorldXYZ> blocks, int dX, int dY, int dZ) {
        for(PersistentRune tmp : activeMagic){
            PersistentRune wp = (PersistentRune) tmp;
            if(blocks.contains(wp.location) )
                wp.location.bump(dX, dY, dZ);
        }
    }

    @Override
    public void moveMagic(HashMap<WorldXYZ, WorldXYZ> positionsMoved) {
        for(PersistentRune tmp : activeMagic){
            PersistentRune wp = (PersistentRune) tmp;
            if(positionsMoved.keySet().contains(wp.location) )
                wp.location = positionsMoved.get(wp.location); //grab the destination keyed by source position
        }
    }

    @Override
    public void saveActiveRunes() {
        System.out.println(getRuneName() + " saving data.");
        //TODO output JSON file
    }

    @Override
    public ArrayList<PersistentRune> getActiveMagic() {
        return activeMagic;
    }
}
