package com.newlinegaming.Runix.helper;

import java.util.Collection;

import com.newlinegaming.Runix.WorldXYZ;

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
    public boolean highlightBoxes(Collection<WorldXYZ> structureBlocks, boolean reverse, EntityPlayer player){
        return highlightBoxes(structureBlocks, reverse, player,0, 216, 216);
    }
    public boolean highlightBoxes(Collection<WorldXYZ> structureBlocks, boolean reverse, EntityPlayer player,int R,int G, int B){
        if((!reverse && progress > 1.0) || (reverse && progress < 0.0))
            return false;
        if(!reverse)
            progress += 0.02f;//animation timer
        else
            progress -= 0.04f;
        double doubleX = player.getPosition().getX() - 0.5;
        double doubleY = player.getPosition().getY() + 0.1;
        double doubleZ = player.getPosition().getZ() - 0.5;
        GL11.glPushMatrix();
            GL11.glTranslated(-doubleX, -doubleY, -doubleZ);
            GL11.glColor3ub((byte)R,(byte)G,(byte)B);
            
            for(WorldXYZ block : structureBlocks){
                renderWireCube(block.getX(), block.getY(), block.getZ(), progress);
            }
        GL11.glPopMatrix();
        return true;
    }
    
    /**
     * Makes a wireframe Cube given an XYZ posiiton
     */
    private void renderWireCube(float mx, float my, float mz, float sideLength) {

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
