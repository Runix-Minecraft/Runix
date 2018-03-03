package com.newlinegaming.Runix.rune.ToolRunes;

import java.io.File;
import java.util.ArrayList;

import com.newlinegaming.Runix.AbstractRune;
import com.newlinegaming.Runix.utils.ActionType;
import net.cerberusstudios.llama.runecraft.*;
import net.cerberusstudios.llama.runecraft.energy.Energy;
import net.cerberusstudios.llama.runecraft.energy.EnergyReservoir;
import net.cerberusstudios.llama.runecraft.energy.NotEnoughRunicEnergyException;
import net.cerberusstudios.llama.runecraft.energy.Tiers;
import net.cerberusstudios.llama.runecraft.logging.Logger;
import net.cerberusstudios.llama.runecraft.util.Numbers;
import net.cerberusstudios.llama.runecraft.util.Vector3;
import net.cerberusstudios.llama.runecraft.util.WorldXYZ;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.util.ArrayList;

import static com.newlinegaming.Runix.utils.ActionType.TP_BROKEN;
import static com.newlinegaming.Runix.utils.ActionType.TP_CATCHALL;
import static net.cerberusstudios.llama.runecraft.ActionType.TP_BROKEN;
import static net.cerberusstudios.llama.runecraft.ActionType.TP_CATCHALL;
import static net.cerberusstudios.llama.runecraft.energy.Tiers.*;
import static net.cerberusstudios.llama.runecraft.runes.RuneRegistry.*;
import static net.cerberusstudios.llama.runecraft.tasks.RunecraftTask.normalizeBlock;
import static org.bukkit.ChatColor.*;

/**
 * Created by Josiah on 6/13/2015.
 */
public class ToolRune extends AbstractRune {
    private static final long serialVersionUID = -8433219562224217690L;
//    private long tempestLastUse = 0;

    public ToolRune(int runeID, String name, ActionType triggerType, String message){
        super(runeID, name, triggerType, message);
        consumedBlocksGrantEnergy = true;
    }

    public ToolRune(int runeID, String name, ActionType[] triggerType, String message, int ink) {
        super(runeID, name, triggerType, message, ink);
        consumedBlocksGrantEnergy = true;
    }

    public ToolRune(AbstractRune runeInfo) {
        throw new NotImplementedException();

    }

    public static boolean doToolRunes(ActionType triggerType, RunePlayer player, WorldXYZ location,
                                      boolean runeFound, boolean secondaryActivation) {
        try {
            for (RuneInfo rune : player.getToolRunesFromHeldItem()) {
                if(!secondaryActivation ||//It's important to disinclude runes that could cause a cascae chain reaction
                        !(rune instanceof PowerTool)) {
                    if (triggerType == TP_CATCHALL || rune.triggers.contains(triggerType)) {
                        runeFound = triggerType != TP_BROKEN;
                        rune.use(player, location, triggerType);
                    }
                }
            }
        } catch (NotEnoughRunicEnergyException ignored) {
        }
        return runeFound;
    }

    /**
     * Enchants a tool with a tool rune.
     * @param runeID ID of rune to enchant the tool with.
     * @param holder Player who owns the tool that is getting enchanted.
     * @return true if the enchant was successful, false if the player is holding wrong item or item already has this enchant.
     */
    public static boolean addToolRune(int runeID, RunePlayer holder) {
        return addToolRune(runeID, "", holder);
    }

    /**
     * Enchants a tool with a tool rune.
     * @param runeID ID of rune to enchant the tool with.
     * @param additionalInfo Additional info about the enchant
     * @param holder Player who owns the tool that is getting enchanted.
     * @return true if the enchant was successful, false if the player is holding wrong item or item already has this enchant.
     */
    public static boolean addToolRune(int runeID, String additionalInfo, RunePlayer holder) {
        if(!holder.isPlayer())
            return false;
        RuneInfo rune = RuneRegistry.registeredRunes.get(runeID);
        String loreText = additionalInfo.isEmpty() ? rune.name : rune.name + " : " + additionalInfo;
        ItemStack inHand = holder.getPlayer().getItemInHand();
        if(inHand.getType() != Material.AIR) {
            if (engraveToolRuneLore(loreText, rune, inHand) == false) { //active ingredient is here
                holder.sendMessage(ChatColor.GREEN + "This tool already has this enchant.");
                return false;
            }
            holder.sendMessage(ChatColor.GREEN + rune.activationMessage);
        } else {
            holder.sendMessage(ChatColor.GREEN + "Please use an item. This does not work on your hand.");
            return false;
        }
//        //successful tool rune activation
//        if(rune.consumedBlocksGrantEnergy == true){
//            consumeRune(holder, rune);
//        }else if(rune.consumedBlocksGrantEnergy == false){
//            clearRune(rune);
//        }
        return true;
    }

