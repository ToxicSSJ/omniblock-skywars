package net.omniblock.skywars.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.google.common.collect.Lists;

import net.omniblock.network.library.utils.TextUtil;
import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;

public class CameraUtil extends org.bukkit.plugin.java.JavaPlugin implements org.bukkit.event.Listener {

	static String prefix = ChatColor.AQUA + "[" + ChatColor.DARK_AQUA + "CP" + ChatColor.AQUA + "CameraUtil] "
			+ ChatColor.GREEN;

	static Set<UUID> travelling = new HashSet<UUID>();
	static Set<UUID> stopping = new HashSet<UUID>();

	public static double round(double unrounded, int precision) {
		BigDecimal bd = new BigDecimal(unrounded);
		BigDecimal rounded = bd.setScale(precision, 4);
		return rounded.doubleValue();
	}

	public static void travel(final Player player, List<Location> Locations, int time, boolean player_direction) {
		List<Double> diffs = Lists.newArrayList();
		List<Integer> travelTimes = Lists.newArrayList();

		double totalDiff = 0.0D;

		for (int i = 0; i < Locations.size() - 1; i++) {
			Location s = (Location) Locations.get(i);
			Location n = (Location) Locations.get(i + 1);
			double diff = positionDifference(s, n);
			totalDiff += diff;
			diffs.add(Double.valueOf(diff));
		}

		for (Iterator<Double> n = diffs.iterator(); n.hasNext();) {
			double d = ((Double) n.next()).doubleValue();
			travelTimes.add(Integer.valueOf((int) (d / totalDiff * time)));
		}

		List<Location> tps = Lists.newArrayList();

		org.bukkit.World w = player.getWorld();

		for (int i = 0; i < Locations.size() - 1; i++) {
			Location s = (Location) Locations.get(i);
			Location n = (Location) Locations.get(i + 1);
			int t = ((Integer) travelTimes.get(i)).intValue();

			double moveX = n.getX() - s.getX();
			double moveY = n.getY() - s.getY();
			double moveZ = n.getZ() - s.getZ();
			double movePitch = n.getPitch() - s.getPitch();

			double yawDiff = Math.abs(n.getYaw() - s.getYaw());
			double c = 0.0D;

			if (yawDiff <= 180.0D) {
				if (s.getYaw() < n.getYaw()) {
					c = yawDiff;
				} else {
					c = -yawDiff;
				}
			} else if (s.getYaw() < n.getYaw()) {
				c = -(360.0D - yawDiff);
			} else {
				c = 360.0D - yawDiff;
			}

			double d = c / t;

			for (int x = 0; x < t; x++) {
				Location l = new Location(w, s.getX() + moveX / t * x, s.getY() + moveY / t * x,
						s.getZ() + moveZ / t * x, (float) (s.getYaw() + d * x),
						(float) (s.getPitch() + movePitch / t * x));
				tps.add(l);
			}
		}

		try {

			player.setAllowFlight(true);
			player.teleport((Location) tps.get(0));
			player.setFlying(true);

			player.playSound(player.getLocation(), Sound.GHAST_CHARGE, 5, -15);
			travelling.add(player.getUniqueId());

			org.bukkit.Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Skywars.getInstance(), new Runnable() {

				private int ticks = 0;

				public void run() {
					if (ticks < tps.size()) {

						Location next_tp = tps.get(ticks);
						if (player_direction) {
							next_tp.setYaw(player.getLocation().getYaw());
							next_tp.setPitch(player.getLocation().getPitch());
						}

						player.teleport(next_tp);

						if (!CameraUtil.stopping.contains(player.getUniqueId())) {
							org.bukkit.Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Skywars.getInstance(),
									this, 1L);
						} else {
							CameraUtil.stopping.remove(player.getUniqueId());
							CameraUtil.travelling.remove(player.getUniqueId());
						}

						ticks += 1;

					} else {
						CameraUtil.travelling.remove(player.getUniqueId());
					}
				}

			});
		} catch (Exception e) {
			player.sendMessage(TextUtil.format(
					"&cUps, &7Ha ocurrido un error inesperado, ayudanos a solucionarlo contactando con el soporte de omniblock en &9www.omniblock.net &7y envia una foto de este error adjuntandole el codigo &c#3827 &7, Gracias a tu ayuda mejoramos en omniblock cada dia!"));
		}
	}

	public static void travel(final List<Player> players, List<Location> Locations, Block platform, int time) {

		if (players.size() <= 0)
			return;

		List<Double> diffs = Lists.newArrayList();
		List<Integer> travelTimes = Lists.newArrayList();

		double totalDiff = 0.0D;

		for (int i = 0; i < Locations.size() - 1; i++) {
			Location s = (Location) Locations.get(i);
			Location n = (Location) Locations.get(i + 1);
			double diff = positionDifference(s, n);
			totalDiff += diff;
			diffs.add(Double.valueOf(diff));
		}

		for (Iterator<Double> n = diffs.iterator(); n.hasNext();) {
			double d = ((Double) n.next()).doubleValue();
			travelTimes.add(Integer.valueOf((int) (d / totalDiff * time)));
		}

		List<Location> tps = Lists.newArrayList();

		org.bukkit.World w = players.get(0).getWorld();

		for (int i = 0; i < Locations.size() - 1; i++) {
			Location s = (Location) Locations.get(i);
			Location n = (Location) Locations.get(i + 1);
			int t = ((Integer) travelTimes.get(i)).intValue();

			double moveX = n.getX() - s.getX();
			double moveY = n.getY() - s.getY();
			double moveZ = n.getZ() - s.getZ();
			double movePitch = n.getPitch() - s.getPitch();

			double yawDiff = Math.abs(n.getYaw() - s.getYaw());
			double c = 0.0D;

			if (yawDiff <= 180.0D) {
				if (s.getYaw() < n.getYaw()) {
					c = yawDiff;
				} else {
					c = -yawDiff;
				}
			} else if (s.getYaw() < n.getYaw()) {
				c = -(360.0D - yawDiff);
			} else {
				c = 360.0D - yawDiff;
			}

			double d = c / t;

			for (int x = 0; x < t; x++) {
				Location l = new Location(w, s.getX() + moveX / t * x, s.getY() + moveY / t * x,
						s.getZ() + moveZ / t * x, (float) (s.getYaw() + d * x),
						(float) (s.getPitch() + movePitch / t * x));
				tps.add(l);
			}
		}

		@SuppressWarnings("deprecation")
		byte b = platform.getData();
		Material m = platform.getType();

		platform.setType(Material.AIR);

		for (Player player : players) {

			try {

				player.setAllowFlight(true);
				player.teleport((Location) tps.get(0));
				player.setFlying(true);

				player.playSound(player.getLocation(), Sound.GHAST_CHARGE, 5, -15);
				travelling.add(player.getUniqueId());

				org.bukkit.Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Skywars.getInstance(),
						new Runnable() {
							private int ticks = 0;

							@SuppressWarnings("deprecation")
							public void run() {
								if (ticks < tps.size()) {

									Location next_tp = tps.get(ticks);
									next_tp.setYaw(player.getLocation().getYaw());
									next_tp.setPitch(player.getLocation().getPitch());

									player.teleport(next_tp);

									if (!CameraUtil.stopping.contains(player.getUniqueId())) {
										org.bukkit.Bukkit.getServer().getScheduler()
												.scheduleSyncDelayedTask(Skywars.getInstance(), this, 1L);
									} else {
										CameraUtil.stopping.remove(player.getUniqueId());
										CameraUtil.travelling.remove(player.getUniqueId());
									}

									ticks += 1;
								} else {
									CameraUtil.travelling.remove(player.getUniqueId());

									player.playSound(player.getLocation(), Sound.BAT_TAKEOFF, 5, -5);
									SoloPlayerManager.forceRemoveFly(player);

									platform.setType(m);
									platform.setData(b);
								}
							}

						});

			} catch (Exception e) {
				player.sendMessage(TextUtil.format(
						"&cUps, &7Ha ocurrido un error inesperado, ayudanos a solucionarlo contactando con el soporte de omniblock en &9www.omniblock.net &7y envia una foto de este error adjuntandole el codigo &c#3827 &7, Gracias a tu ayuda mejoramos en omniblock cada dia!"));
			}

		}

	}

	public static void travel(final Player player, List<Location> Locations, Block platform, int time) {

		List<Double> diffs = Lists.newArrayList();
		List<Integer> travelTimes = Lists.newArrayList();

		double totalDiff = 0.0D;

		for (int i = 0; i < Locations.size() - 1; i++) {
			Location s = (Location) Locations.get(i);
			Location n = (Location) Locations.get(i + 1);
			double diff = positionDifference(s, n);
			totalDiff += diff;
			diffs.add(Double.valueOf(diff));
		}

		for (Iterator<Double> n = diffs.iterator(); n.hasNext();) {
			double d = ((Double) n.next()).doubleValue();
			travelTimes.add(Integer.valueOf((int) (d / totalDiff * time)));
		}

		List<Location> tps = Lists.newArrayList();

		org.bukkit.World w = player.getWorld();

		for (int i = 0; i < Locations.size() - 1; i++) {
			Location s = (Location) Locations.get(i);
			Location n = (Location) Locations.get(i + 1);
			int t = ((Integer) travelTimes.get(i)).intValue();

			double moveX = n.getX() - s.getX();
			double moveY = n.getY() - s.getY();
			double moveZ = n.getZ() - s.getZ();
			double movePitch = n.getPitch() - s.getPitch();

			double yawDiff = Math.abs(n.getYaw() - s.getYaw());
			double c = 0.0D;

			if (yawDiff <= 180.0D) {
				if (s.getYaw() < n.getYaw()) {
					c = yawDiff;
				} else {
					c = -yawDiff;
				}
			} else if (s.getYaw() < n.getYaw()) {
				c = -(360.0D - yawDiff);
			} else {
				c = 360.0D - yawDiff;
			}

			double d = c / t;

			for (int x = 0; x < t; x++) {
				Location l = new Location(w, s.getX() + moveX / t * x, s.getY() + moveY / t * x,
						s.getZ() + moveZ / t * x, (float) (s.getYaw() + d * x),
						(float) (s.getPitch() + movePitch / t * x));
				tps.add(l);
			}
		}

		try {

			@SuppressWarnings("deprecation")
			byte b = platform.getData();
			Material m = platform.getType();

			platform.setType(Material.AIR);

			player.setAllowFlight(true);
			player.teleport((Location) tps.get(0));
			player.setFlying(true);

			player.playSound(player.getLocation(), Sound.GHAST_CHARGE, 5, -15);
			travelling.add(player.getUniqueId());

			org.bukkit.Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Skywars.getInstance(), new Runnable() {
				private int ticks = 0;

				@SuppressWarnings("deprecation")
				public void run() {
					if (ticks < tps.size()) {

						Location next_tp = tps.get(ticks);
						next_tp.setYaw(player.getLocation().getYaw());
						next_tp.setPitch(player.getLocation().getPitch());

						player.teleport(next_tp);

						if (!CameraUtil.stopping.contains(player.getUniqueId())) {
							org.bukkit.Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Skywars.getInstance(),
									this, 1L);
						} else {
							CameraUtil.stopping.remove(player.getUniqueId());
							CameraUtil.travelling.remove(player.getUniqueId());
						}

						ticks += 1;
					} else {
						CameraUtil.travelling.remove(player.getUniqueId());

						player.playSound(player.getLocation(), Sound.BAT_TAKEOFF, 5, -5);
						SoloPlayerManager.forceRemoveFly(player);

						platform.setType(m);
						platform.setData(b);
					}
				}

			});
		} catch (Exception e) {
			player.sendMessage(TextUtil.format(
					"&cUps, &7Ha ocurrido un error inesperado, ayudanos a solucionarlo contactando con el soporte de omniblock en &9www.omniblock.net &7y envia una foto de este error adjuntandole el codigo &c#3827 &7, Gracias a tu ayuda mejoramos en omniblock cada dia!"));
		}
	}

	public static int parseTimeString(String timeString) throws java.text.ParseException {
		Date Length;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("mm'm'ss's'");
			Length = formatter.parse(timeString);
		} catch (Exception e) {
			try {
				SimpleDateFormat formatter = new SimpleDateFormat("m'm'ss's'");
				Length = formatter.parse(timeString);
			} catch (Exception e1) {
				try {
					SimpleDateFormat formatter = new SimpleDateFormat("m'm's's'");
					Length = formatter.parse(timeString);
				} catch (Exception e2) {
					try {
						SimpleDateFormat formatter = new SimpleDateFormat("mm'm's's'");
						Length = formatter.parse(timeString);
					} catch (Exception e3) {
						try {
							SimpleDateFormat formatter = new SimpleDateFormat("mm'm'");
							Length = formatter.parse(timeString);
						} catch (Exception e4) {
							try {
								SimpleDateFormat formatter = new SimpleDateFormat("m'm'");
								Length = formatter.parse(timeString);
							} catch (Exception e5) {
								try {
									SimpleDateFormat formatter = new SimpleDateFormat("s's'");
									Length = formatter.parse(timeString);
								} catch (Exception e6) {
									SimpleDateFormat formatter = new SimpleDateFormat("ss's'");
									Length = formatter.parse(timeString);
								}
							}
						}
					}
				}
			}
		}

		Calendar cal = java.util.GregorianCalendar.getInstance();
		cal.setTime(Length);

		int time = (cal.get(12) * 60 + cal.get(13)) * 20;

		return time;
	}

	public static double positionDifference(Location cLoc, Location eLoc) {
		double cX = cLoc.getX();
		double cY = cLoc.getY();
		double cZ = cLoc.getZ();

		double eX = eLoc.getX();
		double eY = eLoc.getY();
		double eZ = eLoc.getZ();

		double dX = eX - cX;
		if (dX < 0.0D) {
			dX = -dX;
		}
		double dZ = eZ - cZ;
		if (dZ < 0.0D) {
			dZ = -dZ;
		}
		double dXZ = Math.hypot(dX, dZ);

		double dY = eY - cY;
		if (dY < 0.0D) {
			dY = -dY;
		}
		double dXYZ = Math.hypot(dXZ, dY);

		return dXYZ;
	}

	public static boolean getLookingAt(Player player1, Player player2) {

		if (player1 != player2) {

			Location eye = player1.getEyeLocation();
			Vector toEntity = player2.getEyeLocation().toVector().subtract(eye.toVector());
			double dot = toEntity.normalize().dot(eye.getDirection());

			return dot > 0.99D;

		}

		return false;

	}

	public static boolean isTravelling(UUID PlayerUUID) {
		if (travelling.contains(PlayerUUID))
			return true;
		return false;
	}

	public static void stop(UUID PlayerUUID) {
		stopping.add(PlayerUUID);
		org.bukkit.Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Skywars.getInstance(), new Runnable() {
			public void run() {
				CameraUtil.stopping.remove(this);
			}
		}, 2L);
	}
}