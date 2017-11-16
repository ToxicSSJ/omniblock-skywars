package net.omniblock.skywars.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;
import net.omniblock.skywars.games.teams.managers.TeamPlayerManager;
import net.omniblock.skywars.patch.types.SkywarsType;

public class ContaminationUtil {

	public static List<Location> actived_troopers = new ArrayList<Location>();

	public static final Material CONTAMINATION_MATERIAL = Material.SNOW_BLOCK;
	public static final byte CONTAMINATION_MATERIAL_DATA = 0;

	public static final Material TROOPER_MATERIAL = Material.WOOL;
	public static final byte TROOPER_MATERIAL_DATA = 9;

	public static class ContaminationTroop extends BukkitRunnable {

		SkywarsType trooptarget = null;
		List<Player> trooptargets = new ArrayList<Player>();

		public void startTroop() {

			SkywarsType swtype = Skywars.currentMatchType;
			trooptarget = swtype;

			genTrooperIA();
			return;

		}

		@SuppressWarnings("deprecation")
		@Override
		public void run() {

			if (trooptarget == null) {
				return;
			}

			if (trooptarget == SkywarsType.SW_NORMAL_SOLO || trooptarget == SkywarsType.SW_INSANE_SOLO
					|| trooptarget == SkywarsType.SW_Z_SOLO) {
				trooptargets = SoloPlayerManager.getPlayersInGameList();
			} else {
				trooptargets = TeamPlayerManager.getPlayersInGameList();
			}

			for (Player p : trooptargets) {

				List<Location> sphere = generateSphere(p.getLocation(), 3, false);

				for (Location loc : sphere) {
					if (loc.getBlock().getType() == CONTAMINATION_MATERIAL) {

						loc.getBlock().setType(TROOPER_MATERIAL);
						loc.getBlock().setData(TROOPER_MATERIAL_DATA);

						actived_troopers.add(loc);

					}
				}
			}

		}

		private void genTrooperIA() {

			if (trooptarget == null) {
				return;
			}

			new BukkitRunnable() {
				@SuppressWarnings("deprecation")
				@Override
				public void run() {

					List<Location> updated_actived_troopers = new ArrayList<Location>();

					for (Location l : actived_troopers) {

						if (l.getBlock().getType() == TROOPER_MATERIAL
								&& l.getBlock().getData() == TROOPER_MATERIAL_DATA) {

							Collection<Entity> entities = l.getWorld().getNearbyEntities(l, 3, 3, 3);
							boolean existplayer = false;

							for (Entity e : entities) {
								if (e.getType() == EntityType.PLAYER) {
									Player p = (Player) e;
									existplayer = SoloPlayerManager.getPlayersInGameList().contains(p);

									if (existplayer) {

										updated_actived_troopers.add(l);

										if (NumberUtil.getRandomInt(1, 2) == 1) {
											@SuppressWarnings("serial")
											List<BlockFace> bf_list = new ArrayList<BlockFace>() {
												{
													for (BlockFace bf : BlockFace.values()) {
														add(bf);
													}
												}
											};

											Collections.shuffle(bf_list);

											for (BlockFace bf : bf_list) {
												if (l.getBlock().getRelative(bf).getType() == Material.AIR) {
													Vector v = getVector(p.getLocation(), l);

													for (int i = 0; i < NumberUtil.getRandomInt(1, 3); i++) {

														l.getWorld().playSound(l, Sound.NOTE_BASS_DRUM, 5,
																NumberUtil.getRandomInt(-10, 10));
														l.getWorld().playSound(l, Sound.BLAZE_HIT, 5,
																NumberUtil.getRandomInt(-10, 10));

														Snowball bullet = (Snowball) l
																.getWorld().spawn(
																		l.getBlock().getRelative(bf).getLocation()
																				.clone().add(0.5, 0, 0.5),
																		Snowball.class);
														bullet.setMetadata("CONTAMINATION_BULLET",
																new FixedMetadataValue(Skywars.getInstance(), "dummy"));
														bullet.setTicksLived(40);
														bullet.setVelocity(v);

													}

													break;
												}
											}

										}

									}
								}
							}

							if (!existplayer) {
								l.getBlock().setType(CONTAMINATION_MATERIAL);
								continue;
							}

						} else {
							l.getBlock().setType(TROOPER_MATERIAL);
							l.getBlock().setData(TROOPER_MATERIAL_DATA);
							continue;
						}

					}

					actived_troopers.clear();
					actived_troopers.addAll(updated_actived_troopers);

				}
			}.runTaskTimer(Skywars.getInstance(), 1L, 5L);

		}

		public static Vector getVector(Location from, Location to) {
			Vector dir = from.subtract(to).toVector().normalize();
			dir.multiply(0.8);

			return dir;
		}

		public static List<Location> generateSphere(Location centerBlock, int radius, boolean hollow) {

			List<Location> circleBlocks = new ArrayList<Location>();

			int bx = centerBlock.getBlockX();
			int by = centerBlock.getBlockY();
			int bz = centerBlock.getBlockZ();

			for (int x = bx - radius; x <= bx + radius; x++) {
				for (int y = by - radius; y <= by + radius; y++) {
					for (int z = bz - radius; z <= bz + radius; z++) {

						double distance = ((bx - x) * (bx - x) + ((bz - z) * (bz - z)) + ((by - y) * (by - y)));

						if (distance < radius * radius && !(hollow && distance < ((radius - 1) * (radius - 1)))) {

							Location l = new Location(centerBlock.getWorld(), x, y, z);

							circleBlocks.add(l);

						}

					}
				}
			}

			return circleBlocks;
		}

	}

	public static class Contamination extends BukkitRunnable {

		public boolean enabled = false;

		public List<Block> contamblocks = new ArrayList<Block>();
		public ContaminationInfo contaminfo = null;
		public Block pos_block = null;

		public int i = 0;

		public void setContaminationDefaults(ContaminationInfo ci) {

			contaminfo = ci;

			pos_block = ci.getB();
			contamblocks.add(ci.getB());

			enabled = true;

		}

		@Override
		public void run() {

			if (!(i < 50)) {

				cancel();
				return;

			}

			if (!enabled || pos_block == null) {
				return;
			}

			if (contamination(pos_block)) {
				return;
			}

			Collections.shuffle(contamblocks);

			for (Block b : contamblocks) {
				if (contamination(b)) {
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

		@SuppressWarnings("unlikely-arg-type")
		public boolean contamination(Block block) {

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
				if (b.getType() != Material.AIR && b.getType() != CONTAMINATION_MATERIAL
						&& !actived_troopers.contains(b)) {
					b.setType(CONTAMINATION_MATERIAL);
					contamblocks.add(b);
					pos_block = b;
					return true;
				}
			}

			return false;

		}

	}

	public static class ContaminationInfo {

		private Block b = null;
		private World w = null;

		public ContaminationInfo(Block b, World w) {
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
