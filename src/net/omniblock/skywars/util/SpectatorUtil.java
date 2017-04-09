package net.omniblock.skywars.util;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class SpectatorUtil implements Listener {

	private final static Set<Player> hiddenPlayers = new HashSet<>();
	
	public static ItemStack leave = new ItemBuilder(Material.ACACIA_DOOR_ITEM)
												   .amount(1)
												   .name(TextUtil.format("&c&lSALIR"))
												   .lore(TextUtil.format(""))
												   .lore(TextUtil.format("&9- &7Volver al lobby."))
												   .hideAtributes()
												   .build();
	
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		for(Player p : hiddenPlayers) {
			updatePlayerVisibility(p, false);
		}
	}
	
	public static void makeSpectator(Player player) {
		
		player.setGameMode(GameMode.ADVENTURE);
		
		player.setCanPickupItems(false);
		player.getInventory().setItem(8, leave);
		
		updatePlayerVisibility(player, false);
		
	}
	
	public static void updatePlayerVisibility(Player player, boolean show) {
        for (Player target : Bukkit.getOnlinePlayers()) {
            if (!target.getUniqueId().equals(player.getUniqueId())) {
                if (show) {
                    hiddenPlayers.remove(player);
                    target.showPlayer(player);
                }
                else {
                	if(!hiddenPlayers.contains(player)) {
                		hiddenPlayers.add(player);
                	}
                	target.hidePlayer(player);
                }
            }
        }
    }

	
}
