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
    public static ArrayList<PhantomTorchRune> activeMagic = new ArrayList<PhantomTorchRune>();
    public EntityPlayer player = null;
    protected int tier = 1;
    
    public PhantomTorchRune() {}
    public PhantomTorchRune(EntityPlayer activator, WorldXYZ location) {
        player = activator;
        tier = getTier(location);
        updateEveryXTicks(10);
    }

    @Override
    protected void onUpdateTick(EntityPlayer subject) {
        if(subject.equals(player))
        {
            World world = subject.worldObj;
            LinkedList<WorldXYZ> sphere = Util_SphericalFunctions.getShell(new WorldXYZ(player), 7);
            for(WorldXYZ newPos : sphere)
            {
                Material base = world.getBlockMaterial( ((int)newPos.posX), ((int)newPos.posY-1), ((int)newPos.posZ) );
                if(newPos.getBlockId() == 0 && base.isSolid() && 
                        world.getBlockLightValue(newPos.posX, newPos.posY, newPos.posZ) < 7){ //adjustable
                    newPos.setBlockId(Block.torchWood.blockID);//set torch
                    return; //Light levels don't update til the end of the tick, so we need to exit
                }
            }
        }
    }

    @Override
    public int[][][] blockPattern() {
        int REDW = Block.redstoneWire.blockID;
        int TRCH = Block.torchWood.blockID;
        return new int [][][] 
                {{{REDW,TRCH,REDW},
                  {TRCH,TIER,TRCH},
                  {REDW,TRCH,REDW}}}; 
    }

    @Override
    public void execute(EntityPlayer player, WorldXYZ coords) {
        if(!player.worldObj.isRemote)
            activeMagic.add(new PhantomTorchRune(player, coords));
    }

    @Override
    public String getRuneName() {
        return "Phantom Torch";
    }

}
