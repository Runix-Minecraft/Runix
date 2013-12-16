package com.newlinegaming.Runix;

import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;


/**This class was created for Runix to ensure that when transferring between sets of coordinates,
 * the World is always known.  It extends ChunkCoordinates used by the rest of minecraft, but tracks
 * World and contains helper methods useful to Runix.*/
public class WorldCoordinates extends ChunkCoordinates {

    public World worldObj;
    
    public WorldCoordinates() {
        this.posX = 0;
        this.posY = 64;
        this.posZ = 0;
        this.worldObj = defaultWorld();
    }

    public WorldCoordinates(int x, int y, int z) {
        super(x, y, z);
        this.worldObj = defaultWorld();
    }

    public WorldCoordinates(ChunkCoordinates otherGuy) {
        super(otherGuy);
        if( otherGuy instanceof WorldCoordinates)
            this.worldObj = ((WorldCoordinates) otherGuy).worldObj;
        else
            this.worldObj = defaultWorld();
    }

    public World defaultWorld() {
        return WorldProvider.getProviderForDimension(0).worldObj;
    }

}
