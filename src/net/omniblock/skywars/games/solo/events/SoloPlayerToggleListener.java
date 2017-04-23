package net.omniblock.skywars.games.solo.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.SkywarsGameState;
import net.omniblock.skywars.games.solo.SoloSkywars;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;
import net.omniblock.skywars.network.NetworkData;
import net.omniblock.skywars.util.VanishUtil;

public class SoloPlayerToggleListener implements Listener {

	public static boolean Lock = false;
	public static boolean Verifier = false;
	
	@EventHandler
	public void onPlayerPreJoin(AsyncPlayerPreLoginEvent e) {
		
		if(Verifier == false) {
			Verifier = true;
		}
		
		SkywarsGameState currentState = Skywars.getGameState();
		
		if(currentState != SkywarsGameState.IN_LOBBY) {
			
			e.disallow(Result.KICK_OTHER, "¡El juego está en progreso!");
			return;
			
		} else {
			
			if(Bukkit.getOnlinePlayers().size() > SoloSkywars.MAX_PLAYERS) {
				e.disallow(Result.KICK_OTHER, "La partida está llena!");
			}
			return;
			
		}
		
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		
		if(Verifier == false) {
			Verifier = true;
		}
		
		SkywarsGameState currentState = Skywars.getGameState();
		
		if(Bukkit.getOnlinePlayers().size() >= SoloSkywars.MAX_PLAYERS && !Lock && currentState == SkywarsGameState.IN_LOBBY) {
		
			Lock = true;
			NetworkData.broadcaster.read("$ LOCK");
			
		} else if(Bukkit.getOnlinePlayers().size() <= SoloSkywars.MIN_PLAYERS && Lock && currentState == SkywarsGameState.IN_LOBBY){
			
			Lock = false;
			NetworkData.broadcaster.read("$ UNLOCK");
			
		}
			
		SoloPlayerManager.addPlayer(e.getPlayer());
		VanishUtil.updateInvisible();
		
	}
	
	@EventHandler
	public void onLeft(PlayerQuitEvent e) {
		
		SoloPlayerManager.removePlayer(e.getPlayer());
		
		SkywarsGameState currentState = Skywars.getGameState();
		
		new BukkitRunnable() {
			@Override
			public void run() {
				
				if(SoloPlayerToggleListener.Verifier || currentState == SkywarsGameState.IN_LOBBY) {
					if(SoloPlayerManager.getPlayersInLobbyAmount() <= 0 || Bukkit.getOnlinePlayers().size() <= 0) {
						
						SoloSkywars.initializeReset();
						return;
						
					}
				}
				
			}
		}.runTaskLater(Skywars.getInstance(), 1L);
		
		if(Bukkit.getOnlinePlayers().size() >= SoloSkywars.MAX_PLAYERS && !Lock && currentState == SkywarsGameState.IN_LOBBY) {
		
			Lock = true;
			NetworkData.broadcaster.read("$ LOCK");
			return;
			
		} else if(Bukkit.getOnlinePlayers().size() <= SoloSkywars.MIN_PLAYERS && Lock && currentState == SkywarsGameState.IN_LOBBY){
			
			Lock = false;
			NetworkData.broadcaster.read("$ UNLOCK");
			return;
			
		}
		
	}
	
}