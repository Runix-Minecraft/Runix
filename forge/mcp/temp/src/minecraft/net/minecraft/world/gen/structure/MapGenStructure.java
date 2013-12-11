package net.minecraft.world.gen.structure;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ReportedException;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.structure.CallableChunkPosHash;
import net.minecraft.world.gen.structure.CallableIsFeatureChunk;
import net.minecraft.world.gen.structure.CallableStructureType;
import net.minecraft.world.gen.structure.MapGenStructureData;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;

public abstract class MapGenStructure extends MapGenBase {

   private MapGenStructureData field_143029_e;
   protected Map field_75053_d = new HashMap();


   public abstract String func_143025_a();

   protected final void func_75037_a(World p_75037_1_, int p_75037_2_, int p_75037_3_, int p_75037_4_, int p_75037_5_, byte[] p_75037_6_) {
      this.func_143027_a(p_75037_1_);
      if(!this.field_75053_d.containsKey(Long.valueOf(ChunkCoordIntPair.func_77272_a(p_75037_2_, p_75037_3_)))) {
         this.field_75038_b.nextInt();

         try {
            if(this.func_75047_a(p_75037_2_, p_75037_3_)) {
               StructureStart var7 = this.func_75049_b(p_75037_2_, p_75037_3_);
               this.field_75053_d.put(Long.valueOf(ChunkCoordIntPair.func_77272_a(p_75037_2_, p_75037_3_)), var7);
               this.func_143026_a(p_75037_2_, p_75037_3_, var7);
            }

         } catch (Throwable var10) {
            CrashReport var8 = CrashReport.func_85055_a(var10, "Exception preparing structure feature");
            CrashReportCategory var9 = var8.func_85058_a("Feature being prepared");
            var9.func_71500_a("Is feature chunk", new CallableIsFeatureChunk(this, p_75037_2_, p_75037_3_));
            var9.func_71507_a("Chunk location", String.format("%d,%d", new Object[]{Integer.valueOf(p_75037_2_), Integer.valueOf(p_75037_3_)}));
            var9.func_71500_a("Chunk pos hash", new CallableChunkPosHash(this, p_75037_2_, p_75037_3_));
            var9.func_71500_a("Structure type", new CallableStructureType(this));
            throw new ReportedException(var8);
         }
      }
   }

   public boolean func_75051_a(World p_75051_1_, Random p_75051_2_, int p_75051_3_, int p_75051_4_) {
      this.func_143027_a(p_75051_1_);
      int var5 = (p_75051_3_ << 4) + 8;
      int var6 = (p_75051_4_ << 4) + 8;
      boolean var7 = false;
      Iterator var8 = this.field_75053_d.values().iterator();

      while(var8.hasNext()) {
         StructureStart var9 = (StructureStart)var8.next();
         if(var9.func_75069_d() && var9.func_75071_a().func_78885_a(var5, var6, var5 + 15, var6 + 15)) {
            var9.func_75068_a(p_75051_1_, p_75051_2_, new StructureBoundingBox(var5, var6, var5 + 15, var6 + 15));
            var7 = true;
            this.func_143026_a(var9.func_143019_e(), var9.func_143018_f(), var9);
         }
      }

      return var7;
   }

   public boolean func_75048_a(int p_75048_1_, int p_75048_2_, int p_75048_3_) {
      this.func_143027_a(this.field_75039_c);
      return this.func_143028_c(p_75048_1_, p_75048_2_, p_75048_3_) != null;
   }

   protected StructureStart func_143028_c(int p_143028_1_, int p_143028_2_, int p_143028_3_) {
      Iterator var4 = this.field_75053_d.values().iterator();

      while(var4.hasNext()) {
         StructureStart var5 = (StructureStart)var4.next();
         if(var5.func_75069_d() && var5.func_75071_a().func_78885_a(p_143028_1_, p_143028_3_, p_143028_1_, p_143028_3_)) {
            Iterator var6 = var5.func_75073_b().iterator();

            while(var6.hasNext()) {
               StructureComponent var7 = (StructureComponent)var6.next();
               if(var7.func_74874_b().func_78890_b(p_143028_1_, p_143028_2_, p_143028_3_)) {
                  return var5;
               }
            }
         }
      }

      return null;
   }

