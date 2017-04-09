package net.omniblock.skywars.games.solo.chest.item.z.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
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

import net.omniblock.skywars.games.solo.chest.item.z.listeners.type.ItemType;
import net.omniblock.skywars.games.solo.chest.item.z.type.EItem;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;
import net.omniblock.skywars.util.NumberUtil;
import net.omniblock.skywars.util.block.SpawnBlock;

public class AngryChest implements ItemType, Listener {
	
	private static SoloPlayerManager soloplayer;
	
	private static List<Block> chestN = new ArrayList<Block>();
	private static List<Block> chestR = new ArrayList<Block>();

	public enum Phase{
	
		JAIL, 
		SKEWERS,
		NORMAL,
		POSION,
		;
	}
	
		
	private static Phase phase;
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void AngryChests(BlockPlaceEvent event){
		
		Player player = event.getPlayer();
		if(soloplayer.getPlayersInGameList().contains(player) && player.getGameMode() == GameMode.SURVIVAL){
			if(player.getItemInHand().hasItemMeta()){
				if(player.getItemInHand().getItemMeta().hasDisplayName()){
					if(player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(EItem.COFRE_EXPLOSIVO.getName())){
						if(event.getBlockPlaced().getType() == Material.TRAPPED_CHEST){
							
							/**
							* effect
							* */
												
							player.playSound(player.getLocation(), Sound.ZOMBIE_WOOD, 5, -10);
							player.getWorld().playEffect(event.getBlockPlaced().getLocation(), Effect.SMOKE, 10);
							
							
							/**
							* Chest Type
							* */
							
							chestR.add(event.getBlockPlaced().getLocation().getBlock());
							
						}
					}else{
						return;
					}
				}
			}
		}
	}
	
	@SuppressWarnings("static-access")
	@Override
	@EventHandler
	public void AngryChests(PlayerInteractEvent event){
		
		Player player = event.getPlayer();;
		
		if(soloplayer.getPlayersInGameList().contains(player) && player.getGameMode() == GameMode.SURVIVAL){
			if(event.getAction() ==  Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK){					
				if(event.getClickedBlock().getType() == Material.TRAPPED_CHEST){
					if(chestR.contains(event.getClickedBlock())){
						
						chestR.remove(event.getClickedBlock());
						Block block = event.getClickedBlock();
						Location location = block.getLocation();
						removeChest(block, location);
						ChestType();
						
						switch(phase){
							
						/**
						* Explode block effect
						*/	
							
						case JAIL:
							
							List<Block> locblock = SpawnBlock.blockGeneratorInList(block, 20);
							List<Block> cube = SpawnBlock.circle(location, 6,1,false, true, -1);
							List<Block> cube2 = SpawnBlock.circle(location, 4,1,false, true, -1);
							
							for(Block b : cube){
									
								location.getWorld().playEffect(location, Effect.FIREWORKS_SPARK, 10);
								if(b.getType() == Material.AIR) continue;
								b.setType(Material.ICE);
									
							}
							
							for(Block b : cube2){
								b.setType(Material.AIR);
									
							}
							
							for(Block b : locblock){
								
								SpawnBlock.bounceBlock(b, (float) 0.8);
							}
								
							Block b = location.getBlock().getRelative(0, -2, 0);
							location.getWorld().playSound(location, Sound.EXPLODE, 2, 2);
								
							break;

						case SKEWERS:
						
							SpawnBlock.columns(block, 10, 20, 10, -5, +1, -5, 20, 20, true);
							List<Block> cube3 = SpawnBlock.circle(location, 6,1,false, true, -1);
							player.playSound(location, Sound.EXPLODE, 5, 10);
							player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20*3, 2));
							player.setVelocity(location.getDirection().add(new Vector(0,2,0)));
								
							for(Block b2 : cube3){
									
								location.getWorld().playEffect(location, Effect.FIREWORKS_SPARK, 10);
								if(b2.getType() == Material.AIR) continue;
								b2.setType(Material.PACKED_ICE);
									
							}

								
							break;
						case POSION:
							
							World world = block.getLocation().getWorld();
							world.createExplosion(location, (float) 0.4F);							
							
							break;
						case NORMAL:
							
							location.getWorld().playSound(location, Sound.WOLF_HOWL, 5, 30);
							location.getWorld().playSound(location, Sound.GHAST_SCREAM2, 5, 30);
							
					      
					      				
					      	player.setVelocity(location.getDirection().add(new Vector(0,2,0)));
					      	player.getWorld().playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 50);
					      	location.getWorld().playEffect(location, Effect.MOBSPAWNER_FLAMES, 50);
					      	player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20*3, 2));

							
								break;
						}
					}
				}
			}			
		}
	}

	
	public void removeChest(Block block, Location location){
		
		Chest chest = (Chest) block.getState();
		chest.getInventory().clear();
		chest.update();
		block.setType(Material.AIR);

		
	}
	
	public void ChestType(){
		int r = NumberUtil.getRandomInt(3, +1);

		if(r == 1){
			phase = Phase.JAIL;
		}
		if(r == 2){
			phase = Phase.SKEWERS;
		}
		if(r == 3){
			phase = Phase.NORMAL;
		}
	}
}
