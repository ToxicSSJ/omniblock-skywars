package net.omniblock.skywars.patch.managers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.World.Environment;
import org.bukkit.block.BlockState;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;
import org.inventivetalent.mapmanager.MapManagerPlugin;

import com.google.common.collect.Lists;

import net.omniblock.network.library.helpers.Scan;
import net.omniblock.network.library.utils.MCAUtils;
import net.omniblock.network.library.utils.TextUtil;
import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.patch.managers.lobby.LobbyManager;
import net.omniblock.skywars.util.DebugUtil;
import net.omniblock.skywars.util.FileConfigurationUtil.Configuration;
import net.omniblock.skywars.util.FileConfigurationUtil.ConfigurationType;
import net.omniblock.skywars.util.FileUtil;
import net.omniblock.skywars.util.NumberUtil;
import net.omniblock.skywars.util.WebUtils;
import net.omniblock.skywars.util.chunk.CleanroomChunkGenerator;

public class MapManager {

	public static World CURRENT_MAP = null;

	public static World NEW_MAP_NORMAL = null;
	public static World NEW_MAP_Z = null;

	public static String USED_MAP_NORMAL;
	public static String CURRENT_MAP_NORMAL;

	public static String USED_MAP_Z;
	public static String CURRENT_MAP_Z;

	public static List<Location> MAP_Z_SCAN_LOCATIONS = Lists.newArrayList();
	public static List<Location> MAP_NORMAL_SCAN_LOCATIONS = Lists.newArrayList();
	
	public static List<Location> MAP_Z_CAGE_LOCATIONS = Lists.newArrayList();
	public static List<Location> MAP_NORMAL_CAGE_LOCATIONS = Lists.newArrayList();

	public static List<String> availablesNormalWorldsNames = Lists.newArrayList();
	public static List<String> availablesZWorldsNames = Lists.newArrayList();

	public static Map<ScanType, List<Location>> Z_MULTIPLE_LOCS_SCAN = new HashMap<ScanType, List<Location>>();
	public static Map<ScanType, Location> Z_SINGLE_LOCS_SCAN = new HashMap<ScanType, Location>();
	
	public static Map<ScanType, List<Location>> NORMAL_MULTIPLE_LOCS_SCAN = new HashMap<ScanType, List<Location>>();
	public static Map<ScanType, Location> NORMAL_SINGLE_LOCS_SCAN = new HashMap<ScanType, Location>();
	
	public static MapType currentMapType = MapType.UNKNOWN;
	public static LobbySchematic lobbyschematic;

	public static enum ScanType {
		
		CAGES(Material.BEACON),
		
		NORMAL_CHESTS(Material.CHEST),
		IMPROVED_CHESTS(Material.TRAPPED_CHEST),
		
		SPAWN_SCHEMATIC(Material.HOPPER),
		
		;
		
		private Material material;
		
		ScanType(Material material){
			
			this.material = material;
			
		}

		public Material getMaterial(){
			return material;
		}
		
	}
	
