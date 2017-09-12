package com.newlinegaming.Runix.rune;

import java.util.ArrayList;
import java.util.concurrent.DelayQueue;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;

import com.newlinegaming.Runix.AbstractTimedRune;
import com.newlinegaming.Runix.BlockRecord;
import com.newlinegaming.Runix.PersistentRune;
import com.newlinegaming.Runix.Vector3;
import com.newlinegaming.Runix.WorldXYZ;

public class DomainRune extends AbstractTimedRune {

    private static final ArrayList<PersistentRune> activeDomains= new ArrayList<>();
    private final DelayQueue<BlockRecord> phasedBlocks = new DelayQueue<>();

    public DomainRune() {
        runeName = ("Domain");
    }

    public DomainRune( WorldXYZ coords, EntityPlayer activator ) {
        super(coords, activator, "Domain Rune");
        usesConductance = true;
        updateEveryXTicks(20); //TODO this line and the next are crashing the Event Bus on loadRunes().
        MinecraftForge.EVENT_BUS.register(this);
    }

    public ArrayList<PersistentRune> getActiveMagic() {
        return activeDomains;
    }

    private void phaseBlockAt(WorldXYZ coords) {
        BlockRecord record = new BlockRecord(10, new Vector3(location, coords), coords.getSigBlock());
        phasedBlocks.add(record);   
    }
    
    @Override
    public boolean oneRunePerPerson() {
    return false;
    }

    @Override
    public Block[][][] runicTemplateOriginal() {
        Block air = Blocks.air;
        Block stair = Blocks.oak_stairs;
        return new Block[][][]
          {{{air , stair, air },
            {stair,Blocks.glass ,stair},
            {air , stair, air }},
           {{air , stair, air },
            {stair,TIER,stair},
            {air ,stair, air }}};
    }

    @Override
    public boolean isFlatRuneOnly() {
        return true;
    }

    @Override
    protected void onUpdateTick(EntityPlayer subject) {
        unphaseExpiredBlocks();
    }

    private void unphaseExpiredBlocks() {
        for( BlockRecord expired = phasedBlocks.poll(); expired != null; expired = phasedBlocks.poll()){
            //TODO drop block if non-air block
            System.out.println(expired.offset.toString() + "  ==  " + expired.block.blockID);
            location.offset(expired.offset).setBlockIdAndUpdate(expired.block.blockID);
        }
    }

}
