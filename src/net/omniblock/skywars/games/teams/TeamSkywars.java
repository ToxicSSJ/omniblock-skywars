/*
 *  Omniblock Developers Team - Copyright (C) 2016
 *
 *  This program is not a free software; you cannot redistribute it and/or modify it.
 *
 *  Only this enabled the editing and writing by the members of the team. 
 *  No third party is allowed to modification of the code.
 *
 */

package net.omniblock.skywars.games.teams;

import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.collect.Lists;

import net.citizensnpcs.api.CitizensAPI;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.omniblock.lobbies.data.controller.bases.SkywarsBase;
import net.omniblock.network.handlers.base.bases.type.RankBase;
import net.omniblock.network.library.utils.RestarterUtil;
import net.omniblock.network.library.utils.TextUtil;
import net.omniblock.packets.network.Packets;
import net.omniblock.packets.network.structure.packet.PlayerSendToServerPacket;
import net.omniblock.packets.network.structure.type.PacketSenderType;
import net.omniblock.packets.object.external.ServerType;
import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.SkywarsGameState;
import net.omniblock.skywars.games.teams.events.TeamPlayerBattleListener;
import net.omniblock.skywars.games.teams.events.TeamPlayerToggleListener;
import net.omniblock.skywars.games.teams.managers.TeamPlayerManager;
import net.omniblock.skywars.games.teams.managers.TeamPlayerScoreboardManager;
import net.omniblock.skywars.games.teams.object.TeamPlayerBattleInfo;
import net.omniblock.skywars.games.teams.object.TeamPlayerBattleInfo.PlayerBattleInfoUtils;
import net.omniblock.skywars.network.NetworkData;
import net.omniblock.skywars.patch.internal.SkywarsResolver;
import net.omniblock.skywars.patch.internal.SkywarsStarter;
import net.omniblock.skywars.patch.managers.EventsManager;
import net.omniblock.skywars.patch.managers.MapManager;
import net.omniblock.skywars.patch.managers.MapManager.MapType;
import net.omniblock.skywars.patch.managers.chest.Chests;
import net.omniblock.skywars.patch.types.MatchType;
import net.omniblock.skywars.patch.types.SkywarsType;
import net.omniblock.skywars.util.LocationUtil;
import net.omniblock.skywars.util.RandomFirework;

public class TeamSkywars implements SkywarsStarter {

	public static final int DEFAULT_NORMAL_SKYWARS_MAX_PLAYERS = 24;
	public static final int DEFAULT_Z_SKYWARS_MAX_PLAYERS = 32;

	public static final int DEFAULT_NORMAL_SKYWARS_MIN_PLAYERS = 2;
	public static final int DEFAULT_Z_SKYWARS_MIN_PLAYERS = 2;

	public static int MAX_PLAYERS = 24;
	public static int MIN_PLAYERS = 2;

	public static SkywarsResolver gSkywarsResolver;
	public static TeamSkywarsRunnable mainRunnableTask;

	public static List<Location> cagesLocations = Lists.newArrayList();

