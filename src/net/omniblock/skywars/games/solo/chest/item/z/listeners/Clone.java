package net.omniblock.skywars.games.solo.chest.item.z.listeners;

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
import net.omniblock.skywars.games.solo.chest.item.z.listeners.object.ClonData;
import net.omniblock.skywars.games.solo.chest.item.z.listeners.type.ItemType;
import net.omniblock.skywars.games.solo.chest.item.z.type.EItem;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;
import net.omniblock.skywars.util.block.SpawnBlock;

public class Clone implements Listener, ItemType {
	
	private static Map<Player, ClonData> oneClon = new HashMap<Player, ClonData>();
	@Override
	@EventHandler
	public void CloneSpell(PlayerInteractEvent event){
		Player player = event.getPlayer();

		if(SoloPlayerManager.getPlayersInGameList().contains(player) && player.getGameMode() == GameMode.SURVIVAL){
			
			if(event.getPlayer().getItemInHand().hasItemMeta()){
				if(event.getPlayer().getItemInHand().getItemMeta().hasDisplayName()){
					if(event.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(EItem.VARITA_MAGICA.getName())){
						
						if (event.getAction() == Action.LEFT_CLICK_BLOCK 
								|| event.getAction() == Action.RIGHT_CLICK_BLOCK
								|| event.getAction() == Action.RIGHT_CLICK_AIR
								|| event.getAction() == Action.LEFT_CLICK_AIR) {
											
							if(event.getClickedBlock() != null) {
								if(event.getClickedBlock().getType() == Material.CHEST ||
									event.getClickedBlock().getType() == Material.TRAPPED_CHEST ||
									event.getClickedBlock().getType() == Material.JUKEBOX) {
									event.setCancelled(true);
									return;	
							}
						}
						
							
							if(!oneClon.containsKey(player)){
								ClonData clon = new ClonData(player, player.getLocation());
								
								player.playSound(player.getPlayer().getLocation(), Sound.CLICK, 10, 2);
								player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 145, 2));
								player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 145, 2));
							
								oneClon.put(player, clon);
								oneClon.get(player).makeClon();
								RemoveClon(player, clon.getClon());

	    					}else{
	    						explodeSimulation(oneClon.get(player).getSaved());
	    						oneClon.get(player).remove();
	    						oneClon.remove(player);
	    						player.removePotionEffect(PotionEffectType.SPEED);
	    						player.removePotionEffect(PotionEffectType.INVISIBILITY);
	    						player.getWorld().playSound(player.getLocation(), Sound.EXPLODE, 2, 2);
	    						player.getWorld().playSound(player.getLocation(), Sound.GHAST_CHARGE, 10, 1);
	    						player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 5, 10));
	    						player.setVelocity(player.getLocation().getDirection().add(new Vector(0,1,0)));
	    						
	    					}
    					}
					}
				}
			}
		}
	}
	
	public void RemoveClon(Player player, NPC clon){

		ItemStack helmet =  player.getInventory().getHelmet();
		ItemStack chestplate = player.getInventory().getChestplate();;
		ItemStack leggings = player.getInventory().getLeggings();;
		ItemStack boots = player.getInventory().getBoots();;
		
		player.getInventory().setHelmet(null);	
		player.getInventory().setChestplate(null);	
		player.getInventory().setLeggings(null);
		player.getInventory().setBoots(null);

		new BukkitRunnable(){
			int TIME = 0;
			@Override
			public void run() {
				TIME+=20;
				
				if(clon != null){
					if(clon.isSpawned()){
						if(TIME == 140){
							
							oneClon.get(player).remove();
							oneClon.remove(player);
							
							player.getInventory().setItemInHand(null);
							player.getInventory().setHelmet(helmet);
							player.getInventory().setChestplate(chestplate);
							player.getInventory().setLeggings(leggings);
							player.getInventory().setBoots(boots);
							
							player.playSound(player.getLocation(), Sound.PORTAL_TRIGGER, 5, 10);
							player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 25, 10));
							
							cancel();
						
						}
					}else{
						
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

	public void explodeSimulation(Location location){
		List<Block> cube = SpawnBlock.circle(location, 4,1,false, true, -1);
		for(Block b : cube){
			b.setType(Material.AIR);	
		}
	}
}
	
	

