package com.newlinegaming.Runix.fluids;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;

public class ModFluid {

    public static Fluid QixSilver;

    public static Block BlockQixSilver;

    public static void init() {

        //Fluids
        QixSilver = new FluidQixSilver();

        //Fluid Blocks
        BlockQixSilver = new BlockQixSilver(QixSilver, Material.lava);

        GameReg();
    }

    private static void GameReg() {

                GameRegistry.registerBlock(BlockQixSilver, "runixqixsilver");
    }
}
