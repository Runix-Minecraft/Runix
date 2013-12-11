package com.newlinegaming.Runix;

	//Imports

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "Runix", name = "Runix", version = "0.1 Alpha")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)


public class Runix {
	@SidedProxy(clientSide = "com.newlinegaming.Runix.ClientProxy", serverSide = "com.newlinegaming.Runix.CommonProxy")
	public static CommonProxy proxy;
}
