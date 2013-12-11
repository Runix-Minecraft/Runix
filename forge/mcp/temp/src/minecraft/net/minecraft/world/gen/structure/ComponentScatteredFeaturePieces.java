package net.minecraft.world.gen.structure;

import net.minecraft.world.gen.structure.ComponentScatteredFeatureDesertPyramid;
import net.minecraft.world.gen.structure.ComponentScatteredFeatureJunglePyramid;
import net.minecraft.world.gen.structure.ComponentScatteredFeatureSwampHut;
import net.minecraft.world.gen.structure.MapGenStructureIO;

public class ComponentScatteredFeaturePieces {

   public static void func_143045_a() {
      MapGenStructureIO.func_143031_a(ComponentScatteredFeatureDesertPyramid.class, "TeDP");
      MapGenStructureIO.func_143031_a(ComponentScatteredFeatureJunglePyramid.class, "TeJP");
      MapGenStructureIO.func_143031_a(ComponentScatteredFeatureSwampHut.class, "TeSH");
   }
}
