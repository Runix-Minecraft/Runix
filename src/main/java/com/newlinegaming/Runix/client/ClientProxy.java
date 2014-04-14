package com.newlinegaming.Runix.client;

import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

import com.newlinegaming.Runix.client.render.items.ItemRenderTransmutationRod;
import com.newlinegaming.Runix.common.CommonProxy;
import com.newlinegaming.Runix.common.item.ModItem;

public class ClientProxy extends CommonProxy {
	
	public void registerRenderInformation() {
//	    ISimpleBlockRenderingHandler handler = null;
//	    RenderingRegistry.registerBlockHandler(handler);
	}
	
	public void RenderingAndTextures() {
		MinecraftForgeClient.registerItemRenderer(ModItem.TransRod,  (IItemRenderer)new ItemRenderTransmutationRod());
	}
}
