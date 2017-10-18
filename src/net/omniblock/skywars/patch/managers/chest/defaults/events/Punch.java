package net.omniblock.skywars.patch.managers.chest.defaults.events;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.games.solo.events.SoloPlayerBattleListener;
import net.omniblock.skywars.games.solo.events.SoloPlayerBattleListener.DamageCauseZ;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;
import net.omniblock.skywars.games.teams.events.TeamPlayerBattleListener;
import net.omniblock.skywars.games.teams.managers.TeamPlayerManager;
import net.omniblock.skywars.patch.managers.chest.defaults.events.type.ItemType;
import net.omniblock.skywars.patch.types.SkywarsType;
import net.omniblock.skywars.util.SoundPlayer;

public class Punch implements ItemType, Listener {

	private int DELAY = 0; 
	
	@SuppressWarnings("deprecation")
	@Override
	@EventHandler
	public void JhonPunch(EntityDamageByEntityEvent event) {

		if (event.getEntity() instanceof Player) {
			
			Player player = (Player) event.getEntity();
			
			if (event.getDamager() instanceof Player) {
				Player playerdamage = (Player) event.getDamager();
				
				if ((SoloPlayerManager.getPlayersInGameList().contains(player) || TeamPlayerManager.getPlayersInGameList().contains(player)) && player.getGameMode() == GameMode.SURVIVAL) {
					
					if (playerdamage.getItemInHand().hasItemMeta()) {
						
						if(!playerdamage.getItemInHand().getItemMeta().hasDisplayName()) {
							return;
						}
						
						if (playerdamage.getItemInHand().getType() == Material.getMaterial(2257)) {
							
							if(   Skywars.currentMatchType == SkywarsType.SW_NORMAL_TEAMS
									   || Skywars.currentMatchType == SkywarsType.SW_INSANE_TEAMS
									   || Skywars.currentMatchType == SkywarsType.SW_Z_TEAMS){
								
								if(TeamPlayerManager.hasTeam(playerdamage)) {
									if(TeamPlayerManager.getPlayerTeam(playerdamage).getName() == player.getName()) {
										return;
									}
								}
								
							}
							
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

							player.setVelocity(playerdamage.getLocation().getDirection().add(new Vector(0, 1.2, 0)).multiply(12.0));
							
							if(   Skywars.currentMatchType == SkywarsType.SW_NORMAL_TEAMS
									   || Skywars.currentMatchType == SkywarsType.SW_INSANE_TEAMS
									   || Skywars.currentMatchType == SkywarsType.SW_Z_TEAMS){
								
								TeamPlayerBattleListener.makeZDamage(player, playerdamage, 7, net.omniblock.skywars.games.teams.events.TeamPlayerBattleListener.DamageCauseZ.JHON_CENA);
								return;
								
							}
							
							SoloPlayerBattleListener.makeZDamage(player, playerdamage, 7, DamageCauseZ.JHON_CENA);
							return;
							
						}
					}
				}
			}
		}
	}
}
