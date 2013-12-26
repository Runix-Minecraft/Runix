package com.newlinegaming.Runix;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;

public class PhantomTorchRune extends AbstractTimedRune {
    public static ArrayList<PhantomTorchRune> activeMagic = new ArrayList<PhantomTorchRune>();
    public EntityPlayer player = null;
    private WorldXYZ previousLocation;
    
    public PhantomTorchRune() {}
    public PhantomTorchRune(EntityPlayer activator, WorldXYZ location) {
        player = activator;
        previousLocation = location;
        updateEveryXTicks(100);
    }

    @Override
    protected void onUpdateTick(EntityPlayer subject) {
        if(subject.equals(player)){
            if(previousLocation.getBlockId() == Block.torchWood.blockID)
                previousLocation.setBlockId(0);//delete old torch
            WorldXYZ newPos = new WorldXYZ(player.worldObj, (int)player.posX, (int)player.posY-1, (int)player.posZ);
            if(newPos.getBlockId() == 0){
                newPos.setBlockId(Block.torchWood.blockID);
            }
            previousLocation = newPos;
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
        if(player.worldObj.isRemote)
            activeMagic.add(new PhantomTorchRune(player, coords));
    }

    @Override
    public String getRuneName() {
        return "Phantom Torch";
    }

}
