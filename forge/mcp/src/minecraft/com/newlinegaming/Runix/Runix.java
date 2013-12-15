package com.newlinegaming.Runix;

	//Imports

import com.newlinegaming.Runix.creativetabs.TabRunix;
import com.newlinegaming.Runix.placeholder.RunixPlaceHolder;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.network.packet.Packet3Chat;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.LanguageRegistry;


	//Client and Server

	@Mod(modid = "Runix", name = "Runix", version = "0.1 Alpha")
	@NetworkMod(clientSideRequired = true, serverSideRequired = false)

	public class Runix {
	
	@SidedProxy(clientSide = "com.newlinegaming.Runix.ClientProxy", serverSide = "com.newlinegaming.Runix.CommonProxy")
	public static CommonProxy proxy;
	
	//Creative Tab Names
	public static CreativeTabs TabRunix = new TabRunix(CreativeTabs.getNextID(), "Runix");
	public static Item RunixPlaceHolder = new RunixPlaceHolder(2000).setUnlocalizedName("RunixPlaceHolder");
	public static Object instance;

	//Render Information	
	@Init
	public void load(FMLInitializationEvent event) {
		proxy.registerRenderInformation();
	}
	
	//Registry's
	public Runix() 
	{	
		//Language Registry
		LanguageRegistry.addName(RunixPlaceHolder, "Runix");
			
		MinecraftForge.EVENT_BUS.register(new Compass());	//Event Registry
		
	
	}
}
