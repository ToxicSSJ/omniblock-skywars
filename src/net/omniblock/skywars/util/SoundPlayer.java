package net.omniblock.skywars.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import io.netty.buffer.Unpooled;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutCustomPayload;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedSoundEffect;

public class SoundPlayer {

	/**
	 * 
	 * StopSound protocol with Packets
	 */

	public static void stopSound(Player player) {

		PacketDataSerializer packet = new PacketDataSerializer(Unpooled.buffer());
		packet.a("");

		((CraftPlayer) player).getHandle().playerConnection
				.sendPacket(new PacketPlayOutCustomPayload("MC|StopSound", packet));

	}

	/**
	 * 
	 * PlaySound protocol with Packets
	 */

	public static void sendSound(Location l, String soundname) {

		for (Player cache : Bukkit.getOnlinePlayers()) {

			PacketPlayOutNamedSoundEffect cache_h = new PacketPlayOutNamedSoundEffect(soundname,
					cache.getLocation().getX(), cache.getLocation().getY(), cache.getLocation().getZ(), 5, 1);
			((CraftPlayer) cache).getHandle().playerConnection.sendPacket(cache_h);

		}

	}

	public static void sendSound(Location l, String soundname, int radius) {

		for (Player cache : Bukkit.getOnlinePlayers()) {
			if (cache.getLocation().distance(l) <= radius + 1) {

				PacketPlayOutNamedSoundEffect cache_h = new PacketPlayOutNamedSoundEffect(soundname,
						cache.getLocation().getX(), cache.getLocation().getY(), cache.getLocation().getZ(), 5, 1);
				((CraftPlayer) cache).getHandle().playerConnection.sendPacket(cache_h);
			}
		}

	}

	public static void sendSound(Player p, String soundname, boolean nearby, int radius) {
		Location l = p.getLocation();

		if (nearby) {
			for (Player cache : Bukkit.getOnlinePlayers()) {
				if (cache.getLocation().distance(l) <= radius + 1) {
					PacketPlayOutNamedSoundEffect cache_h = new PacketPlayOutNamedSoundEffect(soundname, l.getX(),
							l.getY(), l.getZ(), 5, 1);
					((CraftPlayer) cache).getHandle().playerConnection.sendPacket(cache_h);
				}

			}
		} else {
			PacketPlayOutNamedSoundEffect _p = new PacketPlayOutNamedSoundEffect(soundname, l.getX(), l.getY(),
					l.getZ(), 5, 1);
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(_p);
		}
	}

	public static void sendSound(Player p, String soundname, int volume) {
		Location l = p.getLocation();
		PacketPlayOutNamedSoundEffect _p = new PacketPlayOutNamedSoundEffect(soundname, l.getX(), l.getY(), l.getZ(),
				volume, 1);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(_p);
	}

	public static void sendSound(Player p, String soundname, int volume, int pitch) {
		Location l = p.getLocation();
		PacketPlayOutNamedSoundEffect _p = new PacketPlayOutNamedSoundEffect(soundname, l.getX(), l.getY(), l.getZ(),
				volume, pitch);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(_p);
	}

}
