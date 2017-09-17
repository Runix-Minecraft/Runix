package com.newlinegaming.Runix.fluids;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

class FluidQixSilver extends Fluid {
    public FluidQixSilver() {
        super("quicksilver", //TODO: read http://mcforge.readthedocs.io/en/latest/conventions/locations/
                new ResourceLocation("assets/runix/textures/blocks/qixsilverstill.png"),
                new ResourceLocation("assets/runix/textures/blocks/qixsilverflowing.png"));
        this.setViscosity(2000);
        this.setTemperature(310);
        this.setDensity(2000);
        this.setLuminosity(10);
        FluidRegistry.registerFluid(this);
    }


}
