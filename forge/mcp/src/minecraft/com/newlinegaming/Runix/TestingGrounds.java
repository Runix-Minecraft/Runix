/**
 * 
 */
package com.newlinegaming.Runix;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newlinegaming.Runix.Runes.RubricCreationRune;
import com.newlinegaming.Runix.Runes.TorchBearerRune;
import com.newlinegaming.Runix.Runes.WaypointRune;

/** This file exists in order to test out Java syntax and library function within our
 * build environment without the need to launch the main Minecraft client.  In order
 * to use this, you'll need to create an Eclipse Run profile under Run > Run Configurations...
 */
public class TestingGrounds {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

        HashMap<Vector3, SigBlock> structure = new HashMap<Vector3, SigBlock>();
        structure.put(new Vector3(1,2,3), new SigBlock(5,10));
        structure.put(new Vector3(4,5,6), new SigBlock(15,20));
        
        HashSet<BlockRecord> coords = new HashSet<BlockRecord>();
        coords.add(new BlockRecord(1, new Vector3(1,2,3), new SigBlock(5,10)));
        coords.add(new BlockRecord(2, new Vector3(4,5,6), new SigBlock(15,20)));
        
        Gson converter = new Gson();
        String runeGson = converter.toJson(coords);
        System.out.println("[SAVE][] " +runeGson);
        System.out.println("[SAVE][] " + runeGson.replace("\\", ""));
        
        Type collectionType = new TypeToken<HashSet<BlockRecord> >(){}.getType();
        HashSet<BlockRecord> reconstruct = converter.fromJson(runeGson, collectionType);
        System.out.println(reconstruct);
    }
    
    public static void testRuneList() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
        List<PersistentRune> runes = new ArrayList<PersistentRune>();
        PersistentRune example = new TorchBearerRune();
        PersistentRune match = example.getClass().getConstructor(WorldXYZ.class, EntityPlayer.class).newInstance(new WorldXYZ(null, 11, 11, 11), null);
        runes.add((PersistentRune)new TorchBearerRune());
        runes.add((PersistentRune)new WaypointRune(new WorldXYZ(null, 1, 2, 3), null));
        runes.add((PersistentRune)new WaypointRune(new WorldXYZ(null, 5, 6, 7), null));
        runes.add(match);
        
        
        WorldXYZ p1 = new WorldXYZ(null, 1, 2, 3);
        WorldXYZ p2 = new WorldXYZ(null, 5, 6, 7);;
        
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
