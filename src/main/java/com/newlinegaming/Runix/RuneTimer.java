package com.newlinegaming.Runix;

import com.newlinegaming.Runix.handlers.ITickHandler;

import cpw.mods.fml.common.gameevent.TickEvent;


public class RuneTimer implements ITickHandler {

    AbstractTimedRune rune;
    private int currentTimer;
    private int maxTimer = 20;

    RuneTimer(AbstractTimedRune r, int waitTicks){
        rune = r;
        currentTimer = 0;
        maxTimer = waitTicks;
    }
    
//    @Override
//    public void tickStart(EnumSet<TickType> type, Object... tickData) {
//        if (type.equals(EnumSet.of(TickType.PLAYER))) {
//            ++currentTimer;
//            if( currentTimer >= maxTimer)
//            {
//                currentTimer = 0;
//                if(rune.disabled == false)
//                    rune.onUpdateTick((EntityPlayer) tickData[0]);
//            }
//        }
//    }


//    @Override
//    public String getLabel() {
//        return null;
//    }

    @Override
    public void onWorldTickEvent(TickEvent.WorldTickEvent event) {
        ++ currentTimer;
        if (currentTimer >= maxTimer) {
            currentTimer = 0;
            if (rune.disabled == false) {
//                rune.onUpdateTick((EntityPlayer) event[0]);
            }
        }
    }

    @Override
    public void onServerTickEvent(TickEvent.ServerTickEvent event) {

    }

    @Override
    public void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {

    }

    @Override
    public void onClientTickEvent(TickEvent.ClientTickEvent event) {

    }
}