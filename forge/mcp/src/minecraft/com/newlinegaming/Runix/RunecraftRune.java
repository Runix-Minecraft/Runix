package com.newlinegaming.Runix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;

public class RunecraftRune extends AbstractTimedRune {
    
    public static ArrayList<RunecraftRune> activeVehicles = new ArrayList<RunecraftRune>();
    public WorldCoordinates location;
    public EntityPlayer driver;
    public int tier;
    private HashMap<WorldCoordinates, SigBlock> vehicleBlocks;
    
    public RunecraftRune(){}
    
    /**Runecraft Runix Vehicle is now working and tracking with player while active.  
     * Toggle it by right clicking the center block.  You can jump up to travel up, just not down yet.
     * @param coords Center rune block that the vehicle is checked from 
     * @param player Person that the vehicle gloms on to
     */
    public RunecraftRune(WorldCoordinates coords, EntityPlayer player)
    {
        location = new WorldCoordinates(coords);
        driver = player;
        scanForVehicleShape(coords, player);
        updateEveryXTicks(4);
    }

    protected void onUpdateTick(EntityPlayer subject) {
        //TODO: we're not currently using subject
        if(driver != null && !driver.worldObj.isRemote)
        {//Josiah: turns out running this on server and client side causes strange duplications
            int dX = (int) (driver.posX - location.posX - .5);
            int dY = (int) (driver.posY - location.posY - 1);
            int dZ = (int) (driver.posZ - location.posZ - .5);
            if(driver.isSneaking())
                dY -= 1;
            if(dX != 0 || dY != 0 || dZ != 0){
                vehicleBlocks = moveShape(vehicleBlocks, dX, dY, dZ); //Josiah: I'm not sure if we should move the player or blocks first
                location = location.offset(dX, dY, dZ);
            }
        }
    }

    @Override
    public int[][][] blockPattern() {
        int IRON = Block.blockIron.blockID;
        int LPIS = Block.blockLapis.blockID;
        int GLAS = Block.glass.blockID;
        return new int[][][]
                {{{LPIS,IRON,GLAS},
                  
                  {IRON,KEY ,IRON},
                  
                  {TIER,IRON,TIER}}};
    }

    @Override
    public void execute(EntityPlayer player, WorldCoordinates coords) {
        accept(player);
        if( addOrToggleVehicle(coords, player) )
            aetherSay(player, "The Runecraft is now locked to your body.");
        else
            aetherSay(player, "You are now free from the Runecraft.");
    }

    /** This method exists to ensure that no duplicate vehicles are persisted. */
    public boolean addOrToggleVehicle(WorldCoordinates centerPoint, EntityPlayer player) {
        for(RunecraftRune oldRCV : activeVehicles){
            if( oldRCV.location.equals(centerPoint) )// if it exists already, toggle state
            {
                if(oldRCV.driver == null){ // not currently active
                    oldRCV.driver = player; // assign a driver and start
                    oldRCV.scanForVehicleShape(centerPoint, player);
                    return true;
                }
                else{
                    oldRCV.driver = null; //turn off the vehicle
                    return false;
                }
            }
        }
        activeVehicles.add(new RunecraftRune(centerPoint, player));
        return true;
    }
    
    protected void scanForVehicleShape(WorldCoordinates coords, EntityPlayer player) {
        tier = Tiers.getTier( coords.offset(-1, 0, -1).getBlockId() );
        vehicleBlocks = conductanceStep(coords, (int)Math.pow(2, tier+1));
        aetherSay(player, "Found " + vehicleBlocks.size() + " tier blocks");
    }
    
    private HashMap<WorldCoordinates, SigBlock> conductanceStep(WorldCoordinates startPoint, int maxDistance) {
        //TODO: perhaps rename WorldCoordinates to WorldXYZ
        HashMap<WorldCoordinates, SigBlock> workingSet = new HashMap<WorldCoordinates, SigBlock>();
        HashSet<WorldCoordinates> activeEdge;
        HashSet<WorldCoordinates> nextEdge = new HashSet<WorldCoordinates>();
        workingSet.put(startPoint, startPoint.getSigBlock());
        nextEdge.add(startPoint);
        
        for(int iterationStep = maxDistance; iterationStep > 0; iterationStep--) {
            activeEdge = nextEdge;
            nextEdge = new HashSet<WorldCoordinates>();
            
            for(WorldCoordinates block : activeEdge) {
                ArrayList<WorldCoordinates> neighbors = block.getNeighbors();
                for(WorldCoordinates n : neighbors) {
                    int blockID = n.getBlockId();
                    // && blockID != 0 && blockID != 1){  // this is the Fun version!
                    if( !workingSet.keySet().contains(n) && !Tiers.isNatural(blockID) ) {
                        //TODO: possible slow down = long list of natural blocks
                        workingSet.put(n, n.getSigBlock());
                        nextEdge.add(n);
                    }
                }
            }
        }
        return workingSet;
    }

    public String getRuneName() {
        return "Runecraft";
    }
}
