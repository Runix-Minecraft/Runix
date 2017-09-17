package com.newlinegaming.Runix.rune;

import com.newlinegaming.Runix.PersistentRune;
import com.newlinegaming.Runix.WorldXYZ;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;

import java.util.ArrayList;
import java.util.UUID;

public class FerrousWheelRune extends PersistentRune {

    private static final ArrayList<PersistentRune> globalWheel  = new ArrayList<>();
    private final ArrayList<UUID> guestList = new ArrayList<>();
    
    public FerrousWheelRune() {
        runeName = "Ferrous Wheel";
    }

    public FerrousWheelRune(WorldXYZ coords, EntityPlayer activator) {
        super(coords, activator, "Ferrous Wheel");
    }


    @Override
    public void execute(WorldXYZ coords, EntityPlayer activator) {
        if(!activator.getEntityWorld().isRemote){//server only
            super.execute(coords, activator);
        }
    }

    
    @Override
    protected void poke(EntityPlayer player, WorldXYZ coords) {
        if(player.getEntityWorld().isRemote)
            return;
        consumeFuelBlock(coords);
        if( !guestList.contains(player.getUniqueID()) )
            guestList.add(player.getUniqueID());
        FerrousWheelRune next = getNextWheel(player);
        if(next == null || next == this){
            aetherSay(player, "Create more of these runes to teleport between them.");
            return;
        }
        teleportPlayer(player, next.location);
    }

    private FerrousWheelRune getNextWheel(EntityPlayer player){
        if(globalWheel.size() < 2)
            return null;
        int start = globalWheel.indexOf(this);
        for(int i = (start + 1) % globalWheel.size(); i != start; i = (i+1) % globalWheel.size()){
            FerrousWheelRune fw = (FerrousWheelRune)globalWheel.get(i);
            if( !fw.location.equals(location) 
                    && fw.guestList.contains(player.getUniqueID())
                    && fw.runeIsIntact()) 
                return fw;
        }
        return null;
    }

    public String toString(){
        return hashCode() + ": @" + location.toString() + " guests: " + guestList;
    }
    
    @Override
    public ArrayList<PersistentRune> getActiveMagic() {
        return globalWheel;
    }

    @Override
    public boolean oneRunePerPerson() {
        return false;
    }
    
    public boolean isFlatRuneOnly() {
        return false;
    }
    
    @Override
    public Block[][][] runicTemplateOriginal() {
        Block IRON = Blocks.IRON_ORE;
        return new Block[][][]{{
            {TIER, IRON, TIER},
            {IRON, FUEL, IRON},
            {TIER, IRON, TIER}

        }};
    }

    @Override
    public String getRuneName() {
        return "Ferrous Wheel";
    }

}
