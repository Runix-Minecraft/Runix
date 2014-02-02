package com.newlinegaming.Runix;

import java.util.HashSet;
import java.util.LinkedList;

import net.minecraft.world.World;

public class Util_SphericalFunctions {

    public static boolean radiusCheck(int x, int y, int z, int rd) {
        return ((x * x) + (y * y) + (z * z) < ((rd + 0.5) * (rd + 0.5)));
    }
    
	public static HashSet<WorldXYZ> getSphere (WorldXYZ coords, int radius) 
	{
		World world = coords.getWorld();
		HashSet<WorldXYZ> returnvalues = new HashSet<WorldXYZ>();
		for (int y = -radius-1; y < radius+1; y++)  {
		    for (int z = -radius-1; z < radius+1; z++){
		        for (int x = -radius-1; x < radius+1; x++)
		        {
		            if(radiusCheck(x,y,z, radius))
		            {
		                returnvalues.add(new WorldXYZ(world,coords.posX+x,coords.posY+y,coords.posZ+z));
		            }
		        }
		    }
		}
		return returnvalues;
	}
	
	public static HashSet<WorldXYZ> getShell(WorldXYZ center, int radius){
	    //Josiah: I wrote this so it's probably got holes...
	    HashSet<WorldXYZ> bigSphere = getSphere(center, radius);
	    HashSet<WorldXYZ> smallerSphere = getSphere(center, radius-1);
        bigSphere.removeAll(smallerSphere);
	    return bigSphere;
	}
}
