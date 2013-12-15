package com.newlinegaming.Runix;

/** Josiah: I'm just writing some notes down as code.  This hasn't been tested yet. */

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;


public class RuneHandler
{
	private ArrayList<AbstractRune> runeRegistry = new ArrayList<AbstractRune>();//Josiah: I'm not sure ArrayList is the best, since we'll only have Runes
	
	public RuneHandler(){
		runeRegistry.add(new HoleRune());//Josiah: This is just a test Rune pattern
		runeRegistry.add(new CompassRune());
	}
	
	
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
    	boolean result = false;
    	for( int i = 0; i < runeRegistry.size(); i ++){
    		result = checkRunePattern(runeRegistry.get(i).blockPattern, world, worldX, worldY, worldZ);//TODO: Fetching from the ArrayList is giving me trouble.  
    		//((AbstractRune)runeRegistry.get(i))
    		if( result ){
    			aetherSay("Recognized" + runeRegistry.get(i).getClass().getName());
    			return true;  //Josiah: I know this is redundant, it will eventually return an AbstractRune object
    		}
    	}
    	return false;
    }

	private boolean checkRunePattern(int[][][] blockPattern, World world, int worldX, int worldY, int worldZ) {
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
        return true;//TODO: check for non-empty rune 
		
	}
}
