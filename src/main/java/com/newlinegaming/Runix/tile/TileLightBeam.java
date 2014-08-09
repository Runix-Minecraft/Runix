package com.newlinegaming.Runix.tile;


import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileLightBeam extends TileEntity {
    
    public TileLightBeam() {}
    
    @Override
    public void updateEntity() {//TODO: work on pushing entities
        super.updateEntity();
        @SuppressWarnings({ "unused", "unchecked" })
        List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class, getEntityBounds());
        
        for(Entity entity : entities ) {
            if(entity instanceof EntityItem) {
                entity.motionY *= 1.5;
            }
        }
        
    }
    
    private AxisAlignedBB getEntityBounds() {
        AxisAlignedBB bounds = AxisAlignedBB.getBoundingBox(xCoord, yCoord - 1, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
        return bounds; 
    }

}
