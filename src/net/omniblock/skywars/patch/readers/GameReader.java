package net.omniblock.skywars.patch.readers;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.common.collect.Lists;

import net.omniblock.packets.network.Packets;
import net.omniblock.packets.network.structure.data.PacketSocketData;
import net.omniblock.packets.network.structure.data.PacketStructure;
import net.omniblock.packets.network.structure.data.PacketStructure.DataType;
import net.omniblock.packets.network.structure.packet.GameInitializerInfoPacket;
import net.omniblock.packets.network.structure.packet.ResposeGamePartyInfoPacket;
import net.omniblock.packets.network.tool.object.PacketReader;
import net.omniblock.packets.object.external.GamePreset;
import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.games.teams.managers.TeamPlayerManager;
import net.omniblock.skywars.patch.types.SkywarsType;

public class GameReader {

	public static void start() {

		Packets.READER.registerReader(new PacketReader<GameInitializerInfoPacket>() {

			@Override
			public void readPacket(PacketSocketData<GameInitializerInfoPacket> packetsocketdata) {

				if (Skywars.currentMatchType != null) {
					if (Skywars.currentMatchType != SkywarsType.NONE) {
						return;
					}
				}

				PacketStructure structure = packetsocketdata.getStructure();

				GamePreset preset = GamePreset.valueOf(structure.get(DataType.STRINGS, "gamepreset"));
				SkywarsType type = SkywarsType.withPreset(preset);

				if (type != null)
					type.makeMatch();

				return;

			}

			@Override
			public Class<GameInitializerInfoPacket> getAttachedPacketClass() {
				return GameInitializerInfoPacket.class;
			}

		});
		
		Packets.READER.registerReader(new PacketReader<ResposeGamePartyInfoPacket>() {

			@Override
			public void readPacket(PacketSocketData<ResposeGamePartyInfoPacket> packetsocketdata) {

				if (Skywars.currentMatchType != null) {
					if (Skywars.currentMatchType != SkywarsType.NONE) {
						return;
					}
				}

				if(Skywars.currentMatchType == SkywarsType.SW_INSANE_TEAMS 
						|| Skywars.currentMatchType == SkywarsType.SW_NORMAL_SOLO
						|| Skywars.currentMatchType == SkywarsType.SW_Z_TEAMS) {
					
					
					System.out.println("Leyendo!");
					
					PacketStructure structure = packetsocketdata.getStructure();
					
					List<Player> players_l = Lists.newArrayList();
					String players = structure.get(DataType.STRINGS, "players");
					
					for(String player : players.split(",")) {
						
						Player p = Bukkit.getPlayer(player);
						
						if(p != null) {
							
							players_l.add(p);
							continue;
							
						}
						
					}
					
					TeamPlayerManager.addPreTeam(players_l);
					
				}

				return;

			}

			@Override
			public Class<ResposeGamePartyInfoPacket> getAttachedPacketClass() {
				return ResposeGamePartyInfoPacket.class;
			}

		});
		
	}

}
