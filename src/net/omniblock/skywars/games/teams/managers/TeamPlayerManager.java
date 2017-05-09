package net.omniblock.skywars.games.teams.managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.collect.Lists;

import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.SkywarsGameState;
import net.omniblock.skywars.games.teams.TeamSkywars;
import net.omniblock.skywars.patch.managers.CageManager;
import net.omniblock.skywars.patch.managers.CageManager.CageType;
import net.omniblock.skywars.patch.managers.chest.ChestManager;
import net.omniblock.skywars.patch.managers.lobby.LobbyManager;
import net.omniblock.skywars.patch.types.MatchType;
import net.omniblock.skywars.patch.managers.MapManager;
import net.omniblock.skywars.patch.managers.SpectatorManager;
import net.omniblock.skywars.util.MapUtils;
import net.omniblock.skywars.util.NumberUtil;
import net.omniblock.skywars.util.TextUtil;
import net.omniblock.skywars.util.TitleUtil;
import omniblock.on.addons.games.general.RankBase;
import omniblock.on.addons.games.lobby.adapter.skywars.SkywarsBase;
import omniblock.on.addons.games.lobby.adapter.skywars.SkywarsBase.SelectedItemType;
import omniblock.on.network.packet.Packet;
import omniblock.on.network.packet.assembler.AssemblyType;
import omniblock.on.network.packet.modifier.PacketModifier;

public class TeamPlayerManager {
	
	private static Map<Player, Player> playersTeams = new HashMap<Player, Player>();
	
	private static List<Player> playersInLobby = new ArrayList<Player>();
	private static List<Player> playersInGame = new ArrayList<Player>();
	private static List<Player> playersInSpectator = new ArrayList<Player>();
	
	public static void deathPlayer(Player p) {
		
		if(getPlayersInGameList().contains(p)) {
			playersInGame.remove(p);
		}
		
		playersInSpectator.add(p);
		
		InGameTitles.DEATH.send(p);
		spectatorPlayer(p);
		
	}
	
	public static void winnerTeam(Player p) {
		
		if(playersTeams.containsKey(p)) {
			
			Player team = getPlayerTeam(p);
			
			if(team != null) {
				emptyPlayer(team);
				healPlayer(team);
				forceFly(team);
			}
			
		}
		
		emptyPlayer(p);
		healPlayer(p);
		forceFly(p);
		
		return;
		
	}
	
	public static void spectatorPlayer(Player p) {
		
		emptyPlayer(p);
		healPlayer(p);
		forceFly(p);
		
		if(TeamPlayerManager.getPlayersInGameAmount() >= 1) {
			p.teleport(TeamPlayerManager.getPlayersInGameList().get(0));
		} else {
			p.teleport(MapManager.lobbyschematic.getLocation());
		}
		
		SpectatorManager.addPlayerToSpectator(p);
		return;
		
	}
	
	public static void healPlayer(Player p) {
		
		p.setExp(0);
		p.setTotalExperience(0);
		
		p.setFoodLevel(25);
		
		p.setHealth(20.0);
		p.setMaxHealth(20.0);
		
		for(PotionEffect pe : p.getActivePotionEffects()) {
			p.removePotionEffect(pe.getType());
		}
		
	}
	
	public static void emptyPlayer(Player p) {
		
		p.getInventory().clear();
		
		p.getInventory().setHelmet(new ItemStack(Material.AIR));
		p.getInventory().setChestplate(new ItemStack(Material.AIR));
		p.getInventory().setLeggings(new ItemStack(Material.AIR));
		p.getInventory().setBoots(new ItemStack(Material.AIR));
		
		p.setExp(0);
		p.setLevel(0);
		return;
		
	}
	
	public static boolean isTeamWin(){
		
		if(getPlayersInGameAmount() == 2){
			
			Player player = getPlayersInGameList().get(0);
			Player team = getPlayersInGameList().get(1);
			
			if(isTeam(player, team)){
				return true;
			}
			
		} else if(getPlayersInGameAmount() == 1){
			
			return true;
			
		}
		
		return false;
		
	}
	
	public static void forceFly(Player p) {
		
		new BukkitRunnable() {
			@Override
			public void run() {
				if(!p.isFlying()) {
					p.setAllowFlight(true);
					p.setFlying(true);
				} else {
					cancel();
				}
			}
		}.runTaskTimer(Skywars.getInstance(), 5L, 5L);
		
	}
	
	public static void forceRemoveFly(Player p) {
		
		new BukkitRunnable() {
			@Override
			public void run() {
				if(p.isFlying()) {
					p.setAllowFlight(false);
					p.setFlying(false);
				} else {
					cancel();
				}
			}
		}.runTaskTimer(Skywars.getInstance(), 5L, 5L);
		
	}
	
