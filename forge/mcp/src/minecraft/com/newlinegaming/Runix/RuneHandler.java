package com.newlinegaming.Runix;

/** Josiah: I'm just writing some notes down as code.  This hasn't been tested yet. */

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class RuneHandler {
    public TiersVanilla tiers;
    private ArrayList<AbstractRune> runeRegistry = new ArrayList<AbstractRune>();
    public ArrayList<AbstractRune> activeRunes = new ArrayList<AbstractRune>();

    public RuneHandler() {      
        tiers = new TiersVanilla(); //load the list of block tiers
        runeRegistry.add(new WaypointRune());
        runeRegistry.add(new FaithRune());
        runeRegistry.add(new CompassRune());
        runeRegistry.add(new TeleporterRune());
    }

    @ForgeSubscribe
    public void playerInteractEvent(PlayerInteractEvent event) {
        if (event.action == Action.RIGHT_CLICK_BLOCK)
            possibleRuneActivationEvent(event.entityPlayer, 
                    new WorldCoordinates(event.entityPlayer.worldObj, event.x, event.y, event.z));
    }

    /**Detects a rune pattern, executes it, and stores persistent runes.*/
    public void possibleRuneActivationEvent(EntityPlayer player, WorldCoordinates coords) {
        AbstractRune createdRune = checkForAnyRunePattern(coords);
        if (createdRune != null) {
            Runix.proxy.aetherSay("Recognized " + createdRune.getClass().getName() + " activated by " + player.username + " at " + coords.posX + "," + coords.posY + "," + coords.posZ );
            createdRune.execute(player, coords);//if isPersistent, this will add itself to activeRunes or waypoints
        }
    }



    private AbstractRune checkForAnyRunePattern(WorldCoordinates coords) {
        boolean result = false;
        for (int i = 0; i < runeRegistry.size(); i++) {
            int[][][] blockPattern = runeRegistry.get(i).blockPattern();
            if (blockPattern == null) {
                System.err.println(runeRegistry.get(i).getClass().getName() + " failed to set a blockPattern in their constructor.");
                continue;
            }
            result = checkRunePattern(blockPattern, coords);
            if (result) {
                return runeRegistry.get(i);
            }
        }
        return null;
    }

    private boolean checkRunePattern(int[][][] blockPattern, WorldCoordinates coords) {
        for (int y = 0; y < blockPattern.length; y++) {
            for (int z = 0; z < blockPattern[y].length; z++) {
                for (int x = 0; x < blockPattern[y][z].length; x++) {
                    // World coordinates + relative offset + half the size of the rune (for middle)
                    int blockX = coords.posX - blockPattern[y][z].length / 2 + x;
                    int blockY = coords.posY - y; // Josiah: the activation and "center" block for 3D runes is the top layer, at the moment
                    int blockZ = coords.posZ - blockPattern[y].length / 2 + z;
                    int blockID = coords.worldObj.getBlockId(blockX, blockY, blockZ);
                    if( blockPattern[y][z][x] == AbstractRune.NONE ){
                        if( !tiers.isTier0(blockID) )
                            return false;
                    }
                    else if (blockID != blockPattern[y][z][x])
                        return false;
                        // aetherSay("Found " + world.getBlockId(blockX, blockY, blockZ) + " expected " + blockPattern[y][z][x]);
                }
            }
        }
        return true;
    }
}