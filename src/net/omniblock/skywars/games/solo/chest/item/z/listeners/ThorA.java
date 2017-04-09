package net.omniblock.skywars.games.solo.chest.item.z.listeners;

import java.util.List;
import java.util.Set;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import net.omniblock.skywars.games.solo.chest.item.z.listeners.type.ItemType;
import net.omniblock.skywars.games.solo.chest.item.z.type.EItem;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;
import net.omniblock.skywars.util.block.SpawnBlock;

public class ThorA implements ItemType, Listener {

	private static SoloPlayerManager soloplayer;

	@SuppressWarnings("static-access")
	@EventHandler
	@Override
	public void ThorAxe(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		
		if(soloplayer.getPlayersInGameList().contains(player) && player.getGameMode() == GameMode.SURVIVAL){
			
			if(event.getPlayer().getItemInHand().hasItemMeta()){
				if(event.getPlayer().getItemInHand().getItemMeta().hasDisplayName()){
					if(event.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(EItem.RAYO.getName())){
						if(event.getAction() == Action.RIGHT_CLICK_AIR 
								|| event.getAction() == Action.RIGHT_CLICK_BLOCK
								|| event.getAction() == Action.LEFT_CLICK_AIR 
								|| event.getAction() == Action.LEFT_CLICK_BLOCK){
							
							player.getInventory().setItemInHand(null);
							
							Block targetblock = event.getPlayer().getTargetBlock((Set<Material>) null, 200);
							Location location = targetblock.getLocation();
							World world = location.getWorld();
							
							world.strikeLightning(location);
							world.createExplosion(location, 2.0F);
							List<Block> cube = SpawnBlock.circle(location,3,3,true,true,-2);
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