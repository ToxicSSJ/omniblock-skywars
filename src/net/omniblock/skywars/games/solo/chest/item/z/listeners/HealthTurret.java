package net.omniblock.skywars.games.solo.chest.item.z.listeners;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.games.solo.chest.item.z.listeners.type.ItemType;
import net.omniblock.skywars.games.solo.chest.item.z.listeners.type.Turret;
import net.omniblock.skywars.games.solo.chest.item.z.listeners.type.TurretType;
import net.omniblock.skywars.games.solo.chest.item.z.listeners.type.Turret.TurretUtil.AwakeTurret;
import net.omniblock.skywars.games.solo.chest.item.z.listeners.type.Turret.TurretUtil.TurretBuilder;
import net.omniblock.skywars.games.solo.events.SoloPlayerCustomProtocols;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;
import net.omniblock.skywars.util.TextUtil;
import net.omniblock.skywars.util.effectlib.effect.LineEffect;
import net.omniblock.skywars.util.effectlib.util.ParticleEffect;

public class HealthTurret implements Turret, ItemType, Listener {
	
	TurretType type = TurretType.HEALTH_TURRET;
	
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
					Bukkit.broadcastMessage(TextUtil.getCenteredMessage("&r&7tipo &a&l" + type.getName_type() + "!"));
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
	   	final NPC e1 = registry.createNPC(type.getEntitytype(), TextUtil.format("&b&l▄▄▄▄▄▄▄▄▄▄▄▄"));
	   	final NPC a1 = registry.createNPC(EntityType.ARMOR_STAND, TextUtil.format("&9&lSana: &71❤/hit"));
	   	
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
	   	
	   	setEyeIA(awaketurret, 5, true);
	   	
	   	
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
					
					Collection<Entity> entities = awaketurret.turret.getEntity().getWorld().getNearbyEntities(awaketurret.turret.getEntity().getLocation(), 8, 8, 8);
					for(Entity e : entities) {
						
						if(e.getType() == EntityType.PLAYER) {
							
							Player p = (Player) e;
							
							if(!((p.getHealth() + 2.0) > p.getMaxHealth())) {
								
								if(SoloPlayerManager.getPlayersInGameList().contains(p)) {
									if(awaketurret.extra_exclude.contains(p) || p == awaketurret.owner) {
										
										Villager turret_entity = (Villager) awaketurret.turret.getEntity();
										if(turret_entity.hasLineOfSight(p)) {
											
											if(!shoot) {
												
												awaketurret.turret.faceLocation(p.getLocation());
												awaketurret.damage_hud.faceLocation(p.getLocation());
													  
												LineEffect ef = new LineEffect(Skywars.effectmanager);
												ef.particle = ParticleEffect.REDSTONE;
												
												if(round > 40 && round < 80) {
													ef.color = Color.fromRGB(0, 102, 0);
												} else if(round > 80 && round < 100) {
													ef.color = Color.fromRGB(0, 153, 0);
												}
												
												ef.visibleRange = 300;
												ef.particles = 5;
												ef.setLocation(turret_entity.getEyeLocation());
												ef.setTarget(p.getEyeLocation());
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
					}
					
					for(AwakeTurret otherturret : TurretUtil.awake_turrets) {
						
						if(otherturret != awaketurret) {
							if(awaketurret.turret.getEntity().getLocation().distance(otherturret.turret.getEntity().getLocation()) <= 8) {
								if(otherturret.extra_exclude.contains(awaketurret.owner) && awaketurret.extra_exclude.contains(otherturret.owner)){
									
									Entity e = otherturret.turret.getEntity();
									Villager turret_entity = (Villager) awaketurret.turret.getEntity();
									
									if(otherturret.getHealth() != otherturret.getMaxHealth()) {
										if(turret_entity.hasLineOfSight(e)) {
											
											if(!shoot) {
												
												awaketurret.turret.faceLocation(e.getLocation());
												awaketurret.damage_hud.faceLocation(e.getLocation());
													  
												LineEffect ef = new LineEffect(Skywars.effectmanager);
												ef.particle = ParticleEffect.REDSTONE;
												
												if(round > 40 && round < 80) {
													ef.color = Color.fromRGB(0, 102, 0);
												} else if(round > 80 && round < 100) {
													ef.color = Color.fromRGB(0, 153, 0);
												} else {
													ef.color = Color.LIME;
												}
												
												ef.visibleRange = 300;
												ef.particles = 5;
												ef.setLocation(turret_entity.getEyeLocation());
												ef.setTarget(e.getLocation());
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
			
			toshoot.getWorld().playSound(toshoot.getLocation(), Sound.VILLAGER_YES, 5, 1);
			toshoot.getWorld().playSound(toshoot.getLocation(), Sound.ORB_PICKUP, 5, 1);
			
			Player affected = (Player) toshoot;
			Villager turret_entity = (Villager) awaketurret.turret.getEntity();
			
			awaketurret.turret.faceLocation(affected.getLocation());
			awaketurret.damage_hud.faceLocation(affected.getLocation());
			
			turret_entity.playEffect(EntityEffect.VILLAGER_HAPPY);
			affected.playEffect(EntityEffect.VILLAGER_HEART);
			
			LineEffect ef = new LineEffect(Skywars.effectmanager);
			
			ef.particle = ParticleEffect.HEART;
			ef.visibleRange = 300;
			ef.particles = 4;
			
			ef.setLocation(turret_entity.getEyeLocation());
			ef.setTarget(affected.getEyeLocation());
			ef.start();
			
			if(!((affected.getHealth() + 2.0) > affected.getMaxHealth())) {
				affected.setHealth(affected.getHealth() + 2.0);
			}
			
		}

	}

	@Override
	public void shoot(AwakeTurret awaketurret, AwakeTurret otherturret) {
		
		Entity e = otherturret.turret.getEntity();
		Villager turret_entity = (Villager) awaketurret.turret.getEntity();
		
		if(turret_entity.hasLineOfSight(e)) {
				
			e.getWorld().playSound(e.getLocation(), Sound.VILLAGER_YES, 5, 1);
			e.getWorld().playSound(e.getLocation(), Sound.ORB_PICKUP, 5, 1);
				
			awaketurret.turret.faceLocation(e.getLocation());
			awaketurret.damage_hud.faceLocation(e.getLocation());
				
			turret_entity.playEffect(EntityEffect.VILLAGER_HAPPY);
			e.playEffect(EntityEffect.VILLAGER_HEART);
				
			LineEffect ef = new LineEffect(Skywars.effectmanager);
				
			ef.particle = ParticleEffect.HEART;
			ef.visibleRange = 300;
			ef.particles = 4;
				
			ef.setLocation(turret_entity.getEyeLocation());
			ef.setTarget(e.getLocation().add(0, .5, 0));
			ef.start();
				
			if(!((otherturret.getHealth() + 1) > otherturret.getMaxHealth())) {
				otherturret.health();
			}
				
			return;
			
		}
		
	}
	
	@Override
	public void destroy(AwakeTurret at, Player destructor) {
		
		TurretUtil.explodeTower(type, at.turret.getEntity().getLocation());
		at.finalize();
		
	}

}