package com.newlinegaming.Runix;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;

//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.logging.Level;
//
//import argo.jdom.JdomParser;
//import argo.jdom.JsonNode;
//import argo.jdom.JsonRootNode;
//import argo.saj.InvalidSyntaxException;
//
//import net.minecraft.block.Block;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.nbt.NBTTagList;
//import net.minecraft.nbt.NBTTagString;
//import net.minecraft.world.World;

public class RubricCreationRune extends AbstractRune {

	public static ArrayList<RubricCreationRune> storedPatterns = new ArrayList<RubricCreationRune>();
	public HashMap<WorldXYZ, SigBlock> structure;
	public WorldXYZ anchorpoint;
	private RenderHelper renderer;
	private EntityPlayer player = null;

	public RubricCreationRune() {
	}

	public RubricCreationRune(HashMap<WorldXYZ, SigBlock> building,
			WorldXYZ location, EntityPlayer player2) {
		structure = building;
		anchorpoint = location;
		renderer = new RenderHelper();
		MinecraftForge.EVENT_BUS.register(this);
		player = player2;
	}

	@Override
	public int[][][] blockPattern() {
		int RT = Block.torchRedstoneActive.blockID;
		return new int[][][] { { { 0, TIER, 0, TIER, 0 },
				{ TIER, TIER, RT, TIER, TIER }, { 0, RT, TIER, RT, 0 },
				{ TIER, TIER, RT, TIER, TIER }, { 0, TIER, 0, TIER, 0 } } };

	}

	@Override
	public void execute(EntityPlayer player, WorldXYZ coords) {
		accept(player);
		HashMap<WorldXYZ, SigBlock> structure = conductanceStep(coords, 50);
		storedPatterns.add(new RubricCreationRune(structure, coords, player));
		ItemStack toolused = player.getCurrentEquippedItem();
		if (toolused!=null && toolused.itemID != Item.book.itemID) {
			for (WorldXYZ XYZ : structure.keySet()) {
				XYZ.setBlockId(0);
			}

		}
	}

	@ForgeSubscribe
	public void renderWireframe(RenderWorldLastEvent evt) {
		if (player != null)
			renderer.highlightBoxes(structure.keySet(), player, 221, 0, 0);

	}

	@Override
	public String getRuneName() {
		return "Rubric Creator";
	}

}
