/*
 *  Omniblock Developers Team - Copyright (C) 2016
 *
 *  This program is not a free software; you cannot redistribute it and/or modify it.
 *
 *  Only this enabled the editing and writing by the members of the team. 
 *  No third party is allowed to modification of the code.
 *
 */

package net.omniblock.skywars.patch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.omniblock.network.handlers.Handlers;
import net.omniblock.network.handlers.games.NetworkBroadcaster;
import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.games.teams.managers.TeamPlayerManager;
import net.omniblock.skywars.network.NetworkData;
import net.omniblock.skywars.network.NetworkRunnable;
import net.omniblock.skywars.network.events.FullAttributeListener;
import net.omniblock.skywars.network.events.SkywarsLobbyFilterListener;
import net.omniblock.skywars.patch.internal.Patcher;
import net.omniblock.skywars.patch.readers.GameReader;
import net.omniblock.skywars.patch.types.SkywarsType;

public class NetworkPatcher implements Patcher {

	public static NetworkRunnable networkrunnable;

	public void initialize() {

		GameReader.start();

		Skywars.getInstance().getServer().getPluginManager().registerEvents(new FullAttributeListener(),
				Skywars.getInstance());
		Skywars.getInstance().getServer().getPluginManager().registerEvents(new SkywarsLobbyFilterListener(),
				Skywars.getInstance());

		networkrunnable = new NetworkRunnable();
		networkrunnable.runTaskTimer(Skywars.getInstance(), 5L, 40L);

		NetworkData.date = Calendar.getInstance().getTime();

		NetworkData.broadcaster = new NetworkBroadcaster() {

			@Override
			public void execute(String data) {

				if (data.contains("$ TEAM ") && data.contains(",")) {

					if (Skywars.currentMatchType == SkywarsType.SW_NORMAL_TEAMS
							|| Skywars.currentMatchType == SkywarsType.SW_INSANE_TEAMS
							|| Skywars.currentMatchType == SkywarsType.SW_Z_TEAMS) {

						data = data.replaceFirst("$ TEAM ", "");
						List<Player> party = new ArrayList<Player>();

						Arrays.asList(data.split(",")).stream().forEach(player -> {

							if (Bukkit.getPlayer(player) != null) {
								party.add(Bukkit.getPlayer(player));
							}

						});

						TeamPlayerManager.addPreTeam(party);
						return;

					}
					return;

				} else if (data.contains("#")) {

					if (Skywars.currentMatchType == null)
						Skywars.currentMatchType = SkywarsType.NONE;
					if (Skywars.currentMatchType != SkywarsType.NONE)
						return;

					SkywarsType swtype = SkywarsType.NONE;

					String[] matchanddata = data.split("#");

					swtype = getTypeByBestResolver(matchanddata);

					if (swtype != SkywarsType.NONE) {

						swtype.makeMatch();

					}

				} else {
					Handlers.LOGGER.sendError("SKYWARS: No puedes ejecutar la partida sin alternación de cáracteres en "
							+ "la data. [ERR-NetworkPatcher:35]");
					return;
				}

			}

			@Override
			public void read(String data) {

				if (data.contains("$ LOCK")) {

					return;

				} else if (data.contains("$ UNLOCK")) {

					return;

				}

			}

		};

	}

	public SkywarsType getTypeByBestResolver(String[] args) {

		SkywarsType type = SkywarsType.SW_INSANE_SOLO;
		int lastassert = 0;

		List<String> resolver = new ArrayList<String>() {

			private static final long serialVersionUID = 103020293947854356L;

			{
				for (String s : args) {
					add(s);
				}
			}
		};

		Map<SkywarsType, Integer> asserts = new HashMap<SkywarsType, Integer>();

		for (SkywarsType cache : SkywarsType.values()) {

			if (cache == SkywarsType.NONE)
				continue;

			int cacheinteger = 0;

			List<String> cacheargs = cache.getResolver().getListArgs();
			Iterator<String> iterate = resolver.iterator();

			while (iterate.hasNext()) {

				String str = iterate.next();
				if (cacheargs.contains(str)) {
					cacheinteger++;
				}

			}

			asserts.put(cache, cacheinteger);

		}

		for (Map.Entry<SkywarsType, Integer> k : asserts.entrySet()) {

			if (k.getValue() >= lastassert) {

				lastassert = k.getValue();
				type = k.getKey();

			}

		}

		return type;
	}

	@Override
	public String[] resume() {
		return new String[] { NetworkData.serial };
	}

}
