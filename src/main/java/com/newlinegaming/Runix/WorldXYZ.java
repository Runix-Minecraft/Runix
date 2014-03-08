package com.newlinegaming.Runix;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;


/**This class was created for Runix to ensure that when transferring between sets of coordinates,
 * the World is always known.  It extends ChunkCoordinates used by the rest of minecraft, but tracks
 * World and contains helper methods useful to Runix.*/
public class WorldXYZ extends ChunkCoordinates {

    private transient World worldObj = null;
    private int dimensionID = -500000;
    public int face = 1;
    
//    public WorldXYZ() { //default world is causing an issue with servers because defaultWorld() doesn't work correctly
//        this.posX = 0;
//        this.posY = 64;
//        this.posZ = 0;
//        this.setWorld(defaultWorld());
//    }
//
//    public WorldXYZ(int x, int y, int z) {
//        super(x, y, z);
//        this.setWorld(defaultWorld());
//    }

    public WorldXYZ(World world, int x, int y, int z) {
        super(x, y, z);
        this.setWorld(world);
    }

    public WorldXYZ(World world, int x, int y, int z, int face) {
        super(x,y,z);
        this.setWorld(world);
        this.face  = face;
    }

    public WorldXYZ(EntityPlayer player){
        super((int)(player.posX+.5), (int)(player.posY-1), (int)(player.posZ+.5));
        setWorld(player.worldObj);
    }
    
    public WorldXYZ(ChunkCoordinates otherGuy) {
        super(otherGuy);
        if( otherGuy instanceof WorldXYZ){
            this.setWorld(((WorldXYZ) otherGuy).getWorld());
            face = ((WorldXYZ) otherGuy).face;
        }
        else
            this.setWorld(defaultWorld());
    }

    public World getWorld() {
        if(worldObj == null && dimensionID != -500000){
            setWorld(dimensionID);
        }
        return worldObj;
    }

    public void setWorld(World worldObj) {
        this.worldObj = worldObj;
        dimensionID = getDimensionNumber();
    }

    /** This is for loadRunes() from JSON.  We need to set the WorldObj off 
     * of the dimension number.
     * @param dimension
     */
    public void setWorld(int dimension) {
        worldObj = MinecraftServer.getServer().worldServerForDimension(dimension);
//        worldObj = FMLServerHandler.instance().getServer().worldServerForDimension(dimension);
        dimensionID = getDimensionNumber();
    }
    
    /**Creates a new WorldXYZ based off of a previous one and a relative vector*/
    public WorldXYZ offset(int dX, int dY, int dZ){
        return new WorldXYZ(this.getWorld(), this.posX + dX, this.posY + dY, this.posZ + dZ, face);
    }

    public WorldXYZ offset(int dX, int dY, int dZ, int facing) {
        return new WorldXYZ(this.getWorld(), this.posX + dX, this.posY + dY, this.posZ + dZ, facing);
    }
    
    public WorldXYZ offset(Vector3 delta){
        return new WorldXYZ(this.getWorld(), posX + delta.x, posY + delta.y, posZ + delta.z, face);
    }

    /**Like offset() but for facing instead.  Returning a new instance avoids side-effecting*/
    public WorldXYZ copyWithNewFacing(int face2) {
        WorldXYZ n = new WorldXYZ(this);
        n.face = face2;
        return n;
    }
    
    /**Similar to offset(), but updates the current instance instead of a new one.*/ 
    public WorldXYZ bump(int dX, int dY, int dZ) {
        posX += dX;
        posY += dY;
        posZ += dZ;
        return this;
    }
    
    public WorldXYZ rotate(WorldXYZ referencePoint, boolean counterClockwise){
        Vector3 d = new Vector3(referencePoint, this);// determine quadrant relative to reference
        int direction = counterClockwise ? -1 : 1;
        //handle facing rotation:
//        int index = Vector3.xzRotationOrder.indexOf(new Integer(referencePoint.face));
//        if(index > -1) //not up or down
//            face = Vector3.xzRotationOrder.get( (index+direction ) % 4 );
        //Josiah: you have no idea how hard it was to get this one line of code
        return referencePoint.offset(direction * -d.z, d.y, direction * d.x, face);
    }

    public int getDimensionNumber(){
        if( getWorld() == null)
            setWorld(defaultWorld());
        return getWorld().provider.dimensionId;
    }
    
    public World defaultWorld() {
        return MinecraftServer.getServer().worldServerForDimension(0);
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
                    return ((WorldXYZ) other).getWorld() == this.getWorld();
                else //NOTE: This does not compare the face of each coordinate
                    return true;
            }
            return false;
        }
    }

    public SigBlock getSigBlock() {
        return new SigBlock(getBlockId(), getMetaId());
    }

    /**Simple wrapper method for getBlockID()*/
    public String getBlockId(){
        return this.getWorld().getBlock(posX, posY, posZ);
    }
    
    /** Sister function to getBlockID() for meta values. */
    public int getMetaId() {
        return getWorld().getBlockMetadata(posX, posY, posZ);
    }

    /**Simple wrapper method for setBlockID()
     * @param blockID
     * @return true if successful
     */
    public boolean setBlockIdAndUpdate(Block blockID) {
        if(blockID = Blocks.bedrock || getBlockId() == Blocks.bedrock)
            return false; //You cannot delete or place bedrock
        return this.getWorld().setBlock(posX, posY, posZ, blockID);
    }
    
    public boolean setBlockId(SigBlock sig){
        if(sig.blockID == Block.bedrock.blockID || getBlockId() == Block.bedrock.blockID)
            return false; //You cannot delete or place bedrock
        return this.getWorld().setBlock(posX, posY, posZ, sig.blockID, sig.meta, 2);
        //NOTE: Use last arg 3 if you want a block update.
    }

    public boolean setBlock(int blockID, int meta){
        if(blockID == Block.bedrock.blockID || getBlockId() == Block.bedrock.blockID)
            return false; //You cannot delete or place bedrock
        return this.getWorld().setBlock(posX, posY, posZ, blockID, meta, 3);
    }
    
    public String toString(){//this is designed to match the GSON output
        return "{\"dimensionID\":"+dimensionID+",\"face\":"+face+",\"posX\":"+posX+",\"posY\":"+posY+",\"posZ\":"+posZ+"}";
//        return "(" + posX + "," + posY +  "," + posZ + ")"; 
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

    public double getDistance(WorldXYZ other) {
        double xzDist_2 =  (posX - other.posX)*(posX - other.posX) + (posZ - other.posZ)*(posZ - other.posZ);//Math.sqrt(
        return Math.sqrt( xzDist_2 + (posY - other.posY)*(posY - other.posY));
    }

    public boolean isSolid() {
        Material base = getWorld().getBlockMaterial(posX, posY, posZ );
        return base.isSolid();
    }

    public WorldXYZ mostValuableNeighbor() {
        WorldXYZ best = offset(Vector3.UP);
        int bestEnergy = 0;
        for(WorldXYZ c : getNeighbors()){
            if( Tiers.getEnergy(c.getBlockId()) > bestEnergy){
                bestEnergy = Tiers.getEnergy(c.getBlockId());
                best = c;
            }
        }
        return best;
    }

    public void bump(Vector3 d) {
        bump(d.x, d.y, d.z);
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
