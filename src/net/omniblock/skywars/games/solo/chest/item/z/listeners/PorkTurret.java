package net.omniblock.skywars.games.solo.chest.item.z.listeners;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.games.solo.chest.item.z.listeners.type.ItemType;
import net.omniblock.skywars.games.solo.chest.item.z.listeners.type.Turret;
import net.omniblock.skywars.games.solo.chest.item.z.listeners.type.TurretType;
import net.omniblock.skywars.games.solo.chest.item.z.listeners.type.Turret.TurretUtil.AwakeTurret;
import net.omniblock.skywars.games.solo.chest.item.z.listeners.type.Turret.TurretUtil.TurretBuilder;
import net.omniblock.skywars.games.solo.events.SoloPlayerBattleListener;
import net.omniblock.skywars.games.solo.events.SoloPlayerBattleListener.DamageCauseZ;
import net.omniblock.skywars.games.solo.events.SoloPlayerCustomProtocols;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;
import net.omniblock.skywars.util.TextUtil;
import net.omniblock.skywars.util.effectlib.effect.ExplodeEffect;
import net.omniblock.skywars.util.effectlib.effect.LineEffect;
import net.omniblock.skywars.util.effectlib.util.ParticleEffect;

public class PorkTurret implements Turret, ItemType, Listener {