	public static boolean addPlayer(Player p) {
		
		if(Skywars.getGameState() == SkywarsGameState.IN_LOBBY) {
			
			Bukkit.broadcastMessage(TextUtil.format("&8&lS&8istema &9&l» &7El jugador &a" + RankBase.getRank(p).getCustomName(p) + "&7 ha ingresado a la partida. (" + (TeamPlayerManager.getPlayersInLobbyAmount() + 1) + "/" + (TeamSkywars.cagesLocations.size() * 2) + ")"));
			
			for(Player p2 : getPlayersInLobbyListAsCopy()) {
				if(p.getUniqueId().equals(p2.getUniqueId())) {
					removePlayer(p);
				}
			}
			
			emptyPlayer(p);
			healPlayer(p);
			
			p.spigot().setCollidesWithEntities(true);
			p.teleport(MapManager.lobbyschematic.getLocation().clone().add(0.5, 5, 0.5));
			p.playSound(p.getLocation(), Sound.CLICK, 10, -10);
			
			p.setGameMode(GameMode.ADVENTURE);
			
			LobbyManager.giveItems(p);
			SkywarsBase.saveAccount(p);
			
			playersInLobby.add(p);
			
		} else {
			
			spectatorPlayer(p);
			
		}
		
		if(ChestManager.getCurrentMatchType() == MatchType.Z){
			
			new BukkitRunnable(){
				@Override
				public void run(){
					
					Packet.ASSEMBLER.sendPacket(AssemblyType.PLAYER_SEND_TEXTUREPACK, new PacketModifier()
							.addString(p.getName())
							.addString("SKWZ"));
					
				}
			}.runTaskLater(Skywars.getInstance(), 20 * 2);
			
		}
		
		return true;
	}
	
	public static boolean addTeam(Player p1, Player p2) {
			
		if(playersTeams.containsKey(p1)) {
			playersTeams.remove(p1);
		}
		
		if(playersTeams.containsKey(p2)) {
			playersTeams.remove(p2);
		}
		
		playersTeams.put(p1, p2);
		playersTeams.put(p2, p1);
		
		return true;
	}
	
	public static void fillTeams() {
		
		HashSet<Player> noteamed = new LinkedHashSet<Player>();
		HashSet<Player> cache = new LinkedHashSet<Player>();
		
		for(Map.Entry<Player, Player> k : playersTeams.entrySet()) {
			
			if(k.getValue() != null) {
				cache.add(k.getValue());
			}
			
			if(k.getKey() != null) {
				cache.add(k.getKey());
			}
			
		}
		
		for(Player p : playersInLobby) {
			
			if(!cache.contains(p)) {
				noteamed.add(p);
			}
			
		} cache.clear();
		
		Iterator<Player> iterate = noteamed.iterator();
		
		while(iterate.hasNext()) {
			
			Player p1 = iterate.next();
			
			if(iterate.hasNext()) {
				
				Player p2 = iterate.next();
				addTeam(p1, p2);
				continue;
				
			} else {
				
				playersTeams.put(p1, null);
				break;
				
			}
			
		}
		
		
	}
	
	public static boolean removeTeam(Player p) {
		
		if(playersTeams.containsKey(p)) {
			playersTeams.remove(p);
		}
		
		if(playersTeams.containsValue(p)) {
			
			for(Map.Entry<Player, Player> k : MapUtils.clone(playersTeams).entrySet()) {
				
				if(k.getValue() == null) continue;
				
				Player val = k.getValue();
				if(val == p) {
					playersTeams.put(k.getKey(), null);
					break;
				}
				
				continue;
				
			}
			
		}
		
		return true;
		
	}
	
	public static void removePlayer(Player p) {
		
		playersInLobby.remove(p);
		
		if(playersInGame.contains(p)) {
			playersInGame.remove(p);
		}
		
	}
	
	public static Player getPlayerTeam(Player p) {
		
		if(playersTeams.containsKey(p)) {
			return playersTeams.get(p);
		}
		
		return null;
	}
	
	public static boolean isTeam(Player p, Player p2) {
		
		if(playersTeams.containsKey(p)) {
			return playersTeams.get(p).equals(p2);
		}
		
		return false;
		
	}
	
	public static boolean hasTeam(Player p) {
		
		if(playersTeams.containsKey(p)) {
			return playersTeams.get(p) != null;
		}
		
		return false;
		
	}
	
	public static void transferAllPlayersToInGame() {
		
		fillTeams();
		
		playersInGame.addAll(playersInLobby);
		playersInLobby.clear();
		
	}
	
