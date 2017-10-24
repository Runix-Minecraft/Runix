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
import org.jetbrains.annotations.NotNull;

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

    @NotNull
    public ArrayList<PersistentRune> getActiveMagic() {
        return activeDomains;
    }

    private void phaseBlockAt(@NotNull WorldXYZ coords) {
        BlockRecord record = new BlockRecord(10, new Vector3(location, coords), coords.getSigBlock());
        phasedBlocks.add(record);   
    }
    
    @Override
    public boolean oneRunePerPerson() {
    return false;
    }

    @NotNull
    @Override
    public Block[][][] runicTemplateOriginal() {
        Block air = Blocks.AIR;
        Block stair = Blocks.OAK_STAIRS;
        return new Block[][][]
          {{{air , stair, air },
            {stair,Blocks.GLASS ,stair},
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
            System.out.println(expired.offset.toString() + "  ==  " + expired.block.getBlock().getLocalizedName());
            location.offset(expired.offset).setBlockIdAndUpdate(expired.block.getState());
        }
    }

}
