package com.newlinegaming.Runix.block;

import com.newlinegaming.Runix.Runix;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

public class ModBlock {

    public static Block GreekFire;
    public static Block TestBlock;

    public static void init() {

//        GreekFire = new GreekFire(2014).setUnlocalizedName("Greekfire");
    	TestBlock = new TestBlock();
    	
        Gamereg();

    }

    private static void Gamereg() {

//        GameRegistry.registerBlock(GreekFire, "GreekFire");
    	GameRegistry.registerBlock(TestBlock, "testblock");
    }
}
