package net.omniblock.skywars.patch.managers.chest.item.z;

import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;
import net.omniblock.skywars.games.teams.managers.TeamPlayerManager;
import net.omniblock.skywars.patch.managers.chest.item.z.type.ItemType;

public class Potion implements ItemType, Listener {

	@Override
	@EventHandler
	public void HealPot(PlayerInteractEvent event) {
		
		Player player = event.getPlayer();
		
		if (SoloPlayerManager.getPlayersInGameList().contains(player) || TeamPlayerManager.getPlayersInGameList().contains(player) && player.getGameMode() == GameMode.SURVIVAL) {
			
				if(event.getPlayer().getItemInHand().hasItemMeta()){
					if(event.getPlayer().getItemInHand().getItemMeta().hasDisplayName()){
						if (event.getPlayer().getItemInHand().getType() == Material.POTION) {

							if (event.getAction() == Action.LEFT_CLICK_BLOCK 
									|| event.getAction() == Action.RIGHT_CLICK_BLOCK
									|| event.getAction() == Action.RIGHT_CLICK_AIR
									|| event.getAction() == Action.LEFT_CLICK_AIR) {
								
								if(event.getClickedBlock() != null) {
									if(event.getClickedBlock().getType() == Material.CHEST ||
											event.getClickedBlock().getType() == Material.TRAPPED_CHEST ||
											event.getClickedBlock().getType() == Material.JUKEBOX) {
										
										event.setCancelled(true);
										return;
										
									}
								}
							
								for (PotionEffect potion : player.getActivePotionEffects()) {
									player.removePotionEffect(potion.getType());
								}
								
								player.getInventory().setItemInHand(null);
								player.playSound(player.getLocation(), Sound.DRINK, 3, -30);
								player.playSound(player.getLocation(), Sound.BAT_TAKEOFF, 3, -30);
								player.playSound(player.getLocation(), Sound.VILLAGER_YES, 2, -30);
								player.setHealth(player.getMaxHealth());
								player.setHealth((int) 4);
								player.setFoodLevel((int) 5);
								
								player.playEffect(EntityEffect.VILLAGER_HAPPY);
								potionEffect(player);
							    
						}	
					}	
				}
			}	
		}
	}
	
	public void potionEffect(Player player){
		
		
		new BukkitRunnable(){
			int TIME = 0;
			@Override
			public void run() {
				if(TIME != 40){
					TIME += 20;
					player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 5, 10));
					player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 5, 10));
					player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5, 5));
					player.getWorld().playSound(player.getLocation(), Sound.EXPLODE, 5, 5);
					
				}else{
					player.getWorld().playSound(player.getLocation(), Sound.EXPLODE, 2, 2);
					player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 40, 10));
					player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 40, 10));
					player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 2));
					player.setHealth(player.getMaxHealth());
					player.setFoodLevel((int) 20);
					cancel();
				}
			}
			
		}.runTaskTimer(Skywars.getInstance(), 0, 20L);
	}
}
