package com.newlinegaming.Runix;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class RunixPlayer { //TODO: extends EntityPlayer

    private final EntityPlayer entity;

    public RunixPlayer(EntityPlayer obj){
        entity = obj;
    }
    /**
     * returns inventory of this player as RuneInventory *
     */
    ItemStack[] getInventory(){
        return getPlayer().inventory.mainInventory;
    }

    public EntityPlayer getPlayer() {
        return entity;
    }

    public boolean isPlayer() {
        return true;
    }
}
