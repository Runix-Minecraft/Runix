package com.newlinegaming.Runix;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import org.jetbrains.annotations.NotNull;

public class RuneTimer {

    private final AbstractTimedRune rune;
    private int currentTimer = 0;
    private int maxTimer = 20;

    RuneTimer(AbstractTimedRune r, int waitTicks){
        rune = r;
        currentTimer = 0;
        maxTimer = waitTicks;
    }
    
    @SubscribeEvent
    public void onPlayerTickEvent(@NotNull PlayerTickEvent event) {
        ++currentTimer;
        if(currentTimer >= maxTimer) {
            currentTimer = 0;
            if(!rune.disabled) {
                rune.onUpdateTick(event.player);
            }

        }
    }
}