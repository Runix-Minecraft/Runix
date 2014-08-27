package com.newlinegaming.Runix.workers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import net.minecraft.init.Blocks;

import com.newlinegaming.Runix.SigBlock;
import com.newlinegaming.Runix.Tiers;
import com.newlinegaming.Runix.WorldXYZ;
import com.newlinegaming.Runix.handlers.RuneHandler;
import com.newlinegaming.Runix.helper.LogHelper;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;

public class StructureMoveWorker implements IBlockWorker {

    private HashMap<WorldXYZ, WorldXYZ> moveMapping = null;
    HashSet<WorldXYZ> newPositions = new HashSet<WorldXYZ>();
    HashMap<WorldXYZ, SigBlock> sensitiveBlocks = null;
    private WorldXYZ bumpedBlock = null;  // created whenever a move collides with itself
    private int currentTimer = 0;
    private int maxTimer = 20; // 20 ticks = 1 second
    
    public StructureMoveWorker(HashMap<WorldXYZ, WorldXYZ> moveMap){
        moveMapping = moveMap;
//        scheduleWorkLoad();
    }
    
    @SubscribeEvent
    public void doWork(ServerTickEvent event) {
        ++currentTimer;
        if( !isFinished() && currentTimer >= maxTimer){ // called only once per second
            currentTimer = 0;
            SigBlock AIR = new SigBlock(Blocks.air, 0);
            //Step 1: collision
                //inside safelyTeleportStructure()
            //Step 2: remove sensitive everything
                //inside performMove()
            if( sensitiveBlocks == null) {
                sensitiveBlocks = new HashMap<WorldXYZ, SigBlock>();
                ArrayList<WorldXYZ> airBlocks = new ArrayList<WorldXYZ>();
                for(WorldXYZ point : moveMapping.keySet()) {
                    SigBlock block = point.getSigBlock();
                    if( Tiers.isMoveSensitive(block.blockID) ){//we're splitting sensitive blocks into their own set
                        sensitiveBlocks.put(moveMapping.get(point), block);//record at new location
                        point.setBlockId(AIR);//delete sensitive blocks first to prevent drops
                    } else if(block.equals(Blocks.air)) { 
                        airBlocks.add(point); //don't calculate on AIR blocks
                    }
                }
                moveMapping.keySet().removeAll(airBlocks);
            } else { 
                if( !moveMapping.isEmpty()) { // do other work later
                    HashMap<WorldXYZ, WorldXYZ> currentMove = new HashMap<WorldXYZ, WorldXYZ>();
                    for(WorldXYZ origin : moveMapping.keySet()){
                        currentMove.put(origin, moveMapping.get(origin)); 
                        if( currentMove.size() > 100)
                            break;
                    }
                    moveMapping.keySet().removeAll(currentMove.keySet()); // delete it from our todo list

                    //Step 3: placement
                        //take the next 100 blocks
                        //move those blocks
                    for(WorldXYZ origin : currentMove.keySet()) { //Do Move
                        WorldXYZ destination = currentMove.get(origin);
                        SigBlock block = origin.getSigBlock();
                        destination.setBlockId(block);  //set at destination
                        origin.setBlockId(AIR); //delete at origin
                        // TODO: delete old block in a separate loop to avoid collisions with the new positioning
                    }
                    
                    newPositions.addAll(currentMove.values());
                    RuneHandler.getInstance().moveMagic(currentMove);
        
                } else { //last phase
                  //Step 4: place sensitive blocks
                    LogHelper.info("Placing sensitive blocks");
                    for(WorldXYZ specialPos : sensitiveBlocks.keySet()) //Place all the sensitive blocks
                        specialPos.setBlockId(sensitiveBlocks.get(specialPos));//blocks like torches and redstone
                    
                    newPositions.addAll(sensitiveBlocks.keySet());//merge sensitive locations back in with normal
                    sensitiveBlocks.clear();
                    //return newPositions; //TODO: For Faith and FTP, return doesn't matter, it does matter for Runecraft
                }
            }
        }
    }

    @Override
    public boolean isFinished() {
        return moveMapping.isEmpty()  && (sensitiveBlocks != null && sensitiveBlocks.isEmpty());
    }

    @Override
    public void scheduleWorkLoad() {
        FMLCommonHandler.instance().bus().register(this);
    }

}
