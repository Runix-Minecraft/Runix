package com.newlinegaming.Runix;

import net.minecraft.entity.player.EntityPlayer;

public class WaypointRune extends AbstractRune {

    public int x,y,z;

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
        this.x = worldX;
        this.y = worldY;
        this.z = worldZ;
    }

    @Override
    public boolean isPersistent() {
        return true;
    }
    
    

}
