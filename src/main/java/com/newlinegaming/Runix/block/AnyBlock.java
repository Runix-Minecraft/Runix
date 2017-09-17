package com.newlinegaming.Runix.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

@SuppressWarnings("WeakerAccess")
public class AnyBlock extends Block
{

    public AnyBlock()
    {
        super(Material.AIR);
        setBlockName("ANY");
    }

}
