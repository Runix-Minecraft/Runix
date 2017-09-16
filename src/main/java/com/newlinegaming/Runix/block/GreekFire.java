package com.newlinegaming.Runix.block;
/*
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.swing.Icon;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.client.renderer.Tessellator;
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
    
    private static GreekFire blockIdBackup = null;

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

    public static GreekFire getInstance(){
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
    @Override
    public IIcon getFireIcon(int par1) {
        return IconGreekFire;
    }

    private void initializeBlock() {
        setLightLevel(1f);
        setFireInfo(Blocks.stone, 30, 100);
        setFireInfo(Blocks.dirt, 30, 100);
        setFireInfo(Blocks.gravel, 30, 100);
        rebuildFireInfo();
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return null;
    }
    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return 71;
    }

    @Override
    public int quantityDropped(Random random) {
        return 0;
    }

    @Override
    public int tickRate(World par1World) {
        return 30;// + par1World.rand.nextInt(10);
    }

    @Override
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
        if(isGreekFireSource(world.getBlock(x, y - 1, z)))
            return false;

        if (!canPlaceBlockAt(world, x, y, z)){
            return true;// remove fire because it has no base and nothing to burn on
        }
        if(random.nextInt(50) == 1) {
            return true;// this is a low probability of dying out for all blocks, regardless of meta
        }
        world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));

        return false;
    }

    private boolean isGreekFireSource(Block base) {
        return base != null && base.equals(Blocks.lapis_block);
    }

    private void tryToCatchBlockOnFire(WorldXYZ loc, int lifespan)
    {
        if (getFlammability(loc) > 0){
            spreadAndAgeFire(loc, lifespan, loc.getX(), loc.getY(), loc.getZ());
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
        return getFlammability(block);
//        return greekFlammability.containsKey(block) ? greekFlammability.get(block) : 0;
    }

    private boolean canNeighborBurn(World par1World, int x, int y, int z){
        return canBlockCatchFire(par1World, x + 1, y, z ) ||
               canBlockCatchFire(par1World, x - 1, y, z ) ||
               canBlockCatchFire(par1World, x, y - 1, z ) ||
               canBlockCatchFire(par1World, x, y + 1, z ) ||
               canBlockCatchFire(par1World, x, y, z - 1) ||
               canBlockCatchFire(par1World, x, y, z + 1);
    }

    @Override
    public int getChanceToEncourageFire(IBlockAccess world, int x, int y, int z, int oldChance, ForgeDirection face) {
        try {
            return getFlammability(new WorldXYZ((World)world, x, y, z).offset(new Vector3(face)));
        }catch (ClassCastException ex){
            return 0;
        }
    }

    @Override
    public boolean canPlaceBlockAt(World par1World, int x, int y, int z) {
        return World.doesBlockHaveSolidTopSurface(par1World, x, y - 1, z) || this.canNeighborBurn(par1World, x, y, z);
    }

    @Override
    public void onNeighborBlockChange(World par1World, int x, int y, int z, Block block)
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
                coords.getWorld().scheduleBlockUpdate(coords.getX(), coords.getY(), coords.getZ(), ModBlock.greekFire, 4);

            }
            return true;
        }
        return false;
    }

    @Override
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

    @Override
    public boolean canBlockCatchFire(IBlockAccess world, int x, int y, int z)
    {
            return getFlammability(world.getBlock(x, y, z)) > 0;
    }

    //@Override
//    public int getChanceToEncourageFire(World world, int x, int y, int z, int oldChance, ForgeDirection face)
//    {
//        int newChance = 0;
//        Block block = world.getBlock(x, y, z);
//        if (block != null)
//        {
//            newChance = greekFireSpreadSpeed.containsKey(block) ? greekFireSpreadSpeed.get(block) : 0;
//        }
//        return (newChance > oldChance ? newChance : oldChance);
//    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta)
    {
        return (side == 0 || side == 1)? IconGreekFire : IconGreekFire1;
    }

}
*/