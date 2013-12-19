package com.newlinegaming.Runix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;

public class RunecraftRune extends AbstractRune {

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
        int tier = 4;
        HashSet<WorldCoordinates> vehicleBlocks = conductanceStep(coords, (int)Math.pow(2, tier+1));
        aetherSay(player, "Found " + vehicleBlocks.size() + " tier blocks");
        safelyMovePlayer(player, coords.offset(0, 5, 0));
        moveShape(vehicleBlocks, 0, 5, 0); //Josiah: I'm not sure if we should move the player or blocks first
    }

    private void moveShape(HashSet<WorldCoordinates> vehicleBlocks, int dX, int dY, int dZ) {
        //map each WorldCoordinate to a blockID
        HashMap<WorldCoordinates, Integer> savedPattern = new HashMap<WorldCoordinates, Integer>();
        for(WorldCoordinates loc : vehicleBlocks){
            savedPattern.put(loc, new Integer(loc.getBlockId()));
            loc.setBlockId(0); // delete old block
        }
        //offset(0,0,1) all coordinates //set to blockID
        for(WorldCoordinates start : savedPattern.keySet())
            start.offset(dX, dY, dZ).setBlockId(savedPattern.get(start)); //place new block 1 to the South
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
                    if( !workingSet.contains(n) && !RuneHandler.tiers.isTier0(n.getBlockId()) ) {
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
