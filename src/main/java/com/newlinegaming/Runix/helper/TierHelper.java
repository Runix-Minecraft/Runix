package com.newlinegaming.Runix.helper;

import com.newlinegaming.Runix.apiimpl.API;
import net.minecraft.block.Block;

public class TierHelper {

    /**
     *
     * @param block
     * @return
     */
    public static int getEnergy(Block block) {
       if (API.INSTANCE().getTier(block).getBlock() != null) {
           return API.INSTANCE().getTier(block).getEnergy();
       } else {
           return 6;
       }
    }

    /**
     *
     * @param block
     * @return
     */
    public static int getTier(Block block) {
        int energy = getEnergy(block);
        energy = energy < 1 ? 1 : energy;
        return (int) Math.round(Math.log(energy) / Math.log(2));
    }

    /**
     *
     * @param energy
     * @param cost
     * @return
     */
    public static int energyToRadiusConversion(int energy, float cost) {
        int diameter = 1;

        while (diameter * diameter * diameter * cost < cost) {
           diameter += 2;
        }

        return diameter/2;
    }

    public static boolean isNatural(Block block) {
        return API.INSTANCE().getTier(block).isNatural();
    }


    public static boolean isMoveSensitive(Block block) {
        return API.INSTANCE().getTier(block).isSensitive();
    }


    public static boolean isCrushable(Block block) {
        return API.INSTANCE().getTier(block).isCrushable();
    }

}
