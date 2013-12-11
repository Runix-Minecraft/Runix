package net.minecraft.world.gen.structure;

import java.util.Random;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

abstract class ComponentScatteredFeature extends StructureComponent {

   protected int field_74939_a;
   protected int field_74937_b;
   protected int field_74938_c;
   protected int field_74936_d = -1;


   public ComponentScatteredFeature() {}

   protected ComponentScatteredFeature(Random p_i2065_1_, int p_i2065_2_, int p_i2065_3_, int p_i2065_4_, int p_i2065_5_, int p_i2065_6_, int p_i2065_7_) {
      super(0);
      this.field_74939_a = p_i2065_5_;
      this.field_74937_b = p_i2065_6_;
      this.field_74938_c = p_i2065_7_;
      this.field_74885_f = p_i2065_1_.nextInt(4);
      switch(this.field_74885_f) {
      case 0:
      case 2:
         this.field_74887_e = new StructureBoundingBox(p_i2065_2_, p_i2065_3_, p_i2065_4_, p_i2065_2_ + p_i2065_5_ - 1, p_i2065_3_ + p_i2065_6_ - 1, p_i2065_4_ + p_i2065_7_ - 1);
         break;
      default:
         this.field_74887_e = new StructureBoundingBox(p_i2065_2_, p_i2065_3_, p_i2065_4_, p_i2065_2_ + p_i2065_7_ - 1, p_i2065_3_ + p_i2065_6_ - 1, p_i2065_4_ + p_i2065_5_ - 1);
      }

   }

   protected void func_143012_a(NBTTagCompound p_143012_1_) {
      p_143012_1_.func_74768_a("Width", this.field_74939_a);
      p_143012_1_.func_74768_a("Height", this.field_74937_b);
      p_143012_1_.func_74768_a("Depth", this.field_74938_c);
      p_143012_1_.func_74768_a("HPos", this.field_74936_d);
   }

   protected void func_143011_b(NBTTagCompound p_143011_1_) {
      this.field_74939_a = p_143011_1_.func_74762_e("Width");
      this.field_74937_b = p_143011_1_.func_74762_e("Height");
      this.field_74938_c = p_143011_1_.func_74762_e("Depth");
      this.field_74936_d = p_143011_1_.func_74762_e("HPos");
   }

   protected boolean func_74935_a(World p_74935_1_, StructureBoundingBox p_74935_2_, int p_74935_3_) {
      if(this.field_74936_d >= 0) {
         return true;
      } else {
         int var4 = 0;
         int var5 = 0;

         for(int var6 = this.field_74887_e.field_78896_c; var6 <= this.field_74887_e.field_78892_f; ++var6) {
            for(int var7 = this.field_74887_e.field_78897_a; var7 <= this.field_74887_e.field_78893_d; ++var7) {
               if(p_74935_2_.func_78890_b(var7, 64, var6)) {
                  var4 += Math.max(p_74935_1_.func_72825_h(var7, var6), p_74935_1_.field_73011_w.func_76557_i());
                  ++var5;
               }
            }
         }

         if(var5 == 0) {
            return false;
         } else {
            this.field_74936_d = var4 / var5;
            this.field_74887_e.func_78886_a(0, this.field_74936_d - this.field_74887_e.field_78895_b + p_74935_3_, 0);
            return true;
         }
      }
   }
}
