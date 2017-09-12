package com.newlinegaming.Runix.proxys;

import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

import com.newlinegaming.Runix.client.render.block.GreekFireRenderer;
import com.newlinegaming.Runix.client.render.block.HoarFrostRenderer;
import com.newlinegaming.Runix.client.render.item.ItemRenderTransmutaionRod;
import com.newlinegaming.Runix.client.render.tile.RenderTileLightBeam;
import com.newlinegaming.Runix.item.ModItem;
import com.newlinegaming.Runix.tile.TileLightBeam;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenderInformation() {
//	    ISimpleBlockRenderingHandler handler = null;
//	    RenderingRegistry.registerBlockHandler(handler);
        ClientRegistry.bindTileEntitySpecialRenderer(TileLightBeam.class, new RenderTileLightBeam());
        MinecraftForgeClient.registerItemRenderer(ModItem.transRod, new ItemRenderTransmutaionRod());
        RenderingRegistry.registerBlockHandler(new GreekFireRenderer());
        RenderingRegistry.registerBlockHandler(new HoarFrostRenderer());
    }

    public void registerTileEnitiy() {} //NO OP
}
