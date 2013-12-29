package com.newlinegaming.Runix;

import java.util.ArrayList;

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
    public void saveActiveRunes() {
        System.out.println(getRuneName() + " saving data.");
        //TODO output JSON file
    }

    @Override
    public ArrayList<PersistentRune> getActiveMagic() {
        return activeMagic;
    }

    @Override
    public boolean oneRunePerPerson() {
        return false;
    }
}
