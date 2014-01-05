package com.newlinegaming.Runix;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;

import com.google.gson.Gson;
public abstract class PersistentRune extends AbstractRune{
    
    private String player = null;
    public WorldXYZ location = null;
    public boolean disabled = false;
    private String RuneName;

    public PersistentRune(){ this.runeName = "generic persistent rune";}
    

    public PersistentRune(WorldXYZ coords, EntityPlayer activator, String runeName) {
        location = coords;
        setPlayer(activator);
        this.runeName = runeName;
        
    }
    
    /**Override this method to implement custom rune file saving rules*/
    public void saveActiveRunes(){
        if(getActiveMagic().isEmpty())
            return;
        
        String fileName = shortClassName() + ".json";//  ex:TorcherBearerRune.json
        try {
            PrintWriter file = new PrintWriter(fileName);
            for(PersistentRune rune : getActiveMagic())
            {
                String runeGson = toJson();
                System.out.println("[SAVE]["+shortClassName()+"] " +runeGson);
                file.println(runeGson);
            }
            file.close();
        } catch (FileNotFoundException e) {
            System.err.println("RUNIX: Couldn't write to file: " + fileName);
        } 
    }


    protected String toJson() {
        Gson object = new Gson(); 
    	String runeGson = object.toJson(this);
        return runeGson;
    }

    public void loadRunes(){
        String filename = shortClassName() + ".json";
        String className = "com.newlinegaming.Runix." + filename.replace(".json", "");//hopefully not a regular expression
        System.out.println("Class name: " + className);
        //TorcherBearerRune
        //        String json = open(filename).read()...
        Gson object = new Gson(); 
//        try {
//            Class<?> cls = Class.forName(className);
            AbstractRune rune= object.fromJson("JsonString", this.getClass());
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
    }
    
    /**There's no way to have a static field in an abstract class so we use a getter instead
     * public static ArrayList<WaypointRune> activeMagic = new ArrayList<WaypointRune>(); */ 
    public abstract List<PersistentRune> getActiveMagic();
    
    @Override
    /**Consolidated all the PersistentRune execute functions into a single execute() that searches 
     * for duplicates based on location, builds a new rune if necessary, then notifies the 
     * rune it has been poked.
     * 
     * NOTE: It is important that you implement constructor YourRune(WorldXYZ coords, EntityPlayer activator)
     * even if it is only to call super(coords, activator) in order for persistence to work correctly.
     */
    public void execute(WorldXYZ coords, EntityPlayer activator) {
        if(activator.worldObj.isRemote)//runes server side only
            return;
        PersistentRune match = null;
        if(oneRunePerPerson())
            match = getRuneByPlayer(activator);
        if(match == null)//didn't find anything through players
            match = getRuneByLocation(coords);//check if the Rune already exists
        if(activator.worldObj.isRemote && match == null)
            System.out.println("Client was unable to find a match.");
        
        if( match == null ){//can't find anything: creat a new one
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
    
         /**Return the rune in getActiveMagic() that matches the given coordinates or null if there is none */
    public PersistentRune getRuneByPlayer(EntityPlayer activator) {
        for(PersistentRune rune : getActiveMagic()){
            if( rune.getPlayer() != null && rune.getPlayer().equals(activator) )
                return rune;
        }
        return null;
    }

    /**poke() is called every time the rune's center block is right clicked.  This means it gets called when
     * the rune is first created and every time after that as well.  Functionality that you want to call when the
     * rune is built and also later whenever it is poked should be placed in this method, not in the constructor.
     * Remember, poke will always be called after a rune is created through PersistentRune.execute()
     * @param poker Player that poked the rune
     * @param coords center block
     */
    protected void poke(EntityPlayer poker, WorldXYZ coords){
        if(poker.worldObj.isRemote)
          return;
        if(oneRunePerPerson()){
            consumeRune(coords);
        }
    }
    
    @Override
    /**This has the additional feature of re-enabling dead runes and allowing you to pick up the left over energy. */
    protected void consumeRune(WorldXYZ coords) {
        super.consumeRune(coords);
        disabled = false;//runes are usually disabled because they ran out of energy
    }

    /**Return false if this Rune is more like a persistent world feature. Return
     * true if this is something like a player enchantment that should only have
     * one per person.  If true, energy from consuming the second rune will be added
     * to the player's original rune.*/
    public abstract boolean oneRunePerPerson();
    
    @Override
    /**Prints a verification message to the user */
    protected void accept(EntityPlayer player) {
        aetherSay(player, EnumChatFormatting.GREEN + getRuneName()+"_"+ getActiveMagic().size() + " Accepted.");
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
    /**Adds re-enabling runes to consumeKeyBlock*/
    protected boolean consumeKeyBlock(WorldXYZ coords) {
        if(super.consumeKeyBlock(coords)){
            disabled = false;
            return true;
        }
        return false;
    }

    @Override
    /**as moveMagic() but the parameters allow any kind of transformation.  This is used by rotation to
     * map the starting position as a key, and the end position as the value.   
     *  Ideally, runes should be coded so that moving the center block is
     * sufficient.  However, it's still possible to cleave a rune in half with a Faith sphere.  */
    public void moveMagic(HashMap<WorldXYZ, WorldXYZ> positionsMoved) {
        for(PersistentRune rune : getActiveMagic()){
            if(positionsMoved.keySet().contains(rune.location) ){//grab the destination keyed by source position
                rune.location = positionsMoved.get(rune.location).overrideFacing(rune.location.face); //preserve old facing for runes
            }
        }
    }

    protected void reportOutOfGas(EntityPlayer listener) {
        aetherSay(listener, "We require more Vespene Gas.");
        System.out.println(getRuneName() + ": We require more Vespene Gas; " + energy);
        disabled = true;
    }
    
    public boolean onPlayerLogin(String username){
//        System.out.println(username + " joined the server");
        return false;
    }
    
    public EntityPlayer getPlayer(){
        return MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(player);
    }

    public void setPlayer(EntityPlayer playerObj) {
        if(playerObj == null)
            this.player = null;
        else
            this.player = playerObj.username;
    }
}
