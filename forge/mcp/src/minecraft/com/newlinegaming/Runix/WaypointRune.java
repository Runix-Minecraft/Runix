package com.newlinegaming.Runix;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;

public class WaypointRune extends PersistentRune{
    private static ArrayList<PersistentRune> activeMagic = new ArrayList<PersistentRune>();

    public WaypointRune(){
    	
    	super();
    }
    
    public WaypointRune(WorldXYZ coords, EntityPlayer player)
    {
    	super(coords, player);
        this.runeName = "waypoint";
    }

    @Override
    public int[][][] runicTemplateOriginal() {
        return new int[][][]
                {{{NONE,TIER,TIER,TIER,NONE},
                  {TIER,TIER,SIGR,TIER,TIER},
                  {TIER,SIGR,TIER,SIGR,TIER},
                  {TIER,TIER,SIGR,TIER,TIER},
                  {NONE,TIER,TIER,TIER,NONE}}};
    }

    @Override
    /**Waypoints will detect which side of the key block you activate from and use 
     * that to direct the player's teleport.*/
    protected void poke(EntityPlayer poker, WorldXYZ coords) {
        location.face = coords.face; //update the facing of the waypoint
        aetherSay(poker, "Waypoint is now facing " + Vector3.faceString[location.face]);
    }

    public String getRuneName() {
		return this.runeName;
	}

    @Override
    public ArrayList<PersistentRune> getActiveMagic() {
        return activeMagic;
    }

    @Override
    public boolean oneRunePerPerson() {
        return false;
    }

    public boolean isFlatRuneOnly() {
        return false;
    }
    
}
