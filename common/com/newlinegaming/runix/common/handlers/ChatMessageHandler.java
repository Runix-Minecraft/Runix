package com.newlinegaming.runix.common.handlers;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

import com.newlinegaming.runix.common.lib.LibRef;

public class ChatMessageHandler {
	
	private static final IChatComponent RCC = createSckChatComponent(LibRef.MOD_ID.toLowerCase());
	
	public static void iCommandSenderReply(ICommandSender player, String message) {
        sendChatToPlayer((EntityPlayer)player, message);
    }

    private static IChatComponent createSckChatComponent(String string) {
        ChatComponentText Component = new ChatComponentText(string);
          return Component;
    }

    public static IChatComponent createChatComponent(String message) {
        ChatComponentText component = new ChatComponentText(message);
        return RCC.appendSibling(component);
    }

    public static void sendChatToPlayer(EntityPlayer player, String message) {
    	player.addChatComponentMessage(createChatComponent(message));
    }

    public static void broadcastMessageToPlayers(String message){
        MinecraftServer.getServer().getConfigurationManager().sendChatMsg(createChatComponent(message));
    }

}
