package com.newlinegaming.Runix.handlers;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import net.minecraft.client.settings.KeyBinding;


class KeyHandler {
	
	@SubscribeEvent
	public void onKeyinput(InputEvent.KeyInputEvent event) {
		if(KeyBindings.CHARGE.isPressed()) {
			
		}
	}
	
	static class KeyBindings {
		
		static KeyBinding CHARGE;
		
		public void init() {
			
			CHARGE = new KeyBinding("key.transmode", Keyboard.KEY_C, "key.catergories.runix");
			
		}
		
	}

}
