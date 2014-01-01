package com.newlinegaming.Runix;

import java.util.ArrayList;
import java.util.Arrays;

public class Vector3{// extends paulscode.sound.Vector3D.{
    public static final Vector3 UP =    new Vector3(0, 1,0);
    public static final Vector3 DOWN =  new Vector3(0,-1,0);
    public static final Vector3 NORTH = new Vector3(0,0,-1);
    public static final Vector3 EAST =  new Vector3( 1,0,0);
    public static final Vector3 SOUTH = new Vector3(0,0, 1);
    public static final Vector3 WEST =  new Vector3(-1,0,0);
    //These map to the minecraft block face, such that you're standing on the side they are pointing
    public static final Vector3[] facing = {DOWN, UP, NORTH, SOUTH, WEST, EAST};
    //use like this: WorldXYZ point = point.offset(Vector3.facing[point.face])
    public static final String[] faceString = {"DOWN", "UP", "NORTH", "SOUTH", "WEST", "EAST"};
    public static final ArrayList<Integer> xzRotationOrder = 
            (ArrayList<Integer>) Arrays.asList(2, 5, 3, 4);  
    
    int x;
    int y;
    int z;
    
    public Vector3(int mx, int my, int mz){
        x = mx;
        y = my;
        z = mz;
    }
    
    /** Returns a difference vector such that reference + vector = destination */
    public static Vector3 offset(WorldXYZ reference, WorldXYZ destination){
        return new Vector3(destination.posX - reference.posX, 
                destination.posY - reference.posY,
                destination.posZ - reference.posZ);
    }
    
}
