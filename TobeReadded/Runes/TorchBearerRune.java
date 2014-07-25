package com.newlinegaming.Runix.rune;

import java.util.ArrayList;
import java.util.HashSet;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

import com.newlinegaming.Runix.AbstractTimedRune;
import com.newlinegaming.Runix.NotEnoughRunicEnergyException;
import com.newlinegaming.Runix.PersistentRune;
import com.newlinegaming.Runix.utils.Util_SphericalFunctions;
import com.newlinegaming.Runix.Vector3;
import com.newlinegaming.Runix.WorldXYZ;

/**TorchBearer functionality to place permanent torches appropriately spaced to prevent monster spawn.*/
public class TorchBearerRune extends AbstractTimedRune {
    protected static ArrayList<PersistentRune> activeMagic = new ArrayList<PersistentRune>();
    public TorchBearerRune() {
        runeName = "Torch Bearer";
    }

    public TorchBearerRune( WorldXYZ coords, EntityPlayer activator ) {
        super(coords, activator, "Torch Bearer");
        updateEveryXTicks(10);
    }

    @Override
    protected void onUpdateTick(EntityPlayer subject) {
        if(subject.equals(getPlayer()) && !subject.worldObj.isRemote)
        {
            World world = subject.worldObj;//sphere can be optimized to donut
            location = new WorldXYZ(getPlayer());
            HashSet<WorldXYZ> sphere = Util_SphericalFunctions.getShell(location, 7);
            for(WorldXYZ newPos : sphere)
            {
                if(newPos.getBlockId() == 0 && newPos.offset(Vector3.DOWN).isSolid() && (
                        (world.isDaytime() && world.getBlockLightValue(newPos.posX, newPos.posY, newPos.posZ) < 4) ||//day time checking == caves
                        (!world.isDaytime() && world.getSavedLightValue(EnumSkyBlock.Block, newPos.posX, newPos.posY, newPos.posZ) < 4) )){ //adjustable
                    try {
                        setBlockIdAndUpdate(newPos, Block.torchWood.blockID);//set torch
                    } catch (NotEnoughRunicEnergyException e) {
                        reportOutOfGas(getPlayer());
                    }
                    return; //Light levels don't update til the end of the tick, so we need to exit
                }
            }
        }
    }

    @Override
    public Block[][][] runicTemplateOriginal() {
        int TRCH = Block.torchWood.blockID;
        return new Block[][][] 
                {{{TIER,TRCH,TIER},
                  {TRCH,KEY ,TRCH},
                  {TIER,TRCH,TIER}}}; 
    }

    @Override
    public String getRuneName() {
        return "Torch Bearer";
    }

    @Override
    public ArrayList<PersistentRune> getActiveMagic() {
        return activeMagic;
    }

    @Override
    public boolean oneRunePerPerson() {
        return true;
    }

    @Override
    public boolean isFlatRuneOnly() {
        return true;
    }

}
