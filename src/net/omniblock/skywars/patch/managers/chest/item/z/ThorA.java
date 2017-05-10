package net.omniblock.skywars.patch.managers.chest.item.z;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.games.solo.events.SoloPlayerBattleListener;
import net.omniblock.skywars.games.solo.events.SoloPlayerBattleListener.DamageCauseZ;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;
import net.omniblock.skywars.games.teams.events.TeamPlayerBattleListener;
import net.omniblock.skywars.games.teams.managers.TeamPlayerManager;
import net.omniblock.skywars.patch.managers.chest.item.z.type.ItemType;
import net.omniblock.skywars.patch.types.SkywarsType;
import net.omniblock.skywars.util.CameraUtil;
import net.omniblock.skywars.util.block.SpawnBlock;
import net.omniblock.skywars.util.effectlib.effect.ExplodeEffect;

public class ThorA implements ItemType, Listener {

	@EventHandler
	@Override
	public void ThorAxe(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		if(SoloPlayerManager.getPlayersInGameList().contains(player) || TeamPlayerManager.getPlayersInGameList().contains(player) && player.getGameMode() == GameMode.SURVIVAL){
			
			if(event.getPlayer().getItemInHand().hasItemMeta()){
				if(event.getPlayer().getItemInHand().getItemMeta().hasDisplayName()){
					if(event.getPlayer().getItemInHand().getType() == Material.RECORD_8){
						if(event.getAction() == Action.RIGHT_CLICK_AIR 
								|| event.getAction() == Action.RIGHT_CLICK_BLOCK
								|| event.getAction() == Action.LEFT_CLICK_AIR 
								|| event.getAction() == Action.LEFT_CLICK_BLOCK){
							
							if(event.getClickedBlock() != null) {
								if(event.getClickedBlock().getType() == Material.CHEST ||
										event.getClickedBlock().getType() == Material.TRAPPED_CHEST ||
										event.getClickedBlock().getType() == Material.JUKEBOX) {
									
									event.setCancelled(true);
									return;
									
								}
							}
							
							player.getInventory().setItemInHand(null);
							
							Block targetblock = event.getPlayer().getTargetBlock((Set<Material>) null, 200);
							Player targetplayer = null;
								
							for(Player p : player.getWorld().getEntitiesByClass(Player.class)) {
								if((SoloPlayerManager.getPlayersInGameList().contains(p) || TeamPlayerManager.getPlayersInGameList().contains(p))) {
									if(CameraUtil.getLookingAt(player, p)) {
										targetplayer = p;
									}
								}
							}
							
							Location location = targetplayer != null ? targetplayer.getLocation() : targetblock.getLocation();
							
							World world = location.getWorld();
							
							world.strikeLightning(location);
							
							ExplodeEffect ef = new ExplodeEffect(Skywars.effectmanager);
							ef.visibleRange = 300;
							ef.setLocation(location);
							ef.start();
							
							Collection<Entity> entities = location.getWorld().getNearbyEntities(location, 3, 3, 3);
							for(Entity entity : entities) {
								
								if(entity.getType() == EntityType.PLAYER) {
									Player p = (Player) entity;
									
									if((SoloPlayerManager.getPlayersInGameList().contains(player) || TeamPlayerManager.getPlayersInGameList().contains(player))) {
										
										if(   Skywars.currentMatchType == SkywarsType.SW_NORMAL_TEAMS
												   || Skywars.currentMatchType == SkywarsType.SW_INSANE_TEAMS
												   || Skywars.currentMatchType == SkywarsType.SW_Z_TEAMS){
											
											TeamPlayerBattleListener.makeZDamage(player, player, 5, net.omniblock.skywars.games.teams.events.TeamPlayerBattleListener.DamageCauseZ.THORA);
											continue;
											
										}
										
										SoloPlayerBattleListener.makeZDamage(p, player, 5, DamageCauseZ.THORA);
										continue;
										
									}
								}
								
							}
							
							List<Block> cube = SpawnBlock.circle(location, 3, 3, true, true, -2);
							
							for(Block b : cube ){
								SpawnBlock.bounceBlock(b, (float) 0.8);
							}
						}
					}
				}
			}	
		}
	}
}
