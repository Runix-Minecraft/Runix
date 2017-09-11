package com.newlinegaming.Runix.workers;

import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;

public interface IBlockWorker {
    
    void doWork(ServerTickEvent event);
    
    boolean isFinished();
    
    void scheduleWorkLoad();
    
//    void render();
    
}