    public static boolean engraveToolRuneLore(String lore, RuneInfo rune, ItemStack item) {
        if(item == null) return true;
        ItemMeta meta = item.getItemMeta();
        ArrayList<String> prevLore = new ArrayList<>();
        if (meta.hasLore()) {
            for (String oldLore : meta.getLore()) {
                //#2 Fixed the case where Recalls could be stacked on one item.  Use newest instead.
                if (!oldLore.toLowerCase().contains(rune.name.toLowerCase())) {//or lore.toLowerCase()
                    prevLore.add(oldLore); //don't add in old duplicates
                }
            }
        }
        prevLore.add(ChatColor.GOLD + lore);
        meta.setLore(prevLore);
        item.setItemMeta(meta);
        return true;
    }

    public static boolean itemHasRune(ItemStack itemStack, int runeID) {
        if (itemStack == null) {
            return false;
        }
        final ArrayList<RuneInfo> runesFromItem = RunecraftPlayer.getRunesFromItem(itemStack);
        for (RuneInfo runeInfo : runesFromItem) {
            if (runeInfo.runeID == runeID) return true;
        }
        return false;
    }

    /** This does a generic scan through the whole inventory for Tool Rune Lore.  Useful for items that work
     * by simply being in your inventory. Example: Inheritance
     * @param player
     * @param runeCode
     * @return true if they have an item somewhere in their inventory with that rune on it
     */
    public static boolean playerIsCarryingRune(Player player, int runeCode) {
        for(ItemStack item : player.getInventory()) {
            if(itemHasRune(item, runeCode))
                return true;
        }
        return false;
    }

