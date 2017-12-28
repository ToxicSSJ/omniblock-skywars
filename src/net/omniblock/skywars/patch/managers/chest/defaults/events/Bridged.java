package net.omniblock.skywars.patch.managers.chest.defaults.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import net.omniblock.network.library.helpers.effectlib.effect.LineEffect;
import net.omniblock.network.library.helpers.effectlib.util.ParticleEffect;
import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;
import net.omniblock.skywars.games.teams.managers.TeamPlayerManager;

public class Bridged implements Listener {

	private final int getFirstBlock = 0;
	private final int getLastBlock = 25;
	private final int BRIDGE_SIZE = 25;

	public static List<Block> bridged = new ArrayList<Block>();

	/**
	 *
	 * Con este evento, se creará un puente de STAINED_GLASS cuando el usuario
	 * coloque un bloque de MELON.
	 */
	@SuppressWarnings("deprecation")
	@EventHandler
	public void BridgeHud(BlockPlaceEvent event) {

		Player player = event.getPlayer();

		if (SoloPlayerManager.getPlayersInGameList().contains(player)
				|| TeamPlayerManager.getPlayersInGameList().contains(player)
						&& player.getGameMode() == GameMode.SURVIVAL) {

			if (player.getInventory().getItemInHand().hasItemMeta()) {
				if (player.getInventory().getItemInHand().getItemMeta().hasDisplayName()) {

					if (!Skywars.ingame)
						return;

					if (event.getBlockPlaced().getType() == Material.MELON_BLOCK) {

						Block block = event.getBlock();
						CreateBridged(player, block, player.getWorld());
						player.getWorld().playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, -2);

					}
				}
			}
		}
	}

	/**
	 *
	 * Con este evento, se eliminarán los bloques al momento que el jugar camine por
	 * los bloques de STAINED_GLASS
	 *
	 */
	@SuppressWarnings("deprecation")
	@EventHandler
	public void breakBridged(PlayerMoveEvent event) {

		Player player = event.getPlayer();

		if (SoloPlayerManager.getPlayersInGameList().contains(player)
				|| TeamPlayerManager.getPlayersInGameList().contains(player)
						&& player.getGameMode() == GameMode.SURVIVAL) {

			if (player.getLocation().add(0, -1, 0).getBlock().getType() == Material.STAINED_GLASS
					|| player.getLocation().add(0, -1, 0).getBlock().getType() == Material.COAL_BLOCK) {

				Block block = event.getPlayer().getLocation().add(0, -1, 0).getBlock();

				if (Bridged.bridged.contains(block)) {

					Bridged.bridged.remove(block);
					block.setTypeIdAndData(95, (byte) 14, true);

					new BukkitRunnable() {

						@Override
						public void run() {

							block.setType(Material.AIR);
							FallingBlock fallingblock = event.getPlayer().getWorld()
									.spawnFallingBlock(block.getLocation(), Material.STAINED_GLASS, (byte) 14);
							fallingblock.setVelocity(new Vector(0, 3, 0));
							block.getWorld().playSound(block.getLocation(), Sound.ENTITY_CHICKEN_EGG, 5, -15);

							new BukkitRunnable() {
								@Override
								public void run() {

									if (fallingblock.isDead() == false) {

										fallingblock.remove();
										cancel();
										return;

									} else {

										cancel();
									}
								}
							}.runTaskLater(Skywars.getInstance(), 15L);
						}

					}.runTaskLater(Skywars.getInstance(), 15L);
				}

			}
		}
	}

	/**
	 *
	 * Con este metodo, Con este método, obtendrás unas de las caras del bloque, con
	 * respecto a donde mira el jugador.
	 *
	 * @return BlockFace Te devuelve la cara del bloque, dependiendo donde mire el
	 *         jugador.
	 * @param player
	 *            Jugador al cual obtendrás la dirección de su cabeza, como: west,
	 *            north, etc.
	 *
	 */
	public BlockFace getBlockFace(Player player) {

		double rotation = (player.getLocation().getYaw() - 90) % 360;

		if (rotation < 0) {
			rotation += 360.0;
		}

		if (0 <= rotation && rotation < 22.5) {
			return BlockFace.WEST;
		} else if (22.5 <= rotation && rotation < 67.5) {
			return BlockFace.WEST;
		} else if (67.5 <= rotation && rotation < 112.5) {
			return BlockFace.NORTH;
		} else if (112.5 <= rotation && rotation < 157.5) {
			return BlockFace.NORTH;
		} else if (157.5 <= rotation && rotation < 202.5) {
			return BlockFace.EAST;
		} else if (202.5 <= rotation && rotation < 247.5) {
			return BlockFace.EAST;
		} else if (247.5 <= rotation && rotation < 292.5) {
			return BlockFace.SOUTH;
		} else if (292.5 <= rotation && rotation < 337.5) {
			return BlockFace.SOUTH;
		} else if (337.5 <= rotation && rotation < 360.0) {
			return BlockFace.WEST;
		} else {
			return BlockFace.NORTH;
		}

	}

	/**
	 *
	 * Con este metodo, se colocarán bloques con un límite en línea recta.
	 *
	 * @param player
	 *            Jugador que crea el punte.
	 * @param block
	 *            Tipo de bloque que coloca el jugador.
	 * @param world
	 *            En que mundo se coloco el bloque.
	 *
	 */
	public void CreateBridged(Player player, Block block, World world) {

		List<Block> bridged = new ArrayList<Block>();
		
		BlockFace bf = getBlockFace(player);
		Block b = null;
		bridged.add(block);
		Bridged.bridged.add(block);

		for (int i = 0; i < BRIDGE_SIZE; i++) {
		
			b = bridged.get(i);
			bridged.add(b.getRelative(bf));
			Bridged.bridged.add(b.getRelative(bf));

		}

		Block firstBlock = bridged.get(getFirstBlock);
		Block lastBlock = bridged.get(getLastBlock);

		firstBlock.setType(Material.COAL_BLOCK);
		lastBlock.setType(Material.COAL_BLOCK);

		new BukkitRunnable() {

			int i = 1;
			int time = 0;

			@Override
			public void run() {

				laser(firstBlock, lastBlock);

				time += 20;

				if (time >= 80) {
					
					cancel();
					
					new BukkitRunnable() {

						@Override
						public void run() {

							if (i != BRIDGE_SIZE) {
								
								Block lineBlock = bridged.get(i);
								world.playSound(lineBlock.getLocation(), Sound.BLOCK_GLASS_BREAK, 1, -20);
								world.playEffect(lineBlock.getLocation(), Effect.STEP_SOUND, Material.GLASS);
								lineBlock.setType(Material.STAINED_GLASS);
								
								i++;
							
							} else {
								
								cancel();
							
							}

						}

					}.runTaskTimer(Skywars.getInstance(), 0L, 1L);

				}
			}

		}.runTaskTimer(Skywars.getInstance(), 0L, 20L);

	}

	private void laser(Block pos1, Block pos2) {

		if (pos1.getType() == Material.COAL_BLOCK 
				|| pos2.getType() == Material.COAL_BLOCK
				|| pos1.getType() == Material.STAINED_GLASS
				|| pos2.getType() == Material.STAINED_GLASS) {

			pos1.getLocation().getWorld().playSound(pos1.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 5, -2);

			LineEffect ef = new LineEffect(Skywars.effectmanager);
			ef.particle = ParticleEffect.REDSTONE;
			ef.color = Color.RED;
			ef.autoOrient = true;
			ef.visibleRange = 500;
			ef.setLocation(pos2.getLocation().clone().add(0.5, 0.5, 0.5));
			ef.setTargetLocation(pos1.getLocation().clone().add(0.5, 0.5, 0.5));
			ef.start();

		}
	}
}
