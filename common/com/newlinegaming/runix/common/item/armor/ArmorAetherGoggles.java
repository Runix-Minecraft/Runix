package com.newlinegaming.runix.common.item.armor;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.newlinegaming.runix.Runix;
import com.newlinegaming.runix.api.RunixAPI;
import com.newlinegaming.runix.common.lib.RunixAsset;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ArmorAetherGoggles extends ItemArmor {

	public ArmorAetherGoggles() {
		super(RunixAPI.armorRunix, 0 ,0);
		this.setUnlocalizedName("runix:aethergoggles");
		this.setCreativeTab(Runix.TabRunix);

	}
	
	@Override
	public void onCreated(ItemStack is, World world, EntityPlayer player) {
		is.stackTagCompound = new NBTTagCompound();
		is.stackTagCompound.setString("master", player.getDisplayName());
		is.stackTagCompound.setBoolean("activated", false);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player) {
		if (is.stackTagCompound != null) {
			String master = is.stackTagCompound.getString("master");
			boolean activated = is.stackTagCompound.getBoolean("activated");
			if (master == player.getDisplayName() && activated == false) {
				is.stackTagCompound.setBoolean("activated", true);
				player.addChatComponentMessage(new ChatComponentText("The Aether grants your request"));
			} else if (master == player.getDisplayName() && activated == true) {
				is.stackTagCompound.setBoolean("activated", false);
				player.addChatComponentMessage(new ChatComponentText( "The Aether grants your request"));
			} else {
				player.addChatComponentMessage(new ChatComponentText( "The Aether rejects your greedy attempt to activate these goggles. Please return to the rightful owner" + " " + master));
			}
		}
		return is;
		
	}
	
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack is, EntityPlayer player, List list, boolean par4) {
		if (is.stackTagCompound !=null) {
			String master = is.stackTagCompound.getString("master");
			boolean activated = is.stackTagCompound.getBoolean("activated");
			list.add(StatCollector.translateToLocal(EnumChatFormatting.DARK_PURPLE + "This tool is Bound to: " + EnumChatFormatting.DARK_AQUA + master));
			if (activated == true) {
				list.add(StatCollector.translateToLocal(EnumChatFormatting.DARK_PURPLE + "Is attuned: " + EnumChatFormatting.DARK_AQUA + "True"));
			} else if (master == null) {
				list.add(StatCollector.translateToLocal(EnumChatFormatting.RED + "Not attuned"));
			} else {
				list.add(StatCollector.translateToLocal(EnumChatFormatting.DARK_PURPLE + "Is attuned: " + EnumChatFormatting.DARK_AQUA + "False"));
			}
		}
		
	}
	
//	public boolean onDroppedByPlayer(ItemStack is, EntityPlayer player) {
//		if (is.stackTagCompound != null) {
//			boolean activated = is.stackTagCompound.getBoolean("activated");
//			if (activated == false) {
//				is.stackTagCompound.setBoolean("activated", false);
//			}
//		}
//		return false;
//	}

}