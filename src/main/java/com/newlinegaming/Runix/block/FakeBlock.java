package com.newlinegaming.Runix.block;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import static com.newlinegaming.Runix.RunixMain.MODID;

//Just give A custom texture
@SuppressWarnings("WeakerAccess")
public class FakeBlock extends Block {

    private final HashMap<Block, String> textures = new HashMap<>();

    public FakeBlock(Block mimic) {
        super(mimic.getMaterial(mimic.getDefaultState()));
        textures.put(Blocks.GOLD_BLOCK, "minecraft:gold_block");
        setBlockUnbreakable();
        setUnlocalizedName(MODID + ":faith_anchor");
        setRegistryName("faith_anchor");

        setBlockUnbreakable();  // we don't want Faith anchors to be unbreakable
    }

}