	public static int getPlayersInLobbyAmount() {
		return playersInLobby.size();
	}
	
	public static int getPlayersInGameAmount() {
		return playersInGame.size();
	}
	
	public static List<Player> getPlayersInLobbyList(){
		return playersInLobby;
	}
	
	public static List<Player> getPlayersInLobbyListAsCopy(){
		return new ArrayList<Player>(playersInLobby);
	}
	
	public static List<Player> getPlayersInGameList(){
		return playersInGame;
	}
	
	public static List<Player> getPlayersInGameListAsCopy(){
		return new ArrayList<Player>(playersInGame);
	}

	public static void sendAllPlayersToCages() {
		
		List<Player> processed_players = new ArrayList<Player>();
		
		List<Location> cageLocations = TeamSkywars.getCageLocations();
		Collections.shuffle(cageLocations);
		
		for(int i = 0; i < getPlayersInGameAmount(); i++) {
			
			boolean hasteam = false;
			
			Player player = playersInGame.get(i);
			Player team = playersTeams.get(player);
			
			if(processed_players.contains(player)) continue;
			
			if(team != null && team.isOnline()) {
				if(!processed_players.contains(team)) {
					hasteam = true;
				}
			}
			
			processed_players.add(player); if(hasteam) processed_players.add(team);
			
			List<Object> cages = Lists.newArrayList();
			
			Object cage_obj = SkywarsBase.getSelectedItem(SelectedItemType.CAGE, SkywarsBase.SAVED_ACCOUNTS.get(player).getSelected());
			Object two_obj = null;
			
			player.setFallDistance(0);
			
			emptyPlayer(player);
			
			if(hasteam) {
				team.setFallDistance(0);
				emptyPlayer(team);
				two_obj = SkywarsBase.getSelectedItem(SelectedItemType.CAGE, SkywarsBase.SAVED_ACCOUNTS.get(team).getSelected());
			}
			
			cages.add(cage_obj); if(hasteam && two_obj != null) { cages.add(two_obj); }
			Object selected_obj = cages.get(NumberUtil.getRandomInt(0, cages.size() - 1));
			
			
			if(selected_obj instanceof CageType) {
				
				CageType ct = (CageType) cage_obj;
				Location cageLocation = cageLocations.get(i);
				
				CageManager.registerCage(ct, cageLocation);
				
				player.teleport(cageLocation.clone().add(0.5, 0, 0.5));
				CageManager.cagesdata.put(player, cageLocation);
				
				if(hasteam) {
					team.teleport(cageLocation.clone().add(0.5, 0, 0.5));
					CageManager.cagesdata.put(team, cageLocation);
				}
				
				continue;
				
			} else {
				
				Location cageLocation = cageLocations.get(i);
				
				CageManager.registerCage(CageType.DEFAULT, cageLocation);
				
				player.teleport(cageLocation.clone().add(0.5, 0, 0.5));
				CageManager.cagesdata.put(player, cageLocation);
				
				if(hasteam) {
					team.teleport(cageLocation.clone().add(0.5, 0, 0.5));
					CageManager.cagesdata.put(team, cageLocation);
				}
				
				continue;
				
			}
			
		}
	}
	
	public static void playSound(Sound sound, float volume, float pitch) {
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.playSound(p.getLocation(), sound, volume, pitch);
		}
	}
	
	public static List<Player> getPlayersInSpectator() {
		return playersInSpectator;
	}

	public static void setPlayersInSpectator(List<Player> playersInSpectator) {
		TeamPlayerManager.playersInSpectator = playersInSpectator;
	}

	public static Map<Player, Player> getPlayersTeams() {
		return playersTeams;
	}

	public static void setPlayersTeams(Map<Player, Player> playersTeams) {
		TeamPlayerManager.playersTeams = playersTeams;
	}

	public enum InGameTitles {
		DEATH("&c&l¡HAS MUERTO!", "&7¡Eres un espectador!"),
	    WIN("&a&l¡HAS GANADO!", "&7¡Recompensa adicional!"),
		;
		
		private String title;
		private String subtitle;
		
		InGameTitles(String title, String subtitle){
			this.title = title;
			this.subtitle = subtitle;
		}

		public String getTitle() {
			return TextUtil.format(title);
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getSubtitle() {
			return TextUtil.format(subtitle);
		}

		public void setSubtitle(String subtitle) {
			this.subtitle = subtitle;
		}
		
		public void send(Player p) {
			
			TitleUtil.sendTitleToPlayer(p, 5, 5, 5, TextUtil.format(title), TextUtil.format(subtitle));
			return;
			
		}
		
	}
	
}
