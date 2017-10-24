package com.newlinegaming.Runix;

import com.newlinegaming.Runix.helper.TierHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.server.FMLServerHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;


/**
 * This class was created for Runix to ensure that when transferring between sets of coordinates,
 * the World is always known.  It extends BlockPos used by the rest of minecraft, but tracks
 * World and contains helper methods useful to Runix.
 */

//Note might want to move to a library mod

public class WorldXYZ extends BlockPos {

    @Nullable
    private transient World worldObj = null;
    private int dimensionID = -500000;
    public int face = 1;

//    public WorldXYZ() { //default world is causing an issue with servers because defaultWorld() doesn't work correctly
//        this.getX() = 0;
//        this.getY() = 64;
//        this.getZ() = 0;
//        this.setWorld(defaultWorld());
//    }
//
    public WorldXYZ(int x, int y, int z) {
        super(x, y, z);
        this.setWorld(defaultWorld());
    }

    public WorldXYZ(@NotNull World world, int x, int y, int z) { //this constructor was made to be fast
        super(x, y, z);
        worldObj = world;
        dimensionID = world.provider.getDimension();
    }

    public WorldXYZ(@NotNull PlayerInteractEvent e){
        super(e.getPos());
        worldObj = e.getWorld();
    }

    public WorldXYZ(World world, @NotNull BlockPos pos) {
        super(pos);
        worldObj = world;
    }

    public WorldXYZ(World world, int x, int y, int z, int face) {
        super(x,y,z);
        this.setWorld(world);
        this.face  = face;
    }

    public WorldXYZ(@NotNull EntityPlayer player) {
//        super((int)(player.getX()+.5), (int)(player.getY()-1), (int)(player.getZ()+.5));
        super(player);
        setWorld(player.world);
    }

    public WorldXYZ(@NotNull BlockPos otherGuy) {
        super(otherGuy);
        if(otherGuy instanceof WorldXYZ){
            this.setWorld(((WorldXYZ) otherGuy).getWorld());
            face = ((WorldXYZ) otherGuy).face;
        }
        else
            this.setWorld(defaultWorld());
    }

    @Nullable
    public World getWorld() {
        if(worldObj == null && dimensionID != -500000) {
            setWorld(dimensionID);
        }
        return worldObj;
    }

    private void setWorld(World worldObj) {
        this.worldObj = worldObj;
        dimensionID = getDimensionNumber();
    }

    /**
     * This is for loadRunes() from JSON.  We need to set the WorldObj off
     * of the dimension number.
     * @param dimension
     */
    private void setWorld(int dimension) {
//        worldObj = MinecraftServer.getServer().getWorld(dimension);
//        worldObj = FMLServerHandler.instance().getServer().worldServerForDimension(dimension);
        this.worldObj = FMLServerHandler.instance().getServer().getWorld(dimension);
        dimensionID = getDimensionNumber();
    }

    /**
     * Creates a new WorldXYZ based off of a previous one and a relative vector
     */
    @Nullable
    public WorldXYZ offset(int dX, int dY, int dZ){
        return new WorldXYZ(this.getWorld(), this.getX() + dX, this.getY() + dY, this.getZ() + dZ, face);
    }

    private WorldXYZ offset(int dX, int dY, int dZ, int facing) {
        return new WorldXYZ(this.getWorld(), this.getX() + dX, this.getY() + dY, this.getZ() + dZ, facing);
    }

    @Nullable
    public WorldXYZ offset(@NotNull Vector3 delta){
        return new WorldXYZ(this.getWorld(), getX() + delta.x, getY() + delta.y, getZ() + delta.z, face);
    }
    
    @NotNull
    public WorldXYZ offsetWorld(@NotNull Vector3 delta, World dem) {
        return new WorldXYZ(dem, getX() + delta.x, getY() + delta.y, getZ() + delta.z, face);
    }

    /**
     * Like offset() but for facing instead.  Returning a new instance avoids side-effecting
     */
    @NotNull
    public WorldXYZ copyWithNewFacing(int face2) {
        WorldXYZ n = new WorldXYZ(this);
        n.face = face2;
        return n;
    }

    @NotNull
    public WorldXYZ rotate(@NotNull WorldXYZ referencePoint, boolean counterClockwise){
        Vector3 d = new Vector3(referencePoint, this);// determine quadrant relative to reference
        int direction = counterClockwise ? -1 : 1;
        //handle facing rotation:
//        int index = Vector3.xzRotationOrder.indexOf(new Integer(referencePoint.face));
//        if(index > -1) //not up or down
//            face = Vector3.xzRotationOrder.get( (index+direction ) % 4 );
        //Josiah: you have no idea how hard it was to get this one line of code
        if(referencePoint.face == 1 || referencePoint.face == 0)//UP or DOWN, xz rotation
            return referencePoint.offset(direction * -d.z, d.y, direction * d.x, face);
        if(referencePoint.face == 2 || referencePoint.face == 3)//North South,  XY rotation 
            return referencePoint.offset(direction * d.y, direction * -d.x, d.z, face);
        //East or West  YZ rotation
        return referencePoint.offset(d.x, direction * -d.z, direction * d.y, face);
    }

    private int getDimensionNumber(){
        if( getWorld() == null)
            setWorld(defaultWorld());
        return getWorld().provider.getDimension();
    }

