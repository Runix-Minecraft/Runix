package com.newlinegaming.Runix.block;


import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

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
        return getUnlocalizedName() + "." + subNames[itemstack.getItemDamage()];
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer player)
    {
        ModBlock.hoar_frost.owner = player;
//        float closest = Float.MAX_VALUE;
//        Entity thisOne = null;
//        for (int i = 0; i < par2World.loadedEntityList.size(); i++)
//        {
//            if (((Entity)par2World.loadedEntityList.get(i)).getDistanceToEntity(player)<closest)
//            {
//                if (par2World.loadedEntityList.get(i) instanceof EntityPlayer) //if it is a player
//                {
//                    closest = ((Entity)par2World.loadedEntityList.get(i)).getDistanceToEntity(player);
//                    thisOne = ((Entity)par2World.loadedEntityList.get(i));
//                }
//            }
//        }
//        if (thisOne!=null)
//        {
//            par2World.addWeatherEffect(new EntityLightningBolt(par2World,thisOne.posX,thisOne.posY, thisOne.posZ));
//        }
        return par1ItemStack;
    }
    
}
