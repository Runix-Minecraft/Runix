package com.newlinegaming.Runix.handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

/**
 * @author Anthony Anderson(LordIllyohs)
 *         File created on May, 19 2014
 */
public interface ITickHandler {

    @SubscribeEvent
    public void onWorldTickEvent(TickEvent.WorldTickEvent event);

    @SubscribeEvent
    public void onServerTickEvent(TickEvent.ServerTickEvent event);

    @SubscribeEvent
    public void onPlayerTickEvent(TickEvent.PlayerTickEvent event);

    @SubscribeEvent
    public void onClientTickEvent(TickEvent.ClientTickEvent event);
}
