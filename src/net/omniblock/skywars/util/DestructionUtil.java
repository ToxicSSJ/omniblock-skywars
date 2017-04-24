package net.omniblock.skywars.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.FallingBlock;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import net.omniblock.skywars.patch.managers.CustomProtocolManager;

public class DestructionUtil {

	public static class Destruction extends BukkitRunnable {
		
		public boolean enabled = false;
		
		public List<String> saved_proto = new ArrayList<String>();
		public List<Block> cache_blocks = new ArrayList<Block>();
		
		public DestructionInfo contaminfo = null;
		
		public Block pos_block = null;
		public Block center_block = null;
		
		public int distance = 30;
		public int i = 0;
		
		public void setDestructionDefaults(DestructionInfo ci) {
			
			contaminfo = ci;
			
			pos_block = ci.getB();
			pos_block.getLocation().setY(0);
			
			cache_blocks.add(pos_block);
			center_block = ci.getB();
			
			enabled = true;
			
		}
		
		public void setDestructionDefaults(DestructionInfo ci, int distance) {
			
			contaminfo = ci;
			
			pos_block = ci.getB();
			pos_block.getLocation().setY(0);
			
			cache_blocks.add(pos_block);
			center_block = ci.getB();
			
			enabled = true;
			
			this.distance = distance;
			
		}
		
		@Override
		public void run() {
			
			if(i >= 150) {
				stop();
			}
			
			if(!enabled || pos_block == null) {
				return;
			}
			
			if(destruction(pos_block)) {
				return;
			}
			
			Collections.shuffle(cache_blocks);
			
			for(Block b : cache_blocks) {
				if(destruction(b)) {
					i = 0;
					return;
				}
			}
			
			i++;
			return;
		}
		
		public void stop() {
			
			enabled = false;
			cancel();
			
		}
		
		public boolean destruction(Block block) {
			
			@SuppressWarnings("serial")
			List<Block> FACES = new ArrayList<Block>() {{
				for(BlockFace bf : BlockFace.values()) {
					if(bf != BlockFace.SELF) {
						add(block.getRelative(bf));
					}
				}
			}};
			
			Collections.shuffle(FACES);
			
			for(Block b : FACES) {
				if(!cache_blocks.contains(b)) {
					if(b.getLocation().distance(center_block.getLocation()) <= distance) {
						if(b.getType() != Material.AIR && !CustomProtocolManager.PROTECTED_BLOCK_LIST.contains(b)) {
							
							Block lower = getLowestBlockAt(b);
							if(lower != null) {
								
								cache_blocks.add(lower);
								
								if(NumberUtil.getRandomInt(1, 3) == 2) {
									@SuppressWarnings("deprecation")
									FallingBlock fallingblock = lower.getWorld().spawnFallingBlock(lower.getLocation(), lower.getType(), lower.getData());
									fallingblock.setVelocity(new Vector(0, -2, 0));
								}
								
								lower.setType(Material.AIR);
								
								if(NumberUtil.getRandomInt(1, 4) == 2) {
									
									Block friend = b.getRelative(BlockFace.UP);
									if(friend.getType() != Material.AIR && !CustomProtocolManager.PROTECTED_BLOCK_LIST.contains(friend)) {

										cache_blocks.add(friend);
										
										if(NumberUtil.getRandomInt(1, 3) == 2) {
											@SuppressWarnings("deprecation")
											FallingBlock friendfallingblock = friend.getWorld().spawnFallingBlock(friend.getLocation(), friend.getType(), friend.getData());
											friendfallingblock.setVelocity(new Vector(0, -2, 0));
										}
										
										friend.setType(Material.AIR);
										
									}
								}
								
							}
							
							return true;
						}
					}
				}
				
			}
			
			return false;
			
		}
		
		public static Block getLowestBlockAt(Block block) {
			
			for(int y = 0; y < 256; y++) {
				Block cache = new Location(block.getWorld(), block.getX(), y, block.getZ()).getBlock();
				if(cache.getType() != Material.AIR) {
					return cache;
				}
			}
			
			return null;
		}
		
		public ArrayList <Block> getBlocks(Block start, int radius) {
		    ArrayList <Block> blocks = new ArrayList < Block > ();
		    for (double x = start.getLocation().getX() - radius; x <= start.getLocation().getX() + radius; x++) {
		        for (double y = start.getLocation().getY() - radius; y <= start.getLocation().getY() + radius; y++) {
		            for (double z = start.getLocation().getZ() - radius; z <= start.getLocation().getZ() + radius; z++) {
		                Location loc = new Location(start.getWorld(), x, y, z);
		                blocks.add(loc.getBlock());
		            }
		        }
		    }
		    return blocks;
		}
		
	}
	
	public static class DestructionInfo {
		
		private Block b = null;
		private World w = null;
		
		public DestructionInfo(Block b, World w) {
			this.setB(b);
			this.setW(w);
		}

		public Block getB() {
			return b;
		}

		public void setB(Block b) {
			this.b = b;
		}

		public World getW() {
			return w;
		}

		public void setW(World w) {
			this.w = w;
		}
		
	}
	
}
