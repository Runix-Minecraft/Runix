package com.newlinegaming.Runix.block;


import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlock {

    public static GreekFire greekFire;
    public static HoarFrostOrigin frostOrigin;
    public static Block lightBeam;
    
    //Fake/replacement Blocks
    public static Block fakeGoldBlock;
    public static Block runixAir;
    
    public static void init() {

        greekFire = GreekFire.getInstance();
        frostOrigin = new HoarFrostOrigin();
    	
        lightBeam = new BlockLightBeam();
        fakeGoldBlock = new FakeBlock(Blocks.gold_block);
        runixAir = new RunixAirBlock();



        Gamereg();
        

    }

    private static void Gamereg() {

        GameRegistry.registerBlock(greekFire, "GreekFire");
        GameRegistry.registerBlock(frostOrigin, "HoarFrost");
        GameRegistry.registerBlock(runixAir, "Fake Air");
        GameRegistry.registerBlock(lightBeam, "RunixLightBeam");
        GameRegistry.registerBlock(fakeGoldBlock, "RunixFakeGoldBlock");
    }
}
