package com.newlinegaming.Runix;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/** This class keeps a record of a block at a location so it can be restored after a set delay.
 * It can be used by Domain to rebuild blocks destroyed by Creepers, Endermen or other players.
 * 
 * Use it like this:
    protected static DelayQueue<BlockRecord> phasedBlocks = new DelayQueue<BlockRecord>();
 * 
    private void phaseBlockAt(WorldXYZ coords) {
        BlockRecord record = new BlockRecord(60, coords, coords.getSigBlock());
        phasedBlocks.add(record);
    }
 *
 *  //ON BLOCK DESTROY EVENT
    phaseBlockAt(new WorldXYZ( x, y, z));
 *  
 */


public class BlockRecord implements Delayed{
    long expirationTimeInMillis = 0; //exact expiration time is set when the object is constructed
    public WorldXYZ loc;
    public SigBlock block;
    
    public BlockRecord(WorldXYZ coords)
    {
        expirationTimeInMillis = System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(60, TimeUnit.SECONDS);
        loc = coords;
        block = coords.getSigBlock();
    }
    
    public BlockRecord(int delayInSeconds, WorldXYZ coords, SigBlock b){
        expirationTimeInMillis = System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(delayInSeconds, TimeUnit.SECONDS);
        loc = coords;
        block = b;
    }
    
    @Override
    public int compareTo(Delayed arg0) {
        return (int) (getDelay(TimeUnit.SECONDS) - arg0.getDelay(TimeUnit.SECONDS));
    }
    @Override
    public long getDelay(TimeUnit unit) {
        //the difference is simply figured from the current system time
        return unit.convert(expirationTimeInMillis - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }
}