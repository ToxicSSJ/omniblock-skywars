package net.omniblock.skywars.patch.managers.chest.defaults.events;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
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
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import net.omniblock.network.library.helpers.effectlib.effect.ExplodeEffect;
import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.SkywarsGameState;
import net.omniblock.skywars.games.solo.events.SoloPlayerBattleListener;
import net.omniblock.skywars.games.solo.events.SoloPlayerBattleListener.DamageCauseZ;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;
import net.omniblock.skywars.games.teams.events.TeamPlayerBattleListener;
import net.omniblock.skywars.games.teams.managers.TeamPlayerManager;
import net.omniblock.skywars.patch.managers.CustomProtocolManager;
import net.omniblock.skywars.patch.types.SkywarsType;
import net.omniblock.skywars.util.block.SpawnBlock;

public class Kraken implements Listener {

	private List<Fireball> getFireball = new ArrayList<Fireball>();
	private List<Block> coalBlock = new ArrayList<Block>();
	private List<Block> clayBlock = new ArrayList<Block>();

	private Map<Fireball, Squid> getSquid = new HashMap<Fireball, Squid>();
	private Map<Fireball, Player> getPlayer = new HashMap<Fireball, Player>();

	@SuppressWarnings("deprecation")
	@EventHandler
	public void kraken(PlayerInteractEvent event) {

		if (Skywars.getGameState() != SkywarsGameState.IN_GAME)
			return;

		if (SoloPlayerManager.getPlayersInGameList().contains(event.getPlayer())
				|| TeamPlayerManager.getPlayersInGameList().contains(event.getPlayer())) {

			if (event.getPlayer().getItemInHand().getType() == Material.RECORD_6) {

				if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK
						|| event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {

					if (!Skywars.ingame)
						return;

					if (event.getClickedBlock() != null) {
						if (event.getClickedBlock().getType() == Material.CHEST
								|| event.getClickedBlock().getType() == Material.TRAPPED_CHEST
								|| event.getClickedBlock().getType() == Material.JUKEBOX) {

							return;

						}
					}

					event.getPlayer().getInventory().setItemInHand(null);

					final Fireball fireball = event.getPlayer().launchProjectile(Fireball.class);
					Vector dir = event.getPlayer().getLocation().getDirection().normalize().multiply(5);

					Squid squid = (Squid) event.getPlayer().getWorld().spawnEntity(event.getPlayer().getLocation(),
							EntityType.SQUID);

					getFireball.add(fireball);
					getSquid.put(fireball, squid);
					getPlayer.put(fireball, event.getPlayer());

					squid.isInvulnerable();
					squid.setMaxHealth(1000);
					squid.setHealth(1000);
					squid.setGravity(false);

					fireball.setGravity(false);
					;
					fireball.setIsIncendiary(false);
					fireball.setPassenger(squid);
					fireball.setVelocity(dir);

				}
			}
		}
	}

	@EventHandler
	public void hitEvent(ProjectileHitEvent event) {

		if (event.getEntity() instanceof Fireball) {

			Fireball fb = (Fireball) event.getEntity();

			if (getFireball.contains(fb)) {

				if (fb == event.getHitEntity())
					return;
				if (fb == null)
					return;

				getFireball.remove(fb);
				Location location = fb.getLocation();
				Squid squid = getSquid.get(fb);

				playerEffect(fb);
				squid.remove();

				fb.getWorld().playSound(fb.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 2, 2);

				ExplodeEffect expef = new ExplodeEffect(Skywars.effectmanager);
				expef.visibleRange = 300;
				expef.setLocation(fb.getLocation());
				expef.start();

				location.getWorld().playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 4, 10);
				List<Block> circle = SpawnBlock.circle(location, 5, 1, false, true, -1);

				for (Block b : circle) {

					if (CustomProtocolManager.PROTECTED_BLOCK_LIST.contains(b))
						continue;

					if (b.getType() == Material.AIR)
						continue;

					b.setType(Material.COAL_BLOCK);
					coalBlock.add(b);
					clayBlock.add(b);

				}

				Collection<Entity> entities = location.getWorld().getNearbyEntities(location, 3, 3, 3);
				for (Entity entity : entities) {

					if (entity.getType() == EntityType.PLAYER) {
						Player p = (Player) entity;

						if ((SoloPlayerManager.getPlayersInGameList().contains(getPlayer.get(fb))
								|| TeamPlayerManager.getPlayersInGameList().contains(getPlayer.get(fb)))) {

							if (Skywars.currentMatchType == SkywarsType.SW_NORMAL_TEAMS
									|| Skywars.currentMatchType == SkywarsType.SW_INSANE_TEAMS
									|| Skywars.currentMatchType == SkywarsType.SW_Z_TEAMS) {

								TeamPlayerBattleListener.makeZDamage(getPlayer.get(fb), getPlayer.get(fb), 5,
										net.omniblock.skywars.games.teams.events.TeamPlayerBattleListener.DamageCauseZ.KRAKEN);
								continue;

							}

							SoloPlayerBattleListener.makeZDamage(p, getPlayer.get(fb), 5, DamageCauseZ.KRAKEN);

							continue;

						}
					}
				}
			}
		}
	}

	@EventHandler
	public void moveCoalBlock(PlayerMoveEvent event) {

		Player player = event.getPlayer();

		if (SoloPlayerManager.getPlayersInGameList().contains(player) && player.getGameMode() == GameMode.SURVIVAL) {
			if (player.getLocation().add(0, -1, 0).getBlock().getType() == Material.COAL_BLOCK
					|| player.getLocation().add(0, -1, 0).getBlock().getType() == Material.CLAY) {

				Block block = event.getPlayer().getLocation().add(0, -1, 0).getBlock();

				if (coalBlock.contains(block)) {

					block.setType(Material.CLAY);

					event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 6, 2));

					coalBlock.remove(block);

					return;

				}

				if (clayBlock.contains(block)) {

					clayBlock.remove(block);
					event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 6, 2));

					new BukkitRunnable() {

						@Override
						public void run() {

							player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_LAUNCH, 6, 10);

						}

					}.runTaskLater(Skywars.getInstance(), 20L);

					return;

				}
			}
		}
	}

	public void playerEffect(Entity et) {

		for (Entity entity : getSquid.get(et).getNearbyEntities(8, 8, 8)) {

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