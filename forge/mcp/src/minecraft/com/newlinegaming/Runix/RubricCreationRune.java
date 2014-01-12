package com.newlinegaming.Runix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

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
	public Signature sig=null;
	public HashMap<WorldXYZ, SigBlock> structure=new HashMap<WorldXYZ, SigBlock>();
	protected transient RenderHelper renderer;

    public RubricCreationRune() {
        runeName = "Rubric Creator";
    }

    public RubricCreationRune(WorldXYZ coords, EntityPlayer player2) 
    {
	    super(coords, player2,"Rubric Creator");
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
           HashSet<WorldXYZ> shape = conductanceStep(coords, 50);
           structure = scanStructure(shape);
        sig = new Signature(this, coords);
        //TODO check for signature collision
        
                ItemStack toolused = poker.getCurrentEquippedItem();
        if (toolused!=null && toolused.itemID == Item.book.itemID) {
            
        	consumeRune(location);
        	//add runic energy
        	structure=scanStructure(shape);
        	consumeRune(structure.keySet());
        	//need to remove the rune itself 
        	
            //then capture everything else into the rubric file 
            //rename the book to something we can identify the book with the recall
            
        }
        
	}


	
	@ForgeSubscribe
	public void renderWireframe(RenderWorldLastEvent evt) {
		if (getPlayer() != null)
			renderer.highlightBoxes(structure.keySet(), getPlayer(), 221, 0, 0);

	}

//	@Override
//    public void saveActiveRunes() {
//        //Do nothing
//    }
	
    @Override
    public void loadRunes() {
        //Do nothing
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
