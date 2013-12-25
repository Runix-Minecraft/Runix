package com.newlinegaming.Runix;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;

public class Util_Movement {

    public static HashMap<WorldXYZ, WorldXYZ> xzRotation(Collection<WorldXYZ> startingShape, WorldXYZ centerPoint, boolean counterClockwise){
        //centerPoint is also the axis of rotation
        HashMap<WorldXYZ, WorldXYZ> rotationMapping = new HashMap<WorldXYZ, WorldXYZ>();
        for( WorldXYZ point : startingShape ){
            rotationMapping.put(point, point.rotate(centerPoint, counterClockwise));//flip sign on X
        }
        return rotationMapping;
    }
    
    public static HashMap<WorldXYZ, SigBlock> moveShape(HashMap<WorldXYZ, SigBlock> vehicleBlocks, int dX, int dY, int dZ) {
        //Josiah: If you're having trouble with glitches, try only running it on the server side
        //if( !world.isRemote ) //this is only true server side
        HashMap<WorldXYZ, SigBlock> sensitiveBlocks = new HashMap<WorldXYZ, SigBlock>();
        for(Entry<WorldXYZ, SigBlock> pair : vehicleBlocks.entrySet()){
            if( Tiers.isMoveSensitive(pair.getValue().blockID) ){
                sensitiveBlocks.put(pair.getKey().offset(dX, dY, dZ), pair.getValue());
                pair.getKey().setBlockId(0);
            }//Josiah: Hopefully this isn't too slow.  I coulddn't find a shorter path to preserving these blocks
        }
        
        for(WorldXYZ loc : vehicleBlocks.keySet())
            loc.setBlockId(0); // delete old block in a separate loop to avoid collisions

        HashMap<WorldXYZ, SigBlock> newPositions = new HashMap<WorldXYZ, SigBlock>();
        for(WorldXYZ start : vehicleBlocks.keySet()){
            WorldXYZ target = start.offset(dX, dY, dZ);
            SigBlock sig = vehicleBlocks.get(start);
            if( !Tiers.isMoveSensitive(sig.blockID) )
                target.setBlockId(sig);
            newPositions.put(target, sig);
        }
        for(WorldXYZ specialPos : sensitiveBlocks.keySet()) //blocks like torches and redstone
            specialPos.setBlockId(sensitiveBlocks.get(specialPos)); 

//      RuneHandler.getInstance().moveMagic(vehicleBlocks.keySet(), dX, dY, dZ);
        return newPositions;
    }

    public static HashMap<WorldXYZ, SigBlock> rotateShape(HashMap<WorldXYZ, WorldXYZ> moveMapping) 
    {
        HashMap<WorldXYZ, SigBlock> oldStructure = new HashMap<WorldXYZ, SigBlock>();
        for(WorldXYZ p : moveMapping.keySet()){
            oldStructure.put(p, p.getSigBlock());//record original before we mess it up
        }
        
        HashMap<WorldXYZ, SigBlock> sensitiveBlocks = new HashMap<WorldXYZ, SigBlock>();
        for(WorldXYZ startingPosition : moveMapping.keySet()){
            SigBlock block = startingPosition.getSigBlock();
            if( Tiers.isMoveSensitive(block.blockID) ){
                sensitiveBlocks.put(moveMapping.get(startingPosition)  , block);
                startingPosition.setBlockId(0);//delete sensitive blocks first to prevent drops
            }
        }
        
        for(WorldXYZ loc : moveMapping.keySet())
            loc.setBlockId(0); // delete old block in a separate loop to avoid collisions with the new positioning

        HashMap<WorldXYZ, SigBlock> newStructure = new HashMap<WorldXYZ, SigBlock>();
        for(WorldXYZ start : oldStructure.keySet()){
            WorldXYZ target = moveMapping.get(start);
            SigBlock sig = oldStructure.get(start);
            if( !Tiers.isMoveSensitive(sig.blockID) )
                target.setBlockId(sig);
            newStructure.put(target, sig);
        }
        for(WorldXYZ specialPos : sensitiveBlocks.keySet()) //blocks like torches and redstone
            specialPos.setBlockId(sensitiveBlocks.get(specialPos)); 
        
//        RuneHandler.getInstance().moveMagic(vehicleBlocks.keySet(), dX, dY, dZ);
        return newStructure;
    }
    


}
