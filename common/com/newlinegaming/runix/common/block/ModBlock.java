package com.newlinegaming.runix.common.block;

import com.newlinegaming.runix.common.block.decorative.VoidPlank;
import com.newlinegaming.runix.common.block.trees.VoidLog;

import net.minecraft.block.Block;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlock {

    public static Block GreekFire;
    public static Block TestBlock;
    public static Block VoidLog;
    public static Block VoidPlank;

    public static void init() {

//        GreekFire = new GreekFire(2014).setUnlocalizedName("Greekfire");
//    	TestBlock = new TestBlock();
    	
    	VoidLog = new VoidLog();
    	VoidPlank = new VoidPlank();
    	
        Gamereg();

    }

    private static void Gamereg() {

//        GameRegistry.registerBlock(GreekFire, "GreekFire");
//    	GameRegistry.registerBlock(TestBlock, "testblock");
    	GameRegistry.registerBlock(VoidLog, "voidlog");
    }
}
