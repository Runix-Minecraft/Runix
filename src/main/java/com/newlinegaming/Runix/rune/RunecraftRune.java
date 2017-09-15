package com.newlinegaming.Runix.rune;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.newlinegaming.Runix.utils.UtilMovement;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;

import com.newlinegaming.Runix.AbstractTimedRune;
import com.newlinegaming.Runix.PersistentRune;
import com.newlinegaming.Runix.WorldXYZ;
import com.newlinegaming.Runix.helper.LogHelper;
import com.newlinegaming.Runix.helper.RenderHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class RunecraftRune extends AbstractTimedRune {
    
    private static final ArrayList<PersistentRune> activeMagic = new ArrayList<>();
    private HashSet<WorldXYZ> vehicleBlocks = new HashSet<>();
    private transient RenderHelper renderer = null;
    private boolean moveInProgress = false;
    private boolean snaggedOnSomething = false;
    protected boolean buttonMode = true;
    public RunecraftRune(){
        runeName = "Runecraft";
    }
    
    /** Runecraft Runix Vehicle blocks track with a player while active.
     * Toggle it by right clicking the center block.  You can jump up to travel up, sneak to go down.
     * @param coords Center rune block that the vehicle is checked from 
     * @param player2 Person that the vehicle gloms on to
     */
    public RunecraftRune(WorldXYZ coords, EntityPlayer player2)
    {
        super(coords, player2, "Runecraft");
        setPlayer(null); //this is because poke() acts as if the Rune was activated a second time when it is first constructed
        this.runeName = "Runecraft"; 
        usesConductance = true;
    }

    /**initializeRune() is necessary because of a circular condition in the event registry
     * that does not play well with the GSON object constructor loading from loadRunes()
     */
    private void initializeRune() {
        renderer = new RenderHelper();
        updateEveryXTicks(4);
        MinecraftForge.EVENT_BUS.register(this);        
    }

    @Override
    public Block[][][] runicTemplateOriginal() {
        Block GOLD = Blocks.gold_ore;
        return new Block[][][]{{
            {TIER,GOLD,TIER},
            {GOLD,FUEL ,GOLD},
            {TIER,GOLD,TIER}

        }};
    }

    private WorldXYZ getDestinationByPlayer(EntityPlayer subject) {
        if(getPlayer() != null && subject.equals(getPlayer())) {
            int dX = (int) (getPlayer().getX() - location.getX() - .5);
            int dY = (int) (getPlayer().getY() - location.getY() - 1);
            int dZ = (int) (getPlayer().getZ() - location.getZ() - .5);
            if( 6.0 < location.getDistance(new WorldXYZ(getPlayer())) ){
                setPlayer(null); //Vehicle has been abandoned
                aetherSay(subject, "Runecraft has been abandoned.");
                return location;
            }
            else {
                if(getPlayer().isSneaking())
                    dY -= 1;
                return location.offset(dX, dY, dZ);
            }
        }
        return location;
    }
    
    @Override
    protected void onUpdateTick(EntityPlayer subject) {
        if(moveInProgress) {
            return;
        }
        if( checkRunePattern(location) != null) { //rune is still intact
            try{
                moveInProgress = true;
                WorldXYZ destination = getDestinationByPlayer(subject);
                if( !location.equals(destination) ){
                    HashMap<WorldXYZ, WorldXYZ> move = UtilMovement.displaceShape(vehicleBlocks,  location, destination);
                    if( !UtilMovement.shapeCollides(move) ){
                        snaggedOnSomething = false;
                        vehicleBlocks = UtilMovement.performMove(move);//Josiah: it turns out that running out of gas isn't fun
                    }
                    else{ //collision
                        if(!snaggedOnSomething) { //this is to avoid chat spam, it only says it once
                            aetherSay(getPlayer(), "Runecraft collision!");
                            snaggedOnSomething = true;
                        }
                    }
                }
                moveInProgress  = false;
            } catch (Throwable t){ //this is necessary because otherwise moveInProgress can get in an inconsistent state
                LogHelper.fatal("Runecraft failed.");
                LogHelper.fatal(t);
            }
        }else { //getPlayer() == null
            setPlayer(null); //clears the UUID and disables the rune
            disabled = true;
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void renderWireframe(RenderWorldLastEvent evt) {
        if(getPlayer() != null)
            if(!renderer.highlightBoxes(vehicleBlocks, disabled, getPlayer())){
                if(disabled)
                    setPlayer(null); // done with closing animation
            }
    }
    
    @SubscribeEvent
    public void event(BreakEvent b){
        //TODO: intercept event?
    }
    
    @SubscribeEvent
    public void playerInteractEvent(PlayerInteractEvent event) {
        if (getPlayer() != null && event.action == Action.LEFT_CLICK_BLOCK)
            if( event.isCancelable() ){
                WorldXYZ punchBlock = new WorldXYZ(event.entity.worldObj, event.x, event.y, event.z);
                if( vehicleBlocks.contains( punchBlock )) {
                    if( location.getDistance(punchBlock) < 3 ) {
                        boolean counterClockwise = !UtilMovement.lookingRightOfCenterBlock(getPlayer(), location);
                        HashMap<WorldXYZ, WorldXYZ> move = UtilMovement.xzRotation(vehicleBlocks, location, counterClockwise);
                        if( !UtilMovement.shapeCollides(move) )
                            vehicleBlocks = UtilMovement.performMove(move);
                    }
                    event.setCanceled(true); //build protect anything in vehicleBlocks
                    System.out.println("Runecraft protected");
                }
            }
    }
    
    @Override
    protected void poke(EntityPlayer poker, WorldXYZ coords) {
        if( renderer == null ) //initialization on the first time the rune is poked
            initializeRune();
        
        if(getPlayer() != null){
            setPlayer(null);  ///disabled = true; //player will not be set to null until the closing animation completes
            aetherSay(poker, "You are now free from the Runecraft.");
        }else{
            setPlayer(poker); // assign a player and start
            aetherSay(poker, "The Runecraft is now locked to your body.");
            usesConductance = true; //backwards compatibility
            HashSet<WorldXYZ> newVehicleShape = attachedStructureShape(poker);
            if( newVehicleShape.isEmpty() ) {
                vehicleBlocks = removeAirXYZ(vehicleBlocks);
            }else {
                vehicleBlocks = newVehicleShape;
                renderer.reset();
            }
        }
    }

    /**
     * Removes the coordinates of any air blocks from the shape Set.  This can break contiguous structures
     * and actually return a non-contiguous structure.  For Runecraft, this is desirable. 
     */
    private HashSet<WorldXYZ> removeAirXYZ(HashSet<WorldXYZ> oldShapeCoords) {
        //an iterator is necessary here because of ConcurrentModificationException
        // We specifically want to exclude AIR to avoid confusing collisions
        oldShapeCoords.removeIf(xyz -> xyz.getBlock() == Blocks.air);
            
        return oldShapeCoords;
    }

    /**
     * Runecraft lives or dies by its player.  So the PersistentRune behavior needs to be augmented
     * with a 'disabled' switch.
     */
    public void setPlayer(EntityPlayer playerObj) { 
        super.setPlayer(playerObj);
        disabled = getPlayer() == null;
    }
    
    @Override
    public ArrayList<PersistentRune> getActiveMagic() {
        return activeMagic;
    }

    @Override
    public boolean oneRunePerPerson() {
        return true;
    }

    public boolean isFlatRuneOnly() {
        return false;
    }
}
