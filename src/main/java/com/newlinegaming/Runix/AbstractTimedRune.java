package com.newlinegaming.Runix;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;

public abstract class AbstractTimedRune extends PersistentRune {

//    RuneTimer instance = null;
    
    protected AbstractTimedRune(){}
    protected AbstractTimedRune(WorldXYZ coords, EntityPlayer player2, String name) {
        super(coords, player2, name);
        
    }

    /**
     * This registers the rune as being actively updated.  Forge (thru RuneTimer) will call
     * onUpdateTick() every xTicks from here on out until it is turned off.  There are
     * 20 ticks per second.
     * @param xTicks number of ticks to wait between calls.  20 ticks = 1 second
     */
    protected void updateEveryXTicks(int xTicks) {
	RuneTimer instance = new RuneTimer(this, xTicks);
    	FMLCommonHandler.instance().bus().register(instance);
    }

    //To completely remove timers once they're done : g
//    @Override
//    public void kill() {
//    	if(instance != null) {
//    		MinecraftForge.EVENT_BUS.unregister(instance);
//    		instance = null;
//    	}
//    	super.kill();
//    }//    At the moment, RunecraftRune simply pauses itself by not responding to onUpdateTick();

    
    protected abstract void onUpdateTick(EntityPlayer player);

}
