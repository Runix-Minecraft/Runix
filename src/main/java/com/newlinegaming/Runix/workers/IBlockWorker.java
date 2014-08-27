package com.newlinegaming.Runix.workers;

import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;

public interface IBlockWorker {
    
    public void doWork(ServerTickEvent event);
    
    public boolean isFinished();
    
    public void scheduleWorkLoad();
    
//    void render();
    
}
