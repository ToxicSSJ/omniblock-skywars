/*
 *  Omniblock Developers Team - Copyright (C) 2016
 *
 *  This program is not a free software; you cannot redistribute it and/or modify it.
 *
 *  Only this enabled the editing and writing by the members of the team. 
 *  No third party is allowed to modification of the code.
 *
 */

package net.omniblock.skywars.network;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.omniblock.lobbies.api.LobbyUtility;
import net.omniblock.lobbies.api.LobbyUtility.BoosterInfo;
import net.omniblock.network.library.utils.TextUtil;

public class NetworkRunnable extends BukkitRunnable {

	BossBar bar = Bukkit.createBossBar(TextUtil.format("&6&lOmniblock Network &8« &9SkyWars &8»"), BarColor.PURPLE, BarStyle.SOLID, BarFlag.DARKEN_SKY);
	
	@Override
	public void run() {

		if(LobbyUtility.getFixedBoosterStatusBoolean("skywarsnetworkbooster")) {
			
			BoosterInfo booster = LobbyUtility.getBoosterInfo("skywarsnetworkbooster");
			
			bar.setTitle(TextUtil.format("&8&l(&d&lX2 &a⛃ OmniCoins&8&l) &8» &6&l¡" + booster.playername + " activó un booster global!"));
			bar.setColor(BarColor.BLUE);
			
			for(Player p : Bukkit.getOnlinePlayers()) {
				
			    if(!bar.getPlayers().contains(p)) {
			    	
			    	bar.addPlayer(p);
			    	
			    }
			    
			}
			
		} else {
			
			bar.setTitle(TextUtil.format("&6&lOmniblock Network &8« &9SkyWars &8»"));
			bar.setColor(BarColor.PURPLE);
			
			for(Player p : Bukkit.getOnlinePlayers()) {
			    if(!bar.getPlayers().contains(p)) {
			    	bar.addPlayer(p);
			    }
			}
			
		}

	}

}
