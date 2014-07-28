package com.newlinegaming.Runix;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class RuneTimer {

    AbstractTimedRune rune;
    private int currentTimer;
    private int maxTimer = 20;

    RuneTimer(AbstractTimedRune r, int waitTicks){
        rune = r;
        currentTimer = 0;
        maxTimer = waitTicks;
    }
    
    @SubscribeEvent
    public void onWorldTickEvent(PlayerTickEvent event) {
    	++currentTimer;
    	if(currentTimer >= maxTimer) {
    		currentTimer = 0;
    		rune.onUpdateTick(event.player);

    	}
    }
    
    public String getLabel() {
        return null;
    }
    
}