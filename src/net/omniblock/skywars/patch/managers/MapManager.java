package net.omniblock.skywars.patch.managers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;

import com.google.common.collect.Lists;

import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.games.solo.SoloSkywars;
import net.omniblock.skywars.util.DebugUtil;
import net.omniblock.skywars.util.FileConfigurationUtil.Configuration;
import net.omniblock.skywars.util.FileConfigurationUtil.ConfigurationType;
import net.omniblock.skywars.util.FileUtil;
import net.omniblock.skywars.util.MCAUtil;
import net.omniblock.skywars.util.NumberUtil;
import net.omniblock.skywars.util.Scan;
import net.omniblock.skywars.util.TextUtil;

/**
 * Esta clase se encarga de:
 * 
 * <ul>
 * 		<li>Guardar un mapa en el Backup. (Por ejemplo, antes de que un mapa sea usado).</li>
 * 		<li>Restaurar un mapa desde el Backup. (Por ejemplo, despues de que un mapa fue usado).</li>
 * 		<li>Seleccionar un mapa aleatorio del listado de mapas (siempre y cuando sea distinto al mapa usado previamente)</li>
 * 		<li>Cargar un mapa de la lista de <i>Mapas Normales</i> y <i>Mapas Z</i> sin necesidad de un plugin externo (Multiverse).</li>
 * </ul>
 * 
 * @author Wirlie
 * @since Primera Versión
 *
 */

public class MapManager {
	
	public static World CURRENT_MAP = null;
	
	public static World NEW_MAP_NORMAL = null;
	public static World NEW_MAP_Z = null;
	
	public static String USED_MAP_NORMAL;
	public static String CURRENT_MAP_NORMAL;
	
	public static String USED_MAP_Z;
	public static String CURRENT_MAP_Z;
	
	public static List<Location> MAP_Z_CAGE_LOCATIONS = Lists.newArrayList();
	public static List<Location> MAP_NORMAL_CAGE_LOCATIONS = Lists.newArrayList();
	
	public static List<String> availablesNormalWorldsNames = Lists.newArrayList();
	public static List<String> availablesZWorldsNames = Lists.newArrayList();
	
	public static MapType currentMapType = MapType.UNKNOWN;
	
	/**
	 * Debido a que la clase se comporta como una clase estática, el constructor se establece como privado para evitar posibles confuciones.
	 */
	private MapManager() {
		
	}
	
	public static void unloadWorlds() {
		
		List<String> unloaded = new ArrayList<String>();
		
		Bukkit.getConsoleSender().sendMessage(TextUtil.format("&4------------------------------------"));
		
		if(CURRENT_MAP != null) {
			if(!unloaded.contains(CURRENT_MAP.getName())) {
				Bukkit.getServer().unloadWorld(CURRENT_MAP, false);
			}
		}
		
		if(NEW_MAP_NORMAL != null) {
			if(!unloaded.contains(NEW_MAP_NORMAL.getName())) {
				Bukkit.getServer().unloadWorld(NEW_MAP_NORMAL, false);
			}
		}
		
		if(NEW_MAP_Z != null) {
			if(!unloaded.contains(NEW_MAP_Z.getName())) {
				Bukkit.getServer().unloadWorld(NEW_MAP_Z, false);
			}
		}
		
		Bukkit.getConsoleSender().sendMessage(TextUtil.format("&4------------------------------------"));
		
	}
	
	/**
	 * Solamente usado en el onEnable() del plugin, este método tuvo que ser implementado en conveniencia
	 * de la petición de Boogst en hacer los métodos de manera estáticos (static).
	 */
	public static void prepareWorlds() {
		
		readConfiguration();
		
		Bukkit.getConsoleSender().sendMessage(TextUtil.format("&4------------------------------------"));
		
		SoloSkywars.lobbyschematic = new LobbySchematic();
		
		for(MapType mt : MapType.values()) {
			
			if(mt == MapType.UNKNOWN) {
				continue;
			}
			
			MapManager.prepareNextWorld(mt);
		}
		
		Bukkit.getConsoleSender().sendMessage(TextUtil.format("&4------------------------------------"));
		
	}
	
	/**
	 * Metodo del tipo PRIVATE, lee la configuración para obtener el ultimo mapa usado y re-cargar el listado de
	 * mapas disponibles.
	 * 
	 * No modificar la visibilidad, no está intencionado para ser usado fuera de esta clase.
	 */
	private static void readConfiguration() {
		
		Configuration config = ConfigurationType.DEFAULT_CONFIGURATION.getConfig();
		YamlConfiguration yaml = config.getYaml();
		
		yaml.set("worlds.lastUsedMap", (Object) yaml.getString("worlds.currentMap"));
		yaml.set("worlds.lastUsedZMap", (Object) yaml.getString("worlds.currentZMap"));
		
		USED_MAP_NORMAL = yaml.getString("worlds.lastUsedMap");
		CURRENT_MAP_NORMAL = yaml.getString("worlds.currentMap", null);
		
		USED_MAP_Z = yaml.getString("worlds.lastUsedZMap");
		CURRENT_MAP_Z = yaml.getString("worlds.currentZMap", null);
		
		availablesNormalWorldsNames = yaml.getStringList("worlds.mapsNormal");
		availablesZWorldsNames = yaml.getStringList("worlds.mapsZ");
		
	}
	
