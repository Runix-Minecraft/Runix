package com.newlinegaming.Runix.rune.ToolRunes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.newlinegaming.Runix.AbstractRune;
import com.newlinegaming.Runix.RunixPlayer;
import com.newlinegaming.Runix.WorldXYZ;
import com.newlinegaming.Runix.energy.NotEnoughRunicEnergyException;
import com.newlinegaming.Runix.utils.ActionType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import static com.newlinegaming.Runix.utils.ActionType.TP_BROKEN;
import static com.newlinegaming.Runix.utils.ActionType.TP_CATCHALL;

/**
 * Created by Josiah on 6/13/2015.
 */
public abstract class ToolRune extends AbstractRune {
    private static final long serialVersionUID = -8433219562224217690L;
    public Set<ActionType> triggers = new HashSet<>();
//    private long tempestLastUse = 0;

    public ToolRune(String name, ActionType[] triggerType, String message) {
        super(name, message);
        this.triggers = new HashSet<>(Arrays.asList(triggerType));
        consumedBlocksGrantEnergy = true;
    }

    public ToolRune(AbstractRune runeInfo) {
        throw new NotImplementedException();

    }

    public static boolean doToolRunes(ActionType triggerType, RunixPlayer player, WorldXYZ location,
                                      boolean runeFound, boolean secondaryActivation) {
        try {
            for (ToolRune rune : player.getToolRunesFromHeldItem()) {
                if(!secondaryActivation ) { //It's important to disinclude runes that could cause a cascae chain reaction
                    if (triggerType == TP_CATCHALL || rune.triggers.contains(triggerType)) {
                        runeFound = triggerType != TP_BROKEN;
                        rune.use(player, location, triggerType);
                    }
                }
            }
        } catch (Exception ignored) { //TODO: NotEnoughRunicEnergy
            aetherSay(player.getEntity(), ignored.getMessage());
        }
        return runeFound;
    }

    /**
     * Enchants a tool with a tool rune.
     * @param runeID ID of rune to enchant the tool with.
     * @param holder Player who owns the tool that is getting enchanted.
     * @return true if the enchant was successful, false if the player is holding wrong item or item already has this enchant.
     */
//    public static boolean addToolRune(int runeID, RunixPlayer holder) {
//        return addToolRune(runeID, "", holder);
//    }

    /**
     * Enchants a tool with a tool rune.
     * @param runeID ID of rune to enchant the tool with.
     * @param additionalInfo Additional info about the enchant
     * @param holder Player who owns the tool that is getting enchanted.
     * @return true if the enchant was successful, false if the player is holding wrong item or item already has this enchant.
     */
    public static boolean addToolRune(int runeID, String additionalInfo, RunixPlayer holder) {
        if(!holder.isPlayer())
            return false;
        AbstractRune rune = RuneRegistry.registeredRunes.get(runeID);
        String loreText = additionalInfo.isEmpty() ? rune.name : rune.name + " : " + additionalInfo;
        ItemStack inHand = holder.getPlayer().getItemInHand();
        if(inHand.getType() != Material.AIR) {
            if (engraveToolRuneLore(loreText, rune, inHand) == false) { //active ingredient is here
                holder.sendMessage(EnumChatFormatting.GREEN + "This tool already has this enchant.");
                return false;
            }
            holder.sendMessage(EnumChatFormatting.GREEN + rune.activationMessage);
        } else {
            holder.sendMessage(EnumChatFormatting.GREEN + "Please use an item. This does not work on your hand.");
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

    public static boolean engraveToolRuneLore(String lore, AbstractRune rune, ItemStack item) {
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
        final ArrayList<AbstractRune> runesFromItem = RunixPlayer.getRunesFromItem(itemStack);
        for (AbstractRune runeInfo : runesFromItem) {
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
    public static boolean playerIsCarryingRune(RunixPlayer player, int runeCode) {
        for(ItemStack item : player.getInventory()) {
            if(itemHasRune(item, runeCode))
                return true;
        }
        return false;
    }

    public abstract void poke(final RunixPlayer player, WorldXYZ location, ActionType triggerType) throws NotEnoughRunicEnergyException;
}



//public class ToolRune {
//}
