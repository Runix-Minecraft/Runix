package com.newlinegaming.Runix.proxys;

import com.newlinegaming.Runix.client.render.tile.RenderTileLightBeam;
import com.newlinegaming.Runix.tile.TileLightBeam;

import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {
	
    @Override
	public void registerRenderInformation() {
//	    ISimpleBlockRenderingHandler handler = null;
//	    RenderingRegistry.registerBlockHandler(handler);
	    ClientRegistry.bindTileEntitySpecialRenderer(TileLightBeam.class, new RenderTileLightBeam());
	}
	
	public void registerTileEnitiy() {} //NO OP
}
