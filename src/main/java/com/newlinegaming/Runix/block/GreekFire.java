package com.newlinegaming.Runix.block;

import static net.minecraftforge.common.util.ForgeDirection.DOWN;
import static net.minecraftforge.common.util.ForgeDirection.EAST;
import static net.minecraftforge.common.util.ForgeDirection.NORTH;
import static net.minecraftforge.common.util.ForgeDirection.SOUTH;
import static net.minecraftforge.common.util.ForgeDirection.UP;
import static net.minecraftforge.common.util.ForgeDirection.WEST;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ForgeDirection;

import com.newlinegaming.Runix.RunixMain;
import com.newlinegaming.Runix.SigBlock;
import com.newlinegaming.Runix.Tiers;
import com.newlinegaming.Runix.Vector3;
import com.newlinegaming.Runix.WorldXYZ;
import com.newlinegaming.Runix.lib.LibInfo;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GreekFire extends BlockFire {
    
    protected static HashMap<Block, Integer> greekFireSpreadSpeed = new HashMap<Block, Integer>();
    protected static HashMap<Block, Integer> greekFlammability = new HashMap<Block, Integer>();
    private static Block blockIdBackup = null;

    @SideOnly(Side.CLIENT)
    private IIcon IconGreekFire;
    @SideOnly(Side.CLIENT)
    private IIcon IconGreekFire1;


    private GreekFire() {
        super();
        setBlockName("Greek Fire");
        setTickRandomly(true);
        setCreativeTab(RunixMain.TabRunix);
        initializeBlock();
    }

    public static Block getInstance(){
        if(blockIdBackup == null)
            blockIdBackup = new GreekFire();
        return blockIdBackup;
    }

    
    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Block block, CreativeTabs tab, List subItems) {
        subItems.add(new ItemStack(block, 1, 0));
        subItems.add(new ItemStack(block, 1, 14));
    }

    @Override
    public int damageDropped (int metadata) {
        return metadata;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getFireIcon(int par1) {
        return IconGreekFire;
    }

    private void setBurn(Block block, int encouragement, int flammability){
        greekFireSpreadSpeed.put(block, encouragement);
        greekFlammability.put(block, flammability);
    }
    
    public void initializeBlock() {
        setLightLevel(1f);
        this.setBurn(Blocks.stone, 30, 100);
        this.setBurn(Blocks.dirt, 30, 100);
        this.setBurn(Blocks.gravel, 30, 100);
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return null;
    }
    
    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public int getRenderType() {
        return 3;
    }

    public int quantityDropped(Random random) {
        return 0;
    }

    public int tickRate(World par1World) {
        return 30;// + par1World.rand.nextInt(10);
    }

    public void updateTick(World world, int x, int y, int z, Random random) {
        if (world.getGameRules().getGameRuleBooleanValue("doFireTick")) {
            int fireLifespan = world.getBlockMetadata(x, y, z);

            if(fireDiesOut(world, x, y, z, random, fireLifespan)){
                world.setBlockToAir(x, y, z);
            }
            else if(fireLifespan < 15)
            {
                WorldXYZ loc = new WorldXYZ(world, x,y,z);
                for(WorldXYZ neighbor : loc.getNeighbors())
                    this.tryToCatchBlockOnFire(neighbor, fireLifespan );//new fire spread (adjacent)
            }
        }
    }

    //updated and needed
    private boolean fireDiesOut(World world, int x, int y, int z, Random random, int fireLifespan) {
        Block base = world.getBlock(x, y - 1, z);

        if(isGreekFireSource(base))
            return false;

        if (!canPlaceBlockAt(world, x, y-1, z)){
            return true;// remove fire because it has no base and nothing to burn on
        }
        if (fireLifespan == 15)
        {//&& !this.canBlockCatchFire(world, x, y - 1, z, UP)
            if(random.nextInt(100) == 1)
                return true;// this is a very low probability of dying out
        }
        world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));

        return false;
    }

    private boolean isGreekFireSource(Block base) {
        if(base != null && base.equals(Blocks.lapis_block))
            return true;
        return false;
    }

    private void tryToCatchBlockOnFire(WorldXYZ loc, int lifespan)
    {
        if (getFlammability(loc) > 0){
            spreadAndAgeFire(loc, lifespan, loc.posX, loc.posY, loc.posZ);
        }
    }

    private void spreadAndAgeFire(WorldXYZ loc, int lifespan, int parentX, int parentY, int parentZ) {
        int newLifespan = lifespan + 1;//random.nextInt(6) / 2;

        if (newLifespan > 15)
            return; // we don't allow old fires to propagate more

        loc.setBlockId(new SigBlock(this, newLifespan));//fire spreads to a new location
    }

    private int getFlammability(WorldXYZ loc) {
        Block block = loc.getBlock();
        return greekFlammability.containsKey(block) ? greekFlammability.get(block) : 0;
    }

    private boolean canNeighborBurn(World par1World, int par2, int par3, int par4){
        return canBlockCatchFire(par1World, par2 + 1, par3, par4 ) ||
               canBlockCatchFire(par1World, par2 - 1, par3, par4 ) ||
               canBlockCatchFire(par1World, par2, par3 - 1, par4 ) ||
               canBlockCatchFire(par1World, par2, par3 + 1, par4 ) ||
               canBlockCatchFire(par1World, par2, par3, par4 - 1) ||
               canBlockCatchFire(par1World, par2, par3, par4 + 1);
    }

    public int getChanceToEncourageFire(IBlockAccess world, int x, int y, int z, int oldChance, ForgeDirection face) {
        try {
            return getFlammability(new WorldXYZ((World)world, x, y, z).offset(new Vector3(face)));
        }catch (ClassCastException ex){
            return 0;
        }
    }

    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
        return World.doesBlockHaveSolidTopSurface(par1World, par2, par3 - 1, par4) || this.canNeighborBurn(par1World, par2, par3, par4);
    }

    public void onNeighborBlockChange(World par1World, int x, int y, int z, int par5)
    {
        if (!canPlaceBlockAt(par1World, x, y, z))
        {
            par1World.setBlockToAir(x, y, z);
        }
    }

    public static boolean consumeValuableForFuel(WorldXYZ coords, Block fuelBlock) {
        //consume energy from neighbor to lower meta data and allow spread
        if(fuelBlock == null)
            return false;
        int blockEnergy = Tiers.getEnergy(fuelBlock);
        if(blockEnergy > 1){
            if(!coords.getWorld().isRemote){
                int newLife = Math.min(Math.max(
                        coords.getMetaId() - Tiers.energyToRadiusConversion(blockEnergy, Tiers.blockBreakCost) //radius calculation
                        , 0),15);
                coords.setBlock( ModBlock.greekFire, newLife);
                ((WorldServer)coords.getWorld()).scheduleBlockUpdate(coords.posX, coords.posY, coords.posZ, ModBlock.greekFire, 4);

            }
            return true;
        }
        return false;
    }

    public void onBlockAdded(World par1World, int x, int y, int z)
    {
        if (!canPlaceBlockAt(par1World, x, y, z))
        {
            par1World.setBlockToAir(x, y, z);
        }
        else
        {
            par1World.scheduleBlockUpdate(x, y, z, this, this.tickRate(par1World));
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister reg)
    {
        IconGreekFire = reg.registerIcon(LibInfo.MOD_ID + ":GreekFire");
        IconGreekFire1 = reg.registerIcon(LibInfo.MOD_ID + ":GreekFire1");
    }
    
    
    public boolean canBlockCatchFire(World world, int x, int y, int z)
    {
            return getFlammability(new WorldXYZ(world, x, y, z)) > 0;
    }

    public int getChanceToEncourageFire(World world, int x, int y, int z, int oldChance, ForgeDirection face)
    {
        int newChance = 0;
        Block block = world.getBlock(x, y, z);
        if (block != null)
        {
            newChance = greekFireSpreadSpeed.containsKey(block) ? greekFireSpreadSpeed.get(block) : 0;
        }
        return (newChance > oldChance ? newChance : oldChance);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        return (side == 0 || side == 1)? IconGreekFire : IconGreekFire1;
    }

    
    
//    TODO: Add again later
//    public boolean renderBlockFire(BlockFire par1BlockFire, int par2, int par3, int par4)
//    {
//        Tessellator tessellator = Tessellator.instance;
//        Icon icon = par1BlockFire.getFireIcon(0);
//        Icon icon1 = par1BlockFire.getFireIcon(1);
//        Icon icon2 = icon;
//
//        tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
//        tessellator.setBrightness(par1BlockFire.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
//        double d0 = (double)icon2.getMinU();
//        double d1 = (double)icon2.getMinV();
//        double d2 = (double)icon2.getMaxU();
//        double d3 = (double)icon2.getMaxV();
//        float f = 1.4F;
//        double d4;
//        double d5;
//        double d6;
//        double d7;
//        double d8;
//        double d9;
//        double d10;
//
//        if (!this.blockAccess.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) && !Block.fire.canBlockCatchFire(this.blockAccess, par2, par3 - 1, par4, UP))
//        {
//            float f1 = 0.2F;
//            float f2 = 0.0625F;
//
//            if ((par2 + par3 + par4 & 1) == 1)
//            {
//                d0 = (double)icon1.getMinU();
//                d1 = (double)icon1.getMinV();
//                d2 = (double)icon1.getMaxU();
//                d3 = (double)icon1.getMaxV();
//            }
//
//            if ((par2 / 2 + par3 / 2 + par4 / 2 & 1) == 1)
//            {
//                d5 = d2;
//                d2 = d0;
//                d0 = d5;
//            }
//
//            if (Block.fire.canBlockCatchFire(this.blockAccess, par2 - 1, par3, par4, EAST))
//            {
//                tessellator.addVertexWithUV((double)((float)par2 + f1), (double)((float)par3 + f + f2), (double)(par4 + 1), d2, d1);
//                tessellator.addVertexWithUV((double)(par2 + 0), (double)((float)(par3 + 0) + f2), (double)(par4 + 1), d2, d3);
//                tessellator.addVertexWithUV((double)(par2 + 0), (double)((float)(par3 + 0) + f2), (double)(par4 + 0), d0, d3);
//                tessellator.addVertexWithUV((double)((float)par2 + f1), (double)((float)par3 + f + f2), (double)(par4 + 0), d0, d1);
//                tessellator.addVertexWithUV((double)((float)par2 + f1), (double)((float)par3 + f + f2), (double)(par4 + 0), d0, d1);
//                tessellator.addVertexWithUV((double)(par2 + 0), (double)((float)(par3 + 0) + f2), (double)(par4 + 0), d0, d3);
//                tessellator.addVertexWithUV((double)(par2 + 0), (double)((float)(par3 + 0) + f2), (double)(par4 + 1), d2, d3);
//                tessellator.addVertexWithUV((double)((float)par2 + f1), (double)((float)par3 + f + f2), (double)(par4 + 1), d2, d1);
//            }
//
//            if (Block.fire.canBlockCatchFire(this.blockAccess, par2 + 1, par3, par4, WEST))
//            {
//                tessellator.addVertexWithUV((double)((float)(par2 + 1) - f1), (double)((float)par3 + f + f2), (double)(par4 + 0), d0, d1);
//                tessellator.addVertexWithUV((double)(par2 + 1 - 0), (double)((float)(par3 + 0) + f2), (double)(par4 + 0), d0, d3);
//                tessellator.addVertexWithUV((double)(par2 + 1 - 0), (double)((float)(par3 + 0) + f2), (double)(par4 + 1), d2, d3);
//                tessellator.addVertexWithUV((double)((float)(par2 + 1) - f1), (double)((float)par3 + f + f2), (double)(par4 + 1), d2, d1);
//                tessellator.addVertexWithUV((double)((float)(par2 + 1) - f1), (double)((float)par3 + f + f2), (double)(par4 + 1), d2, d1);
//                tessellator.addVertexWithUV((double)(par2 + 1 - 0), (double)((float)(par3 + 0) + f2), (double)(par4 + 1), d2, d3);
//                tessellator.addVertexWithUV((double)(par2 + 1 - 0), (double)((float)(par3 + 0) + f2), (double)(par4 + 0), d0, d3);
//                tessellator.addVertexWithUV((double)((float)(par2 + 1) - f1), (double)((float)par3 + f + f2), (double)(par4 + 0), d0, d1);
//            }
//
//            if (Block.fire.canBlockCatchFire(this.blockAccess, par2, par3, par4 - 1, SOUTH))
//            {
//                tessellator.addVertexWithUV((double)(par2 + 0), (double)((float)par3 + f + f2), (double)((float)par4 + f1), d2, d1);
//                tessellator.addVertexWithUV((double)(par2 + 0), (double)((float)(par3 + 0) + f2), (double)(par4 + 0), d2, d3);
//                tessellator.addVertexWithUV((double)(par2 + 1), (double)((float)(par3 + 0) + f2), (double)(par4 + 0), d0, d3);
//                tessellator.addVertexWithUV((double)(par2 + 1), (double)((float)par3 + f + f2), (double)((float)par4 + f1), d0, d1);
//                tessellator.addVertexWithUV((double)(par2 + 1), (double)((float)par3 + f + f2), (double)((float)par4 + f1), d0, d1);
//                tessellator.addVertexWithUV((double)(par2 + 1), (double)((float)(par3 + 0) + f2), (double)(par4 + 0), d0, d3);
//                tessellator.addVertexWithUV((double)(par2 + 0), (double)((float)(par3 + 0) + f2), (double)(par4 + 0), d2, d3);
//                tessellator.addVertexWithUV((double)(par2 + 0), (double)((float)par3 + f + f2), (double)((float)par4 + f1), d2, d1);
//            }
//
//            if (Block.fire.canBlockCatchFire(this.blockAccess, par2, par3, par4 + 1, NORTH))
//            {
//                tessellator.addVertexWithUV((double)(par2 + 1), (double)((float)par3 + f + f2), (double)((float)(par4 + 1) - f1), d0, d1);
//                tessellator.addVertexWithUV((double)(par2 + 1), (double)((float)(par3 + 0) + f2), (double)(par4 + 1 - 0), d0, d3);
//                tessellator.addVertexWithUV((double)(par2 + 0), (double)((float)(par3 + 0) + f2), (double)(par4 + 1 - 0), d2, d3);
//                tessellator.addVertexWithUV((double)(par2 + 0), (double)((float)par3 + f + f2), (double)((float)(par4 + 1) - f1), d2, d1);
//                tessellator.addVertexWithUV((double)(par2 + 0), (double)((float)par3 + f + f2), (double)((float)(par4 + 1) - f1), d2, d1);
//                tessellator.addVertexWithUV((double)(par2 + 0), (double)((float)(par3 + 0) + f2), (double)(par4 + 1 - 0), d2, d3);
//                tessellator.addVertexWithUV((double)(par2 + 1), (double)((float)(par3 + 0) + f2), (double)(par4 + 1 - 0), d0, d3);
//                tessellator.addVertexWithUV((double)(par2 + 1), (double)((float)par3 + f + f2), (double)((float)(par4 + 1) - f1), d0, d1);
//            }
//
//            if (Block.fire.canBlockCatchFire(this.blockAccess, par2, par3 + 1, par4, DOWN))
//            {
//                d5 = (double)par2 + 0.5D + 0.5D;
//                d6 = (double)par2 + 0.5D - 0.5D;
//                d7 = (double)par4 + 0.5D + 0.5D;
//                d8 = (double)par4 + 0.5D - 0.5D;
//                d9 = (double)par2 + 0.5D - 0.5D;
//                d10 = (double)par2 + 0.5D + 0.5D;
//                d4 = (double)par4 + 0.5D - 0.5D;
//                double d11 = (double)par4 + 0.5D + 0.5D;
//                d0 = (double)icon.getMinU();
//                d1 = (double)icon.getMinV();
//                d2 = (double)icon.getMaxU();
//                d3 = (double)icon.getMaxV();
//                ++par3;
//                f = -0.2F;
//
//                if ((par2 + par3 + par4 & 1) == 0)
//                {
//                    tessellator.addVertexWithUV(d9, (double)((float)par3 + f), (double)(par4 + 0), d2, d1);
//                    tessellator.addVertexWithUV(d5, (double)(par3 + 0), (double)(par4 + 0), d2, d3);
//                    tessellator.addVertexWithUV(d5, (double)(par3 + 0), (double)(par4 + 1), d0, d3);
//                    tessellator.addVertexWithUV(d9, (double)((float)par3 + f), (double)(par4 + 1), d0, d1);
//                    d0 = (double)icon1.getMinU();
//                    d1 = (double)icon1.getMinV();
//                    d2 = (double)icon1.getMaxU();
//                    d3 = (double)icon1.getMaxV();
//                    tessellator.addVertexWithUV(d10, (double)((float)par3 + f), (double)(par4 + 1), d2, d1);
//                    tessellator.addVertexWithUV(d6, (double)(par3 + 0), (double)(par4 + 1), d2, d3);
//                    tessellator.addVertexWithUV(d6, (double)(par3 + 0), (double)(par4 + 0), d0, d3);
//                    tessellator.addVertexWithUV(d10, (double)((float)par3 + f), (double)(par4 + 0), d0, d1);
//                }
//                else
//                {
//                    tessellator.addVertexWithUV((double)(par2 + 0), (double)((float)par3 + f), d11, d2, d1);
//                    tessellator.addVertexWithUV((double)(par2 + 0), (double)(par3 + 0), d8, d2, d3);
//                    tessellator.addVertexWithUV((double)(par2 + 1), (double)(par3 + 0), d8, d0, d3);
//                    tessellator.addVertexWithUV((double)(par2 + 1), (double)((float)par3 + f), d11, d0, d1);
//                    d0 = (double)icon1.getMinU();
//                    d1 = (double)icon1.getMinV();
//                    d2 = (double)icon1.getMaxU();
//                    d3 = (double)icon1.getMaxV();
//                    tessellator.addVertexWithUV((double)(par2 + 1), (double)((float)par3 + f), d4, d2, d1);
//                    tessellator.addVertexWithUV((double)(par2 + 1), (double)(par3 + 0), d7, d2, d3);
//                    tessellator.addVertexWithUV((double)(par2 + 0), (double)(par3 + 0), d7, d0, d3);
//                    tessellator.addVertexWithUV((double)(par2 + 0), (double)((float)par3 + f), d4, d0, d1);
//                }
//            }
//        }
//        else
//        {
//            double d12 = (double)par2 + 0.5D + 0.2D;
//            d5 = (double)par2 + 0.5D - 0.2D;
//            d6 = (double)par4 + 0.5D + 0.2D;
//            d7 = (double)par4 + 0.5D - 0.2D;
//            d8 = (double)par2 + 0.5D - 0.3D;
//            d9 = (double)par2 + 0.5D + 0.3D;
//            d10 = (double)par4 + 0.5D - 0.3D;
//            d4 = (double)par4 + 0.5D + 0.3D;
//            tessellator.addVertexWithUV(d8, (double)((float)par3 + f), (double)(par4 + 1), d2, d1);
//            tessellator.addVertexWithUV(d12, (double)(par3 + 0), (double)(par4 + 1), d2, d3);
//            tessellator.addVertexWithUV(d12, (double)(par3 + 0), (double)(par4 + 0), d0, d3);
//            tessellator.addVertexWithUV(d8, (double)((float)par3 + f), (double)(par4 + 0), d0, d1);
//            tessellator.addVertexWithUV(d9, (double)((float)par3 + f), (double)(par4 + 0), d2, d1);
//            tessellator.addVertexWithUV(d5, (double)(par3 + 0), (double)(par4 + 0), d2, d3);
//            tessellator.addVertexWithUV(d5, (double)(par3 + 0), (double)(par4 + 1), d0, d3);
//            tessellator.addVertexWithUV(d9, (double)((float)par3 + f), (double)(par4 + 1), d0, d1);
//            d0 = (double)icon1.getMinU();
//            d1 = (double)icon1.getMinV();
//            d2 = (double)icon1.getMaxU();
//            d3 = (double)icon1.getMaxV();
//            tessellator.addVertexWithUV((double)(par2 + 1), (double)((float)par3 + f), d4, d2, d1);
//            tessellator.addVertexWithUV((double)(par2 + 1), (double)(par3 + 0), d7, d2, d3);
//            tessellator.addVertexWithUV((double)(par2 + 0), (double)(par3 + 0), d7, d0, d3);
//            tessellator.addVertexWithUV((double)(par2 + 0), (double)((float)par3 + f), d4, d0, d1);
//            tessellator.addVertexWithUV((double)(par2 + 0), (double)((float)par3 + f), d10, d2, d1);
//            tessellator.addVertexWithUV((double)(par2 + 0), (double)(par3 + 0), d6, d2, d3);
//            tessellator.addVertexWithUV((double)(par2 + 1), (double)(par3 + 0), d6, d0, d3);
//            tessellator.addVertexWithUV((double)(par2 + 1), (double)((float)par3 + f), d10, d0, d1);
//            d12 = (double)par2 + 0.5D - 0.5D;
//            d5 = (double)par2 + 0.5D + 0.5D;
//            d6 = (double)par4 + 0.5D - 0.5D;
//            d7 = (double)par4 + 0.5D + 0.5D;
//            d8 = (double)par2 + 0.5D - 0.4D;
//            d9 = (double)par2 + 0.5D + 0.4D;
//            d10 = (double)par4 + 0.5D - 0.4D;
//            d4 = (double)par4 + 0.5D + 0.4D;
//            tessellator.addVertexWithUV(d8, (double)((float)par3 + f), (double)(par4 + 0), d0, d1);
//            tessellator.addVertexWithUV(d12, (double)(par3 + 0), (double)(par4 + 0), d0, d3);
//            tessellator.addVertexWithUV(d12, (double)(par3 + 0), (double)(par4 + 1), d2, d3);
//            tessellator.addVertexWithUV(d8, (double)((float)par3 + f), (double)(par4 + 1), d2, d1);
//            tessellator.addVertexWithUV(d9, (double)((float)par3 + f), (double)(par4 + 1), d0, d1);
//            tessellator.addVertexWithUV(d5, (double)(par3 + 0), (double)(par4 + 1), d0, d3);
//            tessellator.addVertexWithUV(d5, (double)(par3 + 0), (double)(par4 + 0), d2, d3);
//            tessellator.addVertexWithUV(d9, (double)((float)par3 + f), (double)(par4 + 0), d2, d1);
//            d0 = (double)icon.getMinU();
//            d1 = (double)icon.getMinV();
//            d2 = (double)icon.getMaxU();
//            d3 = (double)icon.getMaxV();
//            tessellator.addVertexWithUV((double)(par2 + 0), (double)((float)par3 + f), d4, d0, d1);
//            tessellator.addVertexWithUV((double)(par2 + 0), (double)(par3 + 0), d7, d0, d3);
//            tessellator.addVertexWithUV((double)(par2 + 1), (double)(par3 + 0), d7, d2, d3);
//            tessellator.addVertexWithUV((double)(par2 + 1), (double)((float)par3 + f), d4, d2, d1);
//            tessellator.addVertexWithUV((double)(par2 + 1), (double)((float)par3 + f), d10, d0, d1);
//            tessellator.addVertexWithUV((double)(par2 + 1), (double)(par3 + 0), d6, d0, d3);
//            tessellator.addVertexWithUV((double)(par2 + 0), (double)(par3 + 0), d6, d2, d3);
//            tessellator.addVertexWithUV((double)(par2 + 0), (double)((float)par3 + f), d10, d2, d1);
//        }
//
//        return true;
//    }    
    
}
