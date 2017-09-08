package com.newlinegaming.Runix.rune;

import java.util.ArrayList;
import java.util.HashSet;

import com.newlinegaming.Runix.NoSuchSignatureException;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;

import com.newlinegaming.Runix.PersistentRune;
import com.newlinegaming.Runix.WorldXYZ;
import com.newlinegaming.Runix.workers.IBlockWorker;

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
            {NONE,NONE,TIER,SIGR,TIER,NONE,NONE},
            {NONE,NONE,TIER,TIER,TIER,NONE,NONE},
            {TIER,TIER,GOLD,TIER,GOLD,TIER,TIER},
            {SIGR,TIER,TIER,FUEL ,TIER,TIER,SIGR},
            {TIER,TIER,GOLD,TIER,GOLD,TIER,TIER},
            {NONE,NONE,TIER,TIER,TIER,NONE,NONE},
            {NONE,NONE,TIER,SIGR,TIER,NONE,NONE}    
        }};
    }

    @Override
    protected void poke(EntityPlayer player, WorldXYZ coords) {
        consumeFuelBlock(coords);
        location.face = coords.face; //update the facing
        try {
            WorldXYZ destination = findWaypointBySignature(player, getSignature());
            HashSet<WorldXYZ> structure = attachedStructureShape(player);
            if (structure.isEmpty())
                return;

            moveStructureAndPlayer(player, destination, structure);
        } catch (NoSuchSignatureException e){
            aetherSay(player, "There's no waypoint with that signature.");
        }
    }

    @Override
    public ArrayList<PersistentRune> getActiveMagic() {
        return energizedFTP;
    }
}
