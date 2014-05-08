package com.newlinegaming.runix;

import com.newlinegaming.runix.common.CommonProxy;
import com.newlinegaming.runix.common.block.ModBlock;
import com.newlinegaming.runix.common.creativetabs.TabRunix;
import com.newlinegaming.runix.common.fluids.ModFluid;
import com.newlinegaming.runix.common.handlers.RunixEventHandlers;
import com.newlinegaming.runix.common.item.ModItem;
import com.newlinegaming.runix.common.lib.LibRef;
import com.newlinegaming.runix.common.network.PacketPipeline;

import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = LibRef.MOD_ID, name = LibRef.MOD_NAME, version = LibRef.MOD_VERSION, dependencies= "required-after:yssgaroth")
public class Runix {

    @Instance
    public static Runix instance;

    @SidedProxy(clientSide = LibRef.CLIENT_PROXY, serverSide = LibRef.COMMON_PROXY)
    public static CommonProxy proxy;

    //Creative Tab Names
    public static CreativeTabs TabRunix = new TabRunix(LibRef.MOD_ID + ":runix");

    public static final PacketPipeline packetPipeline = new PacketPipeline();


    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModBlock.init();
        ModFluid.init();
        ModItem.init();
        
        RunixEventHandlers.init();
        
//        EnergyManager.init();

    }

    //Render Information	
    @EventHandler
    public void init(FMLInitializationEvent event) {
        packetPipeline.initialise();
        proxy.registerRenderInformation();
    	
    	proxy.RenderingAndTextures();
    }

    @EventHandler
    public void postinit(FMLPostInitializationEvent event) {
        packetPipeline.postInitialise();
    }

}
