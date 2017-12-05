package net.omniblock.skywars.patch.managers.chest.defaults.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Squid;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import net.omniblock.network.library.helpers.effectlib.effect.ExplodeEffect;
import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;
import net.omniblock.skywars.games.teams.managers.TeamPlayerManager;
import net.omniblock.skywars.patch.managers.chest.defaults.events.type.ItemType;
import net.omniblock.skywars.util.block.SpawnBlock;

public class Kraken implements Listener, ItemType {

	private List<Arrow> getArrow = new ArrayList<Arrow>();
	private List<Block> coalBlock = new ArrayList<Block>();

	private Map<Arrow, Squid> getSquid = new HashMap<Arrow, Squid>();
	private Map<Player, List<Block>> squidLauncher = new HashMap<Player, List<Block>>();

	@SuppressWarnings("deprecation")
	@EventHandler
	@Override
	public void kraken(PlayerInteractEvent event) {

		Player player = event.getPlayer();

		if (SoloPlayerManager.getPlayersInGameList().contains(player)
				|| TeamPlayerManager.getPlayersInGameList().contains(player)
						&& player.getGameMode() == GameMode.SURVIVAL) {

			if (event.getPlayer().getItemInHand().hasItemMeta()) {
				if (event.getPlayer().getItemInHand().getItemMeta().hasDisplayName()) {
					if (event.getPlayer().getItemInHand().getType() == Material.RECORD_6) {
						if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK
								|| event.getAction() == Action.LEFT_CLICK_AIR
								|| event.getAction() == Action.LEFT_CLICK_BLOCK) {

							if (event.getClickedBlock() != null) {
								if (event.getClickedBlock().getType() == Material.CHEST
										|| event.getClickedBlock().getType() == Material.TRAPPED_CHEST
										|| event.getClickedBlock().getType() == Material.JUKEBOX) {

									event.setCancelled(true);
									return;

								}
							}

							event.getPlayer().getInventory().setItemInHand(null);

							Squid squid = (Squid) event.getPlayer().getWorld()
									.spawnEntity(event.getPlayer().getLocation().add(0, 2, 0), EntityType.SQUID);

							final Arrow arrow = event.getPlayer().launchProjectile(Arrow.class);
							Vector dir = event.getPlayer().getLocation().getDirection().normalize().multiply(5);

							getArrow.add(arrow);
							getSquid.put(arrow, squid);
							squidLauncher.put(event.getPlayer(), coalBlock);

							squid.setMaxHealth(1000);
							squid.setHealth(1000);
							arrow.setPassenger(squid);
							arrow.setVelocity(dir);

						}
					}
				}
			}
		}
	}

	@EventHandler
	public void arrowEffect(ProjectileHitEvent event) {

		if (event.getEntity() instanceof Arrow) {

			Arrow aw = (Arrow) event.getEntity();

			if (aw == null) {
				return;
			}

			if (getArrow.contains(aw)) {

				getArrow.remove(aw);
				Location location = aw.getLocation();
				Squid squid = getSquid.get(aw);

				playerEffect(aw);
				squid.remove();
				aw.remove();

				aw.getWorld().playSound(aw.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 2, 2);

				ExplodeEffect expef = new ExplodeEffect(Skywars.effectmanager);
				expef.visibleRange = 300;
				expef.setLocation(aw.getLocation());
				expef.start();

				location.getWorld().playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 4, 10);
				List<Block> circle = SpawnBlock.circle(location, 5, 1, false, true, -1);

				for (Block b : circle) {

					if (b.getType() == Material.AIR)
						continue;

					b.setType(Material.COAL_BLOCK);
					coalBlock.add(b);

				}

			}

		}

	}

	@EventHandler
	public void moveCoalBlock(PlayerMoveEvent event) {

		Player player = event.getPlayer();

		if (SoloPlayerManager.getPlayersInGameList().contains(player) && player.getGameMode() == GameMode.SURVIVAL) {
			if (player.getLocation().add(0, -1, 0).getBlock().getType() == Material.COAL_BLOCK) {

				Block block = event.getPlayer().getLocation().add(0, -1, 0).getBlock();
				List<Block> clayBlock = new ArrayList<Block>();

				if (coalBlock.contains(block)) {

					block.setType(Material.CLAY);
					Block clay_block = block;
					clayBlock.add(clay_block);

					block.getLocation().getWorld().playEffect(block.getLocation(), Effect.SMOKE, 20);
					event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 6, 2));

					coalBlock.remove(block);

					return;

				}

				if (clayBlock.contains(block)) {

					block.setType(Material.QUARTZ);
					event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 6, 2));
					clayBlock.remove(block);

					block.getLocation().getWorld().playEffect(block.getLocation(), Effect.SMOKE, 20);

					return;

				}

			}
		}
	}

	public void playerEffect(Entity et) {

		for (Entity entity : getSquid.get(et).getNearbyEntities(15, 15, 15)) {

			if (entity == null) {
				continue;
			}

			if (entity instanceof Player) {
				
				Player p = (Player) entity;
				p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 6, 2));

				return;

			} else {
				continue;
			}

		}

	}
}
