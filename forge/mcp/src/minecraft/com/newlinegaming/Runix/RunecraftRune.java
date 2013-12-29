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
    
    protected static ArrayList<PersistentRune> activeMagic = new ArrayList<PersistentRune>();
    public int tier = 1;
    private HashMap<WorldXYZ, SigBlock> vehicleBlocks;
    private RenderHelper renderer;
    
    public RunecraftRune(){}
    
    /**Runecraft Runix Vehicle blocks track with a player while active.  
     * Toggle it by right clicking the center block.  You can jump up to travel up, sneak to go down.
     * @param coords Center rune block that the vehicle is checked from 
     * @param player Person that the vehicle gloms on to
     */
    public RunecraftRune(WorldXYZ coords, EntityPlayer player2)
    {
        super(coords, player2);
        player = null; //this is because poke() acts as if the Rune was activated a second time when it is first constructed
        renderer = new RenderHelper();
        updateEveryXTicks(4);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public int[][][] blockPattern() {
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
        System.out.println("Runecraft array: " + getActiveMagic().size());
        //TODO: we're not currently using subject
        if(player != null && !player.worldObj.isRemote)
        {//Josiah: turns out running this on server and client side causes strange duplications
            int dX = (int) (player.posX - location.posX - .5);
            int dY = (int) (player.posY - location.posY - 1);
            int dZ = (int) (player.posZ - location.posZ - .5);
//            if( 10 < location.getDistanceSquared((int)player.posX, (int)player.posY, (int)player.posZ) ){
//                player = null; //Vehicle has been abandoned
//                return; //Vehicle should stop moving until someone is at the wheel again
//            }
            if(player.isSneaking())
                dY -= 1;
            if(dX != 0 || dY != 0 || dZ != 0){
                if( !shapeCollides(vehicleBlocks, dX, dY, dZ)){
                    vehicleBlocks = Util_Movement.moveShape(vehicleBlocks, dX, dY, dZ);
                    //location.bump(dX, dY, dZ);  //location gets moved through moveShape registry
                }
                else{
                    aetherSay(player, "CRUNCH!");
//                    safelyMovePlayer(player, location.offset(0, 1, 0));//TODO: not working because it's server side only
                }
            }
        }
    }

    @ForgeSubscribe
    public void renderWireframe(RenderWorldLastEvent evt){
        if(player != null )
            renderer.highlightBoxes(vehicleBlocks.keySet(), player);
    }
    
    @ForgeSubscribe
    public void playerInteractEvent(PlayerInteractEvent event) {
        if (player != null && event.action == Action.LEFT_CLICK_BLOCK)
            if( event.isCancelable() ){
                WorldXYZ punchBlock = new WorldXYZ(event.entity.worldObj, event.x, event.y, event.z);
                if( vehicleBlocks.containsKey( punchBlock ))
                    if( location.getDistanceSquaredToChunkCoordinates(punchBlock) < 3 ){//distance may need adjusting
                        if(!location.worldObj.isRemote){  //server side only
                            boolean counterClockwise = !Util_Movement.lookingRightOfCenterBlock(player, location);
                            HashMap<WorldXYZ, WorldXYZ> move = Util_Movement.xzRotation(vehicleBlocks.keySet(), location, counterClockwise);
                            if( !shapeCollides(move) )
                                vehicleBlocks = Util_Movement.rotateShape(move);
                        }
                    }
                    event.setCanceled(true); //build protect
                    System.out.println("Runecraft protected");
            }
    }
    
    @Override
    protected void poke(EntityPlayer poker, WorldXYZ coords) {
        if(poker.worldObj.isRemote)
            return;
        if(player != null){
            player = null;
            aetherSay(poker, "You are now free from the Runecraft.");
            return;
        }
        player = poker; // assign a player and start
        aetherSay(poker, "The Runecraft is now locked to your body.");
        HashMap<WorldXYZ, SigBlock> oldVehicleShape = vehicleBlocks;
        if( scanForVehicleShape() )
            return;
        else
            vehicleBlocks = rescanBlocks(oldVehicleShape);
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

    protected boolean scanForVehicleShape() {
        tier = getTier(location) ;
        vehicleBlocks = conductanceStep(location, (int)Math.pow(2, tier));
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

    @Override
    public void saveActiveRunes() {
        //TODO: actually do something here
    }

    @Override
    public ArrayList<PersistentRune> getActiveMagic() {
        return activeMagic;
    }
}
