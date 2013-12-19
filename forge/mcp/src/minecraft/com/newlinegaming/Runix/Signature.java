package com.newlinegaming.Runix;

import java.util.ArrayList;
import java.util.Arrays;

public class Signature {
    
    public ArrayList<Integer> IDs;
    public ArrayList<Integer> metas;
    
    public Signature()
    {
        IDs = new ArrayList<Integer>();
        metas = new ArrayList<Integer>();// Arrays.asList(0)
    }

    public Signature(AbstractRune rune, WorldCoordinates coords){
        IDs = new ArrayList<Integer>();
        metas = new ArrayList<Integer>();
        
        int [][][] pattern = rune.blockPattern();
        for (int y = 0; y < pattern.length; y++) {//TODO: Josiah: Fourth duplicate of this code == bad!
            for (int z = 0; z < pattern[y].length; z++) {
                for (int x = 0; x < pattern[y][z].length; x++) {
                    if( pattern[y][z][x] == AbstractRune.SIGR ){
                        WorldCoordinates target = coords.offset(-pattern[y][z].length / 2 + x,  -y,  -pattern[y].length / 2 + z);
                        IDs.add(new Integer(target.getBlockId()));
                        metas.add(new Integer(target.getMetaId()));
                    }
                }
            }
        }
        rune.aetherSay(coords.worldObj, "Signature:" + IDs.toString() + "    M" + metas.toString());
    }
    
    public boolean equals(Signature other){
        if( IDs.size() != other.IDs.size() || metas.size() != other.metas.size())
            return false;
        for(int i = 0; i < IDs.size(); ++i){
            //since these are Integer and not int, we need .equals instead of !=
            if( !IDs.get(i).equals(other.IDs.get(i)) || !metas.get(i).equals(other.metas.get(i)))
                return false;
        }
        return true;//Note: this will return true for 2 empty signatures
    }
}
