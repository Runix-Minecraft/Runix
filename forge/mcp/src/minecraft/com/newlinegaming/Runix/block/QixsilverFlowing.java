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
        super(par1, Material.water);
        
        this.blockHardness = 100.0F;
        this.setLightOpacity(1);
        this.setCreativeTab(Runix.TabRunix);
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
