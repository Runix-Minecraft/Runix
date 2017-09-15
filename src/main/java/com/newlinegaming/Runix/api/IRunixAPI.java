package com.newlinegaming.Runix.api;

import com.newlinegaming.Runix.api.tier.ITier;
import net.minecraft.block.Block;

public interface IRunixAPI {

    void registerTier(Block block, ITier tier);

    /**
     * gets the tier object from the registry
     * @param block
     * @return
     */
    ITier getTier(Block block);
}
