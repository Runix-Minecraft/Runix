package com.newlinegaming.Runix;

import java.util.LinkedList;
import java.util.List;

import cpw.mods.fml.common.network.Player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.EnumChatFormatting;

public class FaithRune extends AbstractRune{
	public String runeName = "Faith";
	static List<FaithRune> activeFaithList = new LinkedList<FaithRune>();
	public WorldXYZ islandCoords;
	public Integer radius;
	public int[][][] blockPattern(){
		return new int [][][] 
           {{{0,0,0},
             {0,41,0},
             {0,0,0}},
            {{41,0,41},
             {0,41,0},
             {41,0,41}}};
	}
	
	public void execute(EntityPlayer player, WorldXYZ coords)
	{
		if (!runeAllowed(player, this)) return;
		for (FaithRune fr : activeFaithList)
		{
			if (fr.islandCoords != null && fr.radius != null)
			{
				if(Util_SphericalFunctions.radiusCheck(fr.islandCoords, fr.radius+8))
				{
					player.sendChatToPlayer(ChatMessageComponent.createFromText(EnumChatFormatting.RED+"ISLANDS TOO CLOSE"));
					return;
				}
			}
		}
		this.floatIsland(player, coords);
		
		
	}
	
	public String getRuneName()
	{
		return "Faith";
	}
	
	public void floatIsland(EntityPlayer player,WorldXYZ islandCentre)
	{
		aetherSay(player,EnumChatFormatting.GREEN+"Floating an island");
		List<WorldXYZ> points = Util_SphericalFunctions.getSphere(islandCentre, 8);
		aetherSay(player,EnumChatFormatting.GREEN+"Got "+points.size()+" points to raise");
	}
	

	

}
