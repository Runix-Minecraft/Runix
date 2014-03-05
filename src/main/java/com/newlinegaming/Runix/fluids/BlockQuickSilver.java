package com.newlinegaming.Runix.fluids;

import com.newlinegaming.Runix.Runix;
import com.newlinegaming.Runix.lib.LibRef;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraftforge.fluids.BlockFluidClassic;

public class BlockQuickSilver extends BlockFluidClassic {

    @SideOnly(Side.CLIENT)
    protected Icon FQS; //Still Icon for quicksilver

    @SideOnly(Side.CLIENT)
    protected Icon FQF; //Flowing Icon for quicksilver

    public BlockQuickSilver(int id) {
        super(id, ModFluid.QuickSilver, Material.water);
        this.setCreativeTab(Runix.TabRunix);
        this.setUnlocalizedName("Qiicksilver");
    }

    @Override
    public Icon getIcon(int side, int meta) {
        return (side == 0 || side == 1)? FQS : FQF;
    }

    @Override
    public void registerIcons(IconRegister reg) {
        FQS = reg.registerIcon(LibRef.MOD_ID + ":QuicksilverStill");
        FQF = reg.registerIcon(LibRef.MOD_ID + ":QuicksilverFlowing");
    }
}
