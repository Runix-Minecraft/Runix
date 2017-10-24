package com.newlinegaming.Runix.rune;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Collections;

import com.newlinegaming.Runix.NoSuchSignatureException;
import com.newlinegaming.Runix.Vector3;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;

import com.newlinegaming.Runix.PersistentRune;
import com.newlinegaming.Runix.WorldXYZ;
import org.jetbrains.annotations.NotNull;

public class FtpRune extends TeleporterRune {
    
    private static final ArrayList<PersistentRune> energizedFTP = new ArrayList<>();
    
    public FtpRune(){
        super();
        this.runeName = "Faith Transfer Portal";
    }
    
    public FtpRune(@NotNull WorldXYZ coords, @NotNull EntityPlayer activator) {
        super(coords, activator);
        this.runeName = "Faith Transfer Portal";
        usesConductance = true;
    }

    @NotNull
    public Block[][][] runicTemplateOriginal(){
        Block GOLD = Blocks.GOLD_BLOCK;
        return new Block[][][] {{
                {NONE,TIER,SIGR,TIER,NONE},
                {TIER,TIER,GOLD,TIER,TIER},
                {SIGR,GOLD,FUEL,GOLD,SIGR},
                {TIER,TIER,GOLD,TIER,TIER},
                {NONE,TIER,SIGR,TIER,NONE}
        }};
    }

    @Override
    protected void poke(@NotNull EntityPlayer player, @NotNull WorldXYZ coords) {
        consumeFuelBlock(coords);
        location.face = coords.face; //update the facing
        WorldXYZ destination;
        try {
            destination = findWaypointBySignature(getSignature());
        } catch (NoSuchSignatureException e) {
            aetherSay(player, "There's no waypoint with that signature.");
            return;
        }
        LinkedHashSet<WorldXYZ> structure = attachedStructureShape(player);
        if (structure.isEmpty())
            return;

        moveStructureAndPlayer(player, destination, structure);
    }

    @NotNull
    @Override
    public ArrayList<PersistentRune> getActiveMagic() {
        return energizedFTP;
    }

    @Override
    public int getTier(){
        return super.getTier() * 5;
    }

    /** Looks for the relevant extremity based on the direction of scanning.  If you're scanning UP,
     * it finds the lowest block and lists the distance offset + margin that would be the smallest
     * offset that could be used from the waypoint.
     *
     * Based on directionOfScanning, compare by non-zero coordinate.  If the sign is negative, take
     * them max instead of the min.
     * @param structure object of scrutiny
     * @param directionOfScanning direction that the structure will be moved in to create room if colliding
     * @return minimum displacement distance that could accommodate the structure
     */
    @Override
    public int boundaryFromCenter(@NotNull HashSet<WorldXYZ> structure, Vector3 directionOfScanning){
        int margin = 2;
        //I considered "simplifying" this to the underlying logic, but since there's three degrees of freedom
        //and only 6 outcomes, walking through the logic is just as long and more confusing to read.

        if(directionOfScanning == Vector3.DOWN) { //DOWN =  (0,-1,0);
            WorldXYZ max = Collections.max(structure, (pt1,pt2) -> pt2.getY() - pt1.getY());
            return max.getY() - location.getY() + margin; // margin increases the distance regardless of direction
        }
        if(directionOfScanning == Vector3.SOUTH) { //SOUTH = (0,0, 1);
            WorldXYZ min = Collections.min(structure, (pt1,pt2) -> pt2.getZ() - pt1.getZ());
            return location.getZ() - min.getZ() + margin;
        }
        if(directionOfScanning == Vector3.NORTH) { //NORTH = (0,0,-1);
            WorldXYZ max = Collections.max(structure, (pt1,pt2) -> pt2.getZ() - pt1.getZ());
            return max.getZ() - location.getZ() + margin;
        }
        if(directionOfScanning == Vector3.EAST) { //EAST =  ( 1,0,0);
            WorldXYZ min = Collections.min(structure, (pt1,pt2) -> pt2.getX() - pt1.getX());
            return location.getX() - min.getX() + margin;
        }
        if(directionOfScanning == Vector3.WEST) { //WEST =  (-1,0,0);
            WorldXYZ max = Collections.max(structure, (pt1,pt2) -> pt2.getX() - pt1.getX());
            return max.getX() - location.getX() + margin;
        }
        //UP =    (0, 1,0); and default in case we get a diagonal or NONE vector for some reason
        WorldXYZ min = Collections.min(structure, (pt1,pt2) -> pt2.getY() - pt1.getY());
        return location.getY() - min.getY() + margin;

    }
}
