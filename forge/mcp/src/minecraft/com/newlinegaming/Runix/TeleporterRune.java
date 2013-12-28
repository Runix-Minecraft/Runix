package com.newlinegaming.Runix;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;

public class TeleporterRune extends AbstractRune {
    

	public int[][][] blockPattern(){
		return new int[][][]
				{{{NONE,TIER,SIGR,TIER,NONE},
				  {TIER,TIER,TIER,TIER,TIER},
				  {SIGR,TIER,KEY ,TIER,SIGR},
				  {TIER,TIER,TIER,TIER,TIER},
				  {NONE,TIER,SIGR,TIER,NONE}}};
	}
	
	@Override
	/**Teleport the player to the WaypointRune with a matching signature
	 */
	public void execute(EntityPlayer player, WorldXYZ coords) {
	    accept(player);
//	    if(Tiers.getTier(coords.getBlockId()) > 0){
//	        consumeKeyBlock(coords...)
//	    }
	    // if coords.equals(location)
	    //     execute as normal
	    // else
	    //     find matching rune or construct new rune and pass off execution
	    
	    Signature signature = new Signature(this, coords);
	    WorldXYZ destination;
	    //This is necessary because getActiveMagic() CANNOT be static, so it returns a pointer to a static field...
	    ArrayList<PersistentRune> waypointList = (new WaypointRune().getActiveMagic());
	    
	    PersistentRune wp = null;
	    for( PersistentRune candidate : waypointList){
	        if( new Signature(candidate, candidate.location).equals( signature ) ){
	            wp = candidate;
	            break;
	        }
	    }
	    if( wp == null){
	        aetherSay(player, "A waypoint with that signature cannot be found.");
	        return;
	    }
	    destination = new WorldXYZ(wp.location);
		safelyMovePlayer(player, destination);
	}

    public String getRuneName() {
        return "Teleporter";
	}

}
