package net.omniblock.skywars.games.solo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.collect.Lists;

import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.SkywarsGameState;
import net.omniblock.skywars.games.solo.chest.ChestManager;
import net.omniblock.skywars.games.solo.chest.item.ItemInsane;
import net.omniblock.skywars.games.solo.chest.item.ItemNormal;
import net.omniblock.skywars.games.solo.chest.item.ItemZ;
import net.omniblock.skywars.games.solo.chest.item.object.AddChest;
import net.omniblock.skywars.games.solo.events.SoloPlayerBattleListener;
import net.omniblock.skywars.games.solo.events.SoloPlayerCustomProtocols;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;
import net.omniblock.skywars.games.solo.types.MatchType;
import net.omniblock.skywars.network.NetworkData;
import net.omniblock.skywars.patch.managers.CageManager;
import net.omniblock.skywars.patch.managers.CageManager.CageZCameraUtil;
import net.omniblock.skywars.patch.types.SkywarsType;
import net.omniblock.skywars.util.ActionBarApi;
import net.omniblock.skywars.util.MapUtils;
import net.omniblock.skywars.util.ApocalipsisUtil.Apocalipsis;
import net.omniblock.skywars.util.ContaminationUtil.Contamination;
import net.omniblock.skywars.util.ContaminationUtil.ContaminationInfo;
import net.omniblock.skywars.util.ContaminationUtil.ContaminationTroop;
import net.omniblock.skywars.util.DestructionUtil.Destruction;
import net.omniblock.skywars.util.DestructionUtil.DestructionInfo;
import net.omniblock.skywars.util.SoundPlayer;
import net.omniblock.skywars.util.TitleUtil;
import net.omniblock.skywars.util.TitleUtil.TitleFormat;
import omniblock.on.util.TextUtil;
import omniblock.ot.errorapi.ErrorAPI;

public class SoloSkywarsRunnable extends BukkitRunnable {
	
	private static SoloSkywars gStarter = null;
	
	public static int MIN_PLAYERS_TO_START = 1;
	public static Map<String, Integer> EVENTS = new HashMap<String, Integer>();
	
	private final int timeLobby = 15;
	private int remainingTimeLobby = timeLobby;
	
	private boolean lobbyStarting = false;
	private boolean pregameTitles = false;
	
	private int remainingTimeCages = 5;
	
	private ChestManager chestmanager;
	
	public SoloSkywarsRunnable(SoloSkywars starter) {
		
		gStarter = starter;
		
	}
	
