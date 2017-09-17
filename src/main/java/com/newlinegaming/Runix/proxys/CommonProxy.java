package com.newlinegaming.Runix.proxys;

import com.newlinegaming.Runix.Tiers;
import com.newlinegaming.Runix.api.tier.ITier;
import com.newlinegaming.Runix.apiimpl.tier.Tier;
import com.newlinegaming.Runix.apiimpl.API;
import com.newlinegaming.Runix.handlers.RuneHandler;
import com.newlinegaming.Runix.helper.TierHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class CommonProxy {

    private ArrayList<Block> naturalBlocks;
    private ArrayList<Block> moveSensitiveBlocks;
    private ArrayList<Block> crushableBlocks;
    private HashMap<Block, ITier> energyRegistry = new HashMap<>();

    public CommonProxy() {
        Tiers tier = new Tiers();
    }

    public void preInit(FMLPreInitializationEvent e) {
        API.bind(e.getAsmData());
//        System.out.println(API.INSTANCE().getTier(Blocks.DRAGON_EGG));
//        System.out.print(TierHelper.getTierNumber(Blocks.DRAGON_EGG));

    }

    public void init(FMLInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(RuneHandler.getInstance());
    }

    public void postInit(FMLPostInitializationEvent e) {
//        System.out.println(API.INSTANCE().getTier(Blocks.HAY_BLOCK));
//        System.out.print(TierHelper.getTierNumber(Blocks.DRAGON_EGG));
    }
}
