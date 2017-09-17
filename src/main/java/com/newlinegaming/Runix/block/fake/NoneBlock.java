package com.newlinegaming.Runix.block.fake;

import com.newlinegaming.Runix.block.RunixBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;


public class NoneBlock extends RunixBlock
{

    public NoneBlock()
    {
        super("NONE",Material.AIR, false);
    }
    
}