package net.minecraft.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;

public class CommandSetPlayerTimeout extends CommandBase {

   public String func_71517_b() {
      return "setidletimeout";
   }

   public int func_82362_a() {
      return 3;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.setidletimeout.usage";
   }

   public void func_71515_b(ICommandSender p_71515_1_, String[] p_71515_2_) {
      if(p_71515_2_.length == 1) {
         int var3 = func_71528_a(p_71515_1_, p_71515_2_[0], 0);
         MinecraftServer.func_71276_C().func_143006_e(var3);
         func_71522_a(p_71515_1_, "commands.setidletimeout.success", new Object[]{Integer.valueOf(var3)});
      } else {
         throw new WrongUsageException("commands.setidletimeout.usage", new Object[0]);
      }
   }
}
