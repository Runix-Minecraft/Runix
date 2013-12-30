package com.newlinegaming.Runix;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**PhantomTorch functionality to place permanent torches appropriately spaced to prevent monster spawn.*/
public class PhantomTorchRune extends AbstractTimedRune {
    protected static ArrayList<PersistentRune> activeMagic = new ArrayList<PersistentRune>();
    public PhantomTorchRune() {}

    public PhantomTorchRune( WorldXYZ coords, EntityPlayer activator ) {
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
                Material base = world.getBlockMaterial( ((int)newPos.posX), ((int)newPos.posY-1), ((int)newPos.posZ) );
                if(newPos.getBlockId() == 0 && base.isSolid() && 
                        world.getBlockLightValue(newPos.posX, newPos.posY, newPos.posZ) < 4){ //adjustable
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
    public int[][][] runicFormulae() {
        int REDW = Block.redstoneWire.blockID;
        int TRCH = Block.torchWood.blockID;
        return new int [][][]//TODO: if you're having trouble with this, it's because people click on the side of the torch, not the top 
                {{{REDW,TRCH,REDW},
                  {TRCH,TIER,TRCH},
                  {REDW,TRCH,REDW}}}; 
    }

    @Override
    public String getRuneName() {
        return "Phantom Torch";
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
