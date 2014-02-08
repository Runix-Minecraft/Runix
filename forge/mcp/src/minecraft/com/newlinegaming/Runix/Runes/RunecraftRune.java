package com.newlinegaming.Runix.Runes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import com.newlinegaming.Runix.AbstractTimedRune;
import com.newlinegaming.Runix.NotEnoughRunicEnergyException;
import com.newlinegaming.Runix.PersistentRune;
import com.newlinegaming.Runix.RenderHelper;
import com.newlinegaming.Runix.Util_Movement;
import com.newlinegaming.Runix.WorldXYZ;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class RunecraftRune extends AbstractTimedRune {
    
    protected static ArrayList<PersistentRune> activeMagic = new ArrayList<PersistentRune>();
    private HashSet<WorldXYZ> vehicleBlocks = new HashSet<WorldXYZ>();
    private transient RenderHelper renderer = null;
    private boolean moveInProgress = false;
    
    public RunecraftRune(){
        runeName = "Runecraft";
    }
    
    /**Runecraft Runix Vehicle blocks track with a player while active.  
     * Toggle it by right clicking the center block.  You can jump up to travel up, sneak to go down.
     * @param coords Center rune block that the vehicle is checked from 
     * @param player Person that the vehicle gloms on to
     */
    public RunecraftRune(WorldXYZ coords, EntityPlayer player2)
    {
        super(coords, player2, "Runecraft");
        setPlayer(null); //this is because poke() acts as if the Rune was activated a second time when it is first constructed
        this.runeName = "Runecraft"; 
    }

    /**initializeRune() is necessary because of a circular condition in the event registry
     * that does not play well with the GSON object constructor loading from loadRunes()
     */
    protected void initializeRune() {
        renderer = new RenderHelper();
        updateEveryXTicks(4);
        MinecraftForge.EVENT_BUS.register(this);        
    }

    @Override
    public int[][][] runicTemplateOriginal() {
        int GOLD = Block.oreGold.blockID;
        return new int[][][]
                {{{TIER,GOLD,TIER},
                  
                  {GOLD,KEY ,GOLD},
                  
                  {TIER,GOLD,TIER}}};
    }

    @Override
    protected void onUpdateTick(EntityPlayer subject) {
        if(moveInProgress ||(getPlayer() != null && !subject.equals(getPlayer())) )
            return;
        if(getPlayer() != null)
        {
            moveInProgress = true;
            int dX = (int) (getPlayer().posX - location.posX - .5);
            int dY = (int) (getPlayer().posY - location.posY - 1);
            int dZ = (int) (getPlayer().posZ - location.posZ - .5);
            if( 10.0 < location.getDistance(new WorldXYZ(getPlayer())) ){
                setPlayer(null); //Vehicle has been abandoned
                System.out.println("Runecraft has been abandoned.");
                return; //Vehicle should stop moving until someone is at the wheel again
            }
            if(getPlayer().isSneaking())
                dY -= 1;
            if(dX != 0 || dY != 0 || dZ != 0){
                HashMap<WorldXYZ, WorldXYZ> move = Util_Movement.displaceShape(vehicleBlocks,  dX, dY, dZ);
                if( !Util_Movement.shapeCollides(move) ){
                    vehicleBlocks = Util_Movement.performMove(move);//Josiah: it turns out that running out of gas isn't fun
                }
                else{
                    aetherSay(getPlayer(), "CRUNCH!");
                }
            }
            moveInProgress  = false;
        }
    }

    @SideOnly(Side.CLIENT)
    @ForgeSubscribe
    public void renderWireframe(RenderWorldLastEvent evt) {
        if(getPlayer() != null)
            if(!renderer.highlightBoxes(vehicleBlocks, disabled, getPlayer())){
                if(disabled)
                    setPlayer(null); // done with closing animation
            }
    }
    
    @ForgeSubscribe
    public void playerInteractEvent(PlayerInteractEvent event) {
        if (getPlayer() != null && event.action == Action.LEFT_CLICK_BLOCK)
            if( event.isCancelable() ){
                WorldXYZ punchBlock = new WorldXYZ(event.entity.worldObj, event.x, event.y, event.z);
                if( vehicleBlocks.contains( punchBlock ))
                {
                    if( location.getDistance(punchBlock) < 3 )
                    {
                        boolean counterClockwise = !Util_Movement.lookingRightOfCenterBlock(getPlayer(), location);
                        HashMap<WorldXYZ, WorldXYZ> move = Util_Movement.xzRotation(vehicleBlocks, location, counterClockwise);
                        if( !Util_Movement.shapeCollides(move) )
                            vehicleBlocks = Util_Movement.performMove(move);
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
        
        consumeKeyBlock(coords);
        if(getPlayer() != null){
            disabled = true; //player will not be set to null until the closing animation completes
            aetherSay(poker, "You are now free from the Runecraft.");
            return;
        }
        else{
            setPlayer(poker); // assign a player and start
            aetherSay(poker, "The Runecraft is now locked to your body.");
            HashSet<WorldXYZ> newVehicleShape = attachedStructureShape(poker);
            if( newVehicleShape.isEmpty() )
                vehicleBlocks = removeAirXYZ(vehicleBlocks);
            else
                vehicleBlocks = newVehicleShape;
            renderer.reset();
        }
    }

    /**Removes the coordinates of any air blocks from the shape Set.  This can break contiguous structures
     * and actually return a non-contiguous structure.  For Runecraft, this is desirable. */
    private HashSet<WorldXYZ> removeAirXYZ(HashSet<WorldXYZ> oldShapeCoords) {
        for (Iterator<WorldXYZ> i = oldShapeCoords.iterator(); i.hasNext();) 
        {
            WorldXYZ xyz = i.next(); //an iterator is necessary here because of ConcurrentModificationException
            if(xyz.getBlockId() == 0) // We specifically want to exclude AIR to avoid confusing collisions
                i.remove();
        }
            
        return oldShapeCoords;
    }

    /**Runecraft lives or dies by its player.  So the PersistentRune behavior needs to be augmented
     * with a 'disabled' switch.
     */
    public void setPlayer(EntityPlayer playerObj) { 
        super.setPlayer(playerObj);
        if(getPlayer() != null)
            disabled = false;
        else
            disabled = true; 
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
