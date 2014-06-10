package com.newlinegaming.Runix.fluids;

import com.newlinegaming.Runix.RunixMain;
import com.newlinegaming.Runix.lib.LibRef;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockQixSilver extends BlockFluidClassic {

    @SideOnly(Side.CLIENT)
    private IIcon FQS; //Still Icon for quicksilver

    @SideOnly(Side.CLIENT)
    private IIcon FQF; //Flowing Icon for quicksilver

    public BlockQixSilver(Fluid fluid, Material material) {
        super(fluid, material);
        this.setCreativeTab(RunixMain.TabRunix);
        this.setBlockName("runix:qixsilver");
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return (side == 0 || side == 1)? FQS : FQF;
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        FQS = reg.registerIcon(LibRef.MOD_ID + ":qixsilverstill");
        FQF = reg.registerIcon(LibRef.MOD_ID + ":qixsilverflowing");
    }
}
