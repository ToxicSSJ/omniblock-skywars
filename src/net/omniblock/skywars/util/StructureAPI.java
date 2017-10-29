package net.omniblock.skywars.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

import com.google.common.collect.Lists;

import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.util.jnbt.ByteArrayTag;
import net.omniblock.skywars.util.jnbt.CompoundTag;
import net.omniblock.skywars.util.jnbt.NBTInputStream;
import net.omniblock.skywars.util.jnbt.ShortTag;
import net.omniblock.skywars.util.jnbt.StringTag;
import net.omniblock.skywars.util.jnbt.Tag;

public class StructureAPI {

	public static Plugin plugin;

	public static void start() {
		plugin = Skywars.getInstance();
	}

	public static void removeSchematic(List<Block> blocks) {

		for (Block b : blocks) {

			b.setType(Material.AIR);
			b.getState().update(true);

		}

	}

	@SuppressWarnings("deprecation")
	public static List<Block> pasteSchematic(World world, Location loc, Structure schematic) {

		List<Block> changed_blocks = Lists.newArrayList();

		byte[] blocks = schematic.getBlocks();
		byte[] blockData = schematic.getData();

		short length = schematic.getLenght();
		short width = schematic.getWidth();
		short height = schematic.getHeight();

		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				for (int z = 0; z < length; ++z) {

					int index = y * width * length + z * width + x;
					Block block = new Location(world, x + loc.getX(), y + loc.getY(), z + loc.getZ()).getBlock();
					block.setTypeIdAndData(blocks[index], blockData[index], true);

					changed_blocks.add(block);

				}
			}
		}

		return changed_blocks;

	}

	@SuppressWarnings("resource")
	public static Structure loadSchematic(File file) throws IOException {

		FileInputStream stream = new FileInputStream(file);
		NBTInputStream nbtStream = new NBTInputStream(new GZIPInputStream(stream));

		CompoundTag schematicTag = (CompoundTag) nbtStream.readTag();
		if (!schematicTag.getName().equals("Schematic")) {
			throw new IllegalArgumentException("Tag \"Schematic\" does not exist or is not first");
		}

		Map<String, Tag> schematic = schematicTag.getValue();
		if (!schematic.containsKey("Blocks")) {
			throw new IllegalArgumentException("Schematic file is missing a \"Blocks\" tag");
		}

		short width = getChildTag(schematic, "Width", ShortTag.class).getValue();
		short length = getChildTag(schematic, "Length", ShortTag.class).getValue();
		short height = getChildTag(schematic, "Height", ShortTag.class).getValue();

		String materials = getChildTag(schematic, "Materials", StringTag.class).getValue();
		if (!materials.equals("Alpha")) {
			throw new IllegalArgumentException("Schematic file is not an Alpha schematic");
		}

		byte[] blocks = getChildTag(schematic, "Blocks", ByteArrayTag.class).getValue();
		byte[] blockData = getChildTag(schematic, "Data", ByteArrayTag.class).getValue();
		return new Structure(blocks, blockData, width, length, height);
	}

	/**
	 * Get child tag of a NBT structure.
	 *
	 * @param items
	 *            The parent tag map
	 * @param key
	 *            The name of the tag to get
	 * @param expected
	 *            The expected type of the tag
	 * @return child tag casted to the expected type
	 * @throws DataException
	 *             if the tag does not exist or the tag is not of the expected
	 *             type
	 */
	private static <T extends Tag> T getChildTag(Map<String, Tag> items, String key, Class<T> expected)
			throws IllegalArgumentException {
		if (!items.containsKey(key)) {
			throw new IllegalArgumentException("Schematic file is missing a \"" + key + "\" tag");
		}
		Tag tag = items.get(key);
		if (!expected.isInstance(tag)) {
			throw new IllegalArgumentException(key + " tag is not of tag type " + expected.getName());
		}
		return expected.cast(tag);
	}

	/**
	 * Get all blocks between two points and return a 3d array
	 */

	@SuppressWarnings("deprecation")
	public static int[][][] getStructure(Block block, Block block2) {
		int minX, minZ, minY;
		int maxX, maxZ, maxY;

		minX = block.getX() < block2.getX() ? block.getX() : block2.getX();
		minZ = block.getZ() < block2.getZ() ? block.getZ() : block2.getZ();
		minY = block.getY() < block2.getY() ? block.getY() : block2.getY();

		maxX = block.getX() > block2.getX() ? block.getX() : block2.getX();
		maxZ = block.getZ() > block2.getZ() ? block.getZ() : block2.getZ();
		maxY = block.getY() > block2.getY() ? block.getY() : block2.getY();

		int[][][] blocks = new int[maxX - minX + 1][maxY - minY + 1][maxZ - minZ + 1];

		for (int x = minX; x <= maxX; x++) {

			for (int y = minY; y <= maxY; y++) {

				for (int z = minZ; z <= maxZ; z++) {

					Block b = block.getWorld().getBlockAt(x, y, z);
					blocks[x - minX][y - minY][z - minZ] = b.getTypeId();

				}
			}
		}

		return blocks;

	}

	/**
	 * Pastes a structure to a desired location
	 */

	@SuppressWarnings("deprecation")
	public static Map<int[][][], Location> paste(int[][][] blocks, Location l) {

		for (int x = 0; x < blocks.length; x++) {

			for (int y = 0; y < blocks[x].length; y++) {

				for (int z = 0; z < blocks[x][y].length; z++) {
					Location neww = l.clone();
					neww.add(x, y, z);
					Block b = neww.getBlock();
					if (blocks[x][y][z] != 0) {
						b.setTypeId(blocks[x][y][z]);
						b.getState().update(true);
					}
				}

			}
		}

		return new HashMap<int[][][], Location>() {

			private static final long serialVersionUID = 1L;

			{
				put(blocks, l);
			}

		};

	}

	@SuppressWarnings("deprecation")
	public static void remove(int[][][] blocks, Location l) {

		for (int x = 0; x < blocks.length; x++) {

			for (int y = 0; y < blocks[x].length; y++) {

				for (int z = 0; z < blocks[x][y].length; z++) {
					Location neww = l.clone();
					neww.add(x, y, z);
					Block b = neww.getBlock();
					if (blocks[x][y][z] != 0) {
						b.setTypeId(0);
						b.getState().update(true);
					}
				}

			}
		}

	}

	/**
	 * Save a structure with a desired name
	 */

	public void save(String name, int[][][] b) {
		ObjectOutputStream oos = null;
		FileOutputStream fout = null;

		File f = new File(plugin.getDataFolder() + "/schematics/" + name + ".schem");
		File dir = new File(plugin.getDataFolder() + "/schematics");

		try {
			dir.mkdirs();
			f.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			fout = new FileOutputStream(f);
			oos = new ObjectOutputStream(fout);
			oos.writeObject(b);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Load structure from file
	 */

	public static int[][][] load(File f) {

		int[][][] loaded = new int[0][0][0];

		try {
			FileInputStream streamIn = new FileInputStream(f);
			ObjectInputStream objectinputstream = new ObjectInputStream(streamIn);
			loaded = (int[][][]) objectinputstream.readObject();

			objectinputstream.close();

		} catch (Exception e) {

			e.printStackTrace();
		}

		return loaded;
	}

	public static class Structure {

		private byte[] blocks;
		private byte[] data;
		private short width;
		private short lenght;
		private short height;

		public Structure(byte[] blocks, byte[] data, short width, short lenght, short height) {
			this.blocks = blocks;
			this.data = data;
			this.width = width;
			this.lenght = lenght;
			this.height = height;
		}

		/**
		 * @return the blocks
		 */
		public byte[] getBlocks() {
			return blocks;
		}

		/**
		 * @return the data
		 */
		public byte[] getData() {
			return data;
		}

		/**
		 * @return the width
		 */
		public short getWidth() {
			return width;
		}

		/**
		 * @return the lenght
		 */
		public short getLenght() {
			return lenght;
		}

		/**
		 * @return the height
		 */
		public short getHeight() {
			return height;
		}

	}

}