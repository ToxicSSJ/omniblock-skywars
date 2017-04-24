package net.omniblock.skywars.patch.managers.chest.item.z;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowman;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;
import net.omniblock.skywars.patch.managers.CustomProtocolManager;
import net.omniblock.skywars.patch.managers.chest.item.z.type.ItemType;
import net.omniblock.skywars.patch.managers.chest.item.z.type.Turret;
import net.omniblock.skywars.patch.managers.chest.item.z.type.TurretType;
import net.omniblock.skywars.patch.managers.chest.item.z.type.Turret.TurretUtil.AwakeTurret;
import net.omniblock.skywars.patch.managers.chest.item.z.type.Turret.TurretUtil.TurretBuilder;
import net.omniblock.skywars.util.TextUtil;
import net.omniblock.skywars.util.effectlib.effect.LineEffect;
import net.omniblock.skywars.util.effectlib.util.ParticleEffect;

public class IceTurret implements Turret, ItemType, Listener {

	public Map<Player, Entry<Integer, BukkitTask>> slowness_levels = new HashMap<Player, Entry<Integer, BukkitTask>>();
	
	TurretType type = TurretType.ICE_TURRET;
	
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
					Bukkit.broadcastMessage(TextUtil.getCenteredMessage("&r&7tipo &b&l" + type.getName_type() + "!"));
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
	   	 
	   	l1.getBlock().setType(Material.HOPPER); CustomProtocolManager.PROTECTED_BLOCK_LIST.add(l1.getBlock()); awaketurret.components.add(l1.getBlock());
	   	l2.getBlock().setType(Material.CARPET); CustomProtocolManager.PROTECTED_BLOCK_LIST.add(l2.getBlock()); awaketurret.components.add(l2.getBlock());
	   	 
	   	@SuppressWarnings("deprecation")
		final ArmorStand _a = (ArmorStand) l3.getWorld().spawnCreature(l3, EntityType.ARMOR_STAND);
	   	_a.setCustomName(TextUtil.format("&8&l> &e&lTorreta " + type.getName_type() + " de " + constructor.getName() + " &8&l<"));
	   	_a.setVisible(false);
	   	_a.setCustomNameVisible(true);
	    _a.setGravity(false);
	   	 
	    awaketurret.info_hud = _a;
	    
