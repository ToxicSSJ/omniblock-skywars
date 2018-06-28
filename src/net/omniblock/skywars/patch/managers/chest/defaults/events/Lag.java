package net.omniblock.skywars.patch.managers.chest.defaults.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import net.omniblock.network.library.utils.TextUtil;
import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.SkywarsGameState;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;
import net.omniblock.skywars.games.teams.managers.TeamPlayerManager;
import net.omniblock.skywars.patch.managers.SpectatorManager;
import net.omniblock.skywars.patch.managers.chest.defaults.type.LegendaryItemType;
import net.omniblock.skywars.util.ActionBarApi;
import net.omniblock.skywars.util.ItemBuilder;
import net.omniblock.skywars.util.SoundPlayer;

public class Lag implements Listener{
	
	private static ArrayList<Player> LaggedPlayers = new ArrayList<Player>();
	public static Map<Player, ArrayList<Location>> PLAYER_LOCATIONS = new HashMap<Player, ArrayList<Location>>();
	
	ItemStack reconnector = new ItemBuilder(Material.WATCH).amount(1)
			.name(TextUtil.format("&8&lRECONECTAR AHORA")).build();
	
	BukkitTask laggingtask;
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void LagPot(PlayerInteractEvent event){
		
		if (Skywars.getGameState() != SkywarsGameState.IN_GAME) return;
		
		if (!event.getPlayer().getItemInHand().hasItemMeta() 
				|| !event.getPlayer().getItemInHand().getItemMeta().hasDisplayName()) return;
		
		if (SoloPlayerManager.getPlayersInGameList().contains(event.getPlayer())
				|| TeamPlayerManager.getPlayersInGameList().contains(event.getPlayer())){
			
				
			if (event.getAction() == Action.LEFT_CLICK_BLOCK
					|| event.getAction() == Action.RIGHT_CLICK_BLOCK
					|| event.getAction() == Action.RIGHT_CLICK_AIR
					|| event.getAction() == Action.LEFT_CLICK_AIR){
				
				if (event.getClickedBlock() != null) {
					if (event.getClickedBlock().getType() == Material.CHEST
							|| event.getClickedBlock().getType() == Material.TRAPPED_CHEST
							|| event.getClickedBlock().getType() == Material.JUKEBOX) {

						return;

					}
				}
				
				if(!Skywars.ingame)
					return;
				
				
				if (event.getPlayer().getItemInHand().getItemMeta().getDisplayName()
						.equalsIgnoreCase(LegendaryItemType.POCION_DE_LAG.getItem().getItemMeta().getDisplayName())){
					
					event.setCancelled(true);
					
					if(LaggedPlayers.contains(event.getPlayer())){
						
						event.getPlayer().sendMessage(TextUtil.format("&c¡Ya estás laggeado!"));
						return;
					}
					
					LaggedPlayers.add(event.getPlayer());
					PLAYER_LOCATIONS.put(event.getPlayer(), new ArrayList<Location>());
					
					event.getPlayer().getInventory().setItemInHand(null);
					
					event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_GENERIC_DRINK, 3, -30);
					event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_BAT_TAKEOFF, 3, -30);
					event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_YES, 2, -30);
					
					
					event.getPlayer().getInventory().setItemInHand(reconnector);
					
					new BukkitRunnable(){

						int halfSeconds = 28; //Representa 0.5 segundos en el Runnable
						
						@Override
						public void run() {
							
							if(!LaggedPlayers.contains(event.getPlayer()) || !event.getPlayer().isOnline()
									|| SpectatorManager.playersSpectators.contains(event.getPlayer())){
								
								cancel();
								return;
							}
							
							if(halfSeconds<=0){
								
								cancel();
								reconnect(event.getPlayer());
								
							}else{
								
								PLAYER_LOCATIONS.get(event.getPlayer()).add(event.getPlayer().getLocation());
								
								ActionBarApi.sendActionBar(event.getPlayer(),
										TextUtil.format("&c&l¡Laggeado! &8&l: &7Reconectando en &a"
												+ ((int) halfSeconds / 4) + " &7segundos."));
							}
							
							halfSeconds--;
							return;
						}
					}.runTaskTimer(Skywars.getInstance(), 0, 5);
					
				} else if(event.getPlayer().getItemInHand().getItemMeta().getDisplayName()
						.contains(reconnector.getItemMeta().getDisplayName())){
					
					event.setCancelled(true);
					event.getPlayer().setItemInHand(null);
					
					if(LaggedPlayers.contains(event.getPlayer()))
						reconnect(event.getPlayer());
						

				}
			}
		}
	}

	protected void reconnect(Player player) {
		
		LaggedPlayers.remove(player);
		if(player.getInventory().contains(reconnector))
			player.getInventory().remove(reconnector);

		ActionBarApi.sendActionBar(player,
				TextUtil.format("&c&lReconectando...."));
		
		SoundPlayer.sendSound(player.getLocation(), "skywars.power_up", 30);
		player.setFallDistance(-1000);
		
		new BukkitRunnable(){

			int times = PLAYER_LOCATIONS.get(player).size() - 1;
			
			@Override
			public void run() {
				
				if(!player.isOnline()
						|| SpectatorManager.playersSpectators.contains(player)
						|| !PLAYER_LOCATIONS.containsKey(player)){
					

					cancel();
					PLAYER_LOCATIONS.remove(player);
					return;
				}
				
				if(times <= 0){
					
					cancel();
					PLAYER_LOCATIONS.remove(player);
					
					ActionBarApi.sendActionBar(player,
							TextUtil.format("&a&l¡Conección establecida!"));
					
					return;
				}
				
				player.teleport(PLAYER_LOCATIONS.get(player).get(times));
				
				times--;
			}
			
		}.runTaskTimer(Skywars.getInstance(), 4, 4);
	}

}
