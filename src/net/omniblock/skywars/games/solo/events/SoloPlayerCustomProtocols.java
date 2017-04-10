package net.omniblock.skywars.games.solo.events;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Lists;

import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.SkywarsGameState;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;

public class SoloPlayerCustomProtocols implements Listener {

	public static List<Block> PROTECTED_BLOCK_LIST = Lists.newArrayList();
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onRemoveBlock(BlockBreakEvent e) {
		if(PROTECTED_BLOCK_LIST.contains(e.getBlock())) {
			e.setCancelled(true);
			return;
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onExplodeBlock(BlockExplodeEvent e) {
		if(PROTECTED_BLOCK_LIST.contains(e.getBlock())) {
			e.setCancelled(true);
			return;
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void blockChange(BlockPlaceEvent e) {
		if(PROTECTED_BLOCK_LIST.contains(e.getBlock())) {
			e.setCancelled(true);
			return;
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
    public void blockCanBuild(BlockCanBuildEvent e){
        e.setBuildable(true);
    }
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void blockInteract(PlayerInteractEvent e) {
		if(e.getClickedBlock() != null) {
			if(PROTECTED_BLOCK_LIST.contains(e.getClickedBlock())) {
				e.setCancelled(true);
				return;
			}
		}
	}
	
	@EventHandler
	public void onKick(PlayerKickEvent e) {
		if(SoloPlayerManager.getPlayersInSpectator().contains(e.getPlayer())) {
			if (e.getReason().equalsIgnoreCase("Flying is not enabled on this server")) {
				e.setCancelled(true);
			}	
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void explode(EntityChangeBlockEvent e){
		if(e.getEntity() instanceof FallingBlock){
			
			FallingBlock fb = (FallingBlock) e.getEntity();
			
			if(fb.hasMetadata("REMOVE")){
				
				e.setCancelled(true);
				e.getBlock().breakNaturally(new ItemStack(e.getTo(), 1, e.getData()));
				
				fb.remove();
				
			}
			
		}
	}
	
	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent e) {
		SkywarsGameState currentState = Skywars.getGameState();
		
		if(e.getEntity() instanceof Player) {
			
			Player p = (Player) e.getEntity();
			
			if(SoloPlayerManager.getPlayersInGameList().contains(p)) {
				if(currentState == SkywarsGameState.IN_LOBBY) {
					e.setCancelled(true);
					e.setFoodLevel(25);
				}
				
				return;
			} else {
				e.setCancelled(true);
				e.setFoodLevel(25);
				
				return;
			}
		}
		
	}
	
}
