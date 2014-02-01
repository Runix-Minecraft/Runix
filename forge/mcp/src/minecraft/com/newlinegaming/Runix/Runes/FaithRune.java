package com.newlinegaming.Runix.Runes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.EnumChatFormatting;

import com.newlinegaming.Runix.NotEnoughRunicEnergyException;
import com.newlinegaming.Runix.PersistentRune;
import com.newlinegaming.Runix.Util_Movement;
import com.newlinegaming.Runix.Util_SphericalFunctions;
import com.newlinegaming.Runix.WorldXYZ;

public class FaithRune extends PersistentRune{
	
	protected static ArrayList<PersistentRune> activeFaithList = new ArrayList<PersistentRune>();
	public Integer radius = 11;
	public LinkedList<WorldXYZ> sphere = null;
	
	public FaithRune(){
	    runeName = "Faith";
	}
	public FaithRune(WorldXYZ loc, EntityPlayer creator)
	{
	    super(loc, creator, "Faith");
        //, int radius
	    sphere = Util_SphericalFunctions.getSphere(loc, radius);
	    energy += 100000; //TODO consume rune
	}
	
	public int[][][] runicTemplateOriginal(){
		return new int [][][] 
           {{{0,0,0},
             {0,41,0},
             {0,0,0}},
            {{41,0,41},
             {0,41,0},
             {41,0,41}}};
	}
	
	public void execute(WorldXYZ coords, EntityPlayer player)
	{
		if (!runeAllowed(player, this)) return;
		for (PersistentRune tmp : activeFaithList)
		{//Josiah: sorry, there's no way to get around type casting and still have an interface or abstract parent 
		    FaithRune fr = (FaithRune)tmp;
			if (fr.location != null && fr.radius != null)
			{
				if(coords.getDistance(fr.location) < radius)// check for other Faith centers inside our radius
				{
					player.sendChatToPlayer(ChatMessageComponent.createFromText(EnumChatFormatting.RED+"ISLANDS TOO CLOSE"));
					return;//TODO Chain FTP
				}
			}
		}
		PersistentRune newIsland = this.getOrCreateRune(coords, player);
		if(newIsland != null)
		    ((FaithRune) newIsland).floatIsland();
	}
	
	
	
	public void floatIsland()
	{
		aetherSay(getPlayer(),EnumChatFormatting.GREEN+"Floating an island");
		List<WorldXYZ> points = Util_SphericalFunctions.getSphere(location, radius);
		aetherSay(getPlayer(),EnumChatFormatting.GREEN+"Got "+points.size()+" points to raise");
		//use AbstractRune.moveShape() which will charge energy for the move
		HashMap<WorldXYZ, WorldXYZ> moveMapping = Util_Movement.displaceShape(sphere, 0, 50, 0);
		try {
            this.moveShape(moveMapping);
        } catch (NotEnoughRunicEnergyException e) {
            aetherSay(getPlayer(), "There not enough energy left to move this island.  Energy: " + energy);
        }
	}

    @Override
    public ArrayList<PersistentRune> getActiveMagic() {
        return activeFaithList;
    }

    @Override
    public boolean oneRunePerPerson() {
        return false;
    }
    
    public boolean isFlatRuneOnly(){
        return true;
    }
	

	

}
