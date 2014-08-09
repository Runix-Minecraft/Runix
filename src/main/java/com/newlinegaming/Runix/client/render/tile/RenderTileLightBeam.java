package com.newlinegaming.Runix.client.render.tile;

import org.lwjgl.opengl.GL11;

import com.newlinegaming.Runix.lib.LibInfo;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderTileLightBeam extends TileEntitySpecialRenderer {

    
    private final ResourceLocation TEXTURE = new ResourceLocation(LibInfo.MOD_ID, "textures/fx/beam.png");
    
    private int width = 16;
    private int hight = 16;
    
    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float tick) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, (float)z);
        
        Tessellator tess = Tessellator.instance;
        
        tess.startDrawingQuads();
        
        tess.addVertexWithUV(0, 0, 0, 0, 0);//bottom left texture
        tess.addVertexWithUV(0, 1, 0, 0, 1);//top left
        tess.addVertexWithUV(1, 1, 0, 1, 1);//top right
        tess.addVertexWithUV(1, 0, 0, 1, 0);//bottom right
        System.out.println("FOo");
        
        tess.draw();
        GL11.glPopMatrix();
    }

}
