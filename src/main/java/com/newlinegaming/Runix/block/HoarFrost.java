package com.newlinegaming.Runix.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockIce;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.newlinegaming.Runix.RunixMain;
import com.newlinegaming.Runix.SigBlock;
import com.newlinegaming.Runix.Vector3;
import com.newlinegaming.Runix.WorldXYZ;
import com.newlinegaming.Runix.lib.LibInfo;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class HoarFrost extends BlockIce {
    
    private IIcon opaqueIcon;
    public EntityPlayer owner = null; // updated by HoarFrostItem

    public HoarFrost() {
//        super("ice", Material.ice, false);
        super();
        setTickRandomly(true);
        setCreativeTab(RunixMain.TabRunix);
        setHardness(0.5F);
        setStepSound(soundTypeGlass);
        setBlockName("runix:hoarfrost");
//        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.1225F, 1.0F);
    }

    @Override
    public int getRenderType() {
        return 72;
    }
    
    /**
     * Returns whether this block is collideable based on the arguments passed in n
     * @param meta block metaData n
     * @param p_149678_2_ whether the player right-clicked while holding a boat*/
    public boolean canCollideCheck(int meta, boolean p_149678_2_)
    {
        return meta != 1 && meta != 3 && meta != 4 && meta != 15;
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        int meta = world.getBlockMetadata(x, y, z);
        if(meta == 1 || meta == 3 || meta == 4 || meta == 15) //creep, stasis, explode delete
           return null;
        return super.getCollisionBoundingBoxFromPool(world, x, y, z);
    }
    
    public void setBlockBoundsForItemRender()
    {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }
    
    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }    
    
    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        return true;
    }
    
    
    
    @Override
    public boolean isBlockSolid(IBlockAccess world, int x, int y, int z, int side) {
        if(world.getBlock(x, y, z).equals(ModBlock.hoar_frost)){
            return false;
        }
        return Blocks.ice.isBlockSolid(world, x,y,z, side);
    }
    
    @Override
    public int damageDropped (int metadata) {
        return metadata;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" }) 
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(Item par1, CreativeTabs tab, List subItems) {
        int[] growthModes = {0, 1, 3, 4, 14, 15};
        for (int growthMode : growthModes) {
            subItems.add(new ItemStack(this, 1, growthMode));
        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if(meta == 1 || meta == 14)
            return blockIcon;
        return opaqueIcon;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister registry) {
        blockIcon = registry.registerIcon(LibInfo.MOD_ID + ":hoarfrost-partial-opaque_v3");
        opaqueIcon = registry.registerIcon(LibInfo.MOD_ID + ":hoarfrost-partial-opaque");
    }

    
    @Override
    public int tickRate(World par1World) {
        return 10 + par1World.rand.nextInt(10);
    }
    
    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
        int growthMode = world.getBlockMetadata(x, y, z);

        if(growthMode == 0) {//Origin Sequence
            //TODO if hit, explode
            world.scheduleBlockUpdate(x, y, z, this, 1500);
            ArrayList<WorldXYZ> neighbors = new WorldXYZ(world, x, y, z).getDirectNeighbors();
            for(WorldXYZ n : neighbors){
                n.setBlock(ModBlock.hoar_frost, 1);//create crawl expansion blocks
            }
            new WorldXYZ(world, x, y, z).setBlock(ModBlock.hoar_frost, 2);// next phase in the sequence
        }

        if(growthMode == 1) {//surface crawl
            world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world)); //schedule for child
            int randomIndex = random.nextInt(Vector3.conductanceNeighbors.length);
            WorldXYZ randomNeighbor = new WorldXYZ(world, x, y, z).offset(Vector3.conductanceNeighbors[randomIndex]);
            Block nBlock = randomNeighbor.getBlock();
            if(nBlock.equals(Blocks.air) || nBlock.equals(Blocks.tallgrass) || nBlock.equals(Blocks.double_plant)) {
                ArrayList<WorldXYZ> indirectNeighbors = randomNeighbor.getDirectNeighbors();
                for(WorldXYZ base : indirectNeighbors) {
                    Block block = base.getBlock();
                    if( !block.equals(ModBlock.hoar_frost) && !block.equals(Blocks.air) ) { //found a valid growth location
                        if(random.nextInt(1000) == 1) //important to limit the exponential growth of creep
                            growthMode = 3; //stasis mode
                        randomNeighbor.setBlock(ModBlock.hoar_frost, growthMode);
                        world.scheduleBlockUpdate(randomNeighbor.posX, randomNeighbor.posY, randomNeighbor.posZ, this, this.tickRate(world)); //schedule for child
                        return;
                    }
                }
            }
        }
        
        if(growthMode == 2) {//Origin Sequence 2
            //TODO if hit, explode
            world.scheduleBlockUpdate(x, y, z, this, 10);
            new WorldXYZ(world, x, y, z).setBlock(ModBlock.hoar_frost, 15);// final phase = delete the ice splotch
        }
        
        if(growthMode == 3) { //infectious shutdown stasis mode
            ArrayList<WorldXYZ> neighbors = new WorldXYZ(world, x, y, z).getNeighbors();
            for(WorldXYZ n : neighbors){
                SigBlock data = n.getSigBlock();
                if(data.blockID.equals(ModBlock.hoar_frost) && (data.meta == 1 || data.meta == 14)) {
                    n.setBlock(ModBlock.hoar_frost, growthMode);
                    world.scheduleBlockUpdate(n.posX, n.posY, n.posZ, this, 5); //schedule for neighbor
                }
            }
        }
        
        if(growthMode == 4) { //exploding
            ArrayList<WorldXYZ> neighbors = new WorldXYZ(world, x, y, z).getDirectNeighbors();
            for(WorldXYZ n : neighbors) {
                if(owner != null) {
                    SigBlock sig = n.getSigBlock();
                    sig.blockID.dropBlockAsItem(world, n.posX, n.posY, n.posZ, sig.meta, 0); // last param is fortune
                    n.setBlock(Blocks.air, 0);
                } else {
                    System.out.println("No owner");
                }
            }
            new WorldXYZ(world, x, y, z).setBlock(Blocks.air, 0);
        }

        if(growthMode == 14) { //Expanding shell
            ArrayList<WorldXYZ> neighbors = new WorldXYZ(world, x, y, z).getDirectNeighbors();
            int nCount = 0;
            for(WorldXYZ n : neighbors){
                if(n.getBlock().equals(Blocks.air))
                    ++nCount;
            }
            if(nCount == 0){
                new WorldXYZ(world, x, y, z).setBlock(ModBlock.runixAir, 0);
            } else {
                WorldXYZ target = neighbors.get(random.nextInt(neighbors.size()));
                if( target.getBlock().equals(Blocks.air))
                    target.setBlock(ModBlock.hoar_frost, growthMode);
                world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world)*2);
            }
        }
        
        if(growthMode == 15) { //infectious delete mode
            WorldXYZ me = new WorldXYZ(world, x, y, z);
            ArrayList<WorldXYZ> neighbors = me.getNeighbors();
            for(WorldXYZ n : neighbors){
                if(n.getBlock().equals(ModBlock.hoar_frost) || n.getBlock().equals(ModBlock.runixAir)){
                    n.setBlock(ModBlock.hoar_frost, growthMode); //spread the deletion
                    world.scheduleBlockUpdate(n.posX, n.posY, n.posZ, this, 3); //update neighbor quickly
                }
            }
            me.setBlock(Blocks.air, 0); //delete self
        }
        
    }
    
    @Override
    public void onBlockAdded(World par1World, int x, int y, int z)
    {
        par1World.scheduleBlockUpdate(x, y, z, this, this.tickRate(par1World));
    }
    

    
}
