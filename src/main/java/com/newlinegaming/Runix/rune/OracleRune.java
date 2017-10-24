package com.newlinegaming.Runix.rune;

import com.newlinegaming.Runix.AbstractRune;
import com.newlinegaming.Runix.PersistentRune;
import com.newlinegaming.Runix.WorldXYZ;
import com.newlinegaming.Runix.handlers.RuneHandler;
import com.newlinegaming.Runix.helper.TierHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class OracleRune extends AbstractRune {
    
    public OracleRune() {
        runeName = ("Oracle Rune");
    }

    @NotNull
    @Override
    protected Block[][][] runicTemplateOriginal() {
        
        Block RED = Blocks.REDSTONE_WIRE;
        
        return new Block[][][] {{
            {RED, RED ,RED},
            {RED, TIER, RED},
            {RED, RED, RED}
            
        }};
    }

    @Override
    public boolean isFlatRuneOnly() {
        return true;
    }

    @Override
    public void execute(@NotNull WorldXYZ coords, @NotNull EntityPlayer player) {
      ItemStack toolUsed = player.getHeldItemMainhand();
      
      if(toolUsed !=null && toolUsed.getItem() == Items.GOLDEN_SWORD ||
              toolUsed !=null && toolUsed.getItem() == Items.STONE_SWORD ||
              toolUsed !=null && toolUsed.getItem() == Items.WOODEN_SHOVEL ||
              toolUsed !=null && toolUsed.getItem() == Items.DIAMOND_SWORD) {
          
          ArrayList<PersistentRune> d = RuneHandler.getInstance().getAllRunesByPlayer(player);
          aetherSay(player, "Current enchantments: " + Integer.toString(d.size()));
          for (PersistentRune r : d) {
              aetherSay(player, r.runeName + " Energy: "+ r.energy);
          }
          
      } else {
          
          Block block = coords.getBlock();
          
          aetherSay(player, TextFormatting.RED +block.getLocalizedName());
          aetherSay(player, "Tier: "  + TierHelper.getTierNumber(block) + ".");
          aetherSay(player, "Energy: " + TierHelper.getEnergy(block) + ".");
          aetherSay(player, "Properties: " + (TierHelper.isNatural(block)? "Not Conductive" : "Conductive")
                  + ", " + (TierHelper.isCrushable(block)? "Crushable." : "Not Crushable."));
      }
      
    }
}
