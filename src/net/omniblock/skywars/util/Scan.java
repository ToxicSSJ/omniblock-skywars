package net.omniblock.skywars.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.omniblock.skywars.patch.managers.MapManager;

public class Scan {
	
	@SuppressWarnings("deprecation")
	public static List<Location> oneMaterial(Material m){    
		World world = MapManager.getWorld();
		List<Location> locationblock = new ArrayList<Location>(); 
		List<Chunk> arrayOfChunk = Arrays.asList(world.getLoadedChunks());
	    int chunk = arrayOfChunk.size();
	    @SuppressWarnings("unused")
		int numberofscannedblocks = 0;
	    for(int i = 0; i < chunk; i++){
	    	Chunk c = arrayOfChunk.get(i);
	    	 
	        ChunkSnapshot csnapshot = c.getChunkSnapshot(false, false, false);
	         
	        int blockX = csnapshot.getX() << 4;
	        int blockZ = csnapshot.getZ() << 4;
	         
	        for(int x = 0; x < 16; x++) {
	        	for(int z = 0; z < 16; z++) {
	        		for(int y = 0; y < world.getMaxHeight(); y++) {
	        			if(csnapshot.getBlockTypeId(x, y, z) == m.getId()) {
	        				Location blockLoc = new Location(world, blockX + x, y, blockZ + z);
	        				locationblock.add(blockLoc);
	        				
	    	        		numberofscannedblocks = locationblock.size();
	        			}
	        		}
	        	}
	        }
	    }
	     
	    //Bukkit.getConsoleSender().sendMessage(TextUtil.format("&4----------------------------------------"));
	    //Bukkit.getConsoleSender().sendMessage(TextUtil.format("&eWord scanned " + "&2" + world.toString()));
	    //Bukkit.getConsoleSender().sendMessage(TextUtil.format("&eOne block scanned " + "&2"+ m.toString()));
	    //Bukkit.getConsoleSender().sendMessage(TextUtil.format("&eNumber of scanned blocks " + "&2" + numberofscannedblocks));
	    //Bukkit.getConsoleSender().sendMessage(TextUtil.format("&4----------------------------------------"));
	    
	    return locationblock;
	}
	
	@SuppressWarnings("deprecation")
	public static Location singleBlock(Material m){
		World world = MapManager.getWorld();
		Location singleBlock = null;
		List<Chunk> arrayOfChunk = MCAUtil.getChunksByMCAFiles(world);
	    int chunk = arrayOfChunk.size();
	    for(int i = 0; i < chunk; i++){
	    	Chunk c = arrayOfChunk.get(i);
	    	 
	    	ChunkSnapshot csnapshot = c.getChunkSnapshot(false, false, false);
	         
	        int blockX = csnapshot.getX() << 4;
	        int blockZ = csnapshot.getZ() << 4;
	         
	        for(int x = 0; x < 16; x++) {
	        	for(int z = 0; z < 16; z++) {
	        		for(int y = 0; y < world.getMaxHeight(); y++) {
	        			if(csnapshot.getBlockTypeId(x, y, z) == m.getId()) {
	        				singleBlock = new Location(world, blockX + x, y, blockZ + z);
	        			}
	        		}
	        	}
	        }
	    }
	     
	    //Bukkit.getConsoleSender().sendMessage(TextUtil.format("&4----------------------------------------"));
	    //Bukkit.getConsoleSender().sendMessage(TextUtil.format("&eWord scanned " + "&2" + world.toString()));
	    //Bukkit.getConsoleSender().sendMessage(TextUtil.format("&eSingle block scanned " + "&2"+ m.toString()));
	    //Bukkit.getConsoleSender().sendMessage(TextUtil.format("&4----------------------------------------"));
	    
		
	    return singleBlock; 
	}
	
	@SuppressWarnings("deprecation")
	public static Map<Material, List<Location>> multipleMaterials(Material... materials){
		Map<Material, List<Location>> returnMap = Maps.newHashMap();
		
		World world = MapManager.getWorld();
		List<Chunk> worldChunks = MCAUtil.getChunksByMCAFiles(world);
		
		for(int i = 0; i < worldChunks.size(); i++) {
			ChunkSnapshot csnapshot = worldChunks.get(i).getChunkSnapshot(false, false, false);
			
			int cLocX = csnapshot.getX() << 4;
			int cLocZ = csnapshot.getZ() << 4;

			for(int x = 0; x < 16; x++) {
				for(int z = 0; z < 16; z++) {
					for(int y = 0; y < world.getMaxHeight(); y++) {

						int typeId = csnapshot.getBlockTypeId(x, y, z);

						for(Material material : materials) {
							if(typeId == material.getId()) {
								List<Location> currentList = returnMap.get(material);

								if(currentList == null) {
									currentList = Lists.newArrayList();
								}
								
								Location blockLoc = new Location(world, cLocX + x, y, cLocZ + z);
								currentList.add(blockLoc);

								currentList.add(new Location(world, cLocX + x, y, cLocZ + z));
								returnMap.put(material, currentList);
								break;
							}
						}
					}
				}
			}
		}
		
		return returnMap;
	}
}
