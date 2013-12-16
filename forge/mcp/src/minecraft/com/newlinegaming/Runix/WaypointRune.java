package com.newlinegaming.Runix;

import net.minecraft.entity.player.EntityPlayer;

public class WaypointRune extends AbstractRune {

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
    public void execute(RuneHandler handler, EntityPlayer player, int worldX, int worldY, int worldZ) {
        WaypointRune persistentCopy = new WaypointRune(worldX, worldY, worldZ);
        handler.addWaypoint((WaypointRune) persistentCopy);
        handler.aetherSay("Waypoint added to persistence list");
    }    

}
