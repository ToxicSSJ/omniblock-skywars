package net.omniblock.skywars.games.solo.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.event.player.PlayerJoinEvent;

import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.SkywarsGameState;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;
import net.omniblock.skywars.util.VanishUtil;

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
		
		SoloPlayerManager.addPlayer(e.getPlayer());
		VanishUtil.updateInvisible();
		
	}
	
}