package net.omniblock.skywars.patch.managers.chest.defaults.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.entity.ExplosionPrimeEvent;

import net.omniblock.network.library.helpers.effectlib.effect.ExplodeEffect;
import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.SkywarsGameState;
import net.omniblock.skywars.games.solo.events.SoloPlayerBattleListener;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;
import net.omniblock.skywars.games.teams.events.TeamPlayerBattleListener;
import net.omniblock.skywars.games.teams.managers.TeamPlayerManager;
import net.omniblock.skywars.patch.managers.CustomProtocolManager;
import net.omniblock.skywars.patch.managers.chest.defaults.events.type.ItemType;
import net.omniblock.skywars.patch.types.SkywarsType;
import net.omniblock.skywars.util.block.SpawnBlock;

public class AutomaticTNT implements ItemType, Listener {

	protected Map<Player, List<TNTPrimed>> actived_tnts = new HashMap<Player, List<TNTPrimed>>();

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {

		if (Skywars.getGameState() != SkywarsGameState.IN_GAME) return;

		if (SoloPlayerManager.getPlayersInGameList().contains(e.getPlayer())
				|| TeamPlayerManager.getPlayersInGameList().contains(e.getPlayer())) {

			if (e.getBlockPlaced().getType() == Material.TNT) {

				if(!Skywars.ingame)
					return;
				
				e.setCancelled(true);

				ItemStack itemInHand = e.getPlayer().getItemInHand();
				if (itemInHand == null)
					return;
				if (itemInHand.getAmount() <= 1) {
					e.getPlayer().setItemInHand(null);
				} else {
					itemInHand.setAmount(itemInHand.getAmount() - 1);
				}
				
				TNTPrimed tnt = (TNTPrimed) e.getBlock().getWorld()
						.spawnEntity(e.getBlock().getLocation().clone().add(.5, 0, .5), EntityType.PRIMED_TNT);
				tnt.setFuseTicks(35);

				if(!actived_tnts.containsKey(e.getPlayer()))
					actived_tnts.put(e.getPlayer(), new ArrayList<TNTPrimed>());

				actived_tnts.get(e.getPlayer()).add(tnt);
				return;

			}

		}

	}

	@EventHandler
	public void onExplosion(ExplosionPrimeEvent e) {

		if (e.getEntity() instanceof TNTPrimed) {

			e.setCancelled(true);

			TNTPrimed tnt = (TNTPrimed) e.getEntity();

			Entry<Player, List<TNTPrimed>> got = actived_tnts.entrySet().stream()
					.filter(entry -> entry.getValue().contains(tnt))
					.filter(entry -> SoloPlayerManager.getPlayersInGameList().contains(entry.getKey())
							|| TeamPlayerManager.getPlayersInGameList().contains(entry.getKey()))
					.findAny().orElse(null);

			ExplodeEffect ef = new ExplodeEffect(Skywars.effectmanager);
			ef.visibleRange = 300;
			ef.setLocation(tnt.getLocation());
			ef.start();

			if(got != null){
				
				List<Entity> entities = tnt.getNearbyEntities(3, 3, 3);
				for (Entity entity : entities) {

					if (entity.getType() == EntityType.PLAYER) {

						Player p = (Player) entity;

						if ((SoloPlayerManager.getPlayersInGameList().contains(p)
								|| TeamPlayerManager.getPlayersInGameList().contains(p))) {

							if (Skywars.currentMatchType == SkywarsType.SW_NORMAL_TEAMS
									|| Skywars.currentMatchType == SkywarsType.SW_INSANE_TEAMS
									|| Skywars.currentMatchType == SkywarsType.SW_Z_TEAMS) {

								TeamPlayerBattleListener.callEntityDamageEvent(got.getKey(), p, DamageCause.BLOCK_EXPLOSION,
										3.5);
								continue;

							}

							SoloPlayerBattleListener.callEntityDamageEvent(got.getKey(), p, DamageCause.BLOCK_EXPLOSION,
									3.5);
							continue;

						}
					}

				}
				
			}
			
			List<Block> cube = SpawnBlock.circle(tnt.getLocation(), 5, 1, false, true, -1);

			for (Block b : cube) {
				if (b != null) {
					if (!CustomProtocolManager.PROTECTED_BLOCK_LIST.contains(b)) {
						b.setType(Material.AIR);
					}
				}
			}

		}

	}

}
