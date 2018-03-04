package com.newlinegaming.Runix;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class RunixPlayer {

    private final EntityPlayer entity;

    public RunixPlayer(EntityPlayer obj){
        entity = obj;
    }

    EntityPlayer getEntity(){
        return entity;
    }
    /**
     * returns inventory of this player as RuneInventory *
     */
    ItemStack[] getInventory(){

        return getEntity().getInventory().mainInventory;
    }
}
