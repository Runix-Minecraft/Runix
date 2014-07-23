package com.newlinegaming.Runix;

import static org.junit.Assert.assertTrue;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import org.junit.Test;

public class Tests
{

    @Test
    public void SigBlockEqualityTest()
    {
        Block a = Blocks.redstone_wire;
        SigBlock b = new SigBlock(a, 0);
//        assertTrue( a == b );
        assertTrue( b.equals(a) );
    }

}
