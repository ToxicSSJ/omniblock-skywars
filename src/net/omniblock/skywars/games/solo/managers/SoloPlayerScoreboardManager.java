/*
 *  Omniblock Developers Team - Copyright (C) 2016
 *
 *  This program is not a free software; you cannot redistribute it and/or modify it.
 *
 *  Only this enabled the editing and writing by the members of the team. 
 *  No third party is allowed to modification of the code.
 *
 */

package net.omniblock.skywars.games.solo.managers;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import net.omniblock.network.library.helpers.scoreboard.ScoreboardUtil;
import net.omniblock.network.library.utils.TextUtil;
import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.games.solo.SoloSkywars;
import net.omniblock.skywars.games.solo.events.SoloPlayerBattleListener;
import net.omniblock.skywars.network.NetworkData;
import net.omniblock.skywars.patch.managers.MapManager;
import net.omniblock.skywars.patch.managers.SpectatorManager;
import net.omniblock.skywars.patch.managers.chest.Chests;
import net.omniblock.skywars.patch.types.MatchType;
import net.omniblock.skywars.util.TimeUtil;

public class SoloPlayerScoreboardManager {

	public static BukkitTask sbrunnable;

	public static Map<VarCodec, String> codecs = new HashMap<VarCodec, String>();

	public enum VarCodec {

		Z_DESTRUCTION_FORMAT, Z_REFILL_FORMAT, Z_APOCALIPSIS_FORMAT,

		NORMAL_REFILL_FORMAT, NORMAL_DUEL_FORMAT,

		INSANE_REFILL_FORMAT, INSANE_DUEL_FORMAT,

		;

	}

