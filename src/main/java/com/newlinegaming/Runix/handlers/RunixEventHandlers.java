package com.newlinegaming.Runix.handlers;

import net.minecraftforge.common.MinecraftForge;

import com.newlinegaming.Runix.fluids.ModFluid;
import com.newlinegaming.Runix.handlers.mics.BucketHandler;

public class RunixEventHandlers {

	public static void init() {
		
		registerMicsHandlers();
		
	}

	private static void registerMicsHandlers() {
		
		BucketHandler.INSTANCE.buckets.put(ModFluid.BlockQuickSilver, ModFluid.qsBucket);
		MinecraftForge.EVENT_BUS.register(BucketHandler.INSTANCE);
	}

}
