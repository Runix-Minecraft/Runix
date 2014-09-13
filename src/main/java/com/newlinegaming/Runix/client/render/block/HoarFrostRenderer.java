package com.newlinegaming.Runix.client.render.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.newlinegaming.Runix.block.ModBlock;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class HoarFrostRenderer implements ISimpleBlockRenderingHandler {

    @SideOnly(Side.CLIENT)
    private IIcon IconGreekFire;
    @SideOnly(Side.CLIENT)
    private IIcon IconGreekFire1;

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId,
            RenderBlocks renderer) {
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {

        Tessellator tessellator = Tessellator.instance;
        IIcon icon = ModBlock.hoar_frost.getIcon(0, 0); //TODO individualize sides
        IIcon icon1 = ModBlock.hoar_frost.getIcon(0, 1);
        IIcon icon2 = icon;
        boolean anyRender = false;

        tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
        tessellator.setBrightness(ModBlock.hoar_frost.getMixedBrightnessForBlock(world, x, y, z));
        double d0 = (double)icon2.getMinU();
        double d1 = (double)icon2.getMinV();
        double d2 = (double)icon2.getMaxU();
        double d3 = (double)icon2.getMaxV();
        float f = 1.0F;

        float f1 = 0.0625F;
        float f2 = 0.0625F;

        if ((x + y + z & 1) == 1)
        {
            d0 = (double)icon1.getMinU();
            d1 = (double)icon1.getMinV();
            d2 = (double)icon1.getMaxU();
            d3 = (double)icon1.getMaxV();
        }

        if ((x / 2 + y / 2 + z / 2 & 1) == 1)
        {
            double temp = d2;
            d2 = d0;
            d0 = temp;
        }


        if (ModBlock.hoar_frost.isBlockSolid(world, x - 1, y, z, 0))
        {
            anyRender = true;
            tessellator.addVertexWithUV((double)((float)x + f1), (double)((float)y + f + f2), (double)(z + 1), d2, d1);
            tessellator.addVertexWithUV((double)(x + 0), (double)((float)(y + 0) + f2), (double)(z + 1), d2, d3);
            tessellator.addVertexWithUV((double)(x + 0), (double)((float)(y + 0) + f2), (double)(z + 0), d0, d3);
            tessellator.addVertexWithUV((double)((float)x + f1), (double)((float)y + f + f2), (double)(z + 0), d0, d1);
            tessellator.addVertexWithUV((double)((float)x + f1), (double)((float)y + f + f2), (double)(z + 0), d0, d1);
            tessellator.addVertexWithUV((double)(x + 0), (double)((float)(y + 0) + f2), (double)(z + 0), d0, d3);
            tessellator.addVertexWithUV((double)(x + 0), (double)((float)(y + 0) + f2), (double)(z + 1), d2, d3);
            tessellator.addVertexWithUV((double)((float)x + f1), (double)((float)y + f + f2), (double)(z + 1), d2, d1);
        }

        if (ModBlock.hoar_frost.isBlockSolid(world, x + 1, y, z, 0))
        {
            anyRender = true;
            tessellator.addVertexWithUV((double)((float)(x + 1) - f1), (double)((float)y + f + f2), (double)(z + 0), d0, d1);
            tessellator.addVertexWithUV((double)(x + 1 - 0), (double)((float)(y + 0) + f2), (double)(z + 0), d0, d3);
            tessellator.addVertexWithUV((double)(x + 1 - 0), (double)((float)(y + 0) + f2), (double)(z + 1), d2, d3);
            tessellator.addVertexWithUV((double)((float)(x + 1) - f1), (double)((float)y + f + f2), (double)(z + 1), d2, d1);
            tessellator.addVertexWithUV((double)((float)(x + 1) - f1), (double)((float)y + f + f2), (double)(z + 1), d2, d1);
            tessellator.addVertexWithUV((double)(x + 1 - 0), (double)((float)(y + 0) + f2), (double)(z + 1), d2, d3);
            tessellator.addVertexWithUV((double)(x + 1 - 0), (double)((float)(y + 0) + f2), (double)(z + 0), d0, d3);
            tessellator.addVertexWithUV((double)((float)(x + 1) - f1), (double)((float)y + f + f2), (double)(z + 0), d0, d1);
        }

        if (ModBlock.hoar_frost.isBlockSolid(world, x, y, z - 1, 0))
        {
            anyRender = true;
            tessellator.addVertexWithUV((double)(x + 0), (double)((float)y + f + f2), (double)((float)z + f1), d2, d1);
            tessellator.addVertexWithUV((double)(x + 0), (double)((float)(y + 0) + f2), (double)(z + 0), d2, d3);
            tessellator.addVertexWithUV((double)(x + 1), (double)((float)(y + 0) + f2), (double)(z + 0), d0, d3);
            tessellator.addVertexWithUV((double)(x + 1), (double)((float)y + f + f2), (double)((float)z + f1), d0, d1);
            tessellator.addVertexWithUV((double)(x + 1), (double)((float)y + f + f2), (double)((float)z + f1), d0, d1);
            tessellator.addVertexWithUV((double)(x + 1), (double)((float)(y + 0) + f2), (double)(z + 0), d0, d3);
            tessellator.addVertexWithUV((double)(x + 0), (double)((float)(y + 0) + f2), (double)(z + 0), d2, d3);
            tessellator.addVertexWithUV((double)(x + 0), (double)((float)y + f + f2), (double)((float)z + f1), d2, d1);
        }

        if (ModBlock.hoar_frost.isBlockSolid(world, x, y, z + 1, 0))
        {
            anyRender = true;
            tessellator.addVertexWithUV((double)(x + 1), (double)((float)y + f + f2), (double)((float)(z + 1) - f1), d0, d1);
            tessellator.addVertexWithUV((double)(x + 1), (double)((float)(y + 0) + f2), (double)(z + 1 - 0), d0, d3);
            tessellator.addVertexWithUV((double)(x + 0), (double)((float)(y + 0) + f2), (double)(z + 1 - 0), d2, d3);
            tessellator.addVertexWithUV((double)(x + 0), (double)((float)y + f + f2), (double)((float)(z + 1) - f1), d2, d1);
            tessellator.addVertexWithUV((double)(x + 0), (double)((float)y + f + f2), (double)((float)(z + 1) - f1), d2, d1);
            tessellator.addVertexWithUV((double)(x + 0), (double)((float)(y + 0) + f2), (double)(z + 1 - 0), d2, d3);
            tessellator.addVertexWithUV((double)(x + 1), (double)((float)(y + 0) + f2), (double)(z + 1 - 0), d0, d3);
            tessellator.addVertexWithUV((double)(x + 1), (double)((float)y + f + f2), (double)((float)(z + 1) - f1), d0, d1);
        }


        double d5 = (double)x + 0.5D + 0.5D;
        double d8 = (double)z + 0.5D - 0.5D;
        double d9 = (double)x + 0.5D - 0.5D;
        double d11 = (double)z + 0.5D + 0.5D;
        d0 = (double)icon.getMinU();
        d1 = (double)icon.getMinV();
        d2 = (double)icon.getMaxU();
        d3 = (double)icon.getMaxV();
        if (ModBlock.hoar_frost.isBlockSolid(world, x, y + 1, z, 0)) //block above
        { //TODO not two textures
            anyRender = true;
            ++y;
            f = -0.0625F;

            if ((x + y + z & 1) == 0)
            {
                tessellator.addVertexWithUV(d9, (double)((float)y + f), (double)(z + 0), d2, d1);
                tessellator.addVertexWithUV(d5, (double)(y + 0), (double)(z + 0), d2, d3);
                tessellator.addVertexWithUV(d5, (double)(y + 0), (double)(z + 1), d0, d3);
                tessellator.addVertexWithUV(d9, (double)((float)y + f), (double)(z + 1), d0, d1);
            }
            else
            {
                tessellator.addVertexWithUV((double)(x + 0), (double)((float)y + f), d11, d2, d1);
                tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0), d8, d2, d3);
                tessellator.addVertexWithUV((double)(x + 1), (double)(y + 0), d8, d0, d3);
                tessellator.addVertexWithUV((double)(x + 1), (double)((float)y + f), d11, d0, d1);
            }
        }

        if (ModBlock.hoar_frost.isBlockSolid(world, x, y - 1, z, 0)) { //bottom side
            anyRender = true;
            f = 0.0625F;

            if ((x + y + z & 1) == 0)
            {
                tessellator.addVertexWithUV(d9, (double)((float)y + f), (double)(z + 1), d0, d1);
                tessellator.addVertexWithUV(d5, (double)(y + 0), (double)(z + 1), d0, d3);
                tessellator.addVertexWithUV(d5, (double)(y + 0), (double)(z + 0), d2, d3);
                tessellator.addVertexWithUV(d9, (double)((float)y + f), (double)(z + 0), d2, d1);
            }
            else
            {
                tessellator.addVertexWithUV((double)(x + 1), (double)((float)y + f), d11, d0, d1);
                tessellator.addVertexWithUV((double)(x + 1), (double)(y + 0), d8, d0, d3);
                tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0), d8, d2, d3);
                tessellator.addVertexWithUV((double)(x + 0), (double)((float)y + f), d11, d2, d1);
            }
        }

        return anyRender;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return 72;
    }

}
