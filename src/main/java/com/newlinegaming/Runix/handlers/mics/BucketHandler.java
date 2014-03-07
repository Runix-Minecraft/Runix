package com.newlinegaming.Runix.handlers.mics;

import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;

public class BucketHandler {
	
	public static BucketHandler INSTANCE = new BucketHandler();
	public Map<Block, Item> buckets = new HashMap<Block, Item>();
	
	private BucketHandler() {
	}
	
	@SubscribeEvent
	public void onBucketFill(FillBucketEvent event) {
		ItemStack result = fillCustomBucket(event.world, event.target);
	}

	private ItemStack fillCustomBucket(World world, MovingObjectPosition mop) {
		Block block = world.getBlock(mop.blockX, mop.blockY, mop.blockZ);
		
		Item bucket = Item.getItemFromBlock(block);
		
		if (bucket != null && world.getBlockMetadata(mop.blockX, mop.blockY, mop.blockZ) == 0) {
			world.setBlockToAir(mop.blockX, mop.blockY, mop.blockZ);
			return new ItemStack(bucket);
		} else {
			return null;
		}
		
	}

}
