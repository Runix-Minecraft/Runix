package com.newlinegaming.Runix.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelTransmutationRod extends ModelBase {
    
    private ModelRenderer Rod;
    
    public ModelTransmutationRod() {
        textureHeight = 32;
        textureWidth = 32;
        
        Rod = new ModelRenderer(this, 0, 0);
        Rod.addBox(-1f, -15f, -1f, 2, 30, 2);
        Rod.setRotationPoint(6f, 10f, 0f);
        Rod.setTextureSize(textureWidth, textureWidth);
        setRotation(Rod, 0f, 0f, 0f);
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
