package com.newlinegaming.Runix.rune;

import java.util.ArrayList;
import java.util.HashSet;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;

import com.newlinegaming.Runix.AbstractTimedRune;
import com.newlinegaming.Runix.NotEnoughRunicEnergyException;
import com.newlinegaming.Runix.PersistentRune;
import com.newlinegaming.Runix.utils.UtilSphericalFunctions;
import com.newlinegaming.Runix.Vector3;
import com.newlinegaming.Runix.WorldXYZ;

public class ZeerixChestRune extends AbstractTimedRune {
    private static final ArrayList<PersistentRune> activeMagic = new ArrayList<>();
    
    public ZeerixChestRune() {
        runeName = "Zeerix Chest";
        updateEveryXTicks(200);
    }

    public ZeerixChestRune(WorldXYZ coords, EntityPlayer player2) {
        super(coords, player2, "Zeerix Chest");
        updateEveryXTicks(200);
    }

    @Override
    protected void onUpdateTick(EntityPlayer subject) {
        if(subject.equals(getPlayer()))
        {
            double distance = (new WorldXYZ(getPlayer())).getDistance(location);//distance from player to current chest
            if( distance > 6.0){
                HashSet<WorldXYZ> sphere = UtilSphericalFunctions.getShell(new WorldXYZ(getPlayer()), 4);
                for(WorldXYZ newPos : sphere)
                {
                    if(newPos.getBlock() == Blocks.AIR
                            && newPos.offset(Vector3.DOWN).isSolid()// base is solid 
                            && !newPos.offset(Vector3.UP).isSolid()){//room to open lid
                        try{
                            if(location.getBlock() != Blocks.ENDER_CHEST)
                                setBlockIdAndUpdate(location, Blocks.ENDER_CHEST);//charge for a replacement
                            moveBlock(location, newPos);
                        }catch( NotEnoughRunicEnergyException e){
                            reportOutOfGas(getPlayer());
                        }
                        return; //we only need place the chest in one good position
                    }
                }
            } //else do nothing
        }
    }
  
    @Override
    public Block[][][] runicTemplateOriginal() {
        Block GOLD = Blocks.GOLD_ORE;
        Block CHEST = Blocks.ENDER_CHEST;
        Block WOOD = Blocks.PLANKS;
        return new Block[][][] {{
            {GOLD, NONE, GOLD},
            {NONE, CHEST, NONE},
            {GOLD, NONE, GOLD}},
            {{WOOD,TIER, WOOD},
            {TIER,TIER, TIER},
            {WOOD,TIER, WOOD}
            
        }};
    }

    @Override
    public String getRuneName() {
        return "Zeerix Chest";
    }

    @Override
    public ArrayList<PersistentRune> getActiveMagic() {
        return activeMagic;
    }

    @Override
    public boolean oneRunePerPerson() {
        return true;
    }
    
    public boolean isFlatRuneOnly() {
        return true;
    }
}
