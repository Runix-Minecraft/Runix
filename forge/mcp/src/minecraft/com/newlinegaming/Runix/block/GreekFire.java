package com.newlinegaming.Runix.block;

import static net.minecraftforge.common.ForgeDirection.DOWN;
import static net.minecraftforge.common.ForgeDirection.EAST;
import static net.minecraftforge.common.ForgeDirection.NORTH;
import static net.minecraftforge.common.ForgeDirection.SOUTH;
import static net.minecraftforge.common.ForgeDirection.UP;
import static net.minecraftforge.common.ForgeDirection.WEST;

import java.util.Random;

import com.newlinegaming.Runix.Runix;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

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
        this.setBurn(Block.stone.blockID, 30, 60);
        this.setBurn(Block.grass.blockID, 30, 60);
        this.setBurn(Block.dirt.blockID, 30, 60);
        this.setBurn(Block.gravel.blockID, 30, 60);
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
    
    public void updateTick(World par1World, int x, int y, int z, Random par5Random)
    {
        if (par1World.getGameRules().getGameRuleBooleanValue("doFireTick"))
        {
            Block base = Block.blocksList[par1World.getBlockId(x, y - 1, z)];
            boolean flag = (base != null && isGreekFireSource(base ));

            if (!this.canPlaceBlockAt(par1World, x, y, z))
            {
                par1World.setBlockToAir(x, y, z);
            }

            if (!flag && par1World.isRaining() && (par1World.canLightningStrikeAt(x, y, z) || par1World.canLightningStrikeAt(x - 1, y, z) || par1World.canLightningStrikeAt(x + 1, y, z) || par1World.canLightningStrikeAt(x, y, z - 1) || par1World.canLightningStrikeAt(x, y, z + 1)))
            {
                par1World.setBlockToAir(x, y, z);
            }
            else
            {
                int l = par1World.getBlockMetadata(x, y, z);

                if (l < 15)
                {
                    par1World.setBlockMetadataWithNotify(x, y, z, l + par5Random.nextInt(3) / 2, 4);
                }

                par1World.scheduleBlockUpdate(x, y, z, this.blockID, this.tickRate(par1World) + par5Random.nextInt(10));

                if (!flag && !this.canNeighborBurn(par1World, x, y, z))
                {
                    if (!par1World.doesBlockHaveSolidTopSurface(x, y - 1, z) || l > 3)
                    {
                        par1World.setBlockToAir(x, y, z);// remove fire because it has no base or it expired
                    }
                }
                else if (!flag && !this.canBlockCatchFire(par1World, x, y - 1, z, UP) && l == 15 && par5Random.nextInt(4) == 0)
                {
                    par1World.setBlockToAir(x, y, z);
                }
                else
                {
                    boolean flag1 = par1World.isBlockHighHumidity(x, y, z);
                    byte b0 = 0;

                    if (flag1)
                    {
                        b0 = -50;
                    }

                    this.tryToCatchBlockOnFire(par1World, x + 1, y, z, 300 + b0, par5Random, l, WEST );
                    this.tryToCatchBlockOnFire(par1World, x - 1, y, z, 300 + b0, par5Random, l, EAST );
                    this.tryToCatchBlockOnFire(par1World, x, y - 1, z, 250 + b0, par5Random, l, UP   );
                    this.tryToCatchBlockOnFire(par1World, x, y + 1, z, 250 + b0, par5Random, l, DOWN );
                    this.tryToCatchBlockOnFire(par1World, x, y, z - 1, 300 + b0, par5Random, l, SOUTH);
                    this.tryToCatchBlockOnFire(par1World, x, y, z + 1, 300 + b0, par5Random, l, NORTH);

                    for (int i1 = x - 1; i1 <= x + 1; ++i1)
                    {
                        for (int j1 = z - 1; j1 <= z + 1; ++j1)
                        {
                            for (int k1 = y - 1; k1 <= y + 4; ++k1)
                            {
                                if (i1 != x || k1 != y || j1 != z)
                                {
                                    int l1 = 100;

                                    if (k1 > y + 1)
                                    {
                                        l1 += (k1 - (y + 1)) * 100;
                                    }

                                    int i2 = this.getChanceOfNeighborsEncouragingFire(par1World, i1, k1, j1);

                                    if (i2 > 0)
                                    {
                                        int j2 = (i2 + 40 + par1World.difficultySetting * 7) / (l + 30);

                                        if (flag1)
                                        {
                                            j2 /= 2;
                                        }

                                        if (j2 > 0 && par5Random.nextInt(l1) <= j2 && (!par1World.isRaining() || !par1World.canLightningStrikeAt(i1, k1, j1)) && !par1World.canLightningStrikeAt(i1 - 1, k1, z) && !par1World.canLightningStrikeAt(i1 + 1, k1, j1) && !par1World.canLightningStrikeAt(i1, k1, j1 - 1) && !par1World.canLightningStrikeAt(i1, k1, j1 + 1))
                                        {
                                            int k2 = l + par5Random.nextInt(5) / 4;

                                            if (k2 > 15)
                                            {
                                                k2 = 15;
                                            }

                                            par1World.setBlock(i1, k1, j1, this.blockID, k2, 3);
                                        }
                                    }
                                }
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

    private void tryToCatchBlockOnFire(World par1World, int par2, int par3, int par4, int par5, Random par6Random, int par7, ForgeDirection face)
    {
        int j1 = 0;
        Block block = Block.blocksList[par1World.getBlockId(par2, par3, par4)];
        if (block != null)
        {
            j1 = getFlammability(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), face);
        }

        if (par6Random.nextInt(par5) < j1)
        {
            if (par6Random.nextInt(par7 + 10) < 5 && !par1World.canLightningStrikeAt(par2, par3, par4))
            {
                int k1 = par7 + par6Random.nextInt(5) / 4;

                if (k1 > 15)
                {
                    k1 = 15;
                }

                par1World.setBlock(par2, par3, par4, this.blockID, k1, 3);
            }
            else
            {
                par1World.setBlockToAir(par2, par3, par4);
            }
        }
    }

    private int getFlammability(World par1World, int x, int y, int z, int blockMetadata, ForgeDirection face) {
        x -= face.offsetX; 
        y -= face.offsetY;
        z -= face.offsetZ;
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

    private int getChanceOfNeighborsEncouragingFire(World par1World, int par2, int par3, int par4)
    {
        byte b0 = 0;

        if (!par1World.isAirBlock(par2, par3, par4))
        {
            return 0;
        }
        else
        {
            int l = this.getChanceToEncourageFire(par1World, par2 + 1, par3, par4, b0, WEST);
            l = this.getChanceToEncourageFire(par1World, par2 - 1, par3, par4, l, EAST);
            l = this.getChanceToEncourageFire(par1World, par2, par3 - 1, par4, l, UP);
            l = this.getChanceToEncourageFire(par1World, par2, par3 + 1, par4, l, DOWN);
            l = this.getChanceToEncourageFire(par1World, par2, par3, par4 - 1, l, SOUTH);
            l = this.getChanceToEncourageFire(par1World, par2, par3, par4 + 1, l, NORTH);
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
