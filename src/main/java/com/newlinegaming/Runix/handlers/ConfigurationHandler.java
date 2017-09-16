package com.newlinegaming.Runix.handlers;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

import com.newlinegaming.Runix.lib.LibConfig;
import com.newlinegaming.Runix.lib.LibInfo;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@SuppressWarnings("WeakerAccess")
public class ConfigurationHandler {
    
    @SuppressWarnings("WeakerAccess")
    public static Configuration conf;
    
    public static void init(File confFile) {
        
        conf = new Configuration();
        
        try {
            
            conf.load();
            loadConf();
            
        } catch(Exception e) {
            
        } finally {
            conf.save();
        }
    }

    private static void loadConf() {
//        LibConfig.STRUCWORKER = conf.getInt("Structure Moveworker", Configuration.CATEGORY_GENERAL, LibConfig.runixBlocksPerTick, 1, 1000, "The amount of work the structure moveworker will do");
//        LibConfig.STRUCWORKER cong
        
    }
    
    
}