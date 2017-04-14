package net.omniblock.skywars.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.Chunk;
import org.bukkit.World;

import net.minecraft.server.v1_8_R3.RegionFile;
import net.minecraft.server.v1_8_R3.RegionFileCache;

/**
 * 
 * @author Wirlie
 *
 */

public class MCAUtil {
	
	@SuppressWarnings("unused")
	public static List<Chunk> getChunksByMCAFiles(World world){
		
		//sendMessageToConsole("&7+&8---------------------------------------&7+");
		//sendMessageToConsole(" &eIniciando sistema de lectura de archivos");
		//sendMessageToConsole(" &e.mca del mundo " + world.getName());
		//sendMessageToConsole(" ");
		//sendMessageToConsole(" &eSistema hecho por Wirlie.");
		//sendMessageToConsole("&7+&8---------------------------------------&7+");
		//sendMessageToConsole(" &7Leyendo &f../" + world.getName() + "/region/*.mca");
		
		File worldFolder = world.getWorldFolder();
		File regionFolder = new File(worldFolder, "region");
		
		List<Chunk> chunks = new ArrayList<Chunk>();
		int totalFiles = 0;
		int totalFiles_r = 0;
		
		long lastProgressMessageMCA = new Date().getTime();
		
		File[] files = regionFolder.listFiles();
		for(File file : files){
			
			totalFiles_r++;
			
			//Mensaje de progreso
			long currentTime = new Date().getTime();
			if((currentTime - lastProgressMessageMCA) > 1000){
				//sendMessageToConsole(" &7Leyendo archivos .mca - " + ((totalFiles_r * 100) / files.length) + "%");
				lastProgressMessageMCA = currentTime;
			}
			
			if(file.getName().endsWith(".mca")){
				totalFiles++;
				
				RegionFile resultRegionFile = null;
				
				/*
				 * Este codigo es el mismo usado en los metodos NMS 1.9
				 */
				
		        RegionFile regionfile = (RegionFile) RegionFileCache.a.get(file);
		        
		        if (regionfile != null) {
		        	resultRegionFile = regionfile;
		        } else {
		            if (!regionFolder.exists()) {
		            	regionFolder.mkdirs();
		            }
		            
		            if (RegionFileCache.a.size() >= 256) {
		            	RegionFileCache.a();
		            }

		            RegionFile regionfile1 = new RegionFile(file);
		            
		            RegionFileCache.a.put(file, regionfile1);
		            resultRegionFile = regionfile1;
		        }
		        
		        /*
		         * Adaptacion al LocationManager
		         */
		        
		        //Leer region
		        String[] nameParts = file.getName().split("\\.");
		        int startChunkIteratorX = Integer.parseInt(nameParts[1]) << 5;
		        int startChunkIteratorZ = Integer.parseInt(nameParts[2]) << 5;
		        
		        for(int cx = 0; cx < 32; cx++){
		        	for(int cz = 0; cz < 32; cz++){
		        		
			        	if(resultRegionFile.chunkExists(startChunkIteratorX + cx, startChunkIteratorZ + cz)){
			        		world.loadChunk(startChunkIteratorX + cx, startChunkIteratorZ + cz);
				        	chunks.add(world.getChunkAt(startChunkIteratorX + cx, startChunkIteratorZ + cz));
			        	}
			        	
			        }
		        }
			}
		}
		
		//sendMessageToConsole(" &7Leyendo archivos .mca - 100%");
		//sendMessageToConsole(" ");
		//sendMessageToConsole(" &7Se leyeron " + totalFiles + " archivos .mca");
		//sendMessageToConsole(" &7de los cuales se obtuvieron " + chunks.size() + " chunks.");
		//sendMessageToConsole(" &7Se ahorró la lectura innecesaria de " + ((totalFiles * 32 * 32) - chunks.size()) + " chunks :)");
		//sendMessageToConsole(" &7lo cual aumenta el rendimiento de LocationManager");
		//sendMessageToConsole(" ");
		//sendMessageToConsole(" &aEl sistema de lectura de archivos .mca ha finalizado.");
		//sendMessageToConsole("&7+&8---------------------------------------&7+");
		//sendMessageToConsole(" ");
		
		return chunks;
	}
	
	/*private static void sendMessageToConsole(String message) {
		Bukkit.getConsoleSender().sendMessage(TextUtil.format(message));
	}*/
}
