package com.newlinegaming.Runix.block;

import com.newlinegaming.Runix.Runix;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;

public class GreekFire extends BlockFire {

    public static int blockIdBackup = 2014;
    
    public GreekFire(int blockId) {
	super(blockId);
	blockIdBackup = blockId; //Josiah: This is a cludge. This is why there are all those static Block.stainedClay examples in vanilla  
	
	setCreativeTab(Runix.TabRunix);
	this.setLightValue(1.0f);
	this.setBurnProperties(Block.stone.blockID, 30, 60);
	this.setBurnProperties(Block.grass.blockID, 30, 60);
	this.setBurnProperties(Block.dirt.blockID, 30, 60);
	this.setBurnProperties(Block.gravel.blockID, 30, 60);
    }
    
    @SideOnly(Side.CLIENT)
    private Icon[] iconArray;
    
    @SideOnly(Side.CLIENT)

    public void registerIcons(IconRegister par1IconRegister)
    {
        this.iconArray = new Icon[] {par1IconRegister.registerIcon("Runix:GreekFire"), par1IconRegister.registerIcon("Runix:GreekFire1")};
    }

    @SideOnly(Side.CLIENT)
    public Icon getFireIcon(int par1)
    {
        return this.iconArray[par1];
    }

    @SideOnly(Side.CLIENT)

    public Icon getIcon(int par1, int par2)
    {
        return this.iconArray[0];
    }

}
