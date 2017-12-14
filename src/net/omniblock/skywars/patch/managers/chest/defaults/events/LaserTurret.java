package net.omniblock.skywars.patch.managers.chest.defaults.events;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.omniblock.network.library.helpers.effectlib.effect.LineEffect;
import net.omniblock.network.library.helpers.effectlib.util.ParticleEffect;
import net.omniblock.network.library.utils.TextUtil;
import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.games.solo.events.SoloPlayerBattleListener;
import net.omniblock.skywars.games.solo.events.SoloPlayerBattleListener.DamageCauseZ;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;
import net.omniblock.skywars.games.teams.managers.TeamPlayerManager;
import net.omniblock.skywars.patch.managers.CustomProtocolManager;
import net.omniblock.skywars.patch.managers.chest.defaults.events.type.ItemType;
import net.omniblock.skywars.patch.managers.chest.defaults.events.type.Turret;
import net.omniblock.skywars.patch.managers.chest.defaults.events.type.Turret.TurretUtil.AwakeTurret;
import net.omniblock.skywars.patch.managers.chest.defaults.events.type.Turret.TurretUtil.TurretBuilder;
import net.omniblock.skywars.patch.managers.chest.defaults.events.type.TurretType;

public class LaserTurret implements Turret, ItemType, Listener {

