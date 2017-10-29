package net.omniblock.skywars.games.teams.events;

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
import net.omniblock.skywars.games.teams.TeamSkywars;
import net.omniblock.skywars.games.teams.managers.TeamPlayerManager;
import net.omniblock.skywars.network.NetworkData;
import net.omniblock.skywars.util.VanishUtil;

public class TeamPlayerToggleListener implements Listener {

	public static boolean Lock = false;
	public static boolean Verifier = false;

	@EventHandler
	public void onPlayerPreJoin(AsyncPlayerPreLoginEvent e) {

		if (Verifier == false) {
			Verifier = true;
		}

		SkywarsGameState currentState = Skywars.getGameState();

		if (currentState != SkywarsGameState.IN_LOBBY) {

			e.disallow(Result.KICK_OTHER, "¡El juego está en progreso!");
			return;

		} else {

			if (Bukkit.getOnlinePlayers().size() >= TeamSkywars.MAX_PLAYERS) {
				e.disallow(Result.KICK_OTHER, "La partida está llena!");
			}
			return;

		}

	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {

		e.setJoinMessage(null);

		if (Verifier == false) {
			Verifier = true;
		}

		SkywarsGameState currentState = Skywars.getGameState();

		if (Bukkit.getOnlinePlayers().size() >= TeamSkywars.MAX_PLAYERS && !Lock
				&& currentState == SkywarsGameState.IN_LOBBY) {

			Lock = true;
			NetworkData.broadcaster.read("$ LOCK");

		} else if (Bukkit.getOnlinePlayers().size() <= TeamSkywars.MIN_PLAYERS && Lock
				&& currentState == SkywarsGameState.IN_LOBBY) {

			Lock = false;
			NetworkData.broadcaster.read("$ UNLOCK");

		}

		TeamPlayerManager.addPlayer(e.getPlayer());
		VanishUtil.updateInvisible();

	}

	@EventHandler
	public void onLeft(PlayerQuitEvent e) {

		e.setQuitMessage(null);

		TeamPlayerManager.removePlayer(e.getPlayer());

		SkywarsGameState currentState = Skywars.getGameState();

		if (currentState == SkywarsGameState.IN_LOBBY) {
			TeamPlayerManager.removeTeam(e.getPlayer());
		}

		new BukkitRunnable() {
			@Override
			public void run() {

				if (TeamPlayerToggleListener.Verifier || currentState == SkywarsGameState.IN_LOBBY) {
					if (TeamPlayerManager.getPlayersInLobbyAmount() <= 0 || Bukkit.getOnlinePlayers().size() <= 0) {

						TeamSkywars.initializeReset();
						return;

					}
				}

			}
		}.runTaskLater(Skywars.getInstance(), 1L);

		if (Bukkit.getOnlinePlayers().size() >= TeamSkywars.MAX_PLAYERS && !Lock
				&& currentState == SkywarsGameState.IN_LOBBY) {

			Lock = true;
			NetworkData.broadcaster.read("$ LOCK");
			return;

		} else if (Bukkit.getOnlinePlayers().size() <= TeamSkywars.MIN_PLAYERS && Lock
				&& currentState == SkywarsGameState.IN_LOBBY) {

			Lock = false;
			NetworkData.broadcaster.read("$ UNLOCK");
			return;

		}

	}

}