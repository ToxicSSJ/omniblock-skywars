package net.omniblock.skywars.util;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.util.reflection.ClassBuilder;
import net.omniblock.skywars.util.reflection.NMSClass;

import java.util.Random;

/**
 * � Copyright 2015 inventivetalent
 *
 * @author inventivetalent
 */
public class BossBar extends BukkitRunnable {

	protected static int ENTITY_DISTANCE = 32;

	protected final int ID;

	protected final Player receiver;
	protected       String message;
	protected       float  health;
	protected       float  healthMinus;
	protected float minHealth = 1;

	protected Location location;
	protected World    world;
	protected boolean visible = false;
	protected Object dataWatcher;
	
	public static void startBossBar(){
		Skywars.getInstance().getServer().getPluginManager().registerEvents(new BossBarPlugin(), Skywars.getInstance());
		BossBarPlugin.instance = Skywars.getInstance();
	}
	
	protected BossBar(Player player, String message, float percentage, int timeout, float minHealth) {
		this.ID = new Random().nextInt();

		this.receiver = player;
		this.message = message;
		this.health = percentage / 100F * this.getMaxHealth();
		this.minHealth = minHealth;
		this.world = player.getWorld();
		this.location = this.makeLocation(player.getLocation());

		if (percentage <= minHealth) {
			BossBarAPI.removeBar(player);
		}

		if (timeout > 0) {
			this.healthMinus = this.getMaxHealth() / timeout;
			this.runTaskTimer(BossBarPlugin.instance, 20, 20);
		}
	}

	protected Location makeLocation(Location base) {
		return base.getDirection().multiply(ENTITY_DISTANCE).add(base.toVector()).toLocation(this.world);
	}

	public float getMaxHealth() {
		return 300;
	}

	public void setHealth(float percentage) {
		this.health = percentage / 100F * this.getMaxHealth();
		if (this.health <= this.minHealth) {
			BossBarAPI.removeBar(this.receiver);
		} else {
			this.sendMetadata();
		}
	}

	public float getHealth() {
		return health;
	}

	public void setMessage(String message) {
		this.message = message;
		if (this.isVisible()) {
			this.sendMetadata();
		}
	}

	public String getMessage() {
		return message;
	}

	public Location getLocation() {
		return this.location;
	}

	@Override
	public void run() {
		this.health -= this.healthMinus;
		if (this.health <= this.minHealth) {
			BossBarAPI.removeBar(this.receiver);
		} else {
			this.sendMetadata();
		}
	}

	public void setVisible(boolean flag) {
		if (flag == this.visible) { return; }
		if (flag) {
			this.spawn();
		} else {
			this.destroy();
		}
	}

	public boolean isVisible() {
		return this.visible;
	}

	protected void updateMovement() {
		if (!this.visible) { return; }
		this.location = this.makeLocation(this.receiver.getLocation());
		try {
			Object packet = ClassBuilder.buildTeleportPacket(this.ID, this.getLocation(), false, false);
			BossBarAPI.sendPacket(this.receiver, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void updateDataWatcher() {
		if (this.dataWatcher == null) {
			try {
				this.dataWatcher = ClassBuilder.buildDataWatcher(null);
				ClassBuilder.setDataWatcherValue(this.dataWatcher, 17, new Integer(0));
				ClassBuilder.setDataWatcherValue(this.dataWatcher, 18, new Integer(0));
				ClassBuilder.setDataWatcherValue(this.dataWatcher, 19, new Integer(0));
				ClassBuilder.setDataWatcherValue(this.dataWatcher, 20, new Integer(1000));// Invulnerable time (1000 = very small)

				ClassBuilder.setDataWatcherValue(this.dataWatcher, 0, Byte.valueOf((byte) (0 | 1 << 5)));// Invisible
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			ClassBuilder.setDataWatcherValue(this.dataWatcher, 6, this.health);

			ClassBuilder.setDataWatcherValue(this.dataWatcher, 11, (byte) 1);
			ClassBuilder.setDataWatcherValue(this.dataWatcher, 3, (byte) 1);

			ClassBuilder.setDataWatcherValue(this.dataWatcher, 10, this.message);
			ClassBuilder.setDataWatcherValue(this.dataWatcher, 2, this.message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void sendMetadata() {
		this.updateDataWatcher();
		try {
			Object metaPacket = ClassBuilder.buildNameMetadataPacket(this.ID, this.dataWatcher, 2, 3, this.message);
			BossBarAPI.sendPacket(this.receiver, metaPacket);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void spawn() {
		try {
			this.updateMovement();
			this.updateDataWatcher();
			Object packet = ClassBuilder.buildWitherSpawnPacket(this.ID, this.getLocation(), this.dataWatcher);
			BossBarAPI.sendPacket(this.receiver, packet);
			this.visible = true;
			this.sendMetadata();
			this.updateMovement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void destroy() {
		try {
			this.cancel();
		} catch (IllegalStateException e) {
		}
		try {
			Object packet = NMSClass.PacketPlayOutEntityDestroy.getConstructor(int[].class).newInstance(new int[] { this.ID });
			BossBarAPI.sendPacket(this.receiver, packet);
			this.visible = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}