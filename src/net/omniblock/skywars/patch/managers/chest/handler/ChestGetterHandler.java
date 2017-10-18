package net.omniblock.skywars.patch.managers.chest.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;

import net.omniblock.network.library.helpers.Scan;

public class ChestGetterHandler {

	public enum ChestType {
		
		TOOLS_CHEST(Material.DIAMOND),
		FOOD_CHEST(Material.IRON_INGOT),
		BLOCKS_CHEST(Material.GOLD_INGOT),
		
		MEGA_CHEST(Material.TRAPPED_CHEST),
		
		;
		
		private Material material;
	
		ChestType(Material material){
			this.material = material;
		}

		public Material getMaterial() {
			return material;
		}
		
		public static ChestType getByChest(Chest c){
			
			for(ChestType type : ChestType.values()){
				
				if(c.getInventory().contains(type.getMaterial())) return type;
				
			}
			
			return BLOCKS_CHEST;
			
		}
		
		public static ChestType getByMaterial(Material m){
			
			for(ChestType type : ChestType.values()){
				
				if(type.getMaterial() == m) return type;
				
			}
			
			return BLOCKS_CHEST;
			
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public Map<Chest, ChestType> getChests(World world){
		
		Map<Chest, ChestType> chests = new HashMap<Chest, ChestType>();
		
		List<Location> locs = Scan.oneMaterial(world, Material.CHEST);
		
		for(Location loc : locs){
			
			Chest chest = (Chest) loc.getBlock().getState();
			ChestType type = ChestType.getByChest(chest);
			
			chest.getInventory().clear();
			
			chests.put(chest, type);
			
		}
		
		Scan.oneMaterial(world, Material.TRAPPED_CHEST).stream().forEach(k -> {
			
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
