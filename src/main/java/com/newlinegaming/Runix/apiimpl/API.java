package com.newlinegaming.Runix.apiimpl;

import com.newlinegaming.Runix.api.BindRunixAPI;
import com.newlinegaming.Runix.api.IRunixAPI;
import com.newlinegaming.Runix.api.tier.ITier;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Set;

public class API implements IRunixAPI {

    private static final IRunixAPI INSTANCE = new API();

    @NotNull
    private HashMap<Block, ITier> teirsReg = new HashMap<>();


    @NotNull
    public static IRunixAPI INSTANCE() {
        return INSTANCE;
    }

    public static void bind(@NotNull ASMDataTable table) {

        String annoName = BindRunixAPI.class.getCanonicalName();

        Set<ASMDataTable.ASMData> dataSet = table.getAll(annoName);
        //Use a proper logger
        System.out.println("Found " + dataSet.size() + " RunixAPI candidates");

        //gota love lambdas
        dataSet.forEach(ds -> {
            try {
                Class<?> clazz = Class.forName(ds.getClassName());
                Field field = clazz.getField(ds.getObjectName());

                if (field.getType() == IRunixAPI.class) {
                    field.set(null, INSTANCE);
                }

            } catch (@NotNull ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
                throw  new RuntimeException("Failed to bind: " + ds.getClassName() + ds.getObjectName(), e);
            }
        });
    }

    @Override
    public void registerTier(Block block, ITier tier) {
        teirsReg.put(block, tier);
    }

    /**
     * gets the tier object from the registry
     *
     * @param block
     * @return
     */
    @Override
    public ITier getTier(Block block) {
        return teirsReg.get(block);
    }

}
