package com.newlinegaming.Runix.rune;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

import com.newlinegaming.Runix.NotEnoughRunicEnergyException;
import com.newlinegaming.Runix.PersistentRune;
import com.newlinegaming.Runix.Vector3;
import com.newlinegaming.Runix.WorldXYZ;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;


/**Elevator that ferries player up and down based on open spaces.  Doesn't consume energy.**/
public class ElevatorRune extends PersistentRune {

    private static ArrayList<PersistentRune> otherElevators = new ArrayList<>();
    private WorldXYZ bottomLocation = null;
    
    public ElevatorRune(){
        super();
        runeName = "Elevator";
    }
    
    public ElevatorRune(WorldXYZ coords, EntityPlayer activator){
        super(coords, activator, "Elevator");
        energy = 1;
    }
    
    /**initializeRune() is necessary because of a circular condition in the event registry
     * that does not play well with the GSON object constructor loading from loadRunes()
     */
    private void initializeRune(WorldXYZ coords) {
        bottomLocation = coords.offset(0, -4, 0);
        MinecraftForge.EVENT_BUS.register(this);        
    }
    
    /**
     * Teleport the player up or down
     */
    @Override
    protected void poke(EntityPlayer poker, WorldXYZ coords) {
        if (bottomLocation == null)
        {
            initializeRune(coords);
        }
        
        energy = 1000;
        int facing = Vector3.oppositeSide[coords.face];
        WorldXYZ destination = coords.offset(Vector3.facing[facing]).copyWithNewFacing(facing);
        while (destination.posY < 255 && !destination.isSolid())
        {
            destination = destination.offset(Vector3.facing[facing]);
        }

        if(destination != null){
            aetherSay(poker, "Teleporting to " + destination.toString());
            teleportPlayer(poker, destination);
        }
    }
    
    @SubscribeEvent
    public void bottomPoked(PlayerInteractEvent event)
    {
        if (event.action == Action.RIGHT_CLICK_BLOCK)
        {
            WorldXYZ punchBlock = new WorldXYZ(event.entity.worldObj, event.x, event.y, event.z);
            
            if (punchBlock.equals(bottomLocation))
            {
                poke(event.entityPlayer, punchBlock);
            }
        }
    }

    @Override
    public ArrayList<PersistentRune> getActiveMagic() {
        return otherElevators;
    }

    @Override
    public boolean oneRunePerPerson() {
        return false;
    }

    @Override
    protected Block[][][] runicTemplateOriginal() {
        Block air = Blocks.air;
        Block torc = Blocks.torch;
        Block iron = Blocks.iron_block;
        Block ster = Blocks.stone_brick_stairs;
        Block glas = Blocks.stained_glass;
        Block step = Blocks.stone_slab;
        return new Block[][][]
                {
                {{NONE,NONE,NONE,NONE,NONE}, 
                 {NONE,iron,iron,iron,NONE},
                 {NONE,iron,glas,iron,NONE}, 
                 {NONE,iron,iron,iron,NONE},
                 {NONE,NONE,NONE,NONE,NONE}},
                 
                {{NONE ,NONE,NONE,NONE,NONE}, 
                 {TIER,torc,air, torc,TIER}, 
                 {TIER,air ,air, air ,TIER}, 
                 {TIER,air ,air, air ,TIER}, 
                 {TIER,TIER,TIER,TIER,TIER}},
                 
                {{NONE ,NONE,NONE,NONE,NONE}, 
                 {TIER,air ,air, air, TIER}, 
                 {TIER,air ,air, air ,TIER}, 
                 {TIER,air ,air, air ,TIER}, 
                 {TIER,TIER,TIER,TIER,TIER}},
                 
                {{NONE ,NONE,NONE,NONE,NONE}, 
                 {TIER,air ,air, air, TIER}, 
                 {TIER,air ,air, air ,TIER}, 
                 {TIER,air ,air, air ,TIER}, 
                 {TIER,TIER,TIER,TIER,TIER}},
                 
                {{NONE ,NONE,NONE,NONE,NONE}, 
                 {TIER,ster,step,ster,TIER}, 
                 {TIER,ster,glas,ster,TIER}, 
                 {TIER,ster,ster,ster,TIER}, 
                 {TIER,TIER,TIER,TIER,TIER}},
                };
    }

    @Override
    public boolean isFlatRuneOnly() {
        return true;
    }

    
    
}
