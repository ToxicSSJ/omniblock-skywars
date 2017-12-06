package net.omniblock.skywars.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SoundPlayer {

	/**
	 * 
	 * StopSound protocol with Packets
	 */

	public static void stopSound(Player player) {

		player.playSound(player.getLocation(), "MC|StopSound", 1, 1);
		return;
		
	}

	/**
	 * 
	 * PlaySound protocol with Packets
	 */

	public static void sendSound(Location l, String soundname) {

		l.getWorld().playSound(l, soundname, 1, 1);
		return;

	}

	public static void sendSound(Location l, String soundname, int radius) {

		for (Player cache : Bukkit.getOnlinePlayers()) {
			if (cache.getLocation().distance(l) <= radius + 1) {
				l.getWorld().playSound(cache.getLocation(), soundname, 5, 1);
			}
		}

	}

	public static void sendSound(Player p, String soundname, boolean nearby, int radius) {
		
		Location l = p.getLocation();

		if (nearby) {
			
			for (Player cache : Bukkit.getOnlinePlayers()) {
				if (cache.getLocation().distance(l) <= radius + 1) {
					l.getWorld().playSound(cache.getLocation(), soundname, 5, 1);
				}
			}
			
		} else {
			
			l.getWorld().playSound(l, soundname, 5, 1);
			
		}
		
	}

	public static void sendSound(Player p, String soundname, int volume) {
		p.playSound(p.getLocation(), soundname, volume, 1);
	}

	public static void sendSound(Player p, String soundname, int volume, int pitch) {
		p.playSound(p.getLocation(), soundname, volume, pitch);
	}

}
