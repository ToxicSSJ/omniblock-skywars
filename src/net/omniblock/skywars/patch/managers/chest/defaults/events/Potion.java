package net.omniblock.skywars.patch.managers.chest.defaults.events;

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
import net.omniblock.skywars.SkywarsGameState;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;
import net.omniblock.skywars.games.teams.managers.TeamPlayerManager;
import net.omniblock.skywars.patch.managers.chest.defaults.events.type.ItemType;

public class Potion implements ItemType, Listener {

	@SuppressWarnings("deprecation")
	@Override
	@EventHandler
	public void HealPot(PlayerInteractEvent event) {

		if (Skywars.getGameState() != SkywarsGameState.IN_GAME) return;

		if (SoloPlayerManager.getPlayersInGameList().contains(event.getPlayer())
				|| TeamPlayerManager.getPlayersInGameList().contains(event.getPlayer())) {
			
			if(event.getPlayer().getItemInHand().getType() == Material.POTION) {
				

				if (event.getAction() == Action.LEFT_CLICK_BLOCK
						|| event.getAction() == Action.RIGHT_CLICK_BLOCK
						|| event.getAction() == Action.RIGHT_CLICK_AIR
						|| event.getAction() == Action.LEFT_CLICK_AIR) {

					if (event.getClickedBlock() != null) {
						if (event.getClickedBlock().getType() == Material.CHEST
								|| event.getClickedBlock().getType() == Material.TRAPPED_CHEST
								|| event.getClickedBlock().getType() == Material.JUKEBOX) {

							event.setCancelled(true);
							return;
							
							

						}
					}
				
					for (PotionEffect potion : event.getPlayer().getActivePotionEffects()) {
						event.getPlayer().removePotionEffect(potion.getType());
					}

					event.getPlayer().getInventory().setItemInHand(null);
					event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_GENERIC_DRINK, 3, -30);
					event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_BAT_TAKEOFF, 3, -30);
					event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_YES, 2, -30);
					event.getPlayer().setFoodLevel((int) 1);

					event.getPlayer().playEffect(EntityEffect.VILLAGER_HAPPY);
					potionEffect(event.getPlayer());
				}
			}
		}			
	}

	public void potionEffect(Player player) {

		new BukkitRunnable() {
			int TIME = 0;

			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				if (TIME != 40) {
					TIME += 20;
					player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 5, 10));
					player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 5, 10));
					player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 5, 10));
					player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5, 5));
					player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 80, 10));
					player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 5, 5);

				} else {
					player.removePotionEffect(PotionEffectType.LEVITATION);
					player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 7, 10);
					player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 160, 10));
					player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 160, 15));
					player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 160, 10));
					player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 160, 10));
					player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 160, 10));
					player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 160, 10));
					player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 160, 2));
					player.setHealth(player.getMaxHealth());
					player.setFoodLevel((int) 20);
					cancel();
				}
			}

		}.runTaskTimer(Skywars.getInstance(), 0, 20L);
	}
}
