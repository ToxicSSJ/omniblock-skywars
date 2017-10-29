package net.omniblock.skywars.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import net.omniblock.skywars.Skywars;

public class Cinematix {

	public Plugin plugin = Skywars.getInstance();
	public List<Location> points = new ArrayList<Location>();
	public Map<Entity, Integer> runnables = new HashMap<Entity, Integer>();
	public Map<Entity, Boolean> stopping = new HashMap<Entity, Boolean>();

	/**
	 * @return The list of points to travel between.
	 */
	public List<Location> getPoints() {
		return points;
	}

	/**
	 * Generates a list of locations (often in the hundreds, thousands) that
	 * forms a smooth path between all the points in this cinematix.
	 *
	 * @param seconds
	 *            The time to travel between locations.
	 * @return A path of locations.
	 */
	public List<Location> generatePath(double seconds) {
		if (points.size() == 0)
			return new ArrayList<Location>();

		World world = points.get(0).getWorld();

		double totalTime = seconds * (20.0 / 1.0);

		List<Double> distances = new ArrayList<Double>();
		List<Double> times = new ArrayList<Double>();

		double totalDistance = 0;

		// Calculate distances
		for (int i = 0; i < points.size() - 1; i++) {
			Location from = points.get(i);
			Location to = points.get(i + 1);

			double distance = from.distance(to);

			totalDistance += distance;
			distances.add(distance);
		}

		// Calculate portion of time each point should take
		for (Double distance : distances) {
			double percent = distance / totalDistance;
			times.add(percent * totalTime);
		}

		// path are the places to teleport to every tick (1/20th second)
		List<Location> path = new ArrayList<Location>();

		for (int i = 0; i < points.size() - 1; i++) {
			Location from = points.get(i);
			Location to = points.get(i + 1);
			double time = times.get(i);

			double dX = to.getX() - from.getX();
			double dY = to.getY() - from.getY();
			double dZ = to.getZ() - from.getZ();
			float dYaw = Math.abs(to.getYaw() - from.getYaw());
			float dPitch = to.getPitch() - from.getPitch();

			if (dYaw <= 180.0) {
				if (from.getYaw() >= to.getYaw())
					dYaw = -dYaw;
			} else if (from.getYaw() < to.getYaw()) {
				dYaw = dYaw - 360.0F;
			} else {
				dYaw = 360.0F - dYaw;
			}

			for (double t = 0; t < time; t++) {
				double x = from.getX() + (dX / time) * t;
				double y = from.getY() + (dY / time) * t;
				double z = from.getZ() + (dZ / time) * t;
				float yaw = (float) (from.getYaw() + (dYaw / time) * t);
				float pitch = (float) (from.getPitch() + (dPitch / time) * t);

				Location loc = new Location(world, x, y, z, yaw, pitch);
				path.add(loc);
			}
		}

		return path;
	}

	/**
	 * Start a cinematix for the given duration of time.
	 * 
	 * @param Entity
	 *            The Entity to engage in a cinematix.
	 * @param time
	 *            The time in seconds.
	 * @return True if successful, false if the Entity is already in a
	 *         cinematix.
	 */
	public boolean start(final Entity Entity, final double time) {
		if (isRunning(Entity))
			return false;

		Runnable task = new Runnable() {

			int i = 0;
			List<Location> path = generatePath(time);

			@Override
			public void run() {
				boolean stop = stopping.get(Entity);

				if (i > path.size() - 1 || stop) {
					int taskId = runnables.get(Entity);
					Bukkit.getScheduler().cancelTask(taskId);
					runnables.remove(Entity);
					stopping.remove(Entity);
					return;
				}

				for (int x = 0; x < 5; x++)

					Entity.getLocation().getChunk().load();
				Entity.teleport(path.get(i));

				i++;
			}
		};

		int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, task, 0, 1);

		stopping.put(Entity, false);
		runnables.put(Entity, taskId);
		return true;
	}

	/**
	 * Stop an ongoing cinematix.
	 * 
	 * @param Entity
	 *            The Entity who's cinematix should stop.
	 * @return True if a cinematix was in progress, false if otherwise.
	 */
	public boolean stop(Entity Entity) {
		if (!isRunning(Entity))
			return false;
		stopping.put(Entity, true);
		return true;
	}

	/**
	 * @param Entity
	 *            The Entity to check.
	 * @return True if a Entity's cinematix is in progress, false if otherwise.
	 */
	public boolean isRunning(Entity Entity) {
		return runnables.containsKey(Entity);
	}

	public void moveToward(Entity entity, Location to, double speed) {
		Location loc = entity.getLocation();
		double x = loc.getX() - to.getX();
		double y = loc.getY() - to.getY();
		double z = loc.getZ() - to.getZ();
		Vector velocity = new Vector(x, y, z).normalize().multiply(-speed);
		entity.setVelocity(velocity);
	}

}
