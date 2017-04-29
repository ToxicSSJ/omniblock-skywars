package net.omniblock.skywars.patch.managers.chest.item.z;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;
import net.omniblock.skywars.games.teams.managers.TeamPlayerManager;
import net.omniblock.skywars.patch.managers.chest.item.type.EItem;
import net.omniblock.skywars.patch.managers.chest.item.z.type.ItemType;
import net.omniblock.skywars.util.block.SpawnBlock;

public class IBall implements ItemType, Listener {

	public static List<Snowball> snowball = new ArrayList<Snowball>();
	
	@Override
	@EventHandler(priority = EventPriority.NORMAL)
	public void IceBall(PlayerInteractEvent event){
		
		Player player = event.getPlayer();
		
		if(SoloPlayerManager.getPlayersInGameList().contains(player) || TeamPlayerManager.getPlayersInGameList().contains(player) && player.getGameMode() == GameMode.SURVIVAL){
			if(player.getItemInHand().hasItemMeta()){
				if(player.getItemInHand().getItemMeta().hasDisplayName()){
					if(player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(EItem.BOLA_CONGELACEREBROS.getName())){
						if(event.getAction() == Action.LEFT_CLICK_AIR 
							|| event.getAction() == Action.RIGHT_CLICK_BLOCK
				    		|| event.getAction() == Action.LEFT_CLICK_BLOCK 
				    		|| event.getAction() == Action.RIGHT_CLICK_AIR){
							

							if(event.getClickedBlock() != null) {
								if(event.getClickedBlock().getType() == Material.CHEST ||
										event.getClickedBlock().getType() == Material.TRAPPED_CHEST ||
										event.getClickedBlock().getType() == Material.JUKEBOX) {
									
									event.setCancelled(true);
									return;
									
								}
							}
							
							player.getInventory().setItemInHand(null);	
							Vector dir = player.getLocation().getDirection().normalize().multiply(3);
							final Snowball sb = player.launchProjectile(Snowball.class);
							sb.setVelocity(dir);
			    			
							new BukkitRunnable(){
								int LIFE_TIME = 0;
							
								@Override
								public void run(){
									if(sb.isDead() != true){
										
										Location block = sb.getLocation();
										block.getWorld().playEffect(block, Effect.HAPPY_VILLAGER, 4);
										block.getWorld().playSound(block, Sound.BLAZE_HIT, 3, 1);
										new BukkitRunnable(){
											@Override
											public void run() {
												SpawnBlock.blockGenerator(block.getBlock(), 1, 2, 1, -1, -1, -1, 4);
											}
											
										}.runTaskLater(Skywars.getInstance(), 5L);
										LIFE_TIME++;
										if(LIFE_TIME == 60){
											sb.remove();
											cancel();
										}
			    					 
									} else {
										cancel();
									}
								}
							}.runTaskTimer(Skywars.getInstance(), 1L, 1L);
						}	
					}
				}
			}
		}
	}
	
	@EventHandler
	public void iceBalleffect(ProjectileHitEvent event){
		
		if(event.getEntity() instanceof Snowball){
			
			Snowball sb = (Snowball) event.getEntity();
			Location location = sb.getLocation();
			
			sb.remove();
			location.getWorld().playSound(location, Sound.EXPLODE, 5, 10);
			List<Block> circle = SpawnBlock.circle(location, 6,1,false, true, -1);
			for(Block b : circle){
				if(b.getType() == Material.AIR) continue;
				b.setType(Material.PACKED_ICE);
			}
			
		}
		
	}
}
