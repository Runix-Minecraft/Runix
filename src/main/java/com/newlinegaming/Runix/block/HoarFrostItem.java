package com.newlinegaming.Runix.block;


import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class HoarFrostItem extends ItemBlock {
    
    public HoarFrostItem(Block id) {
        super(id);
        setHasSubtypes(true);
        setUnlocalizedName("hoarFrost");
    }

    private final static String[] subNames = {
        "origin", "creep",  "origin2", "stasis", "origin3", "explosion",
        "pink", "shell", "lightGrey", "cyan", "purple", "blue", "brown",
        "green", "delete", "stasis"
    };
    
    @Override
    public int getMetadata (int damageValue) {
        return damageValue;
    }
    
    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        return getUnlocalizedName() + "." + subNames[itemstack.getItemDamage()];
    }

}
