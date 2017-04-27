package net.omniblock.skywars.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.google.common.collect.Lists;

import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.SkywarsGameState;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;
import net.omniblock.skywars.patch.managers.MapManager;
import net.omniblock.skywars.patch.managers.chest.ChestManager;
import net.omniblock.skywars.patch.managers.chest.item.z.Bombardier;
import net.omniblock.skywars.patch.managers.chest.item.z.Meteoro;
import net.omniblock.skywars.patch.types.SkywarsType;
import net.omniblock.skywars.util.block.SpawnBlock;

public class ApocalipsisUtil {

	public static class Apocalipsis extends BukkitRunnable {

		public int rusher_cooldown = 15;
		
		public int lighting_cooldown = 7;
		public int meteoro_cooldown = 9;
		public int bombardier_cooldown = 12;
		
		public int round = 0;
		
		@SuppressWarnings("serial")
		@Override
		public void run() {
			
			if(round == 20) {
				
				round = 0;
				
				rusher_cooldown--;
				meteoro_cooldown--;
				lighting_cooldown--;
				bombardier_cooldown--;
				
			} else {
				
				round++;
				
			}
			
			if(Skywars.getGameState() == SkywarsGameState.IN_GAME) {
				
				if(rusher_cooldown == 0) {
					
					List<Player> players = getInGamePlayers();
					
					List<Location> random_locs = new ArrayList<Location>() {{
							
							for(Player p : players) {
								add(p.getLocation());
							}
							
					}};
					
					Collections.shuffle(random_locs);
					Location loc = random_locs.get(0);
					
					rusher_cooldown = NumberUtil.getRandomInt(15, 30);
					
					Location based_loc = MapManager.lobbyschematic.getLocation();
					
					new BukkitRunnable() {
						
						int round = 0;
						
						@Override
						public void run() {
							
							loc.setY(based_loc.getY());
							
							loc.getWorld().playSound(loc, Sound.BAT_LOOP, 20, -15);
							loc.getWorld().playSound(loc, Sound.WOLF_GROWL, 20, -20);
								
							Zombie z = loc.getWorld().spawn(loc, Zombie.class);
							
							z.setCanPickupItems(false);
							
							z.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 200, 2, true), true);
							z.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20 * 8, 2, true), true);
							
							z.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20 * 200, 2, true), true);
							z.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 200, 1, true), true);
							
							z.getEquipment().setHelmet(new ItemBuilder(Material.LEATHER_HELMET).enchant(Enchantment.FIRE_ASPECT, 1).build());
							z.getEquipment().setHelmet(new ItemBuilder(Material.LEATHER_BOOTS).enchant(Enchantment.PROTECTION_FALL, 1000).build());
							
							z.getEquipment().setItemInHand(new ItemBuilder(Material.BONE).enchant(Enchantment.FIRE_ASPECT, 1).build());
							
							z.setTicksLived(20 * 8);
							z.setMetadata("RUSHER", new FixedMetadataValue(Skywars.getInstance(), "dummy"));
							z.setVelocity(new Vector(0, -2, 0).multiply(0.6));
							
							round++;
							
							if(round >= 5) {
								cancel();
								return;
							}
							
						}
					}.runTaskTimer(Skywars.getInstance(), 1L, 1L);
					
				}
				
				if(bombardier_cooldown == 0) {
					
					List<Player> players = getInGamePlayers();
					
					List<Location> random_locs = new ArrayList<Location>() {{
							
							for(Player p : players) {
								add(p.getLocation());
							}
							
					}};
					
					Collections.shuffle(random_locs);
					Location loc_r = random_locs.get(0);
					
					bombardier_cooldown = NumberUtil.getRandomInt(6, 12);
					
					if(NumberUtil.getRandomInt(1, 6) == 3) {
						Bombardier.launchNaturallyBomb(loc_r);
					} else {
						Bombardier.launchNoPlaneBomb(loc_r);
					}
					
				}
				
				if(lighting_cooldown == 0) {
					
					List<Player> players = getInGamePlayers();
					
					List<Location> random_locs = new ArrayList<Location>() {{
							
							for(Player p : players) {
								add(p.getLocation());
							}
							
							List<Location> chestLocations = Lists.newArrayList();
							chestLocations.addAll(ChestManager.trappedchest);
							
							addAll(chestLocations);
							
					}};
					
					Collections.shuffle(random_locs);
					
					Location loc_w = getLobbySchematicLoc();
					Location loc_r = random_locs.get(0);
					
					lighting_cooldown = NumberUtil.getRandomInt(5, 10);
					
					loc_w.getWorld().strikeLightning(loc_r);
					loc_w.getWorld().createExplosion(loc_r, 2.0F);
					List<Block> cube = SpawnBlock.circle(loc_r, 3, 3, true, true, -2);
					
					for(Block b : cube ){
						SpawnBlock.bounceBlock(b, (float) 0.8);
					}
					
				}
				
				if(meteoro_cooldown == 0) {
					
					List<Player> players = getInGamePlayers();
					
					List<Location> random_locs = new ArrayList<Location>() {{
							
							for(Player p : players) {
								add(p.getLocation());
							}
							
							List<Location> chestLocations = Lists.newArrayList();
							chestLocations.addAll(ChestManager.trappedchest);
							
							addAll(chestLocations);
							
					}};
					
					Collections.shuffle(random_locs);
					
					meteoro_cooldown = NumberUtil.getRandomInt(8, 16);
					
					Location loc = random_locs.get(0);
					Meteoro.genNaturallyMeteoro(loc);
					
				}
				
			} else {
				
				cancel();
				return;
				
			}
			
		}
		
		public Location getLobbySchematicLoc() {
			
			if(Skywars.currentMatchType == SkywarsType.SW_INSANE_SOLO 
					|| Skywars.currentMatchType == SkywarsType.SW_NORMAL_SOLO
					|| Skywars.currentMatchType == SkywarsType.SW_Z_SOLO){
				
				return MapManager.lobbyschematic.getLocation();
				
			} else {
				
				// TODO
				return null;
				
			}
			
		}
		
		public List<Player> getInGamePlayers(){
			
			if(Skywars.currentMatchType == SkywarsType.SW_INSANE_SOLO 
					|| Skywars.currentMatchType == SkywarsType.SW_NORMAL_SOLO
					|| Skywars.currentMatchType == SkywarsType.SW_Z_SOLO){
				
				return SoloPlayerManager.getPlayersInGameListAsCopy();
				
			} else {
				
				// TODO
				return new ArrayList<Player>();
				
			}
			
		}

		public void startTroop() {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
