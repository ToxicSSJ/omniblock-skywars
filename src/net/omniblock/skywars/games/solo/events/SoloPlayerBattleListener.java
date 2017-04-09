package net.omniblock.skywars.games.solo.events;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.games.solo.chest.item.z.listeners.type.Turret.TurretUtil;
import net.omniblock.skywars.games.solo.chest.item.z.listeners.type.Turret.TurretUtil.AwakeTurret;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;
import net.omniblock.skywars.games.solo.object.PlayerBattleInfo;
import net.omniblock.skywars.util.ActionBarApi;
import net.omniblock.skywars.util.NumberUtil;
import omniblock.on.util.TextUtil;

public class SoloPlayerBattleListener implements Listener {

	public Map<Player, BukkitTask> contamination_infected = new HashMap<Player, BukkitTask>();
	
	public static Map<Player, PlayerBattleInfo> battle_info = new HashMap<Player, PlayerBattleInfo>();
	public Map<Player, Map<Player, BukkitTask>> assistence = new HashMap<Player, Map<Player, BukkitTask>>();
	
	public Map<Player, Player> lasthit = new HashMap<Player, Player>();
	public Map<Player, BukkitTask> lasthit_timer = new HashMap<Player, BukkitTask>();
	
	
	public static void setBattleInfo() {
		
		for(Player k : SoloPlayerManager.getPlayersInGameList()) {
			
			PlayerBattleInfo pbi = new PlayerBattleInfo(k);
			battle_info.put(k, pbi);
			
		}
		
	}
	
	@EventHandler
	public void processDamage(EntityDamageEvent e) {
		
		if(!Skywars.ingame) {
			e.setCancelled(true);
			return;
		}
	
		if(e.getEntity().getType() == EntityType.PLAYER) {
			
			Player affected = (Player) e.getEntity(); 
			if(SoloPlayerManager.getPlayersInGameList().contains(affected)) {
				if(e.getCause() != DamageCause.ENTITY_ATTACK) {
					if(e.getCause() == DamageCause.VOID) {
						
						e.setCancelled(true);
						DeathMessages.P2A_VOID.broadcastMessage(affected);
						killPlayer(null, affected);
						return;
					}
					
					if(affected.getHealth() - e.getFinalDamage() <= 0) {
						
						/**
						 * @Specials
						 * 
						 * - For Z List.
						 * 
						 * - Turrets
						 * - Contamination
						 * - Anothers!
						 */
						
						Player damager = null;
						
						/**
						 * @Commons
						 * 
						 * - For Normal Game List.
						 */
						
						if(e.getCause() == DamageCause.DROWNING) {
							
							if(lasthit.containsKey(affected)) {
								e.setCancelled(true);
								damager = lasthit.get(affected);
								killPlayer(affected, damager);
								DeathMessages.P2P_WATER.broadcastMessage(affected, damager);
								return;
							} else {
								e.setCancelled(true);
								killPlayer(affected);
								DeathMessages.P2A_WATER.broadcastMessage(affected);
								return;
							}
							
						}

						if(e.getCause() == DamageCause.FIRE || e.getCause() == DamageCause.FIRE_TICK) {
							
							if(lasthit.containsKey(affected)) {
								e.setCancelled(true);
								damager = lasthit.get(affected);
								killPlayer(affected, damager);
								DeathMessages.P2P_FIRE.broadcastMessage(affected, damager);
								return;
							} else {
								e.setCancelled(true);
								killPlayer(affected);
								DeathMessages.P2A_FIRE.broadcastMessage(affected);
								return;
							}
						}

						if(e.getCause() == DamageCause.LAVA) {
							
							if(lasthit.containsKey(affected)) {
								e.setCancelled(true);
								damager = lasthit.get(affected);
								killPlayer(affected, damager);
								DeathMessages.P2P_LAVA.broadcastMessage(affected, damager);
								return;
							} else {
								e.setCancelled(true);
								killPlayer(affected);
								DeathMessages.P2A_LAVA.broadcastMessage(affected);
								return;
							}
						}

						if(e.getCause() == DamageCause.FALL) {
							
							if(lasthit.containsKey(affected)) {
								e.setCancelled(true);
								damager = lasthit.get(affected);
								killPlayer(affected, damager);
								DeathMessages.P2P_JUMP.broadcastMessage(affected, damager);
								return;
							} else {
								e.setCancelled(true);
								killPlayer(affected);
								DeathMessages.P2A_JUMP.broadcastMessage(affected);
								return;
							}
							
						}
						
						if(lasthit.containsKey(affected)) {
							e.setCancelled(true);
							damager = lasthit.get(affected);
							killPlayer(affected, damager);
							DeathMessages.P2P_COMMON.broadcastMessage(affected, damager);
							return;
						} else {
							e.setCancelled(true);
							killPlayer(affected);
							DeathMessages.P2A_COMMON.broadcastMessage(affected);
							return;
						}
						
					}
					
				}
			} else {
				e.setCancelled(true);
				return;
			}
			
		}
		
		
	}
	
