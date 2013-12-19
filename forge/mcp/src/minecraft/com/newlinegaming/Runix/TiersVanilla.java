package com.newlinegaming.Runix;



import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.Block;


public class TiersVanilla {
    
    public static ArrayList<Integer> Tier0;
    public static ArrayList<Integer> naturalBlocks;
    protected HashMap<Integer, Integer> allTiers = new HashMap<Integer, Integer>();
    
    public TiersVanilla(){
        Block[] Tier0Blocks = new Block[]{
            Block.sand, Block.stone, Block.dirt, Block.grass, Block.tallGrass, Block.snow, 
            Block.mycelium, Block.netherrack, Block.signPost, Block.signWall};
        
        Tier0 = loadBlockIds(Tier0Blocks);
        Tier0.add( 0 );//Josiah: for whatever reason, AIR is not defined... so we have to add it manually.
        
        /**naturalBlocks is an important list because it lists all blocks that will not conduct runic energy*/
        Block[] extraNaturalBlocks = new Block[]{ //blocks that are natural, but not tier 0
            Block.waterStill, Block.waterMoving, Block.lavaStill, Block.lavaMoving,  
            Block.vine, Block.leaves, Block.cactus, Block.deadBush, Block.gravel,
            Block.ice, Block.sapling, Block.wood};
        naturalBlocks = loadBlockIds(extraNaturalBlocks);
        naturalBlocks.addAll(Tier0);
        
        Block[] Tier1Blocks = new Block[]{
        	Block.cobblestone, Block.stairsCobblestone, Block.cobblestoneWall, 
        	Block.pressurePlateStone, Block.sand, Block.stairsSandStone, Block.gravel,
        	Block.torchWood, Block.whiteStone, Block.leaves, Block.furnaceBurning, 
        	Block.furnaceIdle, Block.stoneButton, Block.stoneSingleSlab, Block.fire,
        	Block.planks, Block.stairsWoodOak, Block.stairsWoodBirch, Block.stairsWoodJungle,
        	Block.stairsWoodSpruce, Block.fence, Block.pressurePlatePlanks, Block.woodenButton,
        	Block.sandStone, Block.glass, Block.netherrack, Block.lever, Block.ice,
        	Block.cactus, Block.tripWire, Block.ladder, Block.bedrock,
        	Block.thinGlass, Block.tilledField};
        
        Block[] Tier2Blocks = new Block[]{
        	Block.waterStill, Block.waterMoving, Block.cloth, Block.wood, Block.netherBrick,
        	Block.stairsNetherBrick, Block.netherFence, Block.carrot, Block.reed, Block.waterlily,
        	Block.cocoaPlant, Block.melon, Block.workbench, Block.chest, Block.chestTrapped,
        	Block.glass, Block.blockNetherQuartz, Block.stoneBrick, Block.slowSand, Block.doorWood,
        	Block.trapdoor, Block.mushroomCapBrown, Block.mushroomCapRed, Block.mushroomBrown, 
        	Block.mushroomRed, Block.potato, Block.crops, Block.sapling, Block.netherStalk, 
        	Block.portal, Block.skull, Block.pumpkinStem, Block.melonStem, Block.endPortal};
        
        Block[] Tier3Blocks = new Block[]{
        	Block.oreIron, Block.redstoneWire, Block.torchRedstoneActive, Block.torchRedstoneIdle,
        	Block.obsidian, Block.pumpkin, Block.pumpkinLantern, Block.pistonBase, Block.blockClay,
        	Block.bookShelf, Block.rail, Block.railDetector, Block.fenceIron, Block.flowerPot, Block.bed,
        	Block.cake, Block.dispenser, Block.oreCoal, Block.oreRedstone, Block.oreRedstoneGlowing,
        	Block.redstoneRepeaterActive, Block.redstoneRepeaterIdle, Block.cobblestoneMossy, Block.pistonStickyBase,
        	Block.music, Block.lavaMoving, Block.lavaStill, Block.tripWireSource, Block.web, Block.sponge};
        
        Block[] Tier4Blocks = new Block[]{
        	Block.glowStone, Block.redstoneLampIdle, Block.redstoneLampActive, Block.blockRedstone,
        	Block.oreGold, Block.oreLapis, Block.blockIron, Block.doorIron, Block.cauldron,
        	Block.brewingStand, Block.railPowered, Block.railActivator, Block.enderChest, Block.tnt};
        
        Block[] Tier5Blocks = new Block[]{
        	Block.oreDiamond, Block.oreEmerald, Block.blockLapis, Block.anvil, Block.jukebox, Block.enchantmentTable,};
        
        Block[] Tier6Blocks = new Block[]{
        	Block.blockDiamond, Block.blockEmerald, Block.blockGold, Block.mobSpawner, Block.endPortalFrame};
        
        Block[] Tier7Blocks = new Block[]{
        	Block.dragonEgg, Block.beacon};
        
        	
        populateTierMap(Tier0Blocks, 0); //Josiah: anyone know how to procedurally fetch variable names?
        populateTierMap(Tier1Blocks, 1);
        populateTierMap(Tier2Blocks, 2);
        populateTierMap(Tier3Blocks, 3);
        populateTierMap(Tier4Blocks, 4);
        populateTierMap(Tier5Blocks, 5);
        populateTierMap(Tier6Blocks, 6);
        populateTierMap(Tier7Blocks, 7);
    }

    private void populateTierMap(Block[] singleTier, Integer value) {
        for(Block b : singleTier){
            allTiers.put(b.blockID, value);
        }
        
    }

    /**The idea behind this method is to take a list of Blocks and pull all the ids. 
     * It really only exists to cut down on the number of ".blockID" that is in this file given
     * how long it will be.  
     * @param blockList
     */
    private ArrayList<Integer> loadBlockIds(Block[] blockList) {
        ArrayList<Integer> IDs = new ArrayList<Integer>();
        for(Block block : blockList)
            IDs.add(block.blockID);
        return IDs;
    }
    
    public int getTier(int blockID){
        return (int) allTiers.get(new Integer(blockID));
    }
    
    public boolean isTier0(int blockID){
        return Tier0.contains(new Integer(blockID));
    }

    /**naturalBlocks is an important list because it lists all blocks that will not conduct runic energy*/
    public boolean isNatural(int blockID){
        return naturalBlocks.contains(new Integer(blockID));
    }
    
}


