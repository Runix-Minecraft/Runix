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
    public static TiersVanilla tiers;
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
    	AbstractRune.aetherSay(event.entityPlayer, event.action.toString() + " event created by FORGE");
        if (event.action == Action.RIGHT_CLICK_BLOCK)
            possibleRuneActivationEvent(event.entityPlayer, 
                    new WorldCoordinates(event.entityPlayer.worldObj, event.x, event.y, event.z));
    }

    /**Detects a rune pattern, executes it, and stores persistent runes.*/
    public void possibleRuneActivationEvent(EntityPlayer player, WorldCoordinates coords) {
    	
        AbstractRune createdRune = checkForAnyRunePattern(coords);
        if (createdRune != null) {
            createdRune.aetherSay(player, "Recognized " + createdRune.getRuneName() + " activated at " + coords.posX + "," + coords.posY + "," + coords.posZ );
            createdRune.execute(player, coords);//if isPersistent, this will add itself to activeRunes or waypoints
        }
    }

    /**This is the main switch board between all of the runes.  It iterates through all Runes in the order that
     * they are registered and asks if each one matches the pattern of blocks at the coordinates.
     * @param coords
     * @return AbstractRune class if there is a match, null otherwise
     */
    private AbstractRune checkForAnyRunePattern(WorldCoordinates coords) {
        boolean result = false;
        for (int i = 0; i < runeRegistry.size(); i++) {
            result = runeRegistry.get(i).checkRunePattern(coords);
            if (result) {// Josiah: this seems redundant, should we just return a populated Rune object instead?
                return runeRegistry.get(i);
            }
        }
        return null;
    }

}