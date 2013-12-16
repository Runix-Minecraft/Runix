package com.newlinegaming.Runix;

/** Josiah: I'm just writing some notes down as code.  This hasn't been tested yet. */

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class RuneHandler {
    private ArrayList<AbstractRune> runeRegistry = new ArrayList<AbstractRune>();
    public ArrayList<WaypointRune> waypoints = new ArrayList<WaypointRune>();
    public ArrayList<AbstractRune> activeRunes = new ArrayList<AbstractRune>();

    public RuneHandler() {
        runeRegistry.add(new WaypointRune());
        runeRegistry.add(new FaithRune());
        runeRegistry.add(new CompassRune());
        runeRegistry.add(new TeleporterRune());
    }

    @ForgeSubscribe
    public void playerInteractEvent(PlayerInteractEvent event) {
        if (event.action == Action.RIGHT_CLICK_BLOCK)
            possibleRuneActivationEvent(event.entityPlayer, event.x, event.y, event.z);
    }

    public void aetherSay(String msg) {
        /** TODO: The double messages can be fixed by using a proper Proxy
         * (client side only, I think) See:
         * https://github.com/denoflionsx/GateCopy
         * /blob/master/src/denoflionsx/GateCopy/Proxy/ProxyClient.java
         * Runix.clientSide?
         */
        Minecraft.getMinecraft().thePlayer.addChatMessage(msg);
    }

    /**Detects a rune pattern, executes it, and stores persistent runes.*/
    public void possibleRuneActivationEvent(EntityPlayer player, int worldX, int worldY, int worldZ) {
        AbstractRune createdRune = checkForAnyRunePattern(player.worldObj, worldX, worldY, worldZ);
        if (createdRune != null) {
            aetherSay("Recognized" + createdRune.getClass().getName());
            createdRune.execute(this, player, worldX, worldY, worldZ);//if isPersistent, this will add itself to activeRunes or waypoints
        }
    }

    /** This method exists to ensure that no duplicate waypoints are persisted. */
    public void addWaypoint(WaypointRune wp) {
        for(WaypointRune oldWP : waypoints){
            if( oldWP.x == wp.x && oldWP.y == wp.y && oldWP.z == wp.z )
                return; //ensure there are no duplicates
        }
        waypoints.add(wp);
    }

    private AbstractRune checkForAnyRunePattern(World world, int worldX, int worldY, int worldZ) {
        boolean result = false;
        for (int i = 0; i < runeRegistry.size(); i++) {
            int[][][] blockPattern = runeRegistry.get(i).blockPattern();
            if (blockPattern == null) {
                System.err.println(runeRegistry.get(i).getClass().getName() + " failed to set a blockPattern in their constructor.");
                continue;
            }
            result = checkRunePattern(blockPattern, world, worldX, worldY, worldZ);
            if (result) {
                return runeRegistry.get(i);
            }
        }
        return null;
    }

    private boolean checkRunePattern(int[][][] blockPattern, World world, int worldX, int worldY, int worldZ) {
        for (int y = 0; y < blockPattern.length; y++) {
            for (int z = 0; z < blockPattern[y].length; z++) {
                for (int x = 0; x < blockPattern[y][z].length; x++) {
                    // World coordinates + relative offset + half the size of the rune (for middle)
                    int blockX = worldX - blockPattern[y][z].length / 2 + x;
                    int blockY = worldY - y; // Josiah: the activation and "center" block for 3D runes is the top layer, at the moment
                    int blockZ = worldZ - blockPattern[y].length / 2 + z;
                    if (world.getBlockId(blockX, blockY, blockZ) != blockPattern[y][z][x])
                        return false;
                        // aetherSay("Found " + world.getBlockId(blockX, blockY, blockZ) + " expected " + blockPattern[y][z][x]);
                }
            }
        }
        return true;
    }
}