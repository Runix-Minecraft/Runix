package com.newlinegaming.Runix;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;

public abstract class PersistentRune extends AbstractRune{
    
    public EntityPlayer player = null;
    public WorldXYZ location = null;
    public boolean disabled = false;
    
    public PersistentRune(){}
    
    public PersistentRune(WorldXYZ coords, EntityPlayer activator) {
        location = coords;
        player = activator;
    }
    
    /**Override this method to implement custom rune file saving rules*/
    public void saveActiveRunes(){}
    
    /**There's no way to have a static field in an abstract class so we use a getter instead
     * public static ArrayList<WaypointRune> activeMagic = new ArrayList<WaypointRune>(); */ 
    public abstract List<PersistentRune> getActiveMagic();
    
    @Override
    /**Consolidated all the PersistRune execute functions into a single execute() that searches 
     * for duplicates based on location, builds a new rune if necessary, then notifies the 
     * rune it has been poked.
     * 
     * NOTE: It is important that you implement constructor YourRune(WorldXYZ coords, EntityPlayer activator)
     * even if it is only to call super(coords, activator) in order for persistence to work correctly.
     */
    public void execute(WorldXYZ coords, EntityPlayer activator) {
      if(activator.worldObj.isRemote)
          return;
        PersistentRune match = getRuneByLocation(coords);//check if the Rune already exists
        if( match == null){//if not create it
            try {//this is a Java trick called reflection that grabs a constructor based on the parameters
                match = this.getClass().getConstructor(WorldXYZ.class, EntityPlayer.class).newInstance(coords, activator);
                getActiveMagic().add(match);//add our new Rune to the list
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        accept(activator);
        match.poke(activator, coords); //either way, we poke the relevant rune to let it know
    }
    
    /**poke() is called every time the rune's center block is right clicked.  This means it gets called when
     * the rune is first created and every time after that as well.  Functionality that you want to call when the
     * rune is built and also later whenever it is poked should be placed in this method, not in the constructor.
     * Remember, poke will always be called after a rune is created through PersistentRune.execute()
     * @param poker Player that poked the rune
     * @param coords center block
     */
    protected void poke(EntityPlayer poker, WorldXYZ coords){}
    
    
    @Override
    protected void accept(EntityPlayer player) {
        aetherSay(player, EnumChatFormatting.GREEN + getRuneName()+"_"+ getActiveMagic().size() + " Accepted.");
        System.out.println(getRuneName()+"_"+ getActiveMagic().size() + " Accepted.");
    }

    /**Return the rune in getActiveMagic() that matches the given coordinates or null if there is none */
    public PersistentRune getRuneByLocation(WorldXYZ coords) {
        for(PersistentRune rune : getActiveMagic()){
            if( rune.location.equals(coords) )
                return rune;
        }
        return null;
    }

    @Override
    /**moveMagic() based on translation offset.  This will slide the PersistentRune.location value for
     * any runes that are affected.  Ideally, runes should be coded so that moving the center block is
     * sufficient.  However, it's still possible to cleave a rune in half with a Faith sphere.*/
    public void moveMagic(Collection<WorldXYZ> blocks, int dX, int dY, int dZ) {
        for(PersistentRune wp : getActiveMagic()){
            if(blocks.contains(wp.location) )
                wp.location.bump(dX, dY, dZ);
        }
    }

    @Override
    /**as moveMagic() but the parameters allow any kind of transformation.  This is used by rotation to
     * map the starting position as a key, and the end position as the value.     */
    public void moveMagic(HashMap<WorldXYZ, WorldXYZ> positionsMoved) {
        for(PersistentRune wp : getActiveMagic()){
            if(positionsMoved.keySet().contains(wp.location) )
                wp.location = positionsMoved.get(wp.location); //grab the destination keyed by source position
        }
    }
}
