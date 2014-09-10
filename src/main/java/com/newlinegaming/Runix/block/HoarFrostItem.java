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
        "origin", "creeping",  "origin2", "crystallized", "exploding", 
        "pink", "lightGrey", "cyan", "purple", "blue", "brown", "fuschia", "green", "orange", 
        "blooming", "melting"
    };
    
    @Override
    public int getMetadata (int damageValue) {
        return damageValue;
    }
    
    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        return subNames[itemstack.getItemDamage()] + "." + getUnlocalizedName();
    }

}
