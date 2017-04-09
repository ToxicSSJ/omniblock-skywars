package net.omniblock.skywars.games.solo.chest.item.z.listeners;

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
import net.omniblock.skywars.games.solo.chest.item.z.listeners.type.ItemType;
import net.omniblock.skywars.games.solo.chest.item.z.type.EItem;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;
import net.omniblock.skywars.util.block.SpawnBlock;

public class IBall implements ItemType, Listener {

	private static SoloPlayerManager soloplayer;
	private static List<Snowball> snowball = new ArrayList<Snowball>();
	
	@SuppressWarnings("static-access")
	@Override
	@EventHandler(priority = EventPriority.NORMAL)
	public void IceBall(PlayerInteractEvent event){
		
		Player player = event.getPlayer();
		
		if(soloplayer.getPlayersInGameList().contains(player) && player.getGameMode() == GameMode.SURVIVAL){
			if(player.getItemInHand().hasItemMeta()){
				if(player.getItemInHand().getItemMeta().hasDisplayName()){
					if(player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(EItem.BOLA_CONGELACEREBROS.getName())){
						if(event.getAction() == Action.LEFT_CLICK_AIR 
							|| event.getAction() == Action.RIGHT_CLICK_BLOCK
				    		|| event.getAction() == Action.LEFT_CLICK_BLOCK 
				    		|| event.getAction() == Action.RIGHT_CLICK_AIR){
												
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
										SpawnBlock.blockGenerator(block.getBlock(), 4, 4, 4, 2, 2, 2, 5);
										LIFE_TIME++;
										if(LIFE_TIME == 60){
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
	@SuppressWarnings("unused")
	@EventHandler
	public void iceBalleffect(ProjectileHitEvent event){
		if(event.getEntity() instanceof Snowball){
			Snowball sb = (Snowball) event.getEntity();
			Location location = sb.getLocation();
			sb.remove();
			location.getWorld().playSound(location, Sound.EXPLODE, 5, 10);
			List<Block> circle = SpawnBlock.circle(location, 3,1,false, true, -1);
			for(Block b : circle){
				if(b.getType() == Material.AIR) continue;
				b.setType(Material.PACKED_ICE);
			}
		}
	}
}
