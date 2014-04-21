package com.newlinegaming.Runix.common.handlers;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

//import com.newlinegaming.Runix.common.runes.AbstractRune;
//import com.newlinegaming.Runix.common.runes.PersistentRune;
import com.newlinegaming.Runix.common.utils.Vector3;
import com.newlinegaming.Runix.common.utils.WorldXYZ;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.world.WorldEvent.Load;
import net.minecraftforge.event.world.WorldEvent.Save;

//import com.newlinegaming.Runix.Runes.CompassRune;
//import com.newlinegaming.Runix.Runes.DomainRune;
//import com.newlinegaming.Runix.Runes.FaithRune;
//import com.newlinegaming.Runix.Runes.FerrousWheelRune;
//import com.newlinegaming.Runix.Runes.FtpRune;
//import com.newlinegaming.Runix.Runes.GreekFireRune;
//import com.newlinegaming.Runix.Runes.OracleRune;
//import com.newlinegaming.Runix.Runes.PlayerHandler;
//import com.newlinegaming.Runix.Runes.RubricCreationRune;
//import com.newlinegaming.Runix.Runes.RubricRecallRune;
//import com.newlinegaming.Runix.Runes.RunecraftRune;
//import com.newlinegaming.Runix.Runes.TeleporterRune;
//import com.newlinegaming.Runix.Runes.TorchBearerRune;
//import com.newlinegaming.Runix.Runes.WaypointRune;
//import com.newlinegaming.Runix.Runes.ZeerixChestRune;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * RuneHandler is the main switchboard between all runes. It contains
 * runeRegistry, which is the list of all runes to scan for matches and be
 * executed. It is a singleton and so RuneHandler.getInsance() is a good way to
 * jump back to a global context.
 * 
 * It should not contain any code specific to a single rune. runes that depend
 * on each other such as Teleporter and Waypoint should use each other's static
 * activeMagic list instead of going through RuneHandler. Generic open-ended
 * interaction such as moveMagic() are handled through RuneHandler, since there
 * is no telling how many runes it could affect.
 */
public class RuneHandler {
//    private static RuneHandler instance = null;//Singleton pattern
//    private ArrayList<AbstractRune> runeRegistry = new ArrayList<AbstractRune>();

