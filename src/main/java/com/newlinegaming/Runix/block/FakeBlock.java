package com.newlinegaming.Runix.block;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;



public class FakeBlock extends Block {

    HashMap<Block, String> textures = new HashMap<Block, String>();

    public FakeBlock(Block mimic) {
        super(mimic.getMaterial());
        textures.put(Blocks.gold_block, "minecraft:gold_block");
        setBlockUnbreakable();
        setBlockName("runix:faith_anchor");
        try{
            setBlockTextureName(textures.get(mimic)); //"minecraft:gold_block");
        }
        catch (Throwable t) { }//default to Iron
    }

}
