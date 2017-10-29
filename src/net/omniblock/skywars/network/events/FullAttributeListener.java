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

import net.omniblock.packets.network.Packets;
import net.omniblock.packets.network.structure.packet.GameOnlineInfoPacket;
import net.omniblock.packets.network.structure.type.PacketSenderType;
import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.games.solo.SoloSkywars;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;
import net.omniblock.skywars.games.teams.TeamSkywars;
import net.omniblock.skywars.games.teams.managers.TeamPlayerManager;
import net.omniblock.skywars.patch.managers.MapManager;
import net.omniblock.skywars.patch.types.SkywarsType;

public class FullAttributeListener implements Listener {

	@EventHandler
	public void onLogin(PlayerLoginEvent e) {

		if (Skywars.currentMatchType == null || Skywars.currentMatchType == SkywarsType.NONE) {

			Packets.STREAMER.streamPacket(new GameOnlineInfoPacket()

					.setServername(Bukkit.getServerName()).setMapname("####").setMaximiumPlayers(Bukkit.getMaxPlayers())
					.setOnlinePlayers(0).setOpened(true)

					.build().setReceiver(PacketSenderType.OMNICORE));
			return;

		}

		if (Skywars.currentMatchType == SkywarsType.SW_INSANE_SOLO
				|| Skywars.currentMatchType == SkywarsType.SW_NORMAL_SOLO
				|| Skywars.currentMatchType == SkywarsType.SW_Z_SOLO) {

			Packets.STREAMER.streamPacket(new GameOnlineInfoPacket()

					.setServername(Bukkit.getServerName())
					.setMapname(Skywars.currentMatchType == SkywarsType.SW_Z_SOLO ? MapManager.NEW_MAP_Z.getName()
							: MapManager.NEW_MAP_NORMAL.getName())
					.setMaximiumPlayers(SoloSkywars.MAX_PLAYERS)
					.setOnlinePlayers(SoloPlayerManager.getPlayersInLobbyAmount()).setOpened(Skywars.ingame == true
							? false : Skywars.inpregame == true ? false : Skywars.inend == true ? false : true)

					.build().setReceiver(PacketSenderType.OMNICORE));
			return;

		} else {

			Packets.STREAMER.streamPacket(new GameOnlineInfoPacket()

					.setServername(Bukkit.getServerName())
					.setMapname(Skywars.currentMatchType == SkywarsType.SW_Z_TEAMS ? MapManager.NEW_MAP_Z.getName()
							: MapManager.NEW_MAP_NORMAL.getName())
					.setMaximiumPlayers(TeamSkywars.MAX_PLAYERS)
					.setOnlinePlayers(TeamPlayerManager.getPlayersInLobbyAmount()).setOpened(Skywars.ingame == true
							? false : Skywars.inpregame == true ? false : Skywars.inend == true ? false : true)

					.build().setReceiver(PacketSenderType.OMNICORE));
			return;

		}

	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {

		if (Skywars.currentMatchType == null || Skywars.currentMatchType == SkywarsType.NONE) {

			Packets.STREAMER.streamPacket(new GameOnlineInfoPacket()

					.setServername(Bukkit.getServerName()).setMapname("####").setMaximiumPlayers(Bukkit.getMaxPlayers())
					.setOnlinePlayers(0).setOpened(true)

					.build().setReceiver(PacketSenderType.OMNICORE));
			return;

		}

		if (Skywars.currentMatchType == SkywarsType.SW_INSANE_SOLO
				|| Skywars.currentMatchType == SkywarsType.SW_NORMAL_SOLO
				|| Skywars.currentMatchType == SkywarsType.SW_Z_SOLO) {

			Packets.STREAMER.streamPacket(new GameOnlineInfoPacket()

					.setServername(Bukkit.getServerName())
					.setMapname(Skywars.currentMatchType == SkywarsType.SW_Z_SOLO ? MapManager.NEW_MAP_Z.getName()
							: MapManager.NEW_MAP_NORMAL.getName())
					.setMaximiumPlayers(SoloSkywars.MAX_PLAYERS)
					.setOnlinePlayers(SoloPlayerManager.getPlayersInLobbyAmount()).setOpened(Skywars.ingame == true
							? false : Skywars.inpregame == true ? false : Skywars.inend == true ? false : true)

					.build().setReceiver(PacketSenderType.OMNICORE));
			return;

		} else {

			Packets.STREAMER.streamPacket(new GameOnlineInfoPacket()

					.setServername(Bukkit.getServerName())
					.setMapname(Skywars.currentMatchType == SkywarsType.SW_Z_TEAMS ? MapManager.NEW_MAP_Z.getName()
							: MapManager.NEW_MAP_NORMAL.getName())
					.setMaximiumPlayers(TeamSkywars.MAX_PLAYERS)
					.setOnlinePlayers(TeamPlayerManager.getPlayersInLobbyAmount()).setOpened(Skywars.ingame == true
							? false : Skywars.inpregame == true ? false : Skywars.inend == true ? false : true)

					.build().setReceiver(PacketSenderType.OMNICORE));
			return;

		}

	}

}
