package com.newlinegaming.Runix.fluids;

import net.minecraft.block.Block;
import net.minecraftforge.fluids.Fluid;

public class ModFluid {

    private static Fluid QixSilver;

    private static Block BlockQixSilver;

    public static void init() {

        //Fluids
//        QixSilver = new FluidQixSilver();

        //Fluid Blocks
//        BlockQixSilver = new BlockQixSilver(QixSilver, Material.lava);

        GameReg();
    }

    private static void GameReg() {

//                GameRegistry.registerBlock(BlockQixSilver, "runixqixsilver");
    }
}
