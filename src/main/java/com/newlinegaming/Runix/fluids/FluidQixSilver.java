package com.newlinegaming.Runix.fluids;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class FluidQixSilver extends Fluid {
    public FluidQixSilver() {
        super("quicksilver");
        this.setViscosity(2000);
        this.setTemperature(310);
        this.setDensity(2000);
        this.setLuminosity(10);
        FluidRegistry.registerFluid(this);
    }


}
