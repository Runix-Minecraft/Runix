package com.newlinegaming.Runix.block;


import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

public class ModBlock {

    public static Block GreekFire;
    
    //Fake/replacement Blocks
    public static Block fakeAirBeam;
    public static Block fakeGoldBlock;
    
    public static void init() {

//        GreekFire = new GreekFire(2014).setUnlocalizedName("Greekfire");
    	
    	fakeAirBeam = new BlockFakeCarry();
    	fakeGoldBlock = new BlockFakeGold();

        Gamereg();

    }

    private static void Gamereg() {

//        GameRegistry.registerBlock(GreekFire, "GreekFire");
//        GreekFire.setCreativeTab(RunixMain.TabRunix);
        GameRegistry.registerBlock(fakeAirBeam, "RunixFakeCarry");
        GameRegistry.registerBlock(fakeGoldBlock, "RunixFakeGoldBlock");
    }
}