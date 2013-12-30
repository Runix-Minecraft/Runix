package com.newlinegaming.Runix;

public class Vector3{// extends paulscode.sound.Vector3D.{
//    enum Direction {UP, DOWN, NORTH, EAST, SOUTH, WEST};// vectors[(int)Direction.UP] = new int[]{0,1,0};
    public static final Vector3 UP =    new Vector3(0, 1,0);
    public static final Vector3 DOWN =  new Vector3(0,-1,0);
    public static final Vector3 NORTH = new Vector3(0,0,-1);
    public static final Vector3 EAST =  new Vector3(0, 1,0);
    public static final Vector3 SOUTH = new Vector3(0,0, 1);
    public static final Vector3 WEST =  new Vector3(0,-1,0);
    
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