	@Override
	public void run(SkywarsType skywarsType, SkywarsResolver sr) {

		gSkywarsResolver = sr;

		TeamSkywarsRunnable cacherunnable = new TeamSkywarsRunnable(this);
		mainRunnableTask = cacherunnable;
		mainRunnableTask.runTaskTimer(Skywars.getInstance(), 20L, 20L);

		if (gSkywarsResolver.getListArgs().contains("normal")) {

			startTeamsNormalGame();

		} else if (gSkywarsResolver.getListArgs().contains("insane")) {

			startTeamsInsaneGame();

		} else if (gSkywarsResolver.getListArgs().contains("z")) {

			startTeamsZGame();

		} else {

			new IllegalStateException(
					"SkywarsResolver no fué especificado con un modo correcto y el sistema no sabe cómo continuar: "
							+ gSkywarsResolver.getListArgs());
			stop();

		}

		/*
		 * Eventos
		 */
		EventsManager.setupEvents(Skywars.currentMatchType);

		/*
		 * Scoreboard
		 */
		Skywars.updateGameState(SkywarsGameState.IN_LOBBY);
		TeamPlayerScoreboardManager.initialize();

		/*
		 * Online Player Add-Adder
		 */
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (!TeamPlayerManager.getPlayersInLobbyList().contains(p)) {
				TeamPlayerManager.addPlayer(p);
			}
		}

	}

	private void startTeamsNormalGame() {

		Chests.currentMatchType = MatchType.NORMAL;
		TeamPlayerManager.currentMatchType = MatchType.NORMAL;

		MAX_PLAYERS = DEFAULT_NORMAL_SKYWARS_MAX_PLAYERS;
		MIN_PLAYERS = DEFAULT_NORMAL_SKYWARS_MIN_PLAYERS;

		MapManager.setCurrentMap(MapType.NORMAL);

		MapManager.lobbyschematic.selectMapType(MapType.NORMAL);
		cagesLocations.addAll(MapManager.MAP_NORMAL_CAGE_LOCATIONS);

		/*
		 * SYSTEM VARS (EVENTS) Only for the game with these specs: - Solo -
		 * Normal
		 */

		mainRunnableTask.addEvent("&6&lRELLENADO:", 120);
		mainRunnableTask.addEvent("&6&lRELLENADO:", 200);
		mainRunnableTask.addEvent("&8&lELECCIÓN:", 420);

	}

	private void startTeamsInsaneGame() {

		Chests.currentMatchType = MatchType.INSANE;
		TeamPlayerManager.currentMatchType = MatchType.INSANE;


		MAX_PLAYERS = DEFAULT_NORMAL_SKYWARS_MAX_PLAYERS;
		MIN_PLAYERS = DEFAULT_NORMAL_SKYWARS_MIN_PLAYERS;

		MapManager.setCurrentMap(MapType.NORMAL);

		MapManager.lobbyschematic.selectMapType(MapType.NORMAL);
		cagesLocations.addAll(MapManager.MAP_NORMAL_CAGE_LOCATIONS);

		/*
		 * SYSTEM VARS (EVENTS) Only for the game with these specs: - Solo -
		 * Mejorado (Insano)
		 */

		mainRunnableTask.addEvent("&6&lRELLENADO:", 120);
		mainRunnableTask.addEvent("&6&lRELLENADO:", 200);
		mainRunnableTask.addEvent("&8&lELECCIÓN:", 420);

	}

	private void startTeamsZGame() {

		Chests.currentMatchType = MatchType.Z;
		TeamPlayerManager.currentMatchType = MatchType.Z;

		MAX_PLAYERS = DEFAULT_Z_SKYWARS_MAX_PLAYERS;
		MIN_PLAYERS = DEFAULT_Z_SKYWARS_MIN_PLAYERS;

		MapManager.setCurrentMap(MapType.Z);

		MapManager.lobbyschematic.selectMapType(MapType.Z);
		cagesLocations.addAll(MapManager.MAP_Z_CAGE_LOCATIONS);

		/*
		 * SYSTEM VARS (EVENTS) Only for the game with these specs: - Solo - Z
		 */

		mainRunnableTask.addEvent("&c&lDESTRUCCIÓN:", 60);
		mainRunnableTask.addEvent("&6&lRELLENADO:", 160);
		mainRunnableTask.addEvent("&6&lRELLENADO:", 240);
		mainRunnableTask.addEvent("&4&lAPOCALIPSIS:", 380);
		mainRunnableTask.addEvent("&8&lELECCIÓN:", 430);

	}

	public void finalize(Player winner) {

		final Map<TeamPlayerBattleInfo, Integer> cache_top = PlayerBattleInfoUtils
				.getTop(TeamPlayerBattleListener.battle_info);
		final Map<Integer, TeamPlayerBattleInfo> top = PlayerBattleInfoUtils.reverse(cache_top);

		final TeamPlayerBattleInfo TOP_1 = top.get(1);
		final TeamPlayerBattleInfo TOP_2 = top.get(2);
		final TeamPlayerBattleInfo TOP_3 = top.get(3);

		Player team = null;
		boolean win = winner != null;

		if (win) {

			if (TeamPlayerManager.hasTeam(winner)) {
				TeamPlayerManager.getPlayerTeam(team);
			}

			TeamPlayerManager.winnerTeam(winner);
		}

		for (Player p : Bukkit.getOnlinePlayers()) {
			p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, -10);
			p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, -10);
		}

		new BukkitRunnable() {

			Location fix_loc = MapManager.lobbyschematic.getLocation().getWorld()
					.getHighestBlockAt(MapManager.lobbyschematic.getLocation()).getLocation();
			int launched = 0;

			@Override
			public void run() {
				if (launched < 12) {
					if (win) {
						if (winner.isOnline()) {
							for (int i = 0; i < 5; i++) {
								RandomFirework.spawnRandomFirework(LocationUtil.getRandomLocation(winner), 3);
							}
							launched++;
							return;
						}
					}

					for (int i = 0; i < 5; i++) {
						RandomFirework.spawnRandomFirework(LocationUtil.getRandomLocation(fix_loc), 3);
					}
					launched++;
					return;
				}
			}
		}.runTaskTimer(Skywars.getInstance(), 5L, 20L);

		new BukkitRunnable() {
			@Override
			public void run() {

				for (Player p : Bukkit.getOnlinePlayers()) {
					p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 10, -10);
					p.playSound(p.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 10, -10);
				}

				if (win) {

					Bukkit.broadcastMessage(TextUtil.format("&r"));
					Bukkit.broadcastMessage(TextUtil.format("&r"));
					Bukkit.broadcastMessage(TextUtil.format("&7&l» &7Tabla de Posiciones/Scores&8&l:"));
					Bukkit.broadcastMessage(TextUtil.format("&r"));
					Bukkit.broadcastMessage(TextUtil.getCenteredMessage(team != null
							? "&r         &a&lGANADORES&r &a&l&m-&r &7" + RankBase.getRank(winner).getCustomName(winner)
									+ "&7," + RankBase.getRank(team).getCustomName(team)
							: "&r         &a&lGANADOR&r &a&l&m-&r &7"
									+ RankBase.getRank(winner).getCustomName(winner)));
					Bukkit.broadcastMessage(TextUtil.getCenteredMessage("&r"));
					Bukkit.broadcastMessage(TOP_1.getTopTierMessage(1));
					Bukkit.broadcastMessage(TOP_2.getTopTierMessage(2));
					Bukkit.broadcastMessage(TOP_3.getTopTierMessage(3));
					Bukkit.broadcastMessage(TextUtil.format("&r"));
					for (Map.Entry<TeamPlayerBattleInfo, Integer> k : cache_top.entrySet()) {

						if (!k.getKey().unknow) {
							Player p = k.getKey().player;
							if (p.isOnline()) {

								int top = k.getKey().getTopNumber(cache_top);
								p.sendMessage(TextUtil.getCenteredMessage(
										"&7Tu pocisión fue &a&l" + top + " &7en esta partida con un promedio de &9&l"
												+ k.getKey().getAverage() + "&r"));

							}
						}

					}
					for (Player p : TeamPlayerManager.getPlayersInSpectator()) {
						boolean contains = false;
						for (Map.Entry<TeamPlayerBattleInfo, Integer> k : cache_top.entrySet()) {
							if (!k.getKey().unknow) {
								if (k.getKey().player != null) {
									if (p == k.getKey().player) {
										contains = true;
										break;
									}
								}
							}
						}
						if (!contains) {
							p.sendMessage(TextUtil.getCenteredMessage(
									"&7El promedio del jugador &c&l#1&r &7fue de &9&l" + TOP_1.getAverage()));
						}
					}

					Bukkit.broadcastMessage(TextUtil.format("&r"));

				} else {

					Bukkit.broadcastMessage(TextUtil.format("&r"));
					Bukkit.broadcastMessage(TextUtil.format("&r"));
					Bukkit.broadcastMessage(TextUtil.format("&7&l» &7Tabla de Posiciones/Scores&8&l:"));
					Bukkit.broadcastMessage(TextUtil.format("&r"));
					Bukkit.broadcastMessage(TOP_1.getTopTierMessage(1));
					Bukkit.broadcastMessage(TOP_2.getTopTierMessage(2));
					Bukkit.broadcastMessage(TOP_3.getTopTierMessage(3));
					Bukkit.broadcastMessage(TextUtil.format("&r"));

					for (Map.Entry<TeamPlayerBattleInfo, Integer> k : cache_top.entrySet()) {

						if (!k.getKey().unknow) {
							Player p = k.getKey().player;
							if (p.isOnline()) {

								int top = k.getKey().getTopNumber(cache_top);
								p.sendMessage(TextUtil.getCenteredMessage(
										"&7Tu pocisión fue &a&l" + top + " &7en esta partida con un promedio de &9&l"
												+ k.getKey().getAverage() + "&r"));

							}
						}

					}

					for (Player p : TeamPlayerManager.getPlayersInSpectator()) {
						boolean contains = false;
						for (Map.Entry<TeamPlayerBattleInfo, Integer> k : cache_top.entrySet()) {
							if (!k.getKey().unknow) {
								if (k.getKey().player != null) {
									if (p == k.getKey().player) {
										contains = true;
										break;
									}
								}
							}
						}
						if (!contains) {
							p.sendMessage(TextUtil.getCenteredMessage(
									"&7El promedio del jugador &c&l#1&r &7fue de &9&l" + TOP_1.getAverage()));
						}
					}

					Bukkit.broadcastMessage(TextUtil.format("&r"));

				}
			}
		}.runTaskLater(Skywars.getInstance(), 60L);

		new BukkitRunnable() {
			@Override
			public void run() {

				for (Player p : Bukkit.getOnlinePlayers()) {
					p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 10, -10);
					p.playSound(p.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 10, -10);
				}

				for (Map.Entry<TeamPlayerBattleInfo, Integer> k : cache_top.entrySet()) {

					if (!k.getKey().unknow) {
						if (k.getKey().player != null) {

							Player p = k.getKey().player;

							if (p.isOnline()) {

								p.sendMessage(TextUtil.format("&r"));
								p.sendMessage(TextUtil.format("&r"));
								p.sendMessage(TextUtil.format("&7&l» &7Ganancias/Premios&8&l:"));
								p.sendMessage(TextUtil.getCenteredMessage("&r"));
								p.sendMessage(TextUtil.getCenteredMessage(" Has ganado un total de &8&l(&3&l+&3"
										+ k.getKey().getExp() + " &3&lExp&8&l)"));
								p.sendMessage(TextUtil.getCenteredMessage(" Has ganado un total de &8&l(&a&l+&a"
										+ k.getKey().getMoney() + " &a&lCoins&8&l)"));
								if (k.getKey().isFirstBlood()) {
									p.sendMessage(TextUtil.getCenteredMessage(
											" Premio por primera muerte &8&l(&a&l+&a20 &a&lCoins&8&l) &8&l(&3&l+&316 &3&lExp&8&l)"));
								}
								if (k.getKey().survival) {
									p.sendMessage(TextUtil
											.getCenteredMessage(" Premio por supervivencia &8&l(&a&l+&a15 &a&lCoins)"));
								}
								p.sendMessage(TextUtil.format("&r"));
								if (NetworkData.generalbooster) {
									p.sendMessage(TextUtil.getCenteredMessage(" &9&lTOTAL: &8&l+&a"
											+ k.getKey().getTotalMoney() + " Coins &6&lX2    &8&l+&3"
											+ k.getKey().getTotalExp() + " Exp &6&lX2"));
									p.sendMessage(TextUtil.format("&r"));
									p.sendMessage(TextUtil
											.getCenteredMessage("&e¡Network Booster de Unknow activado! &6&lX2!"));
								} else {
									p.sendMessage(TextUtil
											.getCenteredMessage(" &9&lTOTAL: &8&l+&a" + k.getKey().getTotalMoney()
													+ " Coins    &8&l+&3" + k.getKey().getTotalExp() + " Exp"));
									p.sendMessage(TextUtil.format("&r"));
								}

							}

							k.getKey().redeemPrizes();

						}
					}

				}

			}
		}.runTaskLater(Skywars.getInstance(), 200L);

		new BukkitRunnable() {
			@Override
			public void run() {

				for (Player p : Bukkit.getOnlinePlayers()) {
					p.playSound(p.getLocation(), Sound.ENTITY_HORSE_SADDLE, 10, -10);
				}

				for (Map.Entry<TeamPlayerBattleInfo, Integer> k : cache_top.entrySet()) {

					if (!k.getKey().unknow) {
						if (k.getKey().player != null) {

							Player p = k.getKey().player;

							if (p.isOnline()) {

								p.sendMessage(TextUtil.format("&r"));
								p.sendMessage(TextUtil.format("&r"));
								p.sendMessage(TextUtil.format("&7&l» &7Estadisticas Generales/Stats&8&l:"));
								p.sendMessage(TextUtil.getCenteredMessage("&r"));
								p.sendMessage(
										TextUtil.getCenteredMessage(" &7Servidor: &2&l" + Bukkit.getServerName()));
								p.sendMessage(TextUtil.getCenteredMessage(" &7Mapa: &6&l"
										+ MapManager.lobbyschematic.getLocation().getWorld().getName()));
								p.sendMessage(TextUtil.getCenteredMessage("&r"));

								if (SkywarsBase.SAVED_ACCOUNTS.containsKey(p)) {

									p.sendMessage(TextUtil.getCenteredMessage(" &7Kills Totales: &c"
											+ (SkywarsBase.getKills(SkywarsBase.SAVED_ACCOUNTS.get(p).getStats())
													+ k.getKey().getKills())));
									p.sendMessage(TextUtil.getCenteredMessage(" &7Asistencias Totales: &b"
											+ (SkywarsBase.getAssistences(SkywarsBase.SAVED_ACCOUNTS.get(p).getStats())
													+ k.getKey().getAssistences())));
									p.sendMessage(TextUtil.getCenteredMessage(" &7Promedio Total: &9&l"
											+ (SkywarsBase.getAverage(SkywarsBase.SAVED_ACCOUNTS.get(p).getStats()))));
									p.sendMessage(TextUtil.getCenteredMessage("&r"));

								}

								ComponentBuilder message = new ComponentBuilder(" ¿Te gustó el mapa?   ");
								message.color(ChatColor.GRAY);
								
								message.append("Si" + ChatColor.COLOR_CHAR + "r    ");
								message.event(new ClickEvent(Action.RUN_COMMAND, "/gen_command_vote true"));
								message.color(ChatColor.GREEN);
								message.strikethrough(false);
								message.underlined(true);
								
								message.append("--" + ChatColor.COLOR_CHAR + "r    ");
								message.color(ChatColor.DARK_GRAY);
								message.strikethrough(true);
								message.underlined(false);
								
								message.append("No");
								message.event(new ClickEvent(Action.RUN_COMMAND, "/gen_command_vote false"));
								message.color(ChatColor.RED);
								message.strikethrough(false);
								message.underlined(true);
								
								p.spigot().sendMessage(message.create());

							}

						}
					}

				}

			}
		}.runTaskLater(Skywars.getInstance(), 290L);

		new BukkitRunnable() {
			@Override
			public void run() {

				reset();

			}
		}.runTaskLater(Skywars.getInstance(), 420L);

	}

	@Override
	public void reset() {

		Chests.currentMatchType = MatchType.NONE;

		if (mainRunnableTask != null) {
			mainRunnableTask.cancel();
			mainRunnableTask = null;
		}

		cagesLocations.clear();

		for (Player player : Bukkit.getOnlinePlayers()) {

			Packets.STREAMER.streamPacket(new PlayerSendToServerPacket()

					.setPlayername(player.getName()).setServertype(ServerType.SKYWARS_LOBBY_SERVER).setParty(false)

					.build().setReceiver(PacketSenderType.OMNICORE));

		}

		new BukkitRunnable() {

			int seconds = 10;
			int round = 0;

			@Override
			public void run() {

				if (Bukkit.getOnlinePlayers().size() != 0) {

					if (round != 20) {

						round++;

					} else {

						round = 0;
						seconds--;

					}

					if (seconds == 0) {

						cancel();

						for (Player p : Bukkit.getOnlinePlayers()) {
							p.kickPlayer(TextUtil.format("&7Se ha perdido la conexión con &9&lOmniNetwork \n "
									+ "&7Por favor, Ingresa nuevamente."));
						}

						initializeReset();
						return;

					}

				} else {

					cancel();
					initializeReset();
					return;

				}

			}

		}.runTaskTimer(Skywars.getInstance(), 1L, 1L);

	}

	public static void initializeReset() {

		TeamSkywarsRunnable.EVENTS.clear();
		TeamPlayerScoreboardManager.sbrunnable.cancel();

		TeamPlayerToggleListener.Verifier = false;

		cagesLocations.clear();

		EventsManager.reset();
		TeamPlayerBattleListener.reset();

		CitizensAPI.getNPCRegistry().deregisterAll();

		MapManager.unloadActualWorldsAndReset();
		Skywars.updateGameState(SkywarsGameState.IN_LOBBY);

		stop();

	}

	public static void stop() {

		MapManager.unloadWorlds();

		System.out.println("Reiniciando servidor en 3 segundos, presione CTRL + C si desea cancelar el reinicio.");

		new BukkitRunnable() {

			@Override
			public void run() {

				RestarterUtil.sendRestart();

			}

		}.runTaskLater(Skywars.getInstance(), 20 * 3);

	}

	public static List<Location> getCageLocations() {
		return cagesLocations;
	}

}