package com.newlinegaming.Runix;

import java.util.ArrayList;
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
        HashSet<WorldCoordinates> vehicleBlocks = conductanceStep(coords);
        aetherSay(player, "Found " + vehicleBlocks.size() + " tier blocks");
    }

    private HashSet<WorldCoordinates> conductanceStep(WorldCoordinates startPoint) {
        //TODO: perhaps rename WorldCoordinates to WorldXYZ
        HashSet<WorldCoordinates> workingSet = new HashSet<WorldCoordinates>();
        HashSet<WorldCoordinates> activeEdge;
        HashSet<WorldCoordinates> nextEdge = new HashSet<WorldCoordinates>();
        workingSet.add(startPoint);
        nextEdge.add(startPoint);
        
        for(int iterationStep = 3; iterationStep > 0; iterationStep--) {
            activeEdge = nextEdge;
            nextEdge = new HashSet<WorldCoordinates>();
            
            for(WorldCoordinates block : activeEdge) {
                ArrayList<WorldCoordinates> neighbors = block.getNeighbors();
                for(WorldCoordinates n : neighbors) {
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
