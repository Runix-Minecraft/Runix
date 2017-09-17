package com.newlinegaming.Runix.rune;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumActionResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import com.newlinegaming.Runix.PersistentRune;
import com.newlinegaming.Runix.Vector3;
import com.newlinegaming.Runix.WorldXYZ;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**Elevator that ferries player up and down based on open spaces.  Doesn't consume energy.**/
public class ElevatorRune extends PersistentRune {

    private static final ArrayList<PersistentRune> otherElevators = new ArrayList<>();
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
        while (destination.getY() < 255 && !destination.isSolid())
        {
            destination = destination.offset(Vector3.facing[facing]);
        }

        aetherSay(poker, "Teleporting to " + destination.toString());
        teleportPlayer(poker, destination);
    }
    
    @SubscribeEvent
    public void bottomPoked(PlayerInteractEvent event)
    {
        if (event.getCancellationResult() == EnumActionResult.PASS)
        {
            WorldXYZ punchBlock = new WorldXYZ(event.getWorld(), event.getPos());
            
            if (punchBlock.equals(bottomLocation))
            {
                poke(event.getEntityPlayer(), punchBlock);
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
        Block air = Blocks.AIR;
        Block torc = Blocks.TORCH;
        Block iron = Blocks.IRON_BLOCK;
        Block ster = Blocks.STONE_BRICK_STAIRS;
        Block glas = Blocks.STAINED_GLASS;
        Block step = Blocks.STONE_SLAB; //need a way to handle stone slab2
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