	public static void initialize() {

		MatchType matchtype = Chests.currentMatchType;

		switch (matchtype) {
		case INSANE:
			sbrunnable = new BukkitRunnable() {
				String title = TextUtil.format("&9&l     SkyWars     ");
				int round = 0;
				int pos = 0;

				@Override
				public void run() {

					if (pos != 34) {
						pos++;
						title = sbTitleNormal(pos);
					} else {
						if (round != 490) {
							round++;
						} else {
							pos = 0;
							round = 0;
						}
					}

					switch (Skywars.getGameState()) {

					case FINISHING:

						String FINISHING_MATCH_PLUS_FORMAT = Chests.currentMatchType.getName() + " " + getTimeFormat();

						for (Player infinish_p : SoloPlayerManager.getPlayersInGameList()) {
							ScoreboardUtil.unrankedSidebarDisplay(infinish_p, new String[] { title,
									TextUtil.format("&7&o   Modo " + FINISHING_MATCH_PLUS_FORMAT), TextUtil.format(" "),
									TextUtil.format("&b&lServidor:"),
									TextUtil.format(" &a&l» &7" + Bukkit.getServerName()), TextUtil.format("  "),
									TextUtil.format("&b&lGanador:"),
									TextUtil.format(
											" &a&l» &7" + SoloPlayerManager.getPlayersInGameList().get(0) != null
													? SoloPlayerManager.getPlayersInGameList().get(0).getName()
													: "Vacio"),
									TextUtil.format("   "),
									TextUtil.format("&b&lOmnicoins: &a&l+&a"
											+ SoloPlayerBattleListener.battle_info.get(infinish_p).getTotalMoney()),
									TextUtil.format("&b&lExperiencia: &9&l+&9"
											+ SoloPlayerBattleListener.battle_info.get(infinish_p).getTotalExp()),
									TextUtil.format("    "), TextUtil.format("&ewww.omniblock.net") }, false);
						}

						for (Player infinish_p : SpectatorManager.playersSpectators) {
							ScoreboardUtil.unrankedSidebarDisplay(infinish_p, new String[] { title,
									TextUtil.format("&7&o   Modo " + FINISHING_MATCH_PLUS_FORMAT), TextUtil.format(" "),
									TextUtil.format("&b&lServidor:"),
									TextUtil.format(" &a&l» &7" + Bukkit.getServerName()), TextUtil.format("  "),
									TextUtil.format("&b&lGanador:"),
									TextUtil.format(
											" &a&l» &7" + SoloPlayerManager.getPlayersInGameList().get(0) != null
													? SoloPlayerManager.getPlayersInGameList().get(0).getName()
													: "Vacio"),
									TextUtil.format("   "),
									TextUtil.format("&b&lOmnicoins: &a&l+&a"
											+ SoloPlayerBattleListener.battle_info.get(infinish_p).getTotalMoney()),
									TextUtil.format("&b&lExperiencia: &9&l+&9"
											+ SoloPlayerBattleListener.battle_info.get(infinish_p).getTotalExp()),
									TextUtil.format("    "), TextUtil.format("&ewww.omniblock.net") }, false);
						}
						break;
					case IN_GAME:

						String INGAME_MAP_NAME = MapManager.CURRENT_MAP.getName();
						String INGAME_NEXT_EVENT = getNextEvent();
						String INGAME_MATCH_PLUS_FORMAT = Chests.currentMatchType.getName() + " " + getTimeFormat();

						for (Player ingame_p : SoloPlayerManager.getPlayersInGameList()) {
							ScoreboardUtil.unrankedSidebarDisplay(ingame_p,
									new String[] { title, TextUtil.format("&7&o   Modo " + INGAME_MATCH_PLUS_FORMAT),
											TextUtil.format(" "), TextUtil.format(INGAME_NEXT_EVENT),
											TextUtil.format("  "),
											TextUtil.format("&b&lAsesinatos: &a"
													+ SoloPlayerBattleListener.battle_info.get(ingame_p).kills),
											TextUtil.format("&b&lAsistencias: &a"
													+ SoloPlayerBattleListener.battle_info.get(ingame_p).assistences),
											TextUtil.format("   "), TextUtil.format("&b&lMapa: &7" + INGAME_MAP_NAME),
											TextUtil.format("&b&lServidor: &7" + Bukkit.getServerName()),
											TextUtil.format(
													"&b&lJugadores: &7" + SoloPlayerManager.getPlayersInGameAmount()),
											TextUtil.format("    "), TextUtil.format("&ewww.omniblock.net") }, true);
						}

						for (Player spectator_p : SpectatorManager.playersSpectators) {
							ScoreboardUtil.unrankedSidebarDisplay(spectator_p, new String[] { title,
									TextUtil.format("&7&o   Modo " + INGAME_MATCH_PLUS_FORMAT), TextUtil.format(" "),
									TextUtil.format(INGAME_NEXT_EVENT), TextUtil.format("  "),
									TextUtil.format("&b&lAsesinatos: &a"
											+ SoloPlayerBattleListener.battle_info.get(spectator_p).kills),
									TextUtil.format("&b&lAsistencias: &a"
											+ SoloPlayerBattleListener.battle_info.get(spectator_p).assistences),
									TextUtil.format("   "), TextUtil.format("&b&lMapa: &7" + INGAME_MAP_NAME),
									TextUtil.format("&b&lServidor: &7" + Bukkit.getServerName()),
									TextUtil.format("&b&lJugadores: &7" + SoloPlayerManager.getPlayersInGameAmount()),
									TextUtil.format("    "), TextUtil.format("&ewww.omniblock.net") }, false);
						}
						break;
					case IN_LOBBY:

						String IN_LOBBY_MAP_NAME = MapManager.CURRENT_MAP.getName();
						String IN_LOBBY_FORK_TEXT = forkText(NetworkData.generalbooster);
						String IN_LOBBY_MATCH_PLUS_FORMAT = Chests.currentMatchType.getName() + " " + getTimeFormat();

						ScoreboardUtil.unrankedSidebarDisplay(SoloPlayerManager.getPlayersInLobbyList(),
								new String[] { title, TextUtil.format("&7&o   Modo " + IN_LOBBY_MATCH_PLUS_FORMAT),
										TextUtil.format(" "), TextUtil.format("&b&lServidor:"),
										TextUtil.format(" &a&l» &7" + Bukkit.getServerName()), TextUtil.format("  "),
										TextUtil.format("&b&lJugadores:"),
										TextUtil.format(" &a&l» &7" + SoloPlayerManager.getPlayersInLobbyAmount()),
										TextUtil.format("   "), TextUtil.format("&b&lMapa: &7" + IN_LOBBY_MAP_NAME),
										TextUtil.format("&b&lNetwork Booster: &r" + IN_LOBBY_FORK_TEXT),
										TextUtil.format("    "), TextUtil.format("&ewww.omniblock.net") }, false);
						break;
					case IN_PRE_GAME:

						for (Player p : SoloPlayerManager.getPlayersInGameList()) {
							p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
						}

						break;
					default:
						break;

					}
				}
			}.runTaskTimer(Skywars.getInstance(), 10l, 10l);
			break;
		case NORMAL:
			sbrunnable = new BukkitRunnable() {
				String title = TextUtil.format("&9&l     SkyWars     ");
				int round = 0;
				int pos = 0;

				@Override
				public void run() {

					if (pos != 34) {
						pos++;
						title = sbTitleNormal(pos);
					} else {
						if (round != 490) {
							round++;
						} else {
							pos = 0;
							round = 0;
						}
					}

					switch (Skywars.getGameState()) {

					case FINISHING:

						String FINISHING_MATCH_PLUS_FORMAT = Chests.currentMatchType.getName() + " " + getTimeFormat();

						for (Player infinish_p : SoloPlayerManager.getPlayersInGameList()) {
							ScoreboardUtil.unrankedSidebarDisplay(infinish_p, new String[] { title,
									TextUtil.format("&7&o   Modo " + FINISHING_MATCH_PLUS_FORMAT), TextUtil.format(" "),
									TextUtil.format("&b&lServidor:"),
									TextUtil.format(" &a&l» &7" + Bukkit.getServerName()), TextUtil.format("  "),
									TextUtil.format("&b&lGanador:"),
									TextUtil.format(
											" &a&l» &7" + SoloPlayerManager.getPlayersInGameList().get(0) != null
													? SoloPlayerManager.getPlayersInGameList().get(0).getName()
													: "Vacio"),
									TextUtil.format("   "),
									TextUtil.format("&b&lOmnicoins: &a&l+&a"
											+ SoloPlayerBattleListener.battle_info.get(infinish_p).getTotalMoney()),
									TextUtil.format("&b&lExperiencia: &9&l+&9"
											+ SoloPlayerBattleListener.battle_info.get(infinish_p).getTotalExp()),
									TextUtil.format("    "), TextUtil.format("&ewww.omniblock.net") }, false);
						}

						for (Player infinish_p : SpectatorManager.playersSpectators) {
							ScoreboardUtil.unrankedSidebarDisplay(infinish_p, new String[] { title,
									TextUtil.format("&7&o   Modo " + FINISHING_MATCH_PLUS_FORMAT), TextUtil.format(" "),
									TextUtil.format("&b&lServidor:"),
									TextUtil.format(" &a&l» &7" + Bukkit.getServerName()), TextUtil.format("  "),
									TextUtil.format("&b&lGanador:"),
									TextUtil.format(
											" &a&l» &7" + SoloPlayerManager.getPlayersInGameList().get(0) != null
													? SoloPlayerManager.getPlayersInGameList().get(0).getName()
													: "Vacio"),
									TextUtil.format("   "),
									TextUtil.format("&b&lOmnicoins: &a&l+&a"
											+ SoloPlayerBattleListener.battle_info.get(infinish_p).getTotalMoney()),
									TextUtil.format("&b&lExperiencia: &9&l+&9"
											+ SoloPlayerBattleListener.battle_info.get(infinish_p).getTotalExp()),
									TextUtil.format("    "), TextUtil.format("&ewww.omniblock.net") }, false);
						}
						break;
					case IN_GAME:

						String INGAME_MAP_NAME = MapManager.CURRENT_MAP.getName();
						String INGAME_NEXT_EVENT = getNextEvent();
						String INGAME_MATCH_PLUS_FORMAT = Chests.currentMatchType.getName() + " " + getTimeFormat();

						for (Player ingame_p : SoloPlayerManager.getPlayersInGameList()) {
							ScoreboardUtil.unrankedSidebarDisplay(ingame_p,
									new String[] { title, TextUtil.format("&7&o   Modo " + INGAME_MATCH_PLUS_FORMAT),
											TextUtil.format(" "), TextUtil.format(INGAME_NEXT_EVENT),
											TextUtil.format("  "),
											TextUtil.format("&b&lAsesinatos: &a"
													+ SoloPlayerBattleListener.battle_info.get(ingame_p).kills),
											TextUtil.format("&b&lAsistencias: &a"
													+ SoloPlayerBattleListener.battle_info.get(ingame_p).assistences),
											TextUtil.format("   "), TextUtil.format("&b&lMapa: &7" + INGAME_MAP_NAME),
											TextUtil.format("&b&lServidor: &7" + Bukkit.getServerName()),
											TextUtil.format(
													"&b&lJugadores: &7" + SoloPlayerManager.getPlayersInGameAmount()),
											TextUtil.format("    "), TextUtil.format("&ewww.omniblock.net") }, true);
						}

						for (Player spectator_p : SpectatorManager.playersSpectators) {
							ScoreboardUtil.unrankedSidebarDisplay(spectator_p,
									new String[] { title, TextUtil.format("&7&o   Modo " + INGAME_MATCH_PLUS_FORMAT),
											TextUtil.format(" "), TextUtil.format(INGAME_NEXT_EVENT),
											TextUtil.format("   "), TextUtil.format("&b&lMapa: &7" + INGAME_MAP_NAME),
											TextUtil.format("&b&lServidor: &7" + Bukkit.getServerName()),
											TextUtil.format(
													"&b&lJugadores: &7" + SoloPlayerManager.getPlayersInGameAmount()),
											TextUtil.format("    "), TextUtil.format("&ewww.omniblock.net") }, false);
						}
						break;
					case IN_LOBBY:

						String IN_LOBBY_MAP_NAME = MapManager.CURRENT_MAP.getName();
						String IN_LOBBY_FORK_TEXT = forkText(NetworkData.generalbooster);
						String IN_LOBBY_MATCH_PLUS_FORMAT = Chests.currentMatchType.getName() + " " + getTimeFormat();

						ScoreboardUtil.unrankedSidebarDisplay(SoloPlayerManager.getPlayersInLobbyList(),
								new String[] { title, TextUtil.format("&7&o   Modo " + IN_LOBBY_MATCH_PLUS_FORMAT),
										TextUtil.format(" "), TextUtil.format("&b&lServidor:"),
										TextUtil.format(" &a&l» &7" + Bukkit.getServerName()), TextUtil.format("  "),
										TextUtil.format("&b&lJugadores:"),
										TextUtil.format(" &a&l» &7" + SoloPlayerManager.getPlayersInLobbyAmount()),
										TextUtil.format("   "), TextUtil.format("&b&lMapa: &7" + IN_LOBBY_MAP_NAME),
										TextUtil.format("&b&lNetwork Booster: &r" + IN_LOBBY_FORK_TEXT),
										TextUtil.format("    "), TextUtil.format("&ewww.omniblock.net") }, false);
						break;
					case IN_PRE_GAME:
						for (Player p : SoloPlayerManager.getPlayersInGameList()) {
							p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
						}
						break;
					default:
						break;

					}
				}
			}.runTaskTimer(Skywars.getInstance(), 2l, 2l);
			break;
		case Z:
			sbrunnable = new BukkitRunnable() {
				String title = TextUtil.format("&8&l     SkyWars &4&lZ     ");
				int round = 0;
				int pos = 0;

				@Override
				public void run() {

					if (pos != 34) {
						pos++;
						title = sbTitleZ(pos);
					} else {
						if (round != 490) {
							round++;
						} else {
							pos = 0;
							round = 0;
						}
					}

					switch (Skywars.getGameState()) {

					case FINISHING:

						String FINISHING_MATCH_PLUS_FORMAT = Chests.currentMatchType.getName() + " " + getTimeFormat();

						for (Player infinish_p : SoloPlayerManager.getPlayersInGameList()) {
							ScoreboardUtil.unrankedSidebarDisplay(infinish_p, new String[] { title,
									TextUtil.format("&7&o   Modo " + FINISHING_MATCH_PLUS_FORMAT), TextUtil.format(" "),
									TextUtil.format("&b&lServidor:"),
									TextUtil.format(" &a&l» &7" + Bukkit.getServerName()), TextUtil.format("  "),
									TextUtil.format("&b&lGanador:"),
									TextUtil.format(
											" &a&l» &7" + SoloPlayerManager.getPlayersInGameList().get(0) != null
													? SoloPlayerManager.getPlayersInGameList().get(0).getName()
													: "Vacio"),
									TextUtil.format("   "),
									TextUtil.format("&b&lOmnicoins: &a&l+&a"
											+ SoloPlayerBattleListener.battle_info.get(infinish_p).getTotalMoney()),
									TextUtil.format("&b&lExperiencia: &9&l+&9"
											+ SoloPlayerBattleListener.battle_info.get(infinish_p).getTotalExp()),
									TextUtil.format("    "), TextUtil.format("&ewww.omniblock.net") }, false);
						}

						for (Player infinish_p : SpectatorManager.playersSpectators) {
							ScoreboardUtil.unrankedSidebarDisplay(infinish_p, new String[] { title,
									TextUtil.format("&7&o   Modo " + FINISHING_MATCH_PLUS_FORMAT), TextUtil.format(" "),
									TextUtil.format("&b&lServidor:"),
									TextUtil.format(" &a&l» &7" + Bukkit.getServerName()), TextUtil.format("  "),
									TextUtil.format("&b&lGanador:"),
									TextUtil.format(
											" &a&l» &7" + SoloPlayerManager.getPlayersInGameList().get(0) != null
													? SoloPlayerManager.getPlayersInGameList().get(0).getName()
													: "Vacio"),
									TextUtil.format("   "),
									TextUtil.format("&b&lOmnicoins: &a&l+&a"
											+ SoloPlayerBattleListener.battle_info.get(infinish_p).getTotalMoney()),
									TextUtil.format("&b&lExperiencia: &9&l+&9"
											+ SoloPlayerBattleListener.battle_info.get(infinish_p).getTotalExp()),
									TextUtil.format("    "), TextUtil.format("&ewww.omniblock.net") }, false);
						}
						break;
					case IN_GAME:

						String INGAME_MAP_NAME = MapManager.CURRENT_MAP.getName();
						String INGAME_NEXT_EVENT = getNextEvent();
						String INGAME_MATCH_PLUS_FORMAT = Chests.currentMatchType.getName() + " " + getTimeFormat();

						for (Player ingame_p : SoloPlayerManager.getPlayersInGameList()) {
							ScoreboardUtil.unrankedSidebarDisplay(ingame_p,
									new String[] { title, TextUtil.format("&7&o   Modo " + INGAME_MATCH_PLUS_FORMAT),
											TextUtil.format(" "), TextUtil.format(INGAME_NEXT_EVENT),
											TextUtil.format("  "),
											TextUtil.format("&b&lAsesinatos: &a"
													+ SoloPlayerBattleListener.battle_info.get(ingame_p).kills),
											TextUtil.format("&b&lAsistencias: &a"
													+ SoloPlayerBattleListener.battle_info.get(ingame_p).assistences),
											TextUtil.format("   "), TextUtil.format("&b&lMapa: &7" + INGAME_MAP_NAME),
											TextUtil.format("&b&lServidor: &7" + Bukkit.getServerName()),
											TextUtil.format(
													"&b&lJugadores: &7" + SoloPlayerManager.getPlayersInGameAmount()),
											TextUtil.format("    "), TextUtil.format("&ewww.omniblock.net") }, true);
						}

						for (Player spectator_p : SpectatorManager.playersSpectators) {
							ScoreboardUtil.unrankedSidebarDisplay(spectator_p,
									new String[] { title, TextUtil.format("&7&o   Modo " + INGAME_MATCH_PLUS_FORMAT),
											TextUtil.format(" "), TextUtil.format(INGAME_NEXT_EVENT),
											TextUtil.format("   "), TextUtil.format("&b&lMapa: &7" + INGAME_MAP_NAME),
											TextUtil.format("&b&lServidor: &7" + Bukkit.getServerName()),
											TextUtil.format(
													"&b&lJugadores: &7" + SoloPlayerManager.getPlayersInGameAmount()),
											TextUtil.format("    "), TextUtil.format("&ewww.omniblock.net") }, false);
						}
						break;
					case IN_LOBBY:

						String IN_LOBBY_MAP_NAME = MapManager.CURRENT_MAP.getName();
						String IN_LOBBY_FORK_TEXT = forkText(NetworkData.generalbooster);
						String IN_LOBBY_MATCH_PLUS_FORMAT = Chests.currentMatchType.getName() + " " + getTimeFormat();

						ScoreboardUtil.unrankedSidebarDisplay(SoloPlayerManager.getPlayersInLobbyList(),
								new String[] { title, TextUtil.format("&7&o   Modo " + IN_LOBBY_MATCH_PLUS_FORMAT),
										TextUtil.format(" "), TextUtil.format("&b&lServidor:"),
										TextUtil.format(" &a&l» &7" + Bukkit.getServerName()), TextUtil.format("  "),
										TextUtil.format("&b&lJugadores:"),
										TextUtil.format(" &a&l» &7" + SoloPlayerManager.getPlayersInLobbyAmount()),
										TextUtil.format("   "), TextUtil.format("&b&lMapa: &7" + IN_LOBBY_MAP_NAME),
										TextUtil.format("&b&lNetwork Booster: &r" + IN_LOBBY_FORK_TEXT),
										TextUtil.format("    "), TextUtil.format("&ewww.omniblock.net") }, false);
						break;
					case IN_PRE_GAME:
						for (Player p : SoloPlayerManager.getPlayersInGameList()) {
							p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
						}
						break;
					default:
						break;

					}
				}
			}.runTaskTimer(Skywars.getInstance(), 2l, 2l);
			break;
		default:
			break;
		}
	}

