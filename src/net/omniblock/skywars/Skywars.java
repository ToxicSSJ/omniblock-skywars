/*
 *  Omniblock Developers Team - Copyright (C) 2016
 *
 *  This program is not a free software; you cannot redistribute it and/or modify it.
 *
 *  Only this enabled the editing and writing by the members of the team. 
 *  No third party is allowed to modification of the code.
 *
 */

package net.omniblock.skywars;

import java.util.logging.Handler;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import net.citizensnpcs.api.CitizensAPI;
import net.omniblock.skywars.patch.MapPatcher;
import net.omniblock.skywars.patch.NetworkPatcher;
import net.omniblock.skywars.patch.managers.CageManager;
import net.omniblock.skywars.patch.managers.MapManager;
import net.omniblock.skywars.patch.types.SkywarsType;
import net.omniblock.skywars.util.ActionBarApi;
import net.omniblock.skywars.util.BossBar;
import net.omniblock.skywars.util.DebugUtil;
import net.omniblock.skywars.util.VanishUtil;
import net.omniblock.skywars.util.FileConfigurationUtil.ConfigurationType;
import net.omniblock.skywars.util.effectlib.EffectLib;
import net.omniblock.skywars.util.effectlib.EffectManager;
import net.omniblock.skywars.util.fix.TeleportFixThree;
import net.omniblock.skywars.util.inventory.InventoryBuilderListener;

public class Skywars extends JavaPlugin {

	private static Skywars instance;
	
	private static NetworkPatcher networkpatcher;
	private static MapPatcher mappatcher;
	
	public static boolean inlobby = true;
	public static boolean inpregame = false;
	public static boolean ingame = false;
	public static boolean inend = false;
	
	public static SkywarsType currentMatchType = null;
	public static EffectManager effectmanager = null;
	
	@Override
	public void onEnable(){
		instance = this;
		
		//logger
		DebugUtil.setupLogger();
		
		//data
		CageManager.extractCages();
		//Ya que boogst no queria usar MapManager.getInstance() ¬¬ tengo que hacer un inicializador del MapManager en el onEnable
		
		DebugUtil.info("Iniciando MapManager ...");
		MapManager.prepareWorlds();
		
		// Inicializador de Patchers
		networkpatcher = new NetworkPatcher();
		networkpatcher.initialize();
		
		mappatcher = new MapPatcher();
		mappatcher.initialize();
		
		/**
		 * [LIB] [ActionBarApi Hook]
		 */
		ActionBarApi.nmsver = Bukkit.getServer().getClass().getPackage().getName();
		ActionBarApi.nmsver = ActionBarApi.nmsver.substring(ActionBarApi.nmsver.lastIndexOf(".") + 1);
		
		//Utils
		
		CitizensAPI.getNPCRegistry().deregisterAll();
		
		VanishUtil.start();
		BossBar.startBossBar();
		EffectLib.startEffectLib();
		TeleportFixThree.initialize();
		InventoryBuilderListener.startInventoryBuilder();
		
		effectmanager = new EffectManager(this);
		
	}

	@Override
	public void onDisable(){
		
		for(Handler h : DebugUtil.getLogger().getHandlers()) {
		    h.close();
		}
		
		CitizensAPI.getNPCRegistry().deregisterAll();
		
	}
	
	@Deprecated
	public static void makeTestMatch() {
		
		DebugUtil.debugInfo("Inicializando Match de prueba...");
		
		if(!SkywarsType.SW_Z_SOLO.makeMatch()) {
			throw new IllegalStateException("Falló al crear un Match con makeMatch()");
		}
		
	}
	
	/**
	 * Obtiene la instancia del plugin.
	 * @return Instancia del plugin.
	 */
	public static Skywars getInstance() {
		return instance;
	}
	
	/**
	 * @deprecated Usa {@link ConfigurationType#DEFAULT_CONFIGURATION} en lugar de usar getConfig() para obtener la ConfiguraciÃ³n del plugin.
	 */
	@Override @Deprecated
	public FileConfiguration getConfig() {
		return ConfigurationType.DEFAULT_CONFIGURATION.getConfig().getYaml();
	}
	
	/**
	 * Guardar la configuraciÃ³n default ({@link ConfigurationType#DEFAULT_CONFIGURATION})
	 */
	@Override
	public void saveConfig() {
		ConfigurationType.DEFAULT_CONFIGURATION.getConfig().save();
	}
	
	/**
	 * @deprecated Usa {@link ConfigurationType#DEFAULT_CONFIGURATION} en lugar de usar saveDefaultConfig().
	 */
	@Override @Deprecated
	public void saveDefaultConfig() { };
	
	/**
	 * Recargar la configuraciÃ³n default ({@link ConfigurationType#DEFAULT_CONFIGURATION})
	 */
	@Override
	public void reloadConfig() {
		ConfigurationType.DEFAULT_CONFIGURATION.getConfig().reload();
	}
	
	/**
	 * Usado en conveniencia para Wirlie, convierte los boleanos {@link #inlobby} {@link #inpregame} {@link #ingame} {@link #inend}
	 * en un Enum {@link SkywarsGameState}.
	 * 
	 * @return {@link SkywarsGameState}
	 */
	public static SkywarsGameState getGameState() {
		if(inend) {
			return SkywarsGameState.FINISHING;
		}else if(ingame) {
			return SkywarsGameState.IN_GAME;
		}else if(inpregame) {
			return SkywarsGameState.IN_PRE_GAME;
		}else if(inlobby) {
			return SkywarsGameState.IN_LOBBY;
		}
		
		throw new IllegalStateException("Estado actual del juego desconocido.");
	}
	
	/**
	 * Creado en conveniencia para Wirlie, transforma {@link SkywarsGameState} para actualizar los booleanos {@link #inlobby} {@link #inpregame} {@link #ingame} {@link #inend}
	 * de esta clase.
	 * 
	 * @param newState Nuevo estado a actualizar.
	 */
	public static void updateGameState(SkywarsGameState newState) {
		switch(newState) {
		case FINISHING:
			inlobby = false;
			inpregame = false;
			ingame = false;
			inend = true;
			break;
		case IN_GAME:
			inlobby = false;
			inpregame = false;
			ingame = true;
			inend = false;
			break;
		case IN_LOBBY:
			inlobby = true;
			inpregame = false;
			ingame = false;
			inend = false;
			break;
		case IN_PRE_GAME:
			inlobby = false;
			inpregame = true;
			ingame = false;
			inend = false;
			break;
		
		}
	}

	/**
	 * Quizas puede ser movido, pero esto ayuda a saber que tipo de juego se esta usando actualmente en el plugin.
	 * @param skywarsType {@link SkywarsType}
	 */
	public static void setSkywarsType(SkywarsType skywarsType) {
		currentMatchType = skywarsType;
	}
	
}