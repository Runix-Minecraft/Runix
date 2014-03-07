package com.newlinegaming.Runix.fluids;

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

    public static Fluid QuickSilver;

    public static Block BlockQuickSilver;
    
    public static Item qsBucket;

    public static void init() {

    	//Fluids
        QuickSilver = new FluidQuickSilver();

        //Fluid Blocks
        BlockQuickSilver = new BlockQuickSilver(QuickSilver, Material.lava);
        
        //Buckets
        qsBucket = new BucketQuickSilver(BlockQuickSilver).setContainerItem(Items.bucket);
        

        GameReg();
    }

    private static void GameReg() {

    	//Fluid blocks
        GameRegistry.registerBlock(BlockQuickSilver, "runixqiicksilver");
        
        //Buckets
        GameRegistry.registerItem(qsBucket, "qsbucket");
        
        //Fluid Container Registry
        FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack("quicksilver", FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(qsBucket), new ItemStack(Items.bucket));
        
        
    }
}