	public static String getTimeFormat() {
		return TimeUtil.format(NetworkData.date);
	}

	public static String getNextEvent() {

		Map<String, Integer> map = SoloSkywars.mainRunnableTask.getEvents();
		String[] eventinfo = getEvent(map);

		if (eventinfo.length >= 2) {
			return TextUtil.format(eventinfo[0] + " &7" + eventinfo[1]);
		}

		return TextUtil.format("&7&lFinalizado");
	}

	public static String[] getEvent(Map<String, Integer> k) {

		if (k.size() > 0) {

			String text = "Unknow";
			int time = 1000;

			for (Map.Entry<String, Integer> m : k.entrySet()) {
				if (m.getValue() <= time) {
					text = m.getKey();
					time = m.getValue();
				}
			}

			return new String[] { TextUtil.format(text), TimeUtil.toTimeMinutesFormat(time) };

		}

		return new String[] { TextUtil.format("&7&lFinalizado"), "" };

	}

	public static String forkText(boolean bool) {
		if (bool) {
			return TextUtil.format("&aActivado");
		}
		return TextUtil.format("&8Desactivado");
	}

	public static String sbTitleNormal(int a) {
		switch (a) {
		case 1:
			return TextUtil.format("&9&l     SkyWars     ");
		case 2:
			return TextUtil.format("&8&l     SkyWars     ");
		case 3:
			return TextUtil.format("&b&l     SkyWars     ");
		case 4:
			return TextUtil.format("&9&l     &b&lSkyWars     ");
		case 5:
			return TextUtil.format("&8&l     &9&lS&b&lkyWars     ");
		case 6:
			return TextUtil.format("&8&l     S&9&lk&b&lyWars     ");
		case 7:
			return TextUtil.format("&8&l     Sk&9&ly&b&lWars     ");
		case 8:
			return TextUtil.format("&8&l     Sky&9&lW&b&lars     ");
		case 9:
			return TextUtil.format("&8&l     SkyW&9&la&b&lrs     ");
		case 10:
			return TextUtil.format("&8&l     SkyWa&9&lr&b&ls     ");
		case 11:
			return TextUtil.format("&8&l     SkyWar&9&ls&b&l     ");
		case 12:
			return TextUtil.format("&8&l     SkyWars     ");
		case 13:
			return TextUtil.format("&8&l     SkyWars     ");
		case 14:
			return TextUtil.format("&8&l     SkyWars     ");
		case 15:
			return TextUtil.format("&8&l     SkyWars     ");
		case 16:
			return TextUtil.format("&8&l     SkyWars     ");
		case 18:
			return TextUtil.format("&8&l     SkyWars     ");
		case 19:
			return TextUtil.format("&8&l     SkyWars     ");
		case 20:
			return TextUtil.format("&8&l     SkyWars     ");
		case 21:
			return TextUtil.format("&9&l     &8&lSkyWars     ");
		case 22:
			return TextUtil.format("&b&l     &9&lS&8&lkyWars     ");
		case 23:
			return TextUtil.format("&b&l     S&9&lk&8&lyWars     ");
		case 24:
			return TextUtil.format("&b&l     Sk&9&ly&8&lWars     ");
		case 25:
			return TextUtil.format("&b&l     Sky&9&lW&8&lars     ");
		case 26:
			return TextUtil.format("&b&l     SkyW&9&la&8&lrs     ");
		case 27:
			return TextUtil.format("&b&l     SkyWa&9&lr&8&ls     ");
		case 28:
			return TextUtil.format("&b&l     SkyWar&9&ls     ");
		case 29:
			return TextUtil.format("&b&l     SkyWars     ");
		case 30:
			return TextUtil.format("&b&l     SkyWars     ");
		case 31:
			return TextUtil.format("&b&l     SkyWars     ");
		case 32:
			return TextUtil.format("&b&l     SkyWars     ");
		case 33:
			return TextUtil.format("&8&l     SkyWars     ");
		case 34:
			return TextUtil.format("&9&l     SkyWars     ");

		}
		return TextUtil.format("&9&l     SkyWars     ");
	}