	TurretType type = TurretType.LASER_TURRET;

	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		
		if (SoloPlayerManager.getPlayersInGameList().contains(e.getPlayer())
				|| TeamPlayerManager.getPlayersInGameList().contains(e.getPlayer())) {
			
			if(!Skywars.ingame)
				return;
			
			if (e.getBlockPlaced().getType() == type.getMaterial()) {
				build(e.getPlayer(), e.getBlockPlaced());
			}
			
		}
		
	}

	@Override
	public void build(Player constructor, Block place) {

		TurretBuilder tb = TurretUtil.startBuildAnimation(type, place);

		new BukkitRunnable() {
			@Override
			public void run() {

				if (tb.isCanceled()) {

					cancel();
					place.setType(Material.AIR);
					TurretUtil.explodeTower(type, place.getLocation());
					return;

				} else if (tb.isCompleted()) {

					cancel();
					place.getLocation().getWorld().playSound(place.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_DOOR_WOOD, 4, 15);
					Bukkit.broadcastMessage(TextUtil.getCenteredMessage("&r"));
					Bukkit.broadcastMessage(TextUtil.getCenteredMessage("&r"));
					Bukkit.broadcastMessage(TextUtil
							.getCenteredMessage("&r&8&l&m[━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━]", true));
					Bukkit.broadcastMessage(TextUtil.getCenteredMessage(
							"&r&b&l» &7" + constructor.getName() + " ha utilizado su torreta", true));
					Bukkit.broadcastMessage(
							TextUtil.getCenteredMessage("&r&7tipo &c&l" + type.getName_type() + "!", true));
					Bukkit.broadcastMessage(TextUtil
							.getCenteredMessage("&r&8&l&m[━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━]", true));
					Bukkit.broadcastMessage(TextUtil.getCenteredMessage("&r"));

					craft(constructor, place);
					return;

				}
			}
		}.runTaskTimer(Skywars.getInstance(), 1L, 1L);

	}

	@Override
	public void craft(Player constructor, Block place) {

		AwakeTurret awaketurret = new AwakeTurret(constructor);

		final Location l1 = place.getLocation();
		final Location l2 = new Location(l1.getWorld(), l1.getX() + 0.5, l1.getY() + 1.2, l1.getZ() + 0.5);
		final Location l3 = new Location(l1.getWorld(), l1.getX() + 0.5, l1.getY() + 1.5, l1.getZ() + 0.5);

		l1.getBlock().setType(Material.HOPPER);
		CustomProtocolManager.PROTECTED_BLOCK_LIST.add(l1.getBlock());
		awaketurret.components.add(l1.getBlock());
		l2.getBlock().setType(Material.CARPET);
		CustomProtocolManager.PROTECTED_BLOCK_LIST.add(l2.getBlock());
		awaketurret.components.add(l2.getBlock());

		final ArmorStand _a = (ArmorStand) l3.getWorld().spawnEntity(l3, EntityType.ARMOR_STAND);
		_a.setCustomName(TextUtil
				.format("&8&l> &e&lTorreta " + type.getName_type() + " de " + constructor.getName() + " &8&l<"));
		_a.setVisible(false);
		_a.setCustomNameVisible(true);
		_a.setGravity(false);
		_a.setBasePlate(false);
		
		awaketurret.info_hud = _a;

		NPCRegistry registry = CitizensAPI.getNPCRegistry();
		final NPC e1 = registry.createNPC(type.getEntitytype(), TextUtil.format("&b&l▄▄▄▄▄"));
		final NPC a1 = registry.createNPC(EntityType.ARMOR_STAND,
				TextUtil.format("&c&lDaño: &7" + type.getDamage() + "❤/hit"));

		e1.spawn(l3);
		a1.spawn(l2);
		e1.setProtected(true);

		a1.setProtected(true);
		a1.getEntity().setCustomNameVisible(false);

		final ArmorStand _a1 = (ArmorStand) a1.getEntity();
		_a1.setGravity(true);

		new BukkitRunnable() {
			public void run() {
				_a1.setGravity(false);
			}
		}.runTaskLater(Skywars.getInstance(), (long) (20 * 1.2));

		awaketurret.turret = e1;
		awaketurret.damage_hud = a1;

		awaketurret.alive = true;

		setEyeIA(awaketurret, 5, true);

	}

	@Override
	public void setEyeIA(AwakeTurret awaketurret, int range, boolean onlyinmunity) {

		awaketurret.initialize();

		new BukkitRunnable() {

			int round = 0;
			int recoil = 3;

			boolean shoot = false;

			@Override
			public void run() {

				if (!awaketurret.turret.isSpawned()) {
					cancel();
					return;
				}

				if (awaketurret.turret.getEntity().isDead()) {
					cancel();
					return;
				}

				if (round >= (20 * recoil)) {
					round = 0;
					shoot = true;
				} else {
					round++;
				}

				if (awaketurret.alive) {

					Collection<Entity> entities = awaketurret.turret.getEntity().getWorld()
							.getNearbyEntities(awaketurret.turret.getEntity().getLocation(), 8, 8, 8);
					for (Entity e : entities) {

						if (e.getType() == EntityType.PLAYER) {

							Player p = (Player) e;
							if (SoloPlayerManager.getPlayersInGameList().contains(p)) {
								if (!awaketurret.extra_exclude.contains(p) && awaketurret.owner != p) {

									Guardian turret_entity = (Guardian) awaketurret.turret.getEntity();
									if (turret_entity.hasLineOfSight(p)) {

										if (!shoot) {

											awaketurret.turret.faceLocation(p.getLocation());
											awaketurret.damage_hud.faceLocation(p.getLocation());

											LineEffect ef = new LineEffect(Skywars.effectmanager);
											ef.particle = ParticleEffect.REDSTONE;

											if (round > 20 && round < 30) {
												ef.color = Color.AQUA;
											} else if (round > 30 && round < 40) {
												ef.color = Color.NAVY;
											} else if (round > 40 && round < 55) {
												ef.color = Color.ORANGE;
											}

											ef.visibleRange = 300;
											ef.particles = 5;
											ef.setLocation(turret_entity.getLocation());
											ef.setTargetLocation(p.getEyeLocation());
											ef.start();
											return;

										} else {

											shoot = false;
											shoot(awaketurret, p);
											return;

										}
									}
								}
							}
						}
					}

					for (AwakeTurret otherturret : TurretUtil.awake_turrets) {

						if (otherturret != awaketurret) {
							if (otherturret.turret.getEntity().getLocation()
									.distance(awaketurret.turret.getEntity().getLocation()) <= 8) {
								if (!(otherturret.extra_exclude.contains(awaketurret.owner)
										&& awaketurret.extra_exclude.contains(otherturret.owner))) {

									Entity e = otherturret.turret.getEntity();
									Guardian turret_entity = (Guardian) awaketurret.turret.getEntity();

									if (turret_entity.hasLineOfSight(e)) {

										if (!shoot) {

											awaketurret.turret.faceLocation(e.getLocation());
											awaketurret.damage_hud.faceLocation(e.getLocation());

											LineEffect ef = new LineEffect(Skywars.effectmanager);
											ef.particle = ParticleEffect.REDSTONE;

											if (round > 20 && round < 30) {
												ef.color = Color.AQUA;
											} else if (round > 30 && round < 40) {
												ef.color = Color.NAVY;
											} else if (round > 40 && round < 55) {
												ef.color = Color.ORANGE;
											}

											ef.visibleRange = 300;
											ef.particles = 5;
											ef.setLocation(turret_entity.getLocation());
											ef.setTargetLocation(e.getLocation().clone().add(0, .5, 0));
											ef.start();
											return;

										} else {

											shoot = false;
											shoot(awaketurret, otherturret);
											return;

										}
									}
								}
							}
						}

					}

				} else {
					destroy(awaketurret, null);
					cancel();
					return;
				}

			}
		}.runTaskTimer(Skywars.getInstance(), 1L, 1L);

	}

	@Override
	public void shoot(AwakeTurret awaketurret, Entity toshoot) {

		if (toshoot.getType() == EntityType.PLAYER) {

			toshoot.getWorld().playSound(toshoot.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 5, -2);

			Player affected = (Player) toshoot;
			Guardian turret_entity = (Guardian) awaketurret.turret.getEntity();

			awaketurret.turret.faceLocation(affected.getLocation());
			awaketurret.damage_hud.faceLocation(affected.getLocation());

			LineEffect ef = new LineEffect(Skywars.effectmanager);
			ef.particle = ParticleEffect.REDSTONE;
			ef.color = Color.RED;
			ef.visibleRange = 300;
			ef.setLocation(turret_entity.getLocation());
			ef.setTargetLocation(affected.getEyeLocation());
			ef.start();

			affected.setVelocity(awaketurret.turret.getEntity().getLocation().getDirection().multiply(0.4));
			SoloPlayerBattleListener.makeZDamage(affected, awaketurret.owner, type.getDamage(), DamageCauseZ.TURRET_L);

		}

	}

	@Override
	public void shoot(AwakeTurret awaketurret, AwakeTurret otherturret) {

		Entity e = otherturret.turret.getEntity();
		Guardian turret_entity = (Guardian) awaketurret.turret.getEntity();

		if (turret_entity.hasLineOfSight(e)) {

			e.getWorld().playSound(e.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 5, -2);

			awaketurret.turret.faceLocation(e.getLocation());
			awaketurret.damage_hud.faceLocation(e.getLocation());

			LineEffect ef = new LineEffect(Skywars.effectmanager);
			ef.particle = ParticleEffect.REDSTONE;
			ef.color = Color.RED;
			ef.visibleRange = 300;
			ef.setLocation(turret_entity.getLocation());
			ef.setTargetLocation(e.getLocation().clone().add(0, .5, 0));
			ef.start();

			otherturret.damage(awaketurret);

			return;
		}

	}

	@Override
	public void destroy(AwakeTurret at, Player destructor) {

		TurretUtil.explodeTower(type, at.turret.getEntity().getLocation());
		at.finalize();

	}

}
