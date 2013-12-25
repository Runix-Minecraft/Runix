package com.newlinegaming.Runix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Signature {
    
    public ArrayList<SigBlock> blocks;
    
    public Signature()
    {
        blocks = new ArrayList<SigBlock>();
    }

    public Signature(AbstractRune rune, WorldXYZ coords){
        blocks = new ArrayList<SigBlock>();
        
        int [][][] pattern = rune.blockPattern();
        for (int y = 0; y < pattern.length; y++) {
            for (int z = 0; z < pattern[y].length; z++) {
                for (int x = 0; x < pattern[y][z].length; x++) {
                    if( pattern[y][z][x] == AbstractRune.SIGR ){
                        WorldXYZ target = coords.offset(-pattern[y][z].length / 2 + x,  -y,  -pattern[y].length / 2 + z);
                        blocks.add(new SigBlock(target.getBlockId(), target.getMetaId() ));
                    }
                }
            }
        }
        rune.aetherSay(coords.worldObj, "Signature:" + blocks.toString());
    }
    
    public boolean equals(Signature other){
        if( blocks.size() != other.blocks.size() )
            return false;
        for( SigBlock b : blocks){
            if( Collections.frequency(other.blocks, b) != Collections.frequency(blocks, b))
                return false;
        }
//        System.out.println("Comparing:" + this + " =? " + other + " = " + answer);
        return true;
    }
    
    public String toString(){
        return blocks.toString();
    }
}
