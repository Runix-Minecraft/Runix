package com.newlinegaming.Runix;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import net.minecraft.entity.player.EntityPlayer;

public class Util_Movement {

    public static HashMap<WorldXYZ, WorldXYZ> xzRotation(Collection<WorldXYZ> startingShape, WorldXYZ centerPoint, boolean counterClockwise){
        //centerPoint is also the axis of rotation
        HashMap<WorldXYZ, WorldXYZ> rotationMapping = new HashMap<WorldXYZ, WorldXYZ>();
        for( WorldXYZ point : startingShape ){
            rotationMapping.put(point, point.rotate(centerPoint, counterClockwise));//flip sign on X
        }
        return rotationMapping;
    }
    


    /**Note: when designing moving runes, DO NOT update your PersistentRune.location variable.  
     * moveShape() calls moveMagic() which will update everything including yourself.*/
    public static HashSet<WorldXYZ> performMove(HashMap<WorldXYZ, WorldXYZ> moveMapping) 
    {
        SigBlock AIR = new SigBlock(0,0);
        HashMap<WorldXYZ, SigBlock> newStructure = new HashMap<WorldXYZ, SigBlock>();
        HashMap<WorldXYZ, SigBlock> sensitiveBlocks = new HashMap<WorldXYZ, SigBlock>();
        for(WorldXYZ point : moveMapping.keySet()){
            SigBlock block = point.getSigBlock();
            if( Tiers.isMoveSensitive(block.blockID) ){//we're splitting sensitive blocks into their own set
                sensitiveBlocks.put(moveMapping.get(point), block);//record at new location
                point.setBlockId(AIR);//delete sensitive blocks first to prevent drops
            }
            else if(block.blockID != 0){//don't write AIR blocks
                newStructure.put(moveMapping.get(point), block);//record original at new location
            }
        }
        
        for(WorldXYZ loc : moveMapping.keySet()) //Delete everything
            loc.setBlockId(AIR); // delete old block in a separate loop to avoid collisions with the new positioning

        for(WorldXYZ destination : newStructure.keySet()) //place all the blocks at new location
            destination.setBlockId(newStructure.get(destination));//doesn't include sensitive blocks

        for(WorldXYZ specialPos : sensitiveBlocks.keySet()) //Place all the sensitive blocks
            specialPos.setBlockId(sensitiveBlocks.get(specialPos));//blocks like torches and redstone
        
        RuneHandler.getInstance().moveMagic(moveMapping);

        HashSet<WorldXYZ> newPositions = new HashSet<WorldXYZ>(newStructure.keySet());
        newPositions.addAll(sensitiveBlocks.keySet());//merge sensitive locations back in with normal
        return newPositions;
    }

    /**Geometry: figure out if we're on the left or right side of the rune relative to the player
     */
    public static boolean lookingRightOfCenterBlock(EntityPlayer player, WorldXYZ referencePoint) {
        float yaw = player.rotationYawHead;//assumption: you're looking at the block you right clicked
        yaw = (yaw > 0.0) ? yaw  : yaw + 360.0F; //Josiah: minecraft yaw wanders into negatives sometimes...
        double opposite = player.posZ - referencePoint.posZ - .5;
        double adjacent = player.posX - referencePoint.posX - .5;
        double angle = Math.toDegrees(Math.atan( opposite / adjacent )) + 90.0;
        if( adjacent > 0.0)
            angle += 180.0;
//        System.out.println("Rune: " + angle + "  Yaw: " + yaw + " = " + (angle - yaw));
        if( ((angle - yaw) < 180.0 && (angle - yaw) > 0.0) || //the difference between the angle to the reference
                ((angle - yaw) < -180.0 && (angle - yaw) > -360.0) )//and the angle we're looking determines left/right
            return true;
        else
            return false;
    }



    public static HashMap<WorldXYZ, WorldXYZ> displaceShape(Collection<WorldXYZ> set, int dX, int dY, int dZ) {
        HashMap<WorldXYZ, WorldXYZ> moveMapping = new HashMap<WorldXYZ, WorldXYZ>();
        Vector3 displacement = new Vector3(dX, dY, dZ);
        for(WorldXYZ point : set)
            moveMapping.put(point, point.offset(displacement));
        return moveMapping;
    }

    /**This function side effects the argument instead of making a new copy.*/
    public static void bumpShape(Collection<WorldXYZ> set, Vector3 bumper)
    {
        for( WorldXYZ point : set)
            point.bump(bumper.x, bumper.y, bumper.z);
    }

    public static boolean shapeCollides(HashMap<WorldXYZ, WorldXYZ> move) {
        for(WorldXYZ newPos : move.values()){
            if( !move.containsKey(newPos) //doesn't overlap with the old position
                    && newPos.getBlockId() != 0 //AIR
                    && !Tiers.isCrushable(newPos.getBlockId()) ) //Something's there, but squish it anyways
                return true;
        }
        return false;
    }

    public static boolean teleportCollision(HashSet<WorldXYZ> structure) {
        for(WorldXYZ point: structure)
            if(point.getBlockId() != 0 && Tiers.isCrushable(point.getBlockId()))
                return true;
        return false;
    }
}
