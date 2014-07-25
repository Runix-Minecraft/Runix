package com.newlinegaming.Runix.rune;

import java.util.ArrayList;

import com.newlinegaming.Runix.PersistentRune;
import com.newlinegaming.Runix.handlers.RuneHandler;
import com.newlinegaming.Runix.Tiers;
import com.newlinegaming.Runix.WorldXYZ;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class OracleRune extends PersistentRune {
	private static ArrayList<PersistentRune> activeOracles = new ArrayList<PersistentRune>();
	public OracleRune() {
		runeName = ("Oracle Rune");
	}

	public OracleRune(WorldXYZ coords, EntityPlayer player) {
		super(coords, player, "Oracle Rune");
	}

	@Override
	public ArrayList<PersistentRune> getActiveMagic() {
		return activeOracles;
	}

	@Override
	public boolean oneRunePerPerson() {
		return false;
	}

	@Override
	protected Block[][][] runicTemplateOriginal() {

		Block RED = Blocks.redstone_wire;
		return new Block[][][] {{
			{RED,RED,RED},
			{RED,TIER,RED},
			{RED,RED,RED}
		}};
	}
	
	@Override
	protected void poke(EntityPlayer player, WorldXYZ coords) {
		ItemStack toolUsed = player.getHeldItem();
		
		if(toolUsed !=null && toolUsed.getItem() == Items.golden_sword) {
			ArrayList<PersistentRune> d = RuneHandler.getInstance().getAllRunesByPlayer(player);
			for (PersistentRune r : d) {
				aetherSay(player, r.runeName + " Energy: "+ r.energy);
			}
			
		} else {
			Block block = coords.getBlock();
			
			aetherSay(player, EnumChatFormatting.RED +block.getLocalizedName());
			aetherSay(player, "Tier: "  + Tiers.getTier(block) + ".");
			aetherSay(player, "Energy: " + Tiers.getEnergy(block) + ".");
			aetherSay(player, "Properties: " + (Tiers.isNatural(block)? "Not Conductive" : "Conductive")
					+ ", " + (Tiers.isCrushable(block)? "Crushable." : "Not Crushable."));
			
		}
	}

	@Override
	public boolean isFlatRuneOnly() {
		return true;
	}

	

}
