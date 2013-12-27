package com.newlinegaming.Runix;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;

public abstract class PersistentRune extends AbstractRune{
    
    public EntityPlayer player = null;
    public WorldXYZ location = null;
    /**There's no way to have a static field in an abstract class so we use a getter instead*/ 
    //public static ArrayList<WaypointRune> activeMagic = new ArrayList<WaypointRune>(); 
    
    public abstract void saveActiveRunes();
    
    public abstract ArrayList<PersistentRune> getActiveMagic();

    /** This method exists to ensure that no duplicate activeMagic are persisted. */
    public boolean addOrRejectDuplicate(PersistentRune newGuy) {
        for(PersistentRune rune : getActiveMagic()){
            if( rune.location.equals(newGuy.location) )  
                return false; //ensure there are no duplicates
        }
        getActiveMagic().add(newGuy);
        return true;
    }
}
