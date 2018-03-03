package com.newlinegaming.Runix.rune.ToolRunes;

import java.util.StringTokenizer;

/**
 * Created by Josiah on 6/27/2015.
 */
public class RecallRune extends ToolRune {

    public RecallRune(int runeID, String name, ActionType[] triggerType, String message) {
        super(runeID, name, triggerType, message, AIR);
    }

    public RecallRune(String lore) {
        super(registeredRunes.get(RECALL));
        int metaStart = lore.indexOf(":");
        StringTokenizer st = new StringTokenizer(lore.substring(metaStart+1), ",)( ");
        int[] coords = new int[st.countTokens()];
        int i = 0;
        while (st.hasMoreElements()) {
            coords[i] = Integer.parseInt((String) st.nextElement());
            i++;
        }
        destination = new WorldXYZ(coords[0], coords[1], coords[2], coords[3]);
    }

    @Override
    public void poke(RunePlayer player, WorldXYZ location, ActionType triggerType) throws NotEnoughRunicEnergyException {
        if(destination == null){
            Logger.fine("ERROR: Received a Recall with no additional info.");
            return;
        }
        RunePlayer target = player;
        if(triggerType == ActionType.TP_RIGHTCLICKAIR || triggerType == ActionType.TP_RIGHTCLICK){
            target = ((RunecraftPlayer) player).getTargetLivingEntity(2);//check entity player is looking at player = entity
            if(target instanceof RunecraftPlayer){ // other players get a choice
                Logger.fine("Recall Found a player");
                TeleportationOffer.offer(target, player, destination, this, false);
                return;
            }
        }
        if (target != null && target.getEntity() instanceof Player) { //uniqueInstance from swing
            Teleporters.regularTeleport(target, player, destination, this);//TODO: better permission checking on banish through patron parameter
            Logger.fine(player.getName() + " is trying to teleport " + target.getEntity().getName());
            // optionally adds potion effect "Weakness" to player for 30 seconds
            if (Permissions.configOptionOn("recall-weakness"))
                player.applyPotion(PotionEffectType.WEAKNESS, 30 * 20, 2);
        }
        if(target != null && target.getEntity() instanceof Animals){ //can't teleport hostile mobs
            Teleporters.regularTeleport(target, player, destination, this);//TODO: better permission checking on banish through patron parameter
            Logger.fine(player.getName() + " is trying to teleport " + target.getEntity().getName());
            Logger.fine(player.getName() + " successfully teleported " + target.getEntity().getName() + ".");
            player.sendMessage("Successfully teleported " + target.getEntity().getName() + ".");
            target.applyPotion(PotionEffectType.REGENERATION, 30*20, 2);
        }
        if (target != null && (target.getEntity() instanceof Monster)){
            Logger.fine(player.getName() + " is trying to teleport " + target.getEntity().getName());
            Logger.fine(player.getName() + " failed to teleport " + target.getEntity().getName() + ".");
            player.sendMessage("I'm sorry, we can't let you teleport " + target.getEntity().getName() + ".");
        }
    }

}