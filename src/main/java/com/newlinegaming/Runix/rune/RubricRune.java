package com.newlinegaming.Runix.rune;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

import com.newlinegaming.Runix.BlockRecord;
import com.newlinegaming.Runix.PersistentRune;
import com.newlinegaming.Runix.SigBlock;
import com.newlinegaming.Runix.Vector3;
import com.newlinegaming.Runix.WorldXYZ;
import com.newlinegaming.Runix.helper.RenderHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class RubricRune extends PersistentRune {

	private static ArrayList<PersistentRune> storedPatterns = new ArrayList<PersistentRune>();
	public ArrayList<BlockRecord> structure = new ArrayList<BlockRecord>();
	protected transient RenderHelper renderer = null;

    public RubricRune() {
        runeName = "Rubric";
        usesConductance = true;
    }

    public RubricRune(WorldXYZ coords, EntityPlayer player2) 
    {
	    super(coords, player2,"Rubric");
	    usesConductance = true;
	}
    
    protected void initializeRune(){
        renderer = new RenderHelper();
        MinecraftForge.EVENT_BUS.register(this);
    }

    private ArrayList<BlockRecord> scanStructure(HashSet<WorldXYZ> shape) {
        ArrayList<BlockRecord> fullData = new ArrayList<BlockRecord>();
        for(WorldXYZ point : shape){
            if(point.getBlock() != Blocks.air){
                Vector3 offset = new Vector3(location, point);
                fullData.add(new BlockRecord(1, offset, point.getSigBlock()));
            }
        }
        return fullData;
    }

    @Override
	public Block[][][] runicTemplateOriginal() {
		return new Block[][][] {{
			{ NONE,TIER,NONE,TIER,NONE },
			{ TIER,TIER,NONE,TIER,TIER },
			{ NONE,NONE,FUEL ,NONE,NONE },
			{ TIER,TIER,NONE,TIER,TIER },
			{ NONE,TIER,NONE,TIER,NONE }
			
		}};
	}


	@Override
	protected void poke(EntityPlayer poker, WorldXYZ coords){
	    if( renderer == null)
	        initializeRune();
		renderer.reset();
		HashSet<WorldXYZ> shape = attachedStructureShape(poker); 
		structure = scanStructure(shape);
		if(structure.isEmpty()){
		    aetherSay(poker, "The rune is touching something that is larger than "+getTier()+" blocks across.");
		    getActiveMagic().remove(this);//move into kill()?
		    kill();
		    return;
		}
		if(getWrittenBookName(poker) != null) {
		    instanceName = getWrittenBookName(poker);
            aetherSay(poker, "This structure is now called "+ instanceName);
            
			consumeRune(location);// remove the rune itself add runic energy
			structure = scanStructure(shape);// then capture everything else into the rubric file 
			consumeRune(extractCoordinates(structure));// delete the old structure
        } else {
            aetherSay(poker, "Activate Rubric with a named book to assign your structure a name.");
            getActiveMagic().remove(this); //the rune still persists for rendering, but it can't be found or saved
        }
		
	}
	
	private String getWrittenBookName(EntityPlayer poker) {
	    ItemStack toolused = poker.getCurrentEquippedItem();
        if (toolused != null && toolused.getItem() == Items.written_book) {
            return toolused.getDisplayName();
        }
        return null;
    }

    @Override
	/**This is overridden to give Rubric increased range when picking up large structures*/
    public int getTier() {
        return super.getTier()*3;
    }

    private Collection<WorldXYZ> extractCoordinates(Collection<BlockRecord> structureRecord) {
	    ArrayList<WorldXYZ> blocks = new ArrayList<WorldXYZ>();
	    for( BlockRecord record : structureRecord )
	        blocks.add(location.offset(record.offset));
        return blocks;
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
	public void renderWireframe(RenderWorldLastEvent evt) {
		if (getPlayer() != null)
			renderer.highlightBoxes(extractCoordinates(structure), false, getPlayer(), 221, 0, 0);//TODO this is really slow for every frame
	}


    @SubscribeEvent
    public void bookClickEvent(PlayerInteractEvent event) {
        if (event.action == Action.RIGHT_CLICK_BLOCK 
                && getWrittenBookName(event.entityPlayer) != null) {
            EntityPlayer poker = event.entityPlayer;
            WorldXYZ coords = new WorldXYZ(event.entity.worldObj, event.x, event.y, event.z);
            
            if(getWrittenBookName(event.entityPlayer).equals(instanceName)) {
                //          try {
                unpackStructure(poker, structure, coords);
                //          } catch (NotEnoughRunicEnergyException e) {
                //              reportOutOfGas(poker);
                //ensure recall is placed back 
                //          }
                //TODO fix the energy requirements
                //consume Rune for energy
                //transfer energy to Rubric rune
                    //if not enough energy, Rubric can keep the energy, just ask for more
            }
        }
    }

    public void unpackStructure(EntityPlayer initiator, ArrayList<BlockRecord> structure, WorldXYZ origin){
        //convert old coordinets to vector3 based on offset from origin
        // create new worldXYZ by adding this.location to each vector3 
        HashMap<WorldXYZ, SigBlock> NewStructure = new HashMap<WorldXYZ, SigBlock>();
        for(BlockRecord record : structure){
            NewStructure.put(location.offset(record.offset), record.block);
        }
            
        //try{
        //for structure
        
        // for(WorldXYZ point : structure.keySet()){}
        stampBlockPattern(NewStructure, initiator);
            //setBlockID(
        //TODO validate area to stamp
        //catch: need more energy
    }
    
    
    @Override
    public ArrayList<PersistentRune> getActiveMagic() {
        return storedPatterns;
    }

    @Override
    public boolean oneRunePerPerson() {
        return false;
    }
    
    public boolean isFlatRuneOnly() {
        return false;
    }
}
