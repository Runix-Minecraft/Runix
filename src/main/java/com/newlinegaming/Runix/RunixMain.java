package com.newlinegaming.Runix;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;

//import com.newlinegaming.Runix.block.ModBlock;
//import com.newlinegaming.Runix.creativetabs.TabRunix;
//import com.newlinegaming.Runix.fluids.ModFluid;
//import com.newlinegaming.Runix.handlers.ConfigurationHandler;
//import com.newlinegaming.Runix.handlers.RuneHandler;
//import com.newlinegaming.Runix.item.ModItem;
//import com.newlinegaming.Runix.lib.LibInfo;
//import com.newlinegaming.Runix.proxys.CommonProxy;


import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = RunixMain.MODID, version = RunixMain.VERSION)
public class RunixMain {
    public static final String MODID = "Runix";
    public static final String VERSION = "1.0";

    @EventHandler
    public void init(FMLInitializationEvent event) {
        System.out.println("Loaded RUNIX");
        System.out.println("DIRT BLOCK >> "+Blocks.DIRT.getUnlocalizedName());
    }
}
/**
    @Instance
    public static RunixMain instance;

    @SidedProxy(clientSide = LibInfo.CLIENT_PROXY, serverSide = LibInfo.COMMON_PROXY)
    private static CommonProxy proxy;

    public static final CreativeTabs TabRunix = new TabRunix(LibInfo.MOD_ID + ":runix");

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
//        ConfigurationHandler.init(new File(event.getModConfigurationDirectory().getAbsolutePath() + File.separator + LibInfo.MOD_NAME + ".cfg"));
        ModBlock.init();
        ModFluid.init();
        ModItem.init();
    }
    
    @EventHandler
    public void load(FMLInitializationEvent event) {
        
        proxy.registerRenderInformation();
        proxy.registerTileEnitiy();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event){
        Tiers tiers = new Tiers(); //load the list of block tiers
        tiers.initializeEnergyRegistry();
        MinecraftForge.EVENT_BUS.register(RuneHandler.getInstance());
    }
    
    @EventHandler
    public void serverStop(FMLServerStoppingEvent event){
    System.out.println("Clearing all runes");
        for(AbstractRune r : RuneHandler.getInstance().runeRegistry)
            if( r instanceof PersistentRune)
                ((PersistentRune) r).clearActiveMagic();
    }
}
*/