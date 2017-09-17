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

import com.newlinegaming.Runix.BlockRecord;
import com.newlinegaming.Runix.PersistentRune;
import com.newlinegaming.Runix.SigBlock;
import com.newlinegaming.Runix.Vector3;
import com.newlinegaming.Runix.WorldXYZ;
import com.newlinegaming.Runix.helper.RenderHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class RubricRune extends PersistentRune {

	private static final ArrayList<PersistentRune> storedPatterns = new ArrayList<>();
	private HashMap<Vector3, SigBlock> structure = new HashMap<>();
	private transient RenderHelper renderer = null;

    public RubricRune() {
        runeName = "Rubric";
        usesConductance = true;
    }

    public RubricRune(WorldXYZ coords, EntityPlayer player2) 
    {
	    super(coords, player2,"Rubric");
	    usesConductance = true;
    }
    
    private void initializeRune(){
        renderer = new RenderHelper();
        MinecraftForge.EVENT_BUS.register(this);
    }

	@Override
	protected void poke(EntityPlayer poker, WorldXYZ coords){
	    if( renderer == null)
	        initializeRune();
		renderer.reset();
		HashSet<WorldXYZ> shape = attachedStructureShape(poker);
		shape.removeAll(runeBlocks(location)); //we don't want to include the rune in the pattern
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
        } else {
            aetherSay(poker, "Activate Rubric with a named book to assign your structure a name.");
            getActiveMagic().remove(this); //the rune still persists for rendering, but it can't be found or saved
        }
		
	} 
	
	@SideOnly(Side.CLIENT)
    @SubscribeEvent
	public void renderWireframe(RenderWorldLastEvent evt) {
		if (getPlayer() != null)
			renderer.highlightBoxes(structureAbsoluteLocation(location).keySet(), false, getPlayer(), 221, 0, 0);//TODO this is really slow for every frame
	}


//    @SubscribeEvent
//    public void bookClickEvent(PlayerInteractEvent event) {
//        String writtenBookName = getWrittenBookName(event.entityPlayer);
//        if (event.action == Action.RIGHT_CLICK_BLOCK && writtenBookName != null) {
//            EntityPlayer poker = event.entityPlayer;
//            WorldXYZ coords = new WorldXYZ(event.entity.worldObj, event.x, event.y, event.z);
//
//            if(writtenBookName.equals(instanceName)) {
//                event.setCanceled(true);
//                //          try {
//                unpackStructure(poker, coords);
//                //          } catch (NotEnoughRunicEnergyException e) {
//                //              reportOutOfGas(poker);
//                //ensure recall is placed back
//                //          }
//                //TODO fix the energy requirements
//                //consume Rune for energy
//                //transfer energy to Rubric rune
//                    //if not enough energy, Rubric can keep the energy, just ask for more
//            }
//        }
//    }

    @SubscribeEvent
    public void bookClickEvent(PlayerInteractEvent.RightClickBlock e) {
	    String writtenBookName = getWrittenBookName(e.getEntityPlayer());

	    if (!e.getWorld().isRemote && writtenBookName != null) {
	        EntityPlayer player = e.getEntityPlayer();
	        WorldXYZ coords = new WorldXYZ(e.getWorld(), e.getPos());

	        if(writtenBookName.equals(instanceName)) {
//                event.setCanceled(true);
//                          try {
//                unpackStructure(poker, coords);
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

    private void unpackStructure(EntityPlayer initiator, WorldXYZ origin){
        //convert old coordinets to vector3 based on offset from origin
        // create new worldXYZ by adding this.location to each vector3 
        HashMap<WorldXYZ, SigBlock> NewStructure = structureAbsoluteLocation(origin);
            
        stampBlockPattern(NewStructure, initiator);
        //TODO validate area to stamp
        //catch: need more energy
    }
    
    private HashMap<Vector3, SigBlock> scanStructure(HashSet<WorldXYZ> shape) {
        HashMap<Vector3, SigBlock> fullData = new HashMap<>();
        for(WorldXYZ point : shape){
            if(point.getBlock() != Blocks.AIR){
                Vector3 offset = new Vector3(location, point);
                fullData.put(offset, point.getSigBlock());
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
	
	private String getWrittenBookName(EntityPlayer poker) {
	    ItemStack toolused = poker.getHeldItemMainhand();
        if (toolused != null && toolused.getItem() == Items.WRITTEN_BOOK) {
            return toolused.getDisplayName();
        }
        return null;
    }

    /** This is overridden to give Rubric increased range when picking up large structures
     * @return conductance range
     */
    @Override
    public int getTier() {
        return super.getTier()*3;
    }

    private Collection<WorldXYZ> extractCoordinates(Collection<BlockRecord> structureRecord) {
	    ArrayList<WorldXYZ> blocks = new ArrayList<>();
	    for( BlockRecord record : structureRecord )
	        blocks.add(location.offset(record.offset));
        return blocks;
    }
   

    private HashMap<WorldXYZ, SigBlock> structureAbsoluteLocation(WorldXYZ origin) {
        HashMap<WorldXYZ, SigBlock> NewStructure = new HashMap<>();
        for(Vector3 relative : structure.keySet()){
            NewStructure.put(origin.offset(relative), structure.get(relative));
        }
        return NewStructure;
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
