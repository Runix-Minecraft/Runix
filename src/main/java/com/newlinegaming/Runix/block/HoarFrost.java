package com.newlinegaming.Runix.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockIce;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.newlinegaming.Runix.RunixMain;
import com.newlinegaming.Runix.SigBlock;
import com.newlinegaming.Runix.WorldXYZ;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class HoarFrost extends BlockIce {
    
    public HoarFrost() {
        super();
        setTickRandomly(true);
        setCreativeTab(RunixMain.TabRunix);
        setHardness(0.5F);
        setStepSound(soundTypeGlass);
        setBlockName("runix:hoarfrost");
        setBlockTextureName("ice_packed");
    }
    
    @Override
    public int damageDropped (int metadata) {
        return metadata;
    }
    
    @SuppressWarnings("rawtypes") 
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(Item par1, CreativeTabs tab, List subItems) {
        int[] growthModes = {0, 1, 2, 14, 15};
        for (int ix = 0; ix < growthModes.length; ix++) {
            subItems.add(new ItemStack(this, 1, growthModes[ix]));
        }
    }
    
    @Override
    public int tickRate(World par1World) {
        return 30 + par1World.rand.nextInt(20);
    }
    
    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
//        new WorldXYZ(world, x, y, z).setBlock(ModBlock.frostOrigin, 15);//this line is to convert all existing frost blocks
        int growthMode = world.getBlockMetadata(x, y, z);

        if(growthMode == 0) {//surface crawl
            ArrayList<WorldXYZ> neighbors = new WorldXYZ(world, x, y, z).getNeighbors();
            for(WorldXYZ n : neighbors){
                if(n.getBlock().equals(Blocks.air)) {
                    ArrayList<WorldXYZ> indirectNeighbors = n.getDirectNeighbors();
                    for(WorldXYZ base : indirectNeighbors) {
                        Block block = base.getBlock();
                        if( !block.equals(ModBlock.hoar_frost) && !block.equals(Blocks.air) ) { //found a child
                            n.setBlock(ModBlock.hoar_frost, 0);
                            world.scheduleBlockUpdate(n.posX, n.posY, n.posZ, this, this.tickRate(world)); //schedule for child
                            return; //no more updates for the parent block
                        }
                    }
                }
            }
        }
        
        if(growthMode == 2) { //Expanding shell
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
                    target.setBlock(ModBlock.hoar_frost, 2);
                world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
            }
        }
        
        if(growthMode == 14) { //infectious delete mode
            WorldXYZ me = new WorldXYZ(world, x, y, z);
            ArrayList<WorldXYZ> neighbors = me.getNeighbors();
            for(WorldXYZ n : neighbors){
                if(n.getBlock().equals(ModBlock.hoar_frost)){
                    n.setBlock(ModBlock.hoar_frost, 14); //spread the deletion
                    world.scheduleBlockUpdate(n.posX, n.posY, n.posZ, this, 3); //update neighbor quickly
                }
            }
            me.setBlock(Blocks.air, 0); //delete self
        }
        
        if(growthMode == 15) { //infectious shutdown stasis mode
            ArrayList<WorldXYZ> neighbors = new WorldXYZ(world, x, y, z).getNeighbors();
            int conversions = 0;
            for(WorldXYZ n : neighbors){
                SigBlock data = n.getSigBlock();
                if(data.blockID.equals(ModBlock.hoar_frost)){
                    ++conversions;
                    if(data.meta != 15) {
                        n.setBlock(ModBlock.hoar_frost, 15);
                        world.scheduleBlockUpdate(n.posX, n.posY, n.posZ, this, 5); //schedule for neighbor
                    }
                }
            }
            if(conversions == 0)
                new WorldXYZ(world, x, y, z).setBlock(Blocks.air, 0);
        }
    }
    
    @Override
    public void onBlockAdded(World par1World, int x, int y, int z)
    {
        par1World.scheduleBlockUpdate(x, y, z, this, this.tickRate(par1World));
    }
}
