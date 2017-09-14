package com.newlinegaming.Runix;

import java.io.File;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;

import com.newlinegaming.Runix.block.ModBlock;
import com.newlinegaming.Runix.creativetabs.TabRunix;
import com.newlinegaming.Runix.fluids.ModFluid;
import com.newlinegaming.Runix.handlers.ConfigurationHandler;
import com.newlinegaming.Runix.handlers.RuneHandler;
import com.newlinegaming.Runix.item.ModItem;
import com.newlinegaming.Runix.lib.LibInfo;
import com.newlinegaming.Runix.proxys.CommonProxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;

@Mod(modid = LibInfo.MOD_ID, name = LibInfo.MOD_NAME, version = LibInfo.MOD_VERSION)
public class RunixMain {

    //Tool and armor Materials
    public static final ArmorMaterial armorRunix = EnumHelper.addArmorMaterial("RUNEIUMARMOR", 30, new int[] { 4, 6, 6, 4 }, 25);
    public static ToolMaterial identifierToolMat = EnumHelper.addToolMaterial("RUNEIUMTOOL", 4, 650, 5, 4, 25);

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
