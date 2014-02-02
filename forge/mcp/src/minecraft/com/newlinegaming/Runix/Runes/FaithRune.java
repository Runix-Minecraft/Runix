package com.newlinegaming.Runix.Runes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.EnumChatFormatting;

import com.newlinegaming.Runix.NotEnoughRunicEnergyException;
import com.newlinegaming.Runix.PersistentRune;
import com.newlinegaming.Runix.Tiers;
import com.newlinegaming.Runix.Util_Movement;
import com.newlinegaming.Runix.Util_SphericalFunctions;
import com.newlinegaming.Runix.WorldXYZ;

public class FaithRune extends PersistentRune{
	
	protected static ArrayList<PersistentRune> activeFaithList = new ArrayList<PersistentRune>();
	public Integer radius = 11;
    private boolean firstTime;
	
	public FaithRune(){
	    runeName = "Faith";
	}
	public FaithRune(WorldXYZ loc, EntityPlayer creator)
	{
	    super(loc, creator, "Faith");
	    firstTime = true;
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
	
//	public void execute(WorldXYZ coords, EntityPlayer player) // TODO we need a way for runes to check more complex validation and delete themselves if they don't pass
//	{
//		if (!runeAllowed(player, this)) return;
//		for (PersistentRune tmp : activeFaithList)
//		{//Josiah: sorry, there's no way to get around type casting and still have an interface or abstract parent 
//		    FaithRune fr = (FaithRune)tmp;
//			if (fr.location != null && fr.radius != null)
//			{
//				if(coords.getDistance(fr.location) < radius)// check for other Faith centers inside our radius
//				{
//					player.sendChatToPlayer(ChatMessageComponent.createFromText(EnumChatFormatting.RED+"ISLANDS TOO CLOSE"));
//					return;//TODO Chain FTP
//				}
//			}
//		}
//		PersistentRune newIsland = this.getOrCreateRune(coords, player);
//		if(newIsland != null)
//		    newIsland.poke(player, coords);
//	}
	
    @Override
    protected void poke(EntityPlayer poker, WorldXYZ coords) {
        if(firstTime){
            firstTime = false;
            consumeRune(coords);
            try {
                setBlockIdAndUpdate(coords, Block.blockGold.blockID); //Gold block is to be a permanent marker
            } catch (NotEnoughRunicEnergyException e) {}
            radius = Tiers.energyToRadiusConversion(energy);
            LinkedList<WorldXYZ> sphere = Util_SphericalFunctions.getSphere(coords, radius);
            energy -= sphere.size() * Tiers.blockMoveCost;
            aetherSay(poker, "Created a Faith Sphere with " + sphere.size() + " blocks.");
            return;
        }
        bounceIsland();
    }
    
    public void bounceIsland()
	{
        LinkedList<WorldXYZ> sphere = Util_SphericalFunctions.getSphere(location, radius);
		HashMap<WorldXYZ, WorldXYZ> moveMapping = Util_Movement.displaceShape(sphere, 0, radius*2+1, 0);
		Util_Movement.performMove(moveMapping);//this is the version that doesn't cost energy, Faith pays the cost up front
	}

    @Override
    public ArrayList<PersistentRune> getActiveMagic() {
        return activeFaithList;
    }

    @Override
    public boolean oneRunePerPerson() {
        return false;
    }
    @Override
    public boolean isFlatRuneOnly(){
        return true;
    }
	

	

}
