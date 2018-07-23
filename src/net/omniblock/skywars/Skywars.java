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

import net.omniblock.network.systems.adapters.GameJOINAdapter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import net.citizensnpcs.api.CitizensAPI;
import net.omniblock.network.handlers.Handlers;
import net.omniblock.network.handlers.network.NetworkManager;
import net.omniblock.network.handlers.updater.object.Updatable;
import net.omniblock.network.handlers.updater.type.PluginType;
import net.omniblock.network.library.helpers.effectlib.EffectLib;
import net.omniblock.network.library.helpers.effectlib.EffectManager;
import net.omniblock.network.systems.InformationCenterPatcher;
import net.omniblock.network.systems.InformationCenterPatcher.Information;
import net.omniblock.network.systems.InformationCenterPatcher.InformationType;
import net.omniblock.packets.network.Packets;
import net.omniblock.packets.network.structure.packet.GameOnlineInfoPacket;
import net.omniblock.packets.network.structure.type.PacketSenderType;
import net.omniblock.packets.object.external.ServerType;
import net.omniblock.skywars.games.solo.SoloSkywars;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;
import net.omniblock.skywars.games.teams.TeamSkywars;
import net.omniblock.skywars.games.teams.managers.TeamPlayerManager;
import net.omniblock.skywars.patch.MapPatcher;
import net.omniblock.skywars.patch.NetworkPatcher;
import net.omniblock.skywars.patch.managers.CageManager;
import net.omniblock.skywars.patch.managers.MapManager;
import net.omniblock.skywars.patch.managers.lobby.LobbyManager;
import net.omniblock.skywars.patch.types.SkywarsType;
import net.omniblock.skywars.util.ActionBarApi;
import net.omniblock.skywars.util.DebugUtil;
import net.omniblock.skywars.util.VanishUtil;
import net.omniblock.skywars.util.FileConfigurationUtil.ConfigurationType;
import net.omniblock.skywars.util.inventory.InventoryBuilderListener;

public class Skywars extends JavaPlugin implements Updatable {

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
	public void onEnable() {

		instance = this;

		if (update(PluginType.SKYWARS, this))
			return;

		if(NetworkManager.getServertype() != ServerType.SKYWARS_GAME_SERVER) {
			
			Handlers.LOGGER.sendModuleMessage("SkyWars", "Este servidor no es de tipo Skywars Game Server, se activará SkyWars en modo API.");
			Handlers.LOGGER.sendModuleInfo("&7Se ha activado SkyWars en modo API!");
			return;
			
		}
		
		DebugUtil.setupLogger();
		CageManager.extractCages();

		DebugUtil.info("Iniciando MapManager...");
		MapManager.prepareWorlds();

		DebugUtil.info("Iniciando NetworkPatcher...");
		networkpatcher = new NetworkPatcher();
		networkpatcher.initialize();

		DebugUtil.info("Iniciando MapPatcher...");
		mappatcher = new MapPatcher();
		mappatcher.initialize();

		/**
		 * [LIB] [ActionBarApi Hook]
		 */
		ActionBarApi.nmsver = Bukkit.getServer().getClass().getPackage().getName();
		ActionBarApi.nmsver = ActionBarApi.nmsver.substring(ActionBarApi.nmsver.lastIndexOf(".") + 1);

		CitizensAPI.getNPCRegistry().deregisterAll();

		DebugUtil.info("Iniciando Librerias...");
		EffectLib.startEffectLib();
		VanishUtil.start();
		LobbyManager.start();

		GameJOINAdapter.toggleJoinMSG(false);

		InventoryBuilderListener.startInventoryBuilder();

		InformationCenterPatcher.registerAutoInformation(
				new Information(InformationType.NETWORK_BOOSTER, "skywarsnetworkbooster"),
				new String[] { "Skywars" },
				0,
				20 * 5);
		
		effectmanager = new EffectManager(this);
		
	}

	@Override
	public void onDisable() {

		CitizensAPI.getNPCRegistry().deregisterAll();

	}

	@Deprecated
	public static void makeTestMatch() {

		DebugUtil.debugInfo("Inicializando Match de prueba...");

		if (!SkywarsType.SW_Z_SOLO.makeMatch()) {
			throw new IllegalStateException("Falló al crear un Match con makeMatch()");
		}

	}

