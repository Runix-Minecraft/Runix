package com.newlinegaming.Runix.block;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

//Just give A custom texture
@SuppressWarnings("WeakerAccess")
public class FakeBlock extends Block {

    private final HashMap<Block, String> textures = new HashMap<>();

    public FakeBlock(Block mimic) {
        super(mimic.getMaterial(mimic.getDefaultState()));
        textures.put(Blocks.GOLD_BLOCK, "minecraft:gold_block");
        setBlockUnbreakable();
        setUnlocalizedName("runix:faith_anchor");
        //TODO: http://mcforge.readthedocs.io/en/latest/blocks/blocks/#coloring-a-block
        //It doesn't look like Block is the place where the renderer gets set.

        setBlockUnbreakable();  // we don't want Faith anchors to be unbreakable
    }

}
