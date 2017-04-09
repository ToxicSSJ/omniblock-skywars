package net.omniblock.skywars.games.solo.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;

public class SoloPlayerQuitListener implements Listener {

	@EventHandler
	public void playerQuit(PlayerQuitEvent e) {
		SoloPlayerManager.removePlayer(e.getPlayer());
	}
	
}
