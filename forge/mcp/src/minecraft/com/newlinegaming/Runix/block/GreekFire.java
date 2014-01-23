package com.newlinegaming.Runix.block;

import static net.minecraftforge.common.ForgeDirection.DOWN;
import static net.minecraftforge.common.ForgeDirection.EAST;
import static net.minecraftforge.common.ForgeDirection.NORTH;
import static net.minecraftforge.common.ForgeDirection.SOUTH;
import static net.minecraftforge.common.ForgeDirection.UP;
import static net.minecraftforge.common.ForgeDirection.WEST;

import java.util.Random;
import java.util.concurrent.DelayQueue;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import com.newlinegaming.Runix.BlockRecord;
import com.newlinegaming.Runix.Runix;
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
        setCreativeTab(Runix.TabRunix);
        blockIdBackup = blockId; //Josiah: This is a cludge. This is why there are all those static Block.stainedClay examples in vanilla  
        initializeBlock();
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
        return 30;
    }
    
    public void updateTick(World world, int x, int y, int z, Random random)
    {
        if (world.getGameRules().getGameRuleBooleanValue("doFireTick"))
        {
            Block base = Block.blocksList[world.getBlockId(x, y - 1, z)];
            boolean infiniteBurn = (base != null && isGreekFireSource(base ));

            if (!this.canPlaceBlockAt(world, x, y, z))
            {
                world.setBlockToAir(x, y, z);// correct invalid placement (probably neighbor changed)
            }

            if (!infiniteBurn && world.isRaining() && (world.canLightningStrikeAt(x, y, z) || world.canLightningStrikeAt(x - 1, y, z) || world.canLightningStrikeAt(x + 1, y, z) || world.canLightningStrikeAt(x, y, z - 1) || world.canLightningStrikeAt(x, y, z + 1)))
            {
                world.setBlockToAir(x, y, z);// fire got rained on and put out
            }
            else
            {
                int fireLifespan = world.getBlockMetadata(x, y, z);

                if (fireLifespan < 15)
                {
                    world.setBlockMetadataWithNotify(x, y, z, fireLifespan + random.nextInt(3) / 2, 4); //increments fireLifespan
                }

                world.scheduleBlockUpdate(x, y, z, this.blockID, this.tickRate(world) + random.nextInt(10));

                if (!infiniteBurn && !this.canNeighborBurn(world, x, y, z))
                {
                    if (!world.doesBlockHaveSolidTopSurface(x, y - 1, z) || fireLifespan > 3)
                    {
                        world.setBlockToAir(x, y, z);// remove fire because it has no base or it expired with no fuel source
                    }
                }
                else if (!infiniteBurn  && fireLifespan == 15 && random.nextInt(4) == 0)
                {//&& !this.canBlockCatchFire(world, x, y - 1, z, UP)
                    world.setBlockToAir(x, y, z);// fire dies of old age
                }
                else
                {
                    this.tryToCatchBlockOnFire(world, x + 1, y, z, 300, random, fireLifespan, WEST );//new fire spread (adjacent)
                    this.tryToCatchBlockOnFire(world, x - 1, y, z, 300, random, fireLifespan, EAST );
                    this.tryToCatchBlockOnFire(world, x, y - 1, z, 250, random, fireLifespan, UP   );
                    this.tryToCatchBlockOnFire(world, x, y + 1, z, 250, random, fireLifespan, DOWN );
                    this.tryToCatchBlockOnFire(world, x, y, z - 1, 300, random, fireLifespan, SOUTH);
                    this.tryToCatchBlockOnFire(world, x, y, z + 1, 300, random, fireLifespan, NORTH);

//                    tryFireJump(world, x, y, z, random, fireLifespan);
                }
            }
        }
    }

    private void tryFireJump(World world, int x, int y, int z, Random random, int fireLifespan) {
        for (int fX = x - 2; fX <= x + 2; ++fX)
        {
            for (int fZ = z - 2; fZ <= z + 2; ++fZ)
            {
                for (int fY = y - 2; fY <= y + 2; ++fY)
                {
                    if (fX != x || fY != y || fZ != z) //not this location
                    {
                        int heatLoss = 100;

                        if (fY > y + 1)
                        {
                            heatLoss += (fY - (y + 1)) * 100;//decreasing chance further away
                        }

                        int i2 = this.getChanceOfNeighborsEncouragingFire(world, fX, fY, fZ);

                        if (i2 > 0)
                        {
                            int j2 = (i2 + 47) / (fireLifespan + 30);

                            if (j2 > 0 && random.nextInt(heatLoss) <= j2 && (!world.isRaining() || !world.canLightningStrikeAt(fX, fY, fZ)) && !world.canLightningStrikeAt(fX - 1, fY, z) && !world.canLightningStrikeAt(fX + 1, fY, fZ) && !world.canLightningStrikeAt(fX, fY, fZ - 1) && !world.canLightningStrikeAt(fX, fY, fZ + 1))
                            {
                                int newLifespan = fireLifespan + 1;//random.nextInt(5) / 4;// increase age

                                if (newLifespan > 15) //cap age
                                    newLifespan = 15;

                                world.setBlock(fX, fY, fZ, this.blockID, newLifespan, 3); // new fire spread (distant)
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean isGreekFireSource(Block base) {
        if(base.blockID == Block.blockLapis.blockID)
            return true;
        return false;
    }

    public boolean func_82506_l()
    {
        return false;
    }

    private void tryToCatchBlockOnFire(World world, int x, int y, int z, int heatLoss, Random random, int lifespan, ForgeDirection face)
    {
        int flammability = 0;
        Block block = Block.blocksList[world.getBlockId(x, y, z)];
        if (block != null)
        {
            flammability = getFlammability(world, x, y, z, world.getBlockMetadata(x, y, z), face);
        }

        if (random.nextInt(heatLoss) < flammability)
        {
            if (random.nextInt(lifespan + 10) < 5 && !world.canLightningStrikeAt(x, y, z))
            {
                int newLifespan = lifespan + random.nextInt(5) / 4;

                if (newLifespan > 15)
                {
                    newLifespan = 15;
                }
                world.setBlock(x, y, z, this.blockID, newLifespan, 3);//fire spreads to a new location
            }
//            else
//            {
//                world.setBlockToAir(x, y, z);
//            }
        }
    }

    private int getFlammability(World par1World, int x, int y, int z, int blockMetadata, ForgeDirection face) {
        //x -= face.offsetX;//face is only used for blocks like stairs that have a non-solid face 
        //y -= face.offsetY;
        //z -= face.offsetZ;
        int block = par1World.getBlockId(x, y, z);
        //TODO we're still ignoring blockMetadata and which side of a stair is solid.  Josiah: I think this can be ignored
        return greekFlammability[block];
    }

    private boolean canNeighborBurn(World par1World, int par2, int par3, int par4)
    {
        return canBlockCatchFire(par1World, par2 + 1, par3, par4, WEST ) ||
               canBlockCatchFire(par1World, par2 - 1, par3, par4, EAST ) ||
               canBlockCatchFire(par1World, par2, par3 - 1, par4, UP   ) ||
               canBlockCatchFire(par1World, par2, par3 + 1, par4, DOWN ) ||
               canBlockCatchFire(par1World, par2, par3, par4 - 1, SOUTH) ||
               canBlockCatchFire(par1World, par2, par3, par4 + 1, NORTH);
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
    
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        if (!par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) && !this.canNeighborBurn(par1World, par2, par3, par4))
        {
            par1World.setBlockToAir(par2, par3, par4);
        }
    }
    
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        if (par1World.provider.dimensionId > 0 || par1World.getBlockId(par2, par3 - 1, par4) != Block.obsidian.blockID || !Block.portal.tryToCreatePortal(par1World, par2, par3, par4))
        {
            if (!par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) && !this.canNeighborBurn(par1World, par2, par3, par4))
            {
                par1World.setBlockToAir(par2, par3, par4);
            }
            else
            {
                par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate(par1World) + par1World.rand.nextInt(10));
            }
        }
    }
        
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.iconArray = new Icon[] {par1IconRegister.registerIcon("Runix:GreekFire"), par1IconRegister.registerIcon("Runix:GreekFire1")};
    }
    
    public boolean canBlockCatchFire(IBlockAccess world, int x, int y, int z, ForgeDirection face)
    {
//        Block block = Block.blocksList[world.getBlockId(x, y, z)];
//        if (block != null)
//        {
            return getFlammability((World) world, x, y, z, world.getBlockMetadata(x, y, z), face) > 0;
            //return greekFlammability[block.blockID] > 0;
            //return block.isFlammable(world, x, y, z, world.getBlockMetadata(x, y, z), face);
//        }
//        return false;
    }
    
    public int getChanceToEncourageFire(World world, int x, int y, int z, int oldChance, ForgeDirection face)
    {
        int newChance = 0;
        Block block = Block.blocksList[world.getBlockId(x, y, z)];
        if (block != null)
        {
            newChance = getFireSpreadSpeed(block, world, x, y, z, world.getBlockMetadata(x, y, z), face);
        }
        return (newChance > oldChance ? newChance : oldChance);
    }


    private int getFireSpreadSpeed(Block block, World world, int x, int y,
	    int z, int blockMetadata, ForgeDirection face) {
	return greekFireSpreadSpeed[block.blockID];
    }

    @SideOnly(Side.CLIENT)
    public Icon getIcon(int par1, int par2)
    {
        return this.iconArray[0];
    }

}
