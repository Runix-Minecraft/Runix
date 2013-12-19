package com.newlinegaming.Runix;

import java.util.ArrayList;
import java.util.HashSet;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class RunecraftRune extends AbstractRune {//TODO: candidate for a persistence interface
    
    public static ArrayList<RunecraftRune> activeVehicles = new ArrayList<RunecraftRune>();
    public WorldCoordinates location;
    public EntityPlayer driver;
    public int tier;
    private HashSet<WorldCoordinates> vehicleBlocks;
    
    public RunecraftRune(){}
    
    public RunecraftRune(WorldCoordinates coords, EntityPlayer player)
    {
        location = new WorldCoordinates(coords);
        driver = player;
        tier = Tiers.getTier( coords.offset(-1, 0, -1).getBlockId() );
        vehicleBlocks = conductanceStep(coords, (int)Math.pow(2, tier+1));
        aetherSay(player, "Found " + vehicleBlocks.size() + " tier blocks");
        updateEveryXTicks(20);
    }

    /** This registers the rune as being actively updated.  Forge (thru RuneTimer) will call
     * onUpdateTick() every xTicks from here on out until it is turned off.  There are
     * 20 ticks per second.
     * @param xTicks number of ticks to wait between calls.  20 ticks = 1 second
     */
    protected void updateEveryXTicks(int xTicks) {
        TickRegistry.registerTickHandler(new RuneTimer(this, xTicks), Side.SERVER);
    }


    void onUpdateTick(EntityPlayer subject) {
        safelyMovePlayer(driver, location.offset(0, 1, 0));
        moveShape(vehicleBlocks, 0, 1, 0); //Josiah: I'm not sure if we should move the player or blocks first
        location = location.offset(0, 1, 0);
    }

    @Override
    public int[][][] blockPattern() {
        int IRON = Block.blockIron.blockID;
        int LPIS = Block.blockLapis.blockID;
        int GLAS = Block.glass.blockID;
        return new int[][][]
                {{{LPIS,IRON,GLAS},
                  
                  {IRON,NONE,IRON},
                  
                  {TIER,IRON,TIER}}};
    }

    @Override
    public void execute(EntityPlayer player, WorldCoordinates coords) {
        accept(player);
        if( addOrRemoveVehicle(coords, player) )
            aetherSay(player, "The Runecraft is now locked to your body.");
        else
            aetherSay(player, "You are now free from the Runecraft.");
    }

    /** This method exists to ensure that no duplicate vehicles are persisted. */
    public boolean addOrRemoveVehicle(WorldCoordinates centerPoint, EntityPlayer player) {
        for(RunecraftRune oldRCV : activeVehicles){
            if( oldRCV.location.equals(centerPoint) ){
                activeVehicles.remove(oldRCV);
                return false; 
            }
        }
        activeVehicles.add(new RunecraftRune(centerPoint, player));
        return true;
    }
    
    
    private HashSet<WorldCoordinates> conductanceStep(WorldCoordinates startPoint, int maxDistance) {
        //TODO: perhaps rename WorldCoordinates to WorldXYZ
        HashSet<WorldCoordinates> workingSet = new HashSet<WorldCoordinates>();
        HashSet<WorldCoordinates> activeEdge;
        HashSet<WorldCoordinates> nextEdge = new HashSet<WorldCoordinates>();
        workingSet.add(startPoint);
        nextEdge.add(startPoint);
        
        for(int iterationStep = maxDistance; iterationStep > 0; iterationStep--) {
            activeEdge = nextEdge;
            nextEdge = new HashSet<WorldCoordinates>();
            
            for(WorldCoordinates block : activeEdge) {
                ArrayList<WorldCoordinates> neighbors = block.getNeighbors();
                for(WorldCoordinates n : neighbors) {
                    int blockID = n.getBlockId();
                    // && blockID != 0 && blockID != 1){  // this is the Fun version!
                    if( !workingSet.contains(n) && !Tiers.isNatural(blockID) ) {
                        //TODO: possible slow down = long list of natural blocks
                        workingSet.add(n);
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
