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
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.omniblock.skywars.util.BossBarAPI;
import net.omniblock.skywars.util.TextUtil;

public class NetworkRunnable extends BukkitRunnable {
	
	@Override
	public void run() {
		
		if(NetworkData.generalbooster) {
			for(Player p : Bukkit.getOnlinePlayers()) {
				BossBarAPI.setMessage(p, TextUtil.format("&d&l¡NETWORK BOOSTER&r &7Activado por Unknow!"), 1, 2);
			}
		} else {
			for(Player p : Bukkit.getOnlinePlayers()) {
				BossBarAPI.setMessage(p, TextUtil.format("&9&lOMNIBLOCK NETWORK &8&l« &fSkyWars"), 1, 2);
			}
		}
		
	}

}
