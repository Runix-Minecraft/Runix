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
        double u0 = (double)icon.getMinU();
        double v0 = (double)icon.getMinV();
        double u1 = (double)icon.getMaxU();
        double v1 = (double)icon.getMaxV();
        float b = 0.0125F; //very small bump to keep planes from intersecting

        if (block.isBlockSolid(world, x - 1, y, z, 0)) {
            anyRender = true;
            tessellator.addVertexWithUV((double)(x + b), (double)(y + 1 + b), (double)(z + 1), u1, v0);
            tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0 + b), (double)(z + 1), u1, v1);
            tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0 + b), (double)(z + 0), u0, v1);
            tessellator.addVertexWithUV((double)(x + b), (double)(y + 1 + b), (double)(z + 0), u0, v0);
        }

        if (block.isBlockSolid(world, x + 1, y, z, 0)) {
            anyRender = true;
            tessellator.addVertexWithUV((double)(x + 1 - b), (double)(y + 1 + b), (double)(z + 0), u0, v0);
            tessellator.addVertexWithUV((double)(x + 1 - 0), (double)(y + 0 + b), (double)(z + 0), u0, v1);
            tessellator.addVertexWithUV((double)(x + 1 - 0), (double)(y + 0 + b), (double)(z + 1), u1, v1);
            tessellator.addVertexWithUV((double)(x + 1 - b), (double)(y + 1 + b), (double)(z + 1), u1, v0);
        }

        if (block.isBlockSolid(world, x, y, z - 1, 0)) {//north solid
            anyRender = true;
            tessellator.addVertexWithUV((double)(x + 0), (double)(y + 1 + b), (double)(z + b), u1, v0);
            tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0 + b), (double)(z + b), u1, v1);
            tessellator.addVertexWithUV((double)(x + 1), (double)(y + 0 + b), (double)(z + b), u0, v1);
            tessellator.addVertexWithUV((double)(x + 1), (double)(y + 1 + b), (double)(z + b), u0, v0);
        }

        if (block.isBlockSolid(world, x, y, z + 1, 0)) {
            anyRender = true;
            tessellator.addVertexWithUV((double)(x + 1), (double)(y + 1 + b), (double)(z + 1 - b), u0, v0);
            tessellator.addVertexWithUV((double)(x + 1), (double)(y + 0 + b), (double)(z + 1 - 0), u0, v1);
            tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0 + b), (double)(z + 1 - 0), u1, v1);
            tessellator.addVertexWithUV((double)(x + 0), (double)(y + 1 + b), (double)(z + 1 - b), u1, v0);
        }

        if (block.isBlockSolid(world, x, y + 1, z, 0)) {//block above
            anyRender = true;
            tessellator.addVertexWithUV((double)(x + 0), (double)(y + 1 - b), (double)(z + 0), u1, v0);
            tessellator.addVertexWithUV((double)(x + 1), (double)(y + 1 - b), (double)(z + 0), u1, v1);
            tessellator.addVertexWithUV((double)(x + 1), (double)(y + 1 - b), (double)(z + 1), u0, v1);
            tessellator.addVertexWithUV((double)(x + 0), (double)(y + 1 - b), (double)(z + 1), u0, v0);
        }

        if (block.isBlockSolid(world, x, y - 1, z, 0)) { //bottom side
            anyRender = true;
            tessellator.addVertexWithUV((double)(x + 0), (double)(y + b), (double)(z + 1), u0, v0);
            tessellator.addVertexWithUV((double)(x + 1), (double)(y + b), (double)(z + 1), u0, v1);
            tessellator.addVertexWithUV((double)(x + 1), (double)(y + b), (double)(z + 0), u1, v1);
            tessellator.addVertexWithUV((double)(x + 0), (double)(y + b), (double)(z + 0), u1, v0);
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
