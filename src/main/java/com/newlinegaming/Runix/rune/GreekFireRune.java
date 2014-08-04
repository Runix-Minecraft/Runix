package com.newlinegaming.Runix.rune;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

import com.newlinegaming.Runix.AbstractRune;
import com.newlinegaming.Runix.Vector3;
import com.newlinegaming.Runix.WorldXYZ;
import com.newlinegaming.Runix.block.GreekFire;
import com.newlinegaming.Runix.block.ModBlock;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class GreekFireRune extends AbstractRune {
    
    public GreekFireRune(){
        runeName = "Greek Fire";
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public Block[][][] runicTemplateOriginal() {
        Block FENC = Blocks.iron_bars;
        Block LAPS = Blocks.lapis_block;
        return new Block[][][] 
                {{{TIER,FENC,TIER},
                  {FENC,LAPS,FENC},
                  {TIER,FENC,TIER}}}; 
    }

    @SubscribeEvent
    public void onBlockPlace(PlayerInteractEvent event) {
        if(!event.entityPlayer.worldObj.isRemote){
            if (event.action == Action.RIGHT_CLICK_BLOCK && event.action != Action.RIGHT_CLICK_AIR){
                WorldXYZ target = new WorldXYZ(event.entityPlayer.worldObj, event.x, event.y, event.z);
                target = target.offset(Vector3.facing[event.face]);
                if(target.getBlock().equals(ModBlock.greekFire)){
                    ItemStack blockUsed = event.entityPlayer.getCurrentEquippedItem();
                    Block block = Block.getBlockFromItem(blockUsed.getItem());
                    if(GreekFire.consumeValuableForFuel(target, block)){
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
        coords.setBlockIdAndUpdate(Blocks.lapis_block);
        coords.offset(Vector3.UP).setBlock(ModBlock.greekFire, 14);//GreekFire.blockID
        //TODO set meta on Fire block for remaining energy from the Rune
    }

}
