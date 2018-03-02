package com.newlinegaming.Runix.block;


import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlock {

    public static GreekFire greekFire;
    public static HoarFrost hoar_frost;
    public static Block lightBeam;
    
    //Fake/replacement Blocks
    public static Block fakeGoldBlock;
    public static Block runixAir;
    
    public static void init() {

        greekFire = GreekFire.getInstance();
        hoar_frost = new HoarFrost();

        fakeGoldBlock = new FakeBlock(Blocks.gold_block);
        runixAir = new RunixAirBlock();

        Gamereg();
    }

    private static void Gamereg() {
        GameRegistry.registerBlock(greekFire, "GreekFire");
        GameRegistry.registerBlock(hoar_frost, HoarFrostItem.class, "HoarFrost");
        GameRegistry.registerBlock(runixAir, "Fake Air");
        GameRegistry.registerBlock(lightBeam, "RunixLightBeam");
        GameRegistry.registerBlock(fakeGoldBlock, "RunixFakeGoldBlock");
        
        
    }
}
