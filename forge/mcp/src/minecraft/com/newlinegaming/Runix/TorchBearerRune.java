package com.newlinegaming.Runix;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

/**TorchBearer functionality to place permanent torches appropriately spaced to prevent monster spawn.*/
public class TorchBearerRune extends AbstractTimedRune {
    protected static ArrayList<PersistentRune> activeMagic = new ArrayList<PersistentRune>();
    public TorchBearerRune() {}

    public TorchBearerRune( WorldXYZ coords, EntityPlayer activator ) {
        super(coords, activator);
        consumeRune(coords);
        updateEveryXTicks(10);
    }

    @Override
    protected void onUpdateTick(EntityPlayer subject) {
        if(subject.equals(player) && !subject.worldObj.isRemote)
        {
            World world = subject.worldObj;//sphere can be optimized to donut
            location = new WorldXYZ(player);
            LinkedList<WorldXYZ> sphere = Util_SphericalFunctions.getShell(location, 7);
            for(WorldXYZ newPos : sphere)
            {
                if(newPos.getBlockId() == 0 && newPos.offset(Vector3.DOWN).isSolid() && 
                        world.getSavedLightValue(EnumSkyBlock.Block, newPos.posX, newPos.posY, newPos.posZ) < 4 //adjustable
                        && !world.isDaytime()){// don't place during the day time
                    try {
                        setBlockId(newPos, Block.torchWood.blockID);//set torch
                    } catch (NotEnoughRunicEnergyException e) {
                        reportOutOfGas(player);
                    }
                    return; //Light levels don't update til the end of the tick, so we need to exit
                }
            }
        }
    }

    @Override
    public int[][][] runicTemplateOriginal() {
        this.flatRuneOnly = true;
        int REDW = Block.redstoneWire.blockID;
        int TRCH = Block.torchWood.blockID;
        return new int [][][]//TODO: if you're having trouble with this, it's because people click on the side of the torch, not the top 
                {{{REDW,TRCH,REDW},
                  {TRCH,TIER,TRCH},
                  {REDW,TRCH,REDW}}}; 
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

}
