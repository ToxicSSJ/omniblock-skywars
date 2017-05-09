package net.omniblock.skywars.games.solo.events;

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
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.SkywarsGameState;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;
import net.omniblock.skywars.patch.managers.CustomProtocolManager;
import net.omniblock.skywars.patch.managers.SpectatorManager;
import net.omniblock.skywars.patch.managers.SpectatorManager.SpectatorItem;

public class SoloPlayerCustomProtocols implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onDamage(EntityDamageEvent e){
		if(e.getEntity() instanceof Player){
			if(CustomProtocolManager.PROTECTED_PLAYER_LIST.contains((Player) e.getEntity())){
				e.setCancelled(true);
				return;
			}
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onRemoveBlock(BlockBreakEvent e) {
		if(CustomProtocolManager.PROTECTED_BLOCK_LIST.contains(e.getBlock())) {
			e.setCancelled(true);
			return;
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onExplodeBlock(BlockExplodeEvent e) {
		if(CustomProtocolManager.PROTECTED_BLOCK_LIST.contains(e.getBlock())) {
			e.setCancelled(true);
			return;
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void blockChange(BlockPlaceEvent e) {
		if(CustomProtocolManager.PROTECTED_BLOCK_LIST.contains(e.getBlock())) {
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
			if(CustomProtocolManager.PROTECTED_BLOCK_LIST.contains(e.getClickedBlock())) {
				e.setCancelled(true);
				return;
			}
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void blockDrop(PlayerDropItemEvent e) {
		if(SpectatorManager.playersSpectators.contains(e.getPlayer())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void itemPickup(PlayerPickupItemEvent e) {
		if(SpectatorManager.playersSpectators.contains(e.getPlayer())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void blockDrag(InventoryDragEvent e) {
		if(SpectatorManager.playersSpectators.contains((Player) e.getWhoClicked())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void damage(EntityDamageEvent e) {
		
		if(!(e.getEntity() instanceof Player)) {
			return;
		}
		
		Player p = (Player) e.getEntity();
		
		if(SpectatorManager.playersSpectators.contains(p) || CustomProtocolManager.PROTECTED_PLAYER_LIST.contains(p)) {
			e.setCancelled(true);
		}
		
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void spectatorInteract(PlayerInteractEvent e) {
		
		if(SpectatorManager.playersSpectators.contains(e.getPlayer())) {
			
			e.setCancelled(true);
			
			if(e.getPlayer().getItemInHand() != null) {
				if(e.getPlayer().getItemInHand().hasItemMeta()) {
					
					String display_name = e.getPlayer().getItemInHand().getItemMeta().getDisplayName();
					if(display_name != null) {
						
						for(SpectatorItem si : SpectatorItem.values()) {
							if(si.getItem().getDisplayname().contains(display_name)) {
								si.getExecutor().execute(e.getPlayer());
							}
						}
						
					}
					
				}
			}
		}
		
	}
	
	@EventHandler
	public void onKick(PlayerKickEvent e) {
		if(		SoloPlayerManager.getPlayersInSpectator().contains(e.getPlayer()) ||
				SoloPlayerManager.getPlayersInLobbyList().contains(e.getPlayer()) ||
				SoloPlayerManager.getPlayersInGameList().contains(e.getPlayer())) {
			if (e.getReason().contains("Flying")) {
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