	/**
	 * Obtiene la instancia del plugin.
	 * 
	 * @return Instancia del plugin.
	 */
	public static Skywars getInstance() {
		return instance;
	}

	/**
	 * @deprecated Usa {@link ConfigurationType#DEFAULT_CONFIGURATION} en lugar
	 *             de usar getConfig() para obtener la ConfiguraciÃ³n del
	 *             plugin.
	 */
	@Override
	@Deprecated
	public FileConfiguration getConfig() {
		return ConfigurationType.DEFAULT_CONFIGURATION.getConfig().getYaml();
	}

	/**
	 * Guardar la configuraciÃ³n default
	 * ({@link ConfigurationType#DEFAULT_CONFIGURATION})
	 */
	@Override
	public void saveConfig() {
		ConfigurationType.DEFAULT_CONFIGURATION.getConfig().save();
	}

	/**
	 * @deprecated Usa {@link ConfigurationType#DEFAULT_CONFIGURATION} en lugar
	 *             de usar saveDefaultConfig().
	 */
	@Override
	@Deprecated
	public void saveDefaultConfig() {
	};

	/**
	 * Recargar la configuraciÃ³n default
	 * ({@link ConfigurationType#DEFAULT_CONFIGURATION})
	 */
	@Override
	public void reloadConfig() {
		ConfigurationType.DEFAULT_CONFIGURATION.getConfig().reload();
	}

	/**
	 * Usado en conveniencia para Wirlie, convierte los boleanos
	 * {@link #inlobby} {@link #inpregame} {@link #ingame} {@link #inend} en un
	 * Enum {@link SkywarsGameState}.
	 * 
	 * @return {@link SkywarsGameState}
	 */
	public static SkywarsGameState getGameState() {
		if (inend) {
			return SkywarsGameState.FINISHING;
		} else if (ingame) {
			return SkywarsGameState.IN_GAME;
		} else if (inpregame) {
			return SkywarsGameState.IN_PRE_GAME;
		} else if (inlobby) {
			return SkywarsGameState.IN_LOBBY;
		}

		throw new IllegalStateException("Estado actual del juego desconocido.");
	}

	/**
	 * Creado en conveniencia para Wirlie, transforma {@link SkywarsGameState}
	 * para actualizar los booleanos {@link #inlobby} {@link #inpregame}
	 * {@link #ingame} {@link #inend} de esta clase.
	 * 
	 * @param newState
	 *            Nuevo estado a actualizar.
	 */
	public static void updateGameState(SkywarsGameState newState) {
		switch (newState) {
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

		if (Skywars.currentMatchType == SkywarsType.SW_INSANE_SOLO
				|| Skywars.currentMatchType == SkywarsType.SW_NORMAL_SOLO
				|| Skywars.currentMatchType == SkywarsType.SW_Z_SOLO) {

			Packets.STREAMER.streamPacket(new GameOnlineInfoPacket()

					.setServername(Bukkit.getServerName())
					.setMapname("OmniWorld")
					.setMaximiumPlayers(SoloSkywars.MAX_PLAYERS)
					.setOnlinePlayers(SoloPlayerManager.getPlayersInLobbyAmount()).setOpened(Skywars.ingame == true
							? false : Skywars.inpregame == true ? false : Skywars.inend == true ? false : true)

					.build().setReceiver(PacketSenderType.OMNICORE));
			return;

		} else {
			
			Packets.STREAMER.streamPacket(new GameOnlineInfoPacket()

					.setServername(Bukkit.getServerName())
					.setMapname("OmniWorld")
					.setMaximiumPlayers(TeamSkywars.MAX_PLAYERS)
					.setOnlinePlayers(TeamPlayerManager.getPlayersInLobbyAmount()).setOpened(Skywars.ingame == true
							? false : Skywars.inpregame == true ? false : Skywars.inend == true ? false : true)

					.build().setReceiver(PacketSenderType.OMNICORE));
			return;

		}

	}

	/**
	 * Quizas puede ser movido, pero esto ayuda a saber que tipo de juego se
	 * esta usando actualmente en el plugin.
	 * 
	 * @param skywarsType
	 *            {@link SkywarsType}
	 */
	public static void setSkywarsType(SkywarsType skywarsType) {
		currentMatchType = skywarsType;
	}

}