    private RuneHandler() {
        //TODO: Change this to scan a folder instead.
//        runeRegistry.add(new PlayerHandler());
//        runeRegistry.add(new WaypointRune());
//        runeRegistry.add(new FaithRune());
//        runeRegistry.add(new CompassRune());
//        runeRegistry.add(new FtpRune());
//        runeRegistry.add(new TeleporterRune());
//        runeRegistry.add(new RunecraftRune());
//        runeRegistry.add(new RubricCreationRune());
//        runeRegistry.add(new RubricRecallRune());
//        runeRegistry.add(new TorchBearerRune());
//        runeRegistry.add(new ZeerixChestRune());
//        runeRegistry.add(new FerrousWheelRune());
//        runeRegistry.add(new OracleRune());
//        runeRegistry.add(new GreekFireRune());
//        runeRegistry.add(new DomainRune());
    }

//    public static RuneHandler getInstance(){
//        if(instance == null)
//            instance = new RuneHandler();
//        return instance;
//    }

//    @SubscribeEvent
//    public void playerInteractEvent(PlayerInteractEvent event) {
//        //Note: I've noticed that torch RIGHT_CLICK when you can't place a torch only show up client side, not server side
//        if (!event.entityPlayer.worldObj.isRemote && event.action == Action.RIGHT_CLICK_BLOCK && event.action != Action.RIGHT_CLICK_AIR){
//            possibleRuneActivationEvent(event.entityPlayer, 
//                    new WorldXYZ(event.entityPlayer.worldObj, event.x, event.y, event.z, event.face));
//        }
//    }

//    @SubscribeEvent
//    public void saving(Save saveEvent){
//        if( saveEvent.world.provider.dimensionId == 0 && !saveEvent.world.isRemote)//Josiah: I figure it's likely there's only one of these
//            for(AbstractRune r : runeRegistry)
//                if( r instanceof PersistentRune)
//                    ((PersistentRune) r).saveActiveRunes(saveEvent);
//    }
//
//    @SubscribeEvent
//    public void loadServer(Load loadEvent){
//        if( loadEvent.world.provider.dimensionId == 0 && !loadEvent.world.isRemote)
//            for(AbstractRune r : runeRegistry)
//                if( r instanceof PersistentRune)
//                    ((PersistentRune) r).loadRunes(loadEvent);
//    }
//
//    @SubscribeEvent
//    public void playerLogin(EntityJoinWorldEvent event){
//        if(event.entity instanceof EntityPlayer){ //fires once each for Client and Server side join event
//            for(AbstractRune r : runeRegistry)
//                if( r instanceof PersistentRune)
//                    ((PersistentRune) r).onPlayerLogin(EntityPlayer.PERSISTED_NBT_TAG);
//        }
//
//    }
//
//
//    /**Detects a rune pattern, and executes it.*/
//    @SuppressWarnings("static-access")
//	public void possibleRuneActivationEvent(EntityPlayer player, WorldXYZ coords) {
//        AbstractRune createdRune = checkForAnyRunePattern(coords);
//        //TODO: check for Activator Rail in hand and subscribe the rune to minecart events
//        if (createdRune != null) {
//            createdRune.aetherSay(player, "The Aether sees you activating a " + EnumChatFormatting.GREEN + 
//                    createdRune.getRuneName() + EnumChatFormatting.WHITE + " facing "+
//                    Vector3.faceString[coords.face] + " at " + coords.posX + "," + coords.posY + "," + coords.posZ + "." );
//            createdRune.execute(coords, player);
//        }
//    }
//
//    /**This is the main switch board between all of the runes.  It iterates through all runes in the order that
//     * they are registered and asks if each one matches the pattern of blocks at the coordinates.
//     * @param coords
//     * @return AbstractRune class if there is a match, null otherwise
//     */
//    private AbstractRune checkForAnyRunePattern(WorldXYZ coords) {
//        boolean result = false;
//        for (int i = 0; i < runeRegistry.size(); i++) {
//            result = runeRegistry.get(i).checkRunePattern(new WorldXYZ(coords));
//            if (result) {
//                return runeRegistry.get(i);
//            }
//        }
//        return null;
//    }
//
//    public void moveMagic(HashMap<WorldXYZ, WorldXYZ> positionsMoved){
//        for(AbstractRune rune : runeRegistry){
//            rune.moveMagic(positionsMoved);
//        }
//    }
//    
//    /**This is modeled after conductanceStep() but on a macro level.
//     * Recursive chaining of rune structures is now working.  You can FTP a 
//     * Runecraft that is touching a Faith block and the whole island will be treated and moved as one structure. */
//    public HashSet<WorldXYZ> chainAttachedStructures(HashSet<WorldXYZ> structure)
//    {
//        HashSet<WorldXYZ> activeEdge = new HashSet<WorldXYZ>();
//        HashSet<WorldXYZ> nextEdge = new HashSet<WorldXYZ>(structure);//starts off being a copy of structure
//
//        while(!nextEdge.isEmpty() && structure.size() < 50000) 
//        {
//            activeEdge = nextEdge;
//            nextEdge = new HashSet<WorldXYZ>();
//
//            for(AbstractRune rune : runeRegistry)
//            {
//                if(rune instanceof PersistentRune)
//                {
//                    HashSet<WorldXYZ> additionalStructure = ((PersistentRune)rune).addYourStructure(activeEdge);
//                    structure.addAll(additionalStructure);
//                    nextEdge.addAll(additionalStructure);
//                }
//            }
//        }
//        
//        if(activeEdge.size() != 0)//tear detection: this should be empty by the last step 
//            System.err.println("Runix exceeded maximum structure chaining size: " + structure.size() + " blocks.");
//        return structure;
//    }
//
//    // TODO   public JSON extractMagic(Collection<WorldXYZ> blocks)
//
//    public ArrayList<PersistentRune> getAllRunesByPlayer(EntityPlayer player){
//        ArrayList<PersistentRune> playerRunes = new ArrayList<PersistentRune>();
//        for(AbstractRune r : runeRegistry)
//            if( r instanceof PersistentRune)
//            {
//                //TODO change getRuneByPlayer to return list when oneRunePerPerson = false.
//                PersistentRune rune = ((PersistentRune) r).getRuneByPlayer(player);
//                if(rune != null)
//                    playerRunes.add(rune);
//            }
//        return playerRunes;
//    }

}