package com.newlinegaming.Runix.client.render.block;

//import net.minecraft.block.Block;
//import net.minecraft.client.renderer.RenderBlocks;
//import net.minecraft.client.renderer.Tessellator;
//import net.minecraft.util.IIcon;
//import net.minecraft.world.IBlockAccess;
//import net.minecraft.world.World;
//
//import com.newlinegaming.Runix.block.ModBlock;
//
//import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
//import cpw.mods.fml.relauncher.Side;
//import cpw.mods.fml.relauncher.SideOnly;
//
//public class GreekFireRenderer implements ISimpleBlockRenderingHandler {
//
//    @SideOnly(Side.CLIENT)
//    private IIcon IconGreekFire;
//    @SideOnly(Side.CLIENT)
//    private IIcon IconGreekFire1;
//
//    @Override
//    public void renderInventoryBlock(Block block, int metadata, int modelId,
//            RenderBlocks renderer) {
//    }
//
//    @Override
//    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
//
//        Tessellator tessellator = Tessellator.instance;
//        IIcon icon = ModBlock.greekFire.getFireIcon(0);
//        IIcon icon1 = ModBlock.greekFire.getFireIcon(1);
//        IIcon icon2 = icon;
//
//        tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
//        tessellator.setBrightness(ModBlock.greekFire.getMixedBrightnessForBlock(world, x, y, z));
//        double d0 = (double)icon2.getMinU();
//        double d1 = (double)icon2.getMinV();
//        double d2 = (double)icon2.getMaxU();
//        double d3 = (double)icon2.getMaxV();
//        float f = 1.4F;
//        double d4;
//        double d5;
//        double d6;
//        double d7;
//        double d8;
//        double d9;
//        double d10;
//
//        if (!World.doesBlockHaveSolidTopSurface(world, x, y - 1, z) && !ModBlock.greekFire.canBlockCatchFire(world, x, y - 1, z)) {
//            float f1 = 0.2F;
//            float f2 = 0.0625F;
//
//            if ((x + y + z & 1) == 1)
//            {
//                d0 = (double)icon1.getMinU();
//                d1 = (double)icon1.getMinV();
//                d2 = (double)icon1.getMaxU();
//                d3 = (double)icon1.getMaxV();
//            }
//
//            if ((x / 2 + y / 2 + z / 2 & 1) == 1)
//            {
//                d5 = d2;
//                d2 = d0;
//                d0 = d5;
//            }
//
//            if (ModBlock.greekFire.canBlockCatchFire(world, x - 1, y, z))
//            {
//                tessellator.addVertexWithUV((double)((float)x + f1), (double)((float)y + f + f2), (double)(z + 1), d2, d1);
//                tessellator.addVertexWithUV((double)(x + 0), (double)((float)(y + 0) + f2), (double)(z + 1), d2, d3);
//                tessellator.addVertexWithUV((double)(x + 0), (double)((float)(y + 0) + f2), (double)(z + 0), d0, d3);
//                tessellator.addVertexWithUV((double)((float)x + f1), (double)((float)y + f + f2), (double)(z + 0), d0, d1);
//                tessellator.addVertexWithUV((double)((float)x + f1), (double)((float)y + f + f2), (double)(z + 0), d0, d1);
//                tessellator.addVertexWithUV((double)(x + 0), (double)((float)(y + 0) + f2), (double)(z + 0), d0, d3);
//                tessellator.addVertexWithUV((double)(x + 0), (double)((float)(y + 0) + f2), (double)(z + 1), d2, d3);
//                tessellator.addVertexWithUV((double)((float)x + f1), (double)((float)y + f + f2), (double)(z + 1), d2, d1);
//            }
//
//            if (ModBlock.greekFire.canBlockCatchFire(world, x + 1, y, z))
//            {
//                tessellator.addVertexWithUV((double)((float)(x + 1) - f1), (double)((float)y + f + f2), (double)(z + 0), d0, d1);
//                tessellator.addVertexWithUV((double)(x + 1 - 0), (double)((float)(y + 0) + f2), (double)(z + 0), d0, d3);
//                tessellator.addVertexWithUV((double)(x + 1 - 0), (double)((float)(y + 0) + f2), (double)(z + 1), d2, d3);
//                tessellator.addVertexWithUV((double)((float)(x + 1) - f1), (double)((float)y + f + f2), (double)(z + 1), d2, d1);
//                tessellator.addVertexWithUV((double)((float)(x + 1) - f1), (double)((float)y + f + f2), (double)(z + 1), d2, d1);
//                tessellator.addVertexWithUV((double)(x + 1 - 0), (double)((float)(y + 0) + f2), (double)(z + 1), d2, d3);
//                tessellator.addVertexWithUV((double)(x + 1 - 0), (double)((float)(y + 0) + f2), (double)(z + 0), d0, d3);
//                tessellator.addVertexWithUV((double)((float)(x + 1) - f1), (double)((float)y + f + f2), (double)(z + 0), d0, d1);
//            }
//
//            if (ModBlock.greekFire.canBlockCatchFire(world, x, y, z - 1))
//            {
//                tessellator.addVertexWithUV((double)(x + 0), (double)((float)y + f + f2), (double)((float)z + f1), d2, d1);
//                tessellator.addVertexWithUV((double)(x + 0), (double)((float)(y + 0) + f2), (double)(z + 0), d2, d3);
//                tessellator.addVertexWithUV((double)(x + 1), (double)((float)(y + 0) + f2), (double)(z + 0), d0, d3);
//                tessellator.addVertexWithUV((double)(x + 1), (double)((float)y + f + f2), (double)((float)z + f1), d0, d1);
//                tessellator.addVertexWithUV((double)(x + 1), (double)((float)y + f + f2), (double)((float)z + f1), d0, d1);
//                tessellator.addVertexWithUV((double)(x + 1), (double)((float)(y + 0) + f2), (double)(z + 0), d0, d3);
//                tessellator.addVertexWithUV((double)(x + 0), (double)((float)(y + 0) + f2), (double)(z + 0), d2, d3);
//                tessellator.addVertexWithUV((double)(x + 0), (double)((float)y + f + f2), (double)((float)z + f1), d2, d1);
//            }
//
//            if (ModBlock.greekFire.canBlockCatchFire(world, x, y, z + 1))
//            {
//                tessellator.addVertexWithUV((double)(x + 1), (double)((float)y + f + f2), (double)((float)(z + 1) - f1), d0, d1);
//                tessellator.addVertexWithUV((double)(x + 1), (double)((float)(y + 0) + f2), (double)(z + 1 - 0), d0, d3);
//                tessellator.addVertexWithUV((double)(x + 0), (double)((float)(y + 0) + f2), (double)(z + 1 - 0), d2, d3);
//                tessellator.addVertexWithUV((double)(x + 0), (double)((float)y + f + f2), (double)((float)(z + 1) - f1), d2, d1);
//                tessellator.addVertexWithUV((double)(x + 0), (double)((float)y + f + f2), (double)((float)(z + 1) - f1), d2, d1);
//                tessellator.addVertexWithUV((double)(x + 0), (double)((float)(y + 0) + f2), (double)(z + 1 - 0), d2, d3);
//                tessellator.addVertexWithUV((double)(x + 1), (double)((float)(y + 0) + f2), (double)(z + 1 - 0), d0, d3);
//                tessellator.addVertexWithUV((double)(x + 1), (double)((float)y + f + f2), (double)((float)(z + 1) - f1), d0, d1);
//            }
//
//            if (ModBlock.greekFire.canBlockCatchFire(world, x, y + 1, z)) //block above
//            {
//                d5 = (double)x + 0.5D + 0.5D;
//                d6 = (double)x + 0.5D - 0.5D;
//                d7 = (double)z + 0.5D + 0.5D;
//                d8 = (double)z + 0.5D - 0.5D;
//                d9 = (double)x + 0.5D - 0.5D;
//                d10 = (double)x + 0.5D + 0.5D;
//                d4 = (double)z + 0.5D - 0.5D;
//                double d11 = (double)z + 0.5D + 0.5D;
//                d0 = (double)icon.getMinU();
//                d1 = (double)icon.getMinV();
//                d2 = (double)icon.getMaxU();
//                d3 = (double)icon.getMaxV();
//                ++y;
//                f = -0.2F;
//
//                if ((x + y + z & 1) == 0)
//                {
//                    tessellator.addVertexWithUV(d9, (double)((float)y + f), (double)(z + 0), d2, d1);
//                    tessellator.addVertexWithUV(d5, (double)(y + 0), (double)(z + 0), d2, d3);
//                    tessellator.addVertexWithUV(d5, (double)(y + 0), (double)(z + 1), d0, d3);
//                    tessellator.addVertexWithUV(d9, (double)((float)y + f), (double)(z + 1), d0, d1);
//                    d0 = (double)icon1.getMinU();
//                    d1 = (double)icon1.getMinV();
//                    d2 = (double)icon1.getMaxU();
//                    d3 = (double)icon1.getMaxV();
//                    tessellator.addVertexWithUV(d10, (double)((float)y + f), (double)(z + 1), d2, d1);
//                    tessellator.addVertexWithUV(d6, (double)(y + 0), (double)(z + 1), d2, d3);
//                    tessellator.addVertexWithUV(d6, (double)(y + 0), (double)(z + 0), d0, d3);
//                    tessellator.addVertexWithUV(d10, (double)((float)y + f), (double)(z + 0), d0, d1);
//                }
//                else
//                {
//                    tessellator.addVertexWithUV((double)(x + 0), (double)((float)y + f), d11, d2, d1);
//                    tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0), d8, d2, d3);
//                    tessellator.addVertexWithUV((double)(x + 1), (double)(y + 0), d8, d0, d3);
//                    tessellator.addVertexWithUV((double)(x + 1), (double)((float)y + f), d11, d0, d1);
//                    d0 = (double)icon1.getMinU();
//                    d1 = (double)icon1.getMinV();
//                    d2 = (double)icon1.getMaxU();
//                    d3 = (double)icon1.getMaxV();
//                    tessellator.addVertexWithUV((double)(x + 1), (double)((float)y + f), d4, d2, d1);
//                    tessellator.addVertexWithUV((double)(x + 1), (double)(y + 0), d7, d2, d3);
//                    tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0), d7, d0, d3);
//                    tessellator.addVertexWithUV((double)(x + 0), (double)((float)y + f), d4, d0, d1);
//                }
//            }
//        }
//        else
//        {
//            double d12 = (double)x + 0.5D + 0.2D;
//            d5 = (double)x + 0.5D - 0.2D;
//            d6 = (double)z + 0.5D + 0.2D;
//            d7 = (double)z + 0.5D - 0.2D;
//            d8 = (double)x + 0.5D - 0.3D;
//            d9 = (double)x + 0.5D + 0.3D;
//            d10 = (double)z + 0.5D - 0.3D;
//            d4 = (double)z + 0.5D + 0.3D;
//            tessellator.addVertexWithUV(d8, (double)((float)y + f), (double)(z + 1), d2, d1);
//            tessellator.addVertexWithUV(d12, (double)(y + 0), (double)(z + 1), d2, d3);
//            tessellator.addVertexWithUV(d12, (double)(y + 0), (double)(z + 0), d0, d3);
//            tessellator.addVertexWithUV(d8, (double)((float)y + f), (double)(z + 0), d0, d1);
//            tessellator.addVertexWithUV(d9, (double)((float)y + f), (double)(z + 0), d2, d1);
//            tessellator.addVertexWithUV(d5, (double)(y + 0), (double)(z + 0), d2, d3);
//            tessellator.addVertexWithUV(d5, (double)(y + 0), (double)(z + 1), d0, d3);
//            tessellator.addVertexWithUV(d9, (double)((float)y + f), (double)(z + 1), d0, d1);
//            d0 = (double)icon1.getMinU();
//            d1 = (double)icon1.getMinV();
//            d2 = (double)icon1.getMaxU();
//            d3 = (double)icon1.getMaxV();
//            tessellator.addVertexWithUV((double)(x + 1), (double)((float)y + f), d4, d2, d1);
//            tessellator.addVertexWithUV((double)(x + 1), (double)(y + 0), d7, d2, d3);
//            tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0), d7, d0, d3);
//            tessellator.addVertexWithUV((double)(x + 0), (double)((float)y + f), d4, d0, d1);
//            tessellator.addVertexWithUV((double)(x + 0), (double)((float)y + f), d10, d2, d1);
//            tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0), d6, d2, d3);
//            tessellator.addVertexWithUV((double)(x + 1), (double)(y + 0), d6, d0, d3);
//            tessellator.addVertexWithUV((double)(x + 1), (double)((float)y + f), d10, d0, d1);
//            d12 = (double)x + 0.5D - 0.5D;
//            d5 = (double)x + 0.5D + 0.5D;
//            d6 = (double)z + 0.5D - 0.5D;
//            d7 = (double)z + 0.5D + 0.5D;
//            d8 = (double)x + 0.5D - 0.4D;
//            d9 = (double)x + 0.5D + 0.4D;
//            d10 = (double)z + 0.5D - 0.4D;
//            d4 = (double)z + 0.5D + 0.4D;
//            tessellator.addVertexWithUV(d8, (double)((float)y + f), (double)(z + 0), d0, d1);
//            tessellator.addVertexWithUV(d12, (double)(y + 0), (double)(z + 0), d0, d3);
//            tessellator.addVertexWithUV(d12, (double)(y + 0), (double)(z + 1), d2, d3);
//            tessellator.addVertexWithUV(d8, (double)((float)y + f), (double)(z + 1), d2, d1);
//            tessellator.addVertexWithUV(d9, (double)((float)y + f), (double)(z + 1), d0, d1);
//            tessellator.addVertexWithUV(d5, (double)(y + 0), (double)(z + 1), d0, d3);
//            tessellator.addVertexWithUV(d5, (double)(y + 0), (double)(z + 0), d2, d3);
//            tessellator.addVertexWithUV(d9, (double)((float)y + f), (double)(z + 0), d2, d1);
//            d0 = (double)icon.getMinU();
//            d1 = (double)icon.getMinV();
//            d2 = (double)icon.getMaxU();
//            d3 = (double)icon.getMaxV();
//            tessellator.addVertexWithUV((double)(x + 0), (double)((float)y + f), d4, d0, d1);
//            tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0), d7, d0, d3);
//            tessellator.addVertexWithUV((double)(x + 1), (double)(y + 0), d7, d2, d3);
//            tessellator.addVertexWithUV((double)(x + 1), (double)((float)y + f), d4, d2, d1);
//            tessellator.addVertexWithUV((double)(x + 1), (double)((float)y + f), d10, d0, d1);
//            tessellator.addVertexWithUV((double)(x + 1), (double)(y + 0), d6, d0, d3);
//            tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0), d6, d2, d3);
//            tessellator.addVertexWithUV((double)(x + 0), (double)((float)y + f), d10, d2, d1);
//        }
//
//        return true;
//    }
//
//    @Override
//    public boolean shouldRender3DInInventory(int modelId) {
//        return false;
//    }
//
//    @Override
//    public int getRenderId() {
//        return 71;
//    }
//
//}
