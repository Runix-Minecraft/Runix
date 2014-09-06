package com.newlinegaming.Runix.block;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.BlockIce;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import com.newlinegaming.Runix.RunixMain;
import com.newlinegaming.Runix.WorldXYZ;

public class HoarFrostOrigin extends BlockIce {
    
    public HoarFrostOrigin() {
        super();
        setBlockName("Hoar Frost");
        setTickRandomly(true);
        setCreativeTab(RunixMain.TabRunix);
        setHardness(0.5F);
        setStepSound(soundTypeGlass);
        setBlockName("HoarFrost");
        setBlockTextureName("ice_packed");
    }
    
    @Override
    public int tickRate(World par1World) {
        int[] means = {3, 50};
        return 30 + par1World.rand.nextInt(20);
    }
    
    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
//        new WorldXYZ(world, x, y, z).setBlock(ModBlock.runixAir, 0);
        if(true) {
            ArrayList<WorldXYZ> neighbors = new WorldXYZ(world, x, y, z).getDirectNeighbors();
            int nCount = 0;
            for(WorldXYZ n : neighbors){
                if(n.getBlock().equals(Blocks.air))
                    ++nCount;
            }
            if(nCount == 0){
                new WorldXYZ(world, x, y, z).setBlock(ModBlock.runixAir, 0);
            } else {
                WorldXYZ target = neighbors.get(random.nextInt(neighbors.size()));
                if( target.getBlock().equals(Blocks.air))
                    target.setBlockIdAndUpdate(ModBlock.frostOrigin);
                world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
            }
        }
    }
    
    @Override
    public void onBlockAdded(World par1World, int x, int y, int z)
    {
        par1World.scheduleBlockUpdate(x, y, z, this, this.tickRate(par1World));
    }
}
