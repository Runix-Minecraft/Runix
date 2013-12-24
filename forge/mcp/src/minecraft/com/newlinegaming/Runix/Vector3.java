package com.newlinegaming.Runix;

public class Vector3{// extends paulscode.sound.Vector3D.{

    float x;
    float y;
    float z;
    
    public Vector3(float mx, float my, float mz){
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
