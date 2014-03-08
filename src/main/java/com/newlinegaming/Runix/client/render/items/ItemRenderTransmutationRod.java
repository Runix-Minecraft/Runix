package com.newlinegaming.Runix.client.render.items;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import com.newlinegaming.Runix.client.models.items.ModelTransmutationRod;
import com.newlinegaming.Runix.lib.LibRef;

import cpw.mods.fml.client.FMLClientHandler;

public class ItemRenderTransmutationRod implements IItemRenderer {
	
	private ModelTransmutationRod model;
	
	public ItemRenderTransmutationRod() {
		model = new ModelTransmutationRod();
	}
	private static final ResourceLocation TEXTURE = new ResourceLocation(LibRef.MOD_ID, "textures/models/transrod.png");

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		switch(type) {
		
//		case ENTITY: return true;
//		case EQUIPPED_FIRST_PERSON:
		case EQUIPPED: return true;
//		case INVENTORY: return true;
		default: return false;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
				return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		switch(type) {
			case EQUIPPED: {
				GL11.glPushMatrix();

				GL11.glRotatef(20F, 0.0F, 0.0F, 20.0F);
//				GL11.glRotatef(6F, 5.0F, 0.0F, 0.0F);
//				GL11.glTranslatef(1.0F, 1.5F, 1.0F);
				GL11.glScalef(1.5F, 1.5F, 1.5F);
				FMLClientHandler.instance().getClient().renderEngine.bindTexture(TEXTURE);
				model.render(.0625F);
				GL11.glPopMatrix();
			}
		
		}

	}

}