	TurretType type = TurretType.PORK_TURRET;
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if(SoloPlayerManager.getPlayersInGameList().contains(e.getPlayer())) {
			if(e.getBlockPlaced().getType() == type.getMaterial()) {
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
				
				if(tb.isCanceled()) {
					
					cancel();
					place.setType(Material.AIR);
					TurretUtil.explodeTower(type, place.getLocation());
					return;
					
				} else if(tb.isCompleted()) {
					
					cancel();
					place.getLocation().getWorld().playSound(place.getLocation(), Sound.ZOMBIE_WOODBREAK, 4, 15);
					Bukkit.broadcastMessage(TextUtil.getCenteredMessage("&r"));
					Bukkit.broadcastMessage(TextUtil.getCenteredMessage("&r"));
					Bukkit.broadcastMessage(TextUtil.getCenteredMessage("&r&8&l&m[━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━]"));
					Bukkit.broadcastMessage(TextUtil.getCenteredMessage("&r&b&l» &7" + constructor.getName() + " ha utilizado su torreta"));
					Bukkit.broadcastMessage(TextUtil.getCenteredMessage("&r&7tipo &6&l" + type.getName_type() + "!"));
					Bukkit.broadcastMessage(TextUtil.getCenteredMessage("&r&8&l&m[━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━]"));
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
	   	 
	   	l1.getBlock().setType(Material.HOPPER); SoloPlayerCustomProtocols.PROTECTED_BLOCK_LIST.add(l1.getBlock()); awaketurret.components.add(l1.getBlock());
	   	l2.getBlock().setType(Material.CARPET); SoloPlayerCustomProtocols.PROTECTED_BLOCK_LIST.add(l2.getBlock()); awaketurret.components.add(l2.getBlock());
	   	 
	   	@SuppressWarnings("deprecation")
		final ArmorStand _a = (ArmorStand) l3.getWorld().spawnCreature(l3, EntityType.ARMOR_STAND);
	   	_a.setCustomName(TextUtil.format("&8&l> &e&lTorreta " + type.getName_type() + " de " + constructor.getName() + " &8&l<"));
	   	_a.setVisible(false);
	   	_a.setCustomNameVisible(true);
	    _a.setGravity(false);
	   	 
	    awaketurret.info_hud = _a;
	    
	   	NPCRegistry registry = CitizensAPI.getNPCRegistry();
	   	final NPC e1 = registry.createNPC(type.getEntitytype(), TextUtil.format("&b&l▄▄▄▄▄"));
	   	final NPC a1 = registry.createNPC(EntityType.ARMOR_STAND, TextUtil.format("&c&lDaño: &7" + type.getDamage() + "❤/cerdo"));
	   	
	   	e1.spawn(l3);
	   	a1.spawn(l2);
	   	e1.setProtected(true);
	   	
	   	a1.setProtected(true);
	   	a1.getEntity().setCustomNameVisible(false);
	   	
	   	 
	   	final ArmorStand _a1 = (ArmorStand) a1.getEntity();
	   	_a1.setGravity(true);
	   	 
	   	new BukkitRunnable(){ public void run() { _a1.setGravity(false);  } }.runTaskLater(Skywars.getInstance(), (long) (20*1.2));
	   	 
	   	awaketurret.turret = e1;
	   	awaketurret.damage_hud = a1;
	   	
	   	awaketurret.alive = true;
	   	
	   	setEyeIA(awaketurret, 12, true);
	   	
	}
	
	@Override
	public void setEyeIA(AwakeTurret awaketurret, int range, boolean onlyinmunity) {
		
		awaketurret.initialize();
		
		new BukkitRunnable() {
			
			int round = 0;
			int recoil = 5;
			
			boolean shoot = false;
			
			@Override
			public void run() {
				
				if(!awaketurret.turret.isSpawned()) {
					cancel();
					return;
				}
				
				if(awaketurret.turret.getEntity().isDead()) {
					cancel();
					return;
				}
				
				if(round >= (20 * recoil)) {
					round = 0;
					shoot = true;
				} else {
					round++;
				}
				
				if(awaketurret.alive) {
					
					Collection<Entity> entities = awaketurret.turret.getEntity().getWorld().getNearbyEntities(awaketurret.turret.getEntity().getLocation(), range, range, range);
					for(Entity e : entities) {
						
						if(e.getType() == EntityType.PLAYER) {
							
							Player p = (Player) e;
							if(SoloPlayerManager.getPlayersInGameList().contains(p)) {
								if(!awaketurret.extra_exclude.contains(p) && awaketurret.owner != p) {
									
									PigZombie turret_entity = (PigZombie) awaketurret.turret.getEntity();
									if(turret_entity.hasLineOfSight(p)) {
										
										if(!shoot) {
											
											awaketurret.turret.faceLocation(p.getLocation());
											awaketurret.damage_hud.faceLocation(p.getLocation());
												  
											if(round >= 20 * 4) {
												
												awaketurret.turret.getEntity().getWorld().playSound(awaketurret.turret.getEntity().getLocation(), Sound.FIREWORK_LARGE_BLAST2, 2, -2);
												
												LineEffect ef = new LineEffect(Skywars.effectmanager);
												
												ef.particle = ParticleEffect.DRIP_LAVA;
												ef.visibleRange = 300;
												ef.particles = 10;
												
												ef.setLocation(turret_entity.getEyeLocation());
												ef.setTarget(p.getEyeLocation());
												ef.start();
											}
											
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
					
					for(AwakeTurret otherturret : TurretUtil.awake_turrets) {
						
						if(otherturret != awaketurret) {
							if(awaketurret.turret.getEntity().getLocation().distance(otherturret.turret.getEntity().getLocation()) <= range) {
								if(!(otherturret.extra_exclude.contains(awaketurret.owner) && awaketurret.extra_exclude.contains(otherturret.owner))){
									
									Entity e = otherturret.turret.getEntity();
									PigZombie turret_entity = (PigZombie) awaketurret.turret.getEntity();
									
									if(turret_entity.hasLineOfSight(e)) {
										
										if(!shoot) {
											
											awaketurret.turret.faceLocation(e.getLocation());
											awaketurret.damage_hud.faceLocation(e.getLocation());
												  
											if(round >= 20 * 4) {
												
												awaketurret.turret.getEntity().getWorld().playSound(awaketurret.turret.getEntity().getLocation(), Sound.FIREWORK_LARGE_BLAST2, 2, -2);
												
												LineEffect ef = new LineEffect(Skywars.effectmanager);
												
												ef.particle = ParticleEffect.DRIP_LAVA;
												ef.visibleRange = 300;
												ef.particles = 10;
												
												ef.setLocation(turret_entity.getEyeLocation());
												ef.setTarget(e.getLocation().clone().add(0, .5, 0));
												ef.start();
											}
											
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
		
		if(toshoot.getType() == EntityType.PLAYER) {
			  
			Player affected = (Player) toshoot;
			PigZombie turret_entity = (PigZombie) awaketurret.turret.getEntity();
			
			toshoot.getWorld().playSound(toshoot.getLocation(), Sound.ENDERDRAGON_HIT, 2, -2);
			
			awaketurret.turret.faceLocation(affected.getLocation());
			awaketurret.damage_hud.faceLocation(affected.getLocation());
				  
			LineEffect ef = new LineEffect(Skywars.effectmanager);
			ef.particle = ParticleEffect.FLAME;
			ef.visibleRange = 300;
			ef.particles = 20;
			ef.setLocation(turret_entity.getEyeLocation());
			ef.setTarget(affected.getEyeLocation());
			ef.start();
			
			@SuppressWarnings("deprecation")
			final Pig bullet = (Pig) turret_entity.getEyeLocation().getWorld().spawnCreature(turret_entity.getEyeLocation(), EntityType.PIG);
			
			bullet.setBaby();
			bullet.setMaxHealth(900);
			bullet.setHealth(900);
			bullet.setNoDamageTicks(10000000);
			
			movePorkBullet(bullet, turret_entity, affected.getEyeLocation().clone().add(0.3, 0.3, 0.3));
			
			new BukkitRunnable() {
				
				List<Player> explosion_affected = new ArrayList<Player>();
				List<AwakeTurret> explosion_turret_affected = new ArrayList<AwakeTurret>();
				
				boolean explode = false;
				
				int countdown = 3;
				int round = 0;
				
				@Override
				public void run() {
					
					if(!awaketurret.turret.isSpawned()) {
						cancel();
						return;
					}
					
					if(awaketurret.turret.getEntity().isDead()) {
						cancel();
						return;
					}
					
					if(countdown <= 0) {
						explode = true;
					}
					
					if(round >= 20) {
						round = 0;
						countdown--; 
					} else {
						round++;
					}
					
					Collection<Entity> entities = awaketurret.turret.getEntity().getWorld().getNearbyEntities(bullet.getLocation(), 0.3, 0.3, 0.3);
					
					for(Entity e : entities) {
						if(e.getType() == EntityType.PLAYER) {
							Player p = (Player) e;
							if(SoloPlayerManager.getPlayersInGameList().contains(p)) {
								explode = true;
								if(!explosion_affected.contains(p)) {
									explosion_affected.add(p);
									continue;
								}
							}
						}
					}
					
					for(AwakeTurret otherturret : TurretUtil.awake_turrets) {
						
						if(otherturret != awaketurret) {
							if(awaketurret.turret.getEntity().getLocation().distance(otherturret.turret.getEntity().getLocation()) <= 12) {
								if(!(otherturret.extra_exclude.contains(awaketurret.owner) && awaketurret.extra_exclude.contains(otherturret.owner))){
									explode = true;
									if(!explosion_turret_affected.contains(otherturret)) {
										explosion_turret_affected.add(otherturret);
										continue;
									}
								}
							}
						}
						
					}
					
					if(explode) {
						
						cancel();
						
						Collection<Entity> nearby_entities = awaketurret.turret.getEntity().getWorld().getNearbyEntities(bullet.getLocation(), 0.5, 0.5, 0.5);
						
						for(Entity e : nearby_entities) {
							if(e.getType() == EntityType.PLAYER) {
								Player p = (Player) e;
								if(SoloPlayerManager.getPlayersInGameList().contains(p)) {
									if(!explosion_affected.contains(p)) {
										explosion_affected.add(p);
										continue;
									}
								}
							}
						}
						
						for(AwakeTurret otherturret : TurretUtil.awake_turrets) {
							
							if(otherturret != awaketurret) {
								if(awaketurret.turret.getEntity().getLocation().distance(otherturret.turret.getEntity().getLocation()) <= 12) {
									if(!(otherturret.extra_exclude.contains(awaketurret.owner) && awaketurret.extra_exclude.contains(otherturret.owner))){
										explode = true;
										if(!explosion_turret_affected.contains(otherturret)) {
											explosion_turret_affected.add(otherturret);
											continue;
										}
									}
								}
							}
							
						}
						
						if(!explosion_affected.isEmpty()) {
							for(Player p : explosion_affected) {
								
								p.setVelocity(awaketurret.turret.getEntity().getLocation().getDirection().multiply(0.4));
								SoloPlayerBattleListener.makeZDamage(p, awaketurret.owner, type.getDamage(), DamageCauseZ.TURRET_P);
								
							}
						}
						
						if(!explosion_turret_affected.isEmpty()) {
							for(AwakeTurret at : explosion_turret_affected) {
								for(int i = 0; i < 7; i++) {
									if(at.alive) {
										at.damage(awaketurret);
									} else {
										continue;
									}
								}
							}
						}
						
						ExplodeEffect ef_2 = new ExplodeEffect(Skywars.effectmanager);
						ef_2.visibleRange = 50;
						ef_2.amount = 1;
						ef_2.setLocation(bullet.getLocation());
						ef_2.setTarget(bullet.getLocation());
						ef_2.start();
						
						bullet.getWorld().createExplosion(bullet.getLocation(), 1, true);
						bullet.getWorld().playSound(bullet.getLocation(), Sound.PIG_DEATH, 2, 1);
						bullet.getWorld().playSound(bullet.getLocation(), Sound.EXPLODE, 2, 1);
						bullet.setCustomNameVisible(false);
						
						bullet.remove();
						  
					}
					
				}
			}.runTaskTimer(Skywars.getInstance(), 0L, 1L);
			
		}
		
	}
	
	@Override
	public void shoot(AwakeTurret awaketurret, AwakeTurret otherturret) {
		
		if(otherturret == awaketurret) {
			return;
		}
		
		Entity e = otherturret.turret.getEntity();
		PigZombie turret_entity = (PigZombie) awaketurret.turret.getEntity();
		
	    e.getWorld().playSound(e.getLocation(), Sound.ENDERDRAGON_HIT, 2, -2);
		
		awaketurret.turret.faceLocation(e.getLocation());
		awaketurret.damage_hud.faceLocation(e.getLocation());
			  
		LineEffect ef = new LineEffect(Skywars.effectmanager);
		ef.particle = ParticleEffect.FLAME;
		ef.visibleRange = 300;
		ef.particles = 20;
		ef.setLocation(turret_entity.getEyeLocation());
		ef.setTarget(e.getLocation());
		ef.start();
		
		@SuppressWarnings("deprecation")
		final Pig bullet = (Pig) turret_entity.getEyeLocation().getWorld().spawnCreature(turret_entity.getEyeLocation(), EntityType.PIG);
		
		bullet.setBaby();
		bullet.setMaxHealth(900);
		bullet.setHealth(900);
		bullet.setNoDamageTicks(10000000);
		
		movePorkBullet(bullet, turret_entity, e.getLocation().clone().add(0.3, 0.3, 0.3));
		
		new BukkitRunnable() {
			
			List<Player> explosion_affected = new ArrayList<Player>();
			List<AwakeTurret> explosion_turret_affected = new ArrayList<AwakeTurret>();
			
			boolean explode = false;
			
			int countdown = 3;
			int round = 0;
			
			@Override
			public void run() {
				
				if(countdown <= 0) {
					explode = true;
				}
				
				if(round >= 20) {
					round = 0;
					countdown--; 
				} else {
					round++;
				}
				
				Collection<Entity> entities = awaketurret.turret.getEntity().getWorld().getNearbyEntities(bullet.getLocation(), 0.3, 0.3, 0.3);
				
				for(Entity e : entities) {
					if(e.getType() == EntityType.PLAYER) {
						Player p = (Player) e;
						if(SoloPlayerManager.getPlayersInGameList().contains(p)) {
							explode = true;
							if(!explosion_affected.contains(p)) {
								explosion_affected.add(p);
								continue;
							}
						}
					}
				}
				
				for(AwakeTurret otherturret : TurretUtil.awake_turrets) {
					
					if(otherturret != awaketurret) {
						if(awaketurret.turret.getEntity().getLocation().distance(otherturret.turret.getEntity().getLocation()) <= 12) {
							if(!(otherturret.extra_exclude.contains(awaketurret.owner) && awaketurret.extra_exclude.contains(otherturret.owner))){
								explode = true;
								if(!explosion_turret_affected.contains(otherturret)) {
									explosion_turret_affected.add(otherturret);
									continue;
								}
							}
						}
					}
					
				}
				
				if(explode) {
					
					cancel();
					
					Collection<Entity> nearby_entities = awaketurret.turret.getEntity().getWorld().getNearbyEntities(bullet.getLocation(), 1, 1, 1);
					
					for(Entity e : nearby_entities) {
						if(e.getType() == EntityType.PLAYER) {
							Player p = (Player) e;
							if(SoloPlayerManager.getPlayersInGameList().contains(p)) {
								if(!explosion_affected.contains(p)) {
									explosion_affected.add(p);
									continue;
								}
							}
						}
					}
					
					for(AwakeTurret otherturret : TurretUtil.awake_turrets) {
						
						if(otherturret != awaketurret) {
							if(awaketurret.turret.getEntity().getLocation().distance(otherturret.turret.getEntity().getLocation()) <= 12) {
								if(!(otherturret.extra_exclude.contains(awaketurret.owner) && awaketurret.extra_exclude.contains(otherturret.owner))){
									explode = true;
									if(!explosion_turret_affected.contains(otherturret)) {
										explosion_turret_affected.add(otherturret);
										continue;
									}
								}
							}
						}
						
					}
					
					if(!explosion_affected.isEmpty()) {
						for(Player p : explosion_affected) {
							
							p.setVelocity(p.getLocation().getDirection().multiply(0.4));
							SoloPlayerBattleListener.makeZDamage(p, awaketurret.owner, type.getDamage(), DamageCauseZ.TURRET_P);
							
						}
					}
					
					if(!explosion_turret_affected.isEmpty()) {
						for(AwakeTurret at : explosion_turret_affected) {
							for(int i = 0; i < 7; i++) {
								if(at.alive) {
									at.damage(awaketurret);
								} else {
									continue;
								}
							}
						}
					}
					
					ExplodeEffect ef_2 = new ExplodeEffect(Skywars.effectmanager);
					ef_2.visibleRange = 50;
					ef_2.amount = 1;
					ef_2.setLocation(bullet.getLocation());
					ef_2.setTarget(bullet.getLocation());
					ef_2.start();
					
					bullet.getWorld().createExplosion(bullet.getLocation(), 1, true);
					bullet.getWorld().playSound(bullet.getLocation(), Sound.PIG_DEATH, 2, 1);
					bullet.getWorld().playSound(bullet.getLocation(), Sound.EXPLODE, 2, 1);
					bullet.setCustomNameVisible(false);
					
					bullet.remove();
					  
				}
				
			}
		}.runTaskTimer(Skywars.getInstance(), 0L, 1L);
		
	}
	
	@Override
	public void destroy(AwakeTurret at, Player destructor) {
		
		TurretUtil.explodeTower(type, at.turret.getEntity().getLocation());
		at.finalize();
		
	}
	
	public void movePorkBullet(Pig bullet, PigZombie turret, Location to) {
		
		double distance = to.distance(bullet.getLocation());
		double multiply = 2.0;
		
		if(distance >= 0 && distance <= 2) {
			multiply = 0.5;
		} else if(distance >= 2 && distance <= 3) {
			multiply = 0.8;
		} else if(distance >= 3 && distance <= 5) {
			multiply = 1.2;
		} else if(distance >= 5 && distance <= 8) {
			multiply = 1.6;
		} else if(distance >= 8 && distance <= 12) {
			multiply = 2.0;
		}
		
		Location loc = bullet.getLocation();
        double x = loc.getX() - to.getX();
        double y = loc.getY() - to.getY();
        double z = loc.getZ() - to.getZ();
        
        Vector v = new Vector(x, y, z).normalize().multiply(-multiply);
		bullet.setVelocity(v);
		
	}
	
}