	@EventHandler
	public void attackAnything(EntityDamageByEntityEvent e) {
		
		if(!Skywars.ingame) {
			e.setCancelled(true);
			return;
		}
		
		if(e.getDamager().getType() == EntityType.PLAYER && 
				e.getEntity().getType() == EntityType.PLAYER) {
			
			Player damager = (Player) e.getDamager();
			Player affected = (Player) e.getEntity();
			
			if(SoloPlayerManager.getPlayersInGameList().contains(damager) &&
					SoloPlayerManager.getPlayersInGameList().contains(affected)) {
				
				attackFilter(damager, affected, e, false);
				
			}
			
			return;
		}
		
		if(e.getDamager().getType() == EntityType.ARROW &&
				e.getEntity().getType() == EntityType.PLAYER) {
			
			Projectile arrow = (Projectile) e.getDamager();
			
			if(arrow.getShooter() instanceof Player) {
				
				Player damager = (Player) arrow.getShooter();
				Player affected = (Player) e.getEntity();
				
				if(SoloPlayerManager.getPlayersInGameList().contains(damager) &&
						SoloPlayerManager.getPlayersInGameList().contains(affected)) {
					
					attackFilter(damager, affected, e, true);
					
				}
				
			}
			
			return;
		}
		
		if(e.getDamager().getType() == EntityType.SNOWBALL &&
				e.getEntity().getType() == EntityType.PLAYER) {
			
			Projectile snowball = (Projectile) e.getDamager();
			
			if(snowball.getShooter() instanceof Player) {
				
				Player damager = (Player) snowball.getShooter();
				Player affected = (Player) e.getEntity();
				
				if(SoloPlayerManager.getPlayersInGameList().contains(damager) &&
						SoloPlayerManager.getPlayersInGameList().contains(affected)) {
					
					attackFilter(damager, affected, e, true);
					
				}
				
			} else if(snowball.hasMetadata("CONTAMINATION_BULLET")) {
				
				Player affected = (Player) e.getEntity();
				Player damager = null;
				Vector v = snowball.getVelocity();
				
				if(SoloPlayerManager.getPlayersInGameList().contains(affected)) {
					
					if((affected.getHealth() - 0.3) <= 0) {
						
						if(contamination_infected.containsKey(affected)) {
							if(lasthit.containsKey(affected)) {
								e.setCancelled(true);
								damager = lasthit.get(affected);
								killPlayer(affected, damager);
								DeathMessages.P2P_Z_CONTAMINATION.broadcastMessage(affected, damager);
								return;
							} else {
								e.setCancelled(true);
								killPlayer(affected);
								DeathMessages.P2A_Z_CONTAMINATION.broadcastMessage(affected);
								return;
							}
						}
						
					} else {
						affected.damage(0.8);
					}
					
					affected.setVelocity(v.normalize().multiply(0.4));
					
					affected.getWorld().playEffect(affected.getLocation(), Effect.SNOWBALL_BREAK, 30);
					affected.getWorld().playEffect(affected.getLocation(), Effect.CRIT, 30);
					
					affected.getWorld().playSound(affected.getLocation(), Sound.IRONGOLEM_HIT, 5, -2);
					affected.getWorld().playSound(affected.getLocation(), Sound.ITEM_BREAK, 5, -2);
					
					affected.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 120, 1, true));
					affected.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 120, 1, true));
					
