package com.newlinegaming.Runix.Runes;

import com.newlinegaming.Runix.AbstractRune;
import com.newlinegaming.Runix.Vector3;
import com.newlinegaming.Runix.WorldXYZ;
import com.newlinegaming.Runix.block.GreekFire;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class GreekFireRune extends AbstractRune {
    
    public GreekFireRune(){
        runeName = "Greek Fire";
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    protected int[][][] runicTemplateOriginal() {
        int FENC = Block.fenceIron.blockID;
        int LAPS = Block.blockLapis.blockID;
        return new int [][][] 
                {{{TIER,FENC,TIER},
                  {FENC,LAPS,FENC},
                  {TIER,FENC,TIER}}}; 
    }
    
    @ForgeSubscribe
    public void onBlockPlace(PlayerInteractEvent event) {
        if(!event.entityPlayer.worldObj.isRemote){
            if (event.action == Action.RIGHT_CLICK_BLOCK && event.action != Action.RIGHT_CLICK_AIR){
                WorldXYZ target = new WorldXYZ(event.entityPlayer.worldObj, event.x, event.y, event.z);
                target = target.offset(Vector3.facing[event.face]);
                if(target.getBlockId() == GreekFire.blockIdBackup){
                    ItemStack blockUsed = event.entityPlayer.getCurrentEquippedItem();
                    if(blockUsed.itemID < 255){//vanilla block
                        if(GreekFire.consumeValuableForFuel(target, blockUsed.itemID))
                            event.setCanceled(true);
                    }
                }
            }
        }
    }

    @Override
    public boolean isFlatRuneOnly() {
        return true;
    }

    @Override
    public void execute(WorldXYZ coords, EntityPlayer player) {
        accept(player);
        consumeRune(coords);
        coords.setBlockIdAndUpdate(Block.blockLapis.blockID);
        coords.offset(Vector3.UP).setBlock(GreekFire.blockIdBackup, 14);//GreekFire.blockID
        //TODO set meta on Fire block for remaining energy from the Rune
    }

}