	public static String sbTitleZ(int a) {
		switch (a) {
		case 1:
			return TextUtil.format("&8&l     SkyWars &4&lZ     ");
		case 2:
			return TextUtil.format("&8&l     SkyWars &4&lZ     ");
		case 3:
			return TextUtil.format("&8&l     SkyWars &4&lZ     ");
		case 4:
			return TextUtil.format("&8&l     SkyWars &4&lZ     ");
		case 5:
			return TextUtil.format("&8&l     SkyWars &c&lZ     ");
		case 6:
			return TextUtil.format("&8&l     SkyWars &9&lZ     ");
		case 7:
			return TextUtil.format("&8&l     SkyWars &e&lZ     ");
		case 8:
			return TextUtil.format("&8&l     SkyWars &6&lZ     ");
		case 9:
			return TextUtil.format("&8&l     SkyWars &4&lZ     ");
		case 10:
			return TextUtil.format("&8&l     SkyWars &4&lZ     ");
		case 11:
			return TextUtil.format("&8&l     SkyWars &4&lZ     ");
		case 12:
			return TextUtil.format("&8&l     SkyWars &4&lZ     ");
		case 13:
			return TextUtil.format("&8&l     SkyWars &4&lZ     ");
		case 14:
			return TextUtil.format("&8&l     SkyWars &4&lZ     ");
		case 15:
			return TextUtil.format("&8&l     SkyWars &4&lZ     ");
		case 16:
			return TextUtil.format("&8&l     SkyWars &4&lZ     ");
		case 18:
			return TextUtil.format("&8&l     SkyWars &4&lZ     ");
		case 19:
			return TextUtil.format("&8&l     SkyWars &4&lZ     ");
		case 20:
			return TextUtil.format("&8&l     SkyWars &4&lZ     ");
		case 21:
			return TextUtil.format("&8&l     SkyWars &4&lZ     ");
		case 22:
			return TextUtil.format("&9&l     S&8&lkyWars &4&lZ     ");
		case 23:
			return TextUtil.format("&9&l     Sk&8&lyWars &4&lZ     ");
		case 24:
			return TextUtil.format("&8&l     S&9&lky&8&lWars &4&lZ     ");
		case 25:
			return TextUtil.format("&8&l     Sk&9&lyW&8&lars &4&lZ     ");
		case 26:
			return TextUtil.format("&8&l     Sky&9&lWa&8&lrs &4&lZ     ");
		case 27:
			return TextUtil.format("&8&l     SkyW&9&lar&8&ls &4&lZ     ");
		case 28:
			return TextUtil.format("&8&l     SkyWa&9&lrs &4&lZ     ");
		case 29:
			return TextUtil.format("&8&l     SkyWar&9&ls &4&lZ     ");
		case 30:
			return TextUtil.format("&8&l     SkyWars &4&lZ     ");
		case 31:
			return TextUtil.format("&8&l     SkyWars &4&lZ     ");
		case 32:
			return TextUtil.format("&8&l     SkyWars &4&lZ     ");
		case 33:
			return TextUtil.format("&8&l     SkyWars &4&lZ     ");
		case 34:
			return TextUtil.format("&8&l     SkyWars &4&lZ     ");

		}
		return TextUtil.format("&8&l     SkyWars &4&lZ     ");
	}
}
