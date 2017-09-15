package com.newlinegaming.Runix.proxys;

import com.newlinegaming.Runix.api.tier.ITier;
import com.newlinegaming.Runix.apiimpl.API;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class CommonProxy {

    private HashMap<Block, ITier> tiers = new HashMap<>();
    private ArrayList<Block> naturalBlocks;
    private ArrayList<Block> moveSenBlocks;
    private ArrayList<Block> crushableBlocks;

    public void preInit(FMLPreInitializationEvent e) {
        API.bind(e.getAsmData());
    }

    public void init(FMLInitializationEvent e) {

    }

    public void postInit(FMLPostInitializationEvent e) {

    }

    private void registerTeirs() {



        tiers.forEach((b, t) -> API.INSTANCE().registerTier(b,t));
    }

//    public void registerRenderInformation() {} //NO-OP
    
//    public void registerTileEnitiy() {
//        GameRegistry.registerTileEntity(TileLightBeam.class, "TileLightBeam");
//    }
}
