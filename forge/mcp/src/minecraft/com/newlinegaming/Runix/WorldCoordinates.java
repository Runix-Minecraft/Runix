package com.newlinegaming.Runix;

import java.util.ArrayList;

import net.minecraft.block.Block;
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

    public WorldCoordinates(World world, int x, int y, int z) {
        super(x, y, z);
        this.worldObj = world;
    }
    
    public WorldCoordinates(ChunkCoordinates otherGuy) {
        super(otherGuy);
        if( otherGuy instanceof WorldCoordinates)
            this.worldObj = ((WorldCoordinates) otherGuy).worldObj;
        else
            this.worldObj = defaultWorld();
    }

    /**Creates a new WorldCoordinates based off of a previous one and a relative vector*/
    public WorldCoordinates offset(int dX, int dY, int dZ){
        return new WorldCoordinates(this.worldObj, this.posX + dX, this.posY + dY, this.posZ + dZ);
    }

    /**Similar to offset(), but updates the current instance instead of a new one.*/ 
    public WorldCoordinates bump(int dX, int dY, int dZ) {
        posX += dX;
        posY += dY;
        posZ += dZ;
        return this;
    }

    public World defaultWorld() {
        return WorldProvider.getProviderForDimension(0).worldObj;//TODO: Josiah: This is not working correctly atm
    }

    @Override
    public boolean equals(Object otherObj)
    {
        if (!(otherObj instanceof ChunkCoordinates)){
            return false;
        }
        else{
            ChunkCoordinates other = (ChunkCoordinates)otherObj;
            if( this.posX == other.posX && this.posY == other.posY && this.posZ == other.posZ){
                if(other instanceof WorldCoordinates)
                    return ((WorldCoordinates) other).worldObj == this.worldObj;
                else
                    return true;
            }
            return false;
        }
    }

    /**Simple wrapper method for getBlockID()*/
    public int getBlockId(){
        return this.worldObj.getBlockId(this.posX, this.posY, this.posZ);
    }

    /** Sister function to getBlockID() for meta values. */
    public int getMetaId() {
        return worldObj.getBlockMetadata(posX, posY, posZ);
    }

    /**Simple wrapper method for setBlockID()
     * @param blockID
     * @return true if successful
     */
    public boolean setBlockId(int blockID){
        if(getBlockId() == Block.bedrock.blockID)
            return false; //TODO: make this more nuanced behavior
        return this.worldObj.setBlock(posX, posY, posZ, blockID);
    }
    
    public boolean setBlockId(SigBlock sig){
        if(getBlockId() == Block.bedrock.blockID)
            return false; //TODO: make this more nuanced behavior
        return this.worldObj.setBlock(posX, posY, posZ, sig.blockID, sig.meta, 2);
        //NOTE: Use last arg 3 if you want a block update.
    }
    
    
    public String toString(){
        return "(" + posX + "," + posY +  "," + posZ + ")"; 
    }

    public ArrayList<WorldCoordinates> getNeighbors() {
        ArrayList<WorldCoordinates> neighbors = new ArrayList<WorldCoordinates>();
        neighbors.add(offset(-1,0,0));
        neighbors.add(offset(0,-1,0));
        neighbors.add(offset(0,0,-1));
        neighbors.add(offset(1,0,0));
        neighbors.add(offset(0,1,0));
        neighbors.add(offset(0,0,1));
        return neighbors;
    }

    public SigBlock getSigBlock() {
        return new SigBlock(getBlockId(), getMetaId());
    }

}
