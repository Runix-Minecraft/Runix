package com.newlinegaming.Runix.utils;

import java.util.*;

import com.newlinegaming.Runix.SigBlock;
import com.newlinegaming.Runix.Vector3;
import com.newlinegaming.Runix.WorldXYZ;
import com.newlinegaming.Runix.handlers.RuneHandler;
import com.newlinegaming.Runix.helper.TierHelper;
import com.newlinegaming.Runix.workers.StructureMoveWorker;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import org.jetbrains.annotations.NotNull;

public class UtilMovement {

    @NotNull
    public static HashMap<WorldXYZ, WorldXYZ> xzRotation(@NotNull Collection<WorldXYZ> startingShape, @NotNull WorldXYZ centerPoint, boolean counterClockwise){
        //centerPoint is also the axis of rotation
        HashMap<WorldXYZ, WorldXYZ> rotationMapping = new HashMap<>();
        for( WorldXYZ point : startingShape ){
            rotationMapping.put(point, point.rotate(centerPoint, counterClockwise));//flip sign on X
        }
        return rotationMapping;
    }
    

    /**
     * Note: when designing moving runes, DO NOT update your PersistentRune.location variable.
     * moveShape() calls moveMagic() which will update everything including yourself.
     */
    @NotNull
    public static HashSet<WorldXYZ> performMove(@NotNull HashMap<WorldXYZ, WorldXYZ> moveMapping) {
        SigBlock AIR = new SigBlock(Blocks.AIR);
        HashMap<WorldXYZ, SigBlock> newStructure = new HashMap<>();
        HashMap<WorldXYZ, SigBlock> sensitiveBlocks = new HashMap<>();
        for(WorldXYZ point : moveMapping.keySet()){
            SigBlock block = point.getSigBlock();
            if(TierHelper.isMoveSensitive(block.getBlock())) {//we're splitting sensitive blocks into their own set
                sensitiveBlocks.put(moveMapping.get(point), block);//record at new location
                point.setBlockId(AIR);//delete sensitive blocks first to prevent drops
            }
            else if(!block.equals(Blocks.AIR)) {//don't write AIR blocks
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

        HashSet<WorldXYZ> newPositions = new HashSet<>(newStructure.keySet());
        newPositions.addAll(sensitiveBlocks.keySet());//merge sensitive locations back in with normal
        return newPositions;
    }

    /**
     * Geometry: figure out if we're on the left or right side of the rune relative to the player
     */
    public static boolean lookingRightOfCenterBlock(@NotNull EntityPlayer player, @NotNull WorldXYZ referencePoint) {
        float yaw = player.rotationYawHead;//assumption: you're looking at the block you right clicked
        yaw = (yaw > 0.0) ? yaw  : yaw + 360.0F; //Josiah: minecraft yaw wanders into negatives sometimes...
        double opposite = player.getPosition().getZ() - referencePoint.getZ() - .5;
        double adjacent = player.getPosition().getX() - referencePoint.getX() - .5;
        double angle = Math.toDegrees(Math.atan( opposite / adjacent )) + 90.0;
        if( adjacent > 0.0)
            angle += 180.0;
//        System.out.println("Rune: " + angle + "  Yaw: " + yaw + " = " + (angle - yaw));
        //the difference between the angle to the reference
//and the angle we're looking determines left/right
        return ((angle - yaw) < 180.0 && (angle - yaw) > 0.0) || //the difference between the angle to the reference
                ((angle - yaw) < -180.0 && (angle - yaw) > -360.0);
    }

    @NotNull
    public static LinkedHashMap<WorldXYZ, WorldXYZ> displaceShape(@NotNull Collection<WorldXYZ> set, @NotNull WorldXYZ startPoint, @NotNull WorldXYZ destinationCenter) {
        LinkedHashMap<WorldXYZ, WorldXYZ> moveMapping = new LinkedHashMap<>();
        Vector3 displacement = new Vector3(startPoint, destinationCenter);
        for(WorldXYZ point : set)
            moveMapping.put(point, point.offsetWorld(displacement, destinationCenter.getWorld()));
        return moveMapping;
    }

    public static boolean shapeCollides(@NotNull HashMap<WorldXYZ, WorldXYZ> move) {
        for(WorldXYZ newPos : move.values()){
            if( !move.containsKey(newPos) && newPos.getBlock() != Blocks.AIR && !TierHelper.isCrushable(newPos.getBlock()))
                return true;
        }
        return false;
    }

    /**
     * Attempt to teleport the structure to a non-colliding location at destination, scanning in the direction of destination.face.
     * @param structure being moved
     * @param destination block (with facing) of waypoint
     * @return center of destination teleport or null if the teleport was unsuccessful
     */
    public static WorldXYZ safelyTeleportStructure(@NotNull HashSet<WorldXYZ> structure, @NotNull WorldXYZ startPoint, @NotNull WorldXYZ destination, int extremitySize) {
        Vector3 scanDirection = Vector3.facing[destination.face];
        Vector3 roomForShip = scanDirection.multiply(extremitySize);
        int collisionTries = 0;
        LinkedHashMap<WorldXYZ, WorldXYZ> moveMapping;
        WorldXYZ destinationCenter;
        do {
            Vector3 stepSize = scanDirection.multiply(5);//try moving it over 5m
            destinationCenter = destination.offset(roomForShip).offsetWorld(stepSize.multiply(collisionTries), destination.getWorld() ); //base roomForShip + collisionTries iterations
            moveMapping = displaceShape(structure, startPoint, destinationCenter);
            collisionTries++;
        } while( shapeCollides( moveMapping ) && collisionTries < 20);
            
        if(collisionTries >= 20)
            return null; //the teleport did not work
        // used to be performMove(moveMapping);, now this is only used by Runecraft
        StructureMoveWorker worker = new StructureMoveWorker(moveMapping);
        worker.scheduleWorkLoad();
        return destinationCenter;
    }


    @NotNull
    public static HashMap<WorldXYZ, SigBlock> rotateStructureInMemory(@NotNull HashMap<WorldXYZ, SigBlock> shape, @NotNull WorldXYZ center, int nTurns) {
        HashMap<WorldXYZ, SigBlock> startShape = new HashMap<>(shape);
        
        for(int turnNumber = 0; turnNumber < nTurns; ++turnNumber) {
            HashMap<WorldXYZ, WorldXYZ> move = UtilMovement.xzRotation(startShape.keySet(), center, false);

            HashMap<WorldXYZ, SigBlock> newShape = new HashMap<>();//blank variable for swapping purposes
            for(WorldXYZ origin : move.keySet()) {
                WorldXYZ destination = move.get(origin);
                newShape.put(destination, startShape.get(origin));
            }
            startShape = newShape;
        }
        return startShape;
    }

    @NotNull
    public static HashMap<WorldXYZ, SigBlock> scanBlocksInShape(@NotNull Set<WorldXYZ> shape) {
        HashMap<WorldXYZ, SigBlock> actualBlocks = new HashMap<>();
        for(WorldXYZ point : shape) {
            actualBlocks.put(point, point.getSigBlock());
        }
        return actualBlocks;
    }

}
