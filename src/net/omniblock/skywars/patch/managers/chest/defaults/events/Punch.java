package net.omniblock.skywars.patch.managers.chest.defaults.events;

import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import net.omniblock.packets.util.Lists;
import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.games.solo.events.SoloPlayerBattleListener;
import net.omniblock.skywars.games.solo.events.SoloPlayerBattleListener.DamageCauseZ;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;
import net.omniblock.skywars.games.teams.events.TeamPlayerBattleListener;
import net.omniblock.skywars.games.teams.managers.TeamPlayerManager;
import net.omniblock.skywars.patch.types.SkywarsType;
import net.omniblock.skywars.util.SoundPlayer;

public class Punch implements Listener {

	protected List<Player> punchBlacklist = Lists.newArrayList();

	@SuppressWarnings("deprecation")
	@EventHandler
	public void JhonPunch(PlayerInteractAtEntityEvent event) {
		
		if (event.getRightClicked() instanceof Player) {
			
			Player player = event.getPlayer();
			Player affected = (Player) event.getRightClicked();
			
			if(punchBlacklist.contains(player))
				return;
			
			if ((SoloPlayerManager.getPlayersInGameList().contains(player)
					|| TeamPlayerManager.getPlayersInGameList().contains(player))
					&& player.getGameMode() == GameMode.SURVIVAL) {
				
				if (player.getItemInHand().hasItemMeta()) {
					
					if (!player.getItemInHand().getItemMeta().hasDisplayName())
						return;

					if (player.getItemInHand().getType() == Material.getMaterial(2257)) {
						
						if (Skywars.currentMatchType == SkywarsType.SW_NORMAL_TEAMS
								|| Skywars.currentMatchType == SkywarsType.SW_INSANE_TEAMS
								|| Skywars.currentMatchType == SkywarsType.SW_Z_TEAMS) {

							if (TeamPlayerManager.hasTeam(player))
								if (TeamPlayerManager.getPlayerTeam(player).getName() == affected.getName())
									return;

						}
						
						if(!Skywars.ingame)
							return;
						
						player.getInventory().setItemInHand(null);
						SoundPlayer.sendSound(player.getLocation(), "skywars.jhonc", 5);
						
						new BukkitRunnable() {
							
							public int delay = 0;
							
							@Override
							public void run() {
								
								if (delay == 5)
									cancel();
								
								player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_LARGE_BLAST,
										5, 1);
								player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_LARGE_BLAST_FAR,
										5, 1);
								player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_TWINKLE, 5,
										1);
								
								delay++;
								
							}
							
						}.runTaskTimer(Skywars.getInstance(), 1L, 1L);
						
						new BukkitRunnable() {
							
							@Override
							public void run() {
								
								if(punchBlacklist.contains(player))
									punchBlacklist.remove(player);
								
							}
							
						}.runTaskLater(Skywars.getInstance(), 20L);
						
						affected.setVelocity(player.getLocation().getDirection().add(new Vector(0, 1.0, 0))
								.multiply(14.5));

						if (Skywars.currentMatchType == SkywarsType.SW_NORMAL_TEAMS
								|| Skywars.currentMatchType == SkywarsType.SW_INSANE_TEAMS
								|| Skywars.currentMatchType == SkywarsType.SW_Z_TEAMS) {
							
							TeamPlayerBattleListener.makeZDamage(affected, player, 7,
									net.omniblock.skywars.games.teams.events.TeamPlayerBattleListener.DamageCauseZ.JHON_CENA);
							return;

						}

						SoloPlayerBattleListener.makeZDamage(affected, player, 7, DamageCauseZ.JHON_CENA);
						return;

					}
				}
			}
			
		}
		
	}
	
}
