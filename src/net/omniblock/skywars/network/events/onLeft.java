/*
 *  Omniblock Developers Team - Copyright (C) 2016
 *
 *  This program is not a free software; you cannot redistribute it and/or modify it.
 *
 *  Only this enabled the editing and writing by the members of the team. 
 *  No third party is allowed to modification of the code.
 *
 */

package net.omniblock.skywars.network.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import net.omniblock.skywars.network.NetworkData;

public class onLeft implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		if(NetworkData.serverplayers.contains(e.getPlayer())){
			NetworkData.serverplayers.remove(e.getPlayer());
		}
	}
	
}
