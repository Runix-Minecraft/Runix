package com.newlinegaming.Runix.block;

import static net.minecraftforge.common.ForgeDirection.DOWN;
import static net.minecraftforge.common.ForgeDirection.EAST;
import static net.minecraftforge.common.ForgeDirection.NORTH;
import static net.minecraftforge.common.ForgeDirection.SOUTH;
import static net.minecraftforge.common.ForgeDirection.UP;
import static net.minecraftforge.common.ForgeDirection.WEST;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import com.newlinegaming.Runix.Runix;
import com.newlinegaming.Runix.SigBlock;
import com.newlinegaming.Runix.Tiers;
import com.newlinegaming.Runix.WorldXYZ;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GreekFire extends BlockFire {
    
    protected static int[] greekFireSpreadSpeed = new int[4096];
    protected static int[] greekFlammability = new int[4096];
    
    @SideOnly(Side.CLIENT)
    private Icon[] iconArray;

    public static int blockIdBackup = 2014;

    public GreekFire(int blockId) {
        super(blockId);
        this.setTickRandomly(true);
        blockIdBackup = blockId; //Josiah: This is a cludge. This is why there are all those static Block.stainedClay examples in vanilla  
        initializeBlock();
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(int block, CreativeTabs tab, List subItems) {
        subItems.add(new ItemStack(block, 1, 0));
        subItems.add(new ItemStack(block, 1, 14));
    }
    
    @Override
    public int damageDropped (int metadata) {
        return metadata;
    }
    
    @SideOnly(Side.CLIENT)
    public Icon getFireIcon(int par1)
    {
        return this.iconArray[par1];
    }
    
    public void initializeBlock()
    {
        this.setLightValue(1.0f);
        this.setBurn(Block.stone.blockID, 30, 100);
        this.setBurn(Block.grass.blockID, 30, 100);
        this.setBurn(Block.dirt.blockID, 30, 100);
        this.setBurn(Block.gravel.blockID, 30, 100);
    }

    private void setBurn(int id, int encouragement, int flammability)
    {
        greekFireSpreadSpeed[id] = encouragement;
        greekFlammability[id] = flammability;
    }
        
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return null;
    }
    
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    public int getRenderType()
    {
        return 3;
    }
    
    public int quantityDropped(Random par1Random)
    {
        return 0;
    }
    
    public int tickRate(World par1World)
    {
        return 30;// + par1World.rand.nextInt(10);
    }
    
    public void updateTick(World world, int x, int y, int z, Random random)
    {
        if (world.getGameRules().getGameRuleBooleanValue("doFireTick"))
        {
            int fireLifespan = world.getBlockMetadata(x, y, z);

            if(fireDiesOut(world, x, y, z, random, fireLifespan)){
                if(world.getBlockId(x, y, z) != Block.glass.blockID) //sometimes it crystallizes instead
                    world.setBlockToAir(x, y, z);    
            }
            else if(fireLifespan < 15)
            {
                int heatLoss = 1;
                WorldXYZ loc = new WorldXYZ(world, x,y,z);
                for(WorldXYZ neighbor : loc.getNeighbors())
                    this.tryToCatchBlockOnFire(neighbor, heatLoss, random, fireLifespan );//new fire spread (adjacent)

                tryFireJump(world, x, y, z, random, fireLifespan);
            }
        }
    }

    private boolean fireDiesOut(World world, int x, int y, int z, Random random, int fireLifespan) {
        Block base = Block.blocksList[world.getBlockId(x, y - 1, z)];
        
        if(isGreekFireSource(base ))
            return false;
        
        if (!this.canPlaceBlockAt(world, x, y, z))
            return true;// correct invalid placement (probably neighbor changed)

        if (world.isRaining())// && (world.canLightningStrikeAt(x, y, z) || world.canLightningStrikeAt(x - 1, y, z) || world.canLightningStrikeAt(x + 1, y, z) || world.canLightningStrikeAt(x, y, z - 1) || world.canLightningStrikeAt(x, y, z + 1)))
            return true;// fire got rained on and put out
        else
        {
            world.scheduleBlockUpdate(x, y, z, this.blockID, this.tickRate(world));

            if (!this.canNeighborBurn(world, x, y, z)){
                if (!world.doesBlockHaveSolidTopSurface(x, y - 1, z) || fireLifespan > 3){
                    return true;// remove fire because it has no base or it expired with no fuel source
                }
            }
            else if (fireLifespan == 15)
            {//&& !this.canBlockCatchFire(world, x, y - 1, z, UP)
//                if(random.nextInt(4) == 0)
//                    new WorldXYZ(world, x, y, z).setBlockIdAndUpdate(Block.glass.blockID);// fire dies of old age
                return false;//even if the block is not removed, we need to not spread
            }
        }
        return false;
    }

    private void tryFireJump(World world, int x, int y, int z, Random random, int fireLifespan) {
        for (int fX = x - 1; fX <= x + 1; ++fX){
            for (int fZ = z - 1; fZ <= z + 1; ++fZ){
                for (int fY = y - 1; fY <= y + 1; ++fY){
                    if (fX != x || fY != y || fZ != z) //not this location
                    {
                        int heatLoss = 100;
                        int i2 = this.getChanceOfNeighborsEncouragingFire(world, fX, fY, fZ);

                        if (i2 > 0)
                        {
                            int j2 = (i2 + 47) / (fireLifespan + 30);

                            if (j2 > 0 && random.nextInt(heatLoss) <= j2 && !world.isRaining() )
                            {
                                spreadAndAgeFire(new WorldXYZ(world, fX, fY, fZ), random, fireLifespan, x,y,z); // new fire spread (distant)
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean isGreekFireSource(Block base) {
        if(base != null && base.blockID == Block.blockLapis.blockID)
            return true;
        return false;
    }

    public boolean func_82506_l()
    {
        return false;
    }

    private void tryToCatchBlockOnFire(WorldXYZ loc, int heatLoss, Random random, int lifespan)
    {
        int flammability = 0;
        flammability = getFlammability(loc);

        if (random.nextInt(heatLoss) < flammability)
        {
            if (random.nextInt(lifespan + 10) < 5)
            {
                spreadAndAgeFire(loc, random, lifespan, loc.posX, loc.posY, loc.posZ);
            }
//            else
//                world.setBlockToAir(x, y, z);
        }
    }

    private void spreadAndAgeFire(WorldXYZ loc, Random random, int lifespan, int parentX, int parentY, int parentZ) {
        int newLifespan = lifespan + 1;//random.nextInt(6) / 2;

        if (newLifespan > 15)
            return; // we don't allow old fires to propagate more
            //newLifespan = 15;

        loc.setBlockId(new SigBlock(this.blockID, newLifespan));//fire spreads to a new location
    }

    private int getFlammability(WorldXYZ loc) {
        int block = loc.getBlockId();
        return greekFlammability[block];
    }

    private boolean canNeighborBurn(World par1World, int par2, int par3, int par4)
    {
        return canBlockCatchFire(par1World, par2 + 1, par3, par4 ) ||
               canBlockCatchFire(par1World, par2 - 1, par3, par4 ) ||
               canBlockCatchFire(par1World, par2, par3 - 1, par4   ) ||
               canBlockCatchFire(par1World, par2, par3 + 1, par4 ) ||
               canBlockCatchFire(par1World, par2, par3, par4 - 1) ||
               canBlockCatchFire(par1World, par2, par3, par4 + 1);
    }

    private int getChanceOfNeighborsEncouragingFire(World par1World, int x, int y, int z)
    {
        byte b0 = 0;

        if (!par1World.isAirBlock(x, y, z))
        {
            return 0;
        }
        else
        {
            int l = this.getChanceToEncourageFire(par1World, x + 1, y, z, b0, WEST);
            l = this.getChanceToEncourageFire(par1World, x - 1, y, z, l, EAST);
            l = this.getChanceToEncourageFire(par1World, x, y - 1, z, l, UP);
            l = this.getChanceToEncourageFire(par1World, x, y + 1, z, l, DOWN);
            l = this.getChanceToEncourageFire(par1World, x, y, z - 1, l, SOUTH);
            l = this.getChanceToEncourageFire(par1World, x, y, z + 1, l, NORTH);
            return l;
        }
    }
    
    public boolean canBlockCatchFire(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return canBlockCatchFire(par1IBlockAccess, par2, par3, par4, UP);
    }

    public int getChanceToEncourageFire(World par1World, int par2, int par3, int par4, int par5)
    {
        return getChanceToEncourageFire(par1World, par2, par3, par4, par5, UP);
    }
    
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        return par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) || this.canNeighborBurn(par1World, par2, par3, par4);
    }
    
    public void onNeighborBlockChange(World par1World, int x, int y, int z, int par5)
    {
        if (!par1World.doesBlockHaveSolidTopSurface(x, y - 1, z) && !this.canNeighborBurn(par1World, x, y, z))
        {
            par1World.setBlockToAir(x, y, z);
        }
        consumeValuableForFuel(par1World, x, y, z);// this will consume natural ores
            
    }

    private void consumeValuableForFuel(World par1World, int x, int y, int z) {
        //consume energy from neighbor to lower meta data and allow spread
        WorldXYZ valuable = new WorldXYZ(par1World, x, y, z).mostValuableNeighbor();
        int tier = Tiers.getTier(valuable.getBlockId());
        if(tier > 3){
//            int newLife = Math.min(Math.max(par1World.getBlockMetadata(x, y, z) - (tier-3), 0),15);
            par1World.setBlock(x, y, z, blockID, 14, 3);
//            valuable.setBlockIdAndUpdate(0);//delete neighbor
        }
    }
    
    public void onBlockAdded(World par1World, int x, int y, int z)
    {
        if (!par1World.doesBlockHaveSolidTopSurface(x, y - 1, z) && !this.canNeighborBurn(par1World, x, y, z))
        {
            par1World.setBlockToAir(x, y, z);
        }
        else
        {
            par1World.scheduleBlockUpdate(x, y, z, this.blockID, this.tickRate(par1World));
        }
    }
        
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.iconArray = new Icon[] {par1IconRegister.registerIcon("Runix:GreekFire"), par1IconRegister.registerIcon("Runix:GreekFire1")};
    }
    
    public boolean canBlockCatchFire(World world, int x, int y, int z)
    {
            return getFlammability(new WorldXYZ(world, x, y, z)) > 0;
    }
    
    public int getChanceToEncourageFire(World world, int x, int y, int z, int oldChance, ForgeDirection face)
    {
        int newChance = 0;
        Block block = Block.blocksList[world.getBlockId(x, y, z)];
        if (block != null)
        {
            newChance = greekFireSpreadSpeed[block.blockID];
        }
        return (newChance > oldChance ? newChance : oldChance);
    }

    @SideOnly(Side.CLIENT)
    public Icon getIcon(int par1, int par2)
    {
        return this.iconArray[0];
    }

}
