package net.omniblock.skywars.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;

public class VanishUtil {
	
	public static List<Player> INVISIBLE_PLAYERS = new ArrayList<Player>();
	
	public static void updateInvisible() {
		
		for(Player player : Bukkit.getOnlinePlayers()) {
			
			if(INVISIBLE_PLAYERS.contains(player)) {
				
				for(Player cache : Bukkit.getOnlinePlayers()) {
					if(player != cache) {
						hidePlayer(player, cache);
					}
				}
				
			} else {
				
				for(Player cache : Bukkit.getOnlinePlayers()) {
					if(player != cache) {
						seePlayer(player, cache);
					}
				}
				
			}
			
		}
		
	}
	
	public static void makeInvisible(Player player) {
		
		INVISIBLE_PLAYERS.add(player);
		updateInvisible();
		
	}
	
	public static void appear(Player player) {
		
		if(INVISIBLE_PLAYERS.contains(player)) {
			
			INVISIBLE_PLAYERS.remove(player);
			updateInvisible();
			
		}
		
	}
	
	public static void seePlayer(Player see, Player from) {
		
	    from.showPlayer(see);
	    
	    EntityPlayer nmsFrom = ((CraftPlayer) from).getHandle();
	    EntityPlayer nmsSee = ((CraftPlayer) see).getHandle();
	    
	    nmsFrom.playerConnection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.REMOVE_PLAYER, nmsSee));
	    
	}
	
	public static void hidePlayer(Player hiding, Player from) {
		
	    from.hidePlayer(hiding);
	    
	    EntityPlayer nmsFrom = ((CraftPlayer) from).getHandle();
	    EntityPlayer nmsHiding = ((CraftPlayer) hiding).getHandle();
	    
	    nmsFrom.playerConnection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, nmsHiding));
	    
	}
	
}
