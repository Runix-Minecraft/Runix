package com.newlinegaming.Runix;



import java.util.ArrayList;
import java.util.Arrays;
import net.minecraft.block.Block;


public class TiersVanilla {
    
    public static ArrayList<Integer> Tier0;
    public static ArrayList<Integer> Tier1;
    public static ArrayList<Integer> Tier2;
    public static ArrayList<Integer> Tier3;
    public static ArrayList<Integer> Tier4;
    public static ArrayList<Integer> Tier5;
    public static ArrayList<Integer> Tier6;
    public static ArrayList<Integer> Tier7;
    
    public TiersVanilla(){
        Block[] Tier0Blocks = new Block[]{
            Block.sand, Block.stone, Block.dirt, Block.grass, Block.tallGrass, Block.snow, 
            Block.mycelium, Block.netherrack, Block.signPost, Block.signWall};
        
        Tier0 = loadBlockIds(Tier0Blocks);
        Tier0.add( 0 );//Josiah: for whatever reason, AIR is not defined... so we have to add it manually.
        
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
        
        Tier1 = loadBlockIds(Tier1Blocks);
        
        Block[] Tier2Blocks = new Block[]{
        	Block.waterStill, Block.waterMoving, Block.cloth, Block.wood, Block.netherBrick,
        	Block.stairsNetherBrick, Block.netherFence, Block.carrot, Block.reed, Block.waterlily,
        	Block.cocoaPlant, Block.melon, Block.workbench, Block.chest, Block.chestTrapped,
        	Block.glass, Block.blockNetherQuartz, Block.stoneBrick, Block.slowSand, Block.doorWood,
        	Block.trapdoor, Block.mushroomCapBrown, Block.mushroomCapRed, Block.mushroomBrown, 
        	Block.mushroomRed, Block.potato, Block.crops, Block.sapling, Block.netherStalk, 
        	Block.portal, Block.skull, Block.pumpkinStem, Block.melonStem, Block.endPortal};
        
        Tier2 = loadBlockIds(Tier2Blocks);
        
        Block[] Tier3Blocks = new Block[]{
        	Block.oreIron, Block.redstoneWire, Block.torchRedstoneActive, Block.torchRedstoneIdle,
        	Block.obsidian, Block.pumpkin, Block.pumpkinLantern, Block.pistonBase, Block.blockClay,
        	Block.bookShelf, Block.rail, Block.railDetector, Block.fenceIron, Block.flowerPot, Block.bed,
        	Block.cake, Block.dispenser, Block.oreCoal, Block.oreRedstone, Block.oreRedstoneGlowing,
        	Block.redstoneRepeaterActive, Block.redstoneRepeaterIdle, Block.cobblestoneMossy, Block.pistonStickyBase,
        	Block.music, Block.lavaMoving, Block.lavaStill, Block.tripWireSource, Block.web, Block.sponge};
        
        Tier3 = loadBlockIds(Tier3Blocks);
        
        Block[] Tier4Blocks = new Block[]{
        	Block.glowStone, Block.redstoneLampIdle, Block.redstoneLampActive, Block.blockRedstone,
        	Block.oreGold, Block.oreLapis, Block.blockIron, Block.doorIron, Block.cauldron,
        	Block.brewingStand, Block.railPowered, Block.railActivator, Block.enderChest, Block.tnt};
        
        Tier4 = loadBlockIds(Tier4Blocks);
        
        Block[] Tier5Blocks = new Block[]{
        	Block.oreDiamond, Block.oreEmerald, Block.blockLapis, Block.anvil, Block.jukebox, Block.enchantmentTable,};
        
        Tier5 = loadBlockIds(Tier5Blocks);
        
        Block[] Tier6Blocks = new Block[]{
        	Block.blockDiamond, Block.blockEmerald, Block.blockGold, Block.mobSpawner, Block.endPortalFrame};
        
        Tier6 = loadBlockIds(Tier6Blocks);
        
        Block[] Tier7Blocks = new Block[]{
        	Block.dragonEgg, Block.beacon};
        
        Tier7 = loadBlockIds(Tier7Blocks);
        	
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
    
    public boolean isTier0(int blockID){
        return Tier0.contains(new Integer(blockID));
    }
    
}


