package net.omniblock.skywars.patch.managers.chest.defaults.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import net.omniblock.network.library.helpers.effectlib.effect.ExplodeEffect;
import net.omniblock.network.library.helpers.effectlib.effect.SkyRocketEffect;
import net.omniblock.network.library.utils.TextUtil;
import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.games.solo.events.SoloPlayerBattleListener;
import net.omniblock.skywars.games.solo.events.SoloPlayerBattleListener.DamageCauseZ;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;
import net.omniblock.skywars.games.teams.events.TeamPlayerBattleListener;
import net.omniblock.skywars.games.teams.managers.TeamPlayerManager;
import net.omniblock.skywars.patch.managers.CustomProtocolManager;
import net.omniblock.skywars.patch.managers.MapManager;
import net.omniblock.skywars.patch.managers.chest.defaults.events.stuff.ClonData;
import net.omniblock.skywars.patch.managers.chest.defaults.events.stuff.PlayerSavedData;
import net.omniblock.skywars.patch.managers.chest.defaults.events.type.ItemType;
import net.omniblock.skywars.patch.managers.chest.defaults.type.LegendaryItemType;
import net.omniblock.skywars.patch.types.SkywarsType;
import net.omniblock.skywars.util.ActionBarApi;
import net.omniblock.skywars.util.CameraUtil;
import net.omniblock.skywars.util.Cinematix;
import net.omniblock.skywars.util.ItemBuilder;
import net.omniblock.skywars.util.NumberUtil;
import net.omniblock.skywars.util.SoundPlayer;
import net.omniblock.skywars.util.TitleUtil;
import net.omniblock.skywars.util.block.SpawnBlock;

public class Bombardier implements ItemType, Listener {

	public static Map<Entity, Player> BOMBARDIER_OWNER = new HashMap<Entity, Player>();
	
	Map<Player, PlayerSavedData> SAVED_STATUS = new HashMap<Player, PlayerSavedData>();

	Map<Player, ClonData> BOMBARDIER_USE = new HashMap<Player, ClonData>();
	Map<Player, Boolean> LAUNCHER_SYSTEM = new HashMap<Player, Boolean>();

