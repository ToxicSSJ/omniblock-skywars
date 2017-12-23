package net.omniblock.skywars.patch.managers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.omniblock.lobbies.skywars.handler.base.SkywarsBase;
import net.omniblock.lobbies.utils.PlayerUtils;
import net.omniblock.network.handlers.base.bases.type.RankBase;
import net.omniblock.network.library.utils.TextUtil;
import net.omniblock.network.systems.rank.RankManager;
import net.omniblock.network.systems.rank.type.RankType;
import net.omniblock.packets.network.Packets;
import net.omniblock.packets.network.structure.packet.PlayerSendToServerPacket;
import net.omniblock.packets.network.structure.type.PacketSenderType;
import net.omniblock.packets.object.external.ServerType;
import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.SkywarsGameState;
import net.omniblock.skywars.games.solo.SoloSkywarsRunnable;
import net.omniblock.skywars.games.teams.TeamSkywarsRunnable;

public class CommandManager implements CommandExecutor {

	protected List<Player> voted_players = new ArrayList<Player>();
	
	protected boolean testmode = false;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender instanceof Player){
			
			if(cmd.getName().equalsIgnoreCase("leave") ||
			   cmd.getName().equalsIgnoreCase("salir") ||
			   cmd.getName().equalsIgnoreCase("abandonar") ||
			   cmd.getName().equalsIgnoreCase("hub")){
				
				sender.sendMessage(TextUtil.format("&bSaliendo de esta partida..."));
				
				Packets.STREAMER.streamPacket(new PlayerSendToServerPacket()
						.setServertype(ServerType.SKYWARS_LOBBY_SERVER)
						.setPlayername(sender.getName())
						.setParty(false).build().setReceiver(PacketSenderType.OMNICORE));
				return true;
				
			}
			
			if(cmd.getName().equalsIgnoreCase("gen_command_vote")){
						
				if(args.length >= 1){
					
					if(voted_players.contains((Player) sender)){
						
						sender.sendMessage(TextUtil.format("&cYa has realizado la votación en esta partida."));
						return true;
						
					}
					
					voted_players.add((Player) sender);
					
					sender.sendMessage(TextUtil.format("&8&lS&8istema &9&l» &7Gracias por darnos tu opinión, nos interesa saber que tipo de mapas te gustan para mejorar a futuro!"));
					SkywarsBase.addMapVote((args[0] == "true" || args[0] == "false") ? Boolean.getBoolean(args[0]) : false);
					return true;
					
				}
					
				sender.sendMessage(TextUtil.format("&cHa ocurrido un error mientras se procesaba el voto. &4&l#305"));
				return false;
				
			}
			
			if(cmd.getName().equalsIgnoreCase("gen_command_test")){
				
				Player player = (Player) sender;
				RankType rank = RankBase.getRank(player);
				
				if(rank == RankType.CEO || rank == RankType.ADMIN) {
					
					if(testmode == true) {
						
						player.sendMessage(TextUtil.format("&cERROR &8&m-- &6El modo testeo ya está activado."));
						return true;
						
					}
					
					if(Skywars.getGameState() != SkywarsGameState.IN_GAME) {
						
						player.sendMessage(TextUtil.format("&cERROR &8&m-- &6Debes esperar a que el juego se encuentre en modo &8IN_GAME&6."));
						return true;
						
					}
					
					Bukkit.broadcastMessage(TextUtil.format("&6&lMODO PRUEBAS ACTIVADO:"));
					Bukkit.broadcastMessage(TextUtil.format("   &7Jugadores Implicados:"));
					
					for(Player p : Bukkit.getOnlinePlayers()) {
						
						Bukkit.broadcastMessage(TextUtil.format("    &8 - &a" + p.getName()));
						
						RankManager.attachments.get(p).setPermission("bukkit.command.gamemode", true);
						p.setGameMode(GameMode.SURVIVAL);
						
						PlayerUtils.forceFly(p);
						
					}
					
					SoloSkywarsRunnable.EVENTS.clear();
					SoloSkywarsRunnable.EVENTS.put(TextUtil.format("&b&lPRUEBAS:"), 90000);
					
					TeamSkywarsRunnable.EVENTS.clear();
					TeamSkywarsRunnable.EVENTS.put(TextUtil.format("&b&lPRUEBAS:"), 90000);
					
					return true;
					
				}
				
				return false;
				
			}
			
		}
		
		return false;
	}
	
}
