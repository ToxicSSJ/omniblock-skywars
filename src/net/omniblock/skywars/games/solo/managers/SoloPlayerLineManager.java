package net.omniblock.skywars.games.solo.managers;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.google.common.collect.Lists;

import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.SkywarsGameState;
import net.omniblock.skywars.util.MultiLineAPI;
import net.omniblock.skywars.util.MultiLineAPI.TagController;

public class SoloPlayerLineManager implements TagController {

	public static BukkitTask sbrunnable;

	public static void initialize() {

		SoloPlayerLineManager spl = new SoloPlayerLineManager();
		MultiLineAPI.register(spl);

		sbrunnable = new BukkitRunnable() {
			@Override
			public void run() {

				if (Skywars.getGameState() == SkywarsGameState.IN_GAME
						|| Skywars.getGameState() == SkywarsGameState.IN_PRE_GAME) {

					List<Player> tags_players = Lists.newArrayList();

					for (Player p : SoloPlayerManager.getPlayersInGameList()) {
						tags_players.add(p);
					}

					for (Player p : Bukkit.getOnlinePlayers()) {

						if (!tags_players.contains(p)) {

							if (MultiLineAPI.hasLines(p)) {

								MultiLineAPI.clearLines(p);

							}

						} else {

						}

					}

				}

			}
		}.runTaskTimer(Skywars.getInstance(), 2L, 2L);

	}

	public static String getPlayerHealthHud(Player p) {

		double health = p.getHealth();
		return "&f" + health + "&c❤";

	}

	@Override
	public int getPriority() {
		return 0;
	}

}
