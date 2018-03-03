package com.newlinegaming.Runix;


import com.newlinegaming.Runix.rune.ToolRunes.ToolRune;
import com.newlinegaming.Runix.utils.ActionType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface RunePlayer {

    boolean clearBlock(WorldXYZ loc, AbstractRune info) throws NotEnoughRunicEnergyException;
    boolean breakBlockAndDrop(WorldXYZ loc, AbstractRune info) throws NotEnoughRunicEnergyException;
    boolean placeBlock(WorldXYZ loc, AbstractRune info, Material mat, boolean physics) throws NotEnoughRunicEnergyException;


    /**
     * Querys a permission for the specific player
     *
     * @param action: optional subnode (ex. "activate")
     */
    boolean check_permission(String name, String action);

    void deleteSelf();

    boolean isDead();

    boolean isDummy();

    boolean isPlayer();

    boolean flightOn();

    boolean flightOff();

    /**
     * Returns the bukkit class equivalent for a player object
     */
    /*

    /**
     * Gets the world the player currently is in
     */
    World getWorld();

    /**
     * Checks if this player is currently online
     */
    boolean isOnline();

    /**
     * Get the name of the player
     */
    String getName();

    int getHeldItemSlotNumber();

    /**
     * @param slotNumber 0-8 slots
     * @return Material ID
     */
    int getItemInSlot(int slotNumber);

    ItemStack getItemStackInSlot(int slotNumber);

    /**
     * Get item the player is holding
     *
     * @return item type or 0 (air)
     */
    int getHeldItem();

    /**
     * get the quantity of the player's held itemstack
     *
     * @return quantity of held item
     */
    int getHeldItemQuantity();

    /**
     * attempts to add
     *
     * @param item
     * @param meta
     *
     * @return - if true, added succesfully. if not true, dropped the item
     */
    boolean giveItem(int item, int meta, int quantity);

    /**
     * Set item in hand slot
     *
     * @param id  Type of item
     * @param amt Amount of items in stack
     * @param dat Data value or damage of item
     */
    void setHeldItem(int id, int amt, short dat);

    /**
     * Removes exactly <c> times the item <t> in the hand slot
     *
     * @return true if successful
     */
    boolean takeAmountOfHeldItem(int t, int c);

    /**
     * Removes once the item <t> in the inventory
     *
     * @return true if successful
     */
    boolean removeFromInventory(int t);

    /**
     * returns true if <tl> is within the user's inventory
     *
     * @return
     */
    boolean inventoryContains(int tl);

    /**
     * Get armor contents
     *
     * @return int[4], array of item types in the armor slots
     */
    int[] getArmorIds();

    ArrayList<ToolRune> getToolRunesFromHeldItem();

    /**
     * Get player's location
     */
    double x();

    double y();

    double z();

    float yaw();

    float pitch();

    /**
     * Teleport player
     *
     * @param x,y,z Target location
     */
    void teleportPlayer(double x, double y, double z);

    /**
     * Teleport player
     *
     * @param x,y,z Target location
     * @param yaw   Yaw
     * @param pitch Pitch
     */
    void teleportPlayer(double x, double y, double z, float yaw, float pitch);

    /**
     * Teleport player to a different world
     *
     * @param targetWorld Target world
     * @param x,y,z       Target location
     * @param yaw,pitch   Yaw,Pitch
     */
    void teleportToWorld(World targetWorld, double x, double y, double z, float yaw, float pitch);

    /**
     * Sets the player's velocity.
     */
    void setVelocity(double dx, double dy, double dz);

    /**
     * Sets the player's fall distance.
     */
    void setCurrentFallDistance(float x);

    /**
     * Sends a message to this player
     *
     * @param m The message to send; it can contain color codes
     */
    void sendMessage(String m);

    /**
     * Sends a block-change packet to the player's client
     * If t==-1, the packet uses current world data
     *
     * @param x,y,z Block coordinates
     * @param t,d   Type and meta-data of new block
     */
    void blockChangePacket(int x, int y, int z, int t, int d);

    /**
     * Set player on fire
     *
     * @param z Burning time (in seconds)
     */
    void setBurningDuration(int z);

    /**
     * Set air bar
     *
     * @param a Air in ticks
     */
    void setAirTicksRemaining(int a);

    /**
     * drops the held item
     *
     * @return
     */
    boolean dropHeldItem();

    boolean isFlying();

    /**
     * Drops a head block item with the tags set to the current player
     * @param dropLocation drop location
     */
    void dropSkull(WorldXYZ dropLocation) throws NotEnoughRunicEnergyException;

    /**
     * Returns a different Player if the player is wearing a "True Name" Head as a helm
     */
    RunePlayer getTargetPlayerViaTrueName();

    /**
     * For destroying the True Name once it has been used
     */
    void destroyHelmet();

    /**
     * returns the damage value of the held item
     */
    int getHeldItemDurability();

    boolean isCreative();

    boolean isSneaking();

    /**
     * returns inventory of this player as RuneInventory *
     */
    InventoryPlayer getInventory();

    List<AbstractRune> getSpecificSlotRunes(ActionType tpInvHolding, int previousSlot);

    List<AbstractRune> getAllItemRunes();

    List<AbstractRune> getAllItemRunes(int runeID);

    WorldXYZ getPos();

    void outOfEnergy();

    void setHasEnergy();

    Entity getEntity();

    UUID getUniqueId();
}
