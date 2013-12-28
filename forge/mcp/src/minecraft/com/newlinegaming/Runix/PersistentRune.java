package com.newlinegaming.Runix;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;

public abstract class PersistentRune extends AbstractRune{
    
    public EntityPlayer player = null;
    public WorldXYZ location = null;
    public boolean disabled = false;
    
    public PersistentRune(){}
    
    public PersistentRune(WorldXYZ coords, EntityPlayer player2) {
        location = coords;
        player = player2;
    }
    
    public void saveActiveRunes(){}
    
    /**There's no way to have a static field in an abstract class so we use a getter instead
     * public static ArrayList<WaypointRune> activeMagic = new ArrayList<WaypointRune>(); */ 
    public abstract List<PersistentRune> getActiveMagic();
    
    @Override
    /**Consolidated all the PersistRune execute functions into a single execute() that searches 
     * for duplicates, builds a new rune if neccessary, then notifies the rune it has been poked.
     */
    public void execute(WorldXYZ coords, EntityPlayer activator) {
//      if(activator.worldObj.isRemote)
//          return;
        PersistentRune match = getRuneByLocation(coords);
        if( match == null){
            try {
                match = this.getClass().getConstructor(WorldXYZ.class, EntityPlayer.class).newInstance(coords, activator);
                getActiveMagic().add(match);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        accept(activator);
        match.poke(activator, coords);
    }
    
    protected void poke(EntityPlayer poker, WorldXYZ coords){}
    
    
    @Override
    protected void accept(EntityPlayer player) {
        aetherSay(player, EnumChatFormatting.GREEN + getRuneName()+ getActiveMagic().size() + " Accepted.");
    }

    /**Returns the rune at that location or constructs a new one as if execute() were called on it
     * @param runeClass a pointer to the class constructor to be called*/
    public PersistentRune getOrCreateRuneByLocation(EntityPlayer player2, WorldXYZ coords, Class<? extends PersistentRune> runeClass){
        return getRuneByLocation(coords);
//        return new runeClass(coords, player2);
    }

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

    /**Return the rune in getActiveMagic() that matches the given coordinates or null if there is none */
    public PersistentRune getRuneByLocation(WorldXYZ coords) {
        for(PersistentRune rune : getActiveMagic()){
            if( rune.location.equals(coords) )
                return rune;
        }
        return null;
    }
}
