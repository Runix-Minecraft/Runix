package com.newlinegaming.Runix.block;

import com.newlinegaming.Runix.Runix;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockFlowing;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;

public class QixsilverFlowing extends BlockFlowing{

    public QixsilverFlowing(int par1) {
        super(par1, Material.lava);
        
        this.blockHardness = 100.0F;
        this.setLightOpacity(15);
        this.setCreativeTab(Runix.TabRunix);
        this.setLightValue(15.0F);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        this.theIcon = new Icon[] {
                iconRegister.registerIcon("Runix:QixsilverStill"),
                iconRegister.registerIcon("Runix:QixsilverFlowing")
        };
    }
}
