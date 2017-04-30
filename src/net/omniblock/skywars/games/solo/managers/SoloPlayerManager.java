package net.omniblock.skywars.games.solo.managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.SkywarsGameState;
import net.omniblock.skywars.games.solo.SoloSkywars;
import net.omniblock.skywars.patch.managers.AccountManager;
import net.omniblock.skywars.patch.managers.AccountManager.SelectedItemType;
import net.omniblock.skywars.patch.managers.CageManager;
import net.omniblock.skywars.patch.managers.MapManager;
import net.omniblock.skywars.patch.managers.CageManager.CageType;
import net.omniblock.skywars.patch.managers.lobby.LobbyManager;
import net.omniblock.skywars.patch.managers.SpectatorManager;
import net.omniblock.skywars.util.TextUtil;
import net.omniblock.skywars.util.TitleUtil;

public class SoloPlayerManager {

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
	
	public static void winnerPlayer(Player p) {
		
		emptyPlayer(p);
		healPlayer(p);
		forceFly(p);
		
		return;
		
	}
	
	public static void spectatorPlayer(Player p) {
		
		emptyPlayer(p);
		healPlayer(p);
		forceFly(p);
		
		if(SoloPlayerManager.getPlayersInGameAmount() >= 1) {
			p.teleport(SoloPlayerManager.getPlayersInGameList().get(0));
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
			
			Bukkit.broadcastMessage(TextUtil.format("&8&lS&8istema &9&l» &7El jugador &a" + p.getName() + "&7 ha ingresado a la partida. (" + (SoloPlayerManager.getPlayersInLobbyAmount() + 1) + "/" + SoloSkywars.cagesLocations.size() + ")"));
			
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
			AccountManager.saveAccount(p);
			
			playersInLobby.add(p);
			
		} else {
			
			spectatorPlayer(p);
			
		}
		
		return true;
	}
	
	public static void removePlayer(Player p) {
		playersInLobby.remove(p);
		
		if(playersInGame.contains(p)) {
			//TODO: Eliminar de la partida
			playersInGame.remove(p);
		}
		
	}
	
	public static void transferAllPlayersToInGame() {
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
		
		List<Location> cageLocations = SoloSkywars.getCageLocations();
		Collections.shuffle(cageLocations);
		
		for(int i = 0; i < getPlayersInGameAmount(); i++) {
			
			Player player = playersInGame.get(i);
			Object cage_obj = AccountManager.getSelectedItem(SelectedItemType.CAGE, AccountManager.SAVED_ACCOUNTS.get(player).getSelected());
			
			emptyPlayer(player);
			
			if(cage_obj instanceof CageType) {
				
				CageType ct = (CageType) cage_obj;
				Location cageLocation = cageLocations.get(i);
				
				CageManager.registerCage(ct, cageLocation);
				player.teleport(cageLocation.clone().add(0.5, 0, 0.5));
				
				CageManager.cagesdata.put(player, cageLocation);
				
				continue;
				
			} else {
				Location cageLocation = cageLocations.get(i);
				
				CageManager.registerCage(CageType.DEFAULT, cageLocation);
				player.teleport(cageLocation.clone().add(0.5, 0, 0.5));
				
				CageManager.cagesdata.put(player, cageLocation);
				
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
		SoloPlayerManager.playersInSpectator = playersInSpectator;
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
