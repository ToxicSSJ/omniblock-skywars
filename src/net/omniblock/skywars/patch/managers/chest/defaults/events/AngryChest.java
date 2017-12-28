package net.omniblock.skywars.patch.managers.chest.defaults.events;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import net.omniblock.network.library.utils.TextUtil;
import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.SkywarsGameState;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;
import net.omniblock.skywars.games.teams.managers.TeamPlayerManager;
import net.omniblock.skywars.patch.managers.chest.defaults.events.stuff.AngryChestData;

public class AngryChest implements Listener {

	private Map<Block, AngryChestData> chestBlock = new HashMap<Block, AngryChestData>();
	
	private Block block = null;

    /**
     * Con este evento, ubicaras o registraras la posición del AngryChest, esta
     * ubicación se utilizara para determinar donde se desarrollara los efectos.
     *
     */
	
	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		
		if (Skywars.getGameState() != SkywarsGameState.IN_GAME) return;
		
		block = event.getBlockPlaced();

		if (SoloPlayerManager.getPlayersInGameList().contains(event.getPlayer())
				|| TeamPlayerManager.getPlayersInGameList().contains(event.getPlayer())) {
			
			if(!Skywars.ingame)
				return;
		
			if (block.getType() == Material.TRAPPED_CHEST) {
				
				event.getPlayer().sendMessage(TextUtil.format("&6¡Has creado un cofre trampa!"));
				event.getPlayer().getWorld().playEffect(block.getLocation(), Effect.SMOKE, 10);
				
				chestBlock.put(block.getLocation().getBlock(), new AngryChestData(block, event.getPlayer()));
			}
		}
	}

    /**
     *
     * Con este evento, Determinaras que efecto se hará cuando le hagas click a un
     * AngryChest ya registrado, una vez que el efecto se desarrolla el AngryChest
     * dejara de estar registrado.
     *
     */
	
	@EventHandler
	public void onClick(PlayerInteractEvent event) {

		if (Skywars.getGameState() != SkywarsGameState.IN_GAME) return;
		
		if (SoloPlayerManager.getPlayersInGameList().contains(event.getPlayer())
				|| TeamPlayerManager.getPlayersInGameList().contains(event.getPlayer())) {
			
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK) {
				
				if(!Skywars.ingame)
					return;
				
				if (event.getClickedBlock().getType() == Material.TRAPPED_CHEST ||
						event.getClickedBlock().getType() == Material.CHEST) {
					
					if (chestBlock.containsKey(event.getClickedBlock())) {
						
						AngryChestData data = chestBlock.get(event.getClickedBlock());
						
						if(event.getPlayer() == data.getPlayer()) {
							
							data.getAngryChestBlock();
							chestBlock.remove(event.getClickedBlock());
							return;
						}
					
						data.removeBlock();
						data.makeExplode(event.getPlayer());
						chestBlock.remove(event.getClickedBlock());
						
					}
				} 
			}
		}
	}
}
