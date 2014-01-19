package com.newlinegaming.Runix.block;

import com.newlinegaming.Runix.SigBlock;
import com.newlinegaming.Runix.WorldXYZ;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;


public class BlockRecord implements Delayed{
    long expirationTimeInMillis = 0; //exact expiration time is set when the object is constructed
    WorldXYZ loc;
    SigBlock block;
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