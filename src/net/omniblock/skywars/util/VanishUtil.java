package net.omniblock.skywars.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.util.EntityHider.Policy;

public class VanishUtil {

	private static EntityHider entityHider;

	public static List<Player> INVISIBLE_PLAYERS = new ArrayList<Player>();

	public static void start() {
		setEntityHider(new EntityHider(Skywars.getInstance(), Policy.BLACKLIST));
	}

	public static void updateInvisible() {

		for (Player player : Bukkit.getOnlinePlayers()) {

			if (INVISIBLE_PLAYERS.contains(player)) {

				for (Player cache : Bukkit.getOnlinePlayers()) {
					if (player != cache) {
						hidePlayer(player, cache);
					}
				}

			} else {

				for (Player cache : Bukkit.getOnlinePlayers()) {
					if (player != cache) {
						seePlayer(player, cache);
					}
				}

			}

		}

	}

	public static void makeInvisible(Player player) {

		INVISIBLE_PLAYERS.add(player);
		updateInvisible();

	}

	public static void appear(Player player) {

		if (INVISIBLE_PLAYERS.contains(player)) {

			INVISIBLE_PLAYERS.remove(player);
			updateInvisible();

		}

	}

	public static void seePlayer(Player see, Player from) {

		from.showPlayer(see);
		entityHider.showEntity(from, see);

	}

	public static void hidePlayer(Player hiding, Player from) {

		from.hidePlayer(hiding);
		entityHider.hideEntity(from, hiding);

	}

	public static EntityHider getEntityHider() {
		return entityHider;
	}

	public static void setEntityHider(EntityHider entityHider) {
		VanishUtil.entityHider = entityHider;
	}

}
