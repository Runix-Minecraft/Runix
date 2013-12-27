package com.newlinegaming.Runix;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;

public abstract class PersistentRune extends AbstractRune{
    
    public EntityPlayer player = null;
    public WorldXYZ location = null;
    
    public PersistentRune(){}
    
    public PersistentRune(WorldXYZ coords, EntityPlayer player2) {
        location = coords;
        player = player2;
    }
    
    public void saveActiveRunes(){}
    
    /**There's no way to have a static field in an abstract class so we use a getter instead
     * public static ArrayList<WaypointRune> activeMagic = new ArrayList<WaypointRune>(); */ 
    public abstract List<PersistentRune> getActiveMagic();

    /** This method exists to ensure that no duplicate activeMagic are persisted. 
     * 
     * NOTE: There's still a danger of duplication for Runes that are registered to an
     * eventListener in their constructor, since a pointer to the object will still be
     * preserved by Forge.*/
    public boolean addOrRejectDuplicate(PersistentRune newGuy) {
        for(PersistentRune rune : getActiveMagic()){
            if( rune.location.equals(newGuy.location) )  
                return false; //ensure there are no duplicates
        }
        getActiveMagic().add(newGuy);
        return true;
    }
}