	@SuppressWarnings("deprecation")
	@EventHandler
	public void launchBombardier(PlayerInteractEvent event) {

		Player player = event.getPlayer();

		if (SoloPlayerManager.getPlayersInGameList().contains(player)
				|| TeamPlayerManager.getPlayersInGameList().contains(player)
						&& player.getGameMode() == GameMode.SURVIVAL) {

			if (event.getPlayer().getItemInHand().hasItemMeta()) {
				if (event.getPlayer().getItemInHand().getItemMeta().hasDisplayName()) {

					if (event.getPlayer().getItemInHand().getItemMeta().getDisplayName()
							.equalsIgnoreCase(LegendaryItemType.BOMBARDERO.getItem().getItemMeta().getDisplayName())) {

						if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK
								|| event.getAction() == Action.LEFT_CLICK_AIR
								|| event.getAction() == Action.LEFT_CLICK_BLOCK) {

							if (event.getClickedBlock() != null) {
								if (event.getClickedBlock().getType() == Material.CHEST
										|| event.getClickedBlock().getType() == Material.TRAPPED_CHEST
										|| event.getClickedBlock().getType() == Material.JUKEBOX) {

									event.setCancelled(true);
									return;

								}
							}

							ItemStack itemInHand = event.getPlayer().getItemInHand();
							if (itemInHand == null)
								return;
							if (itemInHand.getAmount() <= 1) {
								event.getPlayer().setItemInHand(null);
							} else {
								itemInHand.setAmount(itemInHand.getAmount() - 1);
							}

							useBombardier(event.getPlayer());
							return;

						}
					} else if (event.getPlayer().getItemInHand().getItemMeta().getDisplayName()
							.contains(TextUtil.format("&8&lLANZAR BOMBA DE &c&lTNT"))) {

						ItemStack itemInHand = event.getPlayer().getItemInHand();
						if (itemInHand == null)
							return;
						if (itemInHand.getAmount() <= 1) {
							event.getPlayer().setItemInHand(null);
						} else {
							itemInHand.setAmount(itemInHand.getAmount() - 1);
						}

						Block targetblock = event.getPlayer().getTargetBlock((Set<Material>) null, 200);
						Player targetplayer = null;

						for (Player p : player.getWorld().getEntitiesByClass(Player.class)) {
							if (SoloPlayerManager.getPlayersInGameList().contains(p)) {
								if (CameraUtil.getLookingAt(player, p)) {
									targetplayer = p;
								}
							}
						}

						Location location = targetplayer != null ? targetplayer.getLocation()
								: targetblock.getLocation();
						launchBomb(event.getPlayer(), location);
						return;

					}

				}
			}
		}

	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {

		if (BOMBARDIER_USE.containsKey(e.getPlayer())) {

			Location from = e.getFrom();
			Location to = e.getTo();

			if (from.getY() != to.getY()) {

				TitleUtil.sendTitleToPlayer(e.getPlayer(), 0, 40, 0, "",
						TextUtil.format("&c&l¡No puedes moverte Verticalmente!"));
				SoloPlayerManager.forceFly(e.getPlayer());

				e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 2, -2);
				e.getPlayer().teleport(MapManager.lobbyschematic.getLocation().clone().add(0.5, 0, 0.5));
				return;

			}

		}

	}

	@EventHandler
	public void explode(EntityChangeBlockEvent e) {
		if (e.getEntity() instanceof FallingBlock) {

			FallingBlock fb = (FallingBlock) e.getEntity();

			if (fb.hasMetadata("BOMBARDIER")) {

				e.setCancelled(true);

				Player damager = null;

				SoundPlayer.sendSound(fb.getLocation(), "skywars.generic_tnt_explosion", 3000);

				ExplodeEffect ef = new ExplodeEffect(Skywars.effectmanager);
				ef.visibleRange = 300;
				ef.setLocation(fb.getLocation());
				ef.start();

				SkyRocketEffect ef_2 = new SkyRocketEffect(Skywars.effectmanager);
				ef_2.visibleRange = 300;
				ef_2.setLocation(fb.getLocation());
				ef_2.start();

				if (BOMBARDIER_OWNER.containsKey(fb)) {
					damager = BOMBARDIER_OWNER.get(fb);
					BOMBARDIER_OWNER.remove(fb);
				}

				List<Entity> entities = fb.getNearbyEntities(3, 3, 3);
				for (Entity entity : entities) {

					if (entity.getType() == EntityType.PLAYER) {
						Player p = (Player) entity;

						if ((SoloPlayerManager.getPlayersInGameList().contains(p)
								|| TeamPlayerManager.getPlayersInGameList().contains(p))) {

							if (Skywars.currentMatchType == SkywarsType.SW_NORMAL_TEAMS
									|| Skywars.currentMatchType == SkywarsType.SW_INSANE_TEAMS
									|| Skywars.currentMatchType == SkywarsType.SW_Z_TEAMS) {

								TeamPlayerBattleListener.makeZDamage(p, damager, 6,
										net.omniblock.skywars.games.teams.events.TeamPlayerBattleListener.DamageCauseZ.BOMBARDIER);
								continue;

							}

							SoloPlayerBattleListener.makeZDamage(p, damager, 6, DamageCauseZ.BOMBARDIER);
							continue;

						}
					}

				}

				List<Block> cube = SpawnBlock.circle(fb.getLocation(), 5, 1, false, true, -1);

				for (Block b : cube) {
					if (b != null) {
						if (!CustomProtocolManager.PROTECTED_BLOCK_LIST.contains(b)) {
							if (NumberUtil.getRandomInt(1, 8) == 2) {
								bounceBlock(b, (float) (0.5), true);
							} else {
								b.setType(Material.AIR);
							}
						}
					}
				}

				fb.remove();
			}

		}
	}

	public static void launchNoPlaneBomb(Location loc) {

		Location based_loc = MapManager.lobbyschematic.getLocation();

		new BukkitRunnable() {

			int round = 0;

			@SuppressWarnings("deprecation")
			@Override
			public void run() {

				loc.setY(based_loc.getY());

				loc.getWorld().playSound(loc, Sound.ENTITY_FIREWORK_LARGE_BLAST, 20, 5);
				loc.getWorld().playSound(loc, Sound.ENTITY_FIREWORK_LARGE_BLAST_FAR, 20, 5);

				loc.getWorld().playSound(loc, Sound.ENTITY_FIREWORK_TWINKLE, 20, 5);
				loc.getWorld().playSound(loc, Sound.ENTITY_FIREWORK_TWINKLE_FAR, 20, 5);

				FallingBlock fb = loc.getWorld().spawnFallingBlock(loc.clone().add(0, -1, 0), Material.TNT, (byte) 0);

				fb.setDropItem(false);
				fb.setTicksLived(20 * 8);
				fb.setMetadata("BOMBARDIER", new FixedMetadataValue(Skywars.getInstance(), "dummy"));
				fb.setVelocity(new Vector(0, -2, 0).multiply(0.6));

				round++;

				if (round >= 5) {
					cancel();
					return;
				}

			}
		}.runTaskTimer(Skywars.getInstance(), 1L, 1L);

	}

	public static void launchNaturallyBomb(Location loc) {

		Location based_loc = MapManager.lobbyschematic.getLocation();

		Location av1 = new Location(based_loc.getWorld(), loc.getX(), based_loc.getY(), loc.getZ() - 300);
		Location av2 = new Location(based_loc.getWorld(), loc.getX(), based_loc.getY(), loc.getZ());
		Location av3 = new Location(based_loc.getWorld(), loc.getX(), based_loc.getY(), loc.getZ() + 300);

		final Cinematix cm = new Cinematix();
		cm.getPoints().add(av1);
		cm.getPoints().add(av2);
		cm.getPoints().add(av3);

		new BukkitRunnable() {

			boolean launcher = false;

			@Override
			public void run() {

				final ArmorStand plane = (ArmorStand) based_loc.getWorld().spawnEntity(av1, EntityType.ARMOR_STAND);
				plane.setGravity(false);
				plane.setVisible(false);

				plane.setItemInHand(new ItemStack(Material.IRON_BARDING, 1));

				cm.start(plane, 10);

				SoundPlayer.sendSound(plane.getLocation(), "skywars.planelow", 1000);

				new BukkitRunnable() {

					@SuppressWarnings("deprecation")
					@Override
					public void run() {

						if (plane.isValid()) {

							if (!plane.isDead()) {

								Location plane_loc = plane.getLocation();

								if (plane_loc.distance(av2) <= 1) {
									if (!launcher) {

										launcher = true;

										plane_loc.getWorld().playSound(plane_loc, Sound.ENTITY_BAT_TAKEOFF, 20, 5);

										new BukkitRunnable() {

											int round = 0;

											@Override
											public void run() {

												plane_loc.getWorld().playSound(plane_loc, Sound.ENTITY_FIREWORK_LARGE_BLAST, 20, 5);
												plane_loc.getWorld().playSound(plane_loc, Sound.ENTITY_FIREWORK_LARGE_BLAST_FAR, 20, 5);

												plane_loc.getWorld().playSound(plane_loc, Sound.ENTITY_FIREWORK_TWINKLE, 20, 5);
												plane_loc.getWorld().playSound(plane_loc, Sound.ENTITY_FIREWORK_TWINKLE_FAR, 20, 5);

												if (!plane.isDead()) {

													Location plane_loc = plane.getLocation();

													FallingBlock fb = plane_loc.getWorld().spawnFallingBlock(
															plane_loc.clone().add(0, -1, 0), Material.TNT, (byte) 0);

													fb.setDropItem(false);
													fb.setTicksLived(20 * 8);
													fb.setMetadata("BOMBARDIER",
															new FixedMetadataValue(Skywars.getInstance(), "dummy"));
													fb.setVelocity(new Vector(0, -2, 0).multiply(0.6));

												}

												round++;

												if (round >= 5) {
													cancel();
													return;
												}

											}
										}.runTaskTimer(Skywars.getInstance(), 1L, 1L);

									}
								}

								if (plane_loc == av3 || plane_loc.distance(av3) < 2) {

									cancel();
									plane.remove();
									return;

								}

							} else {

								cancel();
								return;

							}

						} else {

							cancel();
							return;

						}

					}

				}.runTaskTimer(Skywars.getInstance(), 1L, 1L);

			}
		}.runTaskLater(Skywars.getInstance(), 35L);

	}

	public void launchBomb(Player player, Location loc) {

		if (BOMBARDIER_USE.containsKey(player) && LAUNCHER_SYSTEM.containsKey(player)
				&& SAVED_STATUS.containsKey(player)) {

			ClonData data = BOMBARDIER_USE.get(player);

			if (data.getClon().isSpawned()) {

				SoloPlayerManager.emptyPlayer(player);

				PlayerSavedData psd = SAVED_STATUS.get(player);

				Location tracker = data.getClon().getEntity().getLocation();
				player.teleport(tracker);

				player.removePotionEffect(PotionEffectType.INVISIBILITY);

				player.setFoodLevel(psd.food);

				player.setHealth(psd.health);
				player.setFireTicks(0);

				player.setCanPickupItems(true);

				player.setExp(psd.exp);
				player.setLevel(psd.level);

				psd.apply();
				player.updateInventory();

				player.setGameMode(GameMode.SURVIVAL);

				SoloPlayerManager.forceRemoveFly(player);

				SoundPlayer.stopSound(player);
				SoundPlayer.sendSound(player, "skywars.radioc", true, 3);

				data.destroyClon(0);

			} else {

				PlayerSavedData psd = SAVED_STATUS.get(player);
				
				psd.apply();
				player.teleport(data.getSaved());

			}

			Location based_loc = MapManager.lobbyschematic.getLocation();

			Location av1 = new Location(based_loc.getWorld(), loc.getX(), based_loc.getY(), loc.getZ() - 300);
			Location av2 = new Location(based_loc.getWorld(), loc.getX(), based_loc.getY(), loc.getZ());
			Location av3 = new Location(based_loc.getWorld(), loc.getX(), based_loc.getY(), loc.getZ() + 300);

			final Cinematix cm = new Cinematix();
			cm.getPoints().add(av1);
			cm.getPoints().add(av2);
			cm.getPoints().add(av3);

			new BukkitRunnable() {

				boolean launcher = false;

				@Override
				public void run() {

					if (!av1.getChunk().isLoaded()) {
						av1.getChunk().load();
					}

					final ArmorStand plane = (ArmorStand) based_loc.getWorld().spawnEntity(av1, EntityType.ARMOR_STAND);
					plane.setGravity(false);
					plane.setVisible(false);

					plane.setItemInHand(new ItemStack(Material.IRON_BARDING, 1));
					cm.start(plane, 10);

					SoundPlayer.sendSound(plane.getLocation(), "skywars.planelow", 1000);

					new BukkitRunnable() {

						@SuppressWarnings("deprecation")
						@Override
						public void run() {

							if (plane.isValid()) {
								if (!plane.isDead()) {

									Location plane_loc = plane.getLocation();

									if (plane_loc.distance(av3) <= 10) {

										plane.remove();
										cancel();
										return;

									}

									if (plane_loc.distance(av2) <= 1) {
										if (!launcher) {

											launcher = true;

											plane_loc.getWorld().playSound(plane_loc, Sound.ENTITY_BAT_TAKEOFF, 20, 5);

											new BukkitRunnable() {

												int round = 0;

												@Override
												public void run() {

													plane_loc.getWorld().playSound(plane_loc, Sound.ENTITY_FIREWORK_LARGE_BLAST, 20, 5);
													plane_loc.getWorld().playSound(plane_loc, Sound.ENTITY_FIREWORK_LARGE_BLAST_FAR, 20, 5);

													plane_loc.getWorld().playSound(plane_loc, Sound.ENTITY_FIREWORK_TWINKLE, 20, 5);
													plane_loc.getWorld().playSound(plane_loc, Sound.ENTITY_FIREWORK_TWINKLE_FAR, 20, 5);

													if (!plane.isDead()) {

														Location plane_loc = plane.getLocation();

														FallingBlock fb = plane_loc.getWorld().spawnFallingBlock(
																plane_loc.clone().add(0, -1, 0), Material.TNT,
																(byte) 0);

														fb.setDropItem(false);
														fb.setTicksLived(20 * 8);
														fb.setMetadata("BOMBARDIER",
																new FixedMetadataValue(Skywars.getInstance(), "dummy"));
														fb.setVelocity(new Vector(0, -2, 0).multiply(0.6));

													}

													round++;

													if (round >= 5) {
														cancel();
														return;
													}

												}
											}.runTaskTimer(Skywars.getInstance(), 1L, 1L);

										}
									}

									if (plane_loc == av3 || plane_loc.distance(av3) < 2) {

										cancel();
										plane.remove();
										return;

									}

								} else {

									cancel();
									return;

								}

							} else {

								cancel();
								return;

							}

						}

					}.runTaskTimer(Skywars.getInstance(), 1L, 1L);

				}
			}.runTaskLater(Skywars.getInstance(), 35L);

			BOMBARDIER_USE.remove(player);
			LAUNCHER_SYSTEM.remove(player);

		}

	}

	@SuppressWarnings({ "serial", "deprecation" })
	public void useBombardier(Player player) {

		Location tp_loc = MapManager.lobbyschematic.getLocation().clone().add(0.5, 0, 0.5);
		tp_loc.setYaw(90L);
		tp_loc.setPitch(90L);

		Location active_loc = player.getLocation().clone();

		ClonData data = new ClonData(player, active_loc);
		data.makeClon();
		
		SAVED_STATUS.put(player, new PlayerSavedData(player));

		SoloPlayerManager.forceFly(player);
		SoloPlayerManager.emptyPlayer(player);

		player.setFoodLevel(20);

		player.setHealth(player.getMaxHealth());
		player.setFireTicks(0);

		player.setCanPickupItems(false);
		player.setLevel(0);

		player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false), false);

		SoundPlayer.sendSound(player, "skywars.bombardier_elevation", 1000);

		CameraUtil.travel(player, new ArrayList<Location>() {
			{
				add(active_loc);
				add(tp_loc);
			}
		}, 20 * 2, false);

		SoloPlayerManager.forceFly(player);

		new BukkitRunnable() {

			int round = 0;

			@Override
			public void run() {

				round++;

				if (!player.isOnline()) {
					cancel();
					return;
				}

				if (player.getLocation().distance(tp_loc) <= 5) {

					cancel();

					player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, -5);

					ItemStack launcher = new ItemBuilder(Material.BLAZE_POWDER).amount(1)
							.name(TextUtil.format("&8&lLANZAR BOMBA DE &c&lTNT")).build();
					player.getInventory().setItemInHand(launcher);

					new BukkitRunnable() {

						int seconds = 6;
						int click = 0;

						@Override
						public void run() {

							if (player.isOnline()) {

								click++;

								if (click == 10) {

									click = 0;
									seconds--;

								}

								if (BOMBARDIER_USE.containsKey(player)) {

									if (seconds <= 0) {

										cancel();

										ActionBarApi.sendActionBar(player,
												TextUtil.format("&c&l - &7Se te ha acabado el tiempo!"));

										if (BOMBARDIER_USE.containsKey(player) && LAUNCHER_SYSTEM.containsKey(player)
												&& SAVED_STATUS.containsKey(player)) {

											ClonData data = BOMBARDIER_USE.get(player);

											if (data.getClon().isSpawned()) {

												SoloPlayerManager.emptyPlayer(player);

												PlayerSavedData psd = SAVED_STATUS.get(player);
												
												Location tracker = data.getClon().getEntity().getLocation();
												player.teleport(tracker);

												player.removePotionEffect(PotionEffectType.INVISIBILITY);

												player.setFoodLevel(psd.food);

												player.setHealth(psd.health);
												player.setFireTicks(0);

												player.setCanPickupItems(true);

												player.setExp(psd.exp);
												player.setLevel(psd.level);

												psd.apply();
												player.updateInventory();

												player.setGameMode(GameMode.SURVIVAL);

												SoloPlayerManager.forceRemoveFly(player);

												SoundPlayer.stopSound(player);
												player.playSound(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 2, -2);

												data.destroyClon(0);

												BOMBARDIER_USE.remove(player);
												LAUNCHER_SYSTEM.remove(player);

											} else {

												player.teleport(data.getSaved());

												SoloPlayerManager.emptyPlayer(player);

												PlayerSavedData psd = SAVED_STATUS.get(player);
												
												Location tracker = data.getClon().getEntity().getLocation();
												player.teleport(tracker);

												player.removePotionEffect(PotionEffectType.INVISIBILITY);

												player.setFoodLevel(psd.food);

												player.setHealth(psd.health);
												player.setFireTicks(0);

												player.setCanPickupItems(true);

												player.setExp(psd.exp);
												player.setLevel(psd.level);

												psd.apply();
												player.updateInventory();

												player.setGameMode(GameMode.SURVIVAL);

												SoloPlayerManager.forceRemoveFly(player);

												SoundPlayer.stopSound(player);
												player.playSound(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 2, -2);

												BOMBARDIER_USE.remove(player);
												LAUNCHER_SYSTEM.remove(player);

											}

										}

										return;

									} else {

										ActionBarApi.sendActionBar(player,
												TextUtil.format("&c&l¡Apunta y Dispara! &8&l: &7Te quedan &a" + seconds
														+ " &7segundos."));
										return;

									}
								}

							} else {

								cancel();
								BOMBARDIER_USE.remove(player);
								LAUNCHER_SYSTEM.remove(player);

							}

							if (seconds <= 0) {

								cancel();
								if (BOMBARDIER_USE.containsKey(player)) {
									BOMBARDIER_USE.remove(player);
								}
								if (LAUNCHER_SYSTEM.containsKey(player)) {
									LAUNCHER_SYSTEM.remove(player);
								}

							}

						}
					}.runTaskTimer(Skywars.getInstance(), 2L, 2L);

					return;

				}

				if (round >= 20 * 5) {

					cancel();

					ClonData data = BOMBARDIER_USE.get(player);

					if (data.getClon().isSpawned()) {

						SoloPlayerManager.emptyPlayer(player);

						PlayerSavedData psd = SAVED_STATUS.get(player);
						
						Location tracker = data.getClon().getEntity().getLocation();
						player.teleport(tracker);

						player.removePotionEffect(PotionEffectType.INVISIBILITY);

						player.setFoodLevel(psd.food);

						player.setHealth(psd.health);
						player.setFireTicks(0);

						player.setCanPickupItems(true);

						player.setExp(psd.exp);
						player.setLevel(psd.level);

						psd.apply();
						player.updateInventory();

						player.setGameMode(GameMode.SURVIVAL);

						SoloPlayerManager.forceRemoveFly(player);

						SoundPlayer.stopSound(player);
						player.playSound(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 2, -2);

						data.destroyClon(0);

						BOMBARDIER_USE.remove(player);
						LAUNCHER_SYSTEM.remove(player);

					} else {

						player.teleport(data.getSaved());

						SoloPlayerManager.emptyPlayer(player);

						PlayerSavedData psd = SAVED_STATUS.get(player);
						
						player.removePotionEffect(PotionEffectType.INVISIBILITY);

						player.setFoodLevel(psd.food);

						player.setHealth(psd.health);
						player.setFireTicks(0);

						player.setCanPickupItems(true);

						player.setExp(psd.exp);
						player.setLevel(psd.level);

						psd.apply();
						player.updateInventory();

						player.setGameMode(GameMode.SURVIVAL);

						SoloPlayerManager.forceRemoveFly(player);

						SoundPlayer.stopSound(player);
						player.playSound(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 2, -2);

						BOMBARDIER_USE.remove(player);
						LAUNCHER_SYSTEM.remove(player);

					}

					return;

				}

			}
		}.runTaskTimer(Skywars.getInstance(), 1L, 1L);

		BOMBARDIER_USE.put(player, data);
		LAUNCHER_SYSTEM.put(player, false);

	}

	@SuppressWarnings("deprecation")
	public static void bounceBlock(Block b, float y_speed, boolean remove) {

		if (b == null)
			return;

		FallingBlock fb = b.getWorld().spawnFallingBlock(b.getLocation(), b.getType(), b.getData());

		fb.setMetadata("REMOVE", new FixedMetadataValue(Skywars.getInstance(), "dummy"));
		fb.setDropItem(false);

		b.setType(Material.AIR);

		float x = (float) -0.2 + (float) (Math.random() * ((0.2 - -0.2) + 0.2));
		float y = y_speed;
		float z = (float) -0.2 + (float) (Math.random() * ((0.2 - -0.2) + 0.2));

		fb.setVelocity(new Vector(x, y, z));
	}

}