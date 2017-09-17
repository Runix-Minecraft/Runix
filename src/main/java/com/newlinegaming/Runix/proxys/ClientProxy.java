package com.newlinegaming.Runix.proxys;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {


    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
    }

    //    @Override
//    public void registerRenderInformation() {
//	    ISimpleBlockRenderingHandler handler = null;
//	    RenderingRegistry.registerBlockHandler(handler);
//        ClientRegistry.bindTileEntitySpecialRenderer(TileLightBeam.class, new RenderTileLightBeam());
//        MinecraftForgeClient.registerItemRenderer(ModItem.transRod, new ItemRenderTransmutaionRod());
//        RenderingRegistry.registerBlockHandler(new GreekFireRenderer());
//        RenderingRegistry.registerBlockHandler(new HoarFrostRenderer());
//    }
//
//    public void registerTileEnitiy() {} //NO OP
}
