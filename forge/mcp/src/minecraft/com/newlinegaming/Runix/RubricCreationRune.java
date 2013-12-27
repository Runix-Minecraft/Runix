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

public class RubricCreationRune extends PersistentRune {

	private static ArrayList<PersistentRune> storedPatterns = new ArrayList<PersistentRune>();
	public HashMap<WorldXYZ, SigBlock> structure;
	protected RenderHelper renderer;

    public RubricCreationRune() {}

    public RubricCreationRune(HashMap<WorldXYZ, SigBlock> building, WorldXYZ coords, EntityPlayer player2) 
    {
	    super(coords, player2);
		structure = building;
		renderer = new RenderHelper();
		MinecraftForge.EVENT_BUS.register(this);
	}

    @Override
	public int[][][] blockPattern() {
		int RTCH = Block.torchRedstoneActive.blockID;
		return new int[][][] 
		      {{{ NONE,TIER,SIGR,TIER,NONE },
				{ TIER,TIER,RTCH,TIER,TIER },
				{ SIGR,RTCH,TIER,RTCH,SIGR },
				{ TIER,TIER,RTCH,TIER,TIER },
				{ NONE,TIER,SIGR,TIER,NONE }}};

	}

	@Override
	public void execute(EntityPlayer player, WorldXYZ coords) {
		accept(player);
		HashMap<WorldXYZ, SigBlock> structure = conductanceStep(coords, 50);
		
		RubricCreationRune match = ((RubricCreationRune)getRuneByLocation(coords));
		if( match != null)
		    match.renderer.reset();
		else
		    getActiveMagic().add(new RubricCreationRune(structure, coords, player));
		
		ItemStack toolused = player.getCurrentEquippedItem();
		if (toolused!=null && toolused.itemID == Item.book.itemID) {
			for (WorldXYZ XYZ : structure.keySet()) {
				XYZ.setBlockId(0);
			}

		}
	}

	public PersistentRune getRuneByLocation(WorldXYZ coords) {
	    for(PersistentRune rune : getActiveMagic()){
            if( rune.location.equals(coords) )
                return rune;
	    }
        return null;
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

    @Override
    public void saveActiveRunes() {
        //TODO: does nothing
    }

    @Override
    public ArrayList<PersistentRune> getActiveMagic() {
        return storedPatterns;
    }

}
