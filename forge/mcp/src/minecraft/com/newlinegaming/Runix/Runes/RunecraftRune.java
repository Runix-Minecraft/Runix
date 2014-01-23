package com.newlinegaming.Runix.Runes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.newlinegaming.Runix.AbstractTimedRune;
import com.newlinegaming.Runix.NotEnoughRunicEnergyException;
import com.newlinegaming.Runix.PersistentRune;
import com.newlinegaming.Runix.RenderHelper;
import com.newlinegaming.Runix.Util_Movement;
import com.newlinegaming.Runix.WorldXYZ;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class RunecraftRune extends AbstractTimedRune {
    
    protected static ArrayList<PersistentRune> activeMagic = new ArrayList<PersistentRune>();
    public int tier = 1;
    private HashSet<WorldXYZ> vehicleBlocks = new HashSet<WorldXYZ>();
    private RenderHelper renderer;
    
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
        renderer = new RenderHelper();
        updateEveryXTicks(4); //TODO this line and the next are crashing the Event Bus on loadRunes().
        MinecraftForge.EVENT_BUS.register(this);
        this.runeName = "Runecraft"; 
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
    public void execute(WorldXYZ coords, EntityPlayer activator) {
        if(!activator.worldObj.isRemote)//server only
            super.execute(coords, activator);
    }

    @Override
    protected void onUpdateTick(EntityPlayer subject) {
        if(getPlayer() != null && !subject.equals(getPlayer()) )
            return;
        if(getPlayer() != null && energy < 100){
            reportOutOfGas(getPlayer());
            setPlayer(null);
        }
        if(getPlayer() != null){//Josiah: turns out running this on server and client side causes strange duplications
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
                if( !shapeCollides(move) ){
                    try {
                        vehicleBlocks = moveShape(move);
                    } catch (NotEnoughRunicEnergyException e) {
                        reportOutOfGas(getPlayer());
                        setPlayer(null);
                    }
                }
                else{
                    aetherSay(getPlayer(), "CRUNCH!");
                }
            }
        }
    }

    @ForgeSubscribe
    public void renderWireframe(RenderWorldLastEvent evt) {
        if(getPlayer() != null)
            renderer.highlightBoxes(vehicleBlocks, getPlayer());
    }
    
    @ForgeSubscribe
    public void playerInteractEvent(PlayerInteractEvent event) {
        if (getPlayer() != null && event.action == Action.LEFT_CLICK_BLOCK)
            if( event.isCancelable() ){
                WorldXYZ punchBlock = new WorldXYZ(event.entity.worldObj, event.x, event.y, event.z);
                if( vehicleBlocks.contains( punchBlock ))
                    if( location.getDistanceSquaredToChunkCoordinates(punchBlock) < 3 ){//distance may need adjusting
                        boolean counterClockwise = !Util_Movement.lookingRightOfCenterBlock(getPlayer(), location);
                        HashMap<WorldXYZ, WorldXYZ> move = Util_Movement.xzRotation(vehicleBlocks, location, counterClockwise);
                        if( !shapeCollides(move) )
                            vehicleBlocks = Util_Movement.performMove(move);
                    }
                    event.setCanceled(true); //build protect
                    System.out.println("Runecraft protected");
            }
    }
    
    @Override
    protected void poke(EntityPlayer poker, WorldXYZ coords) {
        consumeKeyBlock(coords);
        if(getPlayer() != null){
            setPlayer(null);
            aetherSay(poker, "You are now free from the Runecraft.");
            return;
        }
        setPlayer(poker); // assign a player and start
        aetherSay(poker, "The Runecraft is now locked to your body.");
        HashSet<WorldXYZ> oldVehicleShape = vehicleBlocks;
        if( scanForVehicleShape() )
            return;
        else
            vehicleBlocks = rescanBlocks(oldVehicleShape);
    }

    private HashSet<WorldXYZ> rescanBlocks(HashSet<WorldXYZ> oldVehicleShape) {
        for(WorldXYZ xyz : oldVehicleShape){
            if(xyz.getBlockId() == 0) // We specifically want to exclude AIR to avoid confusing collisions
                oldVehicleShape.remove(xyz);
        }
        return oldVehicleShape;
    }

    protected boolean scanForVehicleShape() {
        tier = getTier(location) ;
        vehicleBlocks = conductanceStep(location, tier);
        renderer.reset();
        if(vehicleBlocks.isEmpty()){
            aetherSay(getPlayer(), "You hear blocks rumble and crack as the Rune strains to pick up more than it can carry.");
            return false;   
        }
        else{
            aetherSay(getPlayer(), "Found " + vehicleBlocks.size() + " tier blocks");
            return true;
        }
    }
    
//    @Override
//    public void loadRunes() {
//        //don't load anything
//    }

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
