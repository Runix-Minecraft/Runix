package com.newlinegaming.Runix;

//Imports

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

import com.newlinegaming.Runix.block.GreekFire;
import com.newlinegaming.Runix.block.QixsilverFlowing;
import com.newlinegaming.Runix.block.QixsilverStill;
import com.newlinegaming.Runix.creativetabs.TabRunix;
import com.newlinegaming.Runix.placeholder.RunixPlaceHolder;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;


//Client and Server
@Mod(modid = "Runix", name = "Runix", version = "0.1 Alpha")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)

public class Runix {

    @SidedProxy(clientSide = "com.newlinegaming.Runix.ClientProxy", serverSide = "com.newlinegaming.Runix.CommonProxy")
    public static CommonProxy proxy;

    //Creative Tab Names
    public static CreativeTabs TabRunix = new TabRunix(CreativeTabs.getNextID(), "Runix");
    public static Item RunixPlaceHolder = new RunixPlaceHolder(10000).setUnlocalizedName("RunixPlaceHolder");
    public static Object instance;

    //Block Names
    public static Block QixsilverFlowing = new QixsilverFlowing(2012).setUnlocalizedName("QixsilverFlowing");
    public static Block QixsilverStill = new QixsilverStill(2013).setUnlocalizedName("QixsilverStill");
    public static Block GreekFire = new GreekFire(2014).setUnlocalizedName("GreekFire");

    //Render Information	
    @Init
    public void load(FMLInitializationEvent event) {
        proxy.registerRenderInformation();
    }

    //Registry's
    public Runix() 
    {	
        //Game Registry
        GameRegistry.registerBlock(QixsilverFlowing, "QixsilverFlowing");
        GameRegistry.registerBlock(QixsilverStill, "QixsilverStill");
        GameRegistry.registerBlock(GreekFire, "GreekFire");
        GreekFire.setCreativeTab(Runix.TabRunix);


        //Language Registry
        LanguageRegistry.addName(RunixPlaceHolder, "Runix");
        LanguageRegistry.addName(QixsilverFlowing, "Qixsilver");
        LanguageRegistry.addName(QixsilverStill, "Qixsilver");
        LanguageRegistry.addName(GreekFire, "Greek Fire");
        LanguageRegistry.addName(new ItemStack(GreekFire, 1, 14), "Old Greek Fire");//metadata
        Tiers tiers = new Tiers(); //load the list of block tiers

        
        
        //Event Registry
        MinecraftForge.EVENT_BUS.register(RuneHandler.getInstance());
    }
}
