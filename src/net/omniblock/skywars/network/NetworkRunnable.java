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

import net.omniblock.network.library.utils.TextUtil;

public class NetworkRunnable extends BukkitRunnable {

	BossBar bar = Bukkit.createBossBar(TextUtil.format("&9&lO&9mniblock&9&lN&9etwork &8&l« &fSkyWars"), BarColor.BLUE, BarStyle.SOLID, BarFlag.DARKEN_SKY);
	
	@Override
	public void run() {

		if(NetworkData.generalbooster) {
			
			bar.setTitle(TextUtil.format("&a¡Existe un &b&lNetwork Booster &aactivado en SkyWars!"));
			bar.setColor(BarColor.GREEN);
			
			for(Player p : Bukkit.getOnlinePlayers()) {
				
			    if(!bar.getPlayers().contains(p)) {
			    	
			    	bar.addPlayer(p);
			    	
			    }
			    
			}
			
		} else {
			
			bar.setTitle(TextUtil.format("&8¡No hay ningún Network Booster activado!"));
			bar.setColor(BarColor.RED);
			
			for(Player p : Bukkit.getOnlinePlayers()) {
			    if(!bar.getPlayers().contains(p)) {
			    	bar.addPlayer(p);
			    }
			}
			
		}

	}

}