	public static void makeScan() {
		
		if(CURRENT_MAP_NORMAL != null && CURRENT_MAP_Z != null) {
			
			World[] worlds = new World[] { Bukkit.getWorld(CURRENT_MAP_NORMAL), Bukkit.getWorld(CURRENT_MAP_Z) };
			
			Z_MULTIPLE_LOCS_SCAN.clear();
			Z_SINGLE_LOCS_SCAN.clear();
			
			NORMAL_MULTIPLE_LOCS_SCAN.clear();
			NORMAL_SINGLE_LOCS_SCAN.clear();
			
			Bukkit.getConsoleSender().sendMessage(TextUtil.format("&8 > Detectando bloques..."));
			
			List<BlockState> normal_scan = Scan.getTileEntities(worlds[0]);
			List<BlockState> z_scan = Scan.getTileEntities(worlds[1]);
			
			Bukkit.getConsoleSender().sendMessage(TextUtil.format("&8 > Se estarán escaneando las siguientes variables:"));
			
			for(ScanType type : ScanType.values()) {
				
				Bukkit.getConsoleSender().sendMessage(TextUtil.format("&8 >             - &e" + type.name()));
				
			}
			
			
			for(BlockState state : normal_scan) {
				
				B : for(ScanType type : ScanType.values()) {
					
					if(state.getBlock().getType() == type.getMaterial()) {
						
						if(type == ScanType.CAGES) {
							
							if(!NORMAL_MULTIPLE_LOCS_SCAN.containsKey(ScanType.CAGES)) {
								
								NORMAL_MULTIPLE_LOCS_SCAN.put(ScanType.CAGES, Lists.newArrayList());
								
							}
							
							NORMAL_MULTIPLE_LOCS_SCAN.get(type).add(state.getBlock().getLocation());
							state.getBlock().setType(Material.AIR);
							continue B;
							
						}
						
						if(type == ScanType.NORMAL_CHESTS || type == ScanType.IMPROVED_CHESTS) {
							
							if(!NORMAL_MULTIPLE_LOCS_SCAN.containsKey(type)) {
								
								NORMAL_MULTIPLE_LOCS_SCAN.put(type, Lists.newArrayList());
								
							}
							
							NORMAL_MULTIPLE_LOCS_SCAN.get(type).add(state.getBlock().getLocation());
							continue B;
							
						}
						
						if(type == ScanType.SPAWN_SCHEMATIC) {
							
							if(!NORMAL_SINGLE_LOCS_SCAN.containsKey(type)) {
								
								NORMAL_SINGLE_LOCS_SCAN.put(type, state.getBlock().getLocation());
								continue B;
								
							}
							
						}
						
					}
					
				}
				
			}
			
			for(BlockState state : z_scan) {
				
				B : for(ScanType type : ScanType.values()) {
					
					if(state.getBlock().getType() == type.getMaterial()) {
						
						if(type == ScanType.CAGES) {
							
							if(!Z_MULTIPLE_LOCS_SCAN.containsKey(ScanType.CAGES)) {
								
								Z_MULTIPLE_LOCS_SCAN.put(ScanType.CAGES, Lists.newArrayList());
								
							}
							
							Z_MULTIPLE_LOCS_SCAN.get(type).add(state.getBlock().getLocation());
							state.getBlock().setType(Material.AIR);
							continue B;
							
						}
						
						if(type == ScanType.NORMAL_CHESTS || type == ScanType.IMPROVED_CHESTS) {
							
							if(!Z_MULTIPLE_LOCS_SCAN.containsKey(type)) {
								
								Z_MULTIPLE_LOCS_SCAN.put(type, Lists.newArrayList());
								
							}
							
							Z_MULTIPLE_LOCS_SCAN.get(type).add(state.getBlock().getLocation());
							continue B;
							
						}
						
						if(type == ScanType.SPAWN_SCHEMATIC) {
							
							if(!Z_SINGLE_LOCS_SCAN.containsKey(type)) {
								
								state.getBlock().setType(Material.AIR);
								Z_SINGLE_LOCS_SCAN.put(type, state.getBlock().getLocation());
								continue B;
								
							}
							
						}
						
					}
					
				}
				
			}
			
		}
		
	}
	
	public static void unloadWorlds() {

		List<String> unloaded = new ArrayList<String>();

		Bukkit.getConsoleSender().sendMessage(TextUtil.format("&8----------&c[DESCARGANDO MAPAS]&8----------"));

		if (CURRENT_MAP != null) {
			if (!unloaded.contains(CURRENT_MAP.getName())) {
				Bukkit.getServer().unloadWorld(CURRENT_MAP, false);
			}
		}

		if (NEW_MAP_NORMAL != null) {
			if (!unloaded.contains(NEW_MAP_NORMAL.getName())) {
				Bukkit.getServer().unloadWorld(NEW_MAP_NORMAL, false);
			}
		}

		if (NEW_MAP_Z != null) {
			if (!unloaded.contains(NEW_MAP_Z.getName())) {
				Bukkit.getServer().unloadWorld(NEW_MAP_Z, false);
			}
		}

		Bukkit.getConsoleSender().sendMessage(TextUtil.format("&8----------&c-------------------&8----------"));

	}

