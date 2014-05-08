package com.newlinegaming.runix.client;

import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

import com.newlinegaming.runix.client.render.items.ItemRenderTransmutationRod;
import com.newlinegaming.runix.common.CommonProxy;
import com.newlinegaming.runix.common.item.ModItem;

public class ClientProxy extends CommonProxy {
	
	public void registerRenderInformation() {
//	    ISimpleBlockRenderingHandler handler = null;
//	    RenderingRegistry.registerBlockHandler(handler);
	}
	
	public void RenderingAndTextures() {
		MinecraftForgeClient.registerItemRenderer(ModItem.TransRod,  (IItemRenderer)new ItemRenderTransmutationRod());
	}
}
