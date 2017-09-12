package com.newlinegaming.Runix.workers;

import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;

interface IBlockWorker {
    
    void doWork(ServerTickEvent event);
    
    boolean isFinished();
    
    void scheduleWorkLoad();
    
//    void render();
    
}