	/**
	 * Cuando una partia acaba se debe llamar a esta función para que el MapManager descargue el mundo actual,
	 * restaure el backup y posteriormente se prepare para cargar un nuevo mapa cuando sea necesario.
	 */
	public static void unloadActualWorldsAndReset() {
		
		MAP_Z_CAGE_LOCATIONS.clear();
		MAP_NORMAL_CAGE_LOCATIONS.clear();
		
		if(NEW_MAP_NORMAL != null) {
			
			String worldName = NEW_MAP_NORMAL.getName();
			Bukkit.unloadWorld(NEW_MAP_NORMAL, false);
			NEW_MAP_NORMAL = null;
			
			Configuration config = ConfigurationType.DEFAULT_CONFIGURATION.getConfig();
			YamlConfiguration yaml = config.getYaml();
			yaml.set("worlds.lastUsedMap", worldName);
			config.save();
			config.reload();
			
			if(mapOnBackup(worldName)) {
				restoreBackup(MapType.NORMAL, worldName, CURRENT_MAP_NORMAL);
			}
			
		}
		
		if(NEW_MAP_Z != null) {
			
			String worldName = NEW_MAP_Z.getName();
			Bukkit.unloadWorld(NEW_MAP_Z, false);
			NEW_MAP_Z = null;
			
			Configuration config = ConfigurationType.DEFAULT_CONFIGURATION.getConfig();
			YamlConfiguration yaml = config.getYaml();
			yaml.set("worlds.lastUsedZMap", worldName);
			config.save();
			config.reload();
			
			if(mapOnBackup(worldName)) {
				restoreBackup(MapType.Z, worldName, CURRENT_MAP_Z);
			}
			
		}
		
		currentMapType = MapType.UNKNOWN;
		
	}
	
	public static void prepareNextWorld(MapType mt) {
		
		if(mt == MapType.NORMAL) {
			
			//    [MAPTYPE : NORMAL]
			// 
			//  Lo siguiente prepara
			//  los mapas para el tipo
			//  MapType normal.
			//
			// -------------------
			
			String nextMapName = null;

			if(availablesNormalWorldsNames.size() == 1) {
				
				nextMapName = availablesNormalWorldsNames.get(0);
				
				CURRENT_MAP_NORMAL = nextMapName;
				
			} else {
				
				nextMapName = availablesNormalWorldsNames.get(NumberUtil.getRandomInt(0 , availablesNormalWorldsNames.size() - 1));
				CURRENT_MAP_NORMAL = nextMapName;
				
			}
			
			if(!mapOnBackup(nextMapName)) {
				
				backupMap(nextMapName);
				
			} else {
				
				if(Bukkit.getWorld(nextMapName) != null) {
					Bukkit.unloadWorld(nextMapName, false);
				}
				
				restoreBackup(MapType.NORMAL, nextMapName, CURRENT_MAP_NORMAL);
				
			}
			
			NEW_MAP_NORMAL = Bukkit.createWorld(new WorldCreator(nextMapName));
			
			if(NEW_MAP_NORMAL == null) {
					
				DebugUtil.debugSevere("No se pudo cargar el mundo (error en Bukkit.createWorld()) ...");
				throw new RuntimeException("¡Algo falló al cargar el mundo!");
				
			}
			
			// PREPARE FOR SOLO_SKYWARS   - >
			
			if(!Scan.WORLD_CHUNKS.containsKey(NEW_MAP_NORMAL.getName())) {
				Scan.WORLD_CHUNKS.put(NEW_MAP_NORMAL.getName(), MCAUtil.getChunksByMCAFiles(NEW_MAP_NORMAL));
			}
			
			SoloSkywars.lobbyschematic.scanAndPasteLobbySchematic(NEW_MAP_NORMAL, mt);
			List<Location> scannedBlocks = Scan.oneMaterial(NEW_MAP_NORMAL, Material.SPONGE);
			
			for(Location loc : scannedBlocks) {
				Block bl = loc.getBlock();
				
				if(bl.getRelative(0, 1, 0).getType() == Material.WOOD_PLATE) {
					
					MAP_NORMAL_CAGE_LOCATIONS.add(loc);
					bl.getRelative(0, 1, 0).setType(Material.AIR);
					bl.setType(Material.AIR);
					
				}
			}
			
		} else {
			
			//    [MAPTYPE : Z]
			// 
			//  Lo siguiente prepara
			//  los mapas para el tipo
			//  MapType z.
			//
			// -------------------
			
			String nextMapName = null;

			if(availablesZWorldsNames.size() == 1) {
				
				nextMapName = availablesZWorldsNames.get(0);
				CURRENT_MAP_Z = nextMapName;
				
			} else {
				
				nextMapName = availablesZWorldsNames.get(NumberUtil.getRandomInt(0 , availablesZWorldsNames.size() - 1));
				CURRENT_MAP_Z = nextMapName;
				
			}
			
			if(!mapOnBackup(nextMapName)) {
				
				backupMap(nextMapName);
				
			} else {
				
				if(Bukkit.getWorld(nextMapName) != null) {
					Bukkit.unloadWorld(nextMapName, false);
				}
				
				restoreBackup(MapType.Z, nextMapName, CURRENT_MAP_Z);
				
			}
			
			NEW_MAP_Z = Bukkit.createWorld(new WorldCreator(nextMapName));
			
			if(NEW_MAP_Z == null) {
					
				DebugUtil.debugSevere("No se pudo cargar el mundo (error en Bukkit.createWorld()) ...");
				throw new RuntimeException("¡Algo falló al cargar el mundo!");
				
			}
						
			// PREPARE FOR SOLO_SKYWARS   - >
			
			if(!Scan.WORLD_CHUNKS.containsKey(NEW_MAP_Z.getName())) {
				Scan.WORLD_CHUNKS.put(NEW_MAP_Z.getName(), MCAUtil.getChunksByMCAFiles(NEW_MAP_Z));
			}
			
			SoloSkywars.lobbyschematic.scanAndPasteLobbySchematic(NEW_MAP_Z, mt);
			List<Location> scannedBlocks = Scan.oneMaterial(NEW_MAP_Z, Material.SPONGE);
			
			for(Location loc : scannedBlocks) {
				
				Block bl = loc.getBlock();
							
				if(bl.getRelative(0, 1, 0).getType() == Material.WOOD_PLATE) {
				    
					MAP_Z_CAGE_LOCATIONS.add(loc);
					bl.getRelative(0, 1, 0).setType(Material.AIR);
					bl.setType(Material.AIR);
					
				}
				
			}	
			
		}
		
	}
	
