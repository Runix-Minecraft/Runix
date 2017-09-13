package com.newlinegaming.Runix.rune;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import com.newlinegaming.Runix.Tiers;
import com.newlinegaming.Runix.WorldXYZ;
import com.newlinegaming.Runix.block.ModBlock;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class HoarFrostRune extends GreekFireRune {

    public HoarFrostRune(){
        runeName = "Hoar Frost";
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public Block[][][] runicTemplateOriginal() {
        Block ICE = Blocks.packed_ice;
        return new Block[][][] 
                {{{TIER,ICE ,TIER},
                  {ICE ,FUEL,ICE },
                  {TIER,ICE ,TIER}}};
    }

    @Override
    public void execute(WorldXYZ coords, EntityPlayer player) {
        consumeRune(coords);
        int dropsNumber = (energy / Tiers.blockBreakCost) / 251; // iron pickaxe has 251 uses
        EntityItem drop = new EntityItem(coords.getWorld(), coords.getX(), coords.getY()+1, coords.getZ(), new ItemStack(ModBlock.hoar_frost, dropsNumber, 0));
        coords.getWorld().spawnEntityInWorld(drop);
    }

    @SubscribeEvent
    public void onBlockPlace(PlayerInteractEvent event) {

    }
}
