package net.omniblock.skywars.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.omniblock.lobbies.skywars.handler.base.SkywarsBase;
import net.omniblock.lobbies.skywars.handler.base.SkywarsBase.SelectedItemType;
import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;
import net.omniblock.skywars.games.teams.managers.TeamPlayerManager;
import net.omniblock.skywars.patch.managers.CustomProtocolManager;
import net.omniblock.skywars.patch.managers.MapManager;
import net.omniblock.skywars.util.lib.bukkit.FallingBlockWrapper;

public class DestructionUtil {

	public static Map<Player, Boolean> SPAWN_FB = new HashMap<Player, Boolean>();

	public static class Destruction extends BukkitRunnable {

		public boolean enabled = false;

		public List<String> saved_proto = new ArrayList<String>();
		public LinkedList<Block> cache_blocks = new LinkedList<Block>();

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

			if (i >= 150) {
				stop();
			}

			if (!enabled || pos_block == null) {
				return;
			}

			if (destruction(pos_block)) {
				return;
			}

			if (cache_blocks.size() > 1) {
				cache_blocks.removeFirst();
			}

			Collections.shuffle(cache_blocks);

			for (Block b : cache_blocks) {
				if (destruction(b)) {
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
			List<Block> FACES = new ArrayList<Block>() {
				{
					for (BlockFace bf : BlockFace.values()) {
						if (bf != BlockFace.SELF) {
							add(block.getRelative(bf));
						}
					}
				}
			};

			Collections.shuffle(FACES);

			for (Block b : FACES) {

				if (!cache_blocks.contains(b) && b.getLocation().distance(center_block.getLocation()) < distance) {

					if (b.getLocation().distance(center_block.getLocation()) <= distance) {

						if (b.getType() != Material.AIR && !CustomProtocolManager.PROTECTED_BLOCK_LIST.contains(b)) {

							Block lower = getLowestBlockAt(b);
							if (lower != null) {

								cache_blocks.add(lower);

								lower.setType(Material.AIR);

								if (NumberUtil.getRandomInt(1, 4) == 2) {

									Block friend = b.getRelative(BlockFace.UP);
									if (friend.getType() != Material.AIR
											&& !CustomProtocolManager.PROTECTED_BLOCK_LIST.contains(friend)) {

										cache_blocks.add(friend);
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

			for (int y = 0; y < 256; y++) {
				Block cache = new Location(block.getWorld(), block.getX(), y, block.getZ()).getBlock();
				if (cache.getType() != Material.AIR) {
					return cache;
				}
			}

			return null;
		}

		@SuppressWarnings("deprecation")
		public static void genDestructionBlock(Block block) {

			Material material = block.getType();
			byte subid = block.getData();

			for (Player p : MapManager.CURRENT_MAP.getPlayers()) {

				if (Skywars.ingame) {

					if (SoloPlayerManager.getPlayersInGameList().contains(p)
							|| TeamPlayerManager.getPlayersInGameList().contains(p)) {

						if (SkywarsBase.SAVED_ACCOUNTS.get(p) == null)
							continue;

						if (SkywarsBase.getSelectedItem(SelectedItemType.EXTRA_INFO,
								SkywarsBase.SAVED_ACCOUNTS.get(p).getSelected()).equals("K0")) {
							continue;
						}

						FallingBlockWrapper.sendPacket(p, material, subid);
						continue;

					}

					continue;

				}

			}

		}

		public ArrayList<Block> getBlocks(Block start, int radius) {
			ArrayList<Block> blocks = new ArrayList<Block>();
			for (double x = start.getLocation().getX() - radius; x <= start.getLocation().getX() + radius; x++) {
				for (double y = start.getLocation().getY() - radius; y <= start.getLocation().getY() + radius; y++) {
					for (double z = start.getLocation().getZ() - radius; z <= start.getLocation().getZ()
							+ radius; z++) {
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
