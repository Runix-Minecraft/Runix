package com.newlinegaming.Runix;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import argo.jdom.JdomParser;
import argo.jdom.JsonNode;
import argo.jdom.JsonRootNode;
import argo.saj.InvalidSyntaxException;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.World;

public class RubricControl extends AbstractRune{

    @Override
    public int[][][] blockPattern() {
	int RT=Block.torchRedstoneActive.blockID;
	return new int [][][] 
	            {{{0,   TIER,0, TIER   ,0},
	              {TIER,TIER,RT,TIER,TIER},
	              {0,     RT,TIER,RT,   0},
	              {TIER,TIER,RT,TIER,TIER},
	              {0,   TIER,0, TIER,   0}
	             }}; 
	
    }

    @Override
    public void execute(EntityPlayer player, WorldCoordinates coords) {
	accept(player);
	HashMap<WorldCoordinates, SigBlock> structure=conductanceStep(coords, 50);
	for(WorldCoordinates XYZ : structure.keySet())
	{
	    XYZ.setBlockId(12);
	    
	}
    }

    @Override
    public String getRuneName() { return "Rubric";

    }

}
