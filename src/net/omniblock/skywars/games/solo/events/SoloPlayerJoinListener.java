package net.omniblock.skywars.games.solo.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.event.player.PlayerJoinEvent;

import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.SkywarsGameState;
import net.omniblock.skywars.games.solo.SoloSkywars;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;
import net.omniblock.skywars.util.TextUtil;

public class SoloPlayerJoinListener implements Listener {

	@EventHandler
	public void onPlayerPreJoin(AsyncPlayerPreLoginEvent e) {
		SkywarsGameState currentState = Skywars.getGameState();
		
		if(currentState != SkywarsGameState.IN_LOBBY) {
			e.disallow(Result.KICK_OTHER, "¡El juego está en progreso!");
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		
		Bukkit.broadcastMessage(TextUtil.format("&8&lS&8istema &9&l» &7El jugador &a" + e.getPlayer().getName() + "&7 ha ingresado a la partida. (" + SoloPlayerManager.getPlayersInLobbyAmount() + "/" + SoloSkywars.cagesLocations.size() + ")"));
		SoloPlayerManager.addPlayer(e.getPlayer());
		SoloPlayerManager.addPlayer(e.getPlayer());
		Bukkit.broadcastMessage(TextUtil.format("&8&lS&8istema &9&l» &7El jugador &a" + e.getPlayer().getName() + "&7 ha ingresado a la partida. (" + SoloPlayerManager.getPlayersInLobbyAmount() + "/" + SoloSkywars.cagesLocations.size() + ")"));
		
	}
}
