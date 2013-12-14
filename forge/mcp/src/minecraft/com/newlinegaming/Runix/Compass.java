package com.newlinegaming.Runix;

/** Josiah: I'm just writing some notes down as code.  This hasn't been tested yet. */

import net.minecraft.network.packet.Packet3Chat;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;


public class Compass extends Block //extend Rune
{
    public Compass(int id, Material material) {
		super(id, material);
		this.setCreativeTab(Runix.TabRunix);
	}

	int[][][] blockPattern = new int [][][] 
            {{{4,0,4},
              {0,4,0},
              {4,0,4}}};

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    @Override
    public void onBlockAdded(World world, int worldX, int worldY, int worldZ) { //TODO: world should be type World
        boolean createdRune = checkForAnyRunePattern(world, worldX, worldY, worldZ);
        MinecraftServer.getServer().getConfigurationManager().sendPacketToAllPlayers(new Packet3Chat("Block Added", false));
        if(createdRune)
        	MinecraftServer.getServer().getConfigurationManager().sendPacketToAllPlayers(new Packet3Chat("Rune Created", false));
    }

    private boolean checkForAnyRunePattern(World world, int worldX, int worldY, int worldZ)
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
