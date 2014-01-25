package com.newlinegaming.Runix;



import java.util.ArrayList;
import java.util.HashMap;
import net.minecraft.block.Block;


public class Tiers {
    
    //Cost category values from the Spreadsheet
    //https://docs.google.com/spreadsheet/ccc?key=0AjI7rA2yIcubdG1XbTkxcTg5ZlJkSU1UU3NjOGhnQ0E&usp=drive_web#gid=0
    public static final int blockMoveCost = 1;
    public static final int blockBreakCost = 12;
    public static final float movementPerMeterCost = 0.22f;
    
    private static ArrayList<Integer> naturalBlocks;
    private static ArrayList<Integer> moveSensitiveBlocks;
    private static ArrayList<Integer> crushableBlocks;
    private static int[] blockEnergy;
    
    public Tiers(){
        /**naturalBlocks is an important list because it lists all blocks that will not conduct runic energy*/
        Block[] extraNaturalBlocks = new Block[]{
            Block.waterStill, Block.waterMoving, 
            Block.sand, Block.stone, Block.dirt, 
            Block.grass, Block.tallGrass, Block.snow, 
            Block.mycelium, Block.netherrack,
            Block.lavaStill, Block.lavaMoving,  
            Block.vine, Block.leaves, Block.cactus, Block.deadBush, 
            Block.ice, Block.sapling, Block.wood};
        naturalBlocks = loadBlockIds(extraNaturalBlocks);
        naturalBlocks.add(0);// AIR 0 needs to be added manually
        
        Block[] attachedOrFallingBlocks = new Block[]{
            Block.anvil, Block.cocoaPlant, Block.carrot, Block.carpet, Block.crops,
            Block.potato, Block.portal, Block.endPortal, Block.brewingStand, 
            Block.cactus, Block.deadBush, Block.dragonEgg, Block.fire,
            Block.grass, Block.gravel, Block.lavaMoving, Block.lavaStill,
            Block.ladder, Block.leaves, Block.lever, Block.melonStem,
            Block.mushroomBrown, Block.mushroomRed, Block.netherStalk,
            Block.pistonMoving, //these ones may be co-dependent :?  
            Block.pistonBase, Block.pistonStickyBase,
//            Block.pistonExtension, 
            Block.plantRed, Block.plantYellow, Block.pressurePlateGold,
            Block.pressurePlateIron, Block.pressurePlateStone, Block.pressurePlatePlanks, Block.pumpkinStem,
            Block.rail, Block.railActivator, Block.railDetector, Block.railPowered,
            Block.redstoneComparatorActive, Block.redstoneComparatorIdle, 
            Block.redstoneRepeaterActive, Block.redstoneRepeaterIdle,
            Block.redstoneWire, Block.reed, Block.sand, Block.sapling, 
            Block.signPost, Block.signWall, Block.skull, Block.stoneButton,
            Block.tallGrass, Block.tripWire, Block.tripWireSource,
            Block.torchWood, Block.torchRedstoneActive, Block.torchRedstoneIdle,
            Block.vine, Block.waterlily, 
            Block.waterMoving, Block.waterStill, 
            Block.woodenButton};
        moveSensitiveBlocks = loadBlockIds(attachedOrFallingBlocks);
        
        Block[] crushTheseBlocks = new Block[]{
            Block.deadBush, Block.snow, Block.fire, Block.gravel, Block.waterMoving,
            Block.waterStill, Block.sapling, Block.tallGrass, Block.torchWood,//torches are debatable, since someone did place it there
            Block.vine};
        crushableBlocks = loadBlockIds(crushTheseBlocks);
        
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
    
    public static int getEnergy(int blockID){
        if(blockID > 255)
            return 1;
        return blockEnergy[blockID];
    }

    public static int getTier(int blockID){
        int energy = getEnergy(blockID);
        energy = energy < 1 ? 1 : energy; // log(0) = crash bad
        return (int) Math.round(Math.log(energy) / Math.log(2));
    }

    /**The idea behind this method is to take a list of Blocks and pull all the ids. 
     * It really only exists to cut down on the number of ".blockID" that is in this file given
     * how long it will be.  
     * @param blockList
     */
    public static ArrayList<Integer> loadBlockIds(Block[] blockList) {
        ArrayList<Integer> IDs = new ArrayList<Integer>();
        for(Block block : blockList)
            IDs.add(block.blockID);
        return IDs;
    }
    
    
    
    /**naturalBlocks is an important list because it lists all blocks that will not conduct runic energy*/
    public static boolean isNatural(int blockID){
        return naturalBlocks.contains(new Integer(blockID));
    }
    
    /**This is a list of all the blocks that need special treatment when moving groups of blocks
     * like FTP or Runecraft.  All independent blocks need to be placed first because all of these
     * blocks attach to other blocks or (in the case of liquids) need to be held in by solid blocks.
     */
    public static boolean isMoveSensitive(int blockID){
        return moveSensitiveBlocks.contains(new Integer(blockID));
    }

    public static boolean isCrushable(int blockID) {
        return crushableBlocks.contains(new Integer(blockID));
    }
}