   public boolean func_142038_b(int p_142038_1_, int p_142038_2_, int p_142038_3_) {
      this.func_143027_a(this.field_75039_c);
      Iterator var4 = this.field_75053_d.values().iterator();

      StructureStart var5;
      do {
         if(!var4.hasNext()) {
            return false;
         }

         var5 = (StructureStart)var4.next();
      } while(!var5.func_75069_d());

      return var5.func_75071_a().func_78885_a(p_142038_1_, p_142038_3_, p_142038_1_, p_142038_3_);
   }

   public ChunkPosition func_75050_a(World p_75050_1_, int p_75050_2_, int p_75050_3_, int p_75050_4_) {
      this.field_75039_c = p_75050_1_;
      this.func_143027_a(p_75050_1_);
      this.field_75038_b.setSeed(p_75050_1_.func_72905_C());
      long var5 = this.field_75038_b.nextLong();
      long var7 = this.field_75038_b.nextLong();
      long var9 = (long)(p_75050_2_ >> 4) * var5;
      long var11 = (long)(p_75050_4_ >> 4) * var7;
      this.field_75038_b.setSeed(var9 ^ var11 ^ p_75050_1_.func_72905_C());
      this.func_75037_a(p_75050_1_, p_75050_2_ >> 4, p_75050_4_ >> 4, 0, 0, (byte[])null);
      double var13 = Double.MAX_VALUE;
      ChunkPosition var15 = null;
      Iterator var16 = this.field_75053_d.values().iterator();

      ChunkPosition var19;
      int var21;
      int var20;
      double var23;
      int var22;
      while(var16.hasNext()) {
         StructureStart var17 = (StructureStart)var16.next();
         if(var17.func_75069_d()) {
            StructureComponent var18 = (StructureComponent)var17.func_75073_b().get(0);
            var19 = var18.func_74868_a();
            var20 = var19.field_76930_a - p_75050_2_;
            var21 = var19.field_76928_b - p_75050_3_;
            var22 = var19.field_76929_c - p_75050_4_;
            var23 = (double)(var20 * var20 + var21 * var21 + var22 * var22);
            if(var23 < var13) {
               var13 = var23;
               var15 = var19;
            }
         }
      }

      if(var15 != null) {
         return var15;
      } else {
         List var25 = this.func_75052_o_();
         if(var25 != null) {
            ChunkPosition var26 = null;
            Iterator var27 = var25.iterator();

            while(var27.hasNext()) {
               var19 = (ChunkPosition)var27.next();
               var20 = var19.field_76930_a - p_75050_2_;
               var21 = var19.field_76928_b - p_75050_3_;
               var22 = var19.field_76929_c - p_75050_4_;
               var23 = (double)(var20 * var20 + var21 * var21 + var22 * var22);
               if(var23 < var13) {
                  var13 = var23;
                  var26 = var19;
               }
            }

            return var26;
         } else {
            return null;
         }
      }
   }

   protected List func_75052_o_() {
      return null;
   }

   private void func_143027_a(World p_143027_1_) {
      if(this.field_143029_e == null) {
         this.field_143029_e = (MapGenStructureData)p_143027_1_.func_72943_a(MapGenStructureData.class, this.func_143025_a());
         if(this.field_143029_e == null) {
            this.field_143029_e = new MapGenStructureData(this.func_143025_a());
            p_143027_1_.func_72823_a(this.func_143025_a(), this.field_143029_e);
         } else {
            NBTTagCompound var2 = this.field_143029_e.func_143041_a();
            Iterator var3 = var2.func_74758_c().iterator();

            while(var3.hasNext()) {
               NBTBase var4 = (NBTBase)var3.next();
               if(var4.func_74732_a() == 10) {
                  NBTTagCompound var5 = (NBTTagCompound)var4;
                  if(var5.func_74764_b("ChunkX") && var5.func_74764_b("ChunkZ")) {
                     int var6 = var5.func_74762_e("ChunkX");
                     int var7 = var5.func_74762_e("ChunkZ");
                     StructureStart var8 = MapGenStructureIO.func_143035_a(var5, p_143027_1_);
                     this.field_75053_d.put(Long.valueOf(ChunkCoordIntPair.func_77272_a(var6, var7)), var8);
                  }
               }
            }
         }
      }

   }

   private void func_143026_a(int p_143026_1_, int p_143026_2_, StructureStart p_143026_3_) {
      this.field_143029_e.func_143043_a(p_143026_3_.func_143021_a(p_143026_1_, p_143026_2_), p_143026_1_, p_143026_2_);
      this.field_143029_e.func_76185_a();
   }

   protected abstract boolean func_75047_a(int var1, int var2);

   protected abstract StructureStart func_75049_b(int var1, int var2);
}
