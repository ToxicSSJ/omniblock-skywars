package net.omniblock.skywars.util;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class LocationUtil {

	public static Location getRandomLocation(Player player) {
		if (player.isOnline()) {
			return getRandomLocation(player.getLocation());
		}
		return null;
	}

	public static Location getRandomLocation(Location loc) {
		return loc.clone().add(NumberUtil.getRandomInt(-50, 50), NumberUtil.getRandomInt(-10, 20),
				NumberUtil.getRandomInt(-50, 50));
	}

}
