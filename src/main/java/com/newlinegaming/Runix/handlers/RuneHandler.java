package com.newlinegaming.Runix.handlers;


import com.newlinegaming.Runix.AbstractRune;
import com.newlinegaming.Runix.PersistentRune;
import com.newlinegaming.Runix.Vector3;
import com.newlinegaming.Runix.WorldXYZ;
import com.newlinegaming.Runix.helper.LogHelper;
import com.newlinegaming.Runix.helper.TierHelper;
import com.newlinegaming.Runix.lib.LibConfig;
import com.newlinegaming.Runix.rune.*;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent.Load;
import net.minecraftforge.event.world.WorldEvent.Save;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;

//import com.newlinegaming.Runix.rune.RubricRune;


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
    private static RuneHandler instance = null;//Singleton pattern
    public final ArrayList<AbstractRune> runeRegistry = new ArrayList<>();//TODO move to forges registries
    
    private RuneHandler() {
        //TODO: Make a wrappper class for adding runes something alone the lines of RuneHandler.addRune(RuneFooRune), or add it to a Runix  


//        runeRegistry.add(new PlayerHandler());
        runeRegistry.add(new WaypointRune());
        runeRegistry.add(new FaithRune());
        runeRegistry.add(new CompassRune());
        runeRegistry.add(new FtpRune());
        runeRegistry.add(new TeleporterRune());
        runeRegistry.add(new RunecraftRune()); //FIXME: Make Runecraft runes respect TileEntity Inventories 
//        runeRegistry.add(new RubricRune());
        runeRegistry.add(new TorchBearerRune());
        runeRegistry.add(new ZeerixChestRune());
        runeRegistry.add(new FerrousWheelRune());
        runeRegistry.add(new OracleRune());
//        runeRegistry.add(new GreekFireRune());
//        runeRegistry.add(new HoarFrostRune());
//        runeRegistry.add(new DomainRune());
//        runeRegistry.add(new LightBeamRune());
        runeRegistry.add(new ElevatorRune());
        runeRegistry.add(new BuildMasterRune());
    }

    public static RuneHandler getInstance(){
        if(instance == null)
            instance = new RuneHandler();
        return instance;
    }

