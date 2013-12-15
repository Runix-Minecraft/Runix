package com.newlinegaming.Runix;

/** Josiah: I'm just writing some notes down as code.  This hasn't been tested yet. */

import net.minecraft.network.packet.Packet3Chat;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;


public class Compass// extends Block //extend Rune
{
//    public Compass(int id, Material material) {
//		super(id, material);
//		this.setCreativeTab(Runix.TabRunix);
//	}

	int[][][] blockPattern = new int [][][] 
            {{{4,0,4},
              {0,4,0},
              {4,0,4}}};

	@ForgeSubscribe
	public void playerInteractEvent(PlayerInteractEvent event)
	{
		if(event.action == Action.RIGHT_CLICK_BLOCK)
			possibleRuneActivationEvent(event.entity.worldObj, event.x, event.y, event.z);
	}
	
    public void aetherSay(String msg) {
//		Runix.proxy.printMessageToPlayer("Someone touched Something!");
		/** TODO: The double messages can be fixed by using a proper Proxy (client side only, I think)
		 * See: https://github.com/denoflionsx/GateCopy/blob/master/src/denoflionsx/GateCopy/Proxy/ProxyClient.java		 
		 * Runix.clientSide?  */
    	Minecraft.getMinecraft().thePlayer.addChatMessage(msg);
	}

    public void possibleRuneActivationEvent(World world, int worldX, int worldY, int worldZ) { //TODO: world should be type World
        boolean createdRune = checkForAnyRunePattern(world, worldX, worldY, worldZ);
        if(createdRune)
        	aetherSay("Rune Created");
    }

    private boolean checkForAnyRunePattern(World world, int worldX, int worldY, int worldZ)
    {
		for (int y = 0; y < blockPattern.length; y++) {
			for (int z = 0; z < blockPattern[y].length; z++) {
				for (int x = 0; x < blockPattern[y][z].length; x++) {
                    //World coordinates + relative offset + half the size of the rune (for middle)
					int blockX = worldX - blockPattern[y][z].length /2 + x;
					int blockY = worldY - blockPattern.length / 2 + y;
					int blockZ = worldZ - blockPattern[y].length /2 + z;
					 if (world.getBlockId(blockX, blockY, blockZ) != blockPattern[y][z][x]) {
						 aetherSay("Found" + world.getBlockId(blockX, blockY, blockZ) + " expected " + blockPattern[y][z][x]);
						 return false;
					 }
                }
            }
        }
        return true;//TODO: return true once non-empty checking is done
    }
}
