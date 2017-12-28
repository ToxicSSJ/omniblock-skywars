package net.omniblock.skywars.patch.managers.chest.defaults.events;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import net.omniblock.network.library.helpers.effectlib.effect.LineEffect;
import net.omniblock.network.library.helpers.effectlib.util.ParticleEffect;
import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.SkywarsGameState;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;
import net.omniblock.skywars.games.teams.managers.TeamPlayerManager;
import net.omniblock.skywars.patch.managers.CustomProtocolManager;
import net.omniblock.skywars.util.block.SpawnBlock;

public class IBall implements Listener {

	private List<Snowball> getSnowball = new ArrayList<Snowball>();
	

	@SuppressWarnings("deprecation")
	@EventHandler
	public void IceBall(PlayerInteractEvent event) {
		
		if (Skywars.getGameState() != SkywarsGameState.IN_GAME) return;

		if (SoloPlayerManager.getPlayersInGameList().contains(event.getPlayer())
				|| TeamPlayerManager.getPlayersInGameList().contains(event.getPlayer())) {
			
			if(event.getPlayer().getItemInHand().getType() == Material.getMaterial(2264)) {
				
				if (event.getAction() == Action.RIGHT_CLICK_AIR 
						|| event.getAction() == Action.RIGHT_CLICK_BLOCK
						|| event.getAction() == Action.LEFT_CLICK_AIR
						|| event.getAction() == Action.LEFT_CLICK_BLOCK) {

					if(!Skywars.ingame)
						return;
					
					if (event.getClickedBlock() != null) {
						if (event.getClickedBlock().getType() == Material.CHEST
								|| event.getClickedBlock().getType() == Material.TRAPPED_CHEST
								|| event.getClickedBlock().getType() == Material.JUKEBOX) {

							return;

						}
					}

							
					event.getPlayer().getInventory().setItemInHand(null);
							
					final Vector dir = event.getPlayer().getLocation().getDirection().normalize().multiply(2);
					final Snowball sb = event.getPlayer().launchProjectile(Snowball.class);		
					
					getSnowball.add(sb);
					
					sb.setVelocity(dir);
					

					new BukkitRunnable() {
								
						int LIFE_TIME = 0;

						@Override
						public void run() {
									
							if (LIFE_TIME == 70) sb.remove(); cancel();
							if (sb.isDead() != true) {

								Location block = sb.getLocation();
								block.getWorld().playEffect(block, Effect.HAPPY_VILLAGER, 4);
								block.getWorld().playSound(block, Sound.ENTITY_BLAZE_HURT, 3, 1);
								
								
								for (Entity entity : sb.getNearbyEntities(8, 8, 8)) {

									if (entity == null) continue;

									if (entity instanceof Player) {
										
										Player p = (Player) entity;
										
										if(p == event.getPlayer()) continue;
										
										snowShoot(sb, p);
										
										p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 180, 4));
										
										return;

									}
								}	
								
								LIFE_TIME++;				
							}
						}
						
					}.runTaskTimer(Skywars.getInstance(), 0L, 2L);
				}
			}
		}	
	}

	@EventHandler
	public void iceBalleffect(ProjectileHitEvent event) {

		if (event.getEntity() instanceof Snowball) {

			Snowball sb = (Snowball) event.getEntity();
			
			if(getSnowball.contains(sb)) {
				
				Location location = sb.getLocation();

				sb.remove();
				location.getWorld().playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 5, 10);
				
				List<Block> cubeAir = SpawnBlock.circle(location, 4, 5, true, true, 0);
				List<Block> circle = SpawnBlock.circle(location, 4, 1, false, true, -1);
				
				Collections.shuffle(cubeAir);
				
				for (Block b : circle) {
					
					if(CustomProtocolManager.PROTECTED_BLOCK_LIST.contains(b))
						continue;
					
					if (b.getType() == Material.AIR)
						continue;
					
					b.setType(Material.PACKED_ICE);
					
				}
				
				new BukkitRunnable() {
					
					int start = 0;
					Block bl;
					
					@Override
					public void run() {
						
						if( start >= cubeAir.size()) {
							
							cancel();				
							return;
							
						} 
						
						 	bl = cubeAir.get(start);
							bl.setType(Material.ICE);
							
						start++;
					}
					
				}.runTaskTimer(Skywars.getInstance(), 0L, (long) 0.2);
			}
		}
	}
	
	private void snowShoot(Entity entity, Entity toshoot) {

		if (toshoot.getType() == EntityType.PLAYER) {

			toshoot.getWorld().playSound(toshoot.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 10, 20);

			Player affected = (Player) toshoot;
			Snowball sb = (Snowball) entity;

			LineEffect ef = new LineEffect(Skywars.effectmanager);
			ef.particle = ParticleEffect.REDSTONE;
			ef.color = Color.BLUE;
			ef.autoOrient = true;
			ef.visibleRange = 300;
			ef.setLocation(sb.getLocation());
			ef.setTargetLocation(affected.getEyeLocation());
			ef.start();

			affected.setVelocity(entity.getLocation().getDirection().multiply(0.4));
		}
	}
}
