package com.newlinegaming.Runix;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.world.WorldEvent.Save;

/**
 * RuneHandler is the main switchboard between all Runes. It contains
 * runeRegistry, which is the list of all runes to scan for matches and be
 * executed. It is a singleton and so RuneHandler.getInsance() is a good way to
 * jump back to a global context.
 * 
 * It should not contain any code specific to a single rune. Runes that depend
 * on each other such as Teleporter and Waypoint should use each other's static
 * activeMagic list instead of going through RuneHandler. Generic open-ended
 * interaction such as moveMagic() are handled through RuneHandler, since there
 * is no telling how many runes it could affect.
 */
public class RuneHandler {
    private static RuneHandler instance = null;//Singleton pattern
    int nClicks = 0;
    
    private ArrayList<AbstractRune> runeRegistry = new ArrayList<AbstractRune>();
    
    private RuneHandler() {      
        runeRegistry.add(new WaypointRune());
        runeRegistry.add(new FaithRune());
        runeRegistry.add(new CompassRune());
        runeRegistry.add(new TeleporterRune());
        runeRegistry.add(new RunecraftRune());
        runeRegistry.add(new RubricCreationRune());
        runeRegistry.add(new RubricRecallRune());
        runeRegistry.add(new TorchBearerRune());
        runeRegistry.add(new ZeerixChestRune());
        runeRegistry.add(new FerrousWheelRune());
    }
    
    public static RuneHandler getInstance(){
        if(instance == null)
            instance = new RuneHandler();
        return instance;
    }

    @ForgeSubscribe
    public void playerInteractEvent(PlayerInteractEvent event) {
        //Note: I've noticed that torch RIGHT_CLICK when you can't place a torch only show up client side, not server side
        if (event.action == Action.RIGHT_CLICK_BLOCK && event.action != Action.RIGHT_CLICK_AIR){
//            WorldXYZ pos = new WorldXYZ(event.x, event.y, event.z);
//            System.out.println("Right Click event " + pos + "Facing" + event.face + " clicks: "+ ++nClicks);
//            System.out.println("u: "+ event.useBlock + " I: " + event.useItem);
            possibleRuneActivationEvent(event.entityPlayer, 
                    new WorldXYZ(event.entityPlayer.worldObj, event.x, event.y, event.z, event.face));
        }
    }

    @ForgeSubscribe
    public void saving(Save s){
        for(AbstractRune r : runeRegistry)
            if( r instanceof PersistentRune)
                ((PersistentRune) r).saveActiveRunes();
    }
    
    @ForgeSubscribe
    public void loadServer(){}
    
    @ForgeSubscribe
    public void playerLogin(EntityJoinWorldEvent event){
        if(event.entity instanceof EntityPlayer){ //fires once each for Client and Server side join event
            for(AbstractRune r : runeRegistry)
                if( r instanceof PersistentRune)
                    ((PersistentRune) r).onPlayerLogin(((EntityPlayer)event.entity).username);
        }
            
    }
    
    
    /**Detects a rune pattern, and executes it.*/
    public void possibleRuneActivationEvent(EntityPlayer player, WorldXYZ coords) {
        AbstractRune createdRune = checkForAnyRunePattern(coords);
        //TODO: check for Activator Rail in hand and subscribe the rune to minecart events
        if (createdRune != null) {
            createdRune.aetherSay(player, "The Aether sees you activating a " + EnumChatFormatting.GREEN + 
                    createdRune.getRuneName() + EnumChatFormatting.WHITE + " facing "+
                    Vector3.faceString[coords.face] + " at " + coords.posX + "," + coords.posY + "," + coords.posZ + "." );
            createdRune.execute(coords, player);
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
            result = runeRegistry.get(i).checkRunePattern(new WorldXYZ(coords));
            if (result) {
                return runeRegistry.get(i);
            }
        }
        return null;
    }
    
    public void moveMagic(HashMap<WorldXYZ, WorldXYZ> positionsMoved){
        for(AbstractRune rune : runeRegistry){
            rune.moveMagic(positionsMoved);
        }
    }
//    public JSON extractMagic(Collection<WorldXYZ> blocks)
    
}