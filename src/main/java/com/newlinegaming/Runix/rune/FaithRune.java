package com.newlinegaming.Runix.rune;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;

import com.newlinegaming.Runix.NotEnoughRunicEnergyException;
import com.newlinegaming.Runix.PersistentRune;
import com.newlinegaming.Runix.Tiers;
import com.newlinegaming.Runix.block.ModBlock;
import com.newlinegaming.Runix.utils.Util_SphericalFunctions;
import com.newlinegaming.Runix.WorldXYZ;

public class FaithRune extends PersistentRune{

    private static final ArrayList<PersistentRune> activeFaithList = new ArrayList<>();
    private Integer radius = 11;
    private boolean firstTime;
    protected boolean useCollisionDetection = true;//option to turn off collision detection through JSON
    private transient LinkedHashSet<WorldXYZ> sphere = null;//volatile so that JSON doesn't try to cache this thing
    
    public FaithRune() {
        runeName = "Faith";
    }
    public FaithRune(WorldXYZ loc, EntityPlayer creator) {
        super(loc, creator, "Faith");
        firstTime = true;
    }

    public Block[][][] runicTemplateOriginal(){
        Block gold = Blocks.gold_block;
        return new Block[][][] {{
            {NONE,NONE,NONE},
            {NONE,gold,NONE},
            {NONE,NONE,NONE}},
            {{TIER,gold,TIER},
             {gold,TIER,gold},
             {TIER,gold,TIER}
        }};
    }


    @Override
    protected void poke(EntityPlayer poker, WorldXYZ coords) {
        if(firstTime){// firstTime prevents players from injecting more energy by building a second rune on top of the first
            firstTime = false;
            radius = getTier() * 2 - 1; //Tiers.energyToRadiusConversion(energy);
            consumeRune(coords);
            try {
                setBlockIdAndUpdate(coords, ModBlock.fakeGoldBlock); //Gold block is to be a permanent marker
            } catch (NotEnoughRunicEnergyException e) {}
            HashSet<WorldXYZ> tSphere = fullStructure();
            energy -= tSphere.size() * Tiers.blockMobilizationCost;
            aetherSay(poker, "Created a Faith Sphere with a radius of "+ radius + " and " + sphere.size() + " blocks.");
            bounceIsland();
        }
    }

    /**
     * bounceIsland() will place the sphere sitting on top of the old sphere's location (y+diameter).  It is used the first time
     * Faith is activated. 
     * Josiah: I've tried to speed this up as much as possible with little effect.  Profiling is needed.
     */
    private void bounceIsland() {
        //assumes fullStructure() has already been called
        int height = Math.min(location.posY + radius*2+1, 255 - radius-1);// places a ceiling that does not allow islands to go out the top of the map
        if(location.posY + radius*2 <= height) {
            HashSet<WorldXYZ> structure = attachedStructureShape(getPlayer(), sphere);
            WorldXYZ destination = new WorldXYZ(location.posX, height, location.posZ); // scan up, starting at target height
            moveStructureAndPlayer(getPlayer(), destination, structure);//scan UP, 0 buffer room
        } else {
            aetherSay(getPlayer(), "Radius: " + radius +
                    ". There's not enough room left to bounce. This island can be moved with an FTP, provided there is enough room under build height at the destination.");
        }
    }
    
    @Override
    public LinkedHashSet<WorldXYZ> fullStructure() {
        if(sphere == null)
            sphere = Util_SphericalFunctions.getSphere(location, radius);
        return sphere;
    }
    
    @Override
    /** This override is necessary to invalidate the buffered sphere variable whenever it is moved**/
    public void moveYourLocation(WorldXYZ destination) {
        sphere = null;
        location = destination.copyWithNewFacing(location.face); //preserve old facing
    }
    
    @Override
    public LinkedHashSet<WorldXYZ> runeBlocks(WorldXYZ coords) {
        LinkedHashSet<WorldXYZ> st = new LinkedHashSet<>();
        st.add(location);
        return st;
    }
    
    @Override
    public int authority() {  
        return radius;
    }
    
    @Override
    public ArrayList<PersistentRune> getActiveMagic() {
        return activeFaithList;
    }

    @Override
    public boolean oneRunePerPerson() {
        return false;
    }
    @Override
    public boolean isFlatRuneOnly(){
        return true;
    }	

}
