package com.newlinegaming.Runix;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

import org.lwjgl.opengl.GL11;

public class RunecraftRune extends AbstractTimedRune {
    
    public static ArrayList<RunecraftRune> activeVehicles = new ArrayList<RunecraftRune>();
    public WorldXYZ location = null;
    public EntityPlayer driver = null;
    public int tier = 1;
    private HashMap<WorldXYZ, SigBlock> vehicleBlocks;
    private RenderHelper renderer;
    
    public RunecraftRune(){}
    
    /**Runecraft Runix Vehicle is now working and tracking with player while active.  
     * Toggle it by right clicking the center block.  You can jump up to travel up, just not down yet.
     * @param coords Center rune block that the vehicle is checked from 
     * @param player Person that the vehicle gloms on to
     */
    public RunecraftRune(WorldXYZ coords, EntityPlayer player)
    {
        location = new WorldXYZ(coords);
        driver = player;
        renderer = new RenderHelper();
        scanForVehicleShape(coords, player);
        updateEveryXTicks(4);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public int[][][] blockPattern() {
        int IRON = Block.blockIron.blockID;
        return new int[][][]
                {{{TIER,IRON,TIER},
                  
                  {IRON,KEY ,IRON},
                  
                  {TIER,IRON,TIER}}};
    }

    @Override
    public void execute(EntityPlayer player, WorldXYZ coords) {
        accept(player);
        if(!player.worldObj.isRemote){
            if( addOrToggleVehicle(coords, player) )
                aetherSay(player, "The Runecraft is now locked to your body.");
            else
                aetherSay(player, "You are now free from the Runecraft.");
        }
    }

    @Override
    protected void onUpdateTick(EntityPlayer subject) {
        //TODO: we're not currently using subject
        if(driver != null && !driver.worldObj.isRemote)
        {//Josiah: turns out running this on server and client side causes strange duplications
            int dX = (int) (driver.posX - location.posX - .5);
            int dY = (int) (driver.posY - location.posY - 1);
            int dZ = (int) (driver.posZ - location.posZ - .5);
            if( 10 < location.getDistanceSquared((int)driver.posX, (int)driver.posY, (int)driver.posZ) ){
                driver = null; //Vehicle has been abandoned
                return; //Vehicle should stop moving until someone is at the wheel again
            }
            if(driver.isSneaking())
                dY -= 1;
            if(dX != 0 || dY != 0 || dZ != 0){
                if( !shapeCollides(vehicleBlocks, dX, dY, dZ)){
                    vehicleBlocks = moveShape(vehicleBlocks, dX, dY, dZ);
                    location = location.offset(dX, dY, dZ);
                }
                else{
                    aetherSay(driver, "CRUNCH!");
//                    safelyMovePlayer(driver, location.offset(0, 1, 0));//TODO: not working because it's server side only
                }
            }
        }
    }

    @ForgeSubscribe
    public void renderWireframe(RenderWorldLastEvent evt){
        if(driver != null )
            renderer.highlightBoxes(vehicleBlocks.keySet(), driver);
    }
    
    @ForgeSubscribe
    public void playerInteractEvent(PlayerInteractEvent event) {
        if (driver != null && event.action == Action.LEFT_CLICK_BLOCK)
            if( event.isCancelable() ){
                WorldXYZ punchBlock = new WorldXYZ(event.entity.worldObj, event.x, event.y, event.z);
                if( vehicleBlocks.containsKey( punchBlock ))
                    if( location.getDistanceSquaredToChunkCoordinates(punchBlock) < 3 ){//distance may need adjusting
                        if(!location.worldObj.isRemote){  //server side only
                            boolean counterClockwise = !lookingRightOfCenterBlock(driver, location);
                            vehicleBlocks = rotateShape(vehicleBlocks, location, counterClockwise);
                        }
                    }
                    event.setCanceled(true); //build protect
                    System.out.println("Runecraft protected");
            }
    }

    /**Geometry: figure out if we're on the left or right side of the rune relative to the player
     */
    protected boolean lookingRightOfCenterBlock(EntityPlayer player, WorldXYZ referencePoint) {
        float yaw = player.rotationYawHead;//assumption: you're looking at the block you right clicked
        yaw = (yaw > 0.0) ? yaw  : yaw + 360.0F; //Josiah: minecraft yaw wanders into negatives sometimes...
        double opposite = player.posZ - referencePoint.posZ - .5;
        double adjacent = player.posX - referencePoint.posX - .5;
        double angle = Math.toDegrees(Math.atan( opposite / adjacent )) + 90.0;
        if( adjacent > 0.0)
            angle += 180.0;
//        System.out.println("Rune: " + angle + "  Yaw: " + yaw + " = " + (angle - yaw));
        if( ((angle - yaw) < 180.0 && (angle - yaw) > 0.0) || //the difference between the angle to the reference
                ((angle - yaw) < -180.0 && (angle - yaw) > -360.0) )//and the angle we're looking determines left/right
            return true;
        else
            return false;
    }
    
    /** This method exists to ensure that no duplicate vehicles are persisted. 
     * NOTE: This is an odd method to program for because it is a different instance of Runecraft
     * that is doing something on behalf of the subject Runecraft.  Be very careful to not
     * change class variable, but always call oldRCV.variable.*/
    public boolean addOrToggleVehicle(WorldXYZ centerPoint, EntityPlayer player) {
        for(RunecraftRune oldRCV : activeVehicles){
            if( oldRCV.driver != null && oldRCV.driver.equals(player)){//currently active Rune, to be turned off
                oldRCV.driver = null; //turn off the vehicle
                return false;
            }
        }
        for(RunecraftRune oldRCV : activeVehicles){
            if( oldRCV.location.equals( centerPoint ) )// if it exists already, toggle state
            {
                if(oldRCV.driver == null){ // not currently active
                    oldRCV.driver = player; // assign a driver and start
                    HashMap<WorldXYZ, SigBlock> oldVehicleShape = oldRCV.vehicleBlocks;
                    if( oldRCV.scanForVehicleShape(centerPoint, player) )
                        return true;
                    else{
                        oldRCV.vehicleBlocks = rescanBlocks(oldVehicleShape);
                        return true;
                    }
                }
                else{ //there's already a driver, but it's not the current player
                    aetherSay(player, "Stop messing with someone else's ride.");
                    return false;
                }
            }
        }
        activeVehicles.add(new RunecraftRune(centerPoint, player));
        return true;
    }
    
    private HashMap<WorldXYZ, SigBlock> rescanBlocks(HashMap<WorldXYZ, SigBlock> oldVehicleShape) {
        HashMap<WorldXYZ, SigBlock> newVehicle = new HashMap<WorldXYZ, SigBlock>();
        for(WorldXYZ xyz : oldVehicleShape.keySet()){
            SigBlock block = xyz.getSigBlock();
            if(block.blockID != 0) // We specifically want to exclude AIR to avoid confusing collisions
                newVehicle.put(xyz, block);
        }
        return newVehicle;
    }

    protected boolean scanForVehicleShape(WorldXYZ coords, EntityPlayer player) {
        tier = Tiers.getTier( coords.offset(-1, 0, -1).getBlockId() );
        vehicleBlocks = conductanceStep(coords, (int)Math.pow(2, tier+1));
        renderer.reset();
        if(vehicleBlocks.isEmpty()){
            aetherSay(player, "You hear blocks rumble and crack as the Rune strains to pick up more than it can carry.");
            return false;   
        }
        else{
            aetherSay(player, "Found " + vehicleBlocks.size() + " tier blocks");
            return true;
        }
    }
    
    public String getRuneName() {
        return "Runecraft";
    }
}
