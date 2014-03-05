package com.newlinegaming.Runix.fluids;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraftforge.fluids.Fluid;

public class ModFluid {

    public static Fluid QuickSilver;

    public static Block BlockQuickSilver;

    public static void init() {

        QuickSilver = new FluidQuickSilver();

        BlockQuickSilver = new BlockQuickSilver(2012);

        GameReg();
    }

    private static void GameReg() {

        GameRegistry.registerBlock(BlockQuickSilver, "runixqiicksilver");
    }
}
