package net.omniblock.skywars.patch.managers.chest.defaults.events;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import net.citizensnpcs.api.npc.NPC;
import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;
import net.omniblock.skywars.games.teams.managers.TeamPlayerManager;
import net.omniblock.skywars.patch.managers.chest.defaults.events.stuff.ClonData;
import net.omniblock.skywars.patch.managers.chest.defaults.events.type.ItemType;
import net.omniblock.skywars.util.block.SpawnBlock;

public class Clone implements Listener, ItemType {

	private Map<Player, ClonData> oneClon = new HashMap<Player, ClonData>();

	/**
     *
     * Con este evento, crearas un NPC(clon).
     *
     */
	@SuppressWarnings("deprecation")
	@Override
	@EventHandler
	public void CloneSpell(PlayerInteractEvent event) {
		Player player = event.getPlayer();

		if (SoloPlayerManager.getPlayersInGameList().contains(player)
				|| TeamPlayerManager.getPlayersInGameList().contains(player)
						&& player.getGameMode() == GameMode.SURVIVAL) {

			if (event.getPlayer().getItemInHand().hasItemMeta()) {
				if (event.getPlayer().getItemInHand().getItemMeta().hasDisplayName()) {
					if (event.getPlayer().getItemInHand().getType() == Material.RECORD_7) {

						if (event.getAction() == Action.LEFT_CLICK_BLOCK
								|| event.getAction() == Action.RIGHT_CLICK_BLOCK
								|| event.getAction() == Action.RIGHT_CLICK_AIR
								|| event.getAction() == Action.LEFT_CLICK_AIR) {

							if (event.getClickedBlock() != null) {
								
								if (event.getClickedBlock().getType() == Material.CHEST
										|| event.getClickedBlock().getType() == Material.TRAPPED_CHEST
										|| event.getClickedBlock().getType() == Material.JUKEBOX) {
									
									event.setCancelled(true);
									return;
									
								}
								
							}

							if (!oneClon.containsKey(player)) {
								
								ClonData clon = new ClonData(player, player.getLocation());

								player.playSound(player.getPlayer().getLocation(), Sound.UI_BUTTON_CLICK, 10, 2);
								player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 145, 2));
								player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 145, 2));

								oneClon.put(player, clon);
								oneClon.get(player).makeClon();
								
								Clon(player, clon.getClon());

							} else {
								
								explodeSimulation(oneClon.get(player).getSaved());
								
								while(oneClon.containsKey(player)) {
									
									oneClon.get(player).remove();
									oneClon.remove(player);
									
								}
								
								player.removePotionEffect(PotionEffectType.SPEED);
								player.removePotionEffect(PotionEffectType.INVISIBILITY);
								
								player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 2, 2);
								player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 10, 1);
								player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 5, 10));
								
								player.setVelocity(player.getLocation().getDirection().add(new Vector(0, 1, 0)));

							}
							
						}
					}
				}
			}
		}
	}

	/**
     *
     * Este método crea y elimina al NPC.
     *
     * @param player
     *            Se utiliza el jugador para crear un NPC de él.
     * @param clon
     *            Se utiliza la Clase ClonData para obtener el NPC.
     *
     */
	@SuppressWarnings("deprecation")
	public void Clon(Player player, NPC clon) {

		ItemStack hand = player.getItemInHand();
		ItemStack helmet = player.getInventory().getHelmet();
		ItemStack chestplate = player.getInventory().getChestplate();
		ItemStack leggings = player.getInventory().getLeggings();
		ItemStack boots = player.getInventory().getBoots();

		player.getInventory().setHelmet(null);
		player.getInventory().setChestplate(null);
		player.getInventory().setLeggings(null);
		player.getInventory().setBoots(null);

		new BukkitRunnable() {
			int TIME = 0;

			@Override
			public void run() {
				TIME += 20;

				if (clon != null) {
					if (clon.isSpawned()) {
						
						if (TIME == 140) {

							player.getInventory().remove(hand);

							oneClon.get(player).remove();
							oneClon.remove(player);

							player.getInventory().setHelmet(helmet);
							player.getInventory().setChestplate(chestplate);
							player.getInventory().setLeggings(leggings);
							player.getInventory().setBoots(boots);

							player.playSound(player.getLocation(), Sound.BLOCK_PORTAL_TRIGGER, 5, 10);
							player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 25, 10));

							cancel();

						}
						
					} else {

						player.getInventory().setItemInHand(null);
						player.getInventory().setHelmet(helmet);
						player.getInventory().setChestplate(chestplate);
						player.getInventory().setLeggings(leggings);
						player.getInventory().setBoots(boots);
						
						cancel();
						
					}
				}
			}

		}.runTaskTimer(Skywars.getInstance(), 0, 20L);

	}

	/**
     *
     * Este método los bloques se volverán aire en un radio.
     *
     * @param location
     *            Lotización en donde los bloques pasaran a ser aire.
     */
	public void explodeSimulation(Location location) {
		List<Block> cube = SpawnBlock.circle(location, 4, 1, false, true, -1);
		for (Block b : cube) {
			b.setType(Material.AIR);
		}
	}
	
}
