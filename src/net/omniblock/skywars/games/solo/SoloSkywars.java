/*
 *  Omniblock Developers Team - Copyright (C) 2016
 *
 *  This program is not a free software; you cannot redistribute it and/or modify it.
 *
 *  Only this enabled the editing and writing by the members of the team. 
 *  No third party is allowed to modification of the code.
 *
 */

package net.omniblock.skywars.games.solo;

import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.collect.Lists;

import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.games.solo.events.SoloPlayerBattleListener;
import net.omniblock.skywars.games.solo.events.SoloPlayerCustomProtocols;
import net.omniblock.skywars.games.solo.events.SoloPlayerJoinListener;
import net.omniblock.skywars.games.solo.events.SoloPlayerQuitListener;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;
import net.omniblock.skywars.games.solo.managers.SoloPlayerScoreboardManager;
import net.omniblock.skywars.games.solo.object.PlayerBattleInfo;
import net.omniblock.skywars.games.solo.object.PlayerBattleInfo.PlayerBattleInfoUtils;
import net.omniblock.skywars.games.solo.types.MatchType;
import net.omniblock.skywars.network.NetworkData;
import net.omniblock.skywars.patch.internal.SkywarsResolver;
import net.omniblock.skywars.patch.internal.SkywarsStarter;
import net.omniblock.skywars.patch.managers.AccountManager;
import net.omniblock.skywars.patch.managers.LobbySchematic;
import net.omniblock.skywars.patch.managers.MapManager;
import net.omniblock.skywars.patch.types.SkywarsType;
import net.omniblock.skywars.util.LocationUtil;
import net.omniblock.skywars.util.RandomFirework;
import net.omniblock.skywars.util.Scan;
import net.omniblock.skywars.util.TextUtil;

public class SoloSkywars implements SkywarsStarter {

	public static LobbySchematic lobbyschematic;
	
	public static SkywarsResolver gSkywarsResolver;
	
	private static MatchType gMatchType = MatchType.NONE;
	
	public  static SoloSkywarsRunnable mainRunnableTask;
	
	public static List<Location> cagesLocations = Lists.newArrayList();
	
	@Override
	public void run(SkywarsType skywarsType, SkywarsResolver sr) {
		gSkywarsResolver = sr;
		
		SoloSkywarsRunnable cacherunnable = new SoloSkywarsRunnable(this);
		mainRunnableTask = cacherunnable;
		mainRunnableTask.runTaskTimer(Skywars.getInstance(), 20L, 20L);
		
		if(gSkywarsResolver.getListArgs().contains("normal")){
			System.out.println("MODO NORMAL");
			startSoloNormalGame();
		} else if(gSkywarsResolver.getListArgs().contains("insane")){
			System.out.println("MODO INSANO");
			startSoloInsaneGame();
		} else if(gSkywarsResolver.getListArgs().contains("z")){
			System.out.println("MODO Z");
			startSoloZGame();
		} else {
			new IllegalStateException("SkywarsResolver no fué especificado con un modo correcto y el sistema no sabe cómo continuar: " + gSkywarsResolver.getListArgs());
			stop();
		}
		
		System.out.println("Continuando...");
		
		/*
		 * Eventos
		 */
		Skywars.getInstance().getServer().getPluginManager().registerEvents(new SoloPlayerBattleListener(), Skywars.getInstance());
		Skywars.getInstance().getServer().getPluginManager().registerEvents(new SoloPlayerJoinListener(), Skywars.getInstance());
		Skywars.getInstance().getServer().getPluginManager().registerEvents(new SoloPlayerQuitListener(), Skywars.getInstance());
		Skywars.getInstance().getServer().getPluginManager().registerEvents(new SoloPlayerCustomProtocols(), Skywars.getInstance());
		
		/*
		 * Mapa
		 */
		MapManager.prepareNextWorld(gMatchType);
		
		/*
		 * Localizaciones
		 */
		
		List<Location> scannedBlocks = Scan.oneMaterial(Material.SPONGE);
		for(Location loc : scannedBlocks) {
			Block bl = loc.getBlock();
			if(bl.getRelative(0,1,0).getType() == Material.WOOD_PLATE) {
				cagesLocations.add(loc);
				bl.getRelative(0,1,0).setType(Material.AIR);
				bl.setType(Material.AIR);
			}
		}
		
		/*
		 * Scoreboard
		 */
		SoloPlayerScoreboardManager.initialize();
		
	}
	
