/*
 *  Omniblock Developers Team - Copyright (C) 2016
 *
 *  This program is not a free software; you cannot redistribute it and/or modify it.
 *
 *  Only this enabled the editing and writing by the members of the team. 
 *  No third party is allowed to modification of the code.
 *
 */

package net.omniblock.skywars.patch;

import java.util.Calendar;

import org.bukkit.Bukkit;

import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.network.NetworkData;
import net.omniblock.skywars.network.NetworkRunnable;
import net.omniblock.skywars.patch.internal.Patcher;
import net.omniblock.skywars.patch.types.SkywarsType;
import net.omniblock.skywars.util.ArrayUtils;
import omniblock.on.OmniNetwork;
import omniblock.on.addons.games.NetworkBroadcaster;
import omniblock.on.network.NetworkManager;

public class NetworkPatcher implements Patcher {

	public static NetworkRunnable networkrunnable;
	
	public void initialize(){
		
		networkrunnable = new NetworkRunnable();
		networkrunnable.runTaskTimer(Skywars.getInstance(), 5L, 40L);
		
		NetworkData.date = Calendar.getInstance().getTime();
		
		NetworkData.broadcaster = new NetworkBroadcaster(){

			@Override
			public void execute(String data) {
				
				if(data.contains("#")){
					
					SkywarsType swtype = SkywarsType.NONE;
					
					String[] matchanddata = data.split("#");
					
					swtype = getTypeByBestResolver(new String[] { matchanddata[0],
															      matchanddata[1] });
					
					if(swtype != SkywarsType.NONE){
						
						swtype.makeMatch();
						
					}
					
				} else {
					OmniNetwork.sendError("SKYWARS: No puedes ejecutar la partida sin alternación de cáracteres en "
										+ "la data. [ERR-NetworkPatcher:35]");
					return;
				}
				
			}

			@Override
			public void read(String data) {
				
				if(data.contains("$ LOCK")) {
					
					String serial = NetworkManager.serial;
					String servername = NetworkManager.servername;
					
					String channel1;
					String opendata1;
					
					channel1 = "lockservername";
					opendata1 = serial + "#" + servername + "#" + Bukkit.getOnlinePlayers().size() + "#" + Bukkit.getServer().getMaxPlayers();
					
					NetworkManager.getChannelWrapper().sendToChannel(channel1, opendata1);
					
					return;
					
				} else if(data.contains("$ UNLOCK")) {
					
					String serial = NetworkManager.serial;
					String servername = NetworkManager.servername;
					
					String channel1;
					String opendata1;
					
					channel1 = "unlockservername";
					opendata1 = serial + "#" + servername + "#" + Bukkit.getOnlinePlayers().size() + "#" + Bukkit.getServer().getMaxPlayers();
					
					NetworkManager.getChannelWrapper().sendToChannel(channel1, opendata1);
					
					return;
					
				} else if(data.contains("CANCEL")){
					
					return;
				} else if(data.contains("STOP")){
					
					return;
				} else if(data.contains("RESTART")){
					
					return;
				}
				
			}
			
		};
		
	}
	
	
	
	public String[][] getResolversArgs(){
		
		int index = 0;
		String[][] cacheresolver = new String[10][10];
		
		for(SkywarsType k : SkywarsType.values()){
			
			if(k != SkywarsType.NONE){
				
				String[] cacheargs = k.getResolver().getArgs();
				cacheresolver[index] = cacheargs;
				
				index++;
				
			}
			
		}
		
		return cacheresolver;
	}

	public SkywarsType getTypeByBestResolver(String[] args){
		
		String[][] cacheresolver = getResolversArgs();
		String[] exact = ArrayUtils.getBestAssert(args, cacheresolver);
		
		for(SkywarsType k : SkywarsType.values()){
			
			if(k != SkywarsType.NONE){
				
				if(exact.equals(k.getResolver().getArgs())){
					
					return k;
					
				}
				
			}
			
		}
		
		return SkywarsType.NONE;
	}
	
	public void read(String data){
		
		
		
	}
	
	@Override
	public String[] resume() {
		return new String[] { NetworkData.serial };
	}
	
	

}
