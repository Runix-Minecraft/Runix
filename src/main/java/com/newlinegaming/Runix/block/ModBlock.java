package com.newlinegaming.Runix.block;


import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

public class ModBlock {

    public static Block greekFire;
    
    //Fake/replacement Blocks
    public static Block fakeAirBeam;
    public static Block fakeGoldBlock;
    
    public static void init() {

        greekFire = GreekFire.getInstance();
    	
    	fakeAirBeam = new BlockFakeCarry();
    	fakeGoldBlock = new BlockFakeGold();

        Gamereg();

    }

    private static void Gamereg() {

        GameRegistry.registerBlock(greekFire, "GreekFire");
        GameRegistry.registerBlock(fakeAirBeam, "RunixFakeCarry");
        GameRegistry.registerBlock(fakeGoldBlock, "RunixFakeGoldBlock");
    }
}
