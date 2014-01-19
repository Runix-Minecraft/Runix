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

import com.newlinegaming.Runix.Runix;
import com.newlinegaming.Runix.WorldXYZ;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GreekFire extends BlockFire {
    
    protected static int[] greekFireSpreadSpeed = new int[4096];
    protected static int[] greekFlammability = new int[4096];
    protected static DelayQueue<BlockRecord> phasedBlocks = new DelayQueue<BlockRecord>();
    
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
        return 5;//30
    }
    
    public void updateTick(World world, int x, int y, int z, Random random)
    {
        unphaseExpiredBlocks();
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
                world.setBlockToAir(x, y, z);
            }
            else
            {
                int fireLifespan = world.getBlockMetadata(x, y, z);

                if (fireLifespan < 15)
                {
                    world.setBlockMetadataWithNotify(x, y, z, fireLifespan + random.nextInt(3) / 2, 4);
                }

                world.scheduleBlockUpdate(x, y, z, this.blockID, this.tickRate(world) + random.nextInt(10));

                if (!infiniteBurn && !this.canNeighborBurn(world, x, y, z))
                {
                    if (!world.doesBlockHaveSolidTopSurface(x, y - 1, z) || fireLifespan > 3)
                    {
                        world.setBlockToAir(x, y, z);// remove fire because it has no base or it expired
                    }
                }
                else if (!infiniteBurn && !this.canBlockCatchFire(world, x, y - 1, z, UP) && fireLifespan == 15 && random.nextInt(4) == 0)
                {
                    world.setBlockToAir(x, y, z);// fire dies of old age
                }
                else
                {
                    boolean flag1 = world.isBlockHighHumidity(x, y, z);
                    byte b0 = 0;

                    if (flag1)
                    {
                        b0 = -50;
                    }

                    this.tryToCatchBlockOnFire(world, x + 1, y, z, 300 + b0, random, fireLifespan, WEST );
                    this.tryToCatchBlockOnFire(world, x - 1, y, z, 300 + b0, random, fireLifespan, EAST );
                    this.tryToCatchBlockOnFire(world, x, y - 1, z, 250 + b0, random, fireLifespan, UP   );
                    this.tryToCatchBlockOnFire(world, x, y + 1, z, 250 + b0, random, fireLifespan, DOWN );
                    this.tryToCatchBlockOnFire(world, x, y, z - 1, 300 + b0, random, fireLifespan, SOUTH);
                    this.tryToCatchBlockOnFire(world, x, y, z + 1, 300 + b0, random, fireLifespan, NORTH);

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

                                    int i2 = this.getChanceOfNeighborsEncouragingFire(world, i1, k1, j1);

                                    if (i2 > 0)
                                    {
                                        int j2 = (i2 + 40 + world.difficultySetting * 7) / (fireLifespan + 30);

                                        if (flag1)
                                        {
                                            j2 /= 2;
                                        }

                                        if (j2 > 0 && random.nextInt(l1) <= j2 && (!world.isRaining() || !world.canLightningStrikeAt(i1, k1, j1)) && !world.canLightningStrikeAt(i1 - 1, k1, z) && !world.canLightningStrikeAt(i1 + 1, k1, j1) && !world.canLightningStrikeAt(i1, k1, j1 - 1) && !world.canLightningStrikeAt(i1, k1, j1 + 1))
                                        {
                                            int k2 = fireLifespan + random.nextInt(5) / 4;

                                            if (k2 > 15)
                                            {
                                                k2 = 15;
                                            }
                                            phaseBlockAt(new WorldXYZ(i1, k1, j1));
                                            world.setBlock(i1, k1, j1, this.blockID, k2, 3);
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

    private void phaseBlockAt(WorldXYZ coords) {
        if(coords.getSigBlock().blockID == this.blockIdBackup){
            System.err.println("You just tried to phase fire!");
            return;
        }
        BlockRecord record = new BlockRecord(60, coords, coords.getSigBlock());
        phasedBlocks.add(record);
    }

    private void unphaseExpiredBlocks() {
        
        for( BlockRecord expired = phasedBlocks.poll(); expired != null; expired = phasedBlocks.poll()){
            //TODO drop block if non-air block
            System.out.println(expired.loc.toString() + "  ==  " + expired.block.blockID);
            expired.loc.setBlockIdAndUpdate(expired.block.blockID);
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

    private void tryToCatchBlockOnFire(World world, int x, int y, int z, int par5, Random random, int par7, ForgeDirection face)
    {
        int flammability = 0;
        Block block = Block.blocksList[world.getBlockId(x, y, z)];
        if (block != null)
        {
            flammability = getFlammability(world, x, y, z, world.getBlockMetadata(x, y, z), face);
        }

        if (random.nextInt(par5) < flammability)
        {
            if (random.nextInt(par7 + 10) < 5 && !world.canLightningStrikeAt(x, y, z))
            {
                int k1 = par7 + random.nextInt(5) / 4;

                if (k1 > 15)
                {
                    k1 = 15;
                }
//                phaseBlockAt(new WorldXYZ( x, y, z));
                world.setBlock(x, y, z, this.blockID, k1, 3);
            }
            else
            {
                world.setBlockToAir(x, y, z);
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