    private static World defaultWorld() {
        return FMLServerHandler.instance().getServer().getWorld(0);
    }

    @Override
    public boolean equals(Object otherObj)
    {
        if (!(otherObj instanceof BlockPos)){
            return false;
        }
        else{
            BlockPos other = (BlockPos)otherObj;
            if( this.getX() == other.getX() && this.getY() == other.getY() && this.getZ() == other.getZ()){
                if(other instanceof WorldXYZ)
                    return ((WorldXYZ) other).getWorld() == this.getWorld();
                else //NOTE: This does not compare the face of each coordinate
                    return true;
            }
            return false;
        }
    }

    @NotNull
    public SigBlock getSigBlock() {
        return new SigBlock(getBlock());
    }

    //Simple wrapper method for getBlockID()
    @NotNull
    public Block getBlock() {
        return this.getWorld().getBlockState(this).getBlock();
    }

    //Sister function to getBlockID() for meta values.
//    public int getMetaId() {
//        return getWorld().getBlockMetadata(getX(), getY(), getZ());
//    }

    @NotNull
    public IBlockState getBlockState() {
        return this.getWorld().getBlockState(this);
    }
    /**
     * Simple wrapper method for setBlockID()
     * @param block
     * @return true if successful
     */
    @SuppressWarnings("UnusedReturnValue")

    public boolean setBlockIdAndUpdate(@NotNull Block block){
        if(block == Blocks.BEDROCK || getBlock() == Blocks.BEDROCK)
            return false; //You cannot delete or place bedrock
        return this.getWorld().setBlockState(this, block.getDefaultState());
    }

    public boolean setBlockIdAndUpdate(@NotNull IBlockState block){
        if(block.getBlock() == Blocks.BEDROCK || getBlock() == Blocks.BEDROCK)
            return false; //You cannot delete or place bedrock
        return this.getWorld().setBlockState(this, block);
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean setBlockId(@NotNull SigBlock sig){
        if(sig.equals(Blocks.BEDROCK) || getBlock() == Blocks.BEDROCK)
            return false; //You cannot delete or place bedrock
//        return this.getWorld().setBlock(getX(), getY(), getZ(), sig.blockID, sig.meta, 2);
        return this.getWorld().setBlockState(this, sig.getState());
        //NOTE: Use last arg 3 if you want a block update.
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean setBlock(@NotNull IBlockState block){
        if(block == Blocks.BEDROCK || getBlock() == Blocks.BEDROCK)
            return false; //You cannot delete or place bedrock
//        return this.getWorld().setBlock(getX(), getY(), getZ(), blockID, meta, 3);
        return this.getWorld().setBlockState(this, block);
    }

    @NotNull
    public String toString(){//this is designed to match the GSON output
        return "{\"dimensionID\":"+dimensionID+",\"face\":"+face+",\"getX()\":"+getX()+",\"getY()\":"+getY()+",\"getZ()\":"+getZ()+"}";
//        return "(" + getX() + "," + getY() +  "," + getZ() + ")";
    }

    @NotNull
    public ArrayList<WorldXYZ> getDirectNeighbors() {
        ArrayList<WorldXYZ> neighbors = new ArrayList<>();
        //6 cardinal sides
        neighbors.add(offset(0, 1,0));
        neighbors.add(offset(0,-1,0));
        neighbors.add(offset(0,0,-1));
        neighbors.add(offset( 1,0,0));
        neighbors.add(offset(0,0, 1));
        neighbors.add(offset(-1,0,0));
        return neighbors;
    }
    
    @NotNull
    public ArrayList<WorldXYZ> getNeighbors() {
        ArrayList<WorldXYZ> neighbors = new ArrayList<>();
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

    @NotNull
    public ArrayList<WorldXYZ> getNeighbors(@NotNull Vector3 orientation) {
        int x = 0;
        int y = 0;
        int z = 0;
        ArrayList<WorldXYZ> neighbors = new ArrayList<>();
        if(Math.abs(orientation.x) == 1) {
            for(z = -1; z <= 1; ++z){
                for(y = -1; y <= 1; ++y){
                    neighbors.add(offset(x,y,z));
                }
            }
            return neighbors;
        }
        if(Math.abs(orientation.y) == 1) {
            for(z = -1; z <= 1; ++z){
                for(x = -1; x <= 1; ++x){
                    neighbors.add(offset(x,y,z));
                }
            }
            return neighbors;
        }
        if(Math.abs(orientation.z) == 1) {
            for(y = -1; y <= 1; ++y){
                for(x = -1; x <= 1; ++x){
                    neighbors.add(offset(x,y,z));
                }
            }
            return neighbors;
        }
        return getNeighbors();
    }
    
    public double getDistance(@NotNull WorldXYZ other) {
        double xzDist_2 =  (getX() - other.getX())*(getX() - other.getX()) + (getZ() - other.getZ())*(getZ() - other.getZ());//Math.sqrt(
        return Math.sqrt( xzDist_2 + (getY() - other.getY())*(getY() - other.getY()));
    }

    public boolean isSolid() {
        Material base = getBlock().getMaterial(getBlockState());
        return base.isSolid(); // possibly canPlaceBlockAt()
    }


    public boolean isCrushable() {
        return TierHelper.isCrushable(getBlock());
    }

    public boolean blockEquals(Block target) {
        return Block.isEqualTo(getBlock(), target);
    }
}
