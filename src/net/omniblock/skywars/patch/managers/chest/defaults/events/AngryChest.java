package net.omniblock.skywars.patch.managers.chest.defaults.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;
import net.omniblock.skywars.games.teams.managers.TeamPlayerManager;
import net.omniblock.skywars.patch.managers.chest.defaults.events.type.ItemType;
import net.omniblock.skywars.util.NumberUtil;
import net.omniblock.skywars.util.block.SpawnBlock;

public class AngryChest implements ItemType, Listener {

	private List<Block> chestblock = new ArrayList<Block>();
	private int CHEST_TYPE = 0;


    /**
     * Con este evento, ubicaras o registraras la posición del AngryChest, esta
     * ubicación se utilizara para determinar donde se desarrollara los efectos.
     *
     */
	@SuppressWarnings("deprecation")
	@EventHandler
	public void AngryChests(BlockPlaceEvent event) {

		Player player = event.getPlayer();
		if (SoloPlayerManager.getPlayersInGameList().contains(player)
				|| TeamPlayerManager.getPlayersInGameList().contains(player)
						&& player.getGameMode() == GameMode.SURVIVAL) {
			if (player.getItemInHand().hasItemMeta()) {
				if (player.getItemInHand().getItemMeta().hasDisplayName()) {
					if (player.getItemInHand().getType() == Material.TRAPPED_CHEST) {

						player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_DOOR_WOOD, 5, -10);
						player.getWorld().playEffect(event.getBlockPlaced().getLocation(), Effect.SMOKE, 10);
						chestblock.add(event.getBlockPlaced().getLocation().getBlock());

					}
				}
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
	@SuppressWarnings("unused")
	@Override
	@EventHandler
	public void AngryChests(PlayerInteractEvent event) {

		Player player = event.getPlayer();

		if (SoloPlayerManager.getPlayersInGameList().contains(player)
				|| TeamPlayerManager.getPlayersInGameList().contains(player)
						&& player.getGameMode() == GameMode.SURVIVAL) {
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK) {
				if (event.getClickedBlock().getType() == Material.TRAPPED_CHEST) {
					if (chestblock.contains(event.getClickedBlock())) {
						chestblock.remove(event.getClickedBlock());

						Block block = event.getClickedBlock();
						Location location = block.getLocation();
						removeChest(block, location);
						CHEST_TYPE = NumberUtil.getRandomInt(1, 3);

						switch (CHEST_TYPE) {
						
						case 1:
							
							jail(location, block);
							Block b = location.getBlock().getRelative(0, -2, 0);
							location.getWorld().playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 2, 2);
							break;
							
						case 2:
							
							circle(location, block, Material.PACKED_ICE, 8);

							player.playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 5, 10);
							player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 3, 2));
							player.setVelocity(location.getDirection().add(new Vector(0, 2, 0)));
							SpawnBlock.columns(block, 6, 15, 6, -2, -10, -2, 8, 20, true);
							break;
							
						case 3:
							
							location.getWorld().playSound(location, Sound.ENTITY_WOLF_HOWL, 5, 30);
							location.getWorld().playSound(location, Sound.ENTITY_GHAST_SCREAM, 5, 30);

							player.getWorld().playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 50);
							location.getWorld().playEffect(location, Effect.MOBSPAWNER_FLAMES, 50);
							player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 3, 2));
							break;
							
						default:
							break;
						}
					}
				}
			}
		}
	}

	/**
     *
     * Con este método podrás eliminar un cofre.
     *
     * @param block
     *            Tipo de bloque.
     * @param location
     *            Ubicación donde se encuentra el bloque.
     *
     */
	public void removeChest(Block block, Location location) {

		Chest chest = (Chest) block.getState();
		chest.getInventory().clear();
		chest.update();
		block.setType(Material.AIR);
	}

    /**
     *
     * Con este metodo, se generará un hueco en la posición del “Bloque” con paredes
     * de hielo.
     *
     * @param block
     *            Tipo de bloque.
     * @param location
     *            Ubicación donde se encuentra el bloque.
     *
     */
	public void jail(Location location, Block block) {
		List<Block> locblock = SpawnBlock.blockGeneratorInList(block, 10, 20, 10, +1, +1, -5, 20);
		List<Block> cube = SpawnBlock.circle(location, 4, 1, false, true, -1);
		circle(location, block, Material.ICE, 8);
		for (Block b : cube) {
			b.setType(Material.AIR);
		}

		for (Block b : locblock) {
			SpawnBlock.bounceBlock(b, (float) 0.8);

		}
	}

    /**
     *
     * Con este metodo, se generará un circulo con partículas en un radio.
     *
     * @param location
     *            Localización del bloque.
     * @param block
     *            Tipo de bloque.
     * @param material
     *            Tipo de material con el cual se hará el circulo.
     * @param integer
     *            Tamaño del circulo.
     *
     */
	@SuppressWarnings("deprecation")
	public void circle(Location location, Block bloc, Material m, Integer r) {
		List<Block> cube = SpawnBlock.circle(location, r, 1, false, true, -1);
		for (Block b2 : cube) {
			location.getWorld().playEffect(location, Effect.FIREWORKS_SPARK, 10);
			if (b2.getType() == Material.AIR)
				continue;
			b2.setType(m);
		}
	}
	
}
