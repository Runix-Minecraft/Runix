package com.newlinegaming.Runix.workers;

public interface IBlockWorker {
    
    public void doWork();
    
    public boolean isFinished();
    
    public void scheduleNextWorkLoad();
    
//    void render();
    
}
