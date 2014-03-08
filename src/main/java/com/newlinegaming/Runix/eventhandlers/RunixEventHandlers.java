package com.newlinegaming.Runix.eventhandlers;

import net.minecraftforge.common.MinecraftForge;

import com.newlinegaming.Runix.RuneHandler;
import com.newlinegaming.Runix.eventhandlers.mics.BucketEventHandler;
import com.newlinegaming.Runix.fluids.ModFluid;

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
