package com.newlinegaming.Runix.common.fluids;

import com.newlinegaming.Runix.common.handlers.mics.BucketEventHandler;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;

public class ModFluid {

    public static Fluid QixSilver;

    public static Block BlockQuickSilver;
    
    public static Item qsBucket;

    public static void init() {

    	//Fluids
    	QixSilver = new FluidQixSilver();

        //Fluid Blocks
        BlockQuickSilver = new BlockQixSilver(QixSilver, Material.lava);
        
        //Buckets
        qsBucket = new BucketQixSilver(BlockQuickSilver).setContainerItem(Items.bucket);
        

        GameReg();
    }

    private static void GameReg() {

    	//Fluid blocks
        GameRegistry.registerBlock(BlockQuickSilver, "runixqsixsilver");
        
        //Buckets
        GameRegistry.registerItem(qsBucket, "qsbucket");
        
        //Fluid Container Registry
       BucketEventHandler.INSTANCE.buckets.put(BlockQuickSilver, qsBucket);
        
        
    }
}
