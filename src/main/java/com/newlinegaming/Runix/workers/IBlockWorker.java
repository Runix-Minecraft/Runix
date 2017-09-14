package com.newlinegaming.Runix.workers;


import net.minecraftforge.fml.common.gameevent.TickEvent;

interface IBlockWorker {
    
    void doWork(TickEvent.ServerTickEvent event);
    
    boolean isFinished();
    
    void scheduleWorkLoad();
    
//    void render();
    
}
