package net.omniblock.skywars.patch.managers.chest.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;

import net.omniblock.skywars.patch.managers.MapManager;
import net.omniblock.skywars.patch.managers.MapManager.ScanType;
import net.omniblock.skywars.patch.types.MatchType;

public class ChestGetterHandler {

	public enum ChestType {

		TOOLS_CHEST(Material.DIAMOND),
		FOOD_CHEST(Material.IRON_INGOT),
		BLOCKS_CHEST(Material.GOLD_INGOT),

		MEGA_CHEST(Material.TRAPPED_CHEST),

		;

		private Material material;

		ChestType(Material material) {
			this.material = material;
		}

		public Material getMaterial() {
			return material;
		}

		public static ChestType getByChest(Chest c) {

			for (ChestType type: ChestType.values()) {

				if (c.getInventory().contains(type.getMaterial())) return type;

			}

			return BLOCKS_CHEST;

		}

		public static ChestType getByMaterial(Material m) {

			for (ChestType type: ChestType.values()) {

				if (type.getMaterial() == m) return type;

			}

			return BLOCKS_CHEST;

		}

	}

	@SuppressWarnings("deprecation")
	public Map < Chest,
	ChestType > getChests(MatchType match) {

		Map < Chest,
		ChestType > chests = new HashMap < Chest,
		ChestType > ();

		if(match == MatchType.NORMAL || match == MatchType.INSANE) {
			
			List<Location> locs = MapManager.NORMAL_MULTIPLE_LOCS_SCAN.get(ScanType.NORMAL_CHESTS);
			
			for (Location loc: locs) {

				Chest chest = (Chest) loc.getBlock().getState();
				ChestType type = ChestType.getByChest(chest);

				chest.getInventory().clear();

				chests.put(chest, type);

			}
			
			 MapManager.NORMAL_MULTIPLE_LOCS_SCAN.get(ScanType.IMPROVED_CHESTS).stream().forEach(k -> {

				Block block = k.getBlock();
				byte data = block.getData();

				block.setType(Material.CHEST);
				block.setData(data);

				block.getLocation().getChunk().load(true);

				Chest chest = (Chest) block.getState();
				chest.getInventory().clear();

				chests.put(chest, ChestType.MEGA_CHEST);

			});
			
		}
		
		List<Location> locs = MapManager.Z_MULTIPLE_LOCS_SCAN.get(ScanType.NORMAL_CHESTS);
		
		for (Location loc: locs) {

			Chest chest = (Chest) loc.getBlock().getState();
			ChestType type = ChestType.getByChest(chest);

			chest.getInventory().clear();

			chests.put(chest, type);

		}
		
		 MapManager.Z_MULTIPLE_LOCS_SCAN.get(ScanType.IMPROVED_CHESTS).stream().forEach(k -> {

			Block block = k.getBlock();
			byte data = block.getData();

			block.setType(Material.CHEST);
			block.setData(data);

			block.getLocation().getChunk().load(true);

			Chest chest = (Chest) block.getState();
			chest.getInventory().clear();

			chests.put(chest, ChestType.MEGA_CHEST);

		});

		return chests;

	}

}