package net.omniblock.skywars.patch.managers.chest.defaults.events;

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
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.omniblock.lobbies.utils.PlayerUtils;
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
import net.omniblock.skywars.patch.managers.chest.defaults.events.stuff.BombardierData;
import net.omniblock.skywars.patch.managers.chest.defaults.events.stuff.BombardierData.BombardierLauncherStatus;
import net.omniblock.skywars.patch.managers.chest.defaults.events.stuff.BombardierData.BombardierStatus;
import net.omniblock.skywars.patch.managers.chest.defaults.type.LegendaryItemType;
import net.omniblock.skywars.patch.types.SkywarsType;
import net.omniblock.skywars.util.CameraUtil;
import net.omniblock.skywars.util.Cinematix;
import net.omniblock.skywars.util.SoundPlayer;
import net.omniblock.skywars.util.TitleUtil;
import net.omniblock.skywars.util.block.SpawnBlock;

public class Bombardier implements Listener {

	public static Map<Player, BombardierData> BOMBARDIER_DATA = new HashMap<Player, BombardierData>();

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

									return;

								}
							}

							if(!Skywars.ingame)
								return;
							
							if(BombardierData.launching) {
								
								player.sendMessage(TextUtil.format("&c¡Ya alguien está utilizando el bombardero!"));
								return;
								
							}
							
							if(Lag.PLAYER_LOCATIONS.containsKey(player)){
								
								player.sendMessage(TextUtil.format("&c¡Ya estás usando otro poder Z!"));
								return;
							}
							
							ItemStack itemInHand = event.getPlayer().getItemInHand();
							if (itemInHand == null)
								return;
							if (itemInHand.getAmount() <= 1) {
								event.getPlayer().setItemInHand(null);
							} else {
								itemInHand.setAmount(itemInHand.getAmount() - 1);
							}

							BombardierData data = new BombardierData(player);
							data.useBombardier();
							
							BOMBARDIER_DATA.put(player, data);
							return;

						}
					} else if (event.getPlayer().getItemInHand().getItemMeta().getDisplayName()
							.contains(TextUtil.format("&8&lLANZAR BOMBA DE &c&lTNT")) && BOMBARDIER_DATA.containsKey(player) && BombardierData.launching) {

						BombardierData.launchingtask.cancel();
						
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
								if(player.hasLineOfSight(p)) {
									if(CameraUtil.getLookingAt(player, p)) {
										targetplayer = p;
									}
								}
							}
						}

						Location location = targetplayer != null ? targetplayer.getLocation()
								: targetblock.getLocation();
						
						launchBomb(event.getPlayer(), location);
						
						BOMBARDIER_DATA.get(event.getPlayer()).back(BombardierLauncherStatus.LAUNCHED);
						BOMBARDIER_DATA.remove(event.getPlayer());
						
						BombardierData.launching = false;
						return;

					}

				}
			}
		}

	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {

		if (BOMBARDIER_DATA.containsKey(e.getPlayer())) {

			if(BOMBARDIER_DATA.get(e.getPlayer()).getStatus() != BombardierStatus.LAUNCHING)
				return;
			
			Location from = e.getFrom();
			Location to = e.getTo();

			if (from.getY() != to.getY()) {

				Location location = MapManager.lobbyschematic.getLocation().clone().add(0.5, 0, 0.5);
				
				location.setYaw(e.getPlayer().getLocation().getYaw());
				location.setPitch(e.getPlayer().getLocation().getPitch());
				
				TitleUtil.sendTitleToPlayer(e.getPlayer(), 0, 40, 0, "",
						TextUtil.format("&c&l¡No puedes moverte Verticalmente!"));
				PlayerUtils.forceFly(e.getPlayer());

				e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 2, -2);
				e.getPlayer().teleport(location);
				return;

			}

		}

	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onDamage(EntityDamageEvent e) {
		
		if(CitizensAPI.getNPCRegistry().isNPC(e.getEntity())) {
			
			NPC npc = CitizensAPI.getNPCRegistry().getNPC(e.getEntity());
			
			for(BombardierData data : BOMBARDIER_DATA.values()) {
				
				if(data.getStatus() == BombardierStatus.LAUNCHED)
					continue;
				
				if(data.getClon().getClon().getUniqueId() == npc.getUniqueId()) {
					
					if(data.getElevationTask() != null)
						if(!data.getElevationTask().isCancelled())
							data.getElevationTask().cancel();
					
					if(data.getLaunchingtask() != null)
						if(!data.getLaunchingtask().isCancelled())
							data.getLaunchingtask().cancel();
					
					BombardierData.launchingtask.cancel();
					
					BOMBARDIER_DATA.get(data.getPlayer()).back(BombardierLauncherStatus.DAMAGE);
					BOMBARDIER_DATA.remove(data.getPlayer());
					BombardierData.launching = false;
					return;
					
				}
				
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

				for(BombardierData data : BOMBARDIER_DATA.values()) {
					
					if(data.getTnt().contains(fb)) {
						
						damager = data.getPlayer();
						break;
						
					}
					
				}

				if(damager != null) {
					
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
					
				}

				List<Block> cube = SpawnBlock.circle(fb.getLocation(), 5, 1, false, true, -1);

				for (Block b : cube) {
					if (b != null) {
						if (!CustomProtocolManager.PROTECTED_BLOCK_LIST.contains(b)) {
							b.setType(Material.AIR);
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

		Location av1 = new Location(based_loc.getWorld(), loc.getX(), based_loc.getY() - 20, loc.getZ() - 300);
		Location av2 = new Location(based_loc.getWorld(), loc.getX(), based_loc.getY() - 20, loc.getZ());
		Location av3 = new Location(based_loc.getWorld(), loc.getX(), based_loc.getY() - 20, loc.getZ() + 300);

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

		if (BOMBARDIER_DATA.containsKey(player)) {

			Location based_loc = MapManager.lobbyschematic.getLocation();

			Location av1 = new Location(based_loc.getWorld(), loc.getX(), based_loc.getY() - 20, loc.getZ() - 300);
			Location av2 = new Location(based_loc.getWorld(), loc.getX(), based_loc.getY() - 20, loc.getZ());
			Location av3 = new Location(based_loc.getWorld(), loc.getX(), based_loc.getY() - 20, loc.getZ() + 300);

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

					plane.getEquipment().setHelmet(new ItemStack(Material.IRON_BARDING, 1));
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

										BombardierData.launching = false;
										
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

		}

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