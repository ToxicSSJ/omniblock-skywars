package net.omniblock.skywars.games.solo.chest.item.z.listeners;

import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.games.solo.chest.item.z.listeners.type.ItemType;
import net.omniblock.skywars.games.solo.chest.item.z.type.EItem;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;
import net.omniblock.skywars.util.SoundPlayer;

public class Punch implements ItemType, Listener {

	private static SoloPlayerManager soloplayer;
	private int DELAY = 0;

	@SuppressWarnings("static-access")
	@Override
	@EventHandler
	public void JhonPunch(EntityDamageByEntityEvent event) {

		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (event.getDamager() instanceof Player) {
				Player playerdamage = (Player) event.getDamager();
				if (soloplayer.getPlayersInGameList().contains(player) && player.getGameMode() == GameMode.SURVIVAL) {
					if (playerdamage.getItemInHand().getItemMeta().hasDisplayName()) {
						if (playerdamage.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(EItem.PUÑO_DE_JHONCENA.getName())) {
							
							playerdamage.getInventory().setItemInHand(null);
							SoundPlayer.sendSound(playerdamage.getLocation(), "skywars.jhonc", 30); 
			
							new BukkitRunnable() {
								@Override
								public void run() {
									if (DELAY == 5) {
										cancel();
									} else {
										playerdamage.playSound(playerdamage.getLocation(), Sound.FIREWORK_LARGE_BLAST, 5, 1);
										playerdamage.playSound(playerdamage.getLocation(), Sound.FIREWORK_LARGE_BLAST2,	5, 1);
										playerdamage.playSound(playerdamage.getLocation(), Sound.FIREWORK_TWINKLE2, 5, 1);
										DELAY++;
									}
								}
							}.runTaskTimer(Skywars.getInstance(), 1L, 1L);

							player.damage(7.3, playerdamage);
							player.setVelocity(playerdamage.getLocation().getDirection().add(new Vector(0, 2, 0)).multiply(12.0));
						}
					}
				}
			}
		}
	}
}