	public static void setCurrentMap(MapType mt) {
		
		if(mt == MapType.NORMAL) {
			CURRENT_MAP = NEW_MAP_NORMAL;
		} else {
			CURRENT_MAP = NEW_MAP_Z;
		}
		
	}
	
	/**
	 * En caso de que se requiera saber que tipo de mapa está cargado en el MapManager.
	 * @return {@link MapType} Tipo de mapa.
	 */
	public static MapType getMapType() {
		return currentMapType;
	}
	
	/**
	 * Saber si un mapa (usando su nombre) está almacenado en la carpeta de backups.
	 * @param name Nombre del mundo.
	 * @return true si el mundo con el nombre especificado se encuentra en la carpeta de backups.
	 */
	private static boolean mapOnBackup(String name) {
		File backupFolder = new File(Skywars.getInstance().getDataFolder() + File.separator + "mapBackups");
		File backupWorldDestFolder = new File(backupFolder, name);
		
		return backupWorldDestFolder.exists();
	}
	
	/**
	 * Guarda un mapa en la carpeta de backups.
	 * @param name Nombre del mundo a guardar en el backup.
	 */
	private static void backupMap(String name) {
		File backupFolder = new File(Skywars.getInstance().getDataFolder() + File.separator + "mapBackups");
		
		if(!backupFolder.exists()) {
			backupFolder.mkdirs();
		}
		
		File backupWorldFolder = new File(".", name);
		File backupWorldDestFolder = new File(backupFolder, name);
		
		if(!backupWorldFolder.exists()) {
			throw new IllegalArgumentException("La carpeta del mundo '" + name + "' no fue encontrado en: " + backupWorldFolder.getAbsolutePath());
		}
		
		try {
			FileUtil.copyDirectory(backupWorldFolder, backupWorldDestFolder);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Restaura un backup desde la carpeta de backups. Solo efectivo si el backup existe en la carpeta de backups.
	 * @param name Nombre del mundo a restaurar el backup.
	 */
	private static void restoreBackup(MapType mt, String name, String nextmap) {
		if(!mapOnBackup(name)) {
			return; //no está en el backup
		}
		
		File map = ConfigurationType.DEFAULT_CONFIGURATION.getFile();
		YamlConfiguration yaml =  YamlConfiguration.loadConfiguration((File) map);
		
		if(mt == MapType.NORMAL) {
			yaml.set("worlds.currentMap", (Object) nextmap);
		} else {
			yaml.set("worlds.currentZMap", (Object) nextmap);
		}
		
		try {
			yaml.save(map);
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		File backupsFolder = new File(Skywars.getInstance().getDataFolder() + File.separator + "mapBackups");
		File worldFolder = new File(".", name);
		File worldBackupFolder = new File(backupsFolder, name);
		
		FileUtil.purgeDirectory(worldFolder);
		
		try {
			FileUtil.copyDirectory(worldBackupFolder, worldFolder);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static enum MapType {
		
		NORMAL, 
		Z, 
		
		UNKNOWN;
		
	}
	
}
