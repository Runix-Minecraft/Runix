package com.newlinegaming.Runix;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent.Load;
import net.minecraftforge.event.world.WorldEvent.Save;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.newlinegaming.Runix.handlers.RuneHandler;
import com.newlinegaming.Runix.helper.LogHelper;

public abstract class PersistentRune extends AbstractRune {

	private UUID uuid = null;
	public WorldXYZ location = null;
	public boolean disabled = false;
	public String instanceName = "";
	public PersistentRune(){}


	public PersistentRune(WorldXYZ coords, EntityPlayer activator, String runeName) {
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
		String fileName = getJsonFilePath(saveEvent.world);//  ex:TorcherBearerRune.json
		try {
			PrintWriter file = new PrintWriter(fileName);
			Gson converter = new Gson();
			for(PersistentRune rune : getActiveMagic()) {
				String runeGson = converter.toJson(rune);
//				System.out.println("[SAVE]["+shortClassName()+"] " +runeGson);
				LogHelper.info("Saving ["+shortClassName()+"] " +runeGson);
				file.println(runeGson);
			}
			file.close();
		} catch (FileNotFoundException e) {
//			System.err.println("RUNIX: Couldn't write to file: " + fileName);
			LogHelper.warn("RUNIX: Couldn't write to file: " + fileName);
		} 
	}

	public void loadRunes(Load loadEvent) {
		String fileName = getJsonFilePath(loadEvent.world);
		try {
			ArrayList<PersistentRune> newList = new ArrayList<PersistentRune>();
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
//			System.err.println("RUNIX: Can't access file or doesn't exist: " + fileName);
			LogHelper.fatal("RUNIX: Can't access file or doesn't exist: " + fileName);
		} catch (Exception e){
//			System.err.println("GSON failed to parse " + fileName);
			LogHelper.fatal("GSON failed to parse " + fileName);
			e.printStackTrace();
		}
	}

	String getJsonFilePath(World world) {
		
		String levelName = world.getWorldInfo().getWorldName();
		String directory = "";
		
		
		try {
//			Class
			String subDirectory = ( MinecraftServer.getServer() instanceof DedicatedServer )? "" : "saves/";
			directory = subDirectory + levelName + "/stored_runes/";
			
			
		} catch (Throwable e) {
			LogHelper.info("Server not found");
			directory = "saves/" + levelName + "/stored_runes/";
			
		}
		
		new File(directory).mkdirs();//ensure the folder exists
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
		if(activator.worldObj.isRemote)//runes server side only
			return;
		PersistentRune match = getOrCreateRune(coords, activator);
		match.poke(activator, coords); //either way, we poke the relevant rune to let it know
	}


	protected PersistentRune getOrCreateRune(WorldXYZ coords, EntityPlayer activator) {
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
				System.out.println("This Persistent runes require a constructor YourRune(WorldXYZ loc, EntityPlayer user) to be defined.");
				e.printStackTrace();
				return null;
			}
		}
		accept(activator);
		return match;
	}

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
	 * @param poker Player that poked the rune
	 * @param coords center block
	 */
	protected void poke(EntityPlayer player, WorldXYZ coords){
		if(player.worldObj.isRemote)
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
	public abstract boolean oneRunePerPerson();

	/**
	 * Prints a verification message to the user
	 */
	@Override
	protected void accept(EntityPlayer player) {
		aetherSay(player, EnumChatFormatting.GREEN + getRuneName()+"_"+ getActiveMagic().size() + " Accepted.");
	}

	@Override
	public Signature getSignature() {
		return new Signature(this, location);
	}

	/**
	 * Return the rune in getActiveMagic() that matches the given coordinates or null if there is none
	 */
	public PersistentRune getRuneByLocation(WorldXYZ coords) {
		ArrayList<PersistentRune> list = getActiveMagic();
		for(PersistentRune rune : list){
			if( rune.location.equals(coords) )
				return rune;
		}
		return null;
	}

	@Override
	/**
	 * Adds re-enabling runes to consumeKeyBlock
	 */
	protected boolean consumeKeyBlock(WorldXYZ coords) {
		if(super.consumeKeyBlock(coords)){
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
				rune.location = positionsMoved.get(rune.location).copyWithNewFacing(rune.location.face); //preserve old facing for runes
			}
		}
	}

	protected void reportOutOfGas(EntityPlayer listener) {
		aetherSay(listener, runeName + ": More energy needed. Place a more valuable block in the center and active this Rune again.");
		System.out.println(getRuneName() + ": We require more Vespene Gas; " + energy);
		disabled = true;
	}

	public boolean onPlayerLogin(String username) {
		//        System.out.println(username + " joined the server");
		return false;
	}

	public EntityPlayer getPlayer() {
		for( Object playerObj : MinecraftServer.getServer().getConfigurationManager().playerEntityList){
			if( ((EntityPlayer) playerObj).getUniqueID().equals(uuid))
				return (EntityPlayer)playerObj;
		}
		return null;
	}

	public void setPlayer(EntityPlayer playerObj) {
		if(playerObj == null)
			this.uuid = null;
		else
			this.uuid = playerObj.getUniqueID();
	}

	public int getTier() {
		return super.getTier(location);
	}

	protected HashSet<WorldXYZ> attachedStructureShape(EntityPlayer activator) {
		HashSet<WorldXYZ> scannedStructure = directlyAttachedStructure();
		if(activator != null) {
			if(scannedStructure.isEmpty()) {
				aetherSay(activator, "There are too many block for the Rune to carry. Increase the Tier blocks or choose a smaller structure.");
			}else{
				aetherSay(activator, "Found " + scannedStructure.size() + " conducting blocks");
			}
		}
		//add indirect connections
		scannedStructure = RuneHandler.getInstance().chainAttachedStructures(scannedStructure);
		return scannedStructure;
	}


	public HashSet<WorldXYZ> directlyAttachedStructure() {
		int tier = getTier();
		HashSet<WorldXYZ> scannedStructure = conductanceStep(location, tier);
		return scannedStructure;
	}

	/**
	 * Remember, this is called from the manager instance of each Rune, so you are acting on behalf
	 * of all runes of the same class.  DO NOT call your own functions except for getActiveMagic()
	 * @param structure
	 * @return
	 */
	public HashSet<WorldXYZ> addYourStructure(HashSet<WorldXYZ> structure) {
		HashSet<WorldXYZ> additionalBlocks = new HashSet<WorldXYZ>();
		for(PersistentRune rune : getActiveMagic()) {
			if(structure.contains(rune.location))
				additionalBlocks = rune.directlyAttachedStructure();
		}
		additionalBlocks.removeAll(structure); //only return new blocks
				return additionalBlocks;
	}
}