	/**
	 * Solamente usado en el onEnable() del plugin, este método tuvo que ser
	 * implementado en conveniencia de la petición de Boogst en hacer los
	 * métodos de manera estáticos (static).
	 */
	public static void prepareWorlds() {

		new BukkitRunnable() {

			@Override
			public void run() {

				readConfiguration();

				Bukkit.getConsoleSender().sendMessage(TextUtil.format("&8----------&d[CARGANGO MAPAS]&8----------"));

				lobbyschematic = new LobbySchematic();

				for (MapType mt : MapType.values()) {

					if (mt == MapType.UNKNOWN) {
						continue;
					}

					MapManager.prepareNextWorld(mt);
					
				}
				
				Bukkit.getConsoleSender().sendMessage(TextUtil.format("&8----------&d----------------&8----------"));

				Bukkit.getConsoleSender().sendMessage(TextUtil.format("&8----------&b[INICIANDO ESCANEO]&8----------"));
				
				makeScan();
				
				Bukkit.getConsoleSender().sendMessage(TextUtil.format("&8 > Aplicando localizaciones."));
				
				lobbyschematic.pasteLobbySchematic(MapType.NORMAL);
				lobbyschematic.pasteLobbySchematic(MapType.Z);
				
				NORMAL_MULTIPLE_LOCS_SCAN.get(ScanType.CAGES).stream().forEach(k -> {
					
					MAP_NORMAL_CAGE_LOCATIONS.add(k);
					
				});
				
				Z_MULTIPLE_LOCS_SCAN.get(ScanType.CAGES).stream().forEach(k -> {
					
					MAP_Z_CAGE_LOCATIONS.add(k);
					
				});
				
				Bukkit.getConsoleSender().sendMessage(TextUtil.format("&8 > Aplicando vista previa."));
				
				LobbyManager.mapManagerZ = ((MapManagerPlugin) Bukkit.getPluginManager().getPlugin("MapManager")).getMapManager();
				LobbyManager.mapManagerN = ((MapManagerPlugin) Bukkit.getPluginManager().getPlugin("MapManager")).getMapManager();
				
				try {
					
					if(WebUtils.doesURLExist(new URL("http://www.omniblock.net/gameserver/skwgs/generic/" + net.omniblock.skywars.patch.managers.MapManager.CURRENT_MAP_NORMAL + ".png")))
						LobbyManager.mapControllerN = LobbyManager.mapManagerN.wrapImage(ImageIO.read(new URL("http://www.omniblock.net/gameserver/skwgs/generic/" + net.omniblock.skywars.patch.managers.MapManager.CURRENT_MAP_NORMAL + ".png"))).getController();
					
					if(WebUtils.doesURLExist(new URL("http://www.omniblock.net/gameserver/skwgs/generic/" + net.omniblock.skywars.patch.managers.MapManager.CURRENT_MAP_Z + ".png")))
						LobbyManager.mapControllerZ = LobbyManager.mapManagerZ.wrapImage(ImageIO.read(new URL("http://www.omniblock.net/gameserver/skwgs/generic/" + net.omniblock.skywars.patch.managers.MapManager.CURRENT_MAP_Z + ".png"))).getController();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				Bukkit.getConsoleSender().sendMessage(TextUtil.format("&8----------&b-------------------&8----------"));
				
			}

		}.runTaskLater(Skywars.getInstance(), 20L);

	}

	/**
	 * Metodo del tipo PRIVATE, lee la configuración para obtener el ultimo mapa
	 * usado y re-cargar el listado de mapas disponibles.
	 * 
	 * No modificar la visibilidad, no está intencionado para ser usado fuera de
	 * esta clase.
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
	 * Cuando una partia acaba se debe llamar a esta función para que el
	 * MapManager descargue el mundo actual, restaure el backup y posteriormente
	 * se prepare para cargar un nuevo mapa cuando sea necesario.
	 */
	public static void unloadActualWorldsAndReset() {

		MAP_Z_CAGE_LOCATIONS.clear();
		MAP_NORMAL_CAGE_LOCATIONS.clear();

		if (NEW_MAP_NORMAL != null) {

			String worldName = NEW_MAP_NORMAL.getName();
			Bukkit.unloadWorld(NEW_MAP_NORMAL, false);
			NEW_MAP_NORMAL = null;

			Configuration config = ConfigurationType.DEFAULT_CONFIGURATION.getConfig();
			YamlConfiguration yaml = config.getYaml();
			yaml.set("worlds.lastUsedMap", worldName);
			config.save();
			config.reload();

			if (mapOnBackup(worldName)) {
				restoreBackup(MapType.NORMAL, worldName, CURRENT_MAP_NORMAL);
			}

		}

		if (NEW_MAP_Z != null) {

			String worldName = NEW_MAP_Z.getName();
			Bukkit.unloadWorld(NEW_MAP_Z, false);
			NEW_MAP_Z = null;

			Configuration config = ConfigurationType.DEFAULT_CONFIGURATION.getConfig();
			YamlConfiguration yaml = config.getYaml();
			yaml.set("worlds.lastUsedZMap", worldName);
			config.save();
			config.reload();

			if (mapOnBackup(worldName)) {
				restoreBackup(MapType.Z, worldName, CURRENT_MAP_Z);
			}

		}

		currentMapType = MapType.UNKNOWN;

	}

	public static void prepareNextWorld(MapType mt) {

		if (mt == MapType.NORMAL) {

			// [MAPTYPE : NORMAL]
			//
			// Lo siguiente prepara
			// los mapas para el tipo
			// MapType normal.
			//
			// -------------------

			String nextMapName = null;

			if (availablesNormalWorldsNames.size() == 1) {

				nextMapName = availablesNormalWorldsNames.get(0);

				CURRENT_MAP_NORMAL = nextMapName;

			} else {

				nextMapName = availablesNormalWorldsNames
						.get(NumberUtil.getRandomInt(0, availablesNormalWorldsNames.size() - 1));
				CURRENT_MAP_NORMAL = nextMapName;

			}

			if (!mapOnBackup(nextMapName)) {

				backupMap(nextMapName);

			} else {

				if (Bukkit.getWorld(nextMapName) != null) {
					Bukkit.unloadWorld(nextMapName, false);
				}

				restoreBackup(MapType.NORMAL, nextMapName, CURRENT_MAP_NORMAL);

			}

			WorldCreator creator = new WorldCreator(nextMapName);

			Bukkit.getConsoleSender().sendMessage(TextUtil.format("&8 > Cargando el siguiente mapa:"));
			Bukkit.getConsoleSender().sendMessage(TextUtil.format("&8 >             - Nombre: &a" + nextMapName));
			Bukkit.getConsoleSender().sendMessage(TextUtil.format("&8 >             - Modalidad: &aNormal"));
			
			creator.environment(Environment.NORMAL);
			creator.generator(new CleanroomChunkGenerator("."));

			NEW_MAP_NORMAL = Bukkit.createWorld(creator);

			if (NEW_MAP_NORMAL == null) {

				DebugUtil.debugSevere("No se pudo cargar el mundo (error en Bukkit.createWorld()) ...");
				throw new RuntimeException("¡Algo falló al cargar el mundo!");

			}

			// PREPARE FOR SOLO_SKYWARS - >

			if (!Scan.WORLD_CHUNKS.containsKey(NEW_MAP_NORMAL.getName())) {
				Scan.WORLD_CHUNKS.put(NEW_MAP_NORMAL.getName(), MCAUtils.getChunksByMCAFiles(NEW_MAP_NORMAL));
			}

		} else {

			// [MAPTYPE : Z]
			//
			// Lo siguiente prepara
			// los mapas para el tipo
			// MapType z.
			//
			// -------------------

			String nextMapName = null;

			if (availablesZWorldsNames.size() == 1) {

				nextMapName = availablesZWorldsNames.get(0);
				CURRENT_MAP_Z = nextMapName;

			} else {

				nextMapName = availablesZWorldsNames.get(NumberUtil.getRandomInt(0, availablesZWorldsNames.size() - 1));
				CURRENT_MAP_Z = nextMapName;

			}

			if (!mapOnBackup(nextMapName)) {

				backupMap(nextMapName);

			} else {

				if (Bukkit.getWorld(nextMapName) != null) {
					Bukkit.unloadWorld(nextMapName, false);
				}

				restoreBackup(MapType.Z, nextMapName, CURRENT_MAP_Z);

			}

			WorldCreator creator = new WorldCreator(nextMapName);

			Bukkit.getConsoleSender().sendMessage(TextUtil.format("&8 > Cargando el siguiente mapa:"));
			Bukkit.getConsoleSender().sendMessage(TextUtil.format("&8 >             - Nombre: &a" + nextMapName));
			Bukkit.getConsoleSender().sendMessage(TextUtil.format("&8 >             - Modalidad: &cZ"));
			
			creator.environment(Environment.NORMAL);
			creator.generator(new CleanroomChunkGenerator("."));

			NEW_MAP_Z = Bukkit.createWorld(creator);

			if (NEW_MAP_Z == null) {

				DebugUtil.debugSevere("No se pudo cargar el mundo (error en Bukkit.createWorld()) ...");
				throw new RuntimeException("¡Algo falló al cargar el mundo!");

			}

			// PREPARE FOR SOLO_SKYWARS - >

			if (!Scan.WORLD_CHUNKS.containsKey(NEW_MAP_Z.getName())) {
				Scan.WORLD_CHUNKS.put(NEW_MAP_Z.getName(), MCAUtils.getChunksByMCAFiles(NEW_MAP_Z));
			}

		}

	}

	public static void setCurrentMap(MapType mt) {

		if (mt == MapType.NORMAL) {
			CURRENT_MAP = NEW_MAP_NORMAL;
		} else {
			CURRENT_MAP = NEW_MAP_Z;
		}

	}

	/**
	 * En caso de que se requiera saber que tipo de mapa está cargado en el
	 * MapManager.
	 * 
	 * @return {@link MapType} Tipo de mapa.
	 */
	public static MapType getMapType() {
		return currentMapType;
	}

	/**
	 * Saber si un mapa (usando su nombre) está almacenado en la carpeta de
	 * backups.
	 * 
	 * @param name
	 *            Nombre del mundo.
	 * @return true si el mundo con el nombre especificado se encuentra en la
	 *         carpeta de backups.
	 */
	private static boolean mapOnBackup(String name) {
		File backupFolder = new File(Skywars.getInstance().getDataFolder() + File.separator + "mapBackups");
		File backupWorldDestFolder = new File(backupFolder, name);

		return backupWorldDestFolder.exists();
	}

	/**
	 * Guarda un mapa en la carpeta de backups.
	 * 
	 * @param name
	 *            Nombre del mundo a guardar en el backup.
	 */
	private static void backupMap(String name) {
		File backupFolder = new File(Skywars.getInstance().getDataFolder() + File.separator + "mapBackups");

		if (!backupFolder.exists()) {
			backupFolder.mkdirs();
		}

		File backupWorldFolder = new File(".", name);
		File backupWorldDestFolder = new File(backupFolder, name);

		if (!backupWorldFolder.exists()) {
			throw new IllegalArgumentException(
					"La carpeta del mundo '" + name + "' no fue encontrado en: " + backupWorldFolder.getAbsolutePath());
		}

		try {
			FileUtil.copyDirectory(backupWorldFolder, backupWorldDestFolder);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Restaura un backup desde la carpeta de backups. Solo efectivo si el
	 * backup existe en la carpeta de backups.
	 * 
	 * @param name
	 *            Nombre del mundo a restaurar el backup.
	 */
	private static void restoreBackup(MapType mt, String name, String nextmap) {
		if (!mapOnBackup(name)) {
			return; // no está en el backup
		}

		File map = ConfigurationType.DEFAULT_CONFIGURATION.getFile();
		YamlConfiguration yaml = YamlConfiguration.loadConfiguration((File) map);

		if (mt == MapType.NORMAL) {
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

		NORMAL, Z,

		UNKNOWN;

	}

}
