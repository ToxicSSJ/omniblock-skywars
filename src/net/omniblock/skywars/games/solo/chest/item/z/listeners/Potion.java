package net.omniblock.skywars.games.solo.chest.item.z.listeners;

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

import net.omniblock.skywars.games.solo.chest.item.z.listeners.type.ItemType;
import net.omniblock.skywars.games.solo.chest.item.z.type.EItem;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;

public class Potion implements ItemType, Listener {

	private static SoloPlayerManager soloplayer;

	@SuppressWarnings("static-access")
	@Override
	@EventHandler
	public void HealPot(PlayerInteractEvent event) {
		
		Player player = event.getPlayer();
		
		if (soloplayer.getPlayersInGameList().contains(player) && player.getGameMode() == GameMode.SURVIVAL) {

			if (event.getPlayer().getItemInHand() == null) return;
			if (event.getPlayer().getItemInHand().getType() != Material.POTION) return;
			if (event.getPlayer().getItemInHand().getItemMeta() == null) return;
			
			if (event.getAction() == Action.LEFT_CLICK_BLOCK 
					|| event.getAction() == Action.RIGHT_CLICK_BLOCK
					|| event.getAction() == Action.RIGHT_CLICK_AIR
					|| event.getAction() == Action.LEFT_CLICK_AIR) {
				if (event.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(EItem.POCION_PURIFICADORA.getName())) {
				    
					for (PotionEffect potion : player.getActivePotionEffects()) {
						player.removePotionEffect(potion.getType());
					}
					
					player.getInventory().setItemInHand(null);
					player.playSound(player.getLocation(), Sound.DRINK, 3, -30);
					player.playSound(player.getLocation(), Sound.BAT_TAKEOFF, 3, -30);
					player.playSound(player.getLocation(), Sound.VILLAGER_YES, 2, -30);
					player.setHealth(player.getMaxHealth());
					player.setFoodLevel((int) 20);
					player.playEffect(EntityEffect.VILLAGER_HAPPY);
					player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 10, 10));
					player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 10, 10));
				    
				}
			}
		}
	}
}
