package com.newlinegaming.Runix.block;

import com.newlinegaming.Runix.RunixMain;
import com.newlinegaming.Runix.api.RunixConstants;
import com.newlinegaming.Runix.lib.LibInfo;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import org.jetbrains.annotations.NotNull;

public class RunixBlock  extends Block {
    public RunixBlock(String name, @NotNull Material mat, boolean useCreativeTab) {
        super(mat);

        if (useCreativeTab) {
            setCreativeTab(RunixMain.instance.tabs);
        }
        this.setRegistryName(LibInfo.MOD_ID, name);
        this.setUnlocalizedName(LibInfo.modIdPrefix(name));
    }
}
