package com.newlinegaming.Runix.rune;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;

import com.newlinegaming.Runix.NoSuchSignatureException;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;

import com.newlinegaming.Runix.PersistentRune;
import com.newlinegaming.Runix.WorldXYZ;

public class FtpRune extends TeleporterRune {
    
    private static ArrayList<PersistentRune> energizedFTP = new ArrayList<PersistentRune>(); 
    
    public FtpRune(){
        super();
        this.runeName = "Faith Transfer Portal";
    }
    
    public FtpRune(WorldXYZ coords, EntityPlayer activator) {
        super(coords, activator);
        this.runeName = "Faith Transfer Portal";
        usesConductance = true;
    }

    public Block[][][] runicTemplateOriginal(){
        Block GOLD = Blocks.gold_block;
        return new Block[][][] {{
                {NONE,TIER,SIGR,TIER,NONE},
                {TIER,TIER,GOLD,TIER,TIER},
                {SIGR,GOLD,FUEL,GOLD,SIGR},
                {TIER,TIER,GOLD,TIER,TIER},
                {NONE,TIER,SIGR,TIER,NONE}
        }};
    }

    @Override
    protected void poke(EntityPlayer player, WorldXYZ coords) {
        consumeFuelBlock(coords);
        location.face = coords.face; //update the facing
        WorldXYZ destination;
        try {
            destination = findWaypointBySignature(player, getSignature());
        } catch (NoSuchSignatureException e) {
            aetherSay(player, "There's no waypoint with that signature.");
            return;
        }
        LinkedHashSet<WorldXYZ> structure = attachedStructureShape(player);
        if (structure.isEmpty())
            return;

        moveStructureAndPlayer(player, destination, structure);
    }

    @Override
    public ArrayList<PersistentRune> getActiveMagic() {
        return energizedFTP;
    }

    @Override
    public int getTier(){
        return super.getTier() * 5;
    }

    @Override
    public int boundaryFromCenter(HashSet<WorldXYZ> structure){
        //TODO make this any direction, not just up (lower boundary)
        int min = 256;
        for(WorldXYZ pt : structure){//silly java lack of Lambda comparator...
            if(pt.posY < min){
                min = pt.posY;
            }
        }
        return location.posY - min + 2;
    }
}
