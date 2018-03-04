package com.newlinegaming.Runix.utils;

import com.newlinegaming.Runix.AbstractRune;
import com.newlinegaming.Runix.Vector3;
import com.newlinegaming.Runix.WorldXYZ;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;

public class Teleporters {
    /**
     * This will safely teleport the player by scanning in the coords.face direction for 2 AIR blocks that drop the player
     * less than 20 meters onto something that's not fire or lava.
     * This method should be used for any teleport or similar move that may land the player in some blocks.
     * @param player being teleported
     * @param coords Target destination
     */
    public static void teleportPlayer(EntityPlayer player, WorldXYZ coords) {

        Vector3 direction = Vector3.facing[coords.face];
        for(int tries = 0; tries < 100; ++tries) {
            if( (coords.posY < 255 && coords.posY > 0) // coords are in bounds
                    && coords.getWorld().getBlock(coords.posX, coords.posY, coords.posZ) == Blocks.air
                    && coords.getWorld().getBlock(coords.posX, coords.posY+1, coords.posZ) == Blocks.air)//two AIR blocks
            {
                for(int drop = 1; drop < 20 && coords.posY-drop > 0; ++drop)//less than a 20 meter drop
                {//begin scanning downward
                    Block block = coords.getWorld().getBlock(coords.posX, coords.posY - drop, coords.posZ);
                    if(block != Blocks.air)
                    { //We found something not AIR
                        if (block == Blocks.lava || block == Blocks.flowing_lava//check for Lava, fire, and void
                                || block == Blocks.fire){//if we teleport now, the player will land on an unsafe block
                            break; //break out of the drop loop and proceed on scanning a new location
                        }
                        else if(coords.offset(0, -drop, 0).isSolid()){ //we're going to land on something solid, without dying
                            //distance should be calculated uses the Nether -> Overworld transform
                            WorldXYZ dCalc = new WorldXYZ(player);
                            if(player.worldObj.provider.isHellWorld  && !coords.getWorld().provider.isHellWorld){ //leaving the Nether
                                dCalc.posX *= 8;
                                dCalc.posZ *= 8;
                            }else if (!player.worldObj.provider.isHellWorld  && coords.getWorld().provider.isHellWorld){// going to the Nether
                                dCalc.posX /= 8;
                                dCalc.posZ /= 8;
                            }
                            //spendEnergy((int)( coords.getDistance(dCalc) * Tiers.movementPerMeterCost));

                            if(!coords.getWorld().equals(player.worldObj))// && !subject.worldObj.isRemote)
                                player.travelToDimension(coords.getWorld().provider.dimensionId);
                            player.setPositionAndUpdate(coords.posX+0.5, coords.posY, coords.posZ+0.5);
                            return;
                        }//we've found something that's not AIR, but it's not dangerous so just pass through it and keep going
                    }
                }
            }
            coords = coords.offset(direction);
        }
        AbstractRune.aetherSay(player, "There was no safe place to put your character.");
    }
//    public static boolean regularTeleport(RunixPlayer player, WorldXYZ destination, RuneInfo rune) {
//        return teleportPlayer(EntityPlayer player, WorldXYZ coords);
//    }
//
//    public static boolean regularTeleport(RunePlayer targetEntity, RunePlayer patron, WorldXYZ destination, RuneInfo rune){
//        if(destination != null){
//            safelyTeleportPlayer(targetEntity, patron, destination);
//        }
//        return true;
//    }
}
