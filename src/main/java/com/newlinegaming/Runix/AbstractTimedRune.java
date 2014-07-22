package com.newlinegaming.Runix;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;

public abstract class AbstractTimedRune extends PersistentRune {

    public AbstractTimedRune(){}
    public AbstractTimedRune(WorldXYZ coords, EntityPlayer player2, String name) {
        super(coords, player2, name);
        
    }

    /**
     * This registers the rune as being actively updated.  Forge (thru RuneTimer) will call
     * onUpdateTick() every xTicks from here on out until it is turned off.  There are
     * 20 ticks per second.
     * @param xTicks number of ticks to wait between calls.  20 ticks = 1 second
     */
    protected void updateEveryXTicks(int xTicks) {
        TickRegistry.registerTickHandler(new RuneTimer(this, xTicks), Side.SERVER);
    }

    //To completely remove timers once they're done : g 
    public static void unbind(){
        if(INSTANCE != null){
            MinecraftForge.EVENT_BUS.unregister(instance);
            instance = null;
        }
    }
//    At the moment, RunecraftRune simply pauses itself by not responding to onUpdateTick();
    
    protected abstract void onUpdateTick(EntityPlayer player);

}
