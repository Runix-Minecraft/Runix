package com.newlinegaming.Runix;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.world.World;

public class Util_SphericalFunctions {

	
    public static boolean radiusCheck(WorldXYZ loc, int radius) {
        if ((loc.posX * loc.posX) + (loc.posY * loc.posY) + (loc.posZ * loc.posZ) > (radius * radius)) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean radiusCheck(int x, int y, int z, int rd) {
        if ((x * x) + (y * y) + (z * z) > (rd * rd)) {
            return false;
        } else {
            return true;
        }

    }
    
	public static LinkedList<WorldXYZ> getSphere (WorldXYZ coords, int radius) 
	{
		World world = coords.getWorld();
		LinkedList<WorldXYZ> returnvalues = new LinkedList();
		for (int z = -radius; z <= radius; z++)
			for (int y = -radius; y < radius; y++)  //Josiah: perhaps you should put the y as the top loop so that it animates top to bottom instead of side to side
				for (int x = -radius; x < radius; x++)
				{
					if(radiusCheck(x,y,z, radius))
					{
						WorldXYZ point = new WorldXYZ(world,coords.posX+x,coords.posY+y,coords.posZ+z);
						returnvalues.add(point);
					}
				}
		return returnvalues;
	}
	
	public static LinkedList<WorldXYZ> getShell(WorldXYZ center, int radius){
	    //Josiah: I wrote this so it's probably got holes...
	    LinkedList<WorldXYZ> bigSphere = getSphere(center, radius);
	    LinkedList<WorldXYZ> smallerSphere = getSphere(center, radius-1);
        bigSphere.removeAll(smallerSphere);
	    return bigSphere;
	}
}
