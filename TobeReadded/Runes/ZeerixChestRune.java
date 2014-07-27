package com.newlinegaming.Runix.rune;

import java.util.ArrayList;
import java.util.HashSet;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.newlinegaming.Runix.AbstractTimedRune;
import com.newlinegaming.Runix.NotEnoughRunicEnergyException;
import com.newlinegaming.Runix.PersistentRune;
import com.newlinegaming.Runix.utils.Util_SphericalFunctions;
import com.newlinegaming.Runix.Vector3;
import com.newlinegaming.Runix.WorldXYZ;

public class ZeerixChestRune extends AbstractTimedRune {
    protected static ArrayList<PersistentRune> activeMagic = new ArrayList<PersistentRune>();
    
    public ZeerixChestRune() {
        runeName = "Zeerix Chest";
    }

    public ZeerixChestRune(WorldXYZ coords, EntityPlayer player2) {
        super(coords, player2, "Zeerix Chest");
        updateEveryXTicks(200);
    }

    @Override
    protected void onUpdateTick(EntityPlayer subject) {
        if(subject.equals(getPlayer()))
        {
            World world = subject.worldObj;//sphere can be optimized to donut
            HashSet<WorldXYZ> sphere = Util_SphericalFunctions.getShell(new WorldXYZ(getPlayer()), 4);
            for(WorldXYZ newPos : sphere)
            {
                if(newPos.getBlockId() == 0 
                        && newPos.offset(Vector3.DOWN).isSolid()// base is solid 
                        && !newPos.offset(Vector3.UP).isSolid()){//room to open lid
                    try{
                        if(location.getBlockId() != Block.enderChest.blockID)
                            setBlockIdAndUpdate(location, Block.enderChest.blockID);//charge for a replacement
                        moveBlock(location, newPos);
                    }catch( NotEnoughRunicEnergyException e){
                        reportOutOfGas(getPlayer());
                    }
                    
                    return; //we only need place the chest in one good position
                }
            }
        }
    }
  
    @Override
    public Block[][][] runicTemplateOriginal() {
        int GOLD = Block.oreGold.blockID;
        int CHST = Block.enderChest.blockID;
        return new Block[][][] //NOTE: This is vertical notice the double }}
                {{{NONE,TIER,NONE}},
                  {{GOLD,TIER,GOLD}},
                  {{TIER,CHST,TIER}}}; 
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
