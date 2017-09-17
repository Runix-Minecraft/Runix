package com.newlinegaming.Runix;

import com.google.gson.Gson;
import com.newlinegaming.Runix.handlers.RuneHandler;
import com.newlinegaming.Runix.helper.LogHelper;
import com.newlinegaming.Runix.utils.UtilMovement;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent.Load;
import net.minecraftforge.event.world.WorldEvent.Save;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.server.FMLServerHandler;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public abstract class PersistentRune extends AbstractRune {

    private UUID uuid = null;
    public boolean disabled = false;
    public WorldXYZ location = null;
    protected Vector3 forwards = Vector3.UP;
    protected String instanceName = "";
    protected PersistentRune(){}


    protected PersistentRune(WorldXYZ coords, EntityPlayer activator, String runeName) {
        location = coords;
        setPlayer(activator);
        this.runeName = runeName;

    }

    /**
     * Override this method to implement custom rune file saving rules
     */
    public void saveActiveRunes(Save saveEvent) {
        if(getActiveMagic().isEmpty())
            return;
        String fileName = getJsonFilePath(saveEvent.getWorld());//  ex:TorcherBearerRune.json
        try {
            PrintWriter file = new PrintWriter(fileName);
            Gson converter = new Gson();
            for(PersistentRune rune : getActiveMagic()) {
                String runeGson = converter.toJson(rune);
//				System.out.println("[SAVE]["+shortClassName()+"] " +runeGson);
//				LogHelper.info("Saving ["+shortClassName()+"] " +runeGson);
                file.println(runeGson);
            }
            file.close();
        } catch (FileNotFoundException e) {
//			System.err.println("RUNIX: Couldn't write to file: " + fileName);
            LogHelper.warn("RUNIX: Couldn't write to file: " + fileName);
        }
    }

    public void loadRunes(Load loadEvent) {
        String fileName = getJsonFilePath(loadEvent.getWorld());
        try {
            ArrayList<PersistentRune> newList = new ArrayList<>();
            List<String> json = FileUtils.readLines(new File(fileName));
            Gson gson = new Gson();
            for(String line : json) {
                newList.add(gson.fromJson(line, this.getClass()));
            }
            if( !newList.isEmpty() ) {
                getActiveMagic().clear();
                getActiveMagic().addAll(newList);
            }
        } catch (IOException e) {
            LogHelper.info("Runix: Can't access file or doesn't exist: " + fileName);
        } catch (Exception e){
//			System.err.println("GSON failed to parse " + fileName);
            LogHelper.fatal("GSON failed to parse " + fileName);
            e.printStackTrace();
        }
    }

    private String getJsonFilePath(World world) {

//        String levelName = world.getWorldInfo().getWorldName();
//        String directory;
        File base = DimensionManager.getCurrentSaveRootDirectory();


//        try {
////			Class
//            String subDirectory = (world.getSaveHandler() instanceof DedicatedServer )? "" : "saves/";
//            directory = subDirectory + levelName + "/stored_runes/";


//        } catch (Throwable e) {
//            directory = "saves/" + levelName + "/stored_runes/";
//        }
        File directory = new File(base + "/stored_runes/");

        //noinspection ResultOfMethodCallIgnored
        directory.mkdirs();//ensure the folder exists
        String fileName = directory + shortClassName() + ".json";
        return fileName;

    }

    /**
     * There's no way to have a static field in an abstract class so we use a getter instead
     * public static ArrayList<WaypointRune> activeMagic = new ArrayList<WaypointRune>();
     */

    public abstract ArrayList<PersistentRune> getActiveMagic();

    /**
     * Consolidated all the PersistentRune execute functions into a single execute() that searches
     * for duplicates based on location, builds a new rune if necessary, then notifies the
     * rune it has been poked.
     *
     * NOTE: It is important that you implement constructor YourRune(WorldXYZ coords, EntityPlayer activator)
     * even if it is only to call super(coords, activator) in order for persistence to work correctly.
     */
    @Override
    public void execute(WorldXYZ coords, EntityPlayer activator) {
        execute(coords, activator, Vector3.UP); //Up is the default
    }

    public void execute(WorldXYZ coords, EntityPlayer activator, Vector3 forward) {
        if(activator.world.isRemote)//runes server side only
            return;
        PersistentRune match = getOrCreateRune(coords, activator);
        if (match != null) {
            match.forwards = forward;
            match.poke(activator, coords); //either way, we poke the relevant rune to let it know
        }
    }


    private PersistentRune getOrCreateRune(WorldXYZ coords, EntityPlayer activator) {
        PersistentRune match = null;
        if(oneRunePerPerson())
            match = getRuneByPlayer(activator);
        if(match == null)//didn't find anything through players
            match = getRuneByLocation(coords);//check if the Rune already exists

        if( match == null ){//can't find anything: create a new one
            try {//this is a Java trick called reflection that grabs a constructor based on the parameters
                match = this.getClass().getConstructor(WorldXYZ.class, EntityPlayer.class).newInstance(coords, activator);
                getActiveMagic().add(match);//add our new Rune to the list
            } catch (Exception e) {
                aetherSay(activator, "This Persistent runes require a constructor public YourRune(WorldXYZ loc, EntityPlayer user) to be defined.");
                e.printStackTrace();
                return null;
            }
        }
        accept(activator);
        return match;
    }

    @SuppressWarnings("unused")
    public PersistentRune getRuneByInstanceName(String name) {
        for(PersistentRune rune : getActiveMagic()) {
            if(rune.instanceName.equals(name))
                return rune;
        }
        return null;
    }

    /**
     * Return the rune in getActiveMagic() that matches the given coordinates or null if there is none
     */
    public PersistentRune getRuneByPlayer(EntityPlayer activator) {
        for(PersistentRune rune : getActiveMagic()){
            if( rune.getPlayer() != null){
                UUID runeID = rune.getPlayer().getUniqueID();
                UUID activatorID = activator.getUniqueID();
                if(runeID.equals(activatorID) )
                    return rune;
            }
        }
        return null;
    }

    /**
     * poke() is called every time the rune's center block is right clicked.  This means it gets called when
     * the rune is first created and every time after that as well.  Functionality that you want to call when the
     * rune is built and also later whenever it is poked should be placed in this method, not in the constructor.
     * Remember, poke will always be called after a rune is created through PersistentRune.execute()
     * @param player Player that poked the rune
     * @param coords center block
     */
    protected void poke(EntityPlayer player, WorldXYZ coords){
        if(player.world.isRemote)
            return;
        if(oneRunePerPerson()){
            consumeRune(coords);
        }
    }

    /**
     * This has the additional feature of re-enabling dead runes and allowing you to pick up the left over energy.
     */
    @Override
    protected void consumeRune(WorldXYZ coords) {
        super.consumeRune(coords);
        disabled = false;//runes are usually disabled because they ran out of energy
    }

    /**
     * Return false if this Rune is more like a persistent world feature. Return
     * true if this is something like a player enchantment that should only have
     * one per person.  If true, energy from consuming the second rune will be added
     * to the player's original rune.
     */
    protected abstract boolean oneRunePerPerson();

    /**
     * Prints a verification message to the user
     */
    @Override
    protected void accept(EntityPlayer player) {
        aetherSay(player, TextFormatting.GREEN + getRuneName()+"_"+ getActiveMagic().size() + " Accepted.");
    }

    @Override
    public Signature getSignature() {
        return new Signature(this, location);
    }

    /**
     * Return the rune in getActiveMagic() that matches the given coordinates or null if there is none
     */
    private PersistentRune getRuneByLocation(WorldXYZ coords) {
        ArrayList<PersistentRune> list = getActiveMagic();
        for(PersistentRune rune : list){
            if( rune.location.equals(coords) )
                return rune;
        }
        return null;
    }

    @Override
    /*
      Adds re-enabling runes to consumeKeyBlock
     */
    protected boolean consumeFuelBlock(WorldXYZ coords) {
        if(super.consumeFuelBlock(coords)){
            disabled = false;
            return true;
        }
        return false;
    }

    /**
     * moveMagic() parameters allow any kind of transformation.  This is used by rotation to
     * map the starting position as a key, and the end position as the value.
     *  Ideally, runes should be coded so that moving the center block is
     * sufficient.  However, it's still possible to cleave a rune in half with a Faith sphere.
     */
    @Override
    public void moveMagic(HashMap<WorldXYZ, WorldXYZ> positionsMoved) {
        for(PersistentRune rune : getActiveMagic()){
            if(positionsMoved.keySet().contains(rune.location) ){//grab the destination keyed by source position
                rune.moveYourLocation(positionsMoved.get(rune.location));
            }
        }
    }

    protected void moveYourLocation(WorldXYZ destination) {
        location = destination.copyWithNewFacing(location.face); //preserve old facing for runes
    }

    protected void reportOutOfGas(EntityPlayer listener) {
        aetherSay(listener, runeName + ": More energy needed. Place a more valuable block in the center and active this Rune again.");
        disabled = true;
    }

    @SuppressWarnings("unused")
    public boolean onPlayerLogin(String username) {
        return false;
    }

    protected EntityPlayer getPlayer() {

         try {


             if (!location.getWorld().isRemote) {
                 for (Object playerObj : FMLServerHandler.instance().getServer().getPlayerList().getPlayers()) { //TODO relook.
                  if (((EntityPlayer) playerObj).getUniqueID().equals(uuid))
                      return (EntityPlayer) playerObj;
                 }
             }
         } catch (NullPointerException ex) {
        //Silent fail
    }
        return null;
    }

    public void setPlayer(EntityPlayer playerObj) {
        if (playerObj == null)
            this.uuid = null;
        else
            this.uuid = playerObj.getUniqueID();
    }

    public int getTier() {
        return super.getTier(location);
    }

    protected int boundaryFromCenter(HashSet<WorldXYZ> structure, Vector3 directionOfScanning){
        return getTier(); //Spheres are the same in all directions regardless of facing
    }

    protected LinkedHashSet<WorldXYZ> attachedStructureShape(EntityPlayer activator) {
        return attachedStructureShape(activator, fullStructure());
    }    
    
    protected LinkedHashSet<WorldXYZ> attachedStructureShape(EntityPlayer activator, LinkedHashSet<WorldXYZ> scannedStructure) {
        if (activator != null) {
            if (scannedStructure.isEmpty()) {
                aetherSay(activator, "There are too many blocks for the Rune to carry. Increase the Tier blocks or choose a smaller structure.");
            } else {
                aetherSay(activator, "Found " + scannedStructure.size() + " conducting blocks");
            }
        }
        // add indirect connections
        scannedStructure = RuneHandler.getInstance().chainAttachedStructures(scannedStructure, this);
        return scannedStructure;
    }

    public LinkedHashSet<WorldXYZ> fullStructure() {
        if(usesConductance)
            return directConductanceStructure();
        else 
            return runeBlocks(location);
    }
    
    private LinkedHashSet<WorldXYZ> directConductanceStructure() {
        int tier = getTier();
        LinkedHashSet<WorldXYZ> scannedStructure = conductanceStep(location, tier * 2);
        return scannedStructure;
    }

    public void clearActiveMagic() {
        for (PersistentRune rune : getActiveMagic()) {
            rune.kill();
        }
        getActiveMagic().clear();
    }

    protected void kill() {
        disabled = true;
        try {
            MinecraftForge.EVENT_BUS.unregister(this);
        } catch (NullPointerException e) {
            System.out.println("Failed to unregister " + this);
        }
    }

    /** Currently just checkRunePattern(location).  This could be expanded to kill the rune if broken. */
    public boolean runeIsIntact() {
        return checkRunePattern(location) != null;
    }


    protected void moveStructureAndPlayer(EntityPlayer player, WorldXYZ destination, HashSet<WorldXYZ> structure) {
            Vector3 directionOfScanning = Vector3.facing[destination.face];
            WorldXYZ destinationCenter = UtilMovement.safelyTeleportStructure(structure, location, destination, boundaryFromCenter(structure, directionOfScanning));
            if(destinationCenter != null) {
                teleportPlayer(player, destinationCenter.copyWithNewFacing(location.face)); // so that the player always lands in the right spot regardless of signature
            }else {
                aetherSay(player, "There are obstacles for over 100m in the direction of the destination waypoint.");
            }
    }


    protected void toggleDisabled() {
        disabled = !disabled;
    }
}
