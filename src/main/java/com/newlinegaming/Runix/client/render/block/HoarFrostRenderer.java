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

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId,
            RenderBlocks renderer) {
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {

        Tessellator tessellator = Tessellator.instance;
        int meta = world.getBlockMetadata(x, y, z);
        if(meta == 0 || meta == 2 || meta == 14)
            return renderer.renderStandardBlock(block, x, y, z); //still not rendering side blocks
        IIcon icon = ModBlock.hoar_frost.getIcon(0, meta);
        boolean anyRender = false;

        tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
        tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
        double d0 = (double)icon.getMinU();
        double d1 = (double)icon.getMinV();
        double d2 = (double)icon.getMaxU();
        double d3 = (double)icon.getMaxV();
        float f = 1.0F;

        float f1 = 0.0125F;

        if (block.isBlockSolid(world, x - 1, y, z, 0))
        {
            anyRender = true;
            tessellator.addVertexWithUV((double)((float)x + f1), (double)((float)y + f + f1), (double)(z + 1), d2, d1);
            tessellator.addVertexWithUV((double)(x + 0), (double)((float)(y + 0) + f1), (double)(z + 1), d2, d3);
            tessellator.addVertexWithUV((double)(x + 0), (double)((float)(y + 0) + f1), (double)(z + 0), d0, d3);
            tessellator.addVertexWithUV((double)((float)x + f1), (double)((float)y + f + f1), (double)(z + 0), d0, d1);
            tessellator.addVertexWithUV((double)((float)x + f1), (double)((float)y + f + f1), (double)(z + 0), d0, d1);
            tessellator.addVertexWithUV((double)(x + 0), (double)((float)(y + 0) + f1), (double)(z + 0), d0, d3);
            tessellator.addVertexWithUV((double)(x + 0), (double)((float)(y + 0) + f1), (double)(z + 1), d2, d3);
            tessellator.addVertexWithUV((double)((float)x + f1), (double)((float)y + f + f1), (double)(z + 1), d2, d1);
        }

        if (block.isBlockSolid(world, x + 1, y, z, 0))
        {
            anyRender = true;
            tessellator.addVertexWithUV((double)((float)(x + 1) - f1), (double)((float)y + f + f1), (double)(z + 0), d0, d1);
            tessellator.addVertexWithUV((double)(x + 1 - 0), (double)((float)(y + 0) + f1), (double)(z + 0), d0, d3);
            tessellator.addVertexWithUV((double)(x + 1 - 0), (double)((float)(y + 0) + f1), (double)(z + 1), d2, d3);
            tessellator.addVertexWithUV((double)((float)(x + 1) - f1), (double)((float)y + f + f1), (double)(z + 1), d2, d1);
            tessellator.addVertexWithUV((double)((float)(x + 1) - f1), (double)((float)y + f + f1), (double)(z + 1), d2, d1);
            tessellator.addVertexWithUV((double)(x + 1 - 0), (double)((float)(y + 0) + f1), (double)(z + 1), d2, d3);
            tessellator.addVertexWithUV((double)(x + 1 - 0), (double)((float)(y + 0) + f1), (double)(z + 0), d0, d3);
            tessellator.addVertexWithUV((double)((float)(x + 1) - f1), (double)((float)y + f + f1), (double)(z + 0), d0, d1);
        }

        if (block.isBlockSolid(world, x, y, z - 1, 0)) //north solid
        {
            anyRender = true;
            tessellator.addVertexWithUV((double)(x + 0), (double)((float)y + f + f1), (double)((float)z + f1), d2, d1);
            tessellator.addVertexWithUV((double)(x + 0), (double)((float)(y + 0) + f1), (double)(z + f1), d2, d3);
            tessellator.addVertexWithUV((double)(x + 1), (double)((float)(y + 0) + f1), (double)(z + f1), d0, d3);
            tessellator.addVertexWithUV((double)(x + 1), (double)((float)y + f + f1), (double)((float)z + f1), d0, d1);
            tessellator.addVertexWithUV((double)(x + 1), (double)((float)y + f + f1), (double)((float)z + f1), d0, d1);
            tessellator.addVertexWithUV((double)(x + 1), (double)((float)(y + 0) + f1), (double)(z + f1), d0, d3);
            tessellator.addVertexWithUV((double)(x + 0), (double)((float)(y + 0) + f1), (double)(z + f1), d2, d3);
            tessellator.addVertexWithUV((double)(x + 0), (double)((float)y + f + f1), (double)((float)z + f1), d2, d1);
        }

        if (block.isBlockSolid(world, x, y, z + 1, 0))
        {
            anyRender = true;
            tessellator.addVertexWithUV((double)(x + 1), (double)((float)y + f + f1), (double)((float)(z + 1) - f1), d0, d1);
            tessellator.addVertexWithUV((double)(x + 1), (double)((float)(y + 0) + f1), (double)(z + 1 - 0), d0, d3);
            tessellator.addVertexWithUV((double)(x + 0), (double)((float)(y + 0) + f1), (double)(z + 1 - 0), d2, d3);
            tessellator.addVertexWithUV((double)(x + 0), (double)((float)y + f + f1), (double)((float)(z + 1) - f1), d2, d1);
            tessellator.addVertexWithUV((double)(x + 0), (double)((float)y + f + f1), (double)((float)(z + 1) - f1), d2, d1);
            tessellator.addVertexWithUV((double)(x + 0), (double)((float)(y + 0) + f1), (double)(z + 1 - 0), d2, d3);
            tessellator.addVertexWithUV((double)(x + 1), (double)((float)(y + 0) + f1), (double)(z + 1 - 0), d0, d3);
            tessellator.addVertexWithUV((double)(x + 1), (double)((float)y + f + f1), (double)((float)(z + 1) - f1), d0, d1);
        }


        double d5 = (double)x + 0.5D + 0.5D;
        double d9 = (double)x + 0.5D - 0.5D;
        d0 = (double)icon.getMinU();
        d1 = (double)icon.getMinV();
        d2 = (double)icon.getMaxU();
        d3 = (double)icon.getMaxV();
        f = -0.0125F;
        if (block.isBlockSolid(world, x, y + 1, z, 0)) {//block above
            anyRender = true;
            ++y;
            tessellator.addVertexWithUV(d9, (double)((float)y + f), (double)(z + 0), d2, d1);
            tessellator.addVertexWithUV(d5, (double)(y + 0), (double)(z + 0), d2, d3);
            tessellator.addVertexWithUV(d5, (double)(y + 0), (double)(z + 1), d0, d3);
            tessellator.addVertexWithUV(d9, (double)((float)y + f), (double)(z + 1), d0, d1);
        }

        if (block.isBlockSolid(world, x, y - 1, z, 0)) { //bottom side
            anyRender = true;
            f = -f;
            tessellator.addVertexWithUV(d9, (double)((float)y + f), (double)(z + 1), d0, d1);
            tessellator.addVertexWithUV(d5, (double)(y + f), (double)(z + 1), d0, d3);
            tessellator.addVertexWithUV(d5, (double)(y + f), (double)(z + 0), d2, d3);
            tessellator.addVertexWithUV(d9, (double)((float)y + f), (double)(z + 0), d2, d1);
        }

        return anyRender;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return false;
    }

    @Override
    public int getRenderId() {
        return 72;
    }

}
