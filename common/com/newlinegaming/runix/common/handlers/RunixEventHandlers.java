package com.newlinegaming.runix.common.handlers;

import net.minecraftforge.common.MinecraftForge;

import com.newlinegaming.runix.common.fluids.ModFluid;
import com.newlinegaming.runix.common.handlers.mics.BucketEventHandler;

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