	@Override
	public void run() {
		if(Skywars.getGameState() == SkywarsGameState.IN_LOBBY) {
			if(SoloPlayerManager.getPlayersInLobbyAmount() >= MIN_PLAYERS_TO_START){
				
				if(remainingTimeLobby <= 0) {
					
					NetworkData.broadcaster.read("$ LOCK");
					
					chestmanager = new ChestManager();
					SoloSkywars.lobbyschematic.removePasted();
					
					SoloPlayerManager.transferAllPlayersToInGame();
					SoloPlayerManager.sendAllPlayersToCages(gStarter);
					
					Skywars.updateGameState(SkywarsGameState.IN_PRE_GAME);
					
					sendPreGameTitles(getGameTitle(SoloSkywars.getCurrentMatchType()));
					
				}
				
				if(!lobbyStarting){
					lobbyStarting = true;
				}
				
				if(remainingTimeLobby == timeLobby || remainingTimeLobby == (timeLobby / 2) || remainingTimeLobby == 10 || remainingTimeLobby <= 5 && remainingTimeLobby > 0) {
					Bukkit.broadcastMessage(TextUtil.format(" &8&l» &7La partida inicia en &a" + remainingTimeLobby + "&7 segundos."));
					SoloPlayerManager.playSound(Sound.CLICK, 1, 15);
				}
				
				for(Player p : SoloPlayerManager.getPlayersInLobbyList()){
					
					if(remainingTimeLobby != 0){
						
						p.setLevel(remainingTimeLobby);
						p.setTotalExperience((remainingTimeLobby * 100) / 100);
						
						ActionBarApi.sendActionBar(p, TextUtil.format("&8&m&l-[&c&m&l--&r&7 La partida inicia en &c&l" + remainingTimeLobby + " &r&7segundos &c&m&l--&8&m&l]-"));
						
					} else {
						
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 2, -1);
						p.sendMessage(TextUtil.format(" &8&l» &7La partida ha iniciado!"));
						
						ActionBarApi.sendActionBar(p, TextUtil.format("&8&m&l-[&c&m&l--&r&7 ¡La partida ha iniciado!  &c&m&l--&8&m&l]-"));
						
					}
					
				}
				
				remainingTimeLobby--;
				
			} else {
				
				if(lobbyStarting){
					
					lobbyStarting = false;
					remainingTimeLobby = timeLobby;
					
				}
				
				for(Player p : SoloPlayerManager.getPlayersInLobbyList()){
					ActionBarApi.sendActionBar(p, TextUtil.format("&8&m&l-[&c&m&l--&r&7 Esperando a más jugadores  &c&m&l--&8&m&l]-"));
				}

			}
			
		} else if(Skywars.getGameState() == SkywarsGameState.IN_PRE_GAME) {
			
			if(pregameTitles == true){
				
				for(Player p : SoloPlayerManager.getPlayersInGameList()) {
					SoloPlayerManager.playSound(Sound.CLICK, 1, 10);
					TitleUtil.sendTitleToPlayer(p, 0, 22, 0, "", TextUtil.format("&c&l" + String.valueOf(remainingTimeCages)));
				}
				
				remainingTimeCages--;
				
				for(Player p : SoloPlayerManager.getPlayersInGameList()) {
					SoloPlayerManager.playSound(Sound.CLICK, 1, 10);
					TitleUtil.sendTitleToPlayer(p, 0, 22, 0, "", TextUtil.format("&c&l" + String.valueOf(remainingTimeCages)));
				}
				
				if(remainingTimeCages < 0) {
					
					for(Player p : SoloPlayerManager.getPlayersInGameList()) {
						
						SoloPlayerCustomProtocols.PROTECTED_PLAYER_LIST.add(p);
						
						new BukkitRunnable() {
							
							public int seconds = 10;
							
							@SuppressWarnings("deprecation")
							@Override
							public void run() {
							
								seconds--;
								
								if(seconds <= 0) {
									cancel();
									if(SoloPlayerCustomProtocols.PROTECTED_PLAYER_LIST.contains(p)) {
										SoloPlayerCustomProtocols.PROTECTED_PLAYER_LIST.remove(p);
									}
									return;
								}
								
								if(!p.isOnline()) {
									cancel();
									if(SoloPlayerCustomProtocols.PROTECTED_PLAYER_LIST.contains(p)) {
										SoloPlayerCustomProtocols.PROTECTED_PLAYER_LIST.remove(p);
									}
									return;
								}
								
								if(p.getLastDamageCause().getCause() == DamageCause.VOID) {
									cancel();
									if(SoloPlayerCustomProtocols.PROTECTED_PLAYER_LIST.contains(p)) {
										SoloPlayerCustomProtocols.PROTECTED_PLAYER_LIST.remove(p);
									}
									return;
								}
								
								if(p.isOnGround()) {
									cancel();
									if(SoloPlayerCustomProtocols.PROTECTED_PLAYER_LIST.contains(p)) {
										SoloPlayerCustomProtocols.PROTECTED_PLAYER_LIST.remove(p);
									}
									return;
								}
								
							}
							
						}.runTaskTimer(Skywars.getInstance(), 0l, 20l);
						
						SoloPlayerManager.playSound(Sound.LEVEL_UP, 1F, 1F);
						TitleUtil.sendTitleToPlayer(p, 0, 22, 0, "", TextUtil.format("&c&l¡A PELEAR!"));
						
					}
					
					SoloPlayerBattleListener.setBattleInfo();
					
					CageManager.removeCages();
					
					if(Skywars.currentMatchType == SkywarsType.SW_Z_SOLO) {
						for(Player p : SoloPlayerManager.getPlayersInGameList()) {
							if(CageManager.cagesdata.containsKey(p)) {
								CageZCameraUtil.makeElevation(p, CageManager.cagesdata.get(p));
								continue;
							} else {
								SoloPlayerManager.deathPlayer(p);
								continue;
							}
						}
					}
					
					Skywars.updateGameState(SkywarsGameState.IN_GAME);
					
				}
				
			}
			
		} else if(Skywars.getGameState() == SkywarsGameState.IN_GAME) {
			
			Map<String, Integer> events = MapUtils.clone(EVENTS);
			for(Map.Entry<String, Integer> k : events.entrySet()) {
				try {
					reduceEvent(k.getKey(), 1);
				}catch(Exception e) {
					ErrorAPI.sendError(e);
				}
			}
			
			if(SoloPlayerManager.getPlayersInGameAmount() == 1) {
				
				Skywars.updateGameState(SkywarsGameState.FINISHING);
				gStarter.finalize(SoloPlayerManager.getPlayersInGameList().get(0));
				
			} else if(SoloPlayerManager.getPlayersInGameAmount() <= 0) {
				
				Skywars.updateGameState(SkywarsGameState.FINISHING);
				gStarter.finalize(null);
				
			}
			
		}
	}

	public PreGameTitles getGameTitle(MatchType matchtype){
		switch(matchtype){
			case INSANE:
				return PreGameTitles.INSANE_TITLES;
			case NONE:
				return PreGameTitles.NORMAL_TITLES;
			case NORMAL:
				return PreGameTitles.NORMAL_TITLES;
			case Z:
				return PreGameTitles.Z_TITLES;
			default:
				break;
		}
		return PreGameTitles.NORMAL_TITLES;
	}
	
	public void sendInGameTitle(String str) {
		
		if(str.contains("RELLENADO")) {
			
			sendInGameTitle(InGameTitles.REFILL_TITLE);
			
			switch (SoloSkywars.getCurrentMatchType()) {
			
				case NORMAL:
					
					AddChest.makeReFill(ItemNormal.trappedChest(), 12);
					break;
					
				case INSANE:
					
					AddChest.makeReFill(ItemInsane.trappedChest(), 12);
					break;
					
				case Z:
					
					AddChest.makeReFill(ItemZ.trappedChest(), 12);
					break;
					
				default:
					break;
			
			}
			
			return;
			
		} else if(str.contains("CONTAMINACIÓN")) {

			List<Location> chestLocations = Lists.newArrayList();
			chestLocations.addAll(ChestManager.normalchest);
			chestLocations.addAll(ChestManager.trappedchest);
			
			for(Location l : chestLocations) {
				
				ContaminationInfo ci = new ContaminationInfo(l.getWorld().getHighestBlockAt(l), l.getWorld());
				
				Contamination contamination = new Contamination();
				contamination.setContaminationDefaults(ci);
				contamination.runTaskTimer(Skywars.getInstance(), 10L, 10L);
				
			}
			
			ContaminationTroop contaminationtroop = new ContaminationTroop();
			contaminationtroop.startTroop();
			contaminationtroop.runTaskTimer(Skywars.getInstance(), 10L, 10L);
			
			sendInGameTitle(InGameTitles.CONTAMINATION_TITLE);
			return;
			
		} else if(str.contains("DUELO")) {
			
			sendInGameTitle(InGameTitles.DUEL_TITLE);
			return;
			
		} else if(str.contains("APOCALIPSIS")) {
			
			sendInGameTitle(InGameTitles.APOCALYPSE_TITLE);
			
			Apocalipsis apocalipsis = new Apocalipsis();
			apocalipsis.runTaskTimer(Skywars.getInstance(), 1L, 1L);
			
			for(Player p : apocalipsis.getInGamePlayers()) {
				
				p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20* 7, 2, true), true);
				p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 10, 2, true), true);
				p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 10, 2, true), true);
				
				DestructionInfo di = new DestructionInfo(p.getLocation().getBlock(), p.getWorld());
				
				Destruction destruction = new Destruction();
				destruction.setDestructionDefaults(di, 1000);
				destruction.runTaskTimer(Skywars.getInstance(), 2L, 2L);
				
			}
			
			SoundPlayer.sendSound(apocalipsis.getLobbySchematicLoc(), "skywars.apocalipsis_intro", 5000);
			new BukkitRunnable() {
				@Override
				public void run() {
					
					if(Skywars.getGameState() == SkywarsGameState.IN_GAME) {
						SoundPlayer.sendSound(apocalipsis.getLobbySchematicLoc(), "skywars.apocalipsis_song", 1000000);
					}
					
				}
			}.runTaskLater(Skywars.getInstance(), 7 * 20);
			
			return;
			
		} else if(str.contains("DESTRUCCIÓN")) {
			
			int delay_another = 1;
			
			for(Block b : AddChest.ChestDiamond) {
				
				new BukkitRunnable() {
					@Override
					public void run() {
						
						DestructionInfo di = new DestructionInfo(b, b.getWorld());
						
						Destruction destruction = new Destruction();
						destruction.setDestructionDefaults(di);
						destruction.runTaskTimer(Skywars.getInstance(), 2L, 2L);
						
					}
				}.runTaskLater(Skywars.getInstance(), 20 * delay_another);
				
				delay_another++;
				
			}
			
			sendInGameTitle(InGameTitles.DESTRUCTION_TITLE);
			return;
			
		}
		
		return;
		
	}
	
	public void sendInGameTitle(InGameTitles ingametitle) {
		
		/**
		 * @Titles
		 */
		for(TitleFormat title : ingametitle.getTitles()){
			for(Player p : SoloPlayerManager.getPlayersInGameList()){
				title.send(p);
			}
		}
		
		/**
		 * @Sounds
		 */
		if(ingametitle.getSound() != null) {
			for(Player p : SoloPlayerManager.getPlayersInGameList()){
				p.playSound(p.getLocation(), ingametitle.getSound(), 5, -5);
			}
		}
		
	}
	
	public void sendPreGameTitles(PreGameTitles pregametitles){
		
		new BukkitRunnable(){
			@Override
			public void run(){
				
				pregameTitles = true;
				
			}
		}.runTaskLater(Skywars.getInstance(), (long) pregametitles.getDelay());
		
		if(pregametitles.getTitles().length >= 2){
			
			long cachedelay = 0;
			
			for(TitleFormat title : pregametitles.getTitles()){
				
				new BukkitRunnable(){
					@Override
					public void run(){
						for(Player p : SoloPlayerManager.getPlayersInGameList()){
							title.send(p);
						}
					}
				}.runTaskLater(Skywars.getInstance(), cachedelay);
				
				cachedelay = cachedelay + title.getFadeIn() + title.getFadeOut() + title.getStay();
				
			}
			
		} else if(pregametitles.getTitles().length == 1){
			
			TitleFormat title = pregametitles.getTitles()[0];
			
			for(Player p : SoloPlayerManager.getPlayersInGameList()){
				title.send(p);
			}
			
		}
		
	}
	
	public ChestManager getChestmanager() {
		return chestmanager;
	}

	public void setChestmanager(ChestManager chestmanager) {
		this.chestmanager = chestmanager;
	}

	public void reduceEvent(String event, int seconds) {
		if(EVENTS.containsKey(event)) {
			int remain = EVENTS.get(event) - seconds;
			if(remain <= 0) {
				sendInGameTitle(event);
				EVENTS.remove(event);
				return;
			}
			EVENTS.put(event, remain);
			return;
		}
	}
	
	public void addEvent(String event, int seconds){
		EVENTS.put(event, seconds);
	}
	
	public void removeEvent(String event){
		if(EVENTS.containsKey(event)){
			EVENTS.remove(event);
		}
	}
	
	public Map<String, Integer> getEvents() {
		return EVENTS;
	}

	public enum InGameTitles {
		REFILL_TITLE(65, Sound.CHEST_OPEN, new TitleFormat[] { new TitleFormat(5, 45, 5, TextUtil.format("&6&lCofres"),
															  TextUtil.format("&7¡Rellenado Completado!")) } ),
		DUEL_TITLE(65, Sound.BAT_TAKEOFF, new TitleFormat[] { new TitleFormat(5, 45, 5, TextUtil.format("&4&k|| &r&c&lDuelo&r&4&k ||"),
				  											  TextUtil.format("&7¡El duelo ha iniciado!")) } ),
		CONTAMINATION_TITLE(65, Sound.ZOMBIE_REMEDY, new TitleFormat[] { new TitleFormat(5, 45, 5, TextUtil.format("&d&lContaminación"),
				  											  TextUtil.format("&7¡Se está esparciendo el virus!")) } ),
		DESTRUCTION_TITLE(65, Sound.WITHER_SPAWN, new TitleFormat[] { new TitleFormat(5, 45, 5, TextUtil.format("&4&k|| &r&c&lDestrucción&r&4&k ||"),
				  									   	      TextUtil.format("&7¡Ha iniciado la destrucción!")) } ),
		APOCALYPSE_TITLE(65, Sound.ENDERDRAGON_GROWL, new TitleFormat[] { new TitleFormat(5, 45, 5, TextUtil.format("&4&k|| &r&c&lApocalipsis&r&4&k ||"),
		   	      											  TextUtil.format("&7¡El apocalipsis ha empezado!")) } ),
		
		;
		
		
		private float delay;
		
		private TitleFormat[] titles;
		private Sound sound = null;
		
		InGameTitles(float delay, Sound sound, TitleFormat...titles){
			this.setDelay(delay);
			this.setTitles(titles);
			this.setSound(sound);
		}

		public TitleFormat[] getTitles() {
			return titles;
		}

		public void setTitles(TitleFormat[] titles) {
			this.titles = titles;
		}

		public float getDelay() {
			return delay;
		}

		public void setDelay(float delay) {
			this.delay = delay;
		}

		public Sound getSound() {
			return sound;
		}

		public void setSound(Sound sound) {
			this.sound = sound;
		}
		
	}
	
	public enum PreGameTitles {
		NORMAL_TITLES(65, new TitleFormat[] { new TitleFormat(5, 45, 5, TextUtil.format("&6&lSkyWars"),
															  TextUtil.format("&7Modo Normal")) } ),
				
		INSANE_TITLES(65, new TitleFormat[] { new TitleFormat(5, 45, 5, TextUtil.format("&6&lSkyWars"),
										   					  TextUtil.format("&7Modo Mejorado")) } ),
		Z_TITLES(117, new TitleFormat[] { new TitleFormat(5, 50, 0, TextUtil.format("&8&lSkyWars"),
				   					 					  TextUtil.format("&7Modo &4&lZ")),
					 					  new TitleFormat(0, 8, 0, "",
					 							 		  TextUtil.format("&aCargando mapa...")),
					 					  new TitleFormat(0, 8, 0, "",
					 							 		  TextUtil.format("&eReviviendo guardianes...")),
					 					  new TitleFormat(0, 8, 0, "",
					 							 		  TextUtil.format("&dPuliendo islas...")),
					 					  new TitleFormat(0, 8, 0, "",
					 							 		  TextUtil.format("&cCargando items...")),
					 					  new TitleFormat(0, 8, 0, "",
					 							 		  TextUtil.format("&bCargando core...")),
					 					  new TitleFormat(0, 8, 0, "",
					 							 		  TextUtil.format("&2Añadiendo cofres...")),
					 					 new TitleFormat(0, 8, 0, "",
			 							 		  		  TextUtil.format("&9Añadiendo a Herobrine...")),
					 					  new TitleFormat(0, 8, 5, "",
					 							 	 	  TextUtil.format("&4Preparando impulso...")) } );
		
		
		private float delay;
		private TitleFormat[] titles;
		
		PreGameTitles(float delay, TitleFormat...titles){
			this.setDelay(delay);
			this.setTitles(titles);
		}

		public TitleFormat[] getTitles() {
			return titles;
		}

		public void setTitles(TitleFormat[] titles) {
			this.titles = titles;
		}

		public float getDelay() {
			return delay;
		}

		public void setDelay(float delay) {
			this.delay = delay;
		}
		
	}
}