	   	NPCRegistry registry = CitizensAPI.getNPCRegistry();
	   	final NPC e1 = registry.createNPC(type.getEntitytype(), TextUtil.format("&b&l▄▄▄▄▄"));
	   	final NPC a1 = registry.createNPC(EntityType.ARMOR_STAND, TextUtil.format("&9&lCongela: &72xNivel/hit"));
	   	
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
			int recoil = 1;
			
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
							if(SoloPlayerManager.getPlayersInGameList().contains(p)) {
								if(!awaketurret.extra_exclude.contains(p) && awaketurret.owner != p) {
									
									Snowman turret_entity = (Snowman) awaketurret.turret.getEntity();
									if(turret_entity.hasLineOfSight(p)) {
										
										if(!shoot) {
											
											awaketurret.turret.faceLocation(p.getLocation());
											awaketurret.damage_hud.faceLocation(p.getLocation());
												  
											LineEffect ef = new LineEffect(Skywars.effectmanager);
											ef.particle = ParticleEffect.REDSTONE;
											
											if(round > 0 && round < 10) {
												ef.color = Color.fromRGB(102, 102, 255);
											} else if(round > 10 && round < 20) {
												ef.color = Color.fromRGB(102, 178, 255);
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
			
			toshoot.getWorld().playSound(toshoot.getLocation(), Sound.ENDERDRAGON_WINGS, 5, -2);
			toshoot.getWorld().playSound(toshoot.getLocation(), Sound.DIG_SNOW, 5, -2);
			
			Player affected = (Player) toshoot;
			Snowman turret_entity = (Snowman) awaketurret.turret.getEntity();
			
			awaketurret.turret.faceLocation(affected.getLocation());
			awaketurret.damage_hud.faceLocation(affected.getLocation());
				  
			LineEffect ef = new LineEffect(Skywars.effectmanager);
			ef.particle = ParticleEffect.REDSTONE;
			ef.color = Color.BLUE;
			ef.visibleRange = 300;
			ef.setLocation(turret_entity.getEyeLocation());
			ef.setTarget(affected.getEyeLocation());
			ef.start();
			
			if(!slowness_levels.containsKey(affected)) {
				
				if(affected.hasPotionEffect(PotionEffectType.SLOW)) {
					
					affected.removePotionEffect(PotionEffectType.SLOW);
					affected.playSound(affected.getLocation(), Sound.CLICK, 10, -3);
					
				}
				
				affected.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 5, 2));
				
				slowness_levels.put(affected, new AbstractMap.SimpleEntry<Integer, BukkitTask>
				(1, new BukkitRunnable() {
					@Override
					public void run() {
						if(affected.isOnline()) {
							for(PotionEffect pe : affected.getActivePotionEffects()) {
								if(pe.getType() == PotionEffectType.SLOW) {
									affected.removePotionEffect(pe.getType());
									affected.playSound(affected.getLocation(), Sound.CLICK, 10, -3);
								}
							}
						}
						if(slowness_levels.containsKey(affected)) {
							slowness_levels.remove(affected);
						}
					}
				}.runTaskLater(Skywars.getInstance(), 4*20)));
				
			} else {
				
				slowness_levels.get(affected).getValue().cancel();
				int level = slowness_levels.get(affected).getKey();
				if((level + 1) != 6) {
					level++;
				}
				
				if(affected.hasPotionEffect(PotionEffectType.SLOW)) {
					
					affected.removePotionEffect(PotionEffectType.SLOW);
					affected.playSound(affected.getLocation(), Sound.CLICK, 10, -3);
					
				}
				
				switch(level) {
					case 0:
						affected.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 5, 1));
						affected.playSound(affected.getLocation(), Sound.COW_IDLE, 10, -3);
						break;
					case 1:
						affected.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 5, 2));
						affected.playSound(affected.getLocation(), Sound.COW_IDLE, 10, -6);
						break;
					case 2:
						affected.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 5, 4));
						affected.playSound(affected.getLocation(), Sound.COW_IDLE, 10, -9);
						break;
					case 3:
						affected.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 5, 8));
						affected.playSound(affected.getLocation(), Sound.COW_IDLE, 10, -12);
						break;
					case 4:
						affected.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 5, 16));
						affected.playSound(affected.getLocation(), Sound.COW_IDLE, 10, -15);
						break;
					case 5:
						affected.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 5, 10000));
						affected.playSound(affected.getLocation(), Sound.COW_IDLE, 10, -20);
						affected.playSound(affected.getLocation(), Sound.GLASS, 10, -20);
						affected.playSound(affected.getLocation(), Sound.IRONGOLEM_DEATH, 10, -20);
						affected.playSound(affected.getLocation(), Sound.SKELETON_HURT, 10, -20);
						break;
					default:
						affected.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 10000));
						affected.playSound(affected.getLocation(), Sound.COW_IDLE, 10, -20);
						affected.playSound(affected.getLocation(), Sound.GLASS, 10, -20);
						affected.playSound(affected.getLocation(), Sound.IRONGOLEM_DEATH, 10, -20);
						affected.playSound(affected.getLocation(), Sound.SKELETON_HURT, 10, -20);
						break;
				}
					
				slowness_levels.put(affected, new AbstractMap.SimpleEntry<Integer, BukkitTask>
				(level, new BukkitRunnable() {
					@Override
					public void run() {
						if(affected.isOnline()) {
							
							if(affected.hasPotionEffect(PotionEffectType.SLOW)) {
								
								affected.removePotionEffect(PotionEffectType.SLOW);
								affected.playSound(affected.getLocation(), Sound.CLICK, 10, -3);
								
							}
							
						}
						if(slowness_levels.containsKey(affected)) {
							slowness_levels.remove(affected);
						}
					}
				}.runTaskLater(Skywars.getInstance(), 4*20)));
				
			}
			
		}

	}

	@Override
	public void shoot(AwakeTurret at, AwakeTurret toshoot) {
		return;
	}
	
	@Override
	public void destroy(AwakeTurret at, Player destructor) {
		
		TurretUtil.explodeTower(type, at.turret.getEntity().getLocation());
		at.finalize();
		
	}
	
}