	private void startSoloNormalGame() {
		gMatchType = MatchType.NORMAL;
		
		/*
		 * SYSTEM VARS (EVENTS)
		 * Only for the game with these specs:
		 *   - Solo
		 *   - Normal
		 */
		
		mainRunnableTask.addEvent("&6&lRELLENADO:", 135);
		mainRunnableTask.addEvent("&6&lRELLENADO:", 255);
		mainRunnableTask.addEvent("&8&lELECCIÓN:", 480);
		
	}
	
	private void startSoloInsaneGame() {
		gMatchType = MatchType.INSANE;
		
		/*
		 * SYSTEM VARS (EVENTS)
		 * Only for the game with these specs:
		 *   - Solo
		 *   - Mejorado (Insano)
		 */
		
		mainRunnableTask.addEvent("&6&lRELLENADO:", 135);
		mainRunnableTask.addEvent("&6&lRELLENADO:", 255);
		mainRunnableTask.addEvent("&c&lDUELO:", 460);
		mainRunnableTask.addEvent("&8&lELECCIÓN:", 510);
		
	}
	
	private void startSoloZGame() {
		gMatchType = MatchType.Z;
		
		/*
		 * SYSTEM VARS (EVENTS)
		 * Only for the game with these specs:
		 *   - Solo
		 *   - Z
		 */
		
		mainRunnableTask.addEvent("&d&lDESTRUCCIÓN:", 60);
		mainRunnableTask.addEvent("&6&lRELLENADO:", 120);
		mainRunnableTask.addEvent("&6&lRELLENADO:", 240);
		mainRunnableTask.addEvent("&4&lAPOCALIPSIS:", 350);
		mainRunnableTask.addEvent("&8&lELECCIÓN:", 435);
		
	}
	
	public static MatchType getCurrentMatchType() {
		return gMatchType;
	}
	
	public void finalize(Player winner) {
		
		final Map<PlayerBattleInfo, Integer> cache_top = PlayerBattleInfoUtils.getTop(SoloPlayerBattleListener.battle_info);
		final Map<Integer, PlayerBattleInfo> top = PlayerBattleInfoUtils.reverse(cache_top);
		
		final PlayerBattleInfo TOP_1 = top.get(1);
		final PlayerBattleInfo TOP_2 = top.get(2);
		final PlayerBattleInfo TOP_3 = top.get(3);
		
		boolean win = winner != null;
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.playSound(p.getLocation(), Sound.LEVEL_UP, 10, -10);
			p.playSound(p.getLocation(), Sound.LEVEL_UP, 10, -10);
		}
		