					affected.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 1, 1, true));
					affected.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 120, 1, true));
					
					if(!contamination_infected.containsKey(affected)) {
						affected.setMetadata("CONTAMINATED", new FixedMetadataValue(Skywars.getInstance(), "dummy"));
						contamination_infected.put(affected, new BukkitRunnable(){
							@Override
							public void run() {
								if(affected.isOnline() && SoloPlayerManager.getPlayersInGameList().contains(affected)) {
									if(contamination_infected.containsKey(affected)) {
										contamination_infected.remove(affected);
									}
								}
								cancel();
							}
						}.runTaskLater(Skywars.getInstance(), 5 * 20));
					} else {
						affected.setMetadata("CONTAMINATED", new FixedMetadataValue(Skywars.getInstance(), "dummy"));
						contamination_infected.get(affected).cancel();
						contamination_infected.put(affected, new BukkitRunnable(){
							@Override
							public void run() {
								if(affected.isOnline() && SoloPlayerManager.getPlayersInGameList().contains(affected)) {
									if(contamination_infected.containsKey(affected)) {
										contamination_infected.remove(affected);
									}
								}
								cancel();
							}
						}.runTaskLater(Skywars.getInstance(), 5 * 20));
					}
					
				}
				
			}
			
			return;
		}
		
		/**
		 * @Specials
		 * 
		 * - For Z List.
		 * 
		 * - Turrets
		 * - Contamination
		 * - Anothers!
		 */
			
		
		if(e.getDamager().getType() == EntityType.PLAYER) {
			
			Player damager = (Player) e.getDamager();
			
			if(SoloPlayerManager.getPlayersInGameList().contains(damager)) {
				
				for(AwakeTurret at : TurretUtil.awake_turrets) {
					if(at.turret.getEntity() == e.getEntity()) {
						e.setCancelled(true);
						//if(at.owner != damager) { TODO
							at.damage(damager);
						//}
					}
				}
				
			}
		}
		
		if(e.getDamager().getType() == EntityType.ARROW) {
			
			Projectile pj = (Projectile) e.getDamager();
			if(pj.getShooter() instanceof Player) {
				
				Player damager = (Player) pj.getShooter();
				
				if(SoloPlayerManager.getPlayersInGameList().contains(damager)) {
					for(AwakeTurret at : TurretUtil.awake_turrets) {
						if(at.turret.getEntity() == e.getEntity()) {
							e.setCancelled(true);
							pj.remove();
							//if(at.owner != damager) { TODO
								at.damage(damager);
							//}
						}
					}
				}
				
			}
			
		}
		
		
	}
	
	public void attackFilter(Player damager, Player affected, EntityDamageByEntityEvent e, boolean arrow) {
		
		double health = affected.getHealth();
		if((health - e.getFinalDamage()) <= 0) {
			
			e.setCancelled(true);
			killPlayer(damager, affected);
			
			if(arrow) {
				DeathMessages.P2P_ARROWS.broadcastMessage(affected);
				return;
			}
			DeathMessages.P2P_WEAPON.broadcastMessage(affected);
			return;
			
		} else {
			addToAssistence(damager, affected, 8);
			addToLastHitList(damager, affected, 8);
			return;
			
		}
		
	}
	
	public void addToAssistence(Player damager, Player affected, int time) {
		
		if(assistence.containsKey(affected)) {
			
			Map<Player, BukkitTask> players = assistence.get(affected);
			if(players.containsKey(damager)) {
				
				players.get(damager).cancel();
				players.remove(damager);
				
				players.put(damager, new BukkitRunnable() {
					@Override
					public void run() {
						if(assistence.containsKey(affected)) {
							Map<Player, BukkitTask> cache = assistence.get(affected);
							if(cache.containsKey(damager)) {
								
								cache.remove(damager);
								assistence.put(affected, cache);
								return;
								
							}
						}
						
					}
				}.runTaskLater(Skywars.getInstance(), time * 20));
				
				assistence.put(affected, players);
				return;
				
			} else {
				
				players.put(damager, new BukkitRunnable() {
					@Override
					public void run() {
						if(assistence.containsKey(affected)) {
							Map<Player, BukkitTask> cache = assistence.get(affected);
							if(cache.containsKey(damager)) {
								
								cache.remove(damager);
								assistence.put(affected, cache);
								return;
								
							}
						}
						
					}
				}.runTaskLater(Skywars.getInstance(), time * 20));
				
				assistence.put(affected, players);
				return;
				
			}
			
		} else {
			
			Map<Player, BukkitTask> players = assistence.get(affected);
			players.put(damager, null);
			addToAssistence(damager, affected, time);
			return;
		}
		
	}
	
	public void addToLastHitList(Player damager, Player affected, int time) {
		
		if(lasthit.containsKey(affected)) {
			lasthit.remove(affected);
		}
		
		if(lasthit_timer.containsKey(affected)) {
			
			BukkitTask bt = lasthit_timer.get(affected);
			bt.cancel();
			
			lasthit_timer.remove(affected);
			
		}
		
		lasthit.put(affected, damager);
		
		BukkitTask bt = new BukkitRunnable() {
			@Override
			public void run() {
				cancel();
				
				if(lasthit.containsKey(affected)) {
					lasthit.remove(affected);
				}
				
				if(lasthit_timer.containsKey(affected)) {
					lasthit_timer.remove(affected);
				}
				
			}
		}.runTaskLaterAsynchronously(Skywars.getInstance(), time * 20);
		
		lasthit_timer.put(affected, bt);
		
	}
	
	public void killPlayer(Player affected) {
		
		/**
		 * @AddTheKill
		 */
		
		Player damager = null;
		
		if(lasthit.containsKey(affected)) {
			
			damager = lasthit.get(affected);
			lasthit.remove(affected);
			
		}
		
		if(lasthit_timer.containsKey(affected)) {
			
			BukkitTask bt = lasthit_timer.get(affected);
			bt.cancel();
			
			lasthit_timer.remove(affected);
			
		}
		
		playDeath(affected, damager);
		
		/**
		 * @AddAssistences
		 */
		if(assistence.containsKey(affected)) {
			
			if(damager != null) {
				
				Map<Player, BukkitTask> players = assistence.get(affected);
				for(Map.Entry<Player, BukkitTask> k : players.entrySet()) {
					
					Player cache_p = k.getKey();
					BukkitTask cache_bt = k.getValue();
					
					if(cache_p != damager) {
						
						if(cache_bt != null) {
							cache_bt.cancel();
						}
						
						playAssistence(affected, cache_p);
						
					}
					
				}
				
			} else {
				
				Map<Player, BukkitTask> players = assistence.get(affected);
				for(Map.Entry<Player, BukkitTask> k : players.entrySet()) {
					
					Player cache_p = k.getKey();
					BukkitTask cache_bt = k.getValue();
						
					if(cache_bt != null) {
						cache_bt.cancel();
					}
					
					playAssistence(affected, cache_p);
					
				}
				
			}
			
			assistence.remove(affected);
			
		}
		
	}
	
	public void killPlayer(Player damager, Player affected) {
		
		/**
		 * @AddTheKill
		 */
		playDeath(affected, damager);
		
		/**
		 * @AddAssistences
		 */
		if(assistence.containsKey(affected)) {
			
			Map<Player, BukkitTask> players = assistence.get(affected);
			for(Map.Entry<Player, BukkitTask> k : players.entrySet()) {
				
				Player cache_p = k.getKey();
				BukkitTask cache_bt = k.getValue();
				
				if(cache_p != damager) {
					
					if(cache_bt != null) {
						cache_bt.cancel();
					}
					
					playAssistence(affected, cache_p);
					
				}
				
			}
			
			assistence.remove(affected);
			
		}
		
		
		if(lasthit.containsKey(affected)) {
			lasthit.remove(affected);
		}
		
		if(lasthit_timer.containsKey(affected)) {
			
			BukkitTask bt = lasthit_timer.get(affected);
			bt.cancel();
			
			lasthit_timer.remove(affected);
			
		}
		
	}
	
	public void playAssistence(Player death, Player helper) {
		
		/**
		 * @MakeAssistence
		 */
		if(battle_info.containsKey(helper)) {
			
			PlayerBattleInfo pbi = battle_info.get(helper);
			pbi.assistences += 1;
			
			battle_info.put(helper, pbi);
			
			InGameActionBar.ASSISTENCE.send(helper);
			helper.playSound(helper.getLocation(), Sound.ORB_PICKUP, 5, -5);
			return;
			
		} else {
			
			battle_info.put(helper, new PlayerBattleInfo(helper));
			playAssistence(death, helper);
			return;
			
		}
		
	}

	public void playDeath(Player death, Player killer) {
		
		/**
		 * @MakeKill
		 */
		if(killer != null) {
			
			if(battle_info.containsKey(killer)) {
				
				PlayerBattleInfo pbi = battle_info.get(killer);
				pbi.kills += 1;
				
				battle_info.put(killer, pbi);
				
				InGameActionBar.KILL_ENEMY.send(killer);
				killer.playSound(killer.getLocation(), Sound.LEVEL_UP, 5, -5);
				
			} else {
				
				battle_info.put(killer, new PlayerBattleInfo(killer));
				playDeath(death, killer);
				return;
				
			}
			
		}
		
		
		/**
		 * @MakeDeath
		 */
		if(battle_info.containsKey(death)) {
			
			PlayerBattleInfo pbi = battle_info.get(death);
			pbi.alive = false;
			
			battle_info.put(death, pbi);
			
			InGameActionBar.DEAD.send(death);
			death.playSound(death.getLocation(), Sound.HORSE_SKELETON_HIT, 5, -10);
			
		} else {
			
			battle_info.put(death, new PlayerBattleInfo(death));
			playDeath(death, null);
			return;
			
		}
		
		/**
		 * @RemovePlayer
		 */
		SoloPlayerManager.deathPlayer(death);
		return;
		
	}
	
	public enum InGameActionBar {
		KILL_ENEMY("&f¡Has matado un enemigo &a&l+%a&f!"),
	    ASSISTENCE("&f¡Has ganado una asistencia &a&l+%a&f!"),
	    WINNER("&f¡Has ganado la partida &a&l+%a&f!"),
		DEAD("&c¡Has muerto!"),
		;
		
		private String barmessage;
		
		InGameActionBar(String barmessage){
			
			this.setBarmessage(barmessage);
			
		}

		public String getBarmessage() {
			return barmessage;
		}

		public void setBarmessage(String barmessage) {
			this.barmessage = barmessage;
		}
		
		public void send(Player player) {
			ActionBarApi.sendActionBar(player, TextUtil.format(getBarmessage()));
		}
		
	}
	
	public enum DeathMessages {
		P2P_WEAPON("&cPLAYER_1 &7Ha muerto a manos de &aPLAYER_2!", "&cPLAYER_1 &7Ha sido asesinado brutalmente por &aPLAYER_2!"),
		P2P_ARROWS("&aPLAYER_2 &7Mató a &cPLAYER_1&7, Con su arco!", "&cPLAYER_1 &7Ha destruido a &aPLAYER_2!"),
		
		P2P_Z_JHON_CENA("&aPLAYER_2 &7Disparó a &cPLAYER_1&7, Con su puño de &2&lJHON CENAAAA!"),
		P2P_Z_THOR("&cPLAYER_1 &7Fue destrozado con el &e&lRAYO &7de &7PLAYER_1!"),
		P2P_Z_ICE_THOR("&cPLAYER_1 &7Se congeló con el &b&lRAYO CONGELADOR &7de &7PLAYER_1!"),
		P2P_Z_CLON("&aPLAYER_2 &7Mató a &cPLAYER_1 &7con su poderoso &9&lCLON!"),
		P2P_Z_TRAP_CHEST("&cPLAYER_1 &7Se encontró una sorpresita en el &6&lCOFRE TRAMPA&7 puesto por &aPLAYER_2!"),
		P2P_Z_ICE_BALL("&aPLAYER_2 &7Disparó a &cPLAYER_1&7, Con su &b&lBOLA CONGELACEREBROSSS!"),
		P2P_Z_METEOR("&7El &c&lMETEORITO&7 de &aPLAYER_2 &7aplastó a &cPLAYER_1!"),
		
		P2A_Z_CONTAMINATION("&7La contaminación a acabado con la vida de &cPLAYER_1!"),
		P2A_COMMON("&cPLAYER_1 &7Ha muerto!"),
		P2A_VOID("&cPLAYER_1 &7fue absorbido por el vacio!", "&cPLAYER_1 &7fue exterminado por el espacio-tiempo!",
				"&cPLAYER_1 &7se preguntaba que habia en el vacio!"),
		P2A_SUICIDE("&cPLAYER_1 &7se ha suicidado!"),
		P2A_WATER("&cPLAYER_1 &7se ahogó!", "&cPLAYER_1 &7no pudo salir del agua!"),
		P2A_FIRE("&cPLAYER_1 &7murió quemado!", "&cPLAYER_1 &7no pudo extinguir las llamas!"),
		P2A_LAVA("&cPLAYER_1 &7practicó natación en lava!"),
		P2A_JUMP("&cPLAYER_1 &7cayó desde muy alto!", "&7Los restos de &cPLAYER_1 &7quedaron esparcidos por el suelo."),
		
		P2P_Z_CONTAMINATION("&aPLAYER_2 &7ha empujado sin piedad a &cPLAYER_1 &7hacia la &d&lcontaminacion!"),
		P2P_Z_LASER_TURRET("&7La torreta &4&lLASER &7de &aPLAYER_2 &7destruyó a &cPLAYER_1 &7con su poderoso rayo!"),
		P2P_Z_ICE_TURRET("&cPLAYER_1 No pudo huir de la poderosa torreta &b&lCONGELADORA &7de &aPLAYER_2!"),
		P2P_Z_PORK_TURRET("&7La torreta &6&lPORCINA &7de &aPLAYER_2 &7explotó a &cPLAYER_1 &7en mil pedazos!"),
		
		P2P_COMMON("&aPLAYER_2 &7ha matado a &cPLAYER_1!"),
		P2P_VOID("&aPLAYER_2 &7empujó a &cPLAYER_1 &7hacia el vacio!"),
		P2P_SUICIDE("&aPLAYER_2 &7ha provocado que &cPLAYER_1 &7se suicide!"),
		P2P_WATER("&aPLAYER_2 &7ha ahogado despediadamente a &cPLAYER_1 &7en el agua!"),
		P2P_FIRE("&aPLAYER_2 &7empujó a &cPLAYER_1 &7hacia las llamas infernales!"),
		P2P_LAVA("&aPLAYER_2 entrenó a &cPLAYER_1 &7en su curso de natación en lava!"),
		P2P_JUMP("&cPLAYER_1 fue empujado por &aPLAYER_2 &7desde muy alto!"),
		
		P2E_LEAVE("&cPLAYER_1 &7se ha desconectado!", "&cPLAYER_1 &7ha huido del campo de batalla!"),
		P2E_EXPULSE("&cPLAYER_1 &7fue expulsado de la partida!"),
	
		;
		
		private String[] message;
		
		DeathMessages(String... message){
			this.message = message;
		}

		public void broadcastMessage(Player p1, Player p2) {
			
			String send = message[0];
			if(message.length >= 2) {
				send = message[NumberUtil.getRandomInt(0, message.length)];
			}
			
			send = send.replaceAll("PLAYER_1", p1.getName());
			send = send.replaceAll("PLAYER_2", p2.getName());
			sendMessage(send);
			
		}
		
		public void broadcastMessage(Player p1) {
			
			String send = message[0];
			if(message.length >= 2) {
				send = message[NumberUtil.getRandomInt(0, message.length)];
			}
			
			send = send.replaceAll("PLAYER_1", p1.getName());
			sendMessage(send);
			
		}
		
		public void sendMessage(String message) {
			
			String prefix = " &8&l»&r ";
			String tosend = prefix + message;
			
			for(Player p : Bukkit.getOnlinePlayers()) {
				p.sendMessage(TextUtil.format(tosend));
			}
			
		}
		
		public String[] getMessage() {
			return message;
		}

		public void setMessage(String[] message) {
			this.message = message;
		}
		
	}
	
}
