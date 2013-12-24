package com.newlinegaming.Runix;

import java.util.Collection;

import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.opengl.GL11;

public class RenderHelper {


    private float progress;
    
    public RenderHelper(){
        reset();
    }

    public void reset(){
        progress = 0.0f;
    }
    
    public void highlightBoxes(Collection<WorldXYZ> structureBlocks, EntityPlayer player){
        if(progress > 1.0)
            return;
        progress += 0.01f;//animation timer
        double doubleX = player.posX - 0.5;
        double doubleY = player.posY + 0.1;
        double doubleZ = player.posZ - 0.5;
    
        GL11.glPushMatrix();
            GL11.glTranslated(-doubleX, -doubleY, -doubleZ);
            GL11.glColor3ub((byte)0,(byte)216,(byte)216);
            
            for(WorldXYZ block : structureBlocks){
                renderWireCube(block.posX, block.posY, block.posZ, progress);
            }
        GL11.glPopMatrix();
    }
    
    /**Makes a wireframe Cube given an XYZ posiiton
     */
    protected void renderWireCube(float mx, float my, float mz, float sideLength) {
        my -= 0.4f;
        sideLength /= 2.0f; //half because +.5 and -.5 = distance 1.0 
        GL11.glBegin(GL11.GL_LINE_STRIP);
            GL11.glVertex3f(mx+sideLength, my, mz+sideLength);
            GL11.glVertex3f(mx+sideLength, my, mz-sideLength);
            GL11.glVertex3f(mx-sideLength, my, mz-sideLength);
            GL11.glVertex3f(mx-sideLength, my, mz+sideLength);
            GL11.glVertex3f(mx+sideLength, my, mz+sideLength);
        GL11.glEnd();
    }


}
