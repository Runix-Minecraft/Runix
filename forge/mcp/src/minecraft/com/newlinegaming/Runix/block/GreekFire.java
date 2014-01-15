package com.newlinegaming.Runix.block;

import com.newlinegaming.Runix.Runix;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.material.Material;

public class GreekFire extends BlockFire {

    public GreekFire(int par1) {
	super(par1);
	
        this.blockHardness = 100.0F;
        this.setLightOpacity(15);
        this.setCreativeTab(Runix.TabRunix);
        this.setLightValue(15.0F);
        this.setBurnProperties(Block.stone.blockID, 30, 60);
        this.setBurnProperties(Block.grass.blockID, 30, 60);
        this.setBurnProperties(Block.dirt.blockID, 30, 60);
        this.setBurnProperties(Block.gravel.blockID, 30, 60);
    }
    

}
