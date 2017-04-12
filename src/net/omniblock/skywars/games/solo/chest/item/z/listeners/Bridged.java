package net.omniblock.skywars.games.solo.chest.item.z.listeners;

import java.util.ArrayList;
import java.util.List;

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

import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.games.solo.chest.item.z.listeners.type.ItemType;
import net.omniblock.skywars.games.solo.chest.item.z.type.EItem;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;

public class Bridged implements ItemType, Listener {

	private static SoloPlayerManager soloplayer;
	private static final int BRIDGE_SIZE = 20;
	private boolean build = false;
	
	private static List<Block> bridgeremove = new ArrayList<Block>();

	@SuppressWarnings("static-access")
	@Override
	@EventHandler
	public void BridgeHud(BlockPlaceEvent event) {
		
		Player player = event.getPlayer();
	
		if (soloplayer.getPlayersInGameList().contains(player) && player.getGameMode() == GameMode.SURVIVAL) {
			
			if (player.getInventory().getItemInHand().hasItemMeta()){
				if(player.getInventory().getItemInHand().getItemMeta().hasDisplayName()){
					
					if(player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(EItem.PUENTE_FUTURISTICO.getName())){
						if(event.getBlockPlaced().getType() == Material.MELON_BLOCK){
							Block block = event.getBlock();
							
								CreateBridged bridged = new CreateBridged(player, block, player.getWorld());
								bridged.createBridged();
								player.getWorld().playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1, -2);
														
						}else{
							return;
						
						}
					}
				}
			}	
		}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void breakBridged(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (soloplayer.getPlayersInGameList().contains(player) && player.getGameMode() == GameMode.SURVIVAL) {
			if (player.getLocation().add(0, -1, 0).getBlock().getType() == Material.STAINED_GLASS) {
				Block block = event.getPlayer().getLocation().add(0, -1, 0).getBlock();
				if (bridgeremove.contains(block)) {
					bridgeremove.remove(block);
					block.setTypeIdAndData(95, (byte) 14, true);
					deleteBirdged(player, block);
				}
			}
		}
	}

	
	public void deleteBirdged(Player player, Block block) {
		new BukkitRunnable() {
			@Override
			@SuppressWarnings("deprecation")
			public void run() {
				
				block.setType(Material.AIR);
				FallingBlock fallingblock = player.getWorld().spawnFallingBlock(block.getLocation(), Material.STAINED_GLASS, (byte) 14);
				fallingblock.setVelocity(new Vector(0, 3, 0));
				block.getWorld().playSound(block.getLocation(), Sound.CHICKEN_EGG_POP, 5, -15);
				
				new BukkitRunnable(){
					@Override
					public void run(){
						
						if(fallingblock.isDead() == false){
							
							fallingblock.remove();
							cancel();
							return;
						
						} else {
							
							cancel();
						}
					}
				}.runTaskLater(Skywars.getInstance(), 20L);
			}

		}.runTaskLater(Skywars.getInstance(), 20L);
	}
	
	public static BlockFace getBlockFace(Player player) {
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
	
	public class CreateBridged{
		private Player player;
		private Block block;
		private World world;
		
		public CreateBridged(Player player, Block block, World world){
			
			this.player = player;
			this.block = block;
			this.world = world;
			
		}
		
		
		public void createBridged() {
			List<Block> bridged = new ArrayList<Block>();
			BlockFace bf = getBlockFace(player);
			Block b;
			bridged.add(block);
			bridgeremove.add(block);
			
			for(int i = 0; i < BRIDGE_SIZE; i++){
				b = bridged.get(i);
				bridged.add(b.getRelative(bf));
				bridgeremove.add(b.getRelative(bf));
				
			}
			
			new BukkitRunnable(){
				int i = 0;
				@Override
				public void run() {
					
					if(i != BRIDGE_SIZE){
						Block line = bridged.get(i);
						world.playSound(line.getLocation(), Sound.GLASS, 1, -20);
						world.playEffect(line.getLocation(), Effect.STEP_SOUND, Material.GLASS);
						line.setType(Material.STAINED_GLASS);
						i++;
					}else{
						bridged.clear();
						cancel();
					}
				}
				
			}.runTaskTimer(Skywars.getInstance(), 0L, (long) (0.2*20));
		}
	}
}
