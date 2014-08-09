package com.newlinegaming.Runix;

import java.util.ArrayList;
import java.util.HashMap;

import com.newlinegaming.Runix.lib.BlockDescription;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class Tiers {
    
    // naturalBlocks moveSensitiveBlocks crushableBlocks
    
    
    //Cost category values from the Spreadsheet
    //https://docs.google.com/spreadsheet/ccc?key=0AjI7rA2yIcubdG1XbTkxcTg5ZlJkSU1UU3NjOGhnQ0E&usp=drive_web#gid=0
    public static final float blockMoveCost = 1.0f;
    public static final int blockBreakCost = 2;
    public static final float movementPerMeterCost = 0.22f;
    
    private static ArrayList<Block> naturalBlocks;
    private static ArrayList<Block> moveSensitiveBlocks;
    private static ArrayList<Block> crushableBlocks;
    private static HashMap<Block, BlockDescription> energyRegistry = new HashMap<Block, BlockDescription>();
    
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
            add(Blocks.double_plant);
            add(Blocks.snow_layer);
            add(Blocks.mycelium);
            add(Blocks.netherrack);
            add(Blocks.yellow_flower);
            add(Blocks.red_flower);
            add(Blocks.lava);
            add(Blocks.flowing_lava);
            add(Blocks.vine);
            add(Blocks.leaves);
            add(Blocks.leaves2);
            add(Blocks.cactus);
            add(Blocks.deadbush);
            add(Blocks.ice);
            add(Blocks.sapling);
            add(Blocks.log);
            add(Blocks.log2);
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
            add(Blocks.snow_layer);
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
    }

    public void initializeEnergyRegistry()
    {
        addBlock(Blocks.air, 1);
        addBlock(Blocks.stone, 1);
        addBlock(Blocks.grass, 1);
        addBlock(Blocks.dirt, 1);
        addBlock(Blocks.cobblestone, 1);
        addBlock(Blocks.planks, 8);
        addBlock(Blocks.sapling, 32);
        addBlock(Blocks.bedrock, 1);
        addBlock(Blocks.flowing_water, 16);
        addBlock(Blocks.water, 16);
        addBlock(Blocks.flowing_lava, 80);
        addBlock(Blocks.lava, 80);
        addBlock(Blocks.sand, 5);
        addBlock(Blocks.gravel, 31);
        addBlock(Blocks.gold_ore, 1519);
        addBlock(Blocks.iron_ore, 158);
        addBlock(Blocks.coal_ore, 84);
        addBlock(Blocks.log, 32);
        addBlock(Blocks.log2, 32);
        addBlock(Blocks.leaves, 10);
        addBlock(Blocks.leaves2, 10);
        addBlock(Blocks.sponge, 256);
        addBlock(Blocks.glass, 9);
        addBlock(Blocks.lapis_ore, 3964);
        addBlock(Blocks.lapis_block, 5946);
        addBlock(Blocks.dispenser, 834);
        addBlock(Blocks.sandstone, 20);
        addBlock(Blocks.noteblock, 209);
        addBlock(Blocks.bed, 264);
        addBlock(Blocks.golden_rail, 1543);
        addBlock(Blocks.detector_rail, 59);
        addBlock(Blocks.sticky_piston, 303);
        addBlock(Blocks.web, 3038);
        addBlock(Blocks.tallgrass, 1);
        addBlock(Blocks.deadbush, 1);
        addBlock(Blocks.piston, 303);
        addBlock(Blocks.piston_head, 1);
        addBlock(Blocks.wool, 80);
        addBlock(Blocks.piston_extension, 1);
        addBlock(Blocks.yellow_flower, 16);
        addBlock(Blocks.red_flower, 16);
        addBlock(Blocks.brown_mushroom, 192);
        addBlock(Blocks.red_mushroom, 192);
        addBlock(Blocks.gold_block, 13671);
        addBlock(Blocks.iron_block, 1422);
        addBlock(Blocks.double_stone_slab, 2);
        addBlock(Blocks.stone_slab, 1);
        addBlock(Blocks.brick_block, 279);
        addBlock(Blocks.tnt, 2220);
        addBlock(Blocks.bookshelf, 336);
        addBlock(Blocks.mossy_cobblestone, 5946);
        addBlock(Blocks.obsidian, 80);
        addBlock(Blocks.torch, 12);
        addBlock(Blocks.fire, 4);
        addBlock(Blocks.mob_spawner, 148650);
        addBlock(Blocks.oak_stairs, 12);
        addBlock(Blocks.chest, 64);
        addBlock(Blocks.redstone_wire, 145);
        addBlock(Blocks.diamond_ore, 3566);
        addBlock(Blocks.diamond_block, 32094);
        addBlock(Blocks.crafting_table, 32);
        addBlock(Blocks.wheat, 80);
        addBlock(Blocks.farmland, 1);
        addBlock(Blocks.furnace, 8);
        addBlock(Blocks.lit_furnace, 8);
        addBlock(Blocks.standing_sign, 52);
        addBlock(Blocks.wooden_door, 48);
        addBlock(Blocks.ladder, 28);
        addBlock(Blocks.rail, 59);
        addBlock(Blocks.stone_stairs, 1);
        addBlock(Blocks.wall_sign, 52);
        addBlock(Blocks.lever, 5);
        addBlock(Blocks.stone_pressure_plate, 2);
        addBlock(Blocks.iron_door, 948);
        addBlock(Blocks.wooden_pressure_plate, 16);
        addBlock(Blocks.redstone_ore, 655);
        addBlock(Blocks.lit_redstone_ore, 655);
        addBlock(Blocks.unlit_redstone_torch, 149);
        addBlock(Blocks.redstone_torch, 149);
        addBlock(Blocks.stone_button, 2);
        addBlock(Blocks.snow_layer, 1);
        addBlock(Blocks.ice, 1);
        addBlock(Blocks.snow, 20);
        addBlock(Blocks.cactus, 96);
        addBlock(Blocks.clay, 237);
        addBlock(Blocks.reeds, 32);
        addBlock(Blocks.jukebox, 3630);
        addBlock(Blocks.fence, 12);
        addBlock(Blocks.pumpkin, 192);
        addBlock(Blocks.netherrack, 1);
        addBlock(Blocks.soul_sand, 759);
        addBlock(Blocks.glowstone, 3038);
        addBlock(Blocks.portal, 0);
        addBlock(Blocks.lit_pumpkin, 192);
        addBlock(Blocks.cake, 576);
        addBlock(Blocks.unpowered_repeater, 436);
        addBlock(Blocks.powered_repeater, 436);
        addBlock(Blocks.trapped_chest, 0);
        addBlock(Blocks.trapdoor, 0);
        addBlock(Blocks.monster_egg, 0);
        addBlock(Blocks.stonebrick, 11);
        addBlock(Blocks.red_mushroom_block, 0);
        addBlock(Blocks.brown_mushroom_block, 0);
        addBlock(Blocks.iron_bars, 59);
        addBlock(Blocks.glass_pane, 0);
        addBlock(Blocks.melon_block, 0);
        addBlock(Blocks.pumpkin_stem, 0);
        addBlock(Blocks.melon_stem, 0);
        addBlock(Blocks.vine, 0);
        addBlock(Blocks.fence_gate, 0);
        addBlock(Blocks.brick_stairs, 0);
        addBlock(Blocks.stone_brick_stairs, 0);
        addBlock(Blocks.mycelium, 1);
        addBlock(Blocks.waterlily, 3566);
        addBlock(Blocks.nether_brick, 46);
        addBlock(Blocks.nether_brick_fence, 0);
        addBlock(Blocks.nether_brick_stairs, 0);
        addBlock(Blocks.nether_wart, 0);
        addBlock(Blocks.enchanting_table, 7452);
        addBlock(Blocks.brewing_stand, 1200);
        addBlock(Blocks.cauldron, 1106);
        addBlock(Blocks.end_portal, 0);
        addBlock(Blocks.end_portal_frame, 0);
        addBlock(Blocks.end_stone, 1);
        addBlock(Blocks.dragon_egg, 320940);
        addBlock(Blocks.redstone_lamp, 3620);
        addBlock(Blocks.lit_redstone_lamp, 3620);
        addBlock(Blocks.double_wooden_slab, 0);
        addBlock(Blocks.wooden_slab, 0);
        addBlock(Blocks.cocoa, 0);
        addBlock(Blocks.sandstone_stairs, 0);
        addBlock(Blocks.emerald_ore, 3038);
        addBlock(Blocks.ender_chest, 7120);
        addBlock(Blocks.tripwire_hook, 158);
        addBlock(Blocks.tripwire, 229);
        addBlock(Blocks.emerald_block, 27342);
        addBlock(Blocks.spruce_stairs, 0);
        addBlock(Blocks.birch_stairs, 0);
        addBlock(Blocks.jungle_stairs, 0);
        addBlock(Blocks.command_block, 0);
        addBlock(Blocks.beacon, 320940);
        addBlock(Blocks.cobblestone_wall, 1);
        addBlock(Blocks.flower_pot, 79);
        addBlock(Blocks.carrots, 0);
        addBlock(Blocks.potatoes, 0);
        addBlock(Blocks.wooden_button, 2);
        addBlock(Blocks.skull, 23);
        addBlock(Blocks.anvil, 4898);
        addBlock(Blocks.trapped_chest, 222);
        addBlock(Blocks.light_weighted_pressure_plate, 3038);
        addBlock(Blocks.heavy_weighted_pressure_plate, 316);
        addBlock(Blocks.unpowered_comparator, 606);
        addBlock(Blocks.powered_comparator, 606);
        addBlock(Blocks.daylight_detector, 502);
        addBlock(Blocks.redstone_block, 1310);
        addBlock(Blocks.quartz_ore, 158);
        addBlock(Blocks.hopper, 854);
        addBlock(Blocks.quartz_block, 632);
        addBlock(Blocks.quartz_stairs, 948);
        addBlock(Blocks.activator_rail, 84);
        addBlock(Blocks.dropper, 152);
        addBlock(Blocks.stained_hardened_clay, 247);
        addBlock(Blocks.hay_block, 720);
        addBlock(Blocks.carpet, 53);
        addBlock(Blocks.hardened_clay, 247);
        addBlock(Blocks.coal_block, 756);
    }
    
    public void addBlock(Block block, int energy){
        addBlock(block, energy, 
                naturalBlocks.contains(block), 
                crushableBlocks.contains(block), 
                moveSensitiveBlocks.contains(block)); //call more detailed method
    }
    
    public void addBlock(Block type, int energy, boolean natural, boolean crushable, boolean sensitive){
        energyRegistry.put(type, new BlockDescription(type, energy, natural, crushable, sensitive));
    }
    
    public static int getEnergy(Block blockID){
        if( !energyRegistry.containsKey(blockID)){
            return 1;
        }
        return energyRegistry.get(blockID).energy;
    }

    public static int getTier(Block blockID){
        int energy = getEnergy(blockID);
        energy = energy < 1 ? 1 : energy; // log(0) = crash bad
        return (int) Math.round(Math.log(energy) / Math.log(2));
    }

    public static int energyToRadiusConversion(int energy) { 
        return Tiers.energyToRadiusConversion(energy, blockMoveCost);
    }
    
    public static int energyToRadiusConversion(int energy, float perBlockCost) {
        int diameter = 1;
        while( diameter * diameter * diameter * perBlockCost < energy) //this is over generous intentionally
            diameter += 2; // +2 so that we always have an odd number and have block centered shapes
        return diameter/2; //integer math will round down the .5
    }
    
    
    /**
     * naturalBlocks is an important list because it lists all blocks that will not conduct runic energy
     */
    public static boolean isNatural(Block blockID){
        if( !energyRegistry.containsKey(blockID)){
            return false;
        }
        return energyRegistry.get(blockID).natural;
    }
    
    /**
     * This is a list of all the blocks that need special treatment when moving groups of blocks
     * like FTP or Runecraft.  All independent blocks need to be placed first because all of these
     * blocks attach to other blocks or (in the case of liquids) need to be held in by solid blocks.
     * @param blockID
     */
    public static boolean isMoveSensitive(Block blockID){
        if( !energyRegistry.containsKey(blockID)){
            return false;
        }
        return energyRegistry.get(blockID).sensitive;
    }

    public static boolean isCrushable(Block blockID) {
        if( !energyRegistry.containsKey(blockID)){
            return false;
        }
        return energyRegistry.get(blockID).crushable;
    }
}


