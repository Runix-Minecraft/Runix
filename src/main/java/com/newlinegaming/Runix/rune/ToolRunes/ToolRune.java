package com.newlinegaming.Runix.rune.ToolRunes;

import java.util.*;

import com.newlinegaming.Runix.AbstractRune;
import com.newlinegaming.Runix.RunixPlayer;
import com.newlinegaming.Runix.WorldXYZ;
import com.newlinegaming.Runix.energy.NotEnoughRunicEnergyException;
import com.newlinegaming.Runix.handlers.RuneHandler;
import com.newlinegaming.Runix.utils.ActionType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumChatFormatting;

import static com.newlinegaming.Runix.utils.ActionType.TP_BROKEN;
import static com.newlinegaming.Runix.utils.ActionType.TP_CATCHALL;

/**
 * Created by Josiah on 6/13/2015.
 */
public abstract class ToolRune extends AbstractRune {
    public Set<ActionType> triggers = new HashSet<>();

    public ToolRune(String name, ActionType[] triggerType, String message) {
        super(name, message);
        this.triggers = new HashSet<>(Arrays.asList(triggerType));
        consumedBlocksGrantEnergy = true;
    }

    public static boolean doToolRunes(ActionType triggerType, RunixPlayer player, WorldXYZ location,
                                      boolean runeFound, boolean secondaryActivation) {
        try {
            for (ToolRune rune : getToolRunesFromHeldItem(player)) {
                if(!secondaryActivation ) { //It's important to disinclude runes that could cause a cascae chain reaction
                    if (triggerType == TP_CATCHALL || rune.triggers.contains(triggerType)) {
                        runeFound = triggerType != TP_BROKEN;
                        rune.poke(player, location, triggerType);
                    }
                }
            }
        } catch (Exception ignored) { //TODO: NotEnoughRunicEnergy
            aetherSay(player, ignored.getMessage());
        }
        return runeFound;
    }

    private static Iterable<ToolRune> getToolRunesFromHeldItem(RunixPlayer player) {
        ItemStack item = player.getPlayer().getHeldItem();
        return getRunesFromItem(item);
    }

    private static ArrayList<ToolRune> getRunesFromItem(ItemStack item) {
        ArrayList<ToolRune> allRunes = new ArrayList<>();
        if(item == null)
            return allRunes;

        for (String lore : getLore(item)) {
            lore = lore.substring(2).toLowerCase();//first two unicode characters are color styling
            for (AbstractRune value : RuneHandler.getInstance().runeRegistry) {
                if (lore.contains(value.runeName.toLowerCase())) { //this iteration is a bit cumbersome http://stackoverflow.com/questions/1383797/java-hashmap-how-to-get-key-from-value
                    if(value instanceof ToolRune){
                        allRunes.add(((ToolRune)value).fromLore(lore));
                        break;
                    }
                }
            }
        }
        return allRunes;
    }

    public abstract ToolRune fromLore(String lore);

    private static Iterable<String> getLore(ItemStack item) {
        NBTTagCompound top = item.getTagCompound();
        ArrayList<String> lores = new ArrayList<>();
        if (top.hasKey("display", 10)) {
            NBTTagCompound nbttagcompound = top.getCompoundTag("display");
            if (nbttagcompound.func_150299_b("Lore") == 9) {
                NBTTagList nbttaglist1 = nbttagcompound.getTagList("Lore", 8);
                if (nbttaglist1.tagCount() > 0) {
                    for (int j = 0; j < nbttaglist1.tagCount(); ++j) {
                        lores.add(nbttaglist1.getStringTagAt(j));
                    }
                }
            }
        }
        return lores;
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
     * @param additionalInfo Additional info about the enchant
     * @param holder Player who owns the tool that is getting enchanted.
     * @return true if the enchant was successful, false if the player is holding wrong item or item already has this enchant.
     */
    public static boolean addToolRune(ToolRune rune, String additionalInfo, RunixPlayer holder) {
        if(!holder.isPlayer())
            return false;
        String loreText = additionalInfo.isEmpty() ? rune.runeName : rune.runeName + " : " + additionalInfo;
        ItemStack inHand = holder.getPlayer().getHeldItem();
        if(inHand != null) {
            if (engraveToolRuneLore(loreText, rune, inHand) == false) { //active ingredient is here
                aetherSay(holder, EnumChatFormatting.GREEN + "This tool already has this enchant.");
                return false;
            }
            aetherSay(holder, EnumChatFormatting.GREEN + rune.activationMessage);
        } else {
            aetherSay(holder, EnumChatFormatting.GREEN + "Please use an item. This does not work on your hand.");
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

    public static boolean engraveToolRuneLore(String lore, ToolRune rune, ItemStack item) {
        if(item == null) return true;
        ArrayList<String> prevLore = new ArrayList<>();
        for (String oldLore : getLore(item)) {
            //#2 Fixed the case where Recalls could be stacked on one item.  Use newest instead.
            if (!oldLore.toLowerCase().contains(rune.runeName.toLowerCase())) {
                prevLore.add(oldLore); //don't add in old duplicates
            }
        }
        prevLore.add(EnumChatFormatting.GOLD + lore);
        setLore(item, prevLore);
        return true;
    }

    private static void setLore(ItemStack item, ArrayList<String> lore) {
        item.setTagInfo("Runix", new NBTTagShort((short) 1)); // Random filler to ensure tag exists
        NBTTagCompound top = item.getTagCompound();
        NBTTagCompound displayTag = top.getCompoundTag("display");

//        displayTag.getTagList("Lore", 9);
        NBTTagList newTagList = new NBTTagList();
        for(String line : lore){
            newTagList.appendTag(new NBTTagString(line));
        }
        displayTag.setTag("Lore", newTagList);
        top.setTag("display", displayTag);
//        NBTTagList nbttaglist1 = displayTag.getTagList("Lore", 8);
//        if (nbttaglist1.tagCount() > 0) {
//            for (int j = 0; j < nbttaglist1.tagCount(); ++j) {
//                lores.add(nbttaglist1.getStringTagAt(j));
//            }
//        }
        System.out.println("Wrote" + lore);
    }

//    public static boolean itemHasRune(ItemStack itemStack, String runeName) {
//        if (itemStack == null) {
//            return false;
//        }
//        final ArrayList<ToolRune> runesFromItem = getRunesFromItem(itemStack);
//        for (AbstractRune runeInfo : runesFromItem) {
//            if (Objects.equals(runeInfo.runeName, runeName)) return true;
//        }
//        return false;
//    }

    /* * This does a generic scan through the whole inventory for Tool Rune Lore.  Useful for items that work
     * by simply being in your inventory. Example: Inheritance
     * @param player
     * @param runeCode
     * @return true if they have an item somewhere in their inventory with that rune on it
     */
//    public static boolean playerIsCarryingRune(RunixPlayer player, int runeCode) {
//        for(ItemStack item : player.getInventory()) {
//            if(itemHasRune(item, runeCode))
//                return true;
//        }
//        return false;
//    }

    public abstract void poke(final RunixPlayer player, WorldXYZ location, ActionType triggerType) throws NotEnoughRunicEnergyException;
}



//public class ToolRune {
//}
