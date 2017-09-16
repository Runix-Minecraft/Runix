package com.newlinegaming.Runix.proxys;

import com.newlinegaming.Runix.Tiers;
import com.newlinegaming.Runix.api.tier.ITier;
import com.newlinegaming.Runix.apiimpl.API;
import com.newlinegaming.Runix.handlers.RuneHandler;
import net.minecraft.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class CommonProxy {


    public void preInit(FMLPreInitializationEvent e) {
        API.bind(e.getAsmData());
        Tiers tier = new Tiers();
    }

    public void init(FMLInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(RuneHandler.getInstance());
    }

    public void postInit(FMLPostInitializationEvent e) {

    }


//    public void registerRenderInformation() {} //NO-OP
    
//    public void registerTileEnitiy() {
//        GameRegistry.registerTileEntity(TileLightBeam.class, "TileLightBeam");
//    }
}