//    @SubscribeEvent
//    public void playerInteractEvent(PlayerInteractEvent event) {
//        if(event.getWorld().isRemote)//runes server side only
//            return;
//        //Note: I've noticed that torch RIGHT_CLICK when you can't place a torch only show up client side, not server side
//        if (!event.entityPlayer.worldObj.isRemote && event.action == Action.RIGHT_CLICK_BLOCK && event.action != Action.RIGHT_CLICK_AIR){
//            possibleRuneActivationEvent(event.entityPlayer,
//                    new WorldXYZ(event.entityPlayer.worldObj, event.x, event.y, event.z, event.face));
//        }
//    }

    @SubscribeEvent
    public void playerInteractEvent(PlayerInteractEvent.RightClickBlock e) {
        Block blk = e.getWorld().getBlockState(e.getPos()).getBlock();

        if (!e.getWorld().isRemote) {
            //TODO remove when done
            e.getEntityPlayer().sendMessage(new TextComponentString("Energy is " + TierHelper.getEnergy(blk)));

            if (blk != Blocks.AIR) {

                possibleRuneActivationEvent(e.getEntityPlayer(), new WorldXYZ(e.getWorld(), e.getPos()));
            }
        }
    }

    @SubscribeEvent
    public void saving(Save saveEvent){
        if( saveEvent.getWorld().provider.getDimension() == 0 && !saveEvent.getWorld().isRemote)//Josiah: I figure it's likely there's only one of these
            for(AbstractRune r : runeRegistry)
                if( r instanceof PersistentRune)
                    ((PersistentRune) r).saveActiveRunes(saveEvent);
    }

    @SubscribeEvent
    public void loadServer(Load loadEvent){
        if( loadEvent.getWorld().provider.getDimension() == 0 && !loadEvent.getWorld().isRemote)
            for(AbstractRune r : runeRegistry)
                if( r instanceof PersistentRune)
                    ((PersistentRune) r).loadRunes(loadEvent);
    }

    @SubscribeEvent
    public void playerLogin(EntityJoinWorldEvent event){
        if(event.getEntity() instanceof EntityPlayer){ //fires once each for Client and Server side join event
            for(AbstractRune r : runeRegistry)
                if( r instanceof PersistentRune)
                    ((PersistentRune)r).onPlayerLogin(((EntityPlayer) event.getEntity()).getDisplayNameString());
        }

    }

    /**
     * Detects a rune pattern, and executes it.
     */
    @SuppressWarnings("static-access")
    private void possibleRuneActivationEvent(EntityPlayer player, WorldXYZ coords) {
        Pair<AbstractRune, Vector3> matchingRuneInfo = checkForAnyRunePattern(coords);
        //TODO: check for Activator Rail in hand and subscribe the rune to minecart events
        if (matchingRuneInfo != null) {
            AbstractRune matchingRune = matchingRuneInfo.getLeft();
            String direction;
            if(matchingRune.isAssymetrical())
                direction = Vector3.faceString[Arrays.asList(Vector3.facing).indexOf(matchingRuneInfo.getRight())];
            else 
                direction = Vector3.faceString[coords.face];
            matchingRune.aetherSay(player, "The Aether sees you activating a " + TextFormatting.GREEN +
                    matchingRune.getRuneName() + TextFormatting.WHITE + " facing "+
                    direction + " at " + coords.getX() + "," + coords.getY() + "," + coords.getZ() + "." );
            
            LogHelper.info(player.getDisplayName() + " Has activated a " + matchingRune.getRuneName() + "" );
            matchingRune.execute(coords, player, matchingRuneInfo.getRight());
        }
    }

    /**
     * This is the main switch board between all of the runes.  It iterates through all runes in the order that
     * they are registered and asks if each one matches the pattern of blocks at the coordinates.
     * @param coords location of the right click
     * @return AbstractRune class if there is a match, null otherwise
     */
    private Pair<AbstractRune, Vector3> checkForAnyRunePattern(WorldXYZ coords) {
        for (AbstractRune aRuneRegistry : runeRegistry) {
            WorldXYZ result = aRuneRegistry.checkRunePattern(new WorldXYZ(coords));
            if (result != null) {
                Vector3 forward = Vector3.facing[result.face];//result can contain facing information for assymetrical runes
                return new MutablePair<>(aRuneRegistry, forward);
            }
        }
        return null;
    }

    public void moveMagic(HashMap<WorldXYZ, WorldXYZ> positionsMoved){
        for(AbstractRune rune : runeRegistry){
            rune.moveMagic(positionsMoved);
        }
    }
    
    /**
     * This is modeled after conductanceStep() but on a macro level.
     * Recursive chaining of rune structures is now working.  You can FTP a 
     * Runecraft that is touching a Faith block and the whole island will be treated and moved as one structure.
     * param authority
     */
    public LinkedHashSet<WorldXYZ> chainAttachedStructures(LinkedHashSet<WorldXYZ> structure, AbstractRune originator) {
        LinkedHashSet<WorldXYZ> activeEdge;
        LinkedHashSet<WorldXYZ> nextEdge = new LinkedHashSet<>(structure);//starts off being a copy of structure

        while(!nextEdge.isEmpty() && structure.size() < LibConfig.runixMaximumStructureSize()) {
            activeEdge = nextEdge;
            nextEdge = new LinkedHashSet<>();

            for (AbstractRune rune : runeRegistry) {
                if (rune instanceof PersistentRune) {
                    // pass in and side-effect the collection
                    for (PersistentRune pRune : ((PersistentRune) rune).getActiveMagic()) {
                        //TODO: Does the authority concept still make sense now that we're using conductance on everything?
                        if (activeEdge.contains(pRune.location)) {
                            if ((originator.authority() == 0 || originator.authority() > pRune.authority()) && originator != pRune) {
                                // FaithRune is the only authority user at the moment
                                //Add Faith Island Structure IN ORDER
                                for(WorldXYZ pt : pRune.fullStructure()){
                                    if(!structure.contains(pt)){// we only want new blocks
                                        nextEdge.add(pt);
                                        structure.add(pt);
                                    }
                                }
                            } else if (pRune instanceof FaithRune && originator != pRune) { //obviously don't block yourself
                                // ensure Faith Anchor stays where it is, even if other blocks are moved
                                structure.remove(pRune.location);
                                activeEdge.remove(pRune.location);
                            }
                        }
                    }
                }
            }
        }

        if(nextEdge.size() != 0)//tear detection: this should be empty by the last step
            System.err.println("RunixMain exceeded maximum structure chaining size: " + structure.size() + " blocks.");
        return structure;
    }

    // TODO   public JSON extractMagic(Collection<WorldXYZ> blocks)

    public ArrayList<PersistentRune> getAllRunesByPlayer(EntityPlayer player){
        ArrayList<PersistentRune> playerRunes = new ArrayList<>();
        for(AbstractRune r : runeRegistry)
            if( r instanceof PersistentRune) {
                //TODO change getRuneByPlayer to return list when oneRunePerPerson = false.
                PersistentRune rune = ((PersistentRune) r).getRuneByPlayer(player);
                if(rune != null)
                    playerRunes.add(rune);
            }
        return playerRunes;
    }

}