package com.newlinegaming.Runix.client.render.item;

import org.lwjgl.opengl.GL11;

import com.newlinegaming.Runix.client.models.ModelTransmutationRod;
import com.newlinegaming.Runix.lib.LibInfo;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderTransmutaionRod implements IItemRenderer {
    
    private ModelTransmutationRod model;
    
    public ItemRenderTransmutaionRod() {
        model = new ModelTransmutationRod();
    }
    
    private static final ResourceLocation TEXTURE = new ResourceLocation(LibInfo.MOD_ID, "textures/models/transrod.png");

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        switch (type) {
        
        case ENTITY:
        case EQUIPPED_FIRST_PERSON:
        case EQUIPPED:
        case INVENTORY: return true;

        default: return false;

        }
        
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
            ItemRendererHelper helper) {
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        switch (type) {
        case ENTITY: {
            GL11.glPushMatrix();
            
            
            GL11.glRotatef(0.0f, 0.0f, 0.0F, 0.0F);
            GL11.glTranslatef(-0.35f, 0.2f, -0.0f);
            GL11.glScalef(0.9F, 0.9F, 0.9F);
            FMLClientHandler.instance().getClient().renderEngine.bindTexture(TEXTURE);
            model.render(.0625F);
            GL11.glPopMatrix();
            break;
        }
        case EQUIPPED: {
            GL11.glPushMatrix();
            ;
            GL11.glTranslatef(0.0F, 0.0f, -0.0f);
            GL11.glScalef(1.0f, 1.0f, 1.0f);
            FMLClientHandler.instance().getClient().renderEngine.bindTexture(TEXTURE);
            model.render(.0625F);
            GL11.glPopMatrix();
        }
            
        case EQUIPPED_FIRST_PERSON: {
            GL11.glPushMatrix();
            
            GL11.glRotatef(5F, 0.0F, 0.0F, 20.0F);
            GL11.glRotatef(10F, 100.0F, 0.0F, 20.0F);
            GL11.glTranslatef(0.5F, 0.2F, -0.1F);
            GL11.glScalef(0.9F, 0.9F, 0.9F);
            FMLClientHandler.instance().getClient().renderEngine.bindTexture(TEXTURE);
            model.render(.0625F);
            
            GL11.glPopMatrix();
            break;
            
        }
        
        case INVENTORY:{
            GL11.glPushMatrix();
            
            GL11.glRotatef(0.0F, 0.0F, 0.0F, 0.0F);
            GL11.glTranslatef(1.0F, -2.0F, 0.0F);
            GL11.glScalef(1.0F, 1.0F, 1.0F);
            FMLClientHandler.instance().getClient().renderEngine.bindTexture(TEXTURE);
            model.render(.0625F);
            
            GL11.glPopMatrix();
            break;
        }
        default: {}
        
        }
        
    }

}
