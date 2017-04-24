package net.omniblock.skywars.games.teams.managers;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.google.common.collect.Lists;

import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.SkywarsGameState;
import net.omniblock.skywars.util.MultiLineAPI;
import net.omniblock.skywars.util.MultiLineAPI.TagController;
import net.omniblock.skywars.util.MultiLineAPI.TagLine;

public class TeamPlayerLineManager implements TagController {

	public static BukkitTask sbrunnable;
	
	public static void initialize(){
		
		TeamPlayerLineManager spl = new TeamPlayerLineManager();
		MultiLineAPI.register(spl);
		
		sbrunnable = new BukkitRunnable() {
			
			List<Player> tags_players = Lists.newArrayList();
			
			@Override
			public void run() {
				
				if(Skywars.getGameState() == SkywarsGameState.IN_GAME || Skywars.getGameState() == SkywarsGameState.IN_PRE_GAME) {
					
					tags_players.clear();
					
					for(Player p : TeamPlayerManager.getPlayersInGameList()) {
						tags_players.add(p);
					}
					
					for(Player p : Bukkit.getOnlinePlayers()) {
						
						if(!tags_players.contains(p)) {
							
							if(MultiLineAPI.hasLines(p)) {
								
								if(p.hasMetadata("PLAYER_HEALTH")) {
							    	
							        int i = (int) p.getMetadata("PLAYER_HEALTH").get(0).value();
							        p.removeMetadata("PLAYER_HEALTH", Skywars.getInstance());
							        Bukkit.getScheduler().cancelTask(i);
							        
							    }
								
							}
							
						} else {
							
							if (MultiLineAPI.getLineCount(spl, p) < 1) {
					            MultiLineAPI.addLine(spl, p);
					        }
							
							TagLine line = MultiLineAPI.getLine(spl, p, 0);
					        line.setText(ChatColor.translateAlternateColorCodes('&', getPlayerHealthHud(p)));
					        
					        if (p.hasMetadata("PLAYER_HEALTH")) {
					            int i = (int) p.getMetadata("PLAYER_HEALTH").get(0).value();
					            p.removeMetadata("PLAYER_HEALTH", Skywars.getInstance());
					            Bukkit.getScheduler().cancelTask(i);
					        }
					        
					        p.setMetadata("PLAYER_HEALTH", new FixedMetadataValue(Skywars.getInstance(), 
					        		
					        new BukkitRunnable() {
					        	@Override
					        	public void run() {
					        		
					        		line.setText(null);
					        		MultiLineAPI.refresh(p);
					        		p.removeMetadata("PLAYER_HEALTH", Skywars.getInstance());
					        		
					        	}
					        }.runTaskLater(Skywars.getInstance(), 5l).getTaskId()));

					        MultiLineAPI.refresh(p);
							
						}
						
					}
					
				} else {
					
					for(Player p : Bukkit.getOnlinePlayers()) {
						
						if(MultiLineAPI.hasLines(p)) {
						        
						    if (p.hasMetadata("PLAYER_HEALTH")) {
						    	
						        int i = (int) p.getMetadata("PLAYER_HEALTH").get(0).value();
						        p.removeMetadata("PLAYER_HEALTH", Skywars.getInstance());
						        Bukkit.getScheduler().cancelTask(i);
						        
						    }
						    
						}
					}
					
					return;
					
				}
				
			}
		}.runTaskTimer(Skywars.getInstance(), 2L, 2L);
		
	}

	public static String getPlayerHealthHud(Player p) {
		
		double health = p.getHealth();
		return "&f" + health + "&c❤";
		
		
	}
	
	@Override
	public int getPriority() {
		return 0;
	}
	
}
