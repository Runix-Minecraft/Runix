package com.newlinegaming.Runix.Runes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;

import com.newlinegaming.Runix.BlockRecord;
import com.newlinegaming.Runix.PersistentRune;
import com.newlinegaming.Runix.helper.RenderHelper;
import com.newlinegaming.Runix.Signature;
import com.newlinegaming.Runix.Vector3;
import com.newlinegaming.Runix.WorldXYZ;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class RubricCreationRune extends PersistentRune {

	private static ArrayList<PersistentRune> storedPatterns = new ArrayList<PersistentRune>();
	public Signature sig = null;
	public ArrayList<BlockRecord> structure = new ArrayList<BlockRecord>();
	protected transient RenderHelper renderer = null;

    public RubricCreationRune() {
        runeName = "Rubric Creator";
    }

    public RubricCreationRune(WorldXYZ coords, EntityPlayer player2) 
    {
	    super(coords, player2,"Rubric Creator");
	}
    
    protected void initializeRune(){
        renderer = new RenderHelper();
        MinecraftForge.EVENT_BUS.register(this);
    }

    private ArrayList<BlockRecord> scanStructure(HashSet<WorldXYZ> shape) {
        ArrayList<BlockRecord> fullData = new ArrayList<BlockRecord>();
        for(WorldXYZ point : shape){
            if(point.getBlockId() != 0){
                Vector3 offset = new Vector3(location, point);
                fullData.add(new BlockRecord(1, offset, point.getSigBlock()));
            }
        }
        return fullData;
    }

    @Override
	public int[][][] runicTemplateOriginal() {
		int RTCH = Block.torchRedstoneActive.blockID;
		return new int[][][] 
		      {{{ NONE,TIER,SIGR,TIER,NONE },
				{ TIER,TIER,RTCH,TIER,TIER },
				{ SIGR,RTCH,KEY ,RTCH,SIGR },
				{ TIER,TIER,RTCH,TIER,TIER },
				{ NONE,TIER,SIGR,TIER,NONE }}};
	}


	@Override
	protected void poke(EntityPlayer poker, WorldXYZ coords){
	    if( renderer == null)
	        initializeRune();
		renderer.reset();
		HashSet<WorldXYZ> shape = attachedStructureShape(poker); //TODO the problem is that this will be saved and persisted even without a signature
		structure = scanStructure(shape);
		if(structure.isEmpty()){
		    aetherSay(poker, "The rune is touching something that is larger than "+getTier()+" blocks across.");
		    getActiveMagic().remove(this);// delete this failed rune attempt so it doesn't get saved
		    return;
		}
		ItemStack toolused = poker.getCurrentEquippedItem();
		if (toolused != null && (toolused.itemID == Item.writtenBook.itemID || toolused.itemID == Item.book.itemID)) 
		{
            sig = new Signature(this, coords); // check signature while the rune still exists
			consumeRune(location);// remove the rune itself add runic energy
			structure = scanStructure(shape);// then capture everything else into the rubric file 
			consumeRune(extractCoordinates(structure));// delete the old structure

            if(toolused.itemID == Item.writtenBook.itemID){
    	        specialName = toolused.getDisplayName();
                aetherSay(poker, "the tool used is "+ specialName);
			}
        }
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
    @ForgeSubscribe
	public void renderWireframe(RenderWorldLastEvent evt) {
		if (getPlayer() != null)
			renderer.highlightBoxes(extractCoordinates(structure), false, getPlayer(), 221, 0, 0);//TODO this is really slow for every frame
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
    
    @Override
    public Signature getSignature(){
        return sig;
    }
}
