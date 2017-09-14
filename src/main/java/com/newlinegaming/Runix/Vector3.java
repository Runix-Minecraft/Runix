package com.newlinegaming.Runix;

import net.minecraftforge.common.util.ForgeDirection;


public class Vector3 {
    public static final Vector3 UP =    new Vector3(0, 1,0);
    public static final Vector3 DOWN =  new Vector3(0,-1,0);
    public static final Vector3 NORTH = new Vector3(0,0,-1);
    public static final Vector3 EAST =  new Vector3( 1,0,0);
    public static final Vector3 SOUTH = new Vector3(0,0, 1);
    public static final Vector3 WEST =  new Vector3(-1,0,0);
    //These map to the minecraft block face, such that you're standing on the side they are pointing
    public static final Vector3[] facing = {DOWN, UP, NORTH, SOUTH, WEST, EAST};
    //use like this: WorldXYZ point = point.offset(Vector3.facing[point.face])
    public static final String[] faceString = {"Down", "Up", "North", "South", "West", "East"};
    //Converts a side to the opposite side. This is the same as XOR'ing it with 1.
    public static final int[] oppositeSide = new int[] {1, 0, 3, 2, 5, 4};

    public static final Vector3[] xzRotationOrder = {Vector3.NORTH, Vector3.EAST, Vector3.SOUTH, Vector3.WEST,};
    public static final Vector3[] xyRotationOrder = {Vector3.UP, Vector3.EAST, Vector3.DOWN, Vector3.WEST,};
    public static final Vector3[] yzRotationOrder = {Vector3.NORTH, Vector3.UP, Vector3.SOUTH, Vector3.DOWN,};
    
    public static final Vector3[] conductanceNeighbors = {
        new Vector3( 0, 1, 0),
        new Vector3( 0,-1, 0),
        new Vector3( 0, 0,-1),
        new Vector3( 1, 0, 0),
        new Vector3( 0, 0, 1),
        new Vector3(-1, 0, 0),

        new Vector3( 1, 1, 0),
        new Vector3(-1, 1, 0),
        new Vector3( 0, 1, 1),
        new Vector3( 0, 1,-1),

        new Vector3( 1, 0, 1),
        new Vector3(-1, 0, 1),
        new Vector3( 1, 0,-1),
        new Vector3(-1, 0,-1),

        new Vector3( 1,-1, 0),
        new Vector3(-1,-1, 0),
        new Vector3( 0,-1, 1),
        new Vector3( 0,-1,-1)};
    
    public final int x;
    public final int y;
    public final int z;
    
    public Vector3(int mx, int my, int mz){
        x = mx;
        y = my;
        z = mz;
    }
    
    /** Returns a difference vector such that reference + vector = destination */
    public Vector3(WorldXYZ reference, WorldXYZ destination){
        x = destination.posX - reference.posX; 
        y = destination.posY - reference.posY;
        z = destination.posZ - reference.posZ;
    }
    
    public Vector3(ForgeDirection face)
    {
        x = face.offsetX;
        y = face.offsetY;
        z = face.offsetZ;
    }

    public String toString(){
        return "\"x\":"+x + ", \"y\":" + y + ", \"z\":" + z;
    }

    public Vector3 multiply(int m) {
        return new Vector3(x*m, y*m, z*m);
    }
}
