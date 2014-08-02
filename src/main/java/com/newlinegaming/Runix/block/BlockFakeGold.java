package com.newlinegaming.Runix.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockFakeGold extends Block {

    public BlockFakeGold() {
        super(Material.iron);
        setBlockUnbreakable();
        setBlockTextureName("minecraft:gold_block");
        setBlockName("runix:faithanchore");
    }

}
