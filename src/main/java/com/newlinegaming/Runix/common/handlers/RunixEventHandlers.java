package com.newlinegaming.Runix.common.handlers;

import net.minecraftforge.common.MinecraftForge;

import com.newlinegaming.Runix.common.fluids.ModFluid;
import com.newlinegaming.Runix.common.handlers.mics.BucketEventHandler;
//import com.newlinegaming.Runix.common.runes.RuneHandler;

public class RunixEventHandlers {

	public static void init() {
		
		registerMicsHandlers();
		registerRunehandlers();
		
	}

	private static void registerRunehandlers() {
//		MinecraftForge.EVENT_BUS.register(RuneHandler.getInstance());
		
	}

	private static void registerMicsHandlers() {
		
		BucketEventHandler.INSTANCE.buckets.put(ModFluid.BlockQuickSilver, ModFluid.qsBucket);
		MinecraftForge.EVENT_BUS.register(BucketEventHandler.INSTANCE);
	}

}
