package com.newlinegaming.Runix.workers;

import java.util.HashMap;

import com.newlinegaming.Runix.WorldXYZ;

public class StructureMoveWorker implements IBlockWorker {

    private HashMap<WorldXYZ, WorldXYZ> moveMapping = null;
    
    @Override
    public void doWork() {
        //Step 1: collision
            //inside safelyTeleportStructure()
        //Step 2: remove sensitive everything
            //inside performMove()
        //Step 3: placement
            //take the next 100 blocks
            //move those blocks
        //Step 4: place sensitive blocks
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void scheduleNextWorkLoad() {
        // just do work every tick
        
    }

}
