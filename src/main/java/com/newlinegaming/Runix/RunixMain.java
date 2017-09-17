package com.newlinegaming.Runix;

import com.newlinegaming.Runix.proxys.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
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