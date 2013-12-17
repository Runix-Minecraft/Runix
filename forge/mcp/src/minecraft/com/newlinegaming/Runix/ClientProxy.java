package com.newlinegaming.Runix;

import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;

public class ClientProxy extends CommonProxy {
	
	public void registerRenderInformation() {
		
	}

    public void aetherSay(String msg) {
        /** TODO: The double messages can be fixed by using a proper Proxy
         * (client side only, I think) See: 
         * https://github.com/denoflionsx/GateCopy/blob/master/src/denoflionsx/GateCopy/Proxy/ProxyClient.java
         * Runix.clientSide?
         */
        Minecraft.getMinecraft().thePlayer.addChatMessage(msg);
    }
    
}
