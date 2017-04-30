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

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import omniblock.on.network.packet.Packet;
import omniblock.on.network.packet.assembler.AssemblyType;
import omniblock.on.network.packet.modifier.PacketModifier;

public class FullAttributeListener implements Listener {
	
	@EventHandler
	public void onLogin(PlayerLoginEvent e){
		
		if(Bukkit.getOnlinePlayers().size() >= Bukkit.getMaxPlayers()) {
			Packet.ASSEMBLER.sendPacket(AssemblyType.SERVER_SEND_FULL_ATTRIBUTE, new PacketModifier());
		}
		
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		
		if(Bukkit.getOnlinePlayers().size() < Bukkit.getMaxPlayers()) {
			Packet.ASSEMBLER.sendPacket(AssemblyType.SERVER_SEND_UNFULL_ATTRIBUTE, new PacketModifier());
		}
		
	}
	
}
