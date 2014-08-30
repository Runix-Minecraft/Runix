package com.newlinegaming.Runix.rune;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;

import com.newlinegaming.Runix.AbstractTimedRune;
import com.newlinegaming.Runix.NotEnoughRunicEnergyException;
import com.newlinegaming.Runix.PersistentRune;
import com.newlinegaming.Runix.SigBlock;
import com.newlinegaming.Runix.Tiers;
import com.newlinegaming.Runix.Vector3;
import com.newlinegaming.Runix.WorldXYZ;
import com.newlinegaming.Runix.utils.Util_Movement;

public class BuildMasterRune extends AbstractTimedRune {
    protected static ArrayList<PersistentRune> activeMagic = new ArrayList<PersistentRune>();

    public BuildMasterRune() {
        runeName = "Build Master";
        updateEveryXTicks(20);
    }

    public BuildMasterRune( WorldXYZ coords, EntityPlayer activator ) {
        super(coords, activator, "Build Master");
        updateEveryXTicks(20);
    }

    @Override
    protected void poke(EntityPlayer poker, WorldXYZ coords) {
        consumeKeyBlock(coords);
        disabled = false;
    }
    
    @Override
    protected void onUpdateTick(EntityPlayer player) {
        if(energy > 1){
            HashSet<WorldXYZ> structure = scanTemplate();
            HashMap<WorldXYZ, WorldXYZ> buildMapping = findFirstMissingBlock(structure);
            if(buildMapping.isEmpty())
                disabled = true;
            for(WorldXYZ origin : buildMapping.keySet()) {
                WorldXYZ destination = buildMapping.get(origin);
                Block sourceBlock = origin.getBlock();
                Block destinationBlock = destination.getBlock();
                if( !sourceBlock.equals(destinationBlock)) {
                    if(Tiers.isCrushable(destinationBlock)) {
                        try {
                            setBlockIdAndUpdate(destination, sourceBlock);//no physics?
                        } catch (NotEnoughRunicEnergyException e) {
                            reportOutOfGas(player);
                            return;
                        }
                    } else {
                        //obstruction 
                        return;
                    }
                } else { //harmless pass through
                    //nothing to do, it will increment distance once other blocks are placed
                }
            }
        }
    }

    private HashMap<WorldXYZ,WorldXYZ> findFirstMissingBlock(HashSet<WorldXYZ> structure) {
        Vector3 offset = Vector3.facing[location.face];
        WorldXYZ currentLayer = location;
        HashMap<WorldXYZ, WorldXYZ> buildMapping = Util_Movement.
                displaceShape(structure, location, currentLayer);
        while(partialTemplateMatch(buildMapping)) {
            currentLayer = currentLayer.offset(offset);
            buildMapping = Util_Movement.
                    displaceShape(structure, location, currentLayer);
        }
        
        if(anyCrushable(buildMapping.values()))//any space to build?
            return buildMapping;
        else
            return new HashMap<WorldXYZ, WorldXYZ>();
    }

    /** Very forgiving match to any block in the template to continue the whole template. 
     * Might cause weird behavior especially if you're using smooth stone.
     * @param buildMapping
     * @return
     */
    private boolean partialTemplateMatch(HashMap<WorldXYZ, WorldXYZ> buildMapping) {
        for(WorldXYZ origin : buildMapping.keySet()) {
            WorldXYZ destination = buildMapping.get(origin);
            Block sourceBlock = origin.getBlock();
            Block destinationBlock = destination.getBlock();
            if( sourceBlock.equals(destinationBlock)) {
                return true;
            }
        }
        return false;
    }

    private boolean anyCrushable(Collection<WorldXYZ> points) {
        for( WorldXYZ point : points) {
            if(Tiers.isCrushable(point.getBlock()))
                return true;
        }
        return false;
    }

    private HashSet<WorldXYZ> scanTemplate() {
        Vector3 orientation = Vector3.facing[location.face];
        HashSet<WorldXYZ> points = layerConductance(location.offset(orientation.multiply(2)), 20, orientation);
        return points;
    }
    
    /**This will return an empty list if the activation would tear a structure in two. */
    public HashSet<WorldXYZ> layerConductance(WorldXYZ startPoint, int maxDistance, Vector3 orientation) {
        HashSet<WorldXYZ> workingSet = new HashSet<WorldXYZ>();
        HashSet<WorldXYZ> activeEdge;
        HashSet<WorldXYZ> nextEdge = new HashSet<WorldXYZ>();
        workingSet.add(startPoint);
        nextEdge.add(startPoint);
        
        for(int iterationStep = maxDistance+1; iterationStep > 0; iterationStep--) {
            activeEdge = nextEdge;
            nextEdge = new HashSet<WorldXYZ>();
          //tear detection: this should be empty by the last step
            if(iterationStep == 1 && activeEdge.size() != 0) 
                return new HashSet<WorldXYZ>();
            
            for(WorldXYZ block : activeEdge) {
                ArrayList<WorldXYZ> neighbors = block.getNeighbors(orientation);
                for(WorldXYZ n : neighbors) {
                    Block blockID = n.getBlock();
                    // && blockID != 0 && blockID != 1){  // this is the Fun version!
                    if( !workingSet.contains(n) && blockID != Blocks.air ) {
                        workingSet.add(n);
                        nextEdge.add(n);
                    }
                }
            }
        }
        return workingSet;
    }
    
    @Override
    public ArrayList<PersistentRune> getActiveMagic() {
        return activeMagic;
    }

    @Override
    public boolean oneRunePerPerson() {
        return false;
    }

    @Override
    protected Block[][][] runicTemplateOriginal() {
        Block IRON = Blocks.iron_block;
        Block SBRK = Blocks.stonebrick;
        Block REDB = Blocks.redstone_block;
        return new Block[][][] {{
            {SBRK,REDB, SBRK}, //TODO template from wiki
            {IRON,FUEL , IRON},
            {SBRK,IRON, SBRK}
        }};
    }

    @Override
    public boolean isFlatRuneOnly() {
        return false;
    }
    @Override
    public boolean isAssymetrical() {
        return false;
    }
}
