package com.newlinegaming.Runix.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelTransmutationRod extends ModelBase {
    
    ModelRenderer Rod;
    
    public ModelTransmutationRod() {
        textureHeight = 32;
        textureWidth = 32;
        
        Rod = new ModelRenderer(this, 0, 0);
        Rod.addBox(-1F, -15F, -1F, 2, 30, 2);
        Rod.setRotationPoint(6F, 10F, 0F);
        Rod.setTextureSize(textureWidth, textureWidth);
        setRotation(Rod, -1.07818F, 0F, 0F);
    }
    
    public void render(float f) {
        Rod.render(f);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

}
