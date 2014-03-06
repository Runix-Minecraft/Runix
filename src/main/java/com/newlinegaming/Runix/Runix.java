package com.newlinegaming.Runix;

//Imports

import com.newlinegaming.Runix.block.ModBlock;
import com.newlinegaming.Runix.fluids.ModFluid;
import com.newlinegaming.Runix.item.ModItem;
import com.newlinegaming.Runix.lib.LibRef;
import com.newlinegaming.Runix.network.PacketPipeline;
import com.newlinegaming.Runix.proxys.CommonProxy;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;

import com.newlinegaming.Runix.creativetabs.TabRunix;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;

//Client and Server
@Mod(modid = LibRef.MOD_ID, name = LibRef.MOD_NAME, version = LibRef.MOD_VERSION)
public class Runix {

    @Instance
    public static Runix instance;

    @SidedProxy(clientSide = LibRef.CLIENT_PROXY, serverSide = LibRef.COMMON_PROXY)
    public static CommonProxy proxy;

    //Creative Tab Names
    public static CreativeTabs TabRunix = new TabRunix(CreativeTabs.getNextID(), "Runix");

    public static final PacketPipeline packetPipeline = new PacketPipeline();


    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModBlock.init();
        ModFluid.init();
        ModItem.init();

    }

    //Render Information	
    @EventHandler
    public void init(FMLInitializationEvent event) {
        packetPipeline.initialise();
//        proxy.registerRenderInformation();
    }

    @EventHandler
    public void postinit(FMLPostInitializationEvent event) {
        packetPipeline.postInitialise();
    }

    //Registry's
//    public Runix() {
//
//        Tiers tiers = new Tiers(); //load the list of block tiers
//
//        //Event Registry
//        MinecraftForge.EVENT_BUS.register(RuneHandler.getInstance());
//    }
}
