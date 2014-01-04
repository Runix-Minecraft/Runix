package com.newlinegaming.Runix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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

    public RubricCreationRune() {super();}

    public RubricCreationRune(WorldXYZ coords, EntityPlayer player2) 
    {
	    super(coords, player2,"Rubric Creator");
		HashSet<WorldXYZ> shape = conductanceStep(coords, 50);;
		structure = scanStructure(shape);
		renderer = new RenderHelper();
		MinecraftForge.EVENT_BUS.register(this);
		
	}

    private HashMap<WorldXYZ, SigBlock> scanStructure(HashSet<WorldXYZ> shape) {
        HashMap<WorldXYZ, SigBlock> fullData = new HashMap<WorldXYZ, SigBlock>();
        for(WorldXYZ point : shape)
            fullData.put(point, point.getSigBlock());
        return fullData;
    }

    @Override
	public int[][][] runicTemplateOriginal() {
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
        if (toolused!=null && toolused.itemID == Item.book.itemID) 
            consumeRune(structure.keySet());
	}

	public void unpackStructure(EntityPlayer initiator, WorldXYZ unpackAnchor){
	    //try{
	    //for structure
	        //setBlockID(
	    //catch: need more energy
	}
	
	@ForgeSubscribe
	public void renderWireframe(RenderWorldLastEvent evt) {
		if (getPlayer() != null)
			renderer.highlightBoxes(structure.keySet(), getPlayer(), 221, 0, 0);

	}

	@Override
	public String getRuneName() {
		return this.runeName;
	}

    @Override
    public ArrayList<PersistentRune> getActiveMagic() {
        return storedPatterns;
    }

    @Override
    public boolean oneRunePerPerson() {
        return false;
    }
    
    public boolean isFlatRuneOnly() {
        return false; //TODO: Consider this
    }
}