    public void poke(final RunePlayer player, WorldXYZ location, ActionType triggerType) throws NotEnoughRunicEnergyException {
        boolean runeWasUsed = false;
        final RuneWorld world = location.getRuneWorld();
        switch(runeID){
            default:
                Logger.fine("Poked Tool Rune with no functionality: " + name);
                break;
            case IDENTIFIER: { // check tier of clicked block
                MaterialData materialData = location.getMaterialData();
                Material material = location.getMaterial();
                EnergyReservoir playerReservoir = EnergyReservoir.get(player);
                player.sendMessage(YELLOW + material.name() + " is a tier " + RuneRegistry.blockTier[material.getId()] //old tiers
                        + " block, its base energy is " + Numbers.beautify(Tiers.getEnergy(materialData)) + " energy, currently it's worth "
                        + Numbers.beautify(playerReservoir.getMaterialWorth(materialData)) + " to you.");
                playerReservoir.notifyTotalEnergy();
            }
            break;
            case DEPTHPAPER: {
                location.face = 0; //down
                WorldXYZ found = Runecraft_MAIN.scanForAirInDirection(location, location.getFacingDirection());
                Material foundBlock = normalizeBlock(Material.getMaterial(found.inkBlock));
                // tell the player what we found
                int depth = (int) location.distance(found);
                switch (foundBlock.getId()) {
                    case STATIONARY_WATER:     // water
                        player.sendMessage(ChatColor.BLUE + "Water swells and flushes the paper.");
                        player.sendMessage(ChatColor.BLUE + "The bleeding ink shows the number " + depth + ".");
                        break;
                    case STATIONARY_LAVA:    // lava
                        player.sendMessage(ChatColor.RED + "The paper has caught fire.");
                        break;
                    case AIR:     // space to stand
                        if (depth < 10) {
                            player.sendMessage(ChatColor.GREEN + "The digit " + depth + " appears on the paper.");
                        } else {
                            player.sendMessage(ChatColor.GREEN + "Some digits appear on the paper. You read the number " + depth + ".");
                        }
                        break;
                    case BEDROCK:    // no space or bedrock
                    default:
                        player.sendMessage(ChatColor.DARK_GRAY + "The paper turns ink-black.");
                        break;
                }
            }
            break;
            case LASSO: {
//                if (Permissions.notifyIfBlockedByWard(player, RuneInfo.WardType.Tool)) {
//                    fine(player, "Blocked " + name);
//                    break;
//                }
//                runeWasUsed = false;
//                int[] tr = {0,0,0};//TODO: replace  nasty_old_raytracing(world, player, true);
//                for (RunePlayer p : world.getMobs(tr[0], tr[1], tr[2], 6, 6, 6, true)) {
//                    if (p.getName().equals(player.getName())) continue;
//                    double yaw = Math.toRadians(player.yaw());
//                    double pitch = Math.toRadians(player.pitch());
//                    double x = -Math.sin(yaw);
//                    double y = -Math.sin(pitch);
//                    double z = Math.cos(yaw);
//                    p.setVelocity(-x * 2, -y * 2, -z * 2);
//                    p.setCurrentFallDistance(0);
//                    runeWasUsed = true;
//                }
            }
            break;
            case SPRINGSTRING: {
                runeWasUsed = true;
                double yaw = Math.toRadians(player.yaw());
                double pitch = Math.toRadians(player.pitch());
                double x = -Math.sin(yaw);
                double y = -Math.sin(pitch);
                double z = Math.cos(yaw);
                Energy.spendPlayerEnergy(player, Tiers.movementPerMeterCost * 5); //similar to teleportation
                player.setVelocity(x, y + 0.2, z);
                player.setCurrentFallDistance(-5);
            }
            break;
            case FLOTILLA: {
                do {// set boat above water
                    location.bump(Vector3.UP);
                } while (location.getBlockID() == WATER || location.getBlockID() == STATIONARY_WATER);
                if (location.offset(Vector3.DOWN).getBlockID() == WATER
                        || location.offset(Vector3.DOWN).getBlockID() == STATIONARY_WATER) {
                    Energy.spendPlayerEnergy(player, craftingCost + getEnergy(Material.BOAT));
                    world.spawnBoat(location.x(), location.y(), location.z());
                    runeWasUsed = true;
                }
            }
            break;
            case SMELTER:{
                MaterialData dropItem = null;
                switch (location.getBlockID()) {
                    case STONE:
                    case COBBLESTONE:
                        final int meta = world.getMeta(location.x(), location.y(), location.z());
                        dropItem = new MaterialData(Material.STONE, (byte) meta);
                        break;
                    case SAND:
                        dropItem = new MaterialData(Material.GLASS);
                        break;
                    case GOLD_ORE:
                        dropItem = new MaterialData(Material.GOLD_INGOT);
                        break;
                    case IRON_ORE:
                        dropItem = new MaterialData(Material.IRON_INGOT);
                        break;
                    case LOG:
                    case LOG_2:
                        dropItem =  new MaterialData(Material.COAL, (byte)1); //should be charcoal, but close enough
                        break;
                    case CLAY: // clay block -> brick
                        dropItem = new MaterialData(Material.BRICK);
                        break;
                }
                if(dropItem != null && player.clearBlock(location, this)){ //checks permissions
                    runeWasUsed = true;
                    Energy.spendPlayerEnergy(player, smeltingCost);
//                    ItemStack stack = dropItem.toItemStack();
                    ItemStack stack = new ItemStack(dropItem.getItemTypeId(), 1, dropItem.getData());
                    location.dropItem(stack);
                }
                break;
            }
            case TEMPESTTRIGGER: {
                if ((System.currentTimeMillis() - tempestLastUse) < 1 * 30 * 1000) {
                    player.sendMessage(RED + "The aether cannot change the weather at this time.");
                    break;
                }
                world.setWeather(0);
                Bukkit.broadcastMessage(GREEN + "The weather changes by the forces of " + ChatColor.GOLD + player.getName() + ".");
                tempestLastUse = System.currentTimeMillis();
                Energy.spendPlayerEnergy(player, 1000);
                break;
            }
            case RUBRIK: {
                File rubrikFile = null;
                ItemMeta meta = player.getPlayer().getItemInHand().getItemMeta();
                if (meta.hasLore()) {
                    for (String lore : meta.getLore()) {
                        if (lore.toLowerCase().contains(runeIdToRuneNameTable.get(RUBRIK).toLowerCase())) {//or lore.toLowerCase()
                            final String split = lore.split(":")[1];
                            rubrikFile = Faith.getRubrikFileNameFromEnchantName(split);
                            Logger.fine(rubrikFile.getName());
                            break;
                        }
                    }
                }
                if (rubrikFile == null || rubrikFile.exists() == false) {
                    player.sendMessage(RED + "That rubrik no longer exists.");
                    return;
                }
                try {
                    Faith.importFaithFromFile(rubrikFile, location, player, this, true);
                    EnergyReservoir.get(player).notifyTotalEnergy();
                } catch (NotEnoughRunicEnergyException e) {
                    player.sendMessage(RED + "You don't have enough energy you need " + Numbers.beautify(e.getMissingEnergy()));
                }
                break;
            }
            case PERMANENCE: {
                //Does nothing here.
            }
            case NEW_ZEERIX:
            case NEW_ZEERIX2: {
                //ItemStack itemInHand = player.getPlayer().getItemInHand();
                break;
                /*ItemMeta meta = player.getPlayer().getItemInHand().getItemMeta();
                ZeerixChest zeerixChest = null;
                int zeerixID = -1;
                if (meta.hasLore()) {
                    for (String lore : meta.getLore()) {
                        if (lore.toLowerCase().contains(runeIdToRuneNameTable.get(runeID).toLowerCase())) {//or lore.toLowerCase()
                            final String split = lore.split(":")[1];
                            zeerixID = Integer.valueOf(split.replaceAll("\\s", ""));
                            fine("ID " + zeerixID);
                            zeerixChest = ZeerixChest.getZeerixFromID(zeerixID);
                            break;
                        }
                    }
                }
                if (zeerixChest == null) {
                    fine("Zeerix broken");
                    //ZeerixChest.zeerixMap.remove(ZeerixChest.getZeerixFromID(zeerixID));
                    //TODO remove rune from item.
                    return;
                }
                zeerixChest.moveTo(location);*/
            }
        }
    }
}



//public class ToolRune {
//}
