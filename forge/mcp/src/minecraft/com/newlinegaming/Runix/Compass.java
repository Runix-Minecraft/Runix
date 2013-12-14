/** Josiah: I'm just writing some notes down as code.  This hasn't been tested yet. */

import net.minecraft.world.World;
import net.minecraft.block.Block;


public class Compass //extend Rune
{
    int[][][] blockPattern = new int [][][] 
            {{{4,0,4},
              {0,4,0},
              {4,0,4}}};

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    //@Override
    public void onBlockAdded(int world, int worldX, int worldY, int worldZ) { //TODO: world should be type World
        boolean createdRune = checkForAnyRunePattern(world, worldX, worldY, worldZ);
    }

    private boolean checkForAnyRunePattern(int world, int worldX, int worldY, int worldZ)
    {
        for(int y = 0; y < blockPattern.length; y++)
        {
            for(int z = 0; z < blockPattern[y].length; z++){
                
                for(int x = 0; x < blockPattern[y][z].length; x++)
                {
                    //World coordinates + relative offset + half the size of the rune (for middle)
                }
            }
        }
        return true;
    }
}
