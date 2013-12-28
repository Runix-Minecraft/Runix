package com.newlinegaming.Runix;

import java.lang.reflect.InvocationTargetException;
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

    public RubricCreationRune(WorldXYZ coords, EntityPlayer player2) 
    {
	    super(coords, player2);
		structure = conductanceStep(coords, 50);;
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
	protected void poke(EntityPlayer poker, WorldXYZ coords){
        renderer.reset();

        ItemStack toolused = poker.getCurrentEquippedItem();
        if (toolused!=null && toolused.itemID == Item.book.itemID) {
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

    @Override
    public void saveActiveRunes() {
        //TODO: does nothing
    }

    @Override
    public ArrayList<PersistentRune> getActiveMagic() {
        return storedPatterns;
    }

}