		new BukkitRunnable() {
				
			Location fix_loc = lobbyschematic.getLocation().getWorld().getHighestBlockAt(lobbyschematic.getLocation()).getLocation();
			int launched = 0;
			
			@Override
			public void run() {
				if(launched < 12) {
					if(win) {
						if(winner.isOnline()) {
							for(int i = 0; i < 5; i++) {
								RandomFirework.spawnRandomFirework(LocationUtil.getRandomLocation(winner), 3);
							}
							launched++;
							return;
						}
					}
					
					for(int i = 0; i < 5; i++) {
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
				
				for(Player p : Bukkit.getOnlinePlayers()) {
					p.playSound(p.getLocation(), Sound.CLICK, 10, -10);
					p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 10, -10);
				}
				
				if(win) {
					
					Bukkit.broadcastMessage(TextUtil.format("&r"));
					Bukkit.broadcastMessage(TextUtil.format("&r"));
					Bukkit.broadcastMessage(TextUtil.format("&7&l» &7Tabla de Posiciones/Scores&8&l:"));
					Bukkit.broadcastMessage(TextUtil.format("&r"));
					Bukkit.broadcastMessage(TextUtil.getCenteredMessage("&r         &a&lGANADOR&r &a&l&m-&r &7Desconocido"));
					Bukkit.broadcastMessage(TextUtil.getCenteredMessage("&r"));
					Bukkit.broadcastMessage(TOP_1.getTopTierMessage(1));
					Bukkit.broadcastMessage(TOP_2.getTopTierMessage(2));
					Bukkit.broadcastMessage(TOP_3.getTopTierMessage(3));
					Bukkit.broadcastMessage(TextUtil.format("&r"));
					for(Map.Entry<PlayerBattleInfo, Integer> k : cache_top.entrySet()) {
						
						if(!k.getKey().unknow) {
							Player p = k.getKey().player;
							if(p.isOnline()) {
								
								int top = k.getKey().getTopNumber(cache_top);
								p.sendMessage(TextUtil.getCenteredMessage("&7Tu pocisión fue &a&l" + top + " &7en esta partida con un promedio de &9&l" + k.getKey().getAverage() + "&r"));
								
							}
						}
						
					}
					for(Player p : SoloPlayerManager.getPlayersInSpectator()) {
						boolean contains = false;
						for(Map.Entry<PlayerBattleInfo, Integer> k : cache_top.entrySet()) {
							if(!k.getKey().unknow) {
								if(k.getKey().player != null) {
									if(p == k.getKey().player) {
										contains = true;
										break;
									}
								}
							}
						}
						if(!contains) {
							p.sendMessage(TextUtil.getCenteredMessage("&7El promedio del jugador &c&l#1&r &7fue de &9&l" + TOP_1.getAverage()));
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
					for(Map.Entry<PlayerBattleInfo, Integer> k : cache_top.entrySet()) {
						
						if(!k.getKey().unknow) {
							Player p = k.getKey().player;
							if(p.isOnline()) {
								
								int top = k.getKey().getTopNumber(cache_top);
								p.sendMessage(TextUtil.getCenteredMessage("&7Tu pocisión fue &a&l" + top + " &7en esta partida con un promedio de &9&l" + k.getKey().getAverage() + "&r"));
								
							}
						}
						
					}
					for(Player p : SoloPlayerManager.getPlayersInSpectator()) {
						boolean contains = false;
						for(Map.Entry<PlayerBattleInfo, Integer> k : cache_top.entrySet()) {
							if(!k.getKey().unknow) {
								if(k.getKey().player != null) {
									if(p == k.getKey().player) {
										contains = true;
										break;
									}
								}
							}
						}
						if(!contains) {
							p.sendMessage(TextUtil.getCenteredMessage("&7El promedio del jugador &c&l#1&r &7fue de &9&l" + TOP_1.getAverage()));
						}
					}
					Bukkit.broadcastMessage(TextUtil.format("&r"));
				}
			}
		}.runTaskLater(Skywars.getInstance(), 60L);
		
		new BukkitRunnable() {
			@Override
			public void run() {
				
				for(Player p : Bukkit.getOnlinePlayers()) {
					p.playSound(p.getLocation(), Sound.CLICK, 10, -10);
					p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 10, -10);
				}
				
				for(Map.Entry<PlayerBattleInfo, Integer> k : cache_top.entrySet()) {
					
					if(!k.getKey().unknow) {
						if(k.getKey().player != null) {
							
							Player p = k.getKey().player;
							
							if(p.isOnline()) {
							
								p.sendMessage(TextUtil.format("&r"));
								p.sendMessage(TextUtil.format("&r"));
								p.sendMessage(TextUtil.format("&7&l» &7Ganancias/Premios&8&l:"));
								p.sendMessage(TextUtil.getCenteredMessage("&r"));
								p.sendMessage(TextUtil.getCenteredMessage(" Has ganado un total de &8&l(&3&l+&3" + k.getKey().getExp() + " &3&lExp&8&l)"));
								p.sendMessage(TextUtil.getCenteredMessage(" Has ganado un total de &8&l(&a&l+&a" + k.getKey().getMoney() + " &a&lCoins&8&l)"));
								if(k.getKey().isFirstBlood()) {
									p.sendMessage(TextUtil.getCenteredMessage(" Premio por primera muerte &8&l(&a&l+&a20 &a&lCoins&8&l) &8&l(&3&l+&316 &3&lExp&8&l)"));
								}
								if(k.getKey().survival) {
									p.sendMessage(TextUtil.getCenteredMessage(" Premio por supervivencia &8&l(&a&l+&a15 &a&lCoins)"));
								}
								p.sendMessage(TextUtil.format("&r"));
								if(NetworkData.generalbooster) {
									p.sendMessage(TextUtil.getCenteredMessage(" &9&lTOTAL: &8&l+&a" + k.getKey().getTotalMoney() + " Coins &6&lX2    &8&l+&3" + k.getKey().getTotalExp() + " Exp &6&lX2"));
									p.sendMessage(TextUtil.format("&r"));
									p.sendMessage(TextUtil.getCenteredMessage("&e¡Network Booster de Unknow activado! &6&lX2!"));
								} else {
									p.sendMessage(TextUtil.getCenteredMessage(" &9&lTOTAL: &8&l+&a" + k.getKey().getTotalMoney() + " Coins    &8&l+&3" + k.getKey().getTotalExp() + " Exp"));
									p.sendMessage(TextUtil.format("&r"));
								}
								
							}
							
						}
					}
					
					
				}
				
				
			}
		}.runTaskLater(Skywars.getInstance(), 200L);
		
		new BukkitRunnable() {
			@Override
			public void run() {
				
				for(Player p : Bukkit.getOnlinePlayers()) {
					p.playSound(p.getLocation(), Sound.HORSE_SADDLE, 10, -10);
				}
				
				for(Map.Entry<PlayerBattleInfo, Integer> k : cache_top.entrySet()) {
					
					if(!k.getKey().unknow) {
						if(k.getKey().player != null) {
							
							Player p = k.getKey().player;
							
							if(p.isOnline()) {
							
								p.sendMessage(TextUtil.format("&r"));
								p.sendMessage(TextUtil.format("&r"));
								p.sendMessage(TextUtil.format("&7&l» &7Estadisticas Generales/Stats&8&l:"));
								p.sendMessage(TextUtil.getCenteredMessage("&r"));
								p.sendMessage(TextUtil.getCenteredMessage(" &7Servidor: &2&l" + Bukkit.getServerName()));
								p.sendMessage(TextUtil.getCenteredMessage(" &7Mapa: &6&l" + lobbyschematic.getLocation().getWorld().getName()));
								p.sendMessage(TextUtil.getCenteredMessage("&r"));
								
								if(AccountManager.SAVED_ACCOUNTS.containsKey(p)) {
									
									p.sendMessage(TextUtil.getCenteredMessage(" &7Kills Totales: &c" + (AccountManager.SAVED_ACCOUNTS.get(p).getKills() + k.getKey().getKills())));
									p.sendMessage(TextUtil.getCenteredMessage(" &7Asistencias Totales: &b" + (AccountManager.SAVED_ACCOUNTS.get(p).getAssistences() + k.getKey().getAssistences())));
									p.sendMessage(TextUtil.getCenteredMessage(" &7Promedio Total: &9&l" + k.getKey().getAverage())); // TODO Terminar xd
									p.sendMessage(TextUtil.getCenteredMessage("&r"));
									
								}
								
								p.sendMessage(TextUtil.getCenteredMessage(" &7¿Te gustó el mapa?   &a&nSi&r    &8&m--&r    &c&nNo"));
								
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
		gMatchType = MatchType.NONE;
		
		if(mainRunnableTask != null) {
			mainRunnableTask.cancel();
			mainRunnableTask = null;
		}

		cagesLocations.clear();
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.kickPlayer("El mapa se esta reiniciando");
		}
		
		MapManager.unloadWorldAndPrepareForNextRequest();
		
		Skywars.makeTestMatch();
	}

	@Override
	public void stop() {
		gMatchType = MatchType.NONE;
		reset();
		
	}
	
	public List<Location> getCageLocations() {
		return cagesLocations;
	}

}