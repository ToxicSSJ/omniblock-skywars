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

import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.games.solo.SoloSkywars;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;
import net.omniblock.skywars.games.teams.TeamSkywars;
import net.omniblock.skywars.games.teams.managers.TeamPlayerManager;
import net.omniblock.skywars.patch.managers.MapManager;
import net.omniblock.skywars.patch.types.SkywarsType;
import omniblock.on.network.packet.Packet;
import omniblock.on.network.packet.assembler.AssemblyType;
import omniblock.on.network.packet.modifier.PacketModifier;

public class FullAttributeListener implements Listener {
	
	@EventHandler
	public void onLogin(PlayerLoginEvent e){
		
		if(Skywars.currentMatchType == null || Skywars.currentMatchType == SkywarsType.NONE) {
			
			Packet.ASSEMBLER.sendPacket(AssemblyType.GAME_SEND_PLAYERS_STATUS_INFO,
					new PacketModifier()
					.addInteger(0)
					.addInteger(Bukkit.getMaxPlayers())
					.addString("####"));
			
			if(Bukkit.getOnlinePlayers().size() >= Bukkit.getMaxPlayers()) {
				
				Packet.ASSEMBLER.sendPacket(AssemblyType.SERVER_SEND_FULL_ATTRIBUTE, new PacketModifier());
				
			}
			return;
			
		}
		
		if(Skywars.currentMatchType == SkywarsType.SW_INSANE_SOLO ||
				Skywars.currentMatchType == SkywarsType.SW_NORMAL_SOLO ||
				Skywars.currentMatchType == SkywarsType.SW_Z_SOLO) {
				
			Packet.ASSEMBLER.sendPacket(AssemblyType.GAME_SEND_PLAYERS_STATUS_INFO,
					new PacketModifier()
					.addInteger(SoloPlayerManager.getPlayersInLobbyAmount())
					.addInteger(SoloSkywars.MAX_PLAYERS)
					.addString(Skywars.currentMatchType == SkywarsType.SW_Z_SOLO ? MapManager.NEW_MAP_Z.getName() : MapManager.NEW_MAP_NORMAL.getName()));
			
			if(Bukkit.getOnlinePlayers().size() >= SoloSkywars.MAX_PLAYERS) {
				
				Packet.ASSEMBLER.sendPacket(AssemblyType.SERVER_SEND_FULL_ATTRIBUTE, new PacketModifier());
				
			}
			return;
			
		} else {
			
			Packet.ASSEMBLER.sendPacket(AssemblyType.GAME_SEND_PLAYERS_STATUS_INFO,
					new PacketModifier()
					.addInteger(TeamPlayerManager.getPlayersInLobbyAmount())
					.addInteger(TeamSkywars.MAX_PLAYERS)
					.addString(Skywars.currentMatchType == SkywarsType.SW_Z_TEAMS ? MapManager.NEW_MAP_Z.getName() : MapManager.NEW_MAP_NORMAL.getName()));
			
			if(Bukkit.getOnlinePlayers().size() >= TeamSkywars.MAX_PLAYERS) {
				
				Packet.ASSEMBLER.sendPacket(AssemblyType.SERVER_SEND_FULL_ATTRIBUTE, new PacketModifier());
				
			}
			return;
			
		}
		
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		
		if(Skywars.currentMatchType == null || Skywars.currentMatchType == SkywarsType.NONE) {
			
			Packet.ASSEMBLER.sendPacket(AssemblyType.GAME_SEND_PLAYERS_STATUS_INFO,
					new PacketModifier()
					.addInteger(0)
					.addInteger(Bukkit.getMaxPlayers())
					.addString("####"));
			
			if(Bukkit.getOnlinePlayers().size() < Bukkit.getMaxPlayers()) {
				
				Packet.ASSEMBLER.sendPacket(AssemblyType.SERVER_SEND_UNFULL_ATTRIBUTE, new PacketModifier());
				
			}
			return;
			
		}
		
		if(Skywars.currentMatchType == SkywarsType.SW_INSANE_SOLO ||
				Skywars.currentMatchType == SkywarsType.SW_NORMAL_SOLO ||
				Skywars.currentMatchType == SkywarsType.SW_Z_SOLO) {
				
			Packet.ASSEMBLER.sendPacket(AssemblyType.GAME_SEND_PLAYERS_STATUS_INFO,
					new PacketModifier()
					.addInteger(SoloPlayerManager.getPlayersInLobbyAmount())
					.addInteger(SoloSkywars.MAX_PLAYERS)
					.addString(Skywars.currentMatchType == SkywarsType.SW_Z_SOLO ? MapManager.NEW_MAP_Z.getName() : MapManager.NEW_MAP_NORMAL.getName()));
			
			if(Bukkit.getOnlinePlayers().size() < SoloSkywars.MAX_PLAYERS) {
				
				Packet.ASSEMBLER.sendPacket(AssemblyType.SERVER_SEND_UNFULL_ATTRIBUTE, new PacketModifier());
				
			}
			return;
			
		} else {
			
			Packet.ASSEMBLER.sendPacket(AssemblyType.GAME_SEND_PLAYERS_STATUS_INFO,
					new PacketModifier()
					.addInteger(TeamPlayerManager.getPlayersInLobbyAmount())
					.addInteger(TeamSkywars.MAX_PLAYERS)
					.addString(Skywars.currentMatchType == SkywarsType.SW_Z_TEAMS ? MapManager.NEW_MAP_Z.getName() : MapManager.NEW_MAP_NORMAL.getName()));
			
			if(Bukkit.getOnlinePlayers().size() < TeamSkywars.MAX_PLAYERS) {
				
				Packet.ASSEMBLER.sendPacket(AssemblyType.SERVER_SEND_UNFULL_ATTRIBUTE, new PacketModifier());
				
			}
			return;
			
		}
		
	}
	
}
