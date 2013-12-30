package com.newlinegaming.Runix;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import cpw.mods.fml.client.FMLClientHandler;


/**This class was created for Runix to ensure that when transferring between sets of coordinates,
 * the World is always known.  It extends ChunkCoordinates used by the rest of minecraft, but tracks
 * World and contains helper methods useful to Runix.*/
public class WorldXYZ extends ChunkCoordinates {

    public World worldObj = null;
    public int face = 0;
    
    public WorldXYZ() {
        this.posX = 0;
        this.posY = 64;
        this.posZ = 0;
        this.worldObj = defaultWorld();
    }

    public WorldXYZ(int x, int y, int z) {
        super(x, y, z);
        this.worldObj = defaultWorld();
    }

    public WorldXYZ(World world, int x, int y, int z) {
        super(x, y, z);
        this.worldObj = world;
    }

    public WorldXYZ(World world, int x, int y, int z, int face) {
        super(x,y,z);
        this.worldObj = world;
        this.face  = face;
    }

    public WorldXYZ(EntityPlayer player){
        super((int)(player.posX+.5), (int)(player.posY-1), (int)(player.posZ+.5));
        worldObj = player.worldObj;
    }
    
    public WorldXYZ(ChunkCoordinates otherGuy) {
        super(otherGuy);
        if( otherGuy instanceof WorldXYZ){
            this.worldObj = ((WorldXYZ) otherGuy).worldObj;
            face = ((WorldXYZ) otherGuy).face;
        }
        else
            this.worldObj = defaultWorld();
    }

    /**Creates a new WorldXYZ based off of a previous one and a relative vector*/
    public WorldXYZ offset(int dX, int dY, int dZ){
        return new WorldXYZ(this.worldObj, this.posX + dX, this.posY + dY, this.posZ + dZ, face);
    }
    
    public WorldXYZ offset(Vector3 delta){
        return new WorldXYZ(this.worldObj, posX + delta.x, posY + delta.y, posZ + delta.z, face);
    }

    /**Similar to offset(), but updates the current instance instead of a new one.*/ 
    public WorldXYZ bump(int dX, int dY, int dZ) {
        posX += dX;
        posY += dY;
        posZ += dZ;
        return this;
    }
    
    public WorldXYZ rotate(WorldXYZ referencePoint, boolean counterClockwise){
        Vector3 d = Vector3.offset(referencePoint, this);// determine quadrant relative to reference
        int reverse = counterClockwise ? -1 : 1;
        //Josiah: you have no idea how hard it was to get this one line of code
        return referencePoint.offset(reverse * -d.z, d.y, reverse * d.x);//flip sign on z
        //TODO: if NORTH EAST WEST SOUTH, modify resultant face to match ?counterClockwise
    }

    public World defaultWorld() {
        return FMLClientHandler.instance().getServer().worldServerForDimension(0);
//        return WorldProvider.getProviderForDimension(0).worldObj;//TODO: Josiah: This is not working correctly atm
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
                if(other instanceof WorldXYZ)
                    return ((WorldXYZ) other).worldObj == this.worldObj;
                else //TODO: Should we check for face?
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

    public ArrayList<WorldXYZ> getNeighbors() {
        ArrayList<WorldXYZ> neighbors = new ArrayList<WorldXYZ>();
        //6 cardinal sides
        neighbors.add(offset(0, 1,0));
        neighbors.add(offset(0,-1,0));
        neighbors.add(offset(0,0,-1));
        neighbors.add(offset( 1,0,0));
        neighbors.add(offset(0,0, 1));
        neighbors.add(offset(-1,0,0));
        
        //12 edge diagonals
        //Josiah: If there was a way to get Build Master and Runecraft to cooperate without these 
        //extra 12 checks I would really rather only do 1/3 the workload when loading large Runecraft
        //structures
        neighbors.add(offset( 1, 1, 0));
        neighbors.add(offset(-1, 1, 0));
        neighbors.add(offset( 0, 1, 1));
        neighbors.add(offset( 0, 1,-1));

        neighbors.add(offset( 1, 0, 1));
        neighbors.add(offset(-1, 0, 1));
        neighbors.add(offset( 1, 0,-1));
        neighbors.add(offset(-1, 0,-1));

        neighbors.add(offset( 1,-1, 0));
        neighbors.add(offset(-1,-1, 0));
        neighbors.add(offset( 0,-1, 1));
        neighbors.add(offset( 0,-1,-1));
        //the 8 corner diagonals are not included
        return neighbors;
    }

    public SigBlock getSigBlock() {
        return new SigBlock(getBlockId(), getMetaId());
    }

}
