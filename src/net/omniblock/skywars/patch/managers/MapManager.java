package net.omniblock.skywars.patch.managers;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.YamlConfiguration;

import com.google.common.collect.Lists;

import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.games.solo.SoloSkywars;
import net.omniblock.skywars.games.solo.types.MatchType;
import net.omniblock.skywars.util.FileConfigurationUtil.Configuration;
import net.omniblock.skywars.util.FileConfigurationUtil.ConfigurationType;
import net.omniblock.skywars.util.DebugUtil;
import net.omniblock.skywars.util.FileUtil;

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
	
	public static String lastUsedMapName;
	
	public static List<String> availablesNormalWorldsNames = Lists.newArrayList();
	public static List<String> availablesZWorldsNames = Lists.newArrayList();
	
	public static World currentWorld;
	public static MapType currentMapType = MapType.UNKNOWN;
	
	/**
	 * Debido a que la clase se comporta como una clase estática, el constructor se establece como privado para evitar posibles confuciones.
	 */
	private MapManager() {
		
	}
	
	/**
	 * Solamente usado en el onEnable() del plugin, este método tuvo que ser implementado en conveniencia
	 * de la petición de Boogst en hacer los métodos de manera estáticos (static).
	 */
	public static void initialize() {
		readConfiguration();
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
		lastUsedMapName = yaml.getString("worlds.lastUsedMap", null);
		availablesNormalWorldsNames = yaml.getStringList("worlds.mapsNormal");
		availablesZWorldsNames = yaml.getStringList("worlds.mapsZ");
	}
	
	/**
	 * Saber si el MapManager tiene un mapa para la partida actual o si no cuenta con un mapa (ejemplo cuando la partida ha terminado
	 * y el MapManager se encuentra a la espera de cargar un nuevo mapa).
	 * @return true si hay un mapa cargado en el MapManager.
	 */
	public static boolean hasWorld() {
		return (currentWorld != null);
	}
	
	/**
	 * Obtiene el Mundo actual cargado en el MapManager.
	 * @return {@link World} - Mundo cargado en el MapManager.
	 */
	public static World getWorld() {
		return currentWorld;
	}
	
	/**
	 * Cuando una partia acaba se debe llamar a esta función para que el MapManager descargue el mundo actual,
	 * restaure el backup y posteriormente se prepare para cargar un nuevo mapa cuando sea necesario.
	 */
	public static void unloadWorldAndPrepareForNextRequest() {
		if(currentWorld != null) {
			String worldName = currentWorld.getName();
			Bukkit.unloadWorld(currentWorld, false);
			currentWorld = null;
			
			Configuration config = ConfigurationType.DEFAULT_CONFIGURATION.getConfig();
			YamlConfiguration yaml = config.getYaml();
			yaml.set("worlds.lastUsedMap", worldName);
			config.save();
			
			if(mapOnBackup(worldName)) {
				restoreBackup(worldName);
			}
		}
		currentMapType = MapType.UNKNOWN;
	}
	
	/**
	 * Usado para cargar el proximo mapa y guardarlo en el backup si no existe.
	 * @param match {@link MatchType} tipo de match.
	 */
	public static void prepareNextWorld(MatchType match) {
		
		DebugUtil.debugInfo("Preparando siguiente mundo.");
		
		if(currentWorld != null) {
			DebugUtil.debugInfo("¡Ya hay un mundo cargado! Descargando mundo antes de preparar el siguiente mundo.");
			unloadWorldAndPrepareForNextRequest();
		}

		DebugUtil.debugInfo("Leyendo configuración para actualizar posibles cambios ...");
		readConfiguration();
		
		switch(match) {
		case NONE:
		case INSANE:
			DebugUtil.debugInfo("Preparando Mapa del tipo INSANO");
			resolveNextNormalMap();
			break;
		case NORMAL:
			DebugUtil.debugInfo("Preparando Mapa del tipo NORMAL");
			resolveNextNormalMap();
			break;
		case Z:
			DebugUtil.debugInfo("Preparando Mapa del tipo Z");
			resolveNextZMap();
			break;
		
		}
	}
	
	/**
	 * Metodos PRIVADOS, para resolver el siguiente mapa normal.
	 * 
	 * **No cambiar la visibilidad**, su uso no está intencionado para ser usado fuera de MapManager.
	 */
	private static void resolveNextNormalMap() {
		currentMapType = MapType.NORMAL;
		
		if(availablesNormalWorldsNames.isEmpty()) {
			DebugUtil.debugInfo("¡Algo anda mal! El listado de mapas para los mundos normales está vacío, ¿se configuró correctamente en el Config.yml?");
			throw new IllegalStateException("¡No se pudo resolver el siguiente mapa por que el listado de mapas normales está vacío!");
		}
		
		String nextMapName = null;
		
		DebugUtil.debugInfo("Seleccionando un mapa aleatoriamente ...");
		
		//Si solo hay 1 mapa en el listado (poco probable :v) pues no hacemos el resto del proceso, solo escogemos ese.
		if(availablesNormalWorldsNames.size() == 1) {
			nextMapName = availablesNormalWorldsNames.get(0);
		} else {
			Random rand = new Random();
			
			while(true) {
				nextMapName = availablesNormalWorldsNames.get(rand.nextInt(availablesNormalWorldsNames.size()));
				
				//solo rompemos el bucle si el mapa seleccionado no es el mismo que se usó la última vez
				//quizas puede haber otra forma mas efectiva.
				if(!nextMapName.equalsIgnoreCase(lastUsedMapName)) {
					break;
				}
			}
		}
		
		DebugUtil.debugInfo("Mapa seleccionado aleatoriamente: " + nextMapName);
		DebugUtil.debugInfo("Comprobando si existe el backup para " + nextMapName);
		
		if(!mapOnBackup(nextMapName)) {
			DebugUtil.debugInfo("¡No existe el backup para " + nextMapName + "! Generando BACKUP ...");
			backupMap(nextMapName);
		} else { 
			DebugUtil.debugInfo("El BACKUP para '" + nextMapName + "' existe. No es necesario guardar el mapa. Restaurando BACKUP.");
			
			if(Bukkit.getWorld(nextMapName) != null) {
				Bukkit.unloadWorld(nextMapName, false);
			}
			
			restoreBackup(nextMapName);
		}
		
		currentWorld = Bukkit.createWorld(new WorldCreator(nextMapName));
		
		if(currentWorld == null) {
			try {
				DebugUtil.debugSevere("No se pudo cargar el mundo (error en Bukkit.createWorld()) ...");
				throw new Exception("¡Algo falló al cargar el mundo!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		SoloSkywars.lobbyschematic = new LobbySchematic(MapManager.getMapType());
		SoloSkywars.lobbyschematic.pasteLobbySchematic();
	}
	
	/**
	 * Metodos PRIVADOS, para resolver el siguiente mapa Z.
	 * 
	 * **No cambiar la visibilidad**, su uso no está intencionado para ser usado fuera de MapManager.
	 */
	private static void resolveNextZMap() {
		currentMapType = MapType.Z;
		if(availablesZWorldsNames.isEmpty()) {
			DebugUtil.debugInfo("¡Algo anda mal! El listado de mapas para los mundos Z está vacío, ¿se configuró correctamente en el Config.yml?");
			throw new IllegalStateException("¡No se pudo resolver el siguiente mapa por que el listado de mapas Z está vacío!");
		}
		
		String nextMapName = null;
		
		DebugUtil.debugInfo("Seleccionando un mapa aleatoriamente ...");
		
		//Si solo hay 1 mapa en el listado (poco probable :v) pues no hacemos el resto del proceso, solo escogemos ese.
		if(availablesZWorldsNames.size() == 1) {
			nextMapName = availablesZWorldsNames.get(0);
		} else {
			Random rand = new Random();
			
			while(true) {
				nextMapName = availablesZWorldsNames.get(rand.nextInt(availablesZWorldsNames.size()));
				
				//solo rompemos el bucle si el mapa seleccionado no es el mismo que se usó la última vez
				//quizas puede haber otra forma mas efectiva.
				if(!nextMapName.equalsIgnoreCase(lastUsedMapName)) {
					break;
				}
			}
		}
		
		DebugUtil.debugInfo("Mapa seleccionado aleatoriamente: " + nextMapName);
		DebugUtil.debugInfo("Comprobando si existe el backup para " + nextMapName);
		
		if(!mapOnBackup(nextMapName)) {
			DebugUtil.debugInfo("¡No existe el backup para " + nextMapName + "! Generando BACKUP ...");
			backupMap(nextMapName);
		} else { 
			DebugUtil.debugInfo("El BACKUP para '" + nextMapName + "' existe. No es necesario guardar el mapa.");
			File worldFolder = new File(".", nextMapName);
			if(!worldFolder.exists()) {
				DebugUtil.debugInfo("El BACKUP para '" + nextMapName + "' existe. ¿Pero no existe la carpeta del mundo? Restaurando BACKUP ...");
				//está en el backup pero no está la carpeta del mundo ... restauramos el backup
				restoreBackup(nextMapName);
			}
		}
		
		currentWorld = Bukkit.createWorld(new WorldCreator(nextMapName));
		
		if(currentWorld == null) {
			try {
				DebugUtil.debugSevere("No se pudo cargar el mundo (error en Bukkit.createWorld()) ...");
				throw new Exception("¡Algo falló al cargar el mundo!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		SoloSkywars.lobbyschematic = new LobbySchematic(MapManager.getMapType());
		SoloSkywars.lobbyschematic.pasteLobbySchematic();
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
	private static void restoreBackup(String name) {
		if(!mapOnBackup(name)) {
			return; //no está en el backup
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
		/** Mapa Normal **/
		NORMAL, 
		/** Mapa Z **/
		Z, 
		/** Desconocido, generalmente usado por DEFAULT cuando el manager aun no ha sido preparado para un mapa **/
		UNKNOWN;
	}
	
}
