package net.omniblock.skywars.patch.managers.chest.defaults.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.GameMode;
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

/**
*
* Esta clase contiene todo
* el sistema del item Déjà Vu
* para el modo Z.
*
* @author SoZyk
*/
public class DejaVu implements Listener{
	
	/**
	 * 
	 * Lista estatica que contiene a todos los jugadores
	 * que están experimentando un Déjà Vu.
	 * 
	 */
	private static Set<Player> DejaVuPlayers = new HashSet<Player>();
	
	/**
	 * 
	 * HashMap estatico que contiene los lugares
	 * por los que ha pasado un jugador en el Déjà Vu.
	 * 
	 */
	public static Map<Player, ArrayList<Location>> PLAYER_LOCATIONS = new HashMap<Player, ArrayList<Location>>();
	
	/**
	 * 
	 * Item con el que el jugador
	 * podrá finalizar el efecto Déjà Vu.
	 * 
	 */
	ItemStack reconnector = new ItemBuilder(Material.WATCH).amount(1)
			.name(TextUtil.format("&8&lVOLVER AHORA")).build();
	
	/**
	 * 
	 * Proceso que se encarga del sistema del Déjà Vu.
	 * 
	 * @param event
	 */
	@SuppressWarnings("deprecation")
	@EventHandler
	public void DejaVuPot(PlayerInteractEvent event){
		
		Player player = event.getPlayer();
		
		if (Skywars.getGameState() != SkywarsGameState.IN_GAME) return;
		
		if (!player.getItemInHand().hasItemMeta() 
				|| !player.getItemInHand().getItemMeta().hasDisplayName()) return;
		
		if (SoloPlayerManager.getPlayersInGameList().contains(player)
				|| TeamPlayerManager.getPlayersInGameList().contains(player)){
			
				
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
				
				
				if (player.getItemInHand().getItemMeta().getDisplayName()
						.equalsIgnoreCase(LegendaryItemType.DEJA_VU.getItem().getItemMeta().getDisplayName())){
					
					event.setCancelled(true);
					
					if(PLAYER_LOCATIONS.containsKey(player)){
						
						player.sendMessage(TextUtil.format("&c¡Ya estás en un Déjà Vu!"));
						return;
					}
					
					DejaVuPlayers.add(player);
					PLAYER_LOCATIONS.put(player, new ArrayList<Location>());
					player.setGameMode(GameMode.ADVENTURE);
					
					player.getInventory().setItemInHand(null);
					
					player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_DRINK, 3, -30);
					player.playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 3, -30);
					player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 2, -30);
					
					player.getInventory().setItemInHand(reconnector);
					
					new BukkitRunnable(){

						//Un qSecond representa 0.25 segundos
						int qSeconds = 28;
						
						@Override
						public void run() {
							
							if(!DejaVuPlayers.contains(player) || !player.isOnline()
									|| SpectatorManager.playersSpectators.contains(player)){
								
								cancel();
								return;
							}
							
							if(qSeconds<=0){
								
								cancel();
								reconnect(player);
								
							}else{
								
								PLAYER_LOCATIONS.get(player).add(player.getLocation());
								
								ActionBarApi.sendActionBar(player,
										TextUtil.format("&c&l¡Déjà Vu! &8&l: &7Regresando a la normalidad en &a"
												+ ((int) qSeconds / 4) + " &7segundos."));
							}
							
							qSeconds--;
							return;
						}
					}.runTaskTimer(Skywars.getInstance(), 0, 5);
					
				} else if(player.getItemInHand().getItemMeta().getDisplayName()
						.contains(reconnector.getItemMeta().getDisplayName())){
					
					event.setCancelled(true);
					player.setItemInHand(null);
					
					if(DejaVuPlayers.contains(player))
						reconnect(player);
						

				}
			}
		}
	}

	/**
	 * 
	 * Proceso que "reconecta" al jugador a la normalidad
	 * tras haber pasado por el efecto del Déjà Vu.
	 * 
	 * @param player
	 */
	protected void reconnect(Player player) {
		
		DejaVuPlayers.remove(player);
		
		if(player.getInventory().contains(reconnector))
			player.getInventory().remove(reconnector);

		ActionBarApi.sendActionBar(player,
				TextUtil.format("&c&lSiento que ya pase por aquí... ¿¡Qué pasa!?"));
		
		SoundPlayer.sendSound(player.getLocation(), "skywars.power_up", 30);
		player.setFallDistance(-1000);
		
		new BukkitRunnable(){

			//Veces que se repetirá el Runnable a base
			//de las locaciones guardadas en el Déjà Vu.
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
					player.setGameMode(GameMode.SURVIVAL);
					
					ActionBarApi.sendActionBar(player,
							TextUtil.format("&a&l¡Déjà Vu!"));
					
					return;
				}
				
				//try para evitar un error en el HashMap de locaciones.
				try{
					
					//Evitar teletransportaciones si se encuentra en el mismo
					//lugar (según X y Z) en la siguiente locación.
					while(PLAYER_LOCATIONS.get(player).get(times).getX() == PLAYER_LOCATIONS.get(player).get(times-1).getX()
							&& PLAYER_LOCATIONS.get(player).get(times).getZ() == PLAYER_LOCATIONS.get(player).get(times-1).getZ())
						times--;
					
				}catch(Exception e){ }
				
				player.teleport(PLAYER_LOCATIONS.get(player).get(times));
				
				times--;
			}
			
		}.runTaskTimer(Skywars.getInstance(), 4, 4);
	}
}
