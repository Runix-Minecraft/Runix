package com.newlinegaming.Runix.rune.ToolRunes;

import com.newlinegaming.Runix.RunixPlayer;
import com.newlinegaming.Runix.Vector3;
import com.newlinegaming.Runix.WorldXYZ;
import com.newlinegaming.Runix.energy.NotEnoughRunicEnergyException;
import com.newlinegaming.Runix.utils.ActionType;
import com.newlinegaming.Runix.utils.Teleporters;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import scala.tools.nsc.transform.patmat.Logic;

import java.util.StringTokenizer;

import static com.newlinegaming.Runix.utils.ActionType.TP_RIGHTCLICK;
import static com.newlinegaming.Runix.utils.ActionType.TP_RIGHTCLICKAIR;
import static com.newlinegaming.Runix.utils.ActionType.TP_SWING;

/**
 * Created by Josiah on 6/27/2015.
 */
public class RecallRune extends ToolRune {

    private WorldXYZ destination = null;

    public RecallRune() {
        super("Recall",
                     new ActionType[]{TP_SWING, TP_RIGHTCLICKAIR, TP_RIGHTCLICK},
                "You will be teleported here whenever you swing this item. Touch other creatures to teleport them instead.");
    }

    @Override
    protected Block[][][] runicTemplateOriginal() {
        Block TRCH = Blocks.redstone_torch;
        Block DUST = Blocks.redstone_wire;
        return new Block[][][] {{
                {DUST,DUST,DUST},
                {DUST,TRCH,DUST},
                {DUST,DUST,DUST}
        }};
    }

    @Override
    protected boolean isFlatRuneOnly() {
        return true;
    }

    /**
     * Psuedo constructor for ToolRune to use to get new instances based on tooltip text on ItemStack
     * @param lore
     * @return
     */
    public RecallRune fromLore(String lore) {
        RecallRune me = new RecallRune();
        int metaStart = lore.indexOf(":");
        StringTokenizer st = new StringTokenizer(lore.substring(metaStart+1), ",)( ");
        int[] coords = new int[st.countTokens()];
        int i = 0;
        while (st.hasMoreElements()) {
            coords[i] = Integer.parseInt((String) st.nextElement());
            i++;
        }
        me.destination = new WorldXYZ(coords[0], coords[1], coords[2], coords[3]);
        return me;
    }

    @Override
    public void poke(RunixPlayer player, WorldXYZ location, ActionType triggerType) throws NotEnoughRunicEnergyException {
        if(destination == null){
            //Logger.fine("ERROR: Received a Recall with no additional info.");
            return;
        }
        Teleporters.teleportPlayer(player.getPlayer(), destination);
//        RunixPlayer target = player;
//        if(triggerType == ActionType.TP_RIGHTCLICKAIR || triggerType == ActionType.TP_RIGHTCLICK){
//            target = ((RunixPlayer) player).getTargetLivingEntity(2);//check entity player is looking at player = entity
//            if(target instanceof RunixPlayer){ // other players get a choice
//                Logger.fine("Recall Found a player");
//                TeleportationOffer.offer(target, player, destination, this, false);
//                return;
//            }
//        }
//        if (target != null && target.getEntity() instanceof RunixPlayer) { //uniqueInstance from swing
//            Teleporters.teleportPlayer(player.getPlayer(), destination);
//            Logger.fine(player.getName() + " is trying to teleport " + target.getEntity().getName());
//        }
//        if(target != null && target.getEntity() instanceof Animals){ //can't teleport hostile mobs
//            Teleporters.regularTeleport(target, player, destination, this);//TODO: better permission checking on banish through patron parameter
//            Logger.fine(player.getName() + " is trying to teleport " + target.getEntity().getName());
//            Logger.fine(player.getName() + " successfully teleported " + target.getEntity().getName() + ".");
//            player.sendMessage("Successfully teleported " + target.getEntity().getName() + ".");
//            target.applyPotion(PotionEffectType.REGENERATION, 30*20, 2);
//        }
//        if (target != null && (target.getEntity() instanceof Monster)){
//            Logger.fine(player.getName() + " is trying to teleport " + target.getEntity().getName());
//            Logger.fine(player.getName() + " failed to teleport " + target.getEntity().getName() + ".");
//            player.sendMessage("I'm sorry, we can't let you teleport " + target.getEntity().getName() + ".");
//        }
    }

    @Override
    protected void execute(WorldXYZ coords, EntityPlayer player) {
        String destination = "(" + coords.posX + ", " + coords.posY + ", " + coords.posZ + ", " + coords.getDimensionNumber() + ")";
        addToolRune(this, destination, new RunixPlayer(player));
    }
}