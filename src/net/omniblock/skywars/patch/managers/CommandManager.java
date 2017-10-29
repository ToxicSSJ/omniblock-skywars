package net.omniblock.skywars.patch.managers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.omniblock.lobbies.data.controller.bases.SkywarsBase;
import net.omniblock.network.library.utils.TextUtil;
import net.omniblock.packets.network.Packets;
import net.omniblock.packets.network.structure.packet.PlayerSendToServerPacket;
import net.omniblock.packets.network.structure.type.PacketSenderType;
import net.omniblock.packets.object.external.ServerType;

public class CommandManager implements CommandExecutor {

	protected List<Player> voted_players = new ArrayList<Player>();
	
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
			
		}
		
		return false;
	}
	
}
