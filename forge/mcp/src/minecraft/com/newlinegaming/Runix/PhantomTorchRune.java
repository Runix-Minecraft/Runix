package com.newlinegaming.Runix;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;

public class PhantomTorchRune extends AbstractTimedRune {
    public static ArrayList<PhantomTorchRune> activeMagic = new ArrayList<PhantomTorchRune>();
    public EntityPlayer player = null;
    private ArrayList<WorldXYZ> previousLocations;
    protected int tier = 1;
    
    public PhantomTorchRune() {}
    public PhantomTorchRune(EntityPlayer activator, WorldXYZ location) {
        player = activator;
        tier = getTier(location);
        previousLocations = new ArrayList<WorldXYZ>();
        for(int i = 0; i < tier*tier; ++i)
            previousLocations.add(new WorldXYZ(player)); //this needs to be pre-populated to set the number of torches
        updateEveryXTicks(20);
    }

    @Override
    protected void onUpdateTick(EntityPlayer subject) {
        if(subject.equals(player))
        {
            ArrayList<WorldXYZ> newLocations = new ArrayList<WorldXYZ>();
            LinkedList<WorldXYZ> sphere = Util_SphericalFunctions.getShell(new WorldXYZ(player), tier+1);
            int size = sphere.size();
            for(WorldXYZ previousLocation : previousLocations)
            {
                if(previousLocation.getBlockId() == Block.torchWood.blockID)
                    previousLocation.setBlockId(0);//delete old torch
                
                WorldXYZ newPos = sphere.get(new Random().nextInt(size));
                if(newPos.getBlockId() == 0) //set torch
                    newPos.setBlockId(Block.torchWood.blockID);
                newLocations.add(newPos);
            }
            previousLocations.clear();
            previousLocations = newLocations;
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
