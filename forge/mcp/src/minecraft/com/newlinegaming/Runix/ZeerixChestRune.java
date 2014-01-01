    package com.newlinegaming.Runix;

    import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

    import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ZeerixChestRune extends AbstractTimedRune {
    protected static ArrayList<PersistentRune> activeMagic = new ArrayList<PersistentRune>();
    protected int tier = 1;
    
    public ZeerixChestRune() {
    }

    public ZeerixChestRune(WorldXYZ coords, EntityPlayer player2) {
        super(coords, player2);
        tier = getTier(location);
        updateEveryXTicks(200);
    }

    @Override
    protected void onUpdateTick(EntityPlayer subject) {
        if(subject.equals(player))
        {
            World world = subject.worldObj;//sphere can be optimized to donut
            LinkedList<WorldXYZ> sphere = Util_SphericalFunctions.getShell(new WorldXYZ(player), 4);
            for(WorldXYZ newPos : sphere)
            {
                Material base = world.getBlockMaterial( ((int)newPos.posX), ((int)newPos.posY-1), ((int)newPos.posZ) );
                Material top = world.getBlockMaterial( ((int)newPos.posX), ((int)newPos.posY+1), ((int)newPos.posZ) );
                if(newPos.getBlockId() == 0 && base.isSolid() && !top.isSolid()){
                    try{
                        if(location.getBlockId() != Block.enderChest.blockID)
                            setBlockId(location, Block.enderChest.blockID);//charge for a replacement
                        moveBlock(location, newPos);
                    }catch( NotEnoughRunicEnergyException e){
                        reportOutOfGas(player);
                    }
                    
                    return; //we only need place the chest in one good position
                }
            }
        }
    }
  
    @Override
    public int[][][] runicTemplateOriginal() {
        this.flatRuneOnly = true;
        int GOLD = Block.oreGold.blockID;
        int CHST = Block.enderChest.blockID;
        return new int [][][] //NOTE: This is vertical notice the double }}
                {{{NONE,TIER,NONE}},
                  {{GOLD,TIER,GOLD}},
                  {{TIER,CHST,TIER}}}; 
    }

    @Override
    public String getRuneName() {
        return "Zeerix Chest";
    }

    @Override
    public ArrayList<PersistentRune> getActiveMagic() {
        return activeMagic;
    }

    @Override
    public boolean oneRunePerPerson() {
        return true;
    }

}
