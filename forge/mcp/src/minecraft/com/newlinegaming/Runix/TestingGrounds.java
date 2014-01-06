/**
 * 
 */
package com.newlinegaming.Runix;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;

import com.google.gson.Gson;

/** This file exists in order to test out Java syntax and library function within our
 * build environment without the need to launch the main Minecraft client.  In order
 * to use this, you'll need to create an Eclipse Run profile under Run > Run Configurations...
 */
public class TestingGrounds {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        
        List<PersistentRune> runes = new ArrayList<PersistentRune>();
        PersistentRune example = new TorchBearerRune();
        PersistentRune match = example.getClass().getConstructor(WorldXYZ.class, EntityPlayer.class).newInstance(new WorldXYZ(null, 11, 11, 11), null);
        runes.add((PersistentRune)new TorchBearerRune());
        runes.add((PersistentRune)new WaypointRune(new WorldXYZ(null, 1, 2, 3), null));
        runes.add((PersistentRune)new WaypointRune(new WorldXYZ(null, 5, 6, 7), null));
        runes.add(match);
        
        
        Gson converter = new Gson();
        for(PersistentRune rune : runes)
        {
            String runeGson = converter.toJson(rune);
            System.out.println("[SAVE][] " +runeGson);
//            file.println(runeGson);
        }
//        String completeList = converter.toJson(runes);
//        System.out.println("[SAVE] " + completeList);
    }

}
