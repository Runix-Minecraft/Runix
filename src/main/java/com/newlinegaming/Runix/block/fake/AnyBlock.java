package com.newlinegaming.Runix.block.fake;

import com.newlinegaming.Runix.block.RunixBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class AnyBlock extends RunixBlock
{
    public AnyBlock(String name, Material mat, boolean useCreativeTab) {
        super("ANY", Material.AIR, false);
    }

//    public AnyBlock()
//    {
//        super(Material.AIR);
//        setBlockName("ANY");
//    }

}
