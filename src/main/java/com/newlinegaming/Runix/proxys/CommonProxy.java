package com.newlinegaming.Runix.proxys;

import com.newlinegaming.Runix.api.tier.ITier;
import com.newlinegaming.Runix.apiimpl.tier.Tier;
import com.newlinegaming.Runix.apiimpl.API;
import com.newlinegaming.Runix.handlers.RuneHandler;
import com.newlinegaming.Runix.helper.TierHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class CommonProxy {

    private static ArrayList<Block> naturalBlocks;
    private static ArrayList<Block> moveSensitiveBlocks;
    private static ArrayList<Block> crushableBlocks;
    private static final HashMap<Block, ITier> energyRegistry = new HashMap<>();

    public CommonProxy() {
//        Tiers tier = new Tiers();
    }

    public void preInit(FMLPreInitializationEvent e) {
        API.bind(e.getAsmData());
        registerTiers();
//        System.out.println(API.INSTANCE().getTier(Blocks.DRAGON_EGG));
//        System.out.print(TierHelper.getTierNumber(Blocks.DRAGON_EGG));

    }

    public void init(FMLInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(RuneHandler.getInstance());
    }

    public void postInit(FMLPostInitializationEvent e) {
//        System.out.println(API.INSTANCE().getTier(Blocks.HAY_BLOCK));
//        System.out.print(TierHelper.getTierNumber(Blocks.DRAGON_EGG));
    }

    private void registerTiers() {
        naturalBlocks = new ArrayList<Block>(){{
            add(Blocks.AIR);
            add(Blocks.WATER);
            add(Blocks.FLOWING_WATER);
            add(Blocks.BEDROCK);
            add(Blocks.SAND);
            add(Blocks.STONE);
            add(Blocks.DIRT);
            add(Blocks.GRASS);
            add(Blocks.TALLGRASS);
            add(Blocks.DOUBLE_PLANT);
            add(Blocks.SNOW_LAYER);
            add(Blocks.MYCELIUM);
            add(Blocks.NETHERRACK);
            add(Blocks.YELLOW_FLOWER);
            add(Blocks.RED_FLOWER);
            add(Blocks.LAVA);
            add(Blocks.FLOWING_LAVA);
            add(Blocks.VINE);
            add(Blocks.LEAVES);
            add(Blocks.LEAVES2);
            add(Blocks.CACTUS);
            add(Blocks.DEADBUSH);
            add(Blocks.ICE);
            add(Blocks.SAPLING);
            add(Blocks.LOG);
            add(Blocks.LOG2);
        }};

        moveSensitiveBlocks = new ArrayList<Block>(){{
            add(Blocks.ANVIL);
            add(Blocks.COCOA);
            add(Blocks.CARROTS);
            add(Blocks.CARPET);
            add(Blocks.WHEAT);
            add(Blocks.POTATOES);
            add(Blocks.PORTAL);
            add(Blocks.END_PORTAL);
            add(Blocks.BREWING_STAND);
            add(Blocks.CACTUS);
            add(Blocks.DEADBUSH);
            add(Blocks.DRAGON_EGG);
            add(Blocks.FIRE);
            add(Blocks.GRAVEL);
            add(Blocks.FLOWING_LAVA);
            add(Blocks.FLOWING_WATER);
            add(Blocks.LAVA);
            add(Blocks.LEAVES);
            add(Blocks.LEAVES2);
            add(Blocks.LADDER);
            add(Blocks.LEVER);
            add(Blocks.MELON_STEM);
            add(Blocks.BROWN_MUSHROOM);
            add(Blocks.RED_MUSHROOM);
            add(Blocks.NETHER_WART);
            add(Blocks.PISTON);
            //these ones may be co-dependent :?
            add(Blocks.PISTON_EXTENSION);
            add(Blocks.PISTON_HEAD);
            add(Blocks.STICKY_PISTON);
            add(Blocks.YELLOW_FLOWER);
            add(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE);
            add(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE);
            add(Blocks.WOODEN_PRESSURE_PLATE);
            add(Blocks.STONE_PRESSURE_PLATE);
            add(Blocks.PUMPKIN_STEM);
            add(Blocks.RAIL);
            add(Blocks.ACTIVATOR_RAIL);
            add(Blocks.DETECTOR_RAIL);
            add(Blocks.GOLDEN_RAIL);
            add(Blocks.POWERED_COMPARATOR);
            add(Blocks.UNPOWERED_COMPARATOR);
            add(Blocks.UNPOWERED_REPEATER);
            add(Blocks.POWERED_REPEATER);
            add(Blocks.REDSTONE_WIRE);
            add(Blocks.REEDS);
            add(Blocks.RED_FLOWER);
            add(Blocks.SAND);
            add(Blocks.SAPLING);
            add(Blocks.STANDING_SIGN);
            add(Blocks.WALL_SIGN);
            add(Blocks.SKULL);
            add(Blocks.STONE_BUTTON);
            add(Blocks.TALLGRASS);
            add(Blocks.TRIPWIRE);
            add(Blocks.TRIPWIRE_HOOK);
            add(Blocks.TORCH);
            add(Blocks.REDSTONE_TORCH);
            add(Blocks.UNLIT_REDSTONE_TORCH);
            add(Blocks.VINE);
            add(Blocks.WATERLILY);
            add(Blocks.WATER);
            add(Blocks.WOODEN_BUTTON);
        }};

        crushableBlocks =  new ArrayList<Block>(){{//torches are debatable, since someone did place it there
            add(Blocks.AIR);
            add(Blocks.DEADBUSH);
            add(Blocks.SNOW_LAYER);
            add(Blocks.FIRE);
            add(Blocks.GRAVEL);
            add(Blocks.WATER);
            add(Blocks.FLOWING_WATER);
            add(Blocks.SAPLING);
            add(Blocks.TALLGRASS);
            add(Blocks.TORCH);
            add(Blocks.VINE);
            add(Blocks.FLOWING_LAVA);
            add(Blocks.LADDER);
            //TODO anything in the liquid registry
        }};


        addBlock(Blocks.AIR, 1);
        addBlock(Blocks.STONE, 1);
        addBlock(Blocks.GRASS, 1);
        addBlock(Blocks.DIRT, 1);
        addBlock(Blocks.COBBLESTONE, 1);
        addBlock(Blocks.COBBLESTONE, 8);
        addBlock(Blocks.SAPLING, 32);
        addBlock(Blocks.BEDROCK, 1);
        addBlock(Blocks.FLOWING_WATER, 16);
        addBlock(Blocks.WATER, 16);
        addBlock(Blocks.FLOWING_LAVA, 80);
        addBlock(Blocks.LAVA, 80);
        addBlock(Blocks.SAND, 5);
        addBlock(Blocks.GRAVEL, 31);
        addBlock(Blocks.GOLD_ORE, 1519);
        addBlock(Blocks.IRON_ORE, 158);
        addBlock(Blocks.COAL_ORE, 84);
        addBlock(Blocks.LOG, 32);
        addBlock(Blocks.LOG2, 32);
        addBlock(Blocks.LEAVES, 10);
        addBlock(Blocks.LEAVES2, 10);
        addBlock(Blocks.SPONGE, 256);
        addBlock(Blocks.GLASS, 9);
        addBlock(Blocks.LAPIS_ORE, 3964);
        addBlock(Blocks.LAPIS_ORE, 5946);
        addBlock(Blocks.DISPENSER, 834);
        addBlock(Blocks.SANDSTONE, 20);
        addBlock(Blocks.NOTEBLOCK, 209);
        addBlock(Blocks.BED, 264);
        addBlock(Blocks.GOLDEN_RAIL, 1543);
        addBlock(Blocks.DETECTOR_RAIL, 59);
        addBlock(Blocks.STICKY_PISTON, 303);
        addBlock(Blocks.WEB, 3038);
        addBlock(Blocks.TALLGRASS, 1);
        addBlock(Blocks.DEADBUSH, 1);
        addBlock(Blocks.PISTON, 303);
        addBlock(Blocks.PISTON_HEAD, 1);
        addBlock(Blocks.WOOL, 80);
        addBlock(Blocks.PISTON_EXTENSION, 1);
        addBlock(Blocks.YELLOW_FLOWER, 16);
        addBlock(Blocks.RED_FLOWER, 16);
        addBlock(Blocks.BROWN_MUSHROOM, 192);
        addBlock(Blocks.RED_MUSHROOM, 192);
        addBlock(Blocks.GOLD_BLOCK, 13671);
        addBlock(Blocks.IRON_BLOCK, 1422);
        addBlock(Blocks.DOUBLE_STONE_SLAB, 2);
        addBlock(Blocks.STONE_SLAB, 1);
        addBlock(Blocks.STONE_SLAB2, 1);
        addBlock(Blocks.BRICK_BLOCK, 279);
        addBlock(Blocks.TNT, 2220);
        addBlock(Blocks.BOOKSHELF, 336);
        addBlock(Blocks.MOSSY_COBBLESTONE, 5946);
        addBlock(Blocks.OBSIDIAN, 80);
        addBlock(Blocks.TORCH, 12);
        addBlock(Blocks.FIRE, 4);
        addBlock(Blocks.MOB_SPAWNER, 148650);
        addBlock(Blocks.OAK_STAIRS, 12);
        addBlock(Blocks.CHEST, 64);
        addBlock(Blocks.REDSTONE_WIRE, 145);
        addBlock(Blocks.DIAMOND_ORE, 3566);
        addBlock(Blocks.DIAMOND_BLOCK, 32094);
        addBlock(Blocks.CRAFTING_TABLE, 32);
        addBlock(Blocks.WHEAT, 80);
        addBlock(Blocks.FARMLAND, 1);
        addBlock(Blocks.FURNACE, 8);
        addBlock(Blocks.LIT_FURNACE, 8);
        addBlock(Blocks.STANDING_SIGN, 52);
        addBlock(Blocks.OAK_DOOR, 48);
        addBlock(Blocks.LADDER, 28);
        addBlock(Blocks.RAIL, 59);
        addBlock(Blocks.STONE_STAIRS, 1);
        addBlock(Blocks.WALL_SIGN, 52);
        addBlock(Blocks.LEVER, 5);
        addBlock(Blocks.STONE_PRESSURE_PLATE, 2);
        addBlock(Blocks.IRON_DOOR, 948);
        addBlock(Blocks.WOODEN_PRESSURE_PLATE, 16);
        addBlock(Blocks.REDSTONE_ORE, 655);
        addBlock(Blocks.LIT_REDSTONE_ORE, 655);
        addBlock(Blocks.UNLIT_REDSTONE_TORCH, 149);
        addBlock(Blocks.REDSTONE_TORCH, 149);
        addBlock(Blocks.STONE_BUTTON, 2);
        addBlock(Blocks.SNOW_LAYER, 1);
        addBlock(Blocks.ICE, 1);
        addBlock(Blocks.SNOW, 20);
        addBlock(Blocks.CACTUS, 96);
        addBlock(Blocks.CLAY, 237);
        addBlock(Blocks.REEDS, 32);
        addBlock(Blocks.JUKEBOX, 3630);
        addBlock(Blocks.ACACIA_FENCE, 12);// TODO add all fence types
        addBlock(Blocks.PUMPKIN, 192);
        addBlock(Blocks.NETHERRACK, 1);
        addBlock(Blocks.SOUL_SAND, 759);
        addBlock(Blocks.GLOWSTONE, 3038);
        addBlock(Blocks.PORTAL, 0);
        addBlock(Blocks.LIT_PUMPKIN, 192);
        addBlock(Blocks.CAKE, 576);
        addBlock(Blocks.UNPOWERED_REPEATER, 436);
        addBlock(Blocks.POWERED_REPEATER, 436);
        addBlock(Blocks.TRAPPED_CHEST, 0);
        addBlock(Blocks.TRAPDOOR, 0);
        addBlock(Blocks.MONSTER_EGG, 0);
        addBlock(Blocks.STONEBRICK, 11);
        addBlock(Blocks.RED_MUSHROOM_BLOCK, 0);
        addBlock(Blocks.BROWN_MUSHROOM_BLOCK, 0);
        addBlock(Blocks.IRON_BLOCK, 59);
        addBlock(Blocks.GLASS_PANE, 0);
        addBlock(Blocks.MELON_STEM, 0);
        addBlock(Blocks.PUMPKIN_STEM, 0);
        addBlock(Blocks.MELON_STEM, 0);
        addBlock(Blocks.VINE, 0);
        addBlock(Blocks.ACACIA_FENCE_GATE, 0);//TODO add more gates
        addBlock(Blocks.BRICK_STAIRS, 0);
        addBlock(Blocks.STONE_BRICK_STAIRS, 0);
        addBlock(Blocks.MYCELIUM, 1);
        addBlock(Blocks.WATERLILY, 3566);
        addBlock(Blocks.NETHER_BRICK, 46);
        addBlock(Blocks.NETHER_BRICK_FENCE, 0);
        addBlock(Blocks.NETHER_BRICK_STAIRS, 0);
        addBlock(Blocks.NETHER_WART, 0);
        addBlock(Blocks.ENCHANTING_TABLE, 7452);
        addBlock(Blocks.BREWING_STAND, 1200);
        addBlock(Blocks.CAULDRON, 1106);
        addBlock(Blocks.END_PORTAL_FRAME, 0);
        addBlock(Blocks.END_PORTAL, 0);
        addBlock(Blocks.END_STONE, 1);
        addBlock(Blocks.DRAGON_EGG, 320940);
        addBlock(Blocks.REDSTONE_LAMP, 3620);
        addBlock(Blocks.LIT_REDSTONE_LAMP, 3620);
        addBlock(Blocks.DOUBLE_WOODEN_SLAB, 0);
        addBlock(Blocks.WOODEN_SLAB, 0);
        addBlock(Blocks.COCOA, 0);
        addBlock(Blocks.SANDSTONE_STAIRS, 0);
        addBlock(Blocks.EMERALD_ORE, 3038);
        addBlock(Blocks.ENDER_CHEST, 7120);
        addBlock(Blocks.TRIPWIRE_HOOK, 158);
        addBlock(Blocks.TRIPWIRE, 229);
        addBlock(Blocks.EMERALD_BLOCK, 27342);
        addBlock(Blocks.SPRUCE_STAIRS, 0);
        addBlock(Blocks.BIRCH_STAIRS, 0);
        addBlock(Blocks.JUNGLE_STAIRS, 0);
        addBlock(Blocks.COMMAND_BLOCK, 0);
        addBlock(Blocks.BEACON, 64188); //taken down two tiers
        addBlock(Blocks.COBBLESTONE_WALL, 1);
        addBlock(Blocks.FLOWER_POT, 79);
        addBlock(Blocks.CARROTS, 0);
        addBlock(Blocks.POTATOES, 0);
        addBlock(Blocks.WOODEN_BUTTON, 2);
        addBlock(Blocks.SKULL, 23);
        addBlock(Blocks.ANVIL, 4898);
        addBlock(Blocks.TRAPPED_CHEST, 222);
        addBlock(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, 3038);
        addBlock(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, 316);
        addBlock(Blocks.UNPOWERED_REPEATER, 606);
        addBlock(Blocks.UNPOWERED_COMPARATOR, 606);
        addBlock(Blocks.DAYLIGHT_DETECTOR, 502);
        addBlock(Blocks.REDSTONE_BLOCK, 1310);
        addBlock(Blocks.QUARTZ_ORE, 158);
        addBlock(Blocks.HOPPER, 854);
        addBlock(Blocks.QUARTZ_BLOCK, 632);
        addBlock(Blocks.QUARTZ_STAIRS, 948);
        addBlock(Blocks.ACTIVATOR_RAIL, 84);
        addBlock(Blocks.DROPPER, 152);
        addBlock(Blocks.STAINED_HARDENED_CLAY, 247);
        addBlock(Blocks.HAY_BLOCK, 720);
        addBlock(Blocks.CARPET, 53);
        addBlock(Blocks.HARDENED_CLAY, 247);
        addBlock(Blocks.COAL_BLOCK, 756);

        energyRegistry.forEach((blk, eng) -> API.INSTANCE().registerTier(blk, eng));
    }

    private void addBlock(Block block, int energy){
        addBlock(block, energy, naturalBlocks.contains(block), crushableBlocks.contains(block),
                moveSensitiveBlocks.contains(block)); //call more detailed method
    }

    private void addBlock(Block type, int energy, boolean natural, boolean crushable, boolean sensitive){
        energyRegistry.put(type, new Tier(type, energy, natural, crushable, sensitive));
    }


//    public void registerRenderInformation() {} //NO-OP
    
//    public void registerTileEnitiy() {
//        GameRegistry.registerTileEntity(TileLightBeam.class, "TileLightBeam");
//    }
}
