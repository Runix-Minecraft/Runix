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
                    if(location.getBlockId() == Block.enderChest.blockID)
                        location.setBlockId(0); //delete old chest
                    else{
                        disabled = true; //someone broke the Zeerix Chest!
                        return;
                    }
                    newPos.setBlockId(Block.enderChest.blockID); //place chest
                    location = newPos;
                    return;
                }
            }
        }
    }
  
    @Override
    public int[][][] blockPattern() {
        int GOLD = Block.oreGold.blockID;
        int CHST = Block.enderChest.blockID;
        return new int [][][] //NOTE: This is vertical notice the double }}
                {{{NONE,TIER,NONE}},
                  {{GOLD,TIER,GOLD}},
                  {{TIER,CHST,TIER}}}; 
    }

    @Override
    public void execute(EntityPlayer activator, WorldXYZ coords) {
        if(!activator.worldObj.isRemote)
            activeMagic.add(new ZeerixChestRune(coords.offset(0, -2, 0), activator));
    }

    @Override
    public String getRuneName() {
        return "Zeerix Chest";
    }

    @Override
    public ArrayList<PersistentRune> getActiveMagic() {
        return activeMagic;
    }

}
