package com.newlinegaming.Runix;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class Tiers {
	
    //Yea... this class is going to be overhauld will make my life as well as mod-addon developers a bit easier-
    // I will be adding a TierRegistery that will look singthing like this-
    // TierRegistery.registerTierBlock(Blocks.cobbleston, TierType.natural, 1) TierType will be a enum class contaning-
    // naturalBlocks moveSensitiveBlocks crushableBlocks
    
    
    //Cost category values from the Spreadsheet
    //https://docs.google.com/spreadsheet/ccc?key=0AjI7rA2yIcubdG1XbTkxcTg5ZlJkSU1UU3NjOGhnQ0E&usp=drive_web#gid=0
    public static final float blockMoveCost = 1.0f;
    public static final int blockBreakCost = 12;
    public static final float movementPerMeterCost = 0.22f;
    
    private static ArrayList<Block> naturalBlocks;
    private static ArrayList<Block> moveSensitiveBlocks;
    private static ArrayList<Block> crushableBlocks;
    private static int[] blockEnergy;
    
    public Tiers(){
        /**
         * naturalBlocks is an important list because it lists all blocks that will not conduct runic energy
         */
        naturalBlocks = new ArrayList<Block>(){{
            add(Blocks.air); 
            add(Blocks.water);
            add(Blocks.flowing_water);
            add(Blocks.bedrock);
            add(Blocks.sand);
            add(Blocks.stone);
            add(Blocks.dirt);
            add(Blocks.grass);
            add(Blocks.tallgrass);
            add(Blocks.snow);
            add(Blocks.mycelium);
            add(Blocks.netherrack);
            add(Blocks.lava);
            add(Blocks.flowing_lava);
            add(Blocks.vine);
            add(Blocks.leaves);
            add(Blocks.cactus);
            add(Blocks.deadbush);
            add(Blocks.ice);
            add(Blocks.sapling);
            add(Blocks.log);
//            add(GreekFire);
        }};

        moveSensitiveBlocks = new ArrayList<Block>(){{
            add(Blocks.anvil);
            add(Blocks.cocoa);
            add(Blocks.carrots);
            add(Blocks.carpet);
            add(Blocks.wheat);
            add(Blocks.potatoes);
            add(Blocks.portal);
            add(Blocks.end_portal);
            add(Blocks.brewing_stand);
            add(Blocks.cactus);
            add(Blocks.deadbush);
            add(Blocks.dragon_egg);
            add(Blocks.fire);
            add(Blocks.grass);
            add(Blocks.gravel);
            add(Blocks.lava);
            add(Blocks.flowing_lava);
            add(Blocks.ladder);
            add(Blocks.leaves);
            add(Blocks.lever);
            add(Blocks.melon_stem);
            add(Blocks.brown_mushroom);
            add(Blocks.red_mushroom);
            add(Blocks.nether_wart);
            add(Blocks.piston);
            //these ones may be co-dependent :?
            add(Blocks.piston_extension);
            add(Blocks.piston_head);
            add(Blocks.sticky_piston);
            add(Blocks.red_flower);
            add(Blocks.yellow_flower);
            add(Blocks.heavy_weighted_pressure_plate);
            add(Blocks.light_weighted_pressure_plate);
            add(Blocks.wooden_pressure_plate);
            add(Blocks.stone_pressure_plate);
            add(Blocks.pumpkin);
            add(Blocks.rail);
            add(Blocks.activator_rail);
            add(Blocks.detector_rail);
            add(Blocks.golden_rail);
            add(Blocks.powered_comparator);
            add(Blocks.unpowered_comparator);
            add(Blocks.unpowered_repeater);
            add(Blocks.powered_repeater);
            add(Blocks.redstone_wire);
            add(Blocks.reeds);
            add(Blocks.sand);
            add(Blocks.sapling);
            add(Blocks.standing_sign);
            add(Blocks.wall_sign);
            add(Blocks.skull);
            add(Blocks.stone_button);
            add(Blocks.tallgrass);
            add(Blocks.tripwire);
            add(Blocks.tripwire_hook);
            add(Blocks.torch);
            add(Blocks.redstone_torch);
            add(Blocks.unlit_redstone_torch);
            add(Blocks.vine);
            add(Blocks.waterlily);
            add(Blocks.water);
            add(Blocks.flowing_water);
            add(Blocks.wooden_button);
        }};
        
        crushableBlocks =  new ArrayList<Block>(){{//torches are debatable, since someone did place it there
            add(Blocks.deadbush);
            add(Blocks.snow);
            add(Blocks.fire);
            add(Blocks.gravel);
            add(Blocks.water);
            add(Blocks.flowing_water);
            add(Blocks.sapling);
            add(Blocks.tallgrass);
            add(Blocks.torch);
            add(Blocks.vine);
//          add(GreekFire);
        }};
        
        blockEnergy = new int[]{ //the blockID is the index for this array.  The value at blockEnergy[blockID] = runic energy
                1,   //Air
                1,   //Smooth Stone
                1,   //Grass
                1,   //Dirt
                1,   //Cobblestone
                8,   //Wooden Plank
                32,  //Sapling
                1,   //Bedrock
                16,  //Water
                16,  //Stationary Water
                80,  //Lava
                80,  //Stationary Lava
                5,   //Sand
                31,  //Gravel
                1519,    //Gold Ore
                158,     //Iron Ore
                84,  //Coal Ore
                32,  //Wood Log
                1,   //Leaves
                256,     //Sponge
                10,  //Glass
                3964,    //Lapis Lazuli Ore
                5946,    //Lapis Lazuli Block
                352,     //Dispenser
                20,  //Sandstone
                210,     //Note Block
                96,  //Bed
                1543,    //Powered Rail
                59,  //Detector Rail
                304,     //Sticky Piston
                3038,    //Cobweb
                1,   //Tall Grass
                1,   //Dead Bush
                304,     //Piston
                1,   //Piston Head
                24,  //Wool
                1,   //Moving Block
                16,  //Dandelion
                16,  //Rose
                192,     //Brown Mushroom
                192,     //Red Mushroom
                13671,   //Gold Block
                1422,    //Iron Block
                2,   //Double Slab
                1,   //Stone Slab
                279,     //Brick Block
                680,     //TNT
                336,     //Bookshelf
                13207,   //Moss Stone
                80,  //Obsidian
                12,  //Torch
                4,   //Fire
                330175,  //Monster Spawner
                12,  //Wooden Stairs
                64,  //Chest
                146,     //Redstone
                3566,    //Diamond Ore
                32094,   //Diamond Block
                32,  //Crafting Table
                24,  //Wheat
                1,   //Soil
                8,   //Furnace
                8,   //Burning Furnace
                52,  //Sign
                48,  //Wooden Door
                28,  //Ladder
                59,  //Rails
                1,   //Cobblestone Stairs
                52,  //Sign
                5,   //Lever
                2,   //Stone Pressure Plate
                948,     //Iron Door
                16,  //Wooden Pressure Plate
                655,     //Redstone Ore
                655,     //Glowing Redstone Ore
                150,     //Redstone Torch (off)
                150,     //Redstone Torch
                2,   //Stone Button
                1,   //Snow
                1,   //Ice
                1,   //Snow Block
                96,  //Cactus
                237,     //Clay Block
                32,  //Sugar Cane
                3630,    //Jukebox
                12,  //Fence
                192,     //Pumpkin
                1,   //Netherrack
                760,     //Soul Sand
                3038,    //Glowstone
                0,   //Portal
                192,     //JackOLantern
                173,     //Cake
                437,     //Redstone Repeater (off)
                437,     //Redstone Repeater
                0,   //Locked Chest
                0,   //Trapdoor
                0,   //Stone (Silverfish)
                12,  //Stone Brick
                0,   //Red Mushroom Cap
                0,   //Brown Mushroom Cap
                59,  //Iron Bars
                0,   //Glass Pane
                0,   //Melon Block
                0,   //Pumpkin Stem
                0,   //Melon Stem
                0,   //Vines
                0,   //Fence Gate
                0,   //Brick Stairs
                0,   //Stone Brick Stairs
                1,   //Mycelium
                3566,    //Lily Pad
                46,  //Nether Brick
                0,   //Nether Brick Fence
                0,   //Nether Brick Stairs
                0,   //Nether Wart
                7452,    //Enchantment Table
                360,     //Brewing Stand
                1106,    //Cauldron
                0,   //End Portal
                0,   //End Portal Frame
                1,   //End Stone
                320940,  //Dragon Egg
                3620,    //Redstone Lamp
                3620,    //Redstone Lamp (on)
                0,   //Double Wooden Slab
                0,   //Wooden Slab
                0,   //Cocoa Plant
                0,   //Sandstone Stairs
                3038,    //Emerald Ore
                2584,    //Ender Chest
                158,     //Tripwire Hook
                69,  //Tripwire
                27342,   //Emerald Block
                0,   //Stairs
                0,   //Stairs
                0,   //Stairs
                0,   //Command Block
                320940,  //Beacon
                1,   //Cobblestone Wall
                79,  //Flower Pot
                0,   //Carrots
                0,   //Potatoes
                2,   //Wooden Button
                23,  //Head
                4898, //Anvil
                222 , //Trapped Chest
                3038, //Weighted Pressure Plate (light)
                316 , //Weighted Pressure Plate (heavy)
                607 , //Redstone Comparator (inactive)
                607 , //Redstone Comparator (active)
                503 , //Daylight Sensor
                1310, //Redstone Block
                158 , //Nether Quartz Ore
                854 , //Hopper
                632 , //Quartz Block
                948 , //Quartz Stairs
                84  , //Activator Rail
                153 , //Dropper
                248 , //Stained Clay
                0   , //
                0   , //
                0   , //
                0   , //
                0   , //
                0   , //
                0   , //
                0   , //
                0   , //
                0   , //
                216 , //Hay Bale
                16  , //Carpet
                248 , //Hardened Clay
                756 , //Block of Coal
                0   , //
                0   , //
                0   , //
                0   , //
                0   , //
                0   , //
                0   , //
                0   , //
                0   , //
                0   , //
                0   , //
                0   , //
                0   , //
                0   , //
                0   , //
                0    //
        };
    }
    
    public static int getEnergy(Block blockID){
        return 1;
//        if(blockID > 255)
//            return 1;
//        return blockEnergy[blockID];
    }

    public static int getTier(Block blockID){
        int energy = getEnergy(blockID);
        energy = energy < 1 ? 1 : energy; // log(0) = crash bad
        return (int) Math.round(Math.log(energy) / Math.log(2));
    }

    public static int energyToRadiusConversion(int energy) {
        int diameter = 1;
        while( diameter * diameter * diameter * blockMoveCost < energy) //this is over generous intentionally
            diameter += 2; // +2 so that we always have an odd number and have block centered shapes
        return diameter/2; //integer math will round down the .5
    }
    
    
    /**
     * naturalBlocks is an important list because it lists all blocks that will not conduct runic energy
     */
    public static boolean isNatural(Block blockID){
        return naturalBlocks.contains(blockID);
    }
    
    /**
     * This is a list of all the blocks that need special treatment when moving groups of blocks
     * like FTP or Runecraft.  All independent blocks need to be placed first because all of these
     * blocks attach to other blocks or (in the case of liquids) need to be held in by solid blocks.
     * @param blockID
     */
    public static boolean isMoveSensitive(Block blockID){
        return moveSensitiveBlocks.contains(blockID);
    }

    public static boolean isCrushable(Block blockID) {
        return crushableBlocks.contains(blockID);
    }
}


