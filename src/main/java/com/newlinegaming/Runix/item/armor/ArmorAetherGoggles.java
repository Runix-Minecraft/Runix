package com.newlinegaming.Runix.item.armor;
/*
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;

import com.newlinegaming.Runix.RunixMain;
import com.newlinegaming.Runix.lib.RunixAsset;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

class ArmorAetherGoggles extends ItemArmor implements ISpecialArmor {

    public ArmorAetherGoggles() {
        super(RunixMain.armorRunix, 0 ,0);
        this.setUnlocalizedName("runix:aethergoggles");
        this.setCreativeTab(RunixMain.TabRunix);

    }

//    @Override
//    public void onCreated(ItemStack is, World world, EntityPlayer player) {
//        is.stackTagCompound = new NBTTagCompound();
//        is.stackTagCompound.setLong(S, p_74772_2_);
//        is.stackTagCompound.setBoolean("activated", false);
//    }

//    @Override
//    public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player) {
//        if (is.stackTagCompound != null) {
//            String master = is.stackTagCompound.getString("master");
//            boolean activated = is.stackTagCompound.getBoolean("activated");
//            if (master == player.getDisplayName() && activated == false) {
//                is.stackTagCompound.setBoolean("activated", true);
//                player.addChatComponentMessage(new ChatComponentText("The Aether grants your request"));
//            } else if (master == player.getDisplayName() && activated == true) {
//                is.stackTagCompound.setBoolean("activated", false);
//                player.addChatComponentMessage(new ChatComponentText( "The Aether grants your request"));
//            } else {
//                player.addChatComponentMessage(new ChatComponentText( "The Aether rejects your greedy attempt to activate these goggles. Please return to the rightful owner" + " " + master));
//            }
//        }
//        return is;
//
//    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getArmorTexture(ItemStack is, Entity entity, int slot, String type) {
        return RunixAsset.AETHERGOGGLES;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        itemIcon =  reg.registerIcon(RunixAsset.ICON_AETHERGOGGLES);
    }

    @Override
    public EnumRarity getRarity(ItemStack is) {
        return EnumRarity.rare;

    }

//    @SuppressWarnings({ "rawtypes", "unchecked" })
//    @Override
//    @SideOnly(Side.CLIENT)
//    public void addInformation(ItemStack is, EntityPlayer player, List list, boolean par4) {
//        if (is.stackTagCompound !=null) {
//            String master = is.stackTagCompound.getString("master");
//            boolean activated = is.stackTagCompound.getBoolean("activated");
//            list.add(StatCollector.translateToLocal(EnumChatFormatting.DARK_PURPLE + "This tool is Bound to: " + EnumChatFormatting.DARK_AQUA + master));
//            if (activated == true) {
//                list.add(StatCollector.translateToLocal(EnumChatFormatting.DARK_PURPLE + "Is attuned: " + EnumChatFormatting.DARK_AQUA + "True"));
//            } else if (master == null) {
//                list.add(StatCollector.translateToLocal(EnumChatFormatting.RED + "Not attuned"));
//            } else {
//                list.add(StatCollector.translateToLocal(EnumChatFormatting.DARK_PURPLE + "Is attuned: " + EnumChatFormatting.DARK_AQUA + "False"));
//            }
//        }
//
//    }

    @Override
    public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
        return null;
    }

    @Override
    public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
        return 0;
    }

    @Override
    public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {

    }

}*/