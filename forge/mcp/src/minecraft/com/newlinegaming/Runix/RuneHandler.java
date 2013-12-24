package com.newlinegaming.Runix;

/** Josiah: I'm just writing some notes down as code.  This hasn't been tested yet. */

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class RuneHandler {
    private ArrayList<AbstractRune> runeRegistry = new ArrayList<AbstractRune>();

    public RuneHandler() {      
        runeRegistry.add(new WaypointRune());
        runeRegistry.add(new FaithRune());
        runeRegistry.add(new CompassRune());
        runeRegistry.add(new TeleporterRune());
        runeRegistry.add(new RunecraftRune());
        runeRegistry.add(new RubricCreationRune());
        runeRegistry.add(new RubricRecallRune());
    }

    @ForgeSubscribe
    public void playerInteractEvent(PlayerInteractEvent event) {
        if (event.action == Action.RIGHT_CLICK_BLOCK)
            possibleRuneActivationEvent(event.entityPlayer, 
                    new WorldXYZ(event.entityPlayer.worldObj, event.x, event.y, event.z));
    }

    /**Detects a rune pattern, and executes it.*/
    public void possibleRuneActivationEvent(EntityPlayer player, WorldXYZ coords) {
        AbstractRune createdRune = checkForAnyRunePattern(coords);
        if (createdRune != null) {
            createdRune.aetherSay(player, "The Aether sees you activating a " + EnumChatFormatting.GREEN + createdRune.getRuneName() + EnumChatFormatting.WHITE + " at " + coords.posX + "," + coords.posY + "," + coords.posZ + "." );
            createdRune.execute(player, coords);//if isPersistent, this will add itself to activeRunes or waypoints
        }
    }

    /**This is the main switch board between all of the runes.  It iterates through all Runes in the order that
     * they are registered and asks if each one matches the pattern of blocks at the coordinates.
     * @param coords
     * @return AbstractRune class if there is a match, null otherwise
     */
    private AbstractRune checkForAnyRunePattern(WorldXYZ coords) {
        boolean result = false;
        for (int i = 0; i < runeRegistry.size(); i++) {
            result = runeRegistry.get(i).checkRunePattern(coords);
            if (result) {
                return runeRegistry.get(i);
            }
        }
        return null;
    }

}