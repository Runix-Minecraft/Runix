package com.newlinegaming.Runix.lib;

public class LibInfo {

    public static final String MOD_ID = "runix";
    public static final String MOD_NAME = "Runix";
    public static final String MOD_VERSION = "0.1c";
    public static final String MC_VERSION = "1.6.4";

    public static final String CLIENT_PROXY = "com.newlinegaming.Runix.proxys.ClientProxy";
    public static final String COMMON_PROXY = "com.newlinegaming.Runix.proxys.CommonProxy";

    public static String modIdPrefix(String name){
        return MOD_ID + ":" + name;
    }
}
