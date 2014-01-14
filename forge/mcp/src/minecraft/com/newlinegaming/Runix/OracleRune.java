package com.newlinegaming.Runix;

import java.util.ArrayList;
import java.util.HashSet;

import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class OracleRune extends PersistentRune {
	private static ArrayList<PersistentRune> activeOracles = new ArrayList<PersistentRune>();
	public OracleRune() {
		runeName = ("Oracle Rune");
	}

	public OracleRune(WorldXYZ coords, EntityPlayer player2) 
	{
		super(coords, player2,"Oracle Rune");
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
	public int[][][] runicTemplateOriginal(){
		int Red = Block.redstoneWire.blockID;
		return new int [][][] 
				{{{Red,Red,Red},
					{Red,TIER,Red},
					{Red,Red,Red}}};
	}

	@Override
	protected void poke(EntityPlayer poker, WorldXYZ coords){
		WorldXYZ OracleConsume = coords;

		ItemStack toolused = poker.getCurrentEquippedItem();
		aetherSay(poker, "The tier of this block is "  + Tiers.getTier(coords.getBlockId()) + " and energy is " + Tiers.getEnergy(coords.getBlockId()) + ".");
		aetherSay(poker, "The conductance of this block is " + Tiers.isNatural(TIER) + ".");
	}

	@Override
	public boolean isFlatRuneOnly() {
		return true;
	}

}
