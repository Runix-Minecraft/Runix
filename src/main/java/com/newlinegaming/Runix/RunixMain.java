package com.newlinegaming.Runix;

import com.newlinegaming.Runix.api.RunixConstants;
import com.newlinegaming.Runix.apiimpl.API;
import com.newlinegaming.Runix.proxys.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Constants;
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
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = RunixMain.MODID, version = RunixMain.VERSION)
public class RunixMain {
    public static final String MODID = "runix";
    public static final String VERSION = "1.0";


    @Mod.Instance
    public static RunixMain instance;

    public final CreativeTabs tabs = new CreativeTabs("runix") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(Items.GOLDEN_AXE);
        }
    };

    @SidedProxy(clientSide = "com.newlinegaming.Runix.proxys.ClientProxy", serverSide = "com.newlinegaming.Runix.proxys.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        proxy.preInit(e);
    }

    @EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
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