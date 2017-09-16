package com.newlinegaming.Runix.block;


import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModBlock {

//    public static GreekFire greekFire;
//    public static HoarFrost hoar_frost;
    public static Block lightBeam;
    
    //Fake/replacement Blocks
    public static Block fakeGoldBlock;
    public static Block runixAir;
    
    public static void init() {

//        greekFire = GreekFire.getInstance();
//        hoar_frost = new HoarFrost();
//
//        lightBeam = new BlockLightBeam();
        fakeGoldBlock = new FakeBlock(Blocks.GOLD_BLOCK);
        runixAir = new RunixAirBlock();

        Gamereg();
    }

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(runixAir, fakeGoldBlock);
    }

    private static void Gamereg() {
        //TODO: move the rest to registerBlocks method above
//        GameRegistry.registerBlock(greekFire, "GreekFire");
//        GameRegistry.registerBlock(hoar_frost, HoarFrostItem.class, "HoarFrost");
//        GameRegistry.registerBlock(lightBeam, "RunixLightBeam");
    }
}
