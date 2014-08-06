package com.newlinegaming.Runix.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class FakeBlock extends Block {

    public FakeBlock(Block mimic) {
        super(Material.iron);
        setBlockUnbreakable();
        setBlockName("runix:faith_anchor");
        try{
            setBlockTextureName(mimic.getUnlocalizedName()); //"minecraft:gold_block");
        }
        catch (Throwable t) { }//default to Iron
    }

